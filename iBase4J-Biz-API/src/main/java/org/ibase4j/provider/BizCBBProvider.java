/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCBB;

/**
 * 功能：余额信息表
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizCBBProvider extends BaseProvider<BizCBB> {

    /**
    * @Description: 条件查询单条余额记录
    * @Param: [bizCBB]
    * @return: org.ibase4j.model.BizCBB
    */
    public BizCBB selectOneBizCBB(BizCBB bizCBB);
    
    /**
	 * 功能：删除发放审核对应的CBE
	 * @param grantId
	 * @return
	 */
	int deleteByGrant(Long grantId);
}
