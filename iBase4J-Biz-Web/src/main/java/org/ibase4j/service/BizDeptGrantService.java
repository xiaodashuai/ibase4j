package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.ExportExcelUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lwh
 * @version 2018年7月24日 债项发放申请
 */
@Service
public class BizDeptGrantService extends BaseService<BizDebtGrantProvider, BizDebtGrant> {
	private static final Logger log = (Logger) LogManager.getLogger(BizDeptGrantService.class);

	@Reference
	public void setProvider(BizDebtGrantProvider provider) {
		this.provider = provider;
	}
	@Reference
	private ISysCurrencyProvider currencyProvider;
	@Reference
	private BizDebtSummaryProvider debtSummaryProvider;
	@Reference
	private BizGuaranteeInfoProvider guaranteeInfoProvider;
	@Reference
	private BizGuaranteeTypeProvider guaranteeTypeProvider;
	@Reference
	private IBizProductTypeProvider productTypeProvider;
	@Autowired
	private ISysUserProvider sysUserProvider;
	@Reference
	private BizTRNProvider trnProvider;
	@Reference
	private ISysDeptProvider sysDeptProvider;
	@Reference
	private BizProStatementProvider bizProStatementProvider;
	
	/**
	 * 功能：导出迁移的数据
	 * 
	 * @param params
	 * @return
	 * @throws IOException 
	 */
	public void exportData(Map<String, Object> params, HttpServletResponse response) {
		// 分行只查询自己行的数据，如果是贸金部则可查询所有
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		// 经办机构编码
		if (BizContant.DEPT_MJ_CODE.equals(dept.getParentCode())) {
			// 贸金部查询所有迁移的数据
			String deptCode = StringUtil.objectToString(params.get("deptCode"));
			if (StringUtils.isNotBlank(deptCode)) {
				params.put("deptCode", deptCode);
			}
		} else {
			params.put("deptCode", dept.getParentCode());
		}
		// 只查询数据迁移的发放申请记录，这个标记不为空即可（可以是任意值)
		params.put("historyState", "1");
		List<BizDebtGrant> result = provider.queryList(params);
		List<NameValuePair> titles = new ArrayList<NameValuePair>();
		NameValuePair nvp = new NameValuePair("grantCode","发放审核编号");
		NameValuePair nvp2 = new NameValuePair("businessTypeName","产品名称");
		NameValuePair nvp3 = new NameValuePair("proposer","申请人名称");
		NameValuePair nvp4 = new NameValuePair("grantAmt","金额");
		NameValuePair nvp5 = new NameValuePair("currency","币种");
		NameValuePair nvp6 = new NameValuePair("scopeBusinPeriod","业务期限");
		NameValuePair nvp7 = new NameValuePair("historyState","状态");
		NameValuePair nvp8 = new NameValuePair("sendDate","发放日期");
		titles.add(nvp);
		titles.add(nvp2);
		titles.add(nvp3);
		titles.add(nvp4);
		titles.add(nvp5);
		titles.add(nvp6);
		titles.add(nvp7);
		titles.add(nvp8);
		//查询数据集并转换成mapList
		List<Map<String,Object>> eList = new ArrayList<Map<String,Object>>();
		for(BizDebtGrant item : result){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("grantCode", item.getGrantCode());
			BizProductTypes type = productTypeProvider.getByCode(item.getBusinessTypes());
			if (type != null) {
				map.put("businessTypeName", type.getName());
			}
			map.put("proposer", item.getProposer());
			map.put("grantAmt", item.getGrantAmt());
			map.put("currency", item.getCurrency());
			map.put("scopeBusinPeriod", item.getScopeBusinPeriod());
			map.put("historyState", item.getHistoryState());
			map.put("sendDate", item.getSendDate());
			eList.add(map);
		}
		String fileName = "grant_history.xls";
		try {
			ExportExcelUtil.expExcel(fileName, titles, eList, response);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("导出错误:"+e.getMessage());
		}
	}
	
	// 测试多表连查
//	public Page<?> test(Map<String, Object> params) {
//		// 只查询自己创建的发放信息
//		Long userId = BizWebUtil.getCurrentUser();
//		SysUser user = sysUserProvider.queryById(userId);
//		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
//		// 经办机构编码
//		params.put("objtype", BizContant.BIZ_TABLE_GRANT);// 业务表名称
//		Page<Map<String, Object>> page = trnProvider.queryMapPage(params);
//		return page;
//	}

