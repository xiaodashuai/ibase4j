/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.model.BizCBE;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：发生额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@CacheConfig(cacheNames = "BizCBE")
@Service(interfaceClass = BizCBEProvider.class)
public class BizCBEProviderImpl extends BaseProviderImpl<BizCBE> implements BizCBEProvider {

	@Override
	public int deleteByGrant(Long grantId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("objType", BizContant.BIZ_TABLE_GRANT);
		params.put("objInr", grantId);
		
		return this.deleteByParams(params);
	}

}
