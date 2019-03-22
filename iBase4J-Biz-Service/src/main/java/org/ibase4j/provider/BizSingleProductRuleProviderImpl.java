package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizSingleProductRuleMapper;
import org.ibase4j.model.BizSingleProductRule;
import org.ibase4j.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * @author xiaoshuiquan
 * @date 2018/06/11
 */
@CacheConfig(cacheNames = "bizSingleProductRule")
@Service(interfaceClass = BizSingleProductRuleProvider.class)
public class BizSingleProductRuleProviderImpl extends BaseProviderImpl<BizSingleProductRule> implements BizSingleProductRuleProvider {

    @Autowired
    private BizSingleProductRuleMapper singleProductRuleMapper;


    @Override
    public void savePro(String proIds,String debtNum) {
        if(StringUtils.isNotBlank(proIds)){
            String[] ids = StringUtils.split(proIds, ",");
            for (int a=0;a<ids.length;a++){
                BizSingleProductRule b=new BizSingleProductRule();
                b.setDebtCode(debtNum!=null?debtNum.toString():null);
                b.setBusinessType(ids[a]);
                singleProductRuleMapper.insert(b);
            }
        }
    }


	@Override
	public ProductVo getProductPairBySingleProductRuleId(Long sprId) {
		return singleProductRuleMapper.getProductPairBySingleProductRuleId(sprId);
	}


	@Override
	public List<ProductVo> getProductList(String debtCode) {
		return singleProductRuleMapper.getProductPairByDebtCode(debtCode);
	}
    
}
