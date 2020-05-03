
package com.ginage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @date:2020年3月28日
 * @description:
 * @Copyright: ginage.com
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableSwagger2Doc
@EnableApolloConfig
public class AppZuul {
	@ApolloConfig
	private static Config config;

	public static void main(String[] args) {
		SpringApplication.run(AppZuul.class, args);
		config.addChangeListener(new ConfigChangeListener() {
		    @Override
		    public void onChange(ConfigChangeEvent changeEvent) {
		        System.out.println("Changes for namespace " + changeEvent.getNamespace());
		        for (String key : changeEvent.changedKeys()) {
		            ConfigChange change = changeEvent.getChange(key);
		            System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
		        }
		    }
		});
	}

	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {

		/**
		 * 每次刷新swagger页面都会调用一次get方法
		 */
		@Override
		public List<SwaggerResource> get() {
			// TODO Auto-generated method stub
			/*
			 * List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
			 * resources.add(swaggerResource("api-member", "/app-member/v2/api-docs",
			 * "1.0")); resources.add(swaggerResource("api-weixin",
			 * "/app-weixin/v2/api-docs", "1.0"));
			 */
			return resources();
		}

		/**
		 * 从apollo获取想要的属性名的值
		 * @return
		 */
		private String swaggerDocument() {
			String property = config.getProperty("zuul.swagger.resource", "");
			return property;
		}
		/**
		 * 把获取到的JSON数据转成SwaggerResource数组
		 * @return
		 */
		private List<SwaggerResource> resources() {
			List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
			String swaggerDocJson = swaggerDocument();
			JSONArray jsonArray = JSONArray.parseArray(swaggerDocJson);
			for (Object object : jsonArray) {
				JSONObject jb = (JSONObject) object;
				String name = jb.getString("name");
				String url = jb.getString("url");
				String version = jb.getString("version");
				resources.add(swaggerResource(name, url, version));
			}
			return resources;
		}
		/**
		 * 生成SwaggerResource对象
		 * @param name
		 * @param url
		 * @param version
		 * @return
		 */
		private SwaggerResource swaggerResource(String name, String url, String version) {
			// TODO Auto-generated method stub
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setUrl(url);
			swaggerResource.setSwaggerVersion(version);

			return swaggerResource;
		}

	}

}
