
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.hystrix.QQAuthoriServiceHystrix;
import com.ginage.service.QQAuthoriService;

/**
*@date:2020年4月23日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member",fallback=QQAuthoriServiceHystrix.class)
public interface QQAuthoriServiceFeign extends QQAuthoriService{

}
