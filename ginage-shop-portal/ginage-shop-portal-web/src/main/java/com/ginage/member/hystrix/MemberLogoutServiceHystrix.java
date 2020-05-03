
package com.ginage.member.hystrix;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.member.feign.MemberLogoutServiceFeign;

/**
*@date:2020年4月16日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class MemberLogoutServiceHystrix extends BaseApiService<JSONObject> implements MemberLogoutServiceFeign{

	@Override
	public BaseResponse<JSONObject> logout(String token) {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

}
