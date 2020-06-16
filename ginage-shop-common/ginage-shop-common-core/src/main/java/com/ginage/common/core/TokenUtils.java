
package com.ginage.common.core;

import java.util.ArrayList;
import java.util.List;
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

	public static String createToken() {
		String token=UUID.randomUUID().toString().replace("-", "");
		return token;
	}
	
	public List<String> createTokenList(String prefix,Long quantity){
		List<String> tokenList=new ArrayList<String>();
		for(int i=0;i<quantity;i++) {
			String token=UUID.randomUUID().toString().replace("-", "");
			tokenList.add(prefix+"-"+token);
		}
		
		return tokenList;
		
	}
	
}
