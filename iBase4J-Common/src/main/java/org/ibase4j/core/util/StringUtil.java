/**
 * 
 */
package org.ibase4j.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.Assert;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：字符串处理工具
 * 
 * @date 2018/3/5
 * @author czm_hudy@126.com
 * @version 1.2
 */
public class StringUtil {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 把空字符串或者null转换成"";
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceNullToEmpty(String str) {
		if (StringUtils.isNotBlank(str)) {
			return StringUtils.trim(str);
		} else {
			return StringUtils.EMPTY;
		}
	}

	/***
	 * 去掉字符串中的所有空格
	 * 
	 * @param str
	 * @param regex
	 *            用来分割字符串的字符
	 * @return 返回trim后的值
	 */
	public static String trimString(String str, String regex) {
		if (StringUtils.isNotBlank(str)) {
			String[] array = str.split(regex);
			StringBuffer strBuf = new StringBuffer();
			for (String a : array) {
				strBuf.append(a.trim()).append(regex);
			}
			return strBuf.toString();
		} else {
			return str;
		}
	}

	/**
	 * 格式化字符串
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static String formatString(String str, String format) {
		Formatter fmt = new Formatter();
		return fmt.format(format, str).toString();
	}

	/**
	 * 转换
	 * 
	 * @param bytes
	 * @return
	 */
	public static String convertByteToString(byte[] bytes) {
		String s = new String(bytes);
		return s;
	}

