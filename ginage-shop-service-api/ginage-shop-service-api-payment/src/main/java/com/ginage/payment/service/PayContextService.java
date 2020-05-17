
package com.ginage.payment.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;

/**
*@date:2020年5月16日
*@description:
*@Copyright: ginage.com
*
*/
public interface PayContextService {

	/**
	 * 支付接口
	 * @param channelId
	 * @param payToken
	 * @return
	 */
	@GetMapping("/toPayHtml")
	public BaseResponse<JSONObject> toPayHtml(@RequestParam("channelId") String channelId,@RequestParam("payToken") String payToken);
}
