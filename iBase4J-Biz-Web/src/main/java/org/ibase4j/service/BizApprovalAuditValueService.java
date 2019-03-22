
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizApprovalAuditValue;
import org.ibase4j.model.BizAuditCheck;
import org.ibase4j.provider.BizApprovalAuditValueProvider;
import org.ibase4j.provider.BizAuditCheckProvider;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 * @author lwh
 * @version 1.0
 * @date 2018/6/8
 */
@Service
public class BizApprovalAuditValueService extends BaseService<BizApprovalAuditValueProvider, BizApprovalAuditValue> {
	@Reference
	public void setApprovalAuditValueProvider(BizApprovalAuditValueProvider provider) {
		this.provider = provider;
	}


    }
