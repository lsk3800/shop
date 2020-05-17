
package com.ginage.payment.service;


/**
*@date:2020年5月14日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.dto.input.CreatePayTokenDTO;

public interface PaymentTransacService {
	/**
	 * 创建支付token
	 * @return
	 */
	@GetMapping("/pay")
	public BaseResponse<JSONObject> createPaymentToken(CreatePayTokenDTO createPayTokenDTO);
}
