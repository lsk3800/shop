
package com.ginage.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @date:2020年6月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Configuration
public class RabbitMQConfig {
	public static final String JIFEN_QUEUE = "integral_queue";
	public static final String JIFEN_EXCHANGE = "integral_exchange";

	@Bean
	public Queue integralQueue() {
		return new Queue(JIFEN_QUEUE);
	}

	@Bean
	public DirectExchange integralDirectEXchange() {
		return new DirectExchange(JIFEN_EXCHANGE);
	}
	@Bean
	public Binding bindingExchangeIntegralQueue() {
		return BindingBuilder.bind(integralQueue()).to(integralDirectEXchange()).with("integral_routingKey");
	}
}
