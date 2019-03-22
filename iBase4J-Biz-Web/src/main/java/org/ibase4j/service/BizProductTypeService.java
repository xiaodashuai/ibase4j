package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.provider.IBizProductTypeProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：产品类型
 * @author czm
 * @date 2018/08/09
 */
@Service
public class BizProductTypeService extends BaseService<IBizProductTypeProvider, BizProductTypes> {
	@Reference
	public void setProvider(IBizProductTypeProvider provider) {
		this.provider = provider;
	}

}
