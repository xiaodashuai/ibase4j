package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.mapper.BizCreditLinesMapper;
import org.ibase4j.model.BizCreditLines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaoYu
 * @date 2018/06/27
 */
@CacheConfig(cacheNames = "bizCreditLines")
@Service(interfaceClass = BizCreditLinesProvider.class)
public class BizCreditLinesProviderImpl extends BaseProviderImpl<BizCreditLines> implements BizCreditLinesProvider {
    @Autowired
    private BizCreditLinesMapper bizCreditLinesMapper;

	@Override
	public int deleteByGrantId(String objinr,String debtCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("objinr", objinr);
		params.put("objtyp", BizContant.BIZ_TABLE_GRANT);
		params.put("debtCode", debtCode);
		return this.deleteByParams(params);
	}
	
}
