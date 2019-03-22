/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.provider.*;
import org.ibase4j.vo.BizDebtGrantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：债项发放申请管理
 * 
 * @author czm
 * @date 2018/7/17
 * @version 1.0
 */
@Service
public class BizGrantService extends BaseService<BizDebtGrantProvider, BizDebtGrant> {
	private static final Logger log = LogManager.getLogger(BizGrantService.class);

	@Reference
	public void setProvider(BizDebtGrantProvider provider) {
		this.provider = provider;
	}

	@Reference
	private ISysUserProvider sysUserProvider;
//	@Reference
//	private BizProStatementProvider bizProStatementProvider;
	@Reference
	private ISysUserRoleProvider sysUserRoleProvider;
	@Reference
	private ISysDeptProvider sysDeptProvider;
	@Reference
	private ISysCurrencyProvider iSysCurrencyProvider;
	
	/**
	 * 功能 ：根据id主键查询发放信息，币种显示币种英文字母+汉字
	 * @param id
	 * @return
	 */
	public BizDebtGrant getById(Long id){
		BizDebtGrant model = this.queryById(id);
		String cnCode = model.getCurrency();
		SysCurrency currency = iSysCurrencyProvider.getByCode(cnCode);
		if(currency!=null){
			model.setCurrencyName(currency.getCodeName());
		}
		return model;
	}
	/**
	 * 功能：保存废弃
	 *
	 * @param record
	 * @return
	 */
	public BizDebtGrant saveDiscard(Map<String, Object> record) {
		log.info("开始执行债项发放废弃...");
		Long userId = BizWebUtil.getCurrentUser();
		SysUser loginUser = sysUserProvider.queryById(userId);
		Map map = new HashMap();
		map.put("userId", userId);
		List<SysUserRole> list = sysUserRoleProvider.selectOneSysUserRole(map);
		SysDept dept = sysDeptProvider.queryDeptByCode(loginUser.getDeptCode());
		// 加入操作人
		record.put("agent", userId);// 经办人
		record.put("agencies", dept.getParentCode());// 经办机构
		record.put("createBy", userId);// 经办人
		record.put("deptCode", dept.getParentCode());// 经办机构
		record.put("list", list);
		BizDebtGrant result = provider.saveDiscard(record);
		return result;
	}

	/**
	 * 功能：删除发放
	 *
	 * @param record
	 * @return
	 */
	public BizDebtGrant saveDisabled(Map<String, Object> record) {
		log.info("开始执行发放删除...");
		Long userId = BizWebUtil.getCurrentUser();
		Long id = StringUtil.objectToLong(record.get("grantId"));
		BizDebtGrant grant = provider.queryById(id);
		String originalCode = grant.getOriginalGrantCode();
		Integer grantStatus = grant.getGrantStatus();
		// 如果是变更流程，且状态是驳回状态的删除。则需要恢复老数据的状态为已发放
		Integer back = Integer.valueOf(BizStatus.GRANDOWN);
		if (StringUtils.isNotBlank(originalCode) && back.equals(grantStatus)) {
			log.info("把老数据(" + originalCode + ")状态恢复成已发放...");
			provider.updateDebtGrantStatus(originalCode, BizStatus.GRANALIS);
			log.info("恢复成功!");
		}
		grant.setEnable(BizContant.ENABLE_NO);
		grant.setUpdateBy(userId);
		grant.setUpdateTime(new Date());
		BizDebtGrant result = provider.update(grant);
		log.info("删除成功!");
		return result;
	}

	/**
	 * 功能：获取废弃概要信息
	 *
	 * @param params
	 * @return
	 */
	public BizDebtGrant getGrantInfo(Map<String, Object> params) {
		BizDebtGrant bizDebtGrant = provider.queryList(params).get(0);

		BizDebtGrantVo bizDebtGrantVo = new BizDebtGrantVo();
		//复制对象 此方法（copyProperties）复制内有list或者map的时候 会有问题
		BeanUtils.copyProperties(bizDebtGrant, bizDebtGrantVo);
		//查询主币种
		Map mapMpc = new HashMap();
		mapMpc.put("monCode",bizDebtGrant.getCurrency());
		List<SysCurrency> sysCurrency = iSysCurrencyProvider.queryList(mapMpc);
		SysCurrency sysCurrency1 = sysCurrency.get(0);
		String codeName = sysCurrency1.getCodeName();
		//设置主币种
		bizDebtGrantVo.setMpcName(codeName);
		return bizDebtGrantVo;
	}

