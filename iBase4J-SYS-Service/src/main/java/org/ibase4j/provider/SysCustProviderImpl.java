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
import org.springframework.scheduling.annotation.Async;

import java.util.Collections;
import java.util.List;

/**
 * @author XiaoYu
 * @date 2018/06/27
 */
@CacheConfig(cacheNames = "sysCust")
@Service(interfaceClass = SysCustProvider.class)
public class SysCustProviderImpl extends BaseProviderImpl<BizCust> implements SysCustProvider {

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private BizCustMapper bizCustMapper;

   /**
   * @Description:  把静态客户存入redis中
   * @Param: []
   * @return: void
   * @Author: tangzhiyou
   * @Date: 2018/11/30
   */
    @Override
    @Async
    public void init() {
        List<Long> list = bizCustMapper.selectIdPage(Collections.<String, Object>emptyMap());
        for (Long id : list) {
            BizCust cust = bizCustMapper.selectById(id);
            logger.info("==========BizCustProviderImpl===========init==cust=="+cust);
            //redisHelper.set(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(), JSON.toJSONString(mapper.selectById(id)));
            //redisHelper.expire(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(),86400);
            redisHelper.set(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(), JSON.toJSONString(mapper.selectById(id)),86400);
            logger.info(redisHelper.ttl(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN()));
            logger.info("==========BizCustProviderImpl===========init==cust=="+ redisHelper.get(Constants.CACHE_BIZCUST_NAMESPACE + cust.getCustNo()+cust.getCustNameCN(),86400));
        }

    }
}

