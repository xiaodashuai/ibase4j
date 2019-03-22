package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.mapper.BizCustMapper;
import org.ibase4j.model.BizCust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author XiaoYu
 * @date 2018/06/27
 */
@CacheConfig(cacheNames = "bizCust")
@Service(interfaceClass = BizCustProvider.class)
public class BizCustProviderImpl extends BaseProviderImpl<BizCust> implements BizCustProvider {

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private BizCustMapper bizCustomerMapper;

    @Override
    public void init() {
        List<Long> list = mapper.selectIdPage(Collections.<String, Object>emptyMap());
        for (Long id : list) {
            BizCust cust = mapper.selectById(id);
            logger.info("==========BizCustProviderImpl===========init==cust=="+cust);
            redisHelper.set(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(), JSON.toJSONString(mapper.selectById(id)),86400);
            logger.info(redisHelper.ttl(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN()));
            logger.info("==========BizCustProviderImpl===========init==cust=="+ redisHelper.get(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(),86400));
        }

    }



    @Override
    public List queryByCustNo(String custNo) {
        List customer = bizCustomerMapper.queryByCustNo(custNo);
        logger.debug(customer);
        return customer;
    }

    @Override
    public List<BizCust> getBizCustomerList(Map<String, Object> params) {
        return bizCustomerMapper.getBizCustomerList(params);
    }
}

