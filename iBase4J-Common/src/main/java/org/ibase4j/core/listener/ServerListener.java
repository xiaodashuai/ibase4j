package org.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerListener implements ServletContextListener {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
	}

	@Override
    public void contextInitialized(ServletContextEvent contextEvent) {
		logger.info("=================================");
		logger.info("系统[{}]启动完成!!!", contextEvent.getServletContext().getServletContextName());
		logger.info("=================================");
	}
}