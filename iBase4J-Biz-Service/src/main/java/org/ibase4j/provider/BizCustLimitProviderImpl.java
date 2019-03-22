package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizCreditLinesMapper;
import org.ibase4j.mapper.BizCustLimitMapper;
import org.ibase4j.mapper.BizCustMapper;
import org.ibase4j.mapper.BizProductLinesTypeMapper;
import org.ibase4j.model.BizCreditLines;
import org.ibase4j.model.BizCust;
import org.ibase4j.model.BizCustLimit;
import org.ibase4j.model.BizProductLinesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 额度占用
 * @author hannasong
 */
@CacheConfig(cacheNames = "BizCustLimit")
@Service(interfaceClass = BizCustLimitProvider.class)
public class BizCustLimitProviderImpl extends BaseProviderImpl<BizCustLimit> implements BizCustLimitProvider {

    private static final Logger log = LogManager.getLogger(BizDebtGrantProviderImpl.class);
    @Autowired
    private BizCustLimitMapper bizCustLimitMapper;
    @Autowired
    private BizProductLinesTypeMapper bizProductLinesTypeMapper;
    @Autowired
    private BizCreditLinesMapper bizCreditLinesMapper;
    @Autowired
    private BizCustMapper bizCustMapper;

    /**
     * @Description: 获取客户额度占用信息
     * @Param: grantCode
     * @return: List
     */
    @Override
    public List<BizCustLimit> getCustLimit(Map<String, Object> params){

        // 先在数据库中查，如果库中没有，则通过方案信息拼
        List<BizCustLimit> bizCustLimitList = bizCustLimitMapper.getCustLimitList(params);
         if (null==bizCustLimitList){
             // 如果数据库中没有，说明没存过库，应使用方案信息拼出原始数据
             // 方案号
             String debtCode = StringUtil.objectToString(params.get("debtCode")) ;
             // 发放条件编号
             String grantCode = StringUtil.objectToString(params.get("grantCode")) ;
             // 发放币种
             String cur = StringUtil.objectToString(params.get("cur")) ;
             // 发放金额
             BigDecimal amount = (BigDecimal)params.get("amt") ;
             // 发放牌价
             BigDecimal convertedPrice = (BigDecimal)params.get("convertedPrice") ;


             // 通过方案号查额度信息表biz_product_linestype
             BizProductLinesType bizProductLinesType = new BizProductLinesType();
             bizProductLinesType.setDebtCode(debtCode);
             List<BizProductLinesType> bizProductLinesTypeList = bizProductLinesTypeMapper.selectList(new EntityWrapper<>(bizProductLinesType));
             if (null!=bizProductLinesTypeList){
                 for (BizProductLinesType bizPro:bizProductLinesTypeList){
                     BizCustLimit tmpBizCustLimit = new BizCustLimit();

                     // 获取信贷传来的 授信额度信息
                     BizCreditLines bizCreditLines = new BizCreditLines();
                     bizCreditLines.setDebtCode(debtCode);
                     bizCreditLines.setObjtyp(BizContant.BIZ_DEBT_MAIN);
                     bizCreditLines.setCustNo(bizPro.getCustNo());
                     bizCreditLines.setAmountType(bizPro.getCreditLinesId());
                     BizCreditLines bizCreditLines1 = bizCreditLinesMapper.selectOne(bizCreditLines);

                     // 使用bizPro 和 bizCreditLines1 组装tmpBizCustLimit
                     tmpBizCustLimit.setCustNo(bizPro.getCustNo());
                     tmpBizCustLimit.setPtyinr(bizCreditLines1.getCustomerId().toString());
                     tmpBizCustLimit.setCustNameCN(bizCustMapper.selectById(bizCreditLines1.getCustomerId()).getCustNameCN());
                     tmpBizCustLimit.setAmountType(bizCreditLines1.getAmountType());
                     tmpBizCustLimit.setCreditNo(bizCreditLines1.getCreditNo());
                     tmpBizCustLimit.setCreditLineName(bizCreditLines1.getCreditLineName());
                     tmpBizCustLimit.setCur(BizContant.CURRENCY_CNY);
                     // 额度占用金额
                     // 1.非最高保证额度：额度金额 = 发放金额*比例*汇率
                     // 2.最高保证额度：额度金额 = 保证人 保证（担保类型） 可明确划分金额之和
                     //   如果还选了【信用】：额度金额 = 手输

                     if ("0006"==tmpBizCustLimit.getAmountType()){
                         // 最高保证

                     }else {
                         // 获取比例
                         BigDecimal creditRatio = new BigDecimal(bizPro.getCreditRatio());
                         tmpBizCustLimit.setAmt(amount.multiply(convertedPrice).multiply(creditRatio) );
                     }

                     bizCustLimitList.add(tmpBizCustLimit);
                 }
             }
         }

        return  bizCustLimitList;
    }
	
}
