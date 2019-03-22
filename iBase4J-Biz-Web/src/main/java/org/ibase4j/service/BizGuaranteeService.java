package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizGuarantee;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.BizSingleProductRule;
import org.ibase4j.provider.BizGuaranteeProvider;
import org.ibase4j.provider.BizSingleProductRuleProvider;
import org.ibase4j.provider.IBizProductTypeProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保函担保
 * @author xiaoshuiquan
 * @version 2018年5月26日 下午3:47:21
 */
@Service
public class BizGuaranteeService extends BaseService<BizGuaranteeProvider,BizGuarantee> {
    @Reference
    public void setProvider(BizGuaranteeProvider guaranteeProvider) {
        this.provider = guaranteeProvider;
    }
    @Reference
    private IBizProductTypeProvider bizProductTypeProvider;
    @Reference
    private BizSingleProductRuleProvider singleProductRuleProvider;


	/** 查询勾选的产品 */
	public List<BizProductTypes> getproList(Map map) {
		List<BizProductTypes> pList = new ArrayList<>();
		//债项方案编码查询单一产品规则
		Long debrNum = Long.valueOf((String) map.get("debtNum"));
		Map<String, Object> params = new HashMap<>();
		params.put("debtNum", debrNum);
		List<BizSingleProductRule> singleList = singleProductRuleProvider.queryList(params);
		//通过单一产品中的产品细类查询产品种类名称
		for (BizSingleProductRule single : singleList) {
			String code = single.getBusinessType();
			BizProductTypes businessTypes =bizProductTypeProvider.getByCode(code);
			pList.add(businessTypes);
		}
		return pList;
	}

}
