package com.ginage.weixin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableApolloConfig
@EnableFeignClients
@ComponentScan(basePackages = {"com.ginage.common.core","com.ginage.weixin"})
public class AppWeixin {
	public static void main(String[] args) {
		SpringApplication.run(AppWeixin.class, args);
	}
}
