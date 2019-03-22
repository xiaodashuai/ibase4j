package org.ibase4j.provider;


import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizEmailMapper;
import org.ibase4j.model.BizEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xy
 * @date 2018/05/25
 */
@CacheConfig(cacheNames = "bizEmail")
@Service(interfaceClass = BizEmailProvider.class)
public class BizEmailProviderImpl extends BaseProviderImpl<BizEmail> implements BizEmailProvider {
	@Autowired
	private BizEmailMapper bizEmailMapper;
	
}