	/**
	 * 功能：与我相关的发放列表查询
	 * 
	 * @param params
	 * @return
	 */
	public Page<?> getList(Map<String, Object> params) {
		// 只查询自己创建的发放信息
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		// 经办机构编码
		params.put("bchkeyinr", dept.getParentCode());
		params.put("objtype", BizContant.BIZ_TABLE_GRANT);// 业务表名称
		params.put("orderBy", "INIDATTIM");
		Page<BizTRN> page = trnProvider.query(params);
		//
		List<BizTRN> result = page.getRecords();
		for (BizTRN model : result) {
			String code = model.getBusinessTypes();
			BizProductTypes type = productTypeProvider.getByCode(code);
			if (type != null) {
				model.setBusinessTypeName(type.getName());
			}
		}
		return page;
	}

	/**
	 * 功能：查询发放审核的数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<?> queryResultList(Map<String, Object> params) {
		// 只查询自己创建的发放信息
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		// 经办机构编码
		if (BizContant.DEPT_MJ_CODE.equals(dept.getParentCode())) {
			// 贸金部查询所有迁移的数据
			String deptCode = StringUtil.objectToString(params.get("deptCode"));
			if (StringUtils.isNotBlank(deptCode)) {
				params.put("deptCode", deptCode);
			}
		} else {
			params.put("deptCode", dept.getParentCode());
		}
		params.put("enable", BizContant.ENABLE_YES);
		params.put("notStatus", BizContant.ENABLE_YES);
		Page<BizDebtGrant> page =  provider.query(params);
		for (BizDebtGrant model : page.getRecords()) {
			if(model.getCreateBy()!=null){
				SysUser user2 = sysUserProvider.queryById(model.getCreateBy());
				model.setCreateByName(user2.getUserName());
			}
			if(StringUtils.isNotBlank(model.getInstitutionCode())){
				SysDept dept2 = sysDeptProvider.queryDeptByCode(model.getInstitutionCode());
				model.setDeptName(dept2.getDeptName());
			}
			String currencyCode = model.getCurrency();
			if(StringUtils.isNotBlank(currencyCode)){
				SysCurrency currency = currencyProvider.getByCode(currencyCode);
				if(currency!=null){
					model.setCurrencyName(currency.getCodeName());
				}
			}
		}
		return page;
	}

	/**
	 * 功能：查询本行所有迁移的数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<?> queryMigrList(Map<String, Object> params) {
		// 分行只查询自己行的数据，如果是贸金部则可查询所有
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		// 经办机构编码
		if (BizContant.DEPT_MJ_CODE.equals(dept.getParentCode())) {
			// 贸金部查询所有迁移的数据
			String deptCode = StringUtil.objectToString(params.get("deptCode"));
			if (StringUtils.isNotBlank(deptCode)) {
				params.put("deptCode", deptCode);
			}
		} else {
			params.put("deptCode", dept.getParentCode());
		}
		// 只查询数据迁移的发放申请记录，这个标记不为空即可（可以是任意值)
		params.put("historyState", "1");
		// 查询所有迁移的数据
		Page<BizDebtGrant> page = provider.queryByHundred(params);
		for (BizDebtGrant model : page.getRecords()) {
			if(model.getCreateBy()!=null){
				SysUser user2 = sysUserProvider.queryById(model.getCreateBy());
				model.setRemark(user2.getUserName());
			}
			String code = model.getBusinessTypes();
			BizProductTypes type = productTypeProvider.getByCode(code);
			if (type != null) {
				model.setBusinessTypeName(type.getName());
			}
			//保持与方案的申请人同步
			BizDebtSummary queryModel = new BizDebtSummary();
			queryModel.setDebtCode(model.getDebtCode());
			queryModel = debtSummaryProvider.selectOne(queryModel);
			if(queryModel!=null){
				model.setProposer(queryModel.getProposer());
			}
		}
		return page;
	}

	/**
	 * @Description: 查询满足债项放款的债项方案
	 * @Param: [param]
	 * @return: com.baomidou.mybatisplus.plugins.Page
	 *          <org.ibase4j.model.BizDebtGrant>
	 */
	public Page getMakeLoansDebts(Map<String, Object> param) {
		Page makeLoansDebts = provider.getMakeLoansDebts(param);
		return makeLoansDebts;
	}

