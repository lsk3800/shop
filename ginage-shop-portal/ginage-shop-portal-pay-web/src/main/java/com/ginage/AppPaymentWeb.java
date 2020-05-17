
package com.ginage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableApolloConfig
@EnableFeignClients
public class AppPaymentWeb {

	public static void main(String[] args) {
		SpringApplication.run(AppPaymentWeb.class, args);
	}
}
