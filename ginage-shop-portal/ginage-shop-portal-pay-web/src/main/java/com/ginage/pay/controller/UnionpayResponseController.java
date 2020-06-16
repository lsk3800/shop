
package com.ginage.pay.controller;

import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class UnionpayResponseController {
	private static final String RESULTPAGE = "resultPage.html";

	@GetMapping("/ACPSample_B2C/frontRcvResponse/{orderId}/{result}/{respCode}")
	public String frontRcvResponse(Model model, @PathVariable("orderId") String orderId,
			@PathVariable("result") String result, @PathVariable("respCode") String respCode)
			throws UnsupportedEncodingException {

		model.addAttribute("orderId", orderId);
		model.addAttribute("result", result);
		model.addAttribute("respCode", respCode);

		return RESULTPAGE;

	}

}
