
package com.ginage.payment.callback;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginage.payment.constants.PayConstant;

/**
 * @date:2020年5月17日
 * @description:
 * @Copyright: ginage.com
 *
 */
public abstract class AbstractPayCallbackTemplate {

	public String asyncCallback(HttpServletRequest request, HttpServletResponse response) {
		// 获取参数并验证
		Map<String, String> verifySignature = verifySignature(request, response);
		String result = verifySignature.get(PayConstant.RESULT_NAME);
		if (result.contentEquals(PayConstant.RESULT_PAYCODE_201)) {
			return failResult();
		}
		/**
		 * 将支付参数信息插入数据库
		 */
		payLog(verifySignature);
		return asyncService(verifySignature);

	}

	/**
	 * 业务逻辑
	 * 
	 * @return
	 */
	protected abstract String asyncService(Map<String, String> verifySignature);

	/**
	 * 支付信息插入数据库
	 * 
	 * @param verifySignature
	 */
	protected void payLog(Map<String, String> verifySignature) {
		
	}

	/**
	 * 验证失败
	 * 
	 * @return
	 */
	protected abstract String failResult();
	/**
	 * 
	 * 验证成功
	 * @return
	 */
	protected abstract String seccessResult();

	/**
	 * 验证参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected abstract Map<String, String> verifySignature(HttpServletRequest request, HttpServletResponse response);
}
