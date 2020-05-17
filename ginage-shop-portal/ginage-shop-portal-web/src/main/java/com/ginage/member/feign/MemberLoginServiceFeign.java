
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.hystrix.MemberLoginServiceHystrix;
import com.ginage.member.service.MemberLoginService;

/**
*@date:2020年4月9日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member",fallback=MemberLoginServiceHystrix.class)
public interface MemberLoginServiceFeign extends MemberLoginService{

}
