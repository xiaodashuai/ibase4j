/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizFECMapper;
import org.ibase4j.model.BizFEC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：利率表
 * 日期：2018/7/6
 * @author czm 
 * @version 1.0
 */
@CacheConfig(cacheNames = "BizFEC")
@Service(interfaceClass = BizFECProvider.class)
public class BizFECProviderImpl extends BaseProviderImpl<BizFEC> implements BizFECProvider {

    @Autowired
    private BizFECMapper bizFECMapper;

    @Override
    public List<BizFEC> getBizFECByINR(Map<String, Object> params) {
        List bizFECS = bizFECMapper.getBizFECByINR(params);
        if(bizFECS != null && bizFECS.size() > 0){
            for (int i = 0; i < bizFECS.size(); i++) {
                Map fec = (Map)bizFECS.get(i);
                String rateType = StringUtil.objectToString(fec.get("rateType"));
                // 浮动利率需要查询利率基准
                if("2".equals(rateType)){
                    String irBk = StringUtil.objectToString(fec.get("irBk"));
                    String term = StringUtil.objectToString(fec.get("term"));
                    Map dataMap =new HashMap();
                    dataMap.put("irBk",irBk);
                    dataMap.put("term",term);
                    String irBkString = bizFECMapper.getIrBkStringByCode(dataMap);
                    fec.put("irBkString",irBkString);
                }
            }
        }
        return bizFECS;
    }

	@Override
	public int deleteByGrantId(Long grantId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("objType", BizContant.BIZ_TABLE_GRANT);
		params.put("objInr", grantId);
		
		return this.deleteByParams(params);
	}
}
