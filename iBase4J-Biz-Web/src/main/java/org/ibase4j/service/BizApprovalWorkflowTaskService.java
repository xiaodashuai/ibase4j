
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizApprovalAuditValue;
import org.ibase4j.model.BizApprovalWorkflowTask;
import org.ibase4j.provider.BizApprovalAuditValueProvider;
import org.ibase4j.provider.BizApprovalWorkflowTaskProvider;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lwh
 * @version 1.0
 * @date 2018/6/8
 */
@Service
public class BizApprovalWorkflowTaskService extends BaseService<BizApprovalWorkflowTaskProvider, BizApprovalWorkflowTask> {
	@Reference
	public void setApprovalWorkflowTaskProvider(BizApprovalWorkflowTaskProvider provider) {
		this.provider = provider;
	}


}
