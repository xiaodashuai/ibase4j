package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizEmail;
import org.ibase4j.provider.BizEmailProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author xy
 * @date 2018/05/25
 */
@Service
public class BizEmailService extends BaseService<BizEmailProvider, BizEmail> {
	@Reference
	public void setProvider(BizEmailProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * 功能：查询所有邮件发送记录
	 * @author xy
	 * @return 返回所有有效的邮件发送记录
	 */
	public List<BizEmail> getAllEmail(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		//查询所有状态为1(是)的检查计划配置
		List<BizEmail> emailList = provider.queryList(params);
		return emailList;
	}

	public void create(BizEmail bizEmail) {
		bizEmail.setEnable(1);
		provider.update(bizEmail);
	}
}
