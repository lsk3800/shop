
package com.ginage.payment.service;
/**
*@date:2020年5月15日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ginage.common.base.BaseResponse;
import com.ginage.dto.output.PaymentTransacDTO;

public interface PaymentTransactionInfoService {
	@GetMapping("/paymentTransation")
	public BaseResponse<PaymentTransacDTO> getPaymentTransationByToken(@RequestParam("token") String token);
}
