
package com.ginage.payment.callback.factory;


import com.ginage.common.core.SpringContextUtil;
import com.ginage.payment.callback.AbstractPayCallbackTemplate;

/**
 * @date:2020年5月18日
 * @description:
 * @Copyright: ginage.com
 *
 */
public class TemplateFactory {

	public static AbstractPayCallbackTemplate getPayCallbackTemplate(String beanName) {
		AbstractPayCallbackTemplate bean = (AbstractPayCallbackTemplate) SpringContextUtil.getBean(beanName);
		return bean;
	}

}
