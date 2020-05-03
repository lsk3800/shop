
package com.ginage.weixin.hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.output.dto.UserOutputDTO;
import com.ginage.weixin.feign.MemberServiceFeign;

/**
*@date:2020年4月5日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class MemberServiceHystrix extends BaseApiService<UserOutputDTO>implements MemberServiceFeign{

	@Override
	public BaseResponse<UserOutputDTO> existMobile(String phoneNumber) {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

	@Override
	public BaseResponse<UserOutputDTO> getInfo() {
		// TODO Auto-generated method stub
		return setResultError("服务器忙,请稍后再试!");
	}

}
