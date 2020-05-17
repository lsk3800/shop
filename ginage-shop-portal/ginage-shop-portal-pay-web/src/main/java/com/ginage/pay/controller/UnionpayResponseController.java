
package com.ginage.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class UnionpayResponseController {
	private static final String RESULTPAGE="resultPage.html";
	@GetMapping("/ACPSample_B2C/frontRcvResponse")
	public String frontRcvResponse() {

		return RESULTPAGE;
	}
	@GetMapping("/ACPSample_B2C/backRcvResponse")
	public void backRcvResponse() {
		
	}
}
