
package com.ginage.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * @date:2020年5月16日
 * @description:
 * @Copyright: ginage.com
 *
 */
@Component
public class UnionpayInit implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		SDKConfig.getConfig().loadPropertiesFromSrc();
	}

}
