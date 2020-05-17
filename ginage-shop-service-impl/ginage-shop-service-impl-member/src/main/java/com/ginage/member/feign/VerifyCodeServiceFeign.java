
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.hystrix.VerifyCodeServiceHystrix;
import com.ginage.weixin.service.VerifyCodeService;

/**
*@date:2020年4月4日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-weixin",fallback=VerifyCodeServiceHystrix.class)
public interface VerifyCodeServiceFeign extends VerifyCodeService {

}