	/**
	 * 字符串数转换成数字,如果为空,则返回0.0f;
	 * 
	 * @param strnumber
	 * @return 如果字符串为空或者转换异常返回 0.0f ,否则返回转换后的数字
	 */
	public static float stringToFloat(String strnumber) {
		float num = 0.0f;
		try {
			if (StringUtils.isNotBlank(strnumber)) {
				num = Float.valueOf(strnumber).floatValue();
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 转换成整数
	 * 
	 * @param obj
	 * @return 如果参数是空或转换错误返回0,否则返回整数
	 */
	public static int objToInteger(Object obj) {
		int num = 0;
		try {
			if (obj != null) {
				num = Integer.valueOf(obj.toString()).intValue();
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 字符串数转换成数字,如果为空,则返回0;
	 * 
	 * @param strnumber
	 * @return 如果字符串为空或者转换异常返回 0 ,否则返回转换后的数字
	 */
	public static int stringToInteger(String strnumber) {
		int num = 0;
		try {
			if (StringUtils.isNotBlank(strnumber)) {
				num = Integer.valueOf(strnumber).intValue();
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			// System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 字符串转换成Long类型
	 * 
	 * @param str
	 * @return 如果字符串为空，返回null
	 */
	public static Long stringToLong(String str) {
		if (StringUtils.isNotBlank(str)) {
			return Long.valueOf(str);
		} else {
			return null;
		}
	}
	
	/**
	 * 转换成BigDecimal
	 * @param b
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(Object b) {
		if (b!=null) {
			String amt = b.toString();
			//去掉金额中的逗号分割符号
			if(amt.indexOf(",")!=-1){
				amt = amt.replaceAll(",","");
			}
			return new BigDecimal(amt);
		} else {
			return null;
		}
	}
	
	/**
	 * 字符串转换成Boolean类型
	 * 
	 * @param b
	 * @return 如果字符串为空，返回null
	 */
	public static Boolean objectToBoolean(Object b) {
		if (b!=null) {
			return Boolean.parseBoolean(b.toString());
		} else {
			return null;
		}
	}


	/**
	 * 转对象为字符串
	 * 
	 * @param obj
	 * @return 如果参数为空，返回空字符串
	 */
	public static String objectToString(Object obj) {
		String str = "";
		if (obj != null) {
			str = obj.toString();
		}
		return str;
	}
	
	/**
	 * 功能：长整数转换成字符串
	 * @param n
	 * @return
	 */
	public static String longToString(Long n){
		String str="";
		if(n!=null){
			str = n.toString();
		}
		return str;
	}
	
	/**
	 * 转对象为日期，格式为yyyy-MM-dd
	 * 
	 * @param obj
	 * @return 如果参数为空，返回空字符串
	 */
	public static Date objectToDate(Object obj) {
		Date str =null;
		if (obj != null) {
			str = DateUtil.parseDate(obj.toString(), "yyyy-MM-dd");
		}
		return str;
	}
	/**
	 * 转换成日期时间，带时分秒的日期，格式为yyyy-MM-dd HH:mm:ss
	 * @param obj
	 * @return
	 */
	public static Date objectToDateTime(Object obj) {
		Date str =null;
		if (obj != null) {
			str = DateUtil.parseDate(obj.toString(), "yyyy-MM-dd HH:mm:ss");
		}
		return str;
	}

	/**
	 * Object转换成Long类型
	 * 
	 * @param lg
	 * @return 如果对象为空，那么返回空
	 */
	public static Long objectToLong(Object lg) {
		Long num = null;
		if (lg != null && StringUtils.isNotBlank(lg.toString())) {
			num = Long.valueOf(lg.toString());
		}
		return num;
	}

	/**
	 * 过滤覆盖特殊字符串，比如去掉字符串中的特殊字符
	 * 
	 * @param text
	 *            源字符串
	 * @param regex
	 *            特殊字符
	 * @param replacement
	 *            覆盖字符串数组
	 * @return
	 */
	public static String regexReplace(String text, String regex, List<String> replacements) {
		int size = replacements.size();
		String[] splits = StringUtils.split(text, "//" + regex);
		int len = splits.length;
		StringBuffer result = new StringBuffer();
		// 处理第一位就是分割符的情况
		int index = text.indexOf(regex);
		int k = 0;
		logger.debug(index);
		// 如果找不到则全部返回
		if (index == -1) {
			return text;
		}
		// 如果特殊字符在第一位,那么添加第一个参数
		if (index == 0) {
			result.append(replacements.get(0));
			k = 1;// 同时参数后移一位
		}
		for (int i = 0; i < len; i++) {
			String s = splits[i];
			logger.debug(s);
			if (k < size) {
				result.append(s).append(replacements.get(k));
			} else {
				result.append(s);
			}
			k++;
		}
		return result.toString();
	}

	/**
	 * 过滤覆盖特殊字符串，比如去掉字符串中的特殊字符
	 * 
	 * @param str
	 *            源字符串
	 * @param regex
	 *            特殊字符
	 * @param replacement
	 *            覆盖字符串
	 * @return
	 */
	public static String regexReplace(String str, String regex, String replacement) {
		if (StringUtils.isNotBlank(str)) {
			String regexString = "[" + regex + "]";
			String value = null;
			Pattern p = Pattern.compile(regexString);
			Matcher m = p.matcher(str);
			String temp = str;
			while (m.find()) {
				value = m.group(0);
				temp = str.replaceAll(value, replacement);
			}
			return temp;
		} else {
			return str;
		}
	}

	/**
	 * 获取特殊字符前面的字符串；
	 * 
	 * @param str
	 * @param regex
	 *            特殊字符
	 * @return
	 */
	public static String substringBeforeRegex(String str, String regex) {
		String regexString = "[" + regex + "]";
		Pattern p = Pattern.compile(regexString);
		Matcher m = p.matcher(str);
		//
		String temp = str;
		if (m.find()) {
			int endIndex = m.start();
			if (endIndex > 0) {
				temp = str.substring(0, endIndex);
			}
		}
		return temp;
	}
	
	/**
	 * 功能：解析字符串，从索引1处开始检查，如果遇到最后一个不为0的数字，返回索引
	 * @param number
	 * @return
	 */
	public static int getEndNotZeroNumberIndex(String number){
		int max = 0;
		if(StringUtils.isNotBlank(number)){
			int len = number.length();
			char[] strs = number.toCharArray();
			char zero = '0';
			for(int i=0;i<len;i++){
				char c = strs[i];
				if(c != zero){
					max = i;
				}
			}
		}
		return max;
	}

	/**
	* @Description: 将list里面的字符串用","分隔 返回字符串
	* @Param: [list]
	* @return: java.lang.String
	*/
	public static String listToString(List list,String separatorChar) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + separatorChar);
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 加密
	 * @param datasource byte[]
	 * @param password String
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] datasource, String password) {
		try{
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			//用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密
			//正式执行加密操作
			return cipher.doFinal(datasource); //按单部分操作加密或解密数据，或者结束一个多部分操作
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 解密
	 * @param src byte[]
	 * @param password String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//返回实现指定转换的 Cipher 对象
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}
	
	public static String joinInterfaceParams(List<Object> list) {
        Assert.notNull(list,"joinInterfaceParams is list not null");
		StringBuilder params = new StringBuilder();
		params.append("[");
		params.append(StringUtils.join(list.iterator(), ","));
		params.append("]");
		return params.toString();
	}

}
