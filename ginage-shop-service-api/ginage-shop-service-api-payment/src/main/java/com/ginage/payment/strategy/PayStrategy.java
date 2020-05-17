
package com.ginage.payment.strategy;

import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.dto.output.PaymentTransacDTO;

/**
 * @date:2020年5月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
public interface PayStrategy {
	/**
	 * 获取支付html
	 * 支付父类
	 * @return
	 */
	public String toPayHtml(PaymentChannelDTO paymentChannelDTO,PaymentTransacDTO paymentTransacDTO);
}