
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.hystrix.MemberLogoutServiceHystrix;
import com.ginage.member.service.MemberLogoutService;

/**
*@date:2020年4月16日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member" ,fallback=MemberLogoutServiceHystrix.class)
public interface MemberLogoutServiceFeign extends MemberLogoutService{

}