	/**
	 * @Description: 根据债项发放id查询债项信息
	 * @Param: [param]
	 * @return: org.ibase4j.model.BizDebtGrant
	 */
	public BizDebtGrant getMakeLoansDebtInfo(Map<String, Object> param) {
		String grantCode = StringUtil.objectToString(param.get("grantCode"));
		// 此处为模拟数据
		BizDebtGrant bizDebtGrant = new BizDebtGrant();
		bizDebtGrant.setGrantCode(grantCode);
		return bizDebtGrant;
	}

	public void updateStatus(Map<String, Object> param) {
		// 根据流程实例id查询债项发放编码
		String piid = StringUtil.objectToString(param.get("piid"));
		String userId = StringUtil.objectToString(param.get("userId"));
		Integer grantStatus = Integer.valueOf(param.get("grantStatus").toString());
		String grantCode = bizProStatementProvider.getProcessInsBizId(userId, piid);
		provider.updateDebtGrantStatus(grantCode, grantStatus);
	}
	
	/**
	 * 功能：根据方案编码查询方案信息
	 * @param debtCode
	 * @return
	 */
	public BizDebtSummary getByDebtCode(String debtCode){
		BizDebtSummary query = new BizDebtSummary();
		query.setDebtCode(debtCode);
		BizDebtSummary model = debtSummaryProvider.selectOne(query);
		return model;
	}

