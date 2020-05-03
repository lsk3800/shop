
package com.ginage.service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.input.dto.UserInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @date:2020年4月4日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Api(tags = "会员注册接口")
public interface MemberRegisterService {
	@ApiOperation(value="会员注册接口")
	@PostMapping("/register")
	public BaseResponse<JSONObject> regist(@RequestBody UserInputDTO userInputDTO);
}
