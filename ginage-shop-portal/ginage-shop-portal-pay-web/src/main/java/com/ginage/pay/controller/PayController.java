
package com.ginage.pay.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseResponse;
import com.ginage.common.constants.Constants;
import com.ginage.common.core.BaseWebController;
import com.ginage.dto.output.PaymentChannelDTO;
import com.ginage.dto.output.PaymentTransacDTO;
import com.ginage.pay.feign.PayChannelServiceFeign;
import com.ginage.pay.feign.PayContextServiceFeign;
import com.ginage.pay.feign.PayTranscationServiceFeign;

import lombok.extern.slf4j.Slf4j;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
@Slf4j
public class PayController extends BaseWebController {
	private static final String PAYPAGE = "payPage.html";
	private static final String ERROR = "error500.html";
	private static final String SECCESS = "seccess.html";
	@Autowired
	private PayTranscationServiceFeign payTranscationServiceFeign;
	@Autowired
	private PayChannelServiceFeign payChannelServiceFeign;
	@Autowired
	private PayContextServiceFeign payContextServiceFeign;

	@RequestMapping("/paymentTransaction")
	public String paymentTransation(String token, Model model) {
		if (StringUtils.isEmpty(token)) {
			setErrorMsg(model, "token不能为空");
			PaymentTransacDTO data = new PaymentTransacDTO();
			model.addAttribute("data", data);
			return PAYPAGE;
		}

		BaseResponse<PaymentTransacDTO> paymentTransationResult = payTranscationServiceFeign
				.getPaymentTransationByToken(token);

		if (!paymentTransationResult.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			setErrorMsg(model, "没有此支付订单信息");
			return PAYPAGE;
		}
		PaymentTransacDTO data = paymentTransationResult.getData();
		model.addAttribute("data", data);

		List<PaymentChannelDTO> paymentChannelList = payChannelServiceFeign.getPaymentChannel();

		model.addAttribute("paymentChannelList", paymentChannelList);
		model.addAttribute("payToken", token);
		return PAYPAGE;

	}

	@GetMapping("/payHtml")
	public void payHtml(String channelId, String payToken, Model model,HttpServletResponse response) {
		response.setHeader("content-type", "text/html;charset=UTF-8");
		BaseResponse<JSONObject> payHtml = payContextServiceFeign.toPayHtml(channelId, payToken);
		if(!payHtml.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			setErrorMsg(model, payHtml.getMsg());
		}
		String html=payHtml.getData().getString("payHtml");
		try {
			response.getWriter().print(html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
