/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.matrix.api.MFExecutionAgent;
import com.matrix.api.MFExecutionService;
import com.matrix.api.identity.BasicMFRole;
import com.matrix.engine.proxy.MFExecutionServiceProxy;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.extend.WorkflowCacheUtil;
import org.ibase4j.mapper.WFLogsMapper;
import org.ibase4j.model.WFLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：工作流引擎接口
 * @author czm
 * @date 2018-02-01
 */
@CacheConfig(cacheNames = "WfLogin")
@Service(interfaceClass = WFLogsProvider.class)
public class WFLogsProviderImpl  extends BaseProviderImpl<WFLogs> implements WFLogsProvider {
	@Autowired
	private WFLogsMapper wFLogsMapper;

	@Override
	public boolean exits(String userId) {
		MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);
		if(execution!=null){
			//当前会话有效，且登录状态为已登录，则可以退出系统
			if(execution.isValidSession()&&execution.isLoggedOn()){
				execution.logoff();
			}
			return true;
		}
		//从缓存redis里面清除用户key
		WorkflowCacheUtil.delWfExecution(userId);
		return false;
	}

	@Override
	public boolean signon(String userId, List<String> deptIds, List<String> totalDeptIds, List<String> mfRoles) {
		List<BasicMFRole> roleList = new ArrayList<BasicMFRole>();
		for(String id : mfRoles){
			BasicMFRole role = new BasicMFRole();
			role.setRoleId(id);
			roleList.add(role);
		}
		MFExecutionServiceProxy executionService = (MFExecutionServiceProxy) MFExecutionAgent.getExecutionService();
		executionService.signon(userId, deptIds, totalDeptIds, roleList);
		//保存到Redis
		if(logger.isDebugEnabled()) {
			logger.debug("=======WFLogsProviderImpl====signon======executionService={}", executionService);
			logger.debug("=======WFLogsProviderImpl====signon======userId={}", userId);
		}
		WorkflowCacheUtil.saveWfExecution(userId, executionService);

		return true;
	}

	@Override
	public MFExecutionServiceProxy signonNew(String userId, List<String> deptIds, List<String> totalDeptIds, List<String> mfRoles) {
		List<BasicMFRole> roleList = new ArrayList<BasicMFRole>();
		for(String id : mfRoles){
			BasicMFRole role = new BasicMFRole();
			role.setRoleId(id);
			roleList.add(role);
		}
		MFExecutionServiceProxy executionService = (MFExecutionServiceProxy) MFExecutionAgent.getExecutionService();
		executionService.signon(userId, deptIds, totalDeptIds, roleList);
		//保存到Redis
		if(logger.isDebugEnabled()) {
			logger.debug("=======WFLogsProviderImpl====signon======executionService={}", executionService);
			logger.debug("=======WFLogsProviderImpl====signon======userId={}", userId);
		}
		WorkflowCacheUtil.saveWfExecution(userId, executionService);

		return executionService;
	}
	
	
}
