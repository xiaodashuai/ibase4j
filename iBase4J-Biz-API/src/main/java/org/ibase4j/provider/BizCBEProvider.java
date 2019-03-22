/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCBE;

/**
 * 功能：发生额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizCBEProvider extends BaseProvider<BizCBE> {
	/**
	 * 功能：删除发放审核对应的CBE
	 * @param grantId
	 * @return
	 */
	int deleteByGrant(Long grantId);

}
