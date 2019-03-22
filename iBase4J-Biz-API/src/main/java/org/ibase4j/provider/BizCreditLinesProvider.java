package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCreditLines;

public interface BizCreditLinesProvider extends BaseProvider<BizCreditLines> {
	
	/**
	 * 功能：删除发放审核对应的还款计划
	 * 
	 * @param objinr业务id
	 * @return
	 */
	int deleteByGrantId(String objinr,String debtCode);

}
