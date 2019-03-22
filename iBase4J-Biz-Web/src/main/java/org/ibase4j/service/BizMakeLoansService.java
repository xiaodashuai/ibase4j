package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizMakeLoans;
import org.ibase4j.provider.BizDebtGrantProvider;
import org.ibase4j.provider.BizMakeLoansProvider;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lwh
 * @version 2018年7月24日 放款
 */
@Service
public class BizMakeLoansService extends BaseService<BizMakeLoansProvider, BizMakeLoans> {
	@Reference
	public void setProvider(BizMakeLoansProvider provider) {
		this.provider = provider;
	}
    /**
     * @Description: 获取放款页面信息项
     * @Param: [params]
     * @return: java.util.Map
     */
    public Map getMakeLoansDebtInfo(Map<String,Object> params){
        Map makeLoansDebtInfo = provider.getMakeLoansDebtInfo(params);
        return makeLoansDebtInfo;
    }

    /**
     * @Description: 请求发放相关接口
     * @Param: [params]
     * @return: java.lang.String
     */
    public String requestMakeLoansInterface(Map<String,Object> params){
        return provider.requestMakeLoansInterface(params);
    }

    /**
     * @Description: 发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */
    public String sendRepaymentPlan(Map<String,Object> params){
        return provider.sendRepaymentPlan(params);
    }

    /**
     * @Description: 发放审核更改以后发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */
    public String updateRepaymentPlan(Map<String,Object> params){
        return provider.updateRepaymentPlan(params);
    }
	/**
	 * @Description: 获取还款计划信息项
	 * @Param: [params]
	 * @return: java.util.Map
	 */
	public Map getRepaymentInfo(Map<String,Object> params){
		Map repaymentInfo = provider.getRepaymentInfo(params);
		return repaymentInfo;
	}

	/**
	* @Description: 查询废弃放款信息
	* @Param: [grantCode]
	* @return: java.util.Map
	*/
	public Map getDiscardMakeLoansInfo(Map<String,Object> params){
	    String grantCode = StringUtil.objectToString(params.get("grantCode"));
	    return provider.getDiscardMakeLoansInfo(grantCode);
    }

    /**
     * @Description: 废弃放款
     * @Param: [params]
     * @return: java.lang.String
     */
    public String discardMakeLoans(Map<String, Object> params){
        return provider.discardMakeLoans(params);
    }
}
