
package com.ginage.payment.callback.alipay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.ginage.payment.callback.AbstractPayCallbackTemplate;
import com.ginage.payment.constants.PayConstant;
import com.ginage.payment.service.mapper.PaymentTransactionMapper;
import com.ginage.payment.service.mapper.entity.PaymentTransactionEntity;

/**
 * @date:2020年5月20日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
public class AlipayCallback extends AbstractPayCallbackTemplate {
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	@Override
	protected String asyncService(Map<String, String> verifySignature) {
		// 商户订单号
		String out_trade_no = verifySignature.get("out_trade_no");

		// 支付宝交易号
		String trade_no = verifySignature.get("trade_no");

		// 交易状态
		String trade_status = verifySignature.get("trade_status");

		PaymentTransactionEntity paymentTranscation = paymentTransactionMapper
				.getPaymentTranscationByOrderId(out_trade_no);
		Integer paymentStatus = paymentTranscation.getPaymentStatus();
		if (paymentStatus.equals(PayConstant.PAY_STATUS_SUCCESS)) {
			return seccessResult();
		}

		if (!(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))) {
			return failResult();
		}
		int effect = paymentTransactionMapper.updatePaymentStatus(1, out_trade_no);
		if (effect < 1) {
			return failResult();
		}
		return seccessResult();
	}

	@Override
	protected String failResult() {
		// TODO Auto-generated method stub
		return PayConstant.ALI_RESULT_FAIL;
	}

	@Override
	protected String seccessResult() {
		// TODO Auto-generated method stub
		return PayConstant.ALI_RESULT_SUCCESS;
	}

	@Override
	protected Map<String, String> verifySignature(HttpServletRequest request, HttpServletResponse response) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}

		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,
					AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 调用SDK验证签名

		// ——请在这里编写您的程序（以下代码仅作参考）——

		/*
		 * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
		 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		 * 4、验证app_id是否为该商户本身。
		 */
		if (signVerified) {// 验证成功

			params.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
			params.put("payId", params.get("trade_no"));
			params.put("channelId", "ali_pay");
			params.put("orderId", params.get("out_trade_no"));
			params.put("asyncLog", params.get("trade_status"));
		} else {// 验证失败
			params.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);
		}

		return params;
	}

}
