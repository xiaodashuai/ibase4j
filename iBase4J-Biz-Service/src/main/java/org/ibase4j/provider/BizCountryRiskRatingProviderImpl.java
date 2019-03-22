package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizCountryRiskRatingMapper;
import org.ibase4j.model.BizCountryRiskRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
/**
 * @author xy
 * @date 2018/06/27
 */
@CacheConfig(cacheNames = "bizCountryRiskRating")
@Service(interfaceClass = BizCountryRiskRatingProvider.class)
public class BizCountryRiskRatingProviderImpl extends BaseProviderImpl<BizCountryRiskRating> implements BizCountryRiskRatingProvider {
	@Autowired
	private BizCountryRiskRatingMapper bizCountryRiskRatingMapper;

}
