
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.hystrix.WeixinServiceHystrix;
import com.ginage.weixin.service.WeixinService;

/**
*@date:2020年3月27日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-weixin",fallback=WeixinServiceHystrix.class)
public interface WeixinServiceFeign extends WeixinService{

}
