
package com.ginage.member.hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.member.feign.VerifyCodeServiceFeign;

/**
*@date:2020年4月5日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class VerifyCodeServiceHystrix extends BaseApiService<JSONObject> implements VerifyCodeServiceFeign{

	@Override
	public BaseResponse<JSONObject> verifyCode(String phoneNumber, String code) {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

}
