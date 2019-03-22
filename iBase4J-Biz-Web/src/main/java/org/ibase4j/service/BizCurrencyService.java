/**
 * 
 */
package org.ibase4j.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.model.NameValuePair;
import org.ibase4j.model.SysCurrency;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysCurrencyProvider;
import org.ibase4j.provider.ISysDeptProvider;
import org.ibase4j.provider.ISysUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 描述：前台使用货币组件
 * 
 * @author czm
 * @date 2018/7/13
 * @version 1.0
 */
@Service
public class BizCurrencyService extends BaseService<ISysCurrencyProvider, SysCurrency> {
	@Reference
	public void setProvider(ISysCurrencyProvider provider) {
		this.provider = provider;
	}

	@Reference
	private ISysDeptProvider deptProvider;
	@Autowired
	private ISysUserProvider sysUserProvider;
	@Reference
	private ISysDeptProvider sysDeptProvider;

	/**
	 * 功能：获取世界单位货币
	 * 
	 * @param params
	 * @return
	 */
	public List<NameValuePair> getCurrencyPair(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(code)) {
			params.put("keyword", code);
		}
		List<SysCurrency> currencyList = queryList(params);
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for (SysCurrency node : currencyList) {
			NameValuePair pair = new NameValuePair();
			pair.setName(node.getCodeName());
			pair.setValue(node.getMonCode());
			result.add(pair);
		}
		return result;
	}

	/**
	 * 功能：获取世界单位货币
	 * 
	 * @author xiaoshuqiuan
	 * @return
	 */
	public List<SysCurrency> getCurrency(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(code)) {
			params.put("keyword", code);
		}
		List<SysCurrency> currencyList = queryList(params);
		return currencyList;
	}

	/**
	 * 功能：根据经办人所在的机构，生成机构下拉框，其中贸金部的人员可以查看所有的数据
	 * @return
	 */
	public List<NameValuePair> getDeptList() {
		// 获取登录人编号
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = sysUserProvider.queryById(userId);
		SysDept dept = sysDeptProvider.queryDeptByCode(user.getDeptCode());
		Map<String, Object> params = new HashMap<String, Object>();
		//如果是贸金部的经办，查询所有机构数据，否则查询自己分行的数据
		if(BizContant.DEPT_MJ_CODE.equals(dept.getParentCode())){
			params.put("parentCode", BizContant.DEPT_ROOT_CODE);
			params.put("type", BizContant.DEPT_TYPE_CODE);
			params.put("enable", BizContant.ENABLE_YES);
			params.put("leaf", BizContant.ENABLE_YES);
		}else{
			params.put("code", dept.getParentCode());
		}
		Page<Long> page = new Page<Long>(1, 1000, "SORT_NO");
		page.setAsc(true);
		List<Long> ids = deptProvider.getDeptPage(page,params);
		List<SysDept> deptList = deptProvider.queryList(ids);
		List<NameValuePair> pairList = InstanceUtil.newArrayList();
		//转换集合
		for (SysDept item : deptList) {
			NameValuePair pair = new NameValuePair();
			pair.setName(item.getDeptName());
			pair.setValue(item.getCode());
			//
			pairList.add(pair);
		}
		return pairList;
	}

}
