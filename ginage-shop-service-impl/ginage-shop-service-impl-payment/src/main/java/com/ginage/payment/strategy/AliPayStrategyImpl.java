
package com.ginage.payment.strategy;

import java.text.DecimalFormat;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.dto.output.PaymentTransacDTO;

/**
 * @date:2020年5月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
public class AliPayStrategyImpl implements PayStrategy {

	/**
	 * 支付宝支付具体实现方法
	 */
	@Override
	public String toPayHtml(PaymentChannelDTO paymentChannelDTO, PaymentTransacDTO paymentTransacDTO) {
		// 获得初始化的AlipayClient

		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
				AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(paymentChannelDTO.getSyncUrl());
		alipayRequest.setNotifyUrl(paymentChannelDTO.getAsynUrl());

		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = paymentTransacDTO.getOrderId();
		// 付款金额，必填
		DecimalFormat df=new DecimalFormat("#.00");
		String total_amount = df.format(paymentTransacDTO.getPayAmount()/100);
		// 订单名称，必填
		String subject = paymentTransacDTO.getOrderName();
		// 商品描述，可空
		String body = paymentTransacDTO.getOrderName();

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		// alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
		// + "\"total_amount\":\""+ total_amount +"\","
		// + "\"subject\":\""+ subject +"\","
		// + "\"body\":\""+ body +"\","
		// + "\"timeout_express\":\"10m\","
		// + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		// 请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

		// 请求
		String result="";
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 输出
		// out.println(result);
		return result;
	}

}
