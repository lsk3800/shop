
package com.ginage.spike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.core.RedisUtils;
import com.ginage.common.core.TokenUtils;
import com.ginage.spike.mapper.SpikeMapper;
import com.ginage.spike.mq.producer.SpikeProducer;
import com.ginage.spike.service.SpikeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

/**
*@date:2020年6月13日
*@description:
*@Copyright: ginage.com
*
*/
@RestController
@Slf4j
public class SpikeServiceImpl extends BaseApiService<JSONObject> implements SpikeService{
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private SpikeMapper spikeMapper;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private SpikeProducer spikeProducer;
	
	
	
	@HystrixCommand(fallbackMethod="spikeFallbackMethod")
	@Override
	public BaseResponse<JSONObject> spike(String phoneNum, Long productId) {
		log.info(Thread.currentThread().getName());
		//先从redis里取得token,只有拿到token才能进入下一步
		String spikeToken = redisUtils.getListPop("spikeToken:"+productId);
		if(StringUtils.isEmpty(spikeToken)) {
			return setResultError("对不起,您手慢了一点,商品已经售磬!");
		}
		
		//拿到token的请求进入mq队列
		JSONObject jb=new JSONObject();
		jb.put("phoneNum", phoneNum);
		jb.put("productId", productId);
		spikeProducer.send(jb);
		int effect = spikeMapper.updateSpikeInventory(productId);
		if(effect<1) {
			return setResultError("没有此商品");
		}
		return setResultSuccess("正在排队,请稍候...");
	}

	@Override
	public BaseResponse<JSONObject> createSpikeToken(String productId,Long quantity) {
		if(StringUtils.isEmpty(productId)) {
			return setResultError("商品ID不能为空");
		}
		if(StringUtils.isEmpty(quantity)) {
			return setResultError("商品数量不能为空");
		}
		addSpikeToken(productId, quantity);
		return setResultSuccess("正在创建Token.....");
	}
	
	@Async
	private void addSpikeToken(String productId,Long quantity) {
		
		List<String> tokenList = tokenUtils.createTokenList("spike", quantity);
		redisUtils.setList("spikeToken:"+productId, tokenList);
	}
	
	
	
	public BaseResponse<JSONObject> spikeFallbackMethod(String phoneNum, Long productId){
		
		return setResultError("服务器忙,请稍后再试!");
		
	}
	//@HystrixCommand(fallbackMethod="testFallbackMethod")
	@Override
	public BaseResponse<JSONObject> test() {
		// TODO Auto-generated method stub
		//log.info(Thread.currentThread().getName());
		return setResultSuccess("访问成功"+Thread.currentThread().getName());
	}
	
	
	public BaseResponse<JSONObject> testFallbackMethod(){
		return setResultSuccess("忙");
	}

}
