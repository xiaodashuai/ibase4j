/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizGuaranteeResultMapper;
import org.ibase4j.model.BizGuaranteeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能： 发放流程中的担保信息 
 * 日期：2018/7/6
 * @author czm
 */
@CacheConfig(cacheNames = "BizGuaranteeResult")
@Service(interfaceClass = BizGuaranteeResultProvider.class)
public class BizGuaranteeResultProviderImpl extends BaseProviderImpl<BizGuaranteeResult> implements BizGuaranteeResultProvider {

    @Autowired
    private BizGuaranteeResultMapper bizGuaranteeResultMapper;
    @Override
    public List<BizGuaranteeResult> getBizGuaranteeResultList(Map<String, Object> params) {
        List bizGuaranteeResults = bizGuaranteeResultMapper.getBizGuaranteeResultList(params);
        return bizGuaranteeResults;
    }

    @Override
    public List getGuaranteeInfoList(Map<String, Object> params) {
        return bizGuaranteeResultMapper.getGuaranteeInfoList(params);
    }

    @Override
    public List getGuaranteeRelationInfoList(Map<String, Object> params) {
        return bizGuaranteeResultMapper.getGuaranteeRelationInfoList(params);
    }

    @Override
	public int deleteByGrantCode(String grantCode) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("grantCode", grantCode);
		return this.deleteByParams(params);
	}
}
