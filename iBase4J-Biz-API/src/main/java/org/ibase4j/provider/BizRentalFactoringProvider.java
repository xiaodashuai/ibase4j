/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizRentalFactoring;

import java.util.Map;

/**
 * 功能：租金保理
 * 
 * @author lwh
 * 日期：2018/7/6
 */
public interface BizRentalFactoringProvider extends BaseProvider<BizRentalFactoring> {

    /** 
    * @Description: 发放页面业务专有信息
    * @Param: [params] 
    * @return: java.util.Map 
    */ 
    Map getBizRentalFactoringForMakeLoan(Map<String,Object> params);
}
