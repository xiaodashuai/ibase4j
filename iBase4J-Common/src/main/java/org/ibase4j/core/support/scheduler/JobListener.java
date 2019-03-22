package org.ibase4j.core.support.scheduler;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.Constants.JOBSTATE;
import org.ibase4j.core.util.NativeUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.model.QrtzTaskLog;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 调度监听器
 *
 * @author ShenHuaJie
 * @version 2016年5月27日 下午4:31:31
 */
public class JobListener implements org.quartz.JobListener {
	private static Logger logger = LogManager.getLogger(JobListener.class);
	//	@Autowired
//	private SchedulerService schedulerService;
//	@Autowired
//	private QueueSender queueSender;
	// 线程池
	int threadSleep = PropertiesUtil.getInt("db.reader.list.threadWait", 5);
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, threadSleep, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());
	private static String JOB_LOG = "jobLog";

	@Override
	public String getName() {
		return "taskListener";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	// 任务开始前
	@Override
	public void jobToBeExecuted(final JobExecutionContext context) {
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String targetObject = jobDataMap.getString("targetObject");
		String targetMethod = jobDataMap.getString("targetMethod");
		if (logger.isInfoEnabled()) {
			logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
		}
		// 保存日志
		QrtzTaskLog log = new QrtzTaskLog();
		log.setStartTime(context.getFireTime());
		log.setGroupName(targetObject);
		log.setTaskName(targetMethod);
		log.setStatus(JOBSTATE.INIT_STATS);
		log.setServerHost(NativeUtil.getHostName());
		log.setServerDuid(NativeUtil.getDUID());
//		schedulerService.updateLog(log);
		jobDataMap.put(JOB_LOG, log);
	}

	// 任务结束后
	@Override
	public void jobWasExecuted(final JobExecutionContext context, JobExecutionException exp) {
		Timestamp end = new Timestamp(System.currentTimeMillis());
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String targetObject = jobDataMap.getString("targetObject");
		String targetMethod = jobDataMap.getString("targetMethod");
		if (logger.isInfoEnabled()) {
			logger.info("定时任务执行结束：{}.{}", targetObject, targetMethod);
		}
		// 更新任务执行状态
		final QrtzTaskLog log = (QrtzTaskLog) jobDataMap.get(JOB_LOG);
		if (log != null) {
			log.setEndTime(end);
			if (exp != null) {
				String contactEmail = jobDataMap.getString("contactEmail");
				if (StringUtils.isNotBlank(contactEmail)) {
					String topic = String.format("调度[%s.%s]发生异常", targetMethod, targetMethod);
//					sendEmail(new Email(contactEmail, topic, exp.getMessage()));
				}
				log.setStatus(JOBSTATE.ERROR_STATS);
				log.setFireInfo(exp.getMessage());
			} else {
				if (log.getStatus().equals(JOBSTATE.INIT_STATS)) {
					log.setStatus(JOBSTATE.SUCCESS_STATS);
				}
			}
		}
		executor.submit(new Runnable() {
			@Override
			public void run() {
				if (log != null) {
					try {
//						schedulerService.updateLog(log);
					} catch (Exception e) {
						logger.error("Update TaskRunLog cause error. The log object is : " + JSON.toJSONString(log), e);
					}
				}
			}
		});
	}

//	private void sendEmail(final Email email) {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				if (queueSender != null) {
//					queueSender.send("iBase4J.emailSender", email);
//				} else {
//					logger.info("将发送邮件至：" + email.getSendTo());
//					EmailUtil.sendEmail(email);
//				}
//			}
//		});
//	}
}
