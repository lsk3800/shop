
package com.ginage.payment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.dto.output.PaymentTransacDTO;
import com.ginage.payment.service.PayContextService;
import com.ginage.payment.service.PaymentTransactionInfoService;
import com.ginage.payment.service.mapper.PaymentChannelMapper;
import com.ginage.payment.service.mapper.entity.PaymentChannelEntity;
import com.ginage.payment.strategy.PayStrategy;
import com.ginage.payment.strategy.StrategyFactory;

/**
 * @date:2020年5月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class PayContextServiceImpl extends BaseApiService<JSONObject> implements PayContextService {
	@Autowired
	private PaymentTransactionInfoService paymentTransactionInfoService;
	@Autowired
	private PaymentChannelMapper paymentChannelMapper;
	
	
	/**
	 * 获取支付html
	 * 使用paytoken获取支付信息,使用channelId获取支付渠道信息
	 * 支付渠道中存有PayStrategy子类地址,通过java反射机制生成子类实例,
	 * 通过多态调用子类具体方法,实现不同支付渠道支付
	 */
	@Override
	
	public BaseResponse<JSONObject> toPayHtml(String channelId, String payToken) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(channelId)) {
			return setResultError("支付渠道不能为空");
		}
		if (StringUtils.isEmpty(payToken)) {
			return setResultError("支付Token不能为空");
		}
		//通过token查询支付信息
		BaseResponse<PaymentTransacDTO> paymentTransationInfo = paymentTransactionInfoService
				.getPaymentTransationByToken(payToken);
		if (!paymentTransationInfo.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setResultError("获取支付信息错误");
		}
		//通过支付ID查询支付渠道信息
		PaymentChannelEntity paymentChannelEntity = paymentChannelMapper.getPaymentChannelById(channelId);
		if (paymentChannelEntity == null) {
			return setResultError("获取支付渠道错误");
		}
		String classAddress = paymentChannelEntity.getClassAddress();
		//通过支付渠道中的支付子类地址获取子类实例
		PayStrategy payStrategy = StrategyFactory.getPayStrategy(classAddress);
		if (payStrategy == null) {
			return setResultError("获取支付策略错误");
		}
		PaymentChannelDTO paymentChannelDTO = new PaymentChannelDTO();

		BeanUtils.copyProperties(paymentChannelEntity, paymentChannelDTO);
		//调用子类的方法
		String payHtml = payStrategy.toPayHtml(paymentChannelDTO, paymentTransationInfo.getData());
		if(StringUtils.isEmpty(payHtml)) {
			return setResultError("payHtml错误");
		}
		JSONObject data = new JSONObject();
		data.put("payHtml", payHtml);
		return setResultSuccess(data);
	}

}
