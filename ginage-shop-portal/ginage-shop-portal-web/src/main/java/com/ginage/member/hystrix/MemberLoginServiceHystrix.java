
package com.ginage.member.hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.member.feign.MemberLoginServiceFeign;
import com.ginage.output.dto.UserLoginDTO;

/**
*@date:2020年4月9日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class MemberLoginServiceHystrix extends BaseApiService<JSONObject> implements MemberLoginServiceFeign {

	@Override
	public BaseResponse<JSONObject> login(UserLoginDTO userLoginDTO){
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

	@Override
	public BaseResponse<JSONObject> checkUserToken(String token) {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

	@Override
	public BaseResponse<JSONObject> qqLogin(UserLoginDTO userLoginDTO, String qqOpenID) {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

	

}
