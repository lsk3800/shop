
package com.ginage.common.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @date:2020年3月31日
 * @description:
 * @Copyright: ginage.com
 *
 */
public class RegexUtils {
	private static Pattern pattern;
	private static Matcher matcher;


	public static boolean isPhoneNumber(String str) {
		String regex = "1[0-9]{10}";
		boolean result = str.matches(regex);
		return result;
	}
}
