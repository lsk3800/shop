
package com.ginage;
/**
*@date:2020年4月26日
*@description:
*@Copyright: ginage.com
*
*
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication
@EnableEurekaClient
@EnableApolloConfig
@EnableFeignClients
public class AppMember {

	public static void main(String[] args) {
		SpringApplication.run(AppMember.class, args);
	}
}
