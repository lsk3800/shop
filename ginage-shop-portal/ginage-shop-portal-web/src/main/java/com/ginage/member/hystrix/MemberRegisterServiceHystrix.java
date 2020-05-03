
package com.ginage.member.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.input.dto.UserInputDTO;
import com.ginage.member.controller.BaseWebController;
import com.ginage.member.feign.MemberRegisterServiceFeign;

/**
*@date:2020年4月11日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class MemberRegisterServiceHystrix extends BaseApiService<JSONObject> implements MemberRegisterServiceFeign{

	@Override
	public BaseResponse<JSONObject> regist(UserInputDTO userInputDTO) {
		// TODO Auto-generated method stub
		return setResultError("系统忙,请稍后再试!");
	}

}
