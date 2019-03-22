package org.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.provider.BizCustProvider;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BizServerListener implements ServletContextListener {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
    }

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        //context.getBean(BizCustProvider.class).init();
        logger.info("=================================");
        logger.info("系统[{}]启动完成!!!", contextEvent.getServletContext().getServletContextName());
        logger.info("=================================");
    }
}
