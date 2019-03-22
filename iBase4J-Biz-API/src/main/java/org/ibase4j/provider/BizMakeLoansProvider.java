/**
 * 
 */
package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizMakeLoans;
import org.ibase4j.model.SysDept;

import java.util.Map;

/**
 * 功能：放款
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizMakeLoansProvider extends BaseProvider<BizMakeLoans> {

    /**
     * @Description: 获取放款页面信息项
     * @Param: [params]
     * @return: java.util.Map
     */
    Map getMakeLoansDebtInfo(Map<String,Object> params);

    /**
     * @Description: 请求发放相关接口
     * @Param: [params]
     * @return: java.lang.String
     */
    String requestMakeLoansInterface(Map<String,Object> params);

    /**
     * @Description: 发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */
    String sendRepaymentPlan(Map<String,Object> params);

    /**
     * @Description: 发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */
    String updateRepaymentPlan(Map<String,Object> params);

    /**
     * @Description: 获取还款计划信息项
     * @Param: [params]
     * @return: java.util.Map
     */
    Map getRepaymentInfo(Map<String,Object> params);

    /**
     * @Description: 根据用户id获取用户机构
     * @Param: [userId]
     * @return: org.ibase4j.model.SysDept
     */
    SysDept getUserInstitutionCodeByUserId(Long userId);

    /**
    * @Description: 根据用户机构编码查询用户机构对象
    * @Param: [deptCode]
    * @return: org.ibase4j.model.SysDept
    */
    SysDept getDeptByDeptCode(String deptCode);

    /** 
    * @Description: 查询废弃放款信息 
    * @Param: [grantCode] 
    * @return: java.util.Map 
    */ 
    Map getDiscardMakeLoansInfo(String grantCode);
    
    /**
     * @Description: 废弃放款
     * @Param: [params]
     * @return: java.lang.String
     */
    String discardMakeLoans(Map<String, Object> params);
    
    /** 
    * @Description: 请求接口 
    * @Param: [params] 
    * @return: java.lang.String
    */ 
    Map invokeInterface(String grantCode,String actionSet,String action,String args,Long objInr);

}
