package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizCheckPlanMapper;
import org.ibase4j.model.BizCheckPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
/**
 * @author xy
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizCheckPlan")
@Service(interfaceClass = BizCheckPlanProvider.class)
public class BizCheckPlanProviderImpl extends BaseProviderImpl<BizCheckPlan> implements BizCheckPlanProvider {
	@Autowired
	private BizCheckPlanMapper bizCheckPlanMapper;
}
