
package com.ginage.payment.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.protocol.HttpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ginage.payment.constants.PayConstant;
import com.unionpay.acp.demo.UnionpayBase;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConstants;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Controller
public class UnionpayResponseController {

	@PostMapping("/ACPSample_B2C/frontRcvResponse")
	public void frontRcvResponse(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

		String encoding = req.getParameter(SDKConstants.param_encoding);
		LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
		String pageResult = "";
		if (UnionpayBase.encoding.equalsIgnoreCase(encoding)) {
			pageResult = "/utf8_result.jsp";
		} else {
			pageResult = "/gbk_result.jsp";
		}
		Map<String, String> respParam = getAllRequestParam(req);

		// 打印请求报文
		LogUtil.printRequestLog(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key + "(" + key + ")</td><td>" + value
						+ "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!AcpService.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			LogUtil.writeLog("验证签名结果[失败].");
			respParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			LogUtil.writeLog("验证签名结果[成功].");
			respParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
			System.out.println(valideData.get("orderId")); // 其他字段也可用类似方式获取

			LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
			// 判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。

		}
		String orderId = respParam.get("orderId");
		String result = respParam.get("result");
		String respCode = respParam.get("respCode");
		String location = "http://localhost:8081//ACPSample_B2C/frontRcvResponse/" + orderId + "/" + result + "/"
				+ respCode;
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取请求参数中所有的信息 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
	 * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}

	/**
	 * 获取请求参数中所有的信息。 非struts可以改用此方法获取，好处是可以过滤掉request.getParameter方法过滤不掉的url中的参数。
	 * struts可能对某些content-type会提前读取参数导致从inputstream读不到信息，所以可能用不了这个方法。理论应该可以调整struts配置使不影响，但请自己去研究。
	 * 调用本方法之前不能调用req.getParameter("key");这种方法，否则会导致request取不到输入流。
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParamStream(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		try {
			String notifyStr = new String(IOUtils.toByteArray(request.getInputStream()), UnionpayBase.encoding);
			LogUtil.writeLog("收到通知报文：" + notifyStr);
			String[] kvs = notifyStr.split("&");
			for (String kv : kvs) {
				String[] tmp = kv.split("=");
				if (tmp.length >= 2) {
					String key = tmp[0];
					String value = URLDecoder.decode(tmp[1], UnionpayBase.encoding);
					res.put(key, value);
				}
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.writeLog("getAllRequestParamStream.UnsupportedEncodingException error: " + e.getClass() + ":"
					+ e.getMessage());
		} catch (IOException e) {
			LogUtil.writeLog("getAllRequestParamStream.IOException error: " + e.getClass() + ":" + e.getMessage());
		}
		return res;
	}

}
