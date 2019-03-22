package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.util.CacheUtil;

@Service(interfaceClass = ISysCacheProvider.class)
public class SysCacheProviderImpl implements ISysCacheProvider {
	Logger logger = LogManager.getLogger();

	// 清缓存
	@Override
    public void flush() {
		logger.info("清缓存开始......");
		CacheUtil.getCache().delAll("*:sysDics:*");
		CacheUtil.getCache().delAll("*:sysDicMap:*");
		CacheUtil.getCache().delAll("*:getAuthorize:*");
		CacheUtil.getCache().delAll("*:sysPermission:*");
		logger.info("清缓存结束!");
	}
}