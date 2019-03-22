package org.ibase4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.support.shiro.RedisClusterManager;
import org.ibase4j.core.support.shiro.RedisSentinelManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.Set;

public class RedisHelperTest {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring-config.xml");
//        RedisSentinelManager redisClient=  context.getBean("redisClient",RedisSentinelManager.class);
//        redisClient.set("aa1215".getBytes(),"bbb".getBytes(),1000);
        RedisHelper redisHelper = context.getBean("redisHelper", RedisHelper.class);
        BizCust cust = new BizCust();
        cust.setCustNo("AAAA");
        cust.setCreditLinesId(12121L);
        redisHelper.set(cust.getCustNo(), cust);
        redisHelper.expire(cust.getCustNo(),86400);
        logger.debug(redisHelper.ttl(cust.getCustNo()));
        BizCust cust1 = (BizCust) redisHelper.get(cust.getCustNo());
        logger.debug(cust1);
        Set<Object> sets = redisHelper.getAll("*AA*");
        Iterator iterator = sets.iterator();
        while (iterator.hasNext()) {
            BizCust cust2= (BizCust) iterator.next();
            logger.debug("cust2:"+cust2);
        }



    }
}
