package org.ibase4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.cache.RedissonHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedissonHelperTest {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring-config.xml");
        RedissonHelper redissonHelper = context.getBean("redissonHelper", RedissonHelper.class);
       redissonHelper.set("aa15","我是中国人");
       redissonHelper.set("bb15","123456");
       redissonHelper.ttl("aa");
        logger.debug(redissonHelper.get("aa15"));
        logger.debug(redissonHelper.get("bb15"));
    }
}
