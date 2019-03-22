package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizMsg;
import org.ibase4j.provider.BizMsgProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author xy
 * @date 2018/05/09
 */
@Service
public class BizMsgService extends BaseService<BizMsgProvider, BizMsg> {
	@Reference
	public void setProvider(BizMsgProvider provider) {
		this.provider = provider;
	}
	/**
	 * 功能：查询所有短信发送记录
	 * @author xy
	 * @return 返回所有有效的短息发送记录
	 */
	public List<BizMsg> getAllMsg(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		//查询所有状态为1(是)的检查计划配置
		List<BizMsg> msgList = provider.queryList(params);
		return msgList;
	}

	public void create(BizMsg bizMsg) {
		bizMsg.setEnable(1);
		provider.update(bizMsg);
	}
}

