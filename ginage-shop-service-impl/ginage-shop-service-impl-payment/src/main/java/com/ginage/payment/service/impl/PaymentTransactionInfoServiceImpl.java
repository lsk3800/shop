
package com.ginage.payment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.core.RedisUtils;
import com.ginage.dto.output.PaymentTransacDTO;
import com.ginage.payment.service.PaymentTransactionInfoService;
import com.ginage.payment.service.mapper.PaymentTransactionMapper;
import com.ginage.payment.service.mapper.entity.PaymentTransactionEntity;

/**
 * @date:2020年5月15日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class PaymentTransactionInfoServiceImpl extends BaseApiService<PaymentTransacDTO>
		implements PaymentTransactionInfoService {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	@Override
	public BaseResponse<PaymentTransacDTO> getPaymentTransationByToken(String token) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(token)) {
			return setResultError("TOKEN不能为空");
		}
		String paymentTransactionId =redisUtils.get(token);
		if(StringUtils.isEmpty(paymentTransactionId)) {
			return setResultError("订单失效或无效订单");
		}
		Long id=Long.parseLong(paymentTransactionId);
		PaymentTransactionEntity paymentTranscation = paymentTransactionMapper.getPaymentTranscationById(id);
		if(paymentTranscation==null) {
			return setResultError("无效订单");
		}
		PaymentTransacDTO paymentTransacDTO=new PaymentTransacDTO();
		BeanUtils.copyProperties(paymentTranscation, paymentTransacDTO);
		return setResultSuccess(paymentTransacDTO);
	}

}
