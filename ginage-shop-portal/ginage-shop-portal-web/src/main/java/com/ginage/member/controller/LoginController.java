
package com.ginage.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.CookieUtils;
import com.ginage.common.core.RandomValidateCodeUtil;
import com.ginage.member.feign.MemberLoginServiceFeign;
import com.ginage.member.vo.LoginVo;
import com.ginage.output.dto.UserLoginDTO;


/**
 * @date:2020年4月7日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class LoginController extends BaseWebController {
	@Autowired
	private MemberLoginServiceFeign MemberLoginServiceFeign;
	private static final String LOGIN = "member/login.html";
	private static final String INDEX = "index.html";

	@PostMapping("/login.html")
	public String postLogin(@Validated LoginVo loginVo, BindingResult bindingResult, Model model,
			HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("loginVo", loginVo);

			setErrorMsg(model, bindingResult.getFieldError().getDefaultMessage());
			return LOGIN;
		}

		Boolean checkVerify = RandomValidateCodeUtil.checkVerify(loginVo.getGraphicCode(), httpSession);
		if (!checkVerify) {
			setErrorMsg(model, "验证码错误!");
			model.addAttribute("loginVo", loginVo);
			return LOGIN;
		}

		UserLoginDTO userLoginDTO = new UserLoginDTO();
		BeanUtils.copyProperties(loginVo, userLoginDTO);
		BaseResponse<JSONObject> loginResult = null;
		userLoginDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
		userLoginDTO.setDeviceInfo(getWebBrowserInfo(request));
		try {
			loginResult = MemberLoginServiceFeign.login(userLoginDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!loginResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			model.addAttribute("loginVo", loginVo);
			setErrorMsg(model, loginResult.getMsg());
			return LOGIN;
		}

		CookieUtils.setCookie(request, response, "GINAGE.TOKEN", loginResult.getData().getString("token"));
		model.addAttribute("isLogined", true);
		model.addAttribute("user", loginVo.getMobile());
		return "redirect:/"+INDEX;
	}

	@GetMapping("/login.html")
	public String getLogin(Model model) {
		LoginVo loginVo = new LoginVo();
		model.addAttribute("loginVo", loginVo);
		model.addAttribute("isLogined", false);
		return LOGIN;
	}

	

}
