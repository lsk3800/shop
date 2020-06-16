
package com.ginage.payment.callback;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.mq.producer.IntegralProducer;
import com.ginage.payment.constants.PayConstant;
import com.ginage.payment.service.mapper.PaymentTransactionMapper;
import com.ginage.payment.service.mapper.entity.PaymentTransactionLogEntity;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
public abstract class AbstractPayCallbackTemplate {
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;
	public String asyncCallback(HttpServletRequest request, HttpServletResponse response) {
		// 获取参数并验证
		Map<String, String> verifySignature = verifySignature(request, response);
		String result = verifySignature.get(PayConstant.RESULT_NAME);
		if (result.contentEquals(PayConstant.RESULT_PAYCODE_201)) {
			return failResult();
		}
		/**
		 * 将支付参数信息插入数据库
		 */
		payLog(verifySignature);
		return asyncService(verifySignature);

	}

	/**
	 * 业务逻辑
	 * 
	 * @return
	 */
	protected abstract String asyncService(Map<String, String> verifySignature);

	/**
	 * 支付信息插入数据库
	 * 
	 * @param verifySignature
	 */
	@Async
	protected void payLog(Map<String, String> verifySignature) {
		
		PaymentTransactionLogEntity paymentTransactionLogEntity = new PaymentTransactionLogEntity();
		paymentTransactionLogEntity.setAsyncLog(verifySignature.get("respCode"));
		paymentTransactionLogEntity.setChannelId(verifySignature.get("channelId"));
		paymentTransactionLogEntity.setOrderId(verifySignature.get("orderId"));
		paymentTransactionLogEntity.setCreatedTime(new Date());
		paymentTransactionLogEntity.setPayId(verifySignature.get("payId"));
		paymentTransactionMapper.insterPayLog(paymentTransactionLogEntity);
		

	}

	/**
	 * 验证失败
	 * 
	 * @return
	 */
	protected abstract String failResult();

	/**
	 * 
	 * 验证成功
	 * 
	 * @return
	 */
	protected abstract String seccessResult();

	/**
	 * 验证参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected abstract Map<String, String> verifySignature(HttpServletRequest request, HttpServletResponse response);
}
