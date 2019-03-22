/**
 * 
 */
package org.ibase4j.core.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 工具:集合处理
 * 
 * @author czm email:czm_hudy@126.com
 * @version 1.0
 * @since 1.0
 */

public class CollectionUtil extends CollectionUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Set<?> convertToSet(List<?> list) {
		return new HashSet(list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<?> convertToList(Set<?> set) {
		return new ArrayList(set);
	}

	public static boolean inLongList(List<Long> list, Long obj) {
		if (list != null && list.size() > 0) {
			for (Long o : list) {
				if (o.equals(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断一个对象是否在集合之中，使用的时候需要，自己重写对象的equals方法
	 * 
	 * @param list
	 * @param obj
	 * @return
	 */
	public static boolean isInList(List<Object> list, Object obj) {
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				if (o.equals(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isInSet(Set<Object> set, Object obj) {
		if (set != null && set.size() > 0) {
			Iterator<Object> it = set.iterator();
			while (it.hasNext()) {
				Object item = it.next();
				if (item.equals(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	public static String[] convertListToArrays(List<String> list) {
		int len = list.size();
		return list.toArray(new String[len]);
	}

	/**
	 * 判断字符串是否存在集合中
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @param strList
	 *            集合
	 * @return 如果集合为空或者不包含字符串返回假,否则返回真;
	 */
	public static boolean isInList(String str, List<String> strList) {
		boolean flag = false;
		if (strList != null) {
			for (String s : strList) {
				if (s.equals(str)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 将特殊符号分隔的字符串转换成List
	 * 
	 * @param str
	 *            字符
	 * @param separatorChar
	 *            分隔字符串
	 * @return 如果字符串为空返回空，否则返回List
	 */
	public static List<String> convertStringToList(String str, String separatorChar) {
		if (StringUtils.isNotBlank(str)) {
			String[] strArray = StringUtils.split(str, separatorChar);
			return convertStringArraysToList(strArray);
		} else {
			return null;
		}
	}

	/**
	 * 将字符串数组转换成List
	 * 
	 * @param strArray
	 *            字符串数
	 * @return 如果数组不为空返回List，否则返回空
	 */
	public static List<String> convertStringArraysToList(String[] strArray) {
		if (strArray != null && strArray.length > 0) {
			return Arrays.asList(strArray);
		} else {
			return null;
		}
	}

	/**
	 * 将List中的每个字符串元素转换成用separatorChar号分隔串字符串
	 * 
	 * @param list
	 * @param splitChar
	 *            分隔符
	 * @return 如果List不为空返回字符串，否则返回空
	 */
	public static String convertToSplitString(List<String> list, String separatorChar) {
		if (list != null) {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i);
				result.append(id);
				if (list.size() - i > 1) {
					result.append(separatorChar);
				}
			}
			return result.toString();
		} else {
			return null;
		}
	}

	public static String convertToSplitString(String[] strArray, String separatorChar) {
		StringBuffer strBuf = new StringBuffer("");
		if (strArray != null && strArray.length > 0) {
			int size = strArray.length;
			for (int i = 0; i < size; i++) {
				strBuf.append(strArray[i]);
				if (size - i > 1) {
					strBuf.append(separatorChar);
				}
			}
		}
		return strBuf.toString();
	}
	
	/**
	 * 转换成双引号的id字符串SQL
	 * @param strArray
	 * @param separatorChar
	 * @return
	 */
	public static String convertToSplitStr(String[] strArray, String separatorChar) {
		StringBuffer strBuf = new StringBuffer("");
		if (strArray != null && strArray.length > 0) {
			int size = strArray.length;
			for (int i = 0; i < size; i++) {
				strBuf.append("'").append(strArray[i]).append("'");
				if (size - i > 1) {
					strBuf.append(separatorChar);
				}
			}
		}
		return strBuf.toString();
	}

	/**
	 * 分割字符串，不会生成中间的空字符串
	 * 
	 * @param str
	 *            要分割的字符串
	 * @param delim
	 *            分隔符
	 * @return 分割的结果
	 */
	public static String[] splitWithoutEmpty(String str, String delim) {
		StringTokenizer s = new StringTokenizer(str, delim);
		String result[] = new String[s.countTokens()];
		for (int i = 0; i < result.length; i++) {
			result[i] = s.nextToken();
		}
		return result;
	}

	/**
	 * 字符串转换成List<Map<String, Object>>
	 * 
	 * @param str 要分割的字符串
	 * @param delim 分隔符
	 * @return 分割的结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> splitWithoutListMap(String str) {
		String strList[] = splitWithoutEmpty(str, "*");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < strList.length; i++) {
			String strListMap[] = splitWithoutEmpty(strList[i], "=");
			Map map = new HashMap<Map, Object>();
			map.put(strListMap[0], strListMap[1]);
			result.add(map);
		}
		return result;
	}
}
