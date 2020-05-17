
package com.ginage.member.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.core.CookieUtils;
import com.ginage.common.core.RedisUtils;
import com.ginage.member.service.MemberLogoutService;

/**
 * @date:2020年4月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class MemberLogoutServiceImpl extends BaseApiService<JSONObject> implements MemberLogoutService {

	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@Override
	public BaseResponse<JSONObject> logout(String token) {
		// TODO Auto-generated method stub
		boolean del = redisUtils.del(token);
		if (!del) {
			return setResultError("删除Redis中的token失败");
		}
		return setResultSuccess("删除Redis中的token成功");
	}

}
