package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizAuditCheckMapper;
import org.ibase4j.model.BizAuditCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author lwh
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizAuditCheck")
@Service(interfaceClass = BizAuditCheckProvider.class)
public class BizAuditCheckProviderImpl extends BaseProviderImpl<BizAuditCheck> implements BizAuditCheckProvider {
	@Autowired
	private BizAuditCheckMapper bizAuditCheckMapper;
}
