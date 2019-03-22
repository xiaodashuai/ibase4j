package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizCbsErrorMessageMapper;
import org.ibase4j.model.BizCbsErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author lwh
 * @date 2018/10/10
 */
@CacheConfig(cacheNames = "bizCbsErrorMessage")
@Service(interfaceClass = BizCbsErrorMessageProvider.class)
public class BizCbsErrorMessageProviderImpl extends BaseProviderImpl<BizCbsErrorMessage> implements BizCbsErrorMessageProvider {
	@Autowired
	private BizCbsErrorMessageMapper bizCbsErrorMessageMapper;

	@Override
	public BizCbsErrorMessage selectOne(BizCbsErrorMessage entity) {
		return super.selectOne(entity);
	}
}
