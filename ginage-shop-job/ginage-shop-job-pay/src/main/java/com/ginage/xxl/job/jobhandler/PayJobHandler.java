
package com.ginage.xxl.job.jobhandler;

import org.springframework.stereotype.Component;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
*@date:2020年5月22日
*@description:
*@Copyright: ginage.com
*
*/
@JobHandler(value = "payJobHandler")
@Component
public class PayJobHandler extends IJobHandler{

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		System.out.println("payJobHandler正在执行");
		return null;
	}

}
