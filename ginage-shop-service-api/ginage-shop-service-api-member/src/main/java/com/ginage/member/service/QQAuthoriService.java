
package com.ginage.member.service;
/**
*@date:2020年4月23日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

public interface QQAuthoriService {

	@RequestMapping("/checkQQOpenID")
	public BaseResponse<JSONObject> checkQQOpenID(@RequestParam("qq_openid") String qq_openid,@RequestParam("deviceInfo") String deviceInfo);
}
