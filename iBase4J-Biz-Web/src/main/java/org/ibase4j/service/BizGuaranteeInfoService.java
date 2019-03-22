package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizBetInformation;
import org.ibase4j.model.BizGuaranteeInfo;
import org.ibase4j.provider.BizBetInformationProvider;
import org.ibase4j.provider.BizGuaranteeInfoProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwh
 * @date 2018/09/09
 */
@Service
public class BizGuaranteeInfoService extends BaseService<BizGuaranteeInfoProvider, BizGuaranteeInfo> {
	@Reference
	public void setProvider(BizGuaranteeInfoProvider provider) {
		this.provider = provider;
	}
	@Reference
	private BizBetInformationProvider betInformationProvider;
	
	/**
	 * 功能：根据担保id查询对象，里面包括了多个押品信息
	 * @param id
	 * @return
	 */
	public BizGuaranteeInfo getById(Long id){
		BizGuaranteeInfo model = provider.queryById(id);
		if(model!=null){
			String guarNo = model.getWarrantyContractNumber();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("guaranteeNo", guarNo);
			params.put("debtCode", model.getDebtCode());
			List<BizBetInformation> betInformationList = betInformationProvider.queryList(params);
			model.setBetInformationList(betInformationList);
		}
		return model;
	}
}
