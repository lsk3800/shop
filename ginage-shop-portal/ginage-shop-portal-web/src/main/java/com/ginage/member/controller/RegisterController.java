
package com.ginage.member.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.RandomValidateCodeUtil;
import com.ginage.input.dto.UserInputDTO;
import com.ginage.member.feign.MemberRegisterServiceFeign;
import com.ginage.member.vo.RegisterVo;

/**
 * @date:2020年4月8日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class RegisterController extends BaseWebController {
	@Autowired
	private MemberRegisterServiceFeign memberRegisterServiceFeign;
	private static final String REGIST = "member/register.html";
	private static final String INDEX = "index.html";

	@PostMapping("/register.html")

	public String postRegister(@Validated RegisterVo registerVo, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("registerVo",registerVo);
			setErrorMsg(model, bindingResult.getFieldError().getDefaultMessage());
			return REGIST;
		}
		System.out.println(registerVo);

		Boolean checkVerifyResult = RandomValidateCodeUtil.checkVerify(registerVo.getGraphicCode(), httpSession);
		if (!checkVerifyResult) {
			setErrorMsg(model, "验证码错误!");
			model.addAttribute("registerVo",registerVo);
			return REGIST;
		}
		UserInputDTO userInputDTO = new UserInputDTO();
		BeanUtils.copyProperties(registerVo, userInputDTO);
		BaseResponse<JSONObject> registResult = memberRegisterServiceFeign.regist(userInputDTO);
		if(!registResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			model.addAttribute("registerVo",registerVo);
			setErrorMsg(model, registResult.getMsg());
			return REGIST;
		}
		return INDEX;
	}

	@GetMapping("/register.html")

	public String getRegister(Model model) {
		RegisterVo registerVo=new RegisterVo();
		model.addAttribute("registerVo",registerVo);
		return REGIST;
	}
}
