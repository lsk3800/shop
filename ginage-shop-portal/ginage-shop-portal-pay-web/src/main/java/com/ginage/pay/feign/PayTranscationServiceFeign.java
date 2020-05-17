
package com.ginage.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.payment.service.PaymentTransactionInfoService;

/**
*@date:2020年5月17日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name = "app-payment")
public interface PayTranscationServiceFeign extends PaymentTransactionInfoService{

}
