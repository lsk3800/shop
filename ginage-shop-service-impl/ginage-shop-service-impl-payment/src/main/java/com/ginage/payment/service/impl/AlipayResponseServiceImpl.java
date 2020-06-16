
package com.ginage.payment.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.ginage.payment.constants.PayConstant;
import com.ginage.payment.service.AliPaySyncResponseService;

/**
 * @date:2020年5月20日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class AlipayResponseServiceImpl implements AliPaySyncResponseService {

	@Override
	public void alipaySyncResponse(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 获取支付宝GET过来反馈信息
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
		} catch (AlipayApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // 调用SDK验证签名

		// ——请在这里编写您的程序（以下代码仅作参考）——
		if (signVerified) {
			params.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
		} else {
			params.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);
		}
		String orderId = params.get("out_trade_no");
		String result = params.get("result");
		String respCode = params.get("trade_status");
		String location = "http://localhost:8081//ACPSample_B2C/frontRcvResponse/" + orderId + "/" + result + "/"
				+ respCode;
		try {
			response.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ——请在这里编写您的程序（以上代码仅作参考）——
	}

}
