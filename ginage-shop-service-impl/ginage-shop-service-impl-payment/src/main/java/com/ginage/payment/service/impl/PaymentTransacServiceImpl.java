
package com.ginage.payment.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.core.RedisUtils;
import com.ginage.common.core.TokenUtils;
import com.ginage.common.core.twitter.SnowflakeIdUtils;
import com.ginage.dto.input.CreatePayTokenDTO;
import com.ginage.payment.service.PaymentTransacService;
import com.ginage.payment.service.mapper.PaymentTransactionMapper;
import com.ginage.payment.service.mapper.entity.PaymentTransactionEntity;

/**
*@date:2020年5月14日
*@description:
*@Copyright: ginage.com
*
*/
@RestController
public class PaymentTransacServiceImpl extends BaseApiService<JSONObject> implements PaymentTransacService {
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;
	@Autowired
	private RedisUtils redisUtils;
	@Override
	public BaseResponse<JSONObject> createPaymentToken(CreatePayTokenDTO createPayTokenDTO) {
		// TODO Auto-generated method stub
		String orderID=createPayTokenDTO.getOrderId();
		if(StringUtils.isEmpty(orderID)) {
			return setResultError("订单号不能为空");
		}
		String orderName=createPayTokenDTO.getOrderName();
		if(StringUtils.isEmpty(orderName)) {
			return setResultError("订单名称不能为空");
		}
		Long payAmount=createPayTokenDTO.getPayAmount();
		if(payAmount==null) {
			return setResultError("订单金额不能为空");
		}
		Long userID=createPayTokenDTO.getUserId();
		if(userID==null) {
			return setResultError("用户ID不能为空");
		}
		
		PaymentTransactionEntity paymentTransactionEntity=new PaymentTransactionEntity();
		paymentTransactionEntity.setOrderId(orderID);
		paymentTransactionEntity.setOrderName(orderName);
		paymentTransactionEntity.setPayAmount(payAmount);
		paymentTransactionEntity.setUserId(userID);
		paymentTransactionEntity.setPaymentId(SnowflakeIdUtils.nextId());
		paymentTransactionEntity.setCreatedTime(new Date());
		int result = paymentTransactionMapper.insertPaymentTransaction(paymentTransactionEntity);
		if(result<1) {
			return setResultError("插入PaymentTransaction错误");
		}
		Integer id = paymentTransactionEntity.getId();
		if(id==null) {
			return setResultError("获取插入paymentTransaction返回的ID失败");
		}
		String keyPrefix="pay_";
		String token=keyPrefix+TokenUtils.createToken();
		redisUtils.set(token, id+"", 1800);
		JSONObject dataResult=new JSONObject();
		dataResult.put("token",token);
		return setResultSuccess(dataResult);
	}

}
