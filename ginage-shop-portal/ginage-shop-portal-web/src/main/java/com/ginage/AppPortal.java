
package com.ginage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
*@date:2020年4月7日
*@description:
*@Copyright: ginage.com
*
*/

@SpringBootApplication
@EnableEurekaClient
@EnableApolloConfig
@EnableFeignClients
public class AppPortal {

	public static void main(String[] args) {
		SpringApplication.run(AppPortal.class, args);
	}
}
