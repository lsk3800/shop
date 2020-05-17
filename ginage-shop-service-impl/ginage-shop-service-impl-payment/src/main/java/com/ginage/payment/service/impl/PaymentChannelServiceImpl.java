
package com.ginage.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.payment.service.PaymentChannelService;
import com.ginage.payment.service.mapper.PaymentChannelMapper;
import com.ginage.payment.service.mapper.entity.PaymentChannelEntity;

/**
 * @date:2020年5月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
@RestController
public class PaymentChannelServiceImpl implements PaymentChannelService {
	@Autowired
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public List<PaymentChannelDTO> getPaymentChannel() {
		// TODO Auto-generated method stub
		List<PaymentChannelEntity> paymentChannelList =paymentChannelMapper.getAllPaymentChannel();
		List<PaymentChannelDTO> paymentChannels=new ArrayList<PaymentChannelDTO>();
		for(PaymentChannelEntity paymentChannel:paymentChannelList) {
			PaymentChannelDTO paymentChannelDTO=new PaymentChannelDTO();
			BeanUtils.copyProperties(paymentChannel, paymentChannelDTO);
			paymentChannels.add(paymentChannelDTO);
		}
		return paymentChannels;
	}

}
