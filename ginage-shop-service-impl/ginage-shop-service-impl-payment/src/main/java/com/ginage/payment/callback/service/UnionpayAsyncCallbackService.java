
package com.ginage.payment.callback.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ginage.payment.callback.AbstractPayCallbackTemplate;
import com.ginage.payment.callback.factory.TemplateFactory;

/**
 * @date:2020年5月18日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class UnionpayAsyncCallbackService {
	
	private static final String UNIONPAYCALLBACKBEANNAME="unionpayCallback";
	@RequestMapping("/unionpayAsyncCallback")
	public String unionpayAsyncCallback(HttpServletRequest request, HttpServletResponse response) {

		AbstractPayCallbackTemplate unionpayAsyncCallbackTemplate=
				TemplateFactory.getPayCallbackTemplate(UNIONPAYCALLBACKBEANNAME);
		String asyncCallback = unionpayAsyncCallbackTemplate.asyncCallback(request, response);
		//System.out.println(asyncCallback);
		
		return asyncCallback;
	}
}
