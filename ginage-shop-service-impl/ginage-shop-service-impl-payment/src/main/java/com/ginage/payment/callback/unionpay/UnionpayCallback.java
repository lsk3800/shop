
package com.ginage.payment.callback.unionpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginage.payment.callback.AbstractPayCallbackTemplate;
import com.ginage.payment.constants.PayConstant;
import com.ginage.payment.service.mapper.PaymentTransactionMapper;
import com.ginage.payment.service.mapper.entity.PaymentTransactionEntity;
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
public class UnionpayCallback extends AbstractPayCallbackTemplate {
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	/**
	 * 业务逻辑实现
	 */
	@Override
	protected String asyncService(Map<String, String> verifySignature) {
		String orderId = verifySignature.get("orderId");
		if (StringUtils.isEmpty(orderId)) {
			return failResult();
		}
		// 通过ORDERID查询支付订单信息
		PaymentTransactionEntity paymentTranscation = paymentTransactionMapper.getPaymentTranscationByOrderId(orderId);
		Integer paymentStatus = paymentTranscation.getPaymentStatus();
		// 如果状态为1的话代表该订单已经支付过了,直接返回成功
		if (paymentStatus.equals(PayConstant.RESULT_PAYCODE_200)) {
			return seccessResult();
		}
		// 判断银联返回码,为00或A6为成功,
		String respCode = verifySignature.get("respCode");
		if (!(respCode.contentEquals("00") || respCode.contentEquals("A6"))) {
			return failResult();
		}
		//更新订单支付状态
		int effect = paymentTransactionMapper.updatePaymentStatus(1, orderId);
		if (effect < 1) {
			return failResult();
		}
		return seccessResult();
	}

	/**
	 * 验证失败
	 * 
	 */
	@Override
	protected String failResult() {
		return PayConstant.YINLIAN_RESULT_FAIL;
	}

	/**
	 * 
	 * 验证参数
	 */
	@Override
	protected Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub

		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);
		LogUtil.printRequestLog(reqParam);

		// 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(reqParam, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			// 验签失败，需解决验签问题
			reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);

		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			// 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

			// String orderId = reqParam.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
			// String respCode = reqParam.get("respCode");
			// 判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
			reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
		}

		return reqParam;
	}

	/**
	 * 
	 * 验证成功
	 */
	@Override
	protected String seccessResult() {
		// TODO Auto-generated method stub
		return PayConstant.YINLIAN_RESULT_SUCCESS;
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
