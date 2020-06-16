
package com.ginage.payment.service.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ginage.payment.service.mapper.entity.PaymentTransactionEntity;
import com.ginage.payment.service.mapper.entity.PaymentTransactionLogEntity;


/**
*@date:2020年5月15日
*@description:
*@Copyright: ginage.com
*
*/
@Mapper
public interface PaymentTransactionMapper {

	public int insertPaymentTransaction(PaymentTransactionEntity paymentTransactionEntity);

	public PaymentTransactionEntity getPaymentTranscationById(Long id);

	public PaymentTransactionEntity getPaymentTranscationByOrderId(String orderId);

	public int updatePaymentStatus(@Param("status") int status,@Param("orderId") String orderId);

	public int insterPayLog(PaymentTransactionLogEntity paymentTransactionLogEntity);
}
