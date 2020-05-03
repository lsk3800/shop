
package com.ginage.member.hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.member.feign.QQAuthoriServiceFeign;

/**
*@date:2020年4月23日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class QQAuthoriServiceHystrix extends BaseApiService<JSONObject> implements QQAuthoriServiceFeign{


	@Override
	public BaseResponse<JSONObject> checkQQOpenID(String qq_openid, String deviceInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
