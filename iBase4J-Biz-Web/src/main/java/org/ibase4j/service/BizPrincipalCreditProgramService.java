package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizPrincipalCreditProgram;
import org.ibase4j.provider.BizPrincipalCreditProgramProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xy
 * @date 2018/05/14
 */
@Service
public class BizPrincipalCreditProgramService extends BaseService<BizPrincipalCreditProgramProvider, BizPrincipalCreditProgram> {
	@Reference
	public void setProvider(BizPrincipalCreditProgramProvider provider) {
		this.provider = provider;
	}

	/**
	 * 功能：查询所有客户主体信息
	 * 
	 * @author xy
	 * @return 返回所有客户主体信息
	 */
	public List<BizPrincipalCreditProgram> getAllCheckPlan() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		// 查询所有状态为1(是)的检查计划配置
		List<BizPrincipalCreditProgram> principalCreditProgramList = provider.queryList(params);
		return principalCreditProgramList;
	}

	/**
	 * 功能：根据custNo查询客户下的所有授信信息
	 * 
	 * @author xy
	 * @return 返回客户下的所有授信信息
	 */
	public Page<?> getAllPrincipalCreditProgram(Map<String , Object> param) {
		// 通过custNo查询客户主体下所有的授信信息
		Page<BizPrincipalCreditProgram> query = provider.query(param);
		return query;
	}

	public void addBizPrincipalCreditPrograms(Map<String, Object> bizPrincipalCreditProgramMap) {
		for (Object object : bizPrincipalCreditProgramMap.values()) {
			BizPrincipalCreditProgram bizPrincipalCreditProgram = (BizPrincipalCreditProgram) object;
			bizPrincipalCreditProgram.setEnable(1);
			provider.update(bizPrincipalCreditProgram);
		}
	}
}
