
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizBusiCategory;
import org.ibase4j.provider.BizBusiCategoryProvider;
import org.springframework.stereotype.Service;


/**
 * 描述：请假流程
 * 
 * @author lwh
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizBusiCategoryService extends BaseService<BizBusiCategoryProvider, BizBusiCategory> {
	@Reference
	public void setBusiCategoryProvider(BizBusiCategoryProvider provider) {
		this.provider = provider;
	}


}
