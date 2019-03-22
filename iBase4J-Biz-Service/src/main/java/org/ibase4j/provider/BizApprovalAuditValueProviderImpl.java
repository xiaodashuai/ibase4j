package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizApprovalAuditValueMapper;
import org.ibase4j.mapper.BizApprovalWorkflowTaskMapper;
import org.ibase4j.model.BizApprovalAuditValue;
import org.ibase4j.model.BizApprovalWorkflowTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lwh
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizApprovalAuditValue")
@Service(interfaceClass = BizApprovalAuditValueProvider.class)
public class BizApprovalAuditValueProviderImpl extends BaseProviderImpl<BizApprovalAuditValue> implements BizApprovalAuditValueProvider {

	@Autowired
	private BizApprovalWorkflowTaskProvider bizApprovalWorkflowTaskProvider;
	@Override
	public void saveCheckValues(Map<String,Object> params){
		// 审批流程ID
		String approvalId =  StringUtil.objectToString(params.get("approvalId"));
		String categoryId =  StringUtil.objectToString(params.get("businessType"));
		// 获取check项的值构建审核要素对象并存储数据
		Map<String,Object> checkvalues = (Map<String,Object>)params.get("record");
		for (Map.Entry<String,Object> entry : checkvalues.entrySet()) {
			BizApprovalAuditValue auditValue = new BizApprovalAuditValue();
			String checkName = entry.getKey().toString();
			String checkValue = entry.getValue().toString();
			auditValue.setApprovalId(approvalId);
			auditValue.setCategoryId(categoryId);
			auditValue.setAuditName(checkName);
			auditValue.setAuditValue(checkValue);
			auditValue.setEnable(1);
			update(auditValue);
		}
	}

	@Override
	public Map<String, Object> getCheakValuesByApprovalId(Map<String, Object> params) {
		Map<String,Object> map = new HashMap<>();
		map.put("approvalId",StringUtil.objectToString(params.get("approvalId")));
		map.put("enable",1);
		List<BizApprovalAuditValue> bizApprovalAuditValues = queryList(map);
		Map<String,Object> record = new HashMap<>();
		for(BizApprovalAuditValue check :bizApprovalAuditValues ){
			String auditName = check.getAuditName();
			String auditValue = check.getAuditValue();
			record.put(auditName,auditValue);
		}
		return record;
	}

	@Override
	public Map<String,Object> getCheckValuesByTaskId(String taskId) {
		Map<String,Object> map = new HashMap<>();
		map.put("taskId",taskId);
		map.put("enable",1);
		List<BizApprovalAuditValue> bizApprovalAuditValues = queryList(map);
		Map<String,Object> record = new HashMap<>();
		for(BizApprovalAuditValue check :bizApprovalAuditValues ){
			String auditName = check.getAuditName();
			String auditValue = check.getAuditValue();
			record.put(auditName,auditValue);
		}
		return record;
	}
	@Override
	public Map<String,Object> getCheckValuesDetails(Map<String,Object> params){
		String taskId=StringUtil.objectToString(params.get("taskId"));
		BizApprovalWorkflowTask bizApprovalWorkflowTask =new BizApprovalWorkflowTask();
		bizApprovalWorkflowTask.setTaskId(taskId);
		BizApprovalWorkflowTask bizApprovalWorkflowTask1=bizApprovalWorkflowTaskProvider.selectOneBizBizApproval(bizApprovalWorkflowTask);
		String approvalId =bizApprovalWorkflowTask1.getApprovalId();
		String commentInfo=bizApprovalWorkflowTask1.getCommentInfo();
		Map<String,Object> map = new HashMap<>();
		map.put("approvalId",approvalId);
		List<BizApprovalAuditValue> detailsValues = queryList(map);
		Map<String,Object> detailsCheckValues = new HashMap<>();
		for(BizApprovalAuditValue DetailsCheck : detailsValues){
			String auditName = DetailsCheck.getAuditName();
			String auditValue = DetailsCheck.getAuditValue();
			detailsCheckValues.put(auditName,auditValue);
		}
		detailsCheckValues.put("commentInfo",commentInfo);
		return detailsCheckValues;
	}

}
