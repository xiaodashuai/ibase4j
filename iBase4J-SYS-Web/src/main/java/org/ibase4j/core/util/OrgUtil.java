/**
 * 
 */
package org.ibase4j.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能：组织机构工具类
 * 
 * @date 2018/9/18
 * @author czm
 * @version 1.0
 */
public class OrgUtil {

	/**
	 * 功能：通过部门编码获取机构编码，编码的格式：部门编码的前5位是机构码
	 * 
	 * @param deptCode
	 * @return
	 */
	public static String getOrgCodeFromDeptCode(String deptCode) {
		String str = "";
		if (StringUtils.isNotEmpty(deptCode)) {
			int len = deptCode.length();
			if (len > 3 && len < 8) {
				str = StringUtils.substring(deptCode, 0, 5);
			}
			int size = str.length();
			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < 7 - size; i++) {
				strBuf.append("0");
			}
			str += strBuf.toString();
		} else {
			str = deptCode;
		}
		return str;
	}

}
