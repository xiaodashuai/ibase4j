/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizRepaymentPlanMapper;
import org.ibase4j.model.BizRepaymentPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Map;

/**
 * 描述：还款计划
 * 日期：2018/7/24
 * @author lwh
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizRepaymentPlan")
@Service(interfaceClass =  BizRepaymentPlanProvider.class)
public class BizRepaymentPlanProviderImpl extends BaseProviderImpl<BizRepaymentPlan> implements BizRepaymentPlanProvider {
	@Autowired
    private BizRepaymentPlanMapper bizRepaymentPlanMapper;

	@Override
    public Map getDebtInfoForRepayment(Map<String,Object> param){
        return bizRepaymentPlanMapper.getDebtInfoForRepayment(param);

    }
	
}
