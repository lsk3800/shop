
package com.ginage.spike.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
*@date:2020年6月13日
*@description:
*@Copyright: ginage.com
*
*/
@Configuration
public class Config {

	
	
	public static final String QUEUE = "spike_queue";
	public static final String EXCHANGE = "spike_exchange";

	@Bean
	public Queue createQueue() {
		return new Queue(QUEUE);
	}

	@Bean
	public DirectExchange createDirectEXchange() {
		return new DirectExchange(EXCHANGE);
	}
	@Bean
	public Binding bindingExchangeWidthQueue() {
		return BindingBuilder.bind(createQueue()).to(createDirectEXchange()).with("spike_routingKey");
	}
}
