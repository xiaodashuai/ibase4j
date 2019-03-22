package org.ibase4j.provider;


import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizMsgMapper;
import org.ibase4j.model.BizMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xy
 * @date 2018/05/25
 */
@CacheConfig(cacheNames = "bizMsg")
@Service(interfaceClass = BizMsgProvider.class)
public class BizMsgProviderImpl extends BaseProviderImpl<BizMsg> implements BizMsgProvider {
	@Autowired
	private BizMsgMapper bizMsgMapper;

}
