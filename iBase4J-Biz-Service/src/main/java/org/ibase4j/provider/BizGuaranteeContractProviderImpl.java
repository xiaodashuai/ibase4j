package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizGuaranteeContractMapper;
import org.ibase4j.model.BizGuaranteeContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwh
 * @date 2018/8/16
 */
@CacheConfig(cacheNames = "bizGuaranteeContract")
@Service(interfaceClass = BizGuaranteeContractProvider.class)
public class BizGuaranteeContractProviderImpl extends BaseProviderImpl<BizGuaranteeContract> implements BizGuaranteeContractProvider {

    @Autowired
    private BizGuaranteeContractMapper bizGuaranteeContractMapper;

	@Override
	public int deleteByGrantCode(String grantCode, String debtCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("debtCode", debtCode);
		params.put("grantCode", grantCode);
		return this.deleteByParams(params);
	}


}
