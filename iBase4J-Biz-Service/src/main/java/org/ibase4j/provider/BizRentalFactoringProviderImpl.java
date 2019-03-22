/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizRemtalFactoringMapper;
import org.ibase4j.model.BizRentalFactoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Map;

/**
 * 描述：租金保理
 * 日期：2018/7/24
 * @author lwh
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizRentalFactoring")
@Service(interfaceClass =  BizRentalFactoringProvider.class)
public class BizRentalFactoringProviderImpl extends BaseProviderImpl<BizRentalFactoring> implements BizRentalFactoringProvider {
	@Autowired
    private BizRemtalFactoringMapper bizRentalFactoringMapper;

    @Override
    public Map getBizRentalFactoringForMakeLoan(Map<String, Object> params) {
        Map bizRentalFactoringForMakeLoan = bizRentalFactoringMapper.getBizRentalFactoringForMakeLoan(params);
        return bizRentalFactoringForMakeLoan;
    }
}
