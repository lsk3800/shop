
package com.ginage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @date:2020年6月13日
 * @description:
 * @Copyright: ginage.com
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class AppSpike {
	public static void main(String[] args) {
		SpringApplication.run(AppSpike.class, args);
	}
}
