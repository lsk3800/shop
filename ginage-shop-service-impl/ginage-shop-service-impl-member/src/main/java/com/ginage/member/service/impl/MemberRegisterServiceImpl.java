
package com.ginage.member.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.GinageBeanUtils;
import com.ginage.common.core.MD5Util;
import com.ginage.input.dto.UserInputDTO;
import com.ginage.member.feign.VerifyCodeServiceFeign;
import com.ginage.member.mappers.UserMapper;
import com.ginage.member.mappers.entitys.UserDO;
import com.ginage.member.service.MemberRegisterService;

/**
 * @date:2020年4月4日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
	@Autowired
	private VerifyCodeServiceFeign verifyCodeServiceFeign;
	@Autowired
	private UserMapper userMapper;

	@Override
	public BaseResponse<JSONObject> regist(UserInputDTO userInputDTO) {
		String username = userInputDTO.getUser_name();
		String password = userInputDTO.getPassword();
		String phoneNumber = userInputDTO.getMobile();

		BaseResponse<JSONObject> verifyCodeResult = verifyCodeServiceFeign.verifyCode(phoneNumber, userInputDTO.getRegistCode());
		if (!verifyCodeResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setResultError(verifyCodeResult.getMsg());
		}

		UserDO userEntity = new UserDO();
		BeanUtils.copyProperties(userInputDTO, userEntity);
		userEntity.setPassword(MD5Util.MD5(password));
		userEntity.setCreate_time(new Date());
		// TODO Auto-generated method stub
		
		return userMapper.register(userEntity) > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");
	}

}
