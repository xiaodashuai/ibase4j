package org.ibase4j.service;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.WFLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ibase4j.provider.WFLogsProvider;

import java.util.List;


@Service
public class WFLogsService extends BaseService<WFLogsProvider, WFLogs>{
	@Autowired
	private WFLogsProvider wFLogsProvider;
	
	/** 工作引擎登录 **/
	public boolean signon(String userId, List<String> deptIds, List<String> totalDeptIds, List<String> mfRoles){
		return wFLogsProvider.signon(userId, deptIds, totalDeptIds, mfRoles);

	}
	
	/** 工作引擎退出系统 **/
	public boolean quit(String userId){
		return wFLogsProvider.exits(userId);
	}
	
}
