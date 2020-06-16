package com.ginage.payment.constants;

/**
 * 支付相关常量数据
 * 
 * 
 */
public interface PayConstant {

	String RESULT_NAME = "result";
	String RESULT_PAYCODE_201 = "201";
	String RESULT_PAYCODE_200 = "200";
	/**
	 * 已经支付成功状态
	 */
	Integer PAY_STATUS_SUCCESS = 1;
	/**
	 * 返回银联通知成功
	 */
	String YINLIAN_RESULT_SUCCESS = "ok";
	/**
	 * 返回银联失败通知
	 */
	String YINLIAN_RESULT_FAIL = "fail";
	/**
	 * 返回支付宝通知成功
	 */
	String ALI_RESULT_SUCCESS = "success";
	/**
	 * 返回支付宝失败通知
	 */
	String ALI_RESULT_FAIL = "fail";
}
