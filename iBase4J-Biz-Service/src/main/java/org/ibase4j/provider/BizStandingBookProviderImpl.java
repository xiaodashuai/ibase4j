package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.*;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 台账
 * @Author: dj
 * @CreateDate: 2018-08-31
 */
@SuppressWarnings("unchecked")
@CacheConfig(cacheNames = "bizStandingBook")
@Service(interfaceClass = BizStandingBookProvider.class)
public class BizStandingBookProviderImpl extends BaseProviderImpl<BizDebtGrant> implements BizStandingBookProvider {

    @Autowired
    private BizDebtGrantMapper bizDebtGrantMapper;
    @Autowired
    private BizDebtSummaryMapper bizDebtSummaryMapper;
    @Autowired
    private BizMakeLoansMapper bizMakeLoansMapper;
    @Autowired
    private BizRepaymentPricinalPlanMapper bizRepaymentPricinalPlanMapper;
    @Autowired
    private BizRepaymentLoanPlanMapper bizRepaymentLoanPlanMapper;
    @Reference
    private ISysDeptProvider sysDeptProvider;
    @Autowired
    private BizFECMapper bizFECMapper;
    @Autowired
    private BizCBEMapper bizCBEMapper;
    @Autowired
    private BizCBBMapper bizCBBMapper;
    @Autowired
    private BizGuaranteeResultProvider bizGuaranteeResultProvider;

    @Override
    public Page getDebtStandingBookList(Map<String, Object> param) {
        Page page = getPage(param);
        page.setRecords(bizDebtGrantMapper.getDebtStandingBookList(page, param));
        return page;
    }

    @Override
    public Page getGrantStandingBookPage(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("A.DATE_OF_LOAN");
        List grantStandingBookList = bizMakeLoansMapper.getGrantStandingBookList(page, param);
        // 查询下次还本时间 金额 还息时间
        String endDate = StringUtil.objectToString(param.get("endDate"));
        if (grantStandingBookList != null && grantStandingBookList.size() > 0) {
            for (int i = 0; i < grantStandingBookList.size(); i++) {
                Map dataMap = new HashMap();
                Map grantStandingBook = (Map) grantStandingBookList.get(i);
                String grantCode = StringUtil.objectToString(grantStandingBook.get("grantCode"));
                String objInr = StringUtil.objectToString(grantStandingBook.get("grantId"));
                dataMap.put("objInr", objInr);
                dataMap.put("endDate", endDate.substring(0,10));
                dataMap.put("grantCode", grantCode);
                // 查找发放币种和金额 TODO 由于第一批次租金保理业务只有一个币种 对于多币种只展示一个一种
                List bizFECByINR = bizFECMapper.getBizFECByINR(dataMap);
                if (bizFECByINR != null && bizFECByINR.size() > 0) {
                    for (int i1 = 0; i1 < bizFECByINR.size(); i1++) {
                        Map bizFec = (Map) bizFECByINR.get(i1);
                        grantStandingBook.put("currency", StringUtil.objectToString(bizFec.get("currencyString")));
                        grantStandingBook.put("paymentAmt", StringUtil.objectToString(bizFec.get("paymentAmt")));
                    }
                } else {
                    grantStandingBook.put("currency", "");
                    grantStandingBook.put("paymentAmt", "");
                }
                // 查询下次还本时间 金额
                List debtInfoForRepaymentPricinal = bizRepaymentPricinalPlanMapper.getDebtInfoForRepaymentPricinal(dataMap);
                if (debtInfoForRepaymentPricinal != null && debtInfoForRepaymentPricinal.size() > 0) {
                    Map repaymentPricinal = (Map) debtInfoForRepaymentPricinal.get(0);
                    grantStandingBook.put("payDate", StringUtil.objectToString(repaymentPricinal.get("payDate")).substring(0,10));
                    grantStandingBook.put("principalAmt", StringUtil.objectToString(repaymentPricinal.get("principalAmt")));
                } else {
                    grantStandingBook.put("payDate", "");
                    grantStandingBook.put("principalAmt", "");
                }
                // 查询下次还本时间 金额 还息时间
                List debtInfoForRepaymentLoan = bizRepaymentLoanPlanMapper.getDebtInfoForRepaymentLoan(dataMap);
                if (debtInfoForRepaymentLoan != null && debtInfoForRepaymentLoan.size() > 0) {
                    Map repaymentLoan = (Map) debtInfoForRepaymentLoan.get(0);
                    grantStandingBook.put("interestDate", StringUtil.objectToString(repaymentLoan.get("interestDate")).substring(0,10));
                } else {
                    grantStandingBook.put("interestDate", "");
                }
            }
        }


        page.setRecords(grantStandingBookList);
        return page;
    }

