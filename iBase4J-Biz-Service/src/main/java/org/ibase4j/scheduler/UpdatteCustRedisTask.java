package org.ibase4j.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.provider.BizCustProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
* @Description:  客户存入redis
* @Param:
* @return:
* @Author: xiaoshuiquan
* @Date: 2018/12/3
*/
@Service
@EnableScheduling
public class UpdatteCustRedisTask {
    private final Logger logger = LogManager.getLogger(UpdatteCustRedisTask.class);
    @Autowired
    private BizCustProvider provider;


    @Scheduled(cron = "0 0 03 * * ?")
    public void flushCustRedis() {
        logger.debug("==========保存客户到redis的定时器开始执行了============");
        provider.init();
        logger.debug("==========保存客户到redis的定时器执行完成了============");
    }


}
