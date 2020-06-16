
package com.ginage.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;
import com.ginage.order.api.service.OrderService;
import com.ginage.order.mapper.OrderMapper;
import com.ginage.order.mapper.entity.OrderEntity;

/**
*@date:2020年6月15日
*@description:
*@Copyright: ginage.com
*
*/
@RestController
public class OrderServiceImpl extends BaseApiService<JSONObject> implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public BaseResponse<JSONObject> querySpikeResult(String phoneNum, Long productId) {
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(phoneNum)) {
			return setResultError("手机号不能为空");
		}
		if(StringUtils.isEmpty(productId)) {
			return setResultError("商品ID不能为空");
		}
		OrderEntity orderEntity=orderMapper.querySpikeResult(phoneNum,productId);
		if(orderEntity!=null) {
			return setResultSuccess("恭喜您,秒杀成功!");
		}
		return setResultError("对不起,您慢了一步,商品都被抢完了!");
	}

}
