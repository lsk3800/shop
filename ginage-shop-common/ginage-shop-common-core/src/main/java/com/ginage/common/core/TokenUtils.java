
package com.ginage.common.core;

import java.util.UUID;

import org.springframework.stereotype.Component;

/**
*@date:2020年4月5日
*@description:
*@Copyright: ginage.com
*
*/
@Component
public class TokenUtils {

	public String createToken() {
		String token=UUID.randomUUID().toString().replace("-", "");
		return token;
	}
	
}
