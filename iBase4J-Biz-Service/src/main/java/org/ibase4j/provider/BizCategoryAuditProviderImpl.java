package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizCategoryAuditMapper;
import org.ibase4j.model.BizCategoryAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author lwh
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizCategoryAudit")
@Service(interfaceClass = BizCategoryAuditProvider.class)
public class BizCategoryAuditProviderImpl extends BaseProviderImpl<BizCategoryAudit> implements BizCategoryAuditProvider {
	@Autowired
	private BizCategoryAuditMapper bizCategoryAuditMapper;
}
