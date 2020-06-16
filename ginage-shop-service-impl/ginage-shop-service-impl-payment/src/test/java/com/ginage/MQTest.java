
package com.ginage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSONObject;
import com.ginage.mq.producer.IntegralProducer;

/**
 * @date:2020年6月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
@SpringBootTest
public class MQTest {
	@Autowired
	private IntegralProducer jifenProducer;

	@Test
	public void test() {
		JSONObject jb = new JSONObject();
		jb.put("paymentId", "123456879");
		jb.put("userId", 3);
		jb.put("integral", 100);
		//String s="test";
		jifenProducer.send(jb);
	}
}
