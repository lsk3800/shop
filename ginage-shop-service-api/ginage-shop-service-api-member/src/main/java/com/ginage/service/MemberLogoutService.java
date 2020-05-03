
package com.ginage.service;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
*@date:2020年4月16日
*@description:
*@Copyright: ginage.com
*
*/
@Api(tags="会员退出接口")
public interface MemberLogoutService {

	@ApiOperation(value = "会员退出接口")
	@GetMapping("/logout")
	public BaseResponse<JSONObject> logout(@RequestParam("token") String token);
}
