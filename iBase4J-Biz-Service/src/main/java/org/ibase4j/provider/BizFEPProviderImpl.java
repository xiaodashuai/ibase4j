/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.mapper.BizFEPMapper;
import org.ibase4j.model.BizFEP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能： 收息收费表 
 * 日期：2018/7/6
 * @author czm
 */
@CacheConfig(cacheNames = "BizFEP")
@Service(interfaceClass = BizFEPProvider.class)
public class BizFEPProviderImpl extends BaseProviderImpl<BizFEP> implements BizFEPProvider {

    @Autowired
    private BizFEPMapper bizFEPMapper;

    @Override
    public List getBizFEPByINR(Map<String, Object> params) {
        List bizFEPS = bizFEPMapper.getBizFEPByINR(params);
        return bizFEPS;
    }

	@Override
	public int deleteByGrantCode(String grantId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("objType", BizContant.BIZ_TABLE_GRANT);
		params.put("objInr", grantId);
		return this.deleteByParams(params);
	}
    
    
}
