package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizCountryRiskRating;
import org.ibase4j.provider.BizCountryRiskRatingProvider;
import org.springframework.stereotype.Service;
/**
 * 描述：国别风险等级
 * 
 * @author xy
 * @version 1.0
 * @date 2018/06/27
 */
@Service
public class BizCountryRiskRatingService extends BaseService<BizCountryRiskRatingProvider , BizCountryRiskRating> {
	@Reference
	public void setProvider(BizCountryRiskRatingProvider provider) {
		this.provider = provider;
	}
}
