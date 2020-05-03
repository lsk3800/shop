
package com.ginage.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.output.dto.UserLoginDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @date:2020年4月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Api(tags = "会员登录接口")
public interface MemberLoginService {
	@ApiOperation(value = "会员登录接口")
	@PostMapping("/login")
	public BaseResponse<JSONObject> login(@RequestBody UserLoginDTO userLoginDTO);

	@ApiOperation(value = "根据token自动登录")
	@PostMapping("/checkUserToken")
	public BaseResponse<JSONObject> checkUserToken(@RequestParam("token") String token);

	@ApiOperation(value = "检查手机号和密码")
	@PostMapping("/qqLogin")
	public BaseResponse<JSONObject> qqLogin(@RequestBody UserLoginDTO userLoginDTO,@RequestParam("qqOpenID") String qqOpenID);
}
