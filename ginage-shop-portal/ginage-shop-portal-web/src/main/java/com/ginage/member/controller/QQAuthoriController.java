
package com.ginage.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.CookieUtils;
import com.ginage.member.feign.MemberLoginServiceFeign;
import com.ginage.member.feign.QQAuthoriServiceFeign;
import com.ginage.member.vo.LoginVo;
import com.ginage.output.dto.UserLoginDTO;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * @date:2020年4月21日
 * @description:
 * @Copyright: ginage.com
 *
 */

@Controller
public class QQAuthoriController extends BaseApiService<JSONObject> {
	private final String ERROR500 = "error500.html";
	private final String INDEX = "index.html";
	private final String QQLOGIN = "member/qqlogin.html";
	@Autowired
	private QQAuthoriServiceFeign qqAuthoriServiceFeign;
	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;

	/**
	 * qq授权链接
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/qqAuth")
	public String qqAuth(HttpServletRequest request) {

		//这个请求会重定向到腾讯的qq登录授权网站
		try {
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			return "redirect:" + authorizeURL;
		} catch (QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR500;
		}

	}

	/**
	 * 授权后的回调函数
	 * 
	 * @param request
	 * @param model
	 * @param response
	 */
	@RequestMapping("/qqLoginBack")
	public String qqLoginBack(String code, HttpServletRequest request, Model model, HttpServletResponse response) {
		BaseResponse<JSONObject> checkQQOpenIDResult = null;
		//授权后的回调方法,先得到accessToken
		
		try {
			AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
			if (accessTokenObj == null) {
				return ERROR500;
			}
			String accessToken = accessTokenObj.getAccessToken();
			if (StringUtils.isEmpty(accessToken)) {
				return ERROR500;
			}
			//通过accessToken得到openID
			OpenID openIDObj = new OpenID(accessToken);
			String userOpenID = openIDObj.getUserOpenID();
			if (StringUtils.isEmpty(userOpenID)) {
				return ERROR500;
			}

			//浏览器信息
			String deviceInfo = new BaseWebController().getWebBrowserInfo(request);
			//检查openID是否关联到已注册帐号
			checkQQOpenIDResult = qqAuthoriServiceFeign.checkQQOpenID(userOpenID, deviceInfo);
			if (checkQQOpenIDResult.getCode().equals(Constants.HTTP_RES_CODE_500)) {
				return ERROR500;
			}
			//如果返回203则没有关联账号,那么转到绑定页面
			if (checkQQOpenIDResult.getCode().equals(Constants.HTTP_RES_CODE_203)) {
				UserInfo userInfoObj = new UserInfo(accessToken, userOpenID);
				UserInfoBean userInfoBean = userInfoObj.getUserInfo();
				if (userInfoBean == null) {
					return ERROR500;
				}
				String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
				LoginVo loginVo = new LoginVo();
				model.addAttribute("avatarURL100", avatarURL100);
				model.addAttribute("loginVo", loginVo);
				request.getSession().setAttribute("qqOpenID", userOpenID);
				return QQLOGIN;
			}

		} catch (QQConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//如果openid已经有绑定了,那么就登录
		CookieUtils.setCookie(request, response, "GINAGE.TOKEN", checkQQOpenIDResult.getData().getString("token"));
		model.addAttribute("isLogined", true);
		String mobile = checkQQOpenIDResult.getData().getString("mobile");

		model.addAttribute("user", mobile);
		return "redirect:/" + INDEX;
	}

	/**
	 * 绑定已注册的帐号
	 * 
	 * @param loginVo
	 * @param bindingResult
	 * @param response
	 * @param model 
	 * @param model
	 * @return
	 */
	@RequestMapping("/qqJointLogin")
	@ResponseBody
	public BaseResponse<JSONObject> qqJointLogin(@Validated LoginVo loginVo, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		if (bindingResult.hasErrors()) {
			return setResultError(bindingResult.getFieldError().getDefaultMessage());
		}

		//从session里面得到openID
		String qqOpenID = (String) request.getSession().getAttribute("qqOpenID");
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		BeanUtils.copyProperties(loginVo, userLoginDTO);

		//将openID绑定到帐号
		BaseResponse<JSONObject> checkUserResult = memberLoginServiceFeign.qqLogin(userLoginDTO, qqOpenID);

		if (!checkUserResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setResultError(checkUserResult.getMsg());
		}

		BaseResponse<JSONObject> loginResult = null;
		userLoginDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
		userLoginDTO.setDeviceInfo(new BaseWebController().getWebBrowserInfo(request));

		//绑定成功后登录
		try {
			loginResult = memberLoginServiceFeign.login(userLoginDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (loginResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			CookieUtils.setCookie(request, response, "GINAGE.TOKEN", loginResult.getData().getString("token"));
			model.addAttribute("user", loginResult.getData().getString("mobile"));
		}

		return loginResult;

	}

}
