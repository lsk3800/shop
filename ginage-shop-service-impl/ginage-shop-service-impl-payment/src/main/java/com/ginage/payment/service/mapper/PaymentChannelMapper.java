
package com.ginage.payment.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ginage.payment.service.mapper.entity.PaymentChannelEntity;

/**
*@date:2020年5月16日
*@description:
*@Copyright: ginage.com
*
*/
@Mapper
public interface PaymentChannelMapper {

	List<PaymentChannelEntity> getAllPaymentChannel();

	PaymentChannelEntity getPaymentChannelById(String channelId);

}
