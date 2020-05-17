
package com.ginage.weixin.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.service.MemberService;
import com.ginage.weixin.hystrix.MemberServiceHystrix;

/**
*@date:2020年4月4日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member",fallback=MemberServiceHystrix.class)
public interface MemberServiceFeign extends MemberService{

}