	/**
	 * 功能：查询方案共选择了多少币种
	 * 
	 * @param debtCode
	 * @return
	 */
	public List<NameValuePair> getCurrencyPair(String debtCode) {
		log.debug("开始查询债项编码（" + debtCode + "）选择的币种...");
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		// 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(debtCode)) {
			params.put("debtCode", debtCode);
		}
		List<BizDebtSummary> debtModels = debtSummaryProvider.queryList(params);
		if (debtModels != null && debtModels.size() > 0) {
			BizDebtSummary model = debtModels.get(0);
			// 方案辅助币种(0无1其他可选币种2等值其他币种)
			String aCurrrency = model.getaCurrrency();
			// 等值其他币种：债项发放时，不限制发放币种
			if (BizContant.A_CURRENCY.equals(aCurrrency)) {
				params.clear();// 清空查询条件
				List<SysCurrency> currencyList = currencyProvider.queryList(params);
				for (SysCurrency node : currencyList) {
					NameValuePair pair = new NameValuePair();
					pair.setName(node.getCodeName());
					pair.setValue(node.getMonCode());
					result.add(pair);
				}
			} else if (BizContant.A_CURRENCY0.equals(aCurrrency)) {
				// 只有主币种
				NameValuePair mPair = new NameValuePair();
				String code = model.getMpc();
				SysCurrency currency = currencyProvider.getByCode(code);
				mPair.setName(currency.getCodeName());
				mPair.setValue(code);
				result.add(mPair);
			} else {
				// 其他可选币种：债项发放时，需要在已选择的其他可选币种中进行币种选择。
				NameValuePair mPair = new NameValuePair();// 主币种
				String code = model.getMpc();
				SysCurrency currency = currencyProvider.getByCode(code);
				mPair.setName(currency.getCodeName());
				mPair.setValue(code);
				result.add(mPair);
				// 其他可选币种
				String oc = model.getOc();// 其他可选币种
				if (StringUtils.isNotBlank(oc)) {
					String[] curStrs = oc.split(",");
					log.info("其他可选币种共有" + curStrs.length + "个");
					for (String cur : curStrs) {
						SysCurrency otherCurrency = currencyProvider.getByCode(cur);
						NameValuePair oPair = new NameValuePair();
						oPair.setName(otherCurrency.getCodeName());
						oPair.setValue(cur);
						result.add(oPair);
					}
				}
			}

		}
		return result;
	}
	
	/**
	 * <strong>注意：发放条件专用（其他人不得更改）</strong><br/>
	 * 功能：根据债项编码查询担保类型<br/>
	 * 作者：czm
	 * @param debtCode
	 * @return
	 */
	public List<NameValuePair> getGuaranteeInfoList(String debtCode) {
		// 查询债项保存的担保类型
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("debtCode", debtCode);
		List<BizGuaranteeInfo> infoList = guaranteeInfoProvider.queryList(params);
		// 查询所有担保类型
		params.clear(); 
		params.put("enable", BizContant.ENABLE_YES);
		params.put("type", BizContant.BIZ_BNS_INFO_TYPE);
		List<BizGuaranteeType> typeList = guaranteeTypeProvider.queryList(params);
		//使用Set保证唯一不重复，首先必须让NameValuePair重写equals和hashCode()方法
		Set<NameValuePair> sets = new HashSet<NameValuePair>();
		for (BizGuaranteeInfo bean : infoList) {
			NameValuePair oPair = new NameValuePair();
			String code = StringUtil.objectToString(bean.getTypePoint());
			oPair.setName(StringUtil.replaceNullToEmpty(getNameByCode(code, typeList)));
			oPair.setValue(code);
			sets.add(oPair);
		}
		//Set转换成List
		List<NameValuePair> result = new ArrayList<NameValuePair>(sets);
		return result;
	}

	/**
	 * 功能：根据债项编码查询担保类型
	 * 
	 * @param debtCode
	 * @return
	 */
	public List<NameValuePair> getGuaranteeInfoType(String debtCode) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		// 查询债项保存的担保类型
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("debtCode", debtCode);
		List<BizGuaranteeInfo> infoList = guaranteeInfoProvider.queryList(params);
		List<BizGuaranteeInfo> infoListSel = new ArrayList<>();
		for (BizGuaranteeInfo bizGuaranteeInfo : infoList) {
			Boolean flag = true;
			for (BizGuaranteeInfo bizGuaranteeInfo2 : infoListSel) {
				if (bizGuaranteeInfo2.getTypePoint().equals(bizGuaranteeInfo.getTypePoint())) {
					flag = false;
				}
			}
			if (flag) {
				infoListSel.add(bizGuaranteeInfo);
			}
		}
		// 查询所有担保类型
		params.clear();
		params.put("enable", BizContant.ENABLE_YES);
		params.put("type", BizContant.BIZ_BNS_INFO_TYPE);
		List<BizGuaranteeType> typeList = guaranteeTypeProvider.queryList(params);
		for (BizGuaranteeInfo bean : infoListSel) {
			NameValuePair oPair = new NameValuePair();
			String code = StringUtil.objectToString(bean.getTypePoint());
			oPair.setName(getNameByCode(code, typeList));
			oPair.setValue(code);
			result.add(oPair);
		}

		return result;
	}

	/**
	 * 功能：根据担保类型编码查询（方案保存的担保合同类型）
	 * 
	 * @param debtCode
	 * @param parentCode
	 * @return
	 */
	public List<NameValuePair> getGuaranteeInfoByParentCode(String debtCode, String parentCode) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		// 查询所有担保类型
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", BizContant.ENABLE_YES);
		params.put("type", BizContant.BIZ_BNS_INFO_TYPE);
		params.put("parentCode", parentCode);
		List<BizGuaranteeType> typeList = guaranteeTypeProvider.queryList(params);
		// 查询债项保存的担保类型
		params.clear();
		params.put("debtCode", debtCode);// 方案编码
		params.put("typePoint", parentCode);// 担保类型编码
		List<BizGuaranteeInfo> infoList = guaranteeInfoProvider.queryList(params);
		for (BizGuaranteeInfo bean : infoList) {
			String type = bean.getTypePoint();
			NameValuePair oPair = new NameValuePair();
			String code = StringUtil.objectToString(bean.getGuaranteeContractType());
			long id = bean.getId();
			String wcNo = bean.getWarrantyContractNumber();
			//额度类型名称
			String edTypeName = getNameByCode(code, typeList);
			//如果是其他担保类型(直接拼接)
			if(BizContant.GUANTEE_TYPE_OTHER.equals(type)){
				edTypeName = code;
			}
			String name = StringUtil.replaceNullToEmpty(edTypeName)  + "-" + bean.getGuarantor() + "-" + StringUtil.replaceNullToEmpty(wcNo);
			oPair.setName(name);
			oPair.setValue(String.valueOf(id));
			result.add(oPair);
		}

		return result;
	}

	/**
	 * 功能：在担保类型集合中查找名称
	 * 
	 * @param code
	 * @param typeList
	 * @return
	 */
	private String getNameByCode(String code, List<BizGuaranteeType> typeList) {
		for (BizGuaranteeType type : typeList) {
			if (type.getCode().equals(code)) {
				return type.getName();
			}
		}
		return null;
	}
}
