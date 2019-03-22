package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysNews;
import org.ibase4j.provider.ISysNewsProvider;
import org.springframework.stereotype.Service;

/**
 * 新闻管理
 * @author ShenHuaJie
 */
@Service
public class SysNewsService extends BaseService<ISysNewsProvider, SysNews> {
	@Reference
	public void setProvider(ISysNewsProvider provider) {
		this.provider = provider;
	}
}
