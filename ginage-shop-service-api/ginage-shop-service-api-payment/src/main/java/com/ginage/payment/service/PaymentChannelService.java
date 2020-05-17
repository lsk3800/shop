
package com.ginage.payment.service;

import java.util.List;

/**
*@date:2020年5月16日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;

import com.ginage.dto.output.PaymentChannelDTO;

public interface PaymentChannelService {
	/**
	 * 获取支付渠道
	 * @return
	 */
	@GetMapping("/getPaymentChannel")
	public List<PaymentChannelDTO> getPaymentChannel();
}
