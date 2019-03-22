/**
 * 
 */
package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.support.scheduler.TaskScheduled;
import org.ibase4j.model.QrtzTaskLog;

import java.util.List;
import java.util.Map;

/**
 * 定时任务管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月15日 上午11:06:49
 */
public interface ISchedulerProvider {

	/** 获取所有任务 */
	public List<TaskScheduled> getAllTaskDetail();

	/** 执行任务 */
	public void execTask(String taskGroup, String taskName);

	/** 启停 */
	public void openTask(String taskGroup, String taskName);

	/** 启停 */
	public void closeTask(String taskGroup, String taskName);

	/** 删除作业 */
	public void delTask(String taskGroup, String taskName);

	public QrtzTaskLog updateLog(QrtzTaskLog record);

	public QrtzTaskLog getFireLogById(Long id);

	public Page<QrtzTaskLog> queryLog(Map<String, Object> params);

	/** 修改执行计划 */
	public void updateTask(TaskScheduled taskScheduled);
}
