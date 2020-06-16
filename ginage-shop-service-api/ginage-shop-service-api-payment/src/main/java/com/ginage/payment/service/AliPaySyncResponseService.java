
package com.ginage.payment.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
*@date:2020年5月20日
*@description:
*@Copyright: ginage.com
*
*/
public interface AliPaySyncResponseService {

	
	@GetMapping("/alipaySyncResponse")
	public void alipaySyncResponse(HttpServletRequest request,HttpServletResponse response);
}
