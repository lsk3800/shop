
package com.ginage.mq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.config.RabbitMQConfig;

/**
 * @date:2020年6月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
public class IntegralProducer implements RabbitTemplate.ConfirmCallback {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(JSONObject jb) {

		String jsonString = jb.toJSONString();
		String paymentId = jb.getString("paymentId");
		Message message = MessageBuilder.withBody(jsonString.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(paymentId)
				.build();
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(this);
		CorrelationData correlationData = new CorrelationData(jsonString);
		rabbitTemplate.convertAndSend(RabbitMQConfig.JIFEN_EXCHANGE, "integral_routingKey", message, correlationData);

		//rabbitTemplate.convertAndSend(RabbitMQConfig.JIFEN_EXCHANGE, "integral_routingKey", jb);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String id = correlationData.getId();
		if (ack) {
			System.out.println("编号为:" + id + "的消息投递成功!");
			return;
		}

		System.out.println("编号为:" + id + "的消息投递失败!");
	}

}
