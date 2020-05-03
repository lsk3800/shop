package com.xxl.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * @author xuxueli 2018-03-22 23:41:47
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableApolloConfig
@EnableFeignClients
public class XxlSsoServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(XxlSsoServerApplication.class, args);
	}

}