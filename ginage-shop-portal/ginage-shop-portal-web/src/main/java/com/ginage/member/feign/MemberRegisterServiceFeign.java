
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.service.MemberRegisterService;

/**
*@date:2020年4月11日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService{

}
