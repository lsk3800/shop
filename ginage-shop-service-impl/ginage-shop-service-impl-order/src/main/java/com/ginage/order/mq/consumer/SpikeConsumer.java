
package com.ginage.order.mq.consumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.core.twitter.SnowflakeIdUtils;
import com.ginage.order.mapper.OrderMapper;
import com.ginage.order.mapper.entity.OrderEntity;
import com.rabbitmq.client.Channel;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

/**
 * @date:2020年6月14日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
@Slf4j
@RabbitListener(queues = "spike_queue")
public class SpikeConsumer {
	@Autowired
	private OrderMapper orderMapper;

	@RabbitHandler(isDefault = true)
	public void process(Message message, Channel channel) throws IOException {
		try {
			String msg = new String(message.getBody(), "UTF-8");
			JSONObject jb = JSONObject.parseObject(msg);
			String phoneNum = jb.getString("phoneNum");
			String productId = jb.getString("productId");
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setProductId(productId);
			orderEntity.setPhoneNum(phoneNum);
			orderEntity.setOrderId(SnowflakeIdUtils.nextId());
			orderEntity.setCreatedTime(new Date());
			orderMapper.createSpikeOrder(orderEntity);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("创建订单:" + orderEntity.getOrderId());

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}

	}
}
