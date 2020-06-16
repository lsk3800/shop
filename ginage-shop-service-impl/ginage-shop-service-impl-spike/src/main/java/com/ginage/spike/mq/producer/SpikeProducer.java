
package com.ginage.spike.mq.producer;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ginage.spike.mq.config.Config;

import lombok.extern.slf4j.Slf4j;

/**
 * @date:2020年6月13日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Slf4j
@Component
public class SpikeProducer implements RabbitTemplate.ConfirmCallback {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(JSONObject jb) {
		String jsonString = jb.toJSONString();
		String msgId=UUID.randomUUID().toString().replace("-", "");
		Message message = MessageBuilder.withBody(jsonString.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(msgId)
				.build();
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(this);
		CorrelationData correlationData = new CorrelationData(jsonString);

		rabbitTemplate.convertAndSend(Config.EXCHANGE, "spike_routingKey",message,correlationData);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {

		if(ack) {
			log.info("消息添加到队列成功");
		}else {
			
			log.info("消息添加到队列失败");
		}
	}
}
