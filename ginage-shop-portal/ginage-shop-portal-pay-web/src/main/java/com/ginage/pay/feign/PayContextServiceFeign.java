
package com.ginage.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.payment.service.PayContextService;

/**
*@date:2020年5月17日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name = "app-payment")
public interface PayContextServiceFeign extends PayContextService{

}
