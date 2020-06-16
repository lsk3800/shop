
package com.ginage.mq.consumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ginage.integral.mapper.IntegralMapper;
import com.ginage.integral.mapper.entity.IntegralEntity;
import com.rabbitmq.client.Channel;

/**
 * @date:2020年6月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
@RabbitListener(queues = "integral_queue")
public class IntegralConsumer {
	@Autowired
	private IntegralMapper ntegralMapper;
	@RabbitHandler(isDefault = true)
	public void process(Message message,Channel channel) throws IOException {

		try {
			String msg = new String(message.getBody(), "UTF-8");
			JSONObject jb = JSONObject.parseObject(msg);
			String paymentId = jb.getString("paymentId");
			if (StringUtils.isEmpty(paymentId)) {
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				return;
			}
			int userId=jb.getIntValue("userId");
			int integral=jb.getIntValue("integral");
			IntegralEntity integralEntity=new IntegralEntity();
			integralEntity.setPaymentId(paymentId);
			integralEntity.setUserId(userId);
			integralEntity.setAvailability(1);
			integralEntity.setIntegral(integral);
			integralEntity.setCreatedTime(new Date());
			integralEntity.setUpdatedTime(new Date());
			ntegralMapper.addIntegral(integralEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			return;
		} catch (IOException e) {
			e.printStackTrace();
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			return;
		}

	}
}
