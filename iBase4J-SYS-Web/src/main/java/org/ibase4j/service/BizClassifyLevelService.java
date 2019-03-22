
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizClassifyLevel;
import org.ibase4j.provider.IBizClassifyLevelProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：请假流程
 * 
 * @author czm
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizClassifyLevelService extends BaseService<IBizClassifyLevelProvider, BizClassifyLevel> {
	@Reference
	public void setIBizClassifyLevelProvider(IBizClassifyLevelProvider provider) {
		this.provider = provider;
	}




}