    @Override
    public List getGrantStandingBookList(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("A.DATE_OF_LOAN");
        page.setSize(99999999);
        return bizMakeLoansMapper.getGrantStandingBookList(page,param);
    }

    @Override
    public Map getDebtInfoForStandingBook(Map<String, Object> params) {
        return bizDebtSummaryMapper.getDebtInfoForStandingBook(params);
    }

    @Override
    public Map getGrantInfoForStandingBook(Map<String, Object> params) {
        return bizDebtSummaryMapper.getGrantInfoForStandingBook(params);
    }

    /**
     * @Description: 查询所有机构
     * @Param: [params]
     * @return: java.util.List
     */
    @Override
    public List getAllInstitution(Map<String, Object> params) {
        return sysDeptProvider.queryList(params);
    }

    @Override
    public List getGrantAMTDetailForStandingBook(Map<String, Object> param) {
        List grantAMTList = bizMakeLoansMapper.getGrantAMTDetailForStandingBook(param);
        if (grantAMTList != null && grantAMTList.size() > 0) {
            for (int i = 0; i < grantAMTList.size(); i++) {
                Map grantAMT = (Map) grantAMTList.get(i);
                // 查询发放金额 币种 利率
                String objInr = StringUtil.objectToString(grantAMT.get("objInr"));
                Map dataMap = new HashMap();
                dataMap.put("objInr", objInr);
                List bizFECByINR = bizFECMapper.getBizFECByINR(dataMap);
                if (bizFECByINR != null && bizFECByINR.size() > 0) {
                    Map bizFec = (Map) bizFECByINR.get(0);
                    grantAMT.put("currency", StringUtil.objectToString(bizFec.get("currencyString")));
                    grantAMT.put("paymentAmt", StringUtil.objectToString(bizFec.get("paymentAmt")));
                    grantAMT.put("rateVal", StringUtil.objectToString(bizFec.get("rateVal")));
                } else {
                    grantAMT.put("currency", "");
                    grantAMT.put("paymentAmt", 0);
                    grantAMT.put("rateVal", 0);
                }
                // 查询当前日期折算牌价 暂为1
                BigDecimal amtBalance = new BigDecimal(StringUtil.objectToString(grantAMT.get("amtBalance")));
                grantAMT.put("amtBalanceCNY", amtBalance);
                grantAMT.put("amtBalanceUSD", amtBalance);
                // 本金累计收回金额（原币）
                Map cbeMap = new HashMap();
                cbeMap.put("endDate", StringUtil.objectToString(grantAMT.get("dateOfLoan")));
                cbeMap.put("objInr", StringUtil.objectToString(grantAMT.get("objInr")));
                Map grantAMTInALL = bizCBEMapper.getGrantAMTInALL(cbeMap);
                BigDecimal grantAMTInAll = new BigDecimal(StringUtil.objectToString(grantAMTInALL.get("grantAMTInAll")));
                grantAMT.put("grantAMTInAll", grantAMTInAll);
                // 本金累计收回金额 (折人民币)
                grantAMT.put("grantAMTInAllCNY", grantAMTInAll);
                // 逾期本金余额
                Map OVEDUSUMMap = new HashMap();
                OVEDUSUMMap.put("endDate", StringUtil.objectToString(grantAMT.get("dateOfLoan")));
                OVEDUSUMMap.put("objInr", StringUtil.objectToString(grantAMT.get("objInr")));
                OVEDUSUMMap.put("cbc", BizContant.OVEDUSUM_CBCTXT);
                grantAMT.put("oveduSum", bizCBBMapper.getCBBbyCBCType(OVEDUSUMMap));
                // 表内欠息余额
                Map INATESUMMap = new HashMap();
                INATESUMMap.put("endDate", StringUtil.objectToString(grantAMT.get("dateOfLoan")));
                INATESUMMap.put("objInr", StringUtil.objectToString(grantAMT.get("objInr")));
                INATESUMMap.put("cbc", BizContant.INATESUM_CBCTXT);
                grantAMT.put("inateSum", bizCBBMapper.getCBBbyCBCType(OVEDUSUMMap));
                // 表外欠息余额
                Map OUATESUMMap = new HashMap();
                OUATESUMMap.put("endDate", StringUtil.objectToString(grantAMT.get("dateOfLoan")));
                OUATESUMMap.put("objInr", StringUtil.objectToString(grantAMT.get("objInr")));
                OUATESUMMap.put("cbc", BizContant.OUATESUM_CBCTXT);
                grantAMT.put("ouateSum", bizCBBMapper.getCBBbyCBCType(OVEDUSUMMap));
            }
        }
        return grantAMTList;
    }

