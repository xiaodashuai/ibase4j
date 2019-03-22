package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizBusiCategoryMapper;
import org.ibase4j.model.BizBusiCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author lwh
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizBusiCategory")
@Service(interfaceClass = BizBusiCategoryProvider.class)
public class BizBusiCategoryProviderImpl extends BaseProviderImpl<BizBusiCategory> implements BizBusiCategoryProvider {
	@Autowired
	private BizBusiCategoryMapper bizBusiCategoryMapper;
}
