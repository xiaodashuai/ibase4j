package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.InfAfrlndtl;

/**
 * 功能：贷款附表明细文件表
 * @author hannasong
 */
public interface InfAfrlndtlProvider extends BaseProvider<InfAfrlndtl> {

    /**
     * 查询还款记录;
     */
    void fetchRepayment();

}
