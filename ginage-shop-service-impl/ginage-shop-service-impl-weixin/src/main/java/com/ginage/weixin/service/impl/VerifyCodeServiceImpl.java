
package com.ginage.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.RedisUtils;
import com.ginage.weixin.service.VerifyCodeService;

/**
 * @date:2020年4月2日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class VerifyCodeServiceImpl extends BaseApiService<JSONObject> implements VerifyCodeService {
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public BaseResponse<JSONObject> verifyCode(String phone,String code) {
		String phoneNumber=Constants.WEIXINCODE_KEY+phone;
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(phone)) {
			return setResultError("手机号不能为空");
		}
		if(StringUtils.isEmpty(code)) {
			return setResultError("注册码不能为空");
		}
		
		String redisCode=redisUtils.get(phoneNumber);
		if(null==redisCode) {
			return setResultError("无效或过期的注册码!");
		}
		if(redisCode.contentEquals(code)) {
			return setResultSuccess("注册码对比正确!");
			
		}else {
			return setResultError("注册码不正确!");
		}
	}

}
