
package com.xxl.sso.server.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.ginage.service.SSOLoginService;

/**
*@date:2020年4月29日
*@description:
*@Copyright: ginage.com
*
*/
@FeignClient(name="app-member")
public interface SSOLoginServiceFeign extends SSOLoginService{

}
