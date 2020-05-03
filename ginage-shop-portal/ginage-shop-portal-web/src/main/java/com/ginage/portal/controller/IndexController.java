
package com.ginage.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.CookieUtils;
import com.ginage.member.feign.MemberLoginServiceFeign;
import com.ginage.member.feign.SSOLoginServiceFeign;
import com.ginage.output.dto.UserLoginDTO;
import com.xxl.sso.core.user.XxlSsoUser;

/**
*@date:2020年4月7日
*@description:
*@Copyright: ginage.com
*
*/
@Controller
public class IndexController {

	private static final String INDEX="index.html";
	private static final String TEST="test";
	@Autowired
	private SSOLoginServiceFeign ssoLoginServiceFeign;
	
	
	@RequestMapping("/")
	
	public String index() {
		return INDEX;
		
	}
	/*
	 * public String index(HttpServletRequest request) { String xxl_sso_sessionid =
	 * CookieUtils.getCookieValue(request, "xxl_sso_sessionid");
	 * if(!StringUtils.isEmpty(xxl_sso_sessionid)) { BaseResponse<XxlSsoUser>
	 * checkUserToken = ssoLoginServiceFeign.ssoCheckUserToken(xxl_sso_sessionid);
	 * if(checkUserToken.getCode().equals(Constants.HTTP_RES_CODE_200)) { String
	 * mobile = checkUserToken.getData().getUsername(); mobile =
	 * mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"); if
	 * (!StringUtils.isEmpty(mobile)) { request.setAttribute("mobile", mobile); }
	 * 
	 * }
	 * 
	 * } return INDEX; }
	 */
	@RequestMapping("/index.html")
	public String toIndex() {
		return "redirct:http://shop.ginage.com";
	}
	
	
	@RequestMapping("/test")
	public String test(Model model) {
		model.addAttribute("name","jin");
		return TEST;
	}
	
	
}
