
package com.ginage.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.output.dto.UserLoginDTO;
import com.xxl.sso.core.user.XxlSsoUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @date:2020年4月29日
 * @description:
 * @Copyright: ginage.com
 *
 */

@Api(tags = "会员单点登录接口")
public interface SSOLoginService {
	@ApiOperation(value = "会员单点登录接口")
	@PostMapping("/ssoLogin")
	public BaseResponse<XxlSsoUser> ssoLogin(@RequestBody UserLoginDTO userLoginDTO);
	@ApiOperation(value = "会员单点登录接口")
	@PostMapping("/ssoCheckUserToken")
	public BaseResponse<XxlSsoUser> ssoCheckUserToken(@RequestParam ("xxl_sso_sessionid") String xxl_sso_sessionid);
}