    @Override
    public Page getStatisticInfoPage(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("A.DATE_OF_LOAN");
        List statisticInfoList = bizMakeLoansMapper.getStatisticInfoList(page, param);
        List moreInfoForStatistic = getMoreInfoForStatistic(statisticInfoList);
        page.setRecords(moreInfoForStatistic);
        return page;
    }

    @Override
    public List getStatisticInfoList(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("A.DATE_OF_LOAN");
        page.setSize(99999999);
        List statisticInfoList = bizMakeLoansMapper.getStatisticInfoList(page,param);
        List moreInfoForStatistic = getMoreInfoForStatistic(statisticInfoList);
        return moreInfoForStatistic;
    }

    private List getMoreInfoForStatistic(List statisticInfoList){

        if(statisticInfoList != null && statisticInfoList.size()>0){
            for (int i = 0; i < statisticInfoList.size(); i++) {
                Map statisticInfo = (Map)statisticInfoList.get(i);
                // 查找币种 发放金额
                String grantId = StringUtil.objectToString(statisticInfo.get("grantId"));
                String grantCode = StringUtil.objectToString(statisticInfo.get("grantCode"));
                Map dataMap = new HashMap();
                dataMap.put("objInr", grantId);
                dataMap.put("grantCode", grantCode);
                //担保类型总
                List bizGuaranteeResultList = bizGuaranteeResultProvider.getBizGuaranteeResultList(dataMap);
                String guaranteeTypeTotal = getAllGuaranNameFromList(bizGuaranteeResultList);
                statisticInfo.put("guaranteeTypeTotal",guaranteeTypeTotal);

                List bizFECByINR = bizFECMapper.getBizFECByINR(dataMap);
                if (bizFECByINR != null && bizFECByINR.size() > 0) {
                    Map bizFec = (Map) bizFECByINR.get(0);
                    statisticInfo.put("currency", StringUtil.objectToString(bizFec.get("currency")));
                    statisticInfo.put("paymentAmt", StringUtil.objectToString(bizFec.get("paymentAmt")));
                    statisticInfo.put("rateVal", StringUtil.objectToString(bizFec.get("rateVal")));
                    statisticInfo.put("paymentMode", StringUtil.objectToString(bizFec.get("paymentMode")));
                } else {
                    statisticInfo.put("currency", "");
                    statisticInfo.put("paymentAmt", 0);
                    statisticInfo.put("rateVal", 0);
                    statisticInfo.put("paymentMode", "");
                }
                // 本金期末余额 (原币)
                // 查询当前日期折算牌价 暂为1
                BigDecimal amtBalance = new BigDecimal(StringUtil.objectToString(statisticInfo.get("amtBalance")));
                statisticInfo.put("amtBalanceCNY", amtBalance);
                statisticInfo.put("amtBalanceUSD", amtBalance);
                // 业务期限
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String valueDateStrign = StringUtil.objectToString(statisticInfo.get("valueDate"));
                String dueDateString = StringUtil.objectToString(statisticInfo.get("dueDate"));
                Date valueDate = null;
                Date dueDate = null;
                if(!"".equals(valueDateStrign) && !"".equals(dueDateString)){
                    try {
                        valueDate = dateFormat.parse(valueDateStrign);
                        dueDate = dateFormat.parse(dueDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Integer dayBetween = DateUtil.getDayBetween(valueDate, dueDate) + 1;
                    statisticInfo.put("dayBetween", dayBetween);
                }else{
                    statisticInfo.put("dayBetween", "");
                }
            }
        }
        return statisticInfoList;
    }

    private String getAllGuaranNameFromList(List bizGuaranteeResults){
        List nameList = new ArrayList();
        if(bizGuaranteeResults != null && bizGuaranteeResults.size() > 0){
            for (int i = 0; i < bizGuaranteeResults.size(); i++) {
                Map bizGuaranteeResult = (Map)bizGuaranteeResults.get(i);
                String guaranName = StringUtil.objectToString(bizGuaranteeResult.get("guaranName"));
                nameList.add(guaranName);
            }
        }
        String AllGuaranName = StringUtil.listToString(nameList,",");
        return AllGuaranName;
    }

}
