/**
 * 
 */
package org.ibase4j.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能: 正则表达式工具
 * 
 * @author czm
 * @version 1.0
 * @date 2018-3-1
 */
public class RegexpUtil {
	private final static String PATTERN_CN = "[\u4e00-\u9fa5]*";
	private final static String PATTERN_W = "\\w+";
	private final static String PATTERN_TEL ="^(1[3|4|5|7|8][0-9])\\d{8}$";
	private final static String PATTERN_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	private final static String PATTERN_NUM =  "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	private final static String PATTERN_INTEGER = "^[0-9]+$";
	private final static String PATTERN_CN_REGEX = "[\\u4e00-\\u9fa5]";
	private final static String PATTERN_CHAR = "[a-zA-Z]*";
	private final static String PATTERN_INT = "[0-9]*";
	
	/**
	 * 判断日期格式和范围
	 */
	private final static String PATTERN_REXP = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	/**
	 * 是否包含汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		final Pattern pattern = Pattern.compile(PATTERN_CN);
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否是字母和数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isW(String str) {
		final Pattern pattern = Pattern.compile(PATTERN_W);
		return pattern.matcher(str).matches();
	}

	/**
	 * 中国移动电话分为中国移动，中国联通，中国电信三大运营商；根据开头3位和总位数判断是否合格,规则如下：<br/>
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188<br/>
	 * 联通：130、131、132、152、155、156、185、186<br/>
	 * 电信：133、153、180、189、（1349卫通）
	 * 
	 * @param tel
	 *            号码字符串
	 * @return 正则表达式判断，如果合格返回真，否则返回假
	 */
	public static boolean isTelephone(String tel) {
		final Pattern pattern = Pattern.compile(PATTERN_TEL);
		return pattern.matcher(tel).matches();
	}

	/**
	 * 正则表达式判断是否电子邮件箱
	 * 
	 * @param email
	 * @return 合格返回真，否则返回假
	 */
	public static boolean isEmail(String email) {
		final Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		return pattern.matcher(email).matches();
	}

	/**
	 * 正则表达式判断是否日期格式
	 * 
	 * @param email
	 * @return 合格返回真，否则返回假
	 */
	public static boolean isDate(String date) {
		Pattern pattern = Pattern.compile(PATTERN_REXP);
		Matcher mat = pattern.matcher(date);
		boolean dateType = mat.matches();
		return dateType;
	}

	/**
	 * 正则表达式判断是否是数字，效率高；
	 * 
	 * @param str
	 * @return
	 */
	public static boolean matchDigit(String str) {
		boolean isNum = str.matches("[0-9]+");
		return isNum;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param excelValue
	 * @return
	 */
	public static boolean isNum(String excelValue) {
		// "^[0-9]+$" "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"
		return excelValue.matches(PATTERN_NUM);
	}

	/**
	 * 判断字符串是否为整数
	 * 
	 * @param excelValue
	 * @return
	 */
	public static boolean isInteger(String excelValue) {
		return excelValue.matches(PATTERN_INTEGER);
	}

	/**
	 * 判断字符串必须是英文
	 * 
	 * @param excelValue
	 * @return
	 */
	public static boolean isEnglish(String excelValue) {
		int count = 0;
		final Pattern pattern = Pattern.compile(PATTERN_CN_REGEX);
		Matcher m = pattern.matcher(excelValue);// 用Pattern指定的规则，进行正则验证?
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Determines if the specified character is a digit.
	 * 
	 * @param c
	 * @return true if the character is a digit; false otherwise.
	 */
	public static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

	/**
	 * 正则表达式判断是否全部是字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isString(String str) {
		final Pattern pattern = Pattern.compile(PATTERN_CHAR);
		return pattern.matcher(str).matches();
	}

	/**
	 * 正则表达式判断是否全部是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		final Pattern pattern = Pattern.compile(PATTERN_INT);
		return pattern.matcher(str).matches();
	}

}
