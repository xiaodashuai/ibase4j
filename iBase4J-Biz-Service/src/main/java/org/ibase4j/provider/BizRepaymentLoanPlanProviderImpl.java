/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizRepaymentLoanPlanMapper;
import org.ibase4j.model.BizRepaymentLoanPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：还息计划表
 * 日期：2018/7/24
 * @author lwh
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizRepaymentLoanPlan")
@Service(interfaceClass =  BizRepaymentLoanPlanProvider.class)
public class BizRepaymentLoanPlanProviderImpl extends BaseProviderImpl<BizRepaymentLoanPlan> implements BizRepaymentLoanPlanProvider {
	@Autowired
    private BizRepaymentLoanPlanMapper bizRepaymentLoanPlanMapper;

	public  List getDebtInfoForRepaymentLoan(Map<String,Object> params){
	    return bizRepaymentLoanPlanMapper.getDebtInfoForRepaymentLoan(params);
    }

	@Override
	public int deleteByGrantCode(String grantCode, String debtCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("debtCode", debtCode);
		params.put("grantCode", grantCode);
		return this.deleteByParams(params);
	}

	@Override
	public List<Long> selectIdPage(Page page, Map<String, Object> params) {
		return bizRepaymentLoanPlanMapper.selectIdPage(page,params);
	}

}
