
package com.ginage.payment.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
*@date:2020年5月16日
*@description:
*@Copyright: ginage.com
*
*/
public class StrategyFactory {

	private static Map<String,PayStrategy> payStrategyMap=new ConcurrentHashMap<String, PayStrategy>();
	public static PayStrategy getPayStrategy(String classAddress) {
		PayStrategy payStrategy = payStrategyMap.get(classAddress);
		if(payStrategy!=null) {
			return payStrategy;
		}
		
		try {
			Class<?> forName = Class.forName(classAddress);
			payStrategy=(PayStrategy) forName.newInstance();
			payStrategyMap.put(classAddress, payStrategy);
			return payStrategy;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
}
