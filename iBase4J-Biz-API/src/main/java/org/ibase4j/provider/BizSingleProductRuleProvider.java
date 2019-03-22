package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizSingleProductRule;
import org.ibase4j.vo.ProductVo;

import java.util.List;

/**
 * 描述：债项概要
 * @author xiaoshuiquan
 * @date 2018/05/28
 */
public interface BizSingleProductRuleProvider extends BaseProvider<BizSingleProductRule> {

    /**
     *保存产品种类
     * xiaoshuiquan
     * 2018.6.12
     *
     */
    void savePro(String proIds,String debtNum);
    /**
	 * 功能：查询一个产品信息
	 * @param sprId 产品号
	 * @return
	 */
    ProductVo getProductPairBySingleProductRuleId(Long sprId);
    /**
     * 功能：查询方案中的所有产品列表
     * @param debtCode
     * @return
     */
    List<ProductVo> getProductList(String debtCode);
}
