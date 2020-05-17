
package com.ginage.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.member.service.SSOLoginService;

/**
*@date:2020年5月1日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member")
public interface SSOLoginServiceFeign extends SSOLoginService{

}
