
package com.ginage.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.CookieUtils;
import com.ginage.member.feign.MemberLogoutServiceFeign;

/**
 * @date:2020年4月15日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class MemberLogoutController {
	@Autowired
	private MemberLogoutServiceFeign memberLogoutServiceFeign;

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, "GINAGE.TOKEN");
		memberLogoutServiceFeign.logout(token);
		CookieUtils.deleteCookie(request, response, "GINAGE.TOKEN");
		return "redirect:/";

	}
}
