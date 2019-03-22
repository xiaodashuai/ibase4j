package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizPrincipalCreditProgramMapper;
import org.ibase4j.model.BizPrincipalCreditProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
/**
 * @author xy
 * @date 2018/05/14
 */
@CacheConfig(cacheNames = "bizPrincipalCreditProgram")
@Service(interfaceClass = BizPrincipalCreditProgramProvider.class)
public class BizPrincipalCreditProgramProviderImpl extends BaseProviderImpl<BizPrincipalCreditProgram> implements BizPrincipalCreditProgramProvider {
	@Autowired
	private BizPrincipalCreditProgramMapper bizPrincipalCreditProgramMapper;
	

}
