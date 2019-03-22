package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizTemporarySave;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.BizDebtSummaryProvider;
import org.ibase4j.provider.BizTemporarySaveProvider;
import org.ibase4j.provider.ISysDeptProvider;
import org.ibase4j.provider.ISysUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：临时保存数据
 * 
 * @author xiaoshuqiuan
 * @version 1.0
 */
@Service
public class BizSchemeTemStorageService extends BaseService<BizTemporarySaveProvider, BizTemporarySave> {
	private static final Logger log = (Logger) LogManager.getLogger(BizSchemeTemStorageService.class);

	@Reference
	public void setProvider(BizTemporarySaveProvider provider) {
		this.provider = provider;
	}

	@Reference
	private ISysUserProvider sysUserProvider;
	@Reference
	private ISysDeptProvider sysDeptProvider;
	@Reference
	private BizDebtSummaryProvider bizDebtSummaryProvider;
	@Autowired
	private BizSchemeTemStorageService bizSchemeTemStorageService;
	/**
	 * 功能：暂存方案补录
	 *
	 * @param
	 * @return
	 */
	public boolean saveTemp(Map<String, Object> record) {
		log.info("开始暂存方案补录...");
		BizTemporarySave temp = new BizTemporarySave();
		Map<String, Object> params = new HashMap<String, Object>();
		Long userId = BizWebUtil.getCurrentUser();
		//Map<String, Object> loginMap = sysUserProvider.getRoleAndDepartment(userId);
		String debtCode = StringUtil.objectToString(record.get("debtCode"));
		// 设置参数
		SysUser user=sysUserProvider.queryById(userId);
		SysDept dept=sysDeptProvider.queryDeptByCode(user.getDeptCode());
		params.put("rolid", dept.getParentCode());
		//params.put("rolid", loginMap.get("roleId").toString());
		params.put("userid", userId.toString());
		//通过taskid判断是补录（1）还是修订暂存（2）,历史数据的修订（3）
		params.put("remark",record.get("remark"));
		params.put("bizcode", debtCode);
		params.put("deptCode", dept.getParentCode());
		//设置文件的id
		params.put("tempId", record.get("tempId"));
		//保存项目名称
		Map<String, Object> paramTem=(Map<String, Object>)record.get("debtMain");
		String projectName=paramTem.get("projectName").toString();
		if(projectName.length()!=0){
			params.put("projectName",projectName);
		}else {
			params.put("projectName",null);
		}
		//保存申请人
		if (org.apache.commons.lang3.StringUtils.isNotBlank(StringUtil.objectToString(paramTem.get("proposer")))) {
			String proposer = paramTem.get("proposer").toString();
			if (proposer.length() != 0) {
				params.put("taskid", proposer);
			} else {
				params.put("taskid", null);
			}
		}
		temp.setObj(record);
		// 执行保存
		boolean success = provider.saveSchemeTemporary(temp, params);
		log.info(success ? "暂存成功" : "暂存失败");

		return success;
	}




	/**
	 * 功能：查询用户所有暂存内容
	 * 
	 * @param grantModel
	 * @return
	 */
	public Page<?> getTempResult(Map<String, Object> params) {
		Long userId = BizWebUtil.getCurrentUser();
		SysUser sysUser=sysUserProvider.queryById(userId);
		SysDept sysDept=sysDeptProvider.queryDeptByCode(sysUser.getDeptCode());
		String deptCod=sysDept.getParentCode();
		log.debug("开始查询用户(" + userId + ")的暂存数据...");
		if(!deptCod.equals("1550000")){
			params.put("deptCod", deptCod);
			params.put("rolid", deptCod);
		}
//		params.put("remark", "2");
		Page<BizTemporarySave> tmPage=provider.query(params);
		List<BizTemporarySave>temList=tmPage.getRecords();
		for(BizTemporarySave bizTem:temList){
			//设置操作人员
			SysUser sysUser1=sysUserProvider.queryById(Long.valueOf(bizTem.getUserid()));
			//设置暂存需要展示的机构名称
			SysDept sysDept1=sysDeptProvider.queryDeptByCode(bizTem.getDeptCode());
			bizTem.setDeptName(sysDept1.getDeptName());
			bizTem.setUserName(sysUser1.getUserName());
			Object list = bizSchemeTemStorageService.getTempFile(bizTem.getId());
			bizTem.setObj(list);
		}
		tmPage.setRecords(temList);
		return tmPage;
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
	public boolean delTempDataAndFile(Map<String, Object> params) {
		log.info("开始删除暂存...");
		String debtCode=params.get("debtCode").toString();
		BizTemporarySave temp = new BizTemporarySave();
		temp.setBizcode(debtCode);
		BizTemporarySave temp1 =provider.selectOne(temp);
		if(temp1!=null){
			provider.delById(temp1.getId());
			log.info("删除暂存完成...");
		}
		return true;
	}
}