	/**
	 * 功能：仅仅修改发放审核数据而不启动流程
	 * 
	 * @param record
	 * @return
	 */
	public BizDebtGrant updateNoFw(Map<String, Object> record) {
		log.info("开始修改保存债项发放申请...");
		Long userId = BizWebUtil.getCurrentUser();
		SysUser loginUser = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(loginUser.getDeptCode());
		// 加入操作人
		record.put("agent", userId);// 经办人
		record.put("agencies", dept.getParentCode());// 经办机构
		record.put("createBy", userId);// 经办人
		record.put("deptCode", dept.getParentCode());// 经办机构
		// 然后调用修改方法
		BizDebtGrant result = provider.updateNofw(record);
		return result;
	}

	/**
	 * 功能：修改重新提交发放申请内容并启动审核流程
	 * 
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public BizDebtGrant update(Map<String, Object> record) throws Exception {
		log.info("开始重新提交债项发放申请...");
		Long userId = BizWebUtil.getCurrentUser();
		SysUser loginUser = sysUserProvider.queryById(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<SysUserRole> list = sysUserRoleProvider.selectOneSysUserRole(map);
		SysDept dept = sysDeptProvider.queryDeptByCode(loginUser.getDeptCode());
		// 加入操作人
		record.put("agent", userId);// 经办人
		record.put("agencies", dept.getParentCode());// 经办机构
		record.put("createBy", userId);// 经办人
		record.put("deptCode", dept.getParentCode());// 经办机构
		record.put("list", list);
		BizDebtGrant result = provider.update(record);
		return result;
	}

	/**
	 * 功能：保存发放申请内容
	 * 
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public BizDebtGrant save(Map<String, Object> record) throws Exception {
		log.info("开始保存债项发放申请...");
		Long userId = BizWebUtil.getCurrentUser();
		SysUser loginUser = sysUserProvider.queryById(userId);
		Map map = new HashMap();
		map.put("userId", userId);
		List<SysUserRole> list = sysUserRoleProvider.selectOneSysUserRole(map);
		SysDept dept = sysDeptProvider.queryDeptByCode(loginUser.getDeptCode());
		// 加入操作人
		record.put("agent", userId);// 经办人
		record.put("agencies", dept.getParentCode());// 经办机构
		record.put("createBy", userId);// 经办人
		record.put("deptCode", dept.getParentCode());// 经办机构
		record.put("list", list);
		BizDebtGrant result = provider.save(record);
		return result;
	}

	/**
	 * 功能：查询债项发放
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEditGrant(Map<String, Object> param) {
		log.info("开始编辑债项发放申请...");
		Long id = StringUtil.objectToLong(param.get("grantId"));
		BizDebtGrant model = provider.queryById(id);
		String debtCode = model.getDebtCode();
		String grantCode = model.getGrantCode();
		Map<String, Object> record = provider.getEditGrant(grantCode, debtCode);
		// 查询经办人和机构
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		record.put("agent", user.getUserName());// 经办人
		record.put("agencies", dept.getDeptName());// 经办机构
		record.put("id", id);//回写发放主键id
		return record;
	}
	
	/**
	 * 功能：查询债项发放变更（发放编码会进行覆盖）
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEditChangeGrant(Map<String, Object> param) {
		log.info("开始编辑债项发放变更申请...");
		Long id = StringUtil.objectToLong(param.get("grantId"));
		BizDebtGrant model = provider.queryById(id);
		String debtCode = model.getDebtCode();
		String grantCode = model.getGrantCode();
		Map<String, Object> record = provider.getEditGrant(grantCode, debtCode);
		// 查询经办人和机构
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		record.put("agent", user.getUserName());// 经办人
		record.put("agencies", dept.getDeptName());// 经办机构
		//变更流程将使用新生成一个发放编码并与旧编码关联
		record.put("originalGrantCode", grantCode);//原编码成为旧编码
		String newCode = "";
		if(record.get("NewCode")!=null){
			newCode = record.get("NewCode").toString();
		}
		record.put("grantCode",newCode);//新编码成为发放编码
		return record;
	}
}
