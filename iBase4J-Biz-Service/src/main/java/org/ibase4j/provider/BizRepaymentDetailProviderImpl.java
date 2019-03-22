/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizRepaymentDetailMapper;
import org.ibase4j.model.BizRepaymentDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 描述：还款计划详细信息
 * 日期：2018/7/24
 * @author lwh
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizRepaymentDetail")
@Service(interfaceClass =  BizRepaymentDetailProvider.class)
public class BizRepaymentDetailProviderImpl extends BaseProviderImpl<BizRepaymentDetail> implements BizRepaymentDetailProvider {
	@Autowired
    private BizRepaymentDetailMapper bizRepaymentDetailMapper;
	
}
