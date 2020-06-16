
package com.ginage.spike.service;
/**
 * 秒杀接口
*@date:2020年6月13日
*@description:
*@Copyright: ginage.com
*
*/

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

public interface SpikeService {

	@GetMapping("/spike")
	public BaseResponse<JSONObject> spike(String phoneNum,Long productId);
	@GetMapping("/createSpikeToken")
	public BaseResponse<JSONObject> createSpikeToken(@RequestParam("productId") String productId,@RequestParam("quantity") Long quantity);
	@GetMapping("/test")
	public BaseResponse<JSONObject> test();
}
