package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.provider.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：临时保存数据
 * 
 * @author czm
 * @version 1.0
 */
@Service
public class BizTempSaveService extends BaseService<BizTemporarySaveProvider, BizTemporarySave> {
	private static final Logger log = (Logger) LogManager.getLogger(BizTempSaveService.class);

	@Reference
	public void setProvider(BizTemporarySaveProvider provider) {
		this.provider = provider;
	}

	@Reference
	private ISysUserProvider sysUserProvider;
	
	@Reference
	private BizDebtGrantProvider grantProvider;

	@Reference
	private BizTemporarySaveProvider bizTemporarySaveProvider;

	@Reference
	private ISysUserRoleProvider sysUserRoleProvider;

	@Reference
	private BizDebtSummaryProvider bizDebtSummaryProvider;

	@Reference
	private BizSingleProductRuleProvider bizSingleProductRuleProvider;

	@Reference
	private IBizProductTypeProvider bizProductTypeProvider;

	@Reference
	private ISysDeptProvider sysDeptProvider;
	
	/**
	 * 功能：查询发放有没有暂存的记录
	 * @param bizCode 发放编码
	 * @return 返回暂存的记录数量，如果不存在返回零
	 */
	public int getTempCountByBizCode(String bizCode){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("bizcode", bizCode);
		List<BizTemporarySave> result = provider.queryList(param);
		if(result!=null && result.size()>0){
			return result.size();
		}else{
			return 0;
		}
	}
	
	/**
	 * 功能：模糊查询发放有没有暂存的记录
	 * @param bizCode 发放编码
	 * @return 返回暂存的记录数量，如果不存在返回零
	 */
	public int getTempCountLikeBizCode(String bizCode){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("schemeNum", StringUtils.substring(bizCode, 0, 16));
		List<BizTemporarySave> result = provider.queryList(param);
		if(result!=null && result.size()>0){
			return result.size();
		}else{
			return 0;
		}
	}
	
	/**
	 * 功能：查询用户所有暂存内容
	 * 
	 * @param record
	 * @return
	 */
	public Page<BizTemporarySave> getTempResult(Map<String, Object> params) {
		Long userId = BizWebUtil.getCurrentUser();
		log.debug("开始查询用户(" + userId + ")的暂存数据...");
		SysUser sysUser = sysUserProvider.queryById(userId);
		String taskId = StringUtil.objectToString(params.get("taskId"));
		SysDept dept = sysDeptProvider.queryDeptByCode(sysUser.getDeptCode());
		//查询条件
		params.put("taskid", taskId);//业务类型
		params.put("excRemark","2");//排除掉2的业务就是发放审核使用的暂存
		// params.put("userid", String.valueOf(userId));//用户
		// 经办机构编码
		if (BizContant.DEPT_MJ_CODE.equals(dept.getParentCode())) {
			// 贸金部查询所有迁移的数据
			String deptCode = StringUtil.objectToString(params.get("deptCode"));
			if (StringUtils.isNotBlank(deptCode)) {
				params.put("deptCode", deptCode);//机构编码
			}
		} else {
			params.put("deptCode", dept.getParentCode());//机构编码
		}
		// 按照创建时间倒序
		params.put("orderBy", "CREATE_TIME");
		Page<BizTemporarySave> page = provider.query(params);
		List<BizTemporarySave> tmpList = page.getRecords();
		// 通过解析文件获取项目名称等属性
		for (BizTemporarySave bizTem : tmpList) {
			SysUser sysUser2 = sysUserProvider.queryById(Long.valueOf(bizTem.getUserid()));
			bizTem.setUserName(sysUser2.getUserName());
			Object list = bizTemporarySaveProvider.getById(bizTem.getId());
			SysDept dept2 = sysDeptProvider.queryDeptByCode(bizTem.getDeptCode());
			if (dept2 != null) {
				bizTem.setDeptCode(dept2.getDeptName());
			}
			bizTem.setObj(list);
		}
		page.setRecords(tmpList);
		return page;
	}

	/**
	 * 功能：暂存发放申请内容
	 * 
	 * @param record
	 * @return
	 */
	public boolean saveTemp(Map<String, Object> record) {
		log.info("开始暂存发放审请...");
		BizTemporarySave temp = new BizTemporarySave();
		Map<String, Object> params = new HashMap<String, Object>();
		Long userId = BizWebUtil.getCurrentUser();
		SysUser sysUser = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(sysUser.getDeptCode());
		Map<String, Object> loginMap = sysUserProvider.getRoleAndDepartment(userId);
		String grantCode = StringUtil.objectToString(record.get("grantCode"));
		String taskId = StringUtil.objectToString(record.get("taskid")); 
		String debtCode = grantCode.substring(0,16);//截取前16位是方案编号
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("debtCode",debtCode);
		List<BizDebtSummary> debtSummaryList = bizDebtSummaryProvider.queryList(queryMap);
		//设置方案的申请人就是发放的申请人
		if(CollectionUtils.isNotEmpty(debtSummaryList)){
			params.put("remark",debtSummaryList.get(0).getProposer());
		}
		// 设置参数
		params.put("rolid", loginMap.get("roleId").toString());
		params.put("userid", userId.toString());
		params.put("taskid", taskId);
		params.put("bizcode", grantCode);
		params.put("debtCode", debtCode);
		params.put("deptCode",dept.getParentCode());
		//设置查询条件
		temp.setDeptCode(dept.getCode());
		temp.setId(StringUtil.objectToLong(record.get("tempId")));
		temp.setObj(record);
		// 执行保存
		boolean success = provider.saveTemporary(temp, params);
		log.info(success ? "暂存成功" : "暂存失败");

		return success;
	}

	/**
	 * 功能：通过id查询暂存的对象
	 * 
	 * @param id
	 * @return
	 */
	public Object getTempFile(Long id) {
		return provider.getById(id);
	}

	/**
	 * 功能：删除暂存对象，同时删除暂存的文件
	 * 
	 * @param id
	 * @return
	 */
	public boolean delTempDataAndFile(Long id) {
		log.info("开始删除暂存...");
		// 执行保存
		boolean success = provider.delById(id);
		log.info(success ? "暂存成功" : "暂存失败");

		return success;
	}
}
