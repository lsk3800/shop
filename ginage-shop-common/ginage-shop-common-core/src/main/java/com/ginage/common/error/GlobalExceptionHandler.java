
package com.ginage.common.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ginage.common.base.BaseApiService;
import com.ginage.common.base.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @date:2020年4月5日
 * @description:
 * @Copyright: ginage.com
 *
 */
//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseApiService<JSONObject> {

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public BaseResponse<JSONObject> exceptionHandler(Exception e) {
		log.info("全局异常:,error:{}",e);
		return setResultError("系统错误");

	}

}
