
package com.ginage.payment.callback.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ginage.payment.callback.AbstractPayCallbackTemplate;
import com.ginage.payment.callback.factory.TemplateFactory;

/**
 * @date:2020年5月20日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class AlipayAsyncCallbackService {
	private static final String ALIPAYCALLBACKBEANNAME = "alipayCallback";

	@RequestMapping("/alipayAsyncCallback")
	public String alipayAsyncCallback(HttpServletRequest request, HttpServletResponse response) {

		AbstractPayCallbackTemplate alipayAsyncCallbackTemplate = TemplateFactory
				.getPayCallbackTemplate(ALIPAYCALLBACKBEANNAME);
		String asyncCallback = alipayAsyncCallbackTemplate.asyncCallback(request, response);
		//System.out.println(asyncCallback);
		return asyncCallback;
	}
}
