package org.ibase4j.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.provider.BizCustProvider;
import org.ibase4j.provider.BizDebtSummaryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
* @Description:  刷新方案过期状态
* @Param:
* @return:
* @Author: xiaoshuiquan
* @Date: 2018/12/3
*/
@Service
@EnableScheduling
public class RefreshDebtExpired {
    private final Logger logger = LogManager.getLogger(RefreshDebtExpired.class);
    @Autowired
    private BizDebtSummaryProvider bizDebtSummaryProvider;

    @Scheduled(cron = "0 0 01 * * ?")
    public void flushCustRedis() {
        logger.debug("==========刷新方案过期状态定时器开始执行了============");
        bizDebtSummaryProvider.refreshDebtExpired();
        logger.debug("==========刷新方案过期状态定时器执行完成了============");
    }


}
