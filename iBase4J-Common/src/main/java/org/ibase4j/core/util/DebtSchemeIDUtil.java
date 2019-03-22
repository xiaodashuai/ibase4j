package org.ibase4j.core.util;

import java.util.Date;

public final class DebtSchemeIDUtil {
	
	private DebtSchemeIDUtil() {
		
	}
	
	/**
	 * 生成债项方案ID
	 * 
	 * @param institutionCode
	 * 			机构代码(参照国结)
	 * @param countOfFirst
	 * 			当天业务总数
	 * @param countOfSecond
	 * 			单笔债项次数
	 */
	public static final String getDebtSchemeID(String institutionCode , Integer countOfFirst , Integer countOfSecond){
		
		//主编号
		String firstItemNo = institutionCode + new Date();
		if (countOfFirst < 10){
			firstItemNo += "000" + countOfFirst;
		}else if (countOfFirst < 100){
			firstItemNo += "00" + countOfFirst;
		}else if (countOfFirst < 1000){
			firstItemNo += "0" + countOfFirst;
		}else{
			firstItemNo += countOfFirst;
		}
		
		//子编号
		String secondItemNo = "";
		if (countOfSecond < 10){
			secondItemNo += "00" + countOfSecond;
		}else if (countOfSecond < 100){
			secondItemNo += "0" + countOfSecond;
		}else{
			secondItemNo += countOfSecond.toString();
		}
		String debtSchemeID = firstItemNo + secondItemNo;
		return debtSchemeID;
	}
}
