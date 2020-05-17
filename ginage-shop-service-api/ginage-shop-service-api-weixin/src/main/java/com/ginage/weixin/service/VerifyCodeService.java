
package com.ginage.weixin.service;

/**
*@date:2020年4月2日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信注册码验证")
public interface VerifyCodeService {
	@ApiOperation("验证注册码是否正确!")
	@GetMapping("/verifyCode")
	public BaseResponse<JSONObject> verifyCode(@RequestParam("phoneNumber") String phoneNumber,@RequestParam("code") String code);

}
