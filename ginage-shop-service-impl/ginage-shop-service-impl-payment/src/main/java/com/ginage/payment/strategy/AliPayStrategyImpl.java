
package com.ginage.payment.strategy;

import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.dto.output.PaymentTransacDTO;

/**
*@date:2020年5月16日
*@description:
*@Copyright: ginage.com
*
*/
public class AliPayStrategyImpl implements PayStrategy {


	/**
	 * 支付宝支付具体实现方法
	 */
	@Override
	public String toPayHtml(PaymentChannelDTO paymentChannelDTO, PaymentTransacDTO paymentTransacDTO) {
		// TODO Auto-generated method stub
		return "支付宝支付";
	}


}
