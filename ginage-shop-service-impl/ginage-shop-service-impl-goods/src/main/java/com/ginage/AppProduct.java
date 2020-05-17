
package com.ginage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
*@date:2020年5月5日
*@description:
*@Copyright: ginage.com
*
*/
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.ginage.goods.es")
@EnableEurekaClient
public class AppProduct {

	public static void main(String[] args) {
		SpringApplication.run(AppProduct.class, args);
	}
}
