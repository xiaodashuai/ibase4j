/**
 * 
 */
package org.ibase4j.extend;

import com.matrix.engine.proxy.MFExecutionServiceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.util.CacheUtil;



/**
 * 工作流使用的缓存管理
 * @author czm
 *
 */
public class WorkflowCacheUtil {
	protected static Logger logger = LogManager.getLogger(WorkflowCacheUtil.class);
	private static final String WF_KEY = "IBASE4J:CACHE:WF";
	/**
	 * 保存工作流核心对象到redis缓存，用户id作为标示键
	 * @param userId
	 * @param mfEsp
	 */
	public static void saveWfExecution(String userId, MFExecutionServiceProxy mfEsp){
		//如果获取锁，那么保存
//		CacheUtil.getCache().putMap(WF_KEY, userId,mfEsp);

		if(logger.isDebugEnabled()) {
			logger.debug("=======WorkflowCacheUtil====saveWfExecution======executionService={}", mfEsp);
			logger.debug("=======WorkflowCacheUtil====saveWfExecution======userId={}", userId);
		}
		CacheUtil.getLockManager().set(WF_KEY+userId,mfEsp);
	}
	
	/**
	 * 获取工作流核心对象
	 * @param userId 用户标示id 
	 * @return 如果不存在返回空
	 */
	public static MFExecutionServiceProxy getWfExecution(String userId){

		Object obj = CacheUtil.getLockManager().get(WF_KEY+userId);

		if(logger.isDebugEnabled()) {
			logger.debug("=======WorkflowCacheUtil====getWfExecution======executionService={}", obj);
			logger.debug("=======WorkflowCacheUtil====getWfExecution======userId={}", userId);
		}
		if(obj!=null){
			return (MFExecutionServiceProxy) obj;
		}else{
			return null;
		}
		
	}
	
	/**
	 * 退出系统删除工作流核心对象
	 * @param userId 用户标示id 
	 * @return 如果不存在返回空
	 */
	public static boolean delWfExecution(String userId){
		if(logger.isDebugEnabled()) {
			logger.debug("=======WorkflowCacheUtil====getWfExecution======userId={}", userId);
		}
		CacheUtil.getLockManager().del(WF_KEY+userId);
		return true;
	}
}
