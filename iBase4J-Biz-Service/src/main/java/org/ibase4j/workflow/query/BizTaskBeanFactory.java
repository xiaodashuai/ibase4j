/**
 * 
 */
package org.ibase4j.workflow.query;

import com.matrix.api.instance.assistant.CommentInfo;
import com.matrix.api.instance.assistant.CommentInfoVO;
import com.matrix.api.instance.task.QueryBizField;
import com.matrix.api.instance.task.QueryBizTaskModel;
import org.ibase4j.extend.WorkflowConstant;
import org.ibase4j.vo.ProstatAuditVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述: 业务查询对象装配工厂类
 * 
 * @author czm
 *
 */
public class BizTaskBeanFactory {
	public static QueryBizTaskModel createBean(WorkFlowType type) {
		if (WorkFlowType.LOAN.equals(type)) {
			return createLoan();
		} else if (WorkFlowType.PROSTA.equals(type)) {
			return createProsta();
		}
		else {
			return null;
		}
	}

	private static QueryBizTaskModel createProsta() {
		QueryBizTaskModel queryModel = new QueryBizTaskModel();
		queryModel.setBizCls("org.ibase4j.vo.ProStatementVo");
		queryModel.setBizTableName("BIZ_INSPROCESS_STATEMENT");// 数据库名称.表名称
		// SQL 业务表与工作流表之间的关联关系字段
		StringBuffer sql = new StringBuffer();
		sql.append("t.PROCESS_DEF_ID='");
		sql.append("");
		sql.append("' ");
		sql.append("and t.PROCESS_INS_ID=bo.PIID ");
		//
		queryModel.setAssosiationFilter(sql.toString());
		//
		List<QueryBizField> bizFieldList = new ArrayList<QueryBizField>();
		QueryBizField idField = new QueryBizField("id_", "bizid", QueryBizField.STRING_TYPE);
		QueryBizField titleField = new QueryBizField("TITLE", "title", QueryBizField.STRING_TYPE);
		QueryBizField insprocessnameField = new QueryBizField("INS_PROCESS_NAME", "insprocessname", QueryBizField.STRING_TYPE);
		QueryBizField actprocessnameField = new QueryBizField("ACT_PROCESS_NAME", "actprocessname", QueryBizField.STRING_TYPE);
		QueryBizField createdField = new QueryBizField("CREATED_DATE", "createddate", QueryBizField.DATETIME_TYPE);
		bizFieldList.add(idField);
		bizFieldList.add(titleField);
		bizFieldList.add(insprocessnameField);
		bizFieldList.add(actprocessnameField);
		bizFieldList.add(createdField);
		queryModel.setQueryBizFields(bizFieldList);
		return queryModel;
	}


	protected static QueryBizTaskModel createLoan() {
		// TODO
		return null;
	}
	

	/**
	 * 功能：审核内容
	 * @param prostat
	 * @return
	 */
	public static CommentInfo getProstatCommentInfo(ProstatAuditVo prostat){
		CommentInfoVO info = new CommentInfoVO();
		info.setActivityName(prostat.getActivityName());
		info.setAdid(prostat.getAdid());
		info.setAiid(prostat.getAiid());
		info.setContent(prostat.getAuditOpinion());//审核意见
		info.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
		info.setLastUpdateDate(new java.sql.Timestamp(System.currentTimeMillis()));
		info.setPiid(prostat.getPiid());
		info.setSubject(prostat.getSubject());
		info.setSubmitFlag(prostat.isSubmit());//通过1，或不通过0
		info.setTaskId(prostat.getTaskId());
		info.setUserId(prostat.getUserId());
		info.setUserName(prostat.getUserName());
		return info;
	}
}
