
package com.ginage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @date:2020年5月15日
 * @description:
 * @Copyright: ginage.com
 *
 */
@SpringBootApplication
@EnableAsync
public class AppPayment {
	public static void main(String[] args) {
		SpringApplication.run(AppPayment.class, args);
	}
}
