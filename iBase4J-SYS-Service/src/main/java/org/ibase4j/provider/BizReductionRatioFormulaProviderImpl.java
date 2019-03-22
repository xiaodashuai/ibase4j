package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizReductionRatioFormulaMapper;
import org.ibase4j.model.BizReductionRatioFormula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;


/**
 * @author xy
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizReductionRatioFormula")
@Service(interfaceClass = BizReductionRatioFormulaProvider.class)
public class BizReductionRatioFormulaProviderImpl extends BaseProviderImpl<BizReductionRatioFormula> implements BizReductionRatioFormulaProvider{
	@Autowired
	private BizReductionRatioFormulaMapper bizReductionRatioFormulaMapper;
}
