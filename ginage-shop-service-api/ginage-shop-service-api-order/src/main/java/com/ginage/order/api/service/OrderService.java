
package com.ginage.order.api.service;
/**
*@date:2020年6月14日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

public interface OrderService {

	@GetMapping("/querySpikeResult")
	public BaseResponse<JSONObject> querySpikeResult(@RequestParam("phoneNum") String phoneNum,@RequestParam("productId") Long productId);
	
	
}
