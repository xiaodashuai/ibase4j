package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCust;

import java.util.List;
import java.util.Map;

public interface BizCustProvider extends BaseProvider<BizCust> {

    void init();

    //通过客户编号查询
    List queryByCustNo(String custNo);
    /**
     * 功能：查询方案中的客户主体信息
     * @param params
     * @return
     */
    List<BizCust> getBizCustomerList(Map<String,Object> params);
}
