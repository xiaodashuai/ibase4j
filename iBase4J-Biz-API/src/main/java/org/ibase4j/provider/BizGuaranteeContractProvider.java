package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizGuaranteeContract;

/**
 * 描述：保函担保
 * @author xiaoshuiquan
 * @date 2018/06/20
 */


public interface BizGuaranteeContractProvider extends BaseProvider<BizGuaranteeContract> {
	/**
     * 功能：删除发放审核对应的还款计划
     * @param grantCode
     * @param debtCode
     * @return
     */
    int deleteByGrantCode(String grantCode,String debtCode);
}
