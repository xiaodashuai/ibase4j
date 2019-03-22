/**
 * 
 */
package org.ibase4j.provider;

import com.matrix.engine.proxy.MFExecutionServiceProxy;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.WFLogs;

import java.util.List;


/**
 * 描述：工作流登录日志
 * @author czm
 * @date 2018-02-01
 */
public interface WFLogsProvider extends BaseProvider<WFLogs> {
	/** 工作引擎登录 **/
	boolean signon(String userId, List<String> deptIds, List<String> totalDeptIds, List<String> mfRoles);
	/** 工作引擎退出系统 **/
	boolean exits(String userId);
	/** 工作引擎登录 **/
	MFExecutionServiceProxy signonNew(String userId, List<String> deptIds, List<String> totalDeptIds, List<String> mfRoles);
}
