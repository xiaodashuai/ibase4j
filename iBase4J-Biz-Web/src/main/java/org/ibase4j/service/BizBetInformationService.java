
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizBetInformation;
import org.ibase4j.provider.BizBetInformationProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：请假流程
 * 
 * @author czm
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizBetInformationService extends BaseService<BizBetInformationProvider, BizBetInformation> {
	@Reference
	public void setBizBetInformationProvider(BizBetInformationProvider provider) {
		this.provider = provider;
	}

}
