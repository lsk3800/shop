package com.ginage.common.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

public class BaseWebController {
	/**
	 * 500页面
	 */
	protected static final String ERROR_500 = "500.html";

	public Boolean isSuccess(BaseResponse<?> baseResp) {
		if (baseResp == null) {
			return false;
		}
		if (!baseResp.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return false;
		}
		return true;
	}

	public void setErrorMsg(Model model, String errorMsg) {
		model.addAttribute("error", errorMsg);
	}

	public String getWebBrowserInfo(HttpServletRequest request) {
		Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
		String info = browser.getName() + "/" + browser.getVersion(request.getHeader("User-Agent"));
		return info;
	}

}
