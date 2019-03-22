package org.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.provider.ISysUserProvider;
import org.ibase4j.provider.SysCustProvider;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

public class SysServerListerner extends ServerListener {
	protected final Logger logger = LogManager.getLogger(this.getClass());


	@Override
    public void contextInitialized(ServletContextEvent contextEvent) {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		context.getBean(ISysUserProvider.class).init();
		context.getBean(SysCustProvider.class).init();
		logger.info("=================================");
		logger.info("系统[{}]启动完成!!!", contextEvent.getServletContext().getServletContextName());
		logger.info("=================================");
	}
}