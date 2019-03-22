package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.*;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.InfAfwkpln;
import org.ibase4j.model.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.io.Serializable;
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
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private InfAfpaexoProvider infAfpaexoProvider;
    @Autowired
    private InfAfpcmemMapper infAfpcmemMapper;

    @Override
    public Page getDebtStandingBookList(Map<String, Object> param) {
        Page page = getPage(param);
        page.setRecords(bizDebtGrantMapper.getDebtStandingBookList(page, param));
        return page;
    }

    @Override
    public Page getGrantStandingBookPage(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("mem.workdate");
        List grantStandingBookList = bizMakeLoansMapper.getGrantStandingBookList(page, param);
        // 查询下次还本时间 金额 还息时间
        String endDate = StringUtil.objectToString(param.get("endDate"));

        if (grantStandingBookList != null && grantStandingBookList.size() > 0) {
            for (int i = 0; i < grantStandingBookList.size(); i++) {
                Map dataMap = new HashMap();
                Map grantStandingBook = (Map) grantStandingBookList.get(i);
                String grantCode = StringUtil.objectToString(grantStandingBook.get("grantCode"));
                String objInr = StringUtil.objectToString(grantStandingBook.get("grantId"));
                BigDecimal totffamt = (String) grantStandingBook.get("totffamt") == null ?new BigDecimal(0):new BigDecimal((String) grantStandingBook.get("totffamt"));
                BigDecimal tothkamt = (String) grantStandingBook.get("tothkamt") == null ?new BigDecimal(0):new BigDecimal((String) grantStandingBook.get("tothkamt"));
                grantStandingBook.put("closingBal", StringUtil.objectToString(totffamt.subtract(tothkamt)));

                String protseno = StringUtil.objectToString(grantStandingBook.get("protseno"));

                Long endDateL = Long.parseLong(endDate.replace("-","").substring(0,8));

                Set<Serializable> amtSet = redisHelper.zget(Constants.CACHE_INF_AFWKPLN_AMT_NEXTPLAN + protseno,endDateL,22991231L,1,1);
                if(amtSet.size()==1){
                    for (Serializable jsonStr : amtSet) {
                        InfAfwkpln amtPln = JSON.parseObject((String)jsonStr,InfAfwkpln.class);
                        grantStandingBook.put("gbDate", amtPln.getGbdate());
                        grantStandingBook.put("gbAmt", amtPln.getGbamount());
                    }
                }
                Set<Serializable> intSet = redisHelper.zget(Constants.CACHE_INF_AFWKPLN_INT_NEXTPLAN + protseno,endDateL,22991231L,1,1);
                if(intSet.size()==1){
                    for (Serializable jsonStr : intSet) {
                        InfAfwkpln intPln = JSON.parseObject((String)jsonStr,InfAfwkpln.class);
                        grantStandingBook.put("interestDate", intPln.getGbdate());
                        grantStandingBook.put("interestAmt", intPln.getGbint());
                    }
                }
            }
        }
        page.setRecords(grantStandingBookList);
        return page;
    }

    @Override
    public List getGrantStandingBookList(Map<String, Object> param) {
        Page page = getPage(param);
        page.setOrderByField("mem.workdate");
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
//        List grantAMTList = bizMakeLoansMapper.getGrantAMTDetailForStandingBook(param);
        List grantAMTList = bizMakeLoansMapper.getGrantStandingBookList(param);

       /* if (grantAMTList != null && grantAMTList.size() > 0) {
            for (int i = 0; i < grantAMTList.size(); i++) {
                Map grantAMT = (Map) grantAMTList.get(i);

                BigDecimal totffamt = grantAMT.get("totffamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("totffamt"));
                BigDecimal tothkamt =  grantAMT.get("tothkamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("tothkamt"));
                BigDecimal closingBal = totffamt.subtract(tothkamt);
                grantAMT.put("closingBal", StringUtil.objectToString(closingBal));
                String currency = StringUtil.objectToString(grantAMT.get("currency"));
                String deptCode = StringUtil.objectToString(grantAMT.get("deptCode"));
                BigDecimal inamt =  grantAMT.get("inamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("inamt"));
                BigDecimal ofamt =  grantAMT.get("ofamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("ofamt"));
                grantAMT.put("inoffAmt", inamt.add(ofamt).setScale(2,BigDecimal.ROUND_HALF_UP));
                BigDecimal intrate =  grantAMT.get("intrate") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("intrate"));
                grantAMT.put("intrate", StringUtil.objectToString(intrate.multiply(new BigDecimal("0.000001"))));

                if("CNY".equals(currency)){
                    grantAMT.put("closingBalCNY", StringUtil.objectToString(closingBal));
                    grantAMT.put("tothkamtCNY", StringUtil.objectToString(tothkamt));
                }else if("USD".equals(currency)){
                    grantAMT.put("closingBalUSD", StringUtil.objectToString(closingBal));
                }else if("".equals(currency)){
                    throw new RuntimeException("ERROR.currency is null!");
                }

                BigDecimal crossRateCNY = infAfpaexoProvider.getCrossRate(currency,"CNY",null,deptCode);
                if(null != crossRateCNY){
                    grantAMT.put("closingBalCNY", StringUtil.objectToString(closingBal.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                    grantAMT.put("tothkamtCNY", StringUtil.objectToString(tothkamt.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                }else {
                    logger.error("未获取到折算牌价...crossRateCNY=currency="+currency+" deptCode="+deptCode);
                    throw new RuntimeException("can't get crossRate...crossRateCNY=currency="+currency+" deptCode="+deptCode);
                }
                BigDecimal crossRateUSD = infAfpaexoProvider.getCrossRate(currency,"USD",null,deptCode);
                if(null != crossRateUSD){
                    grantAMT.put("closingBalUSD", StringUtil.objectToString(closingBal.multiply(crossRateUSD).setScale(2,BigDecimal.ROUND_HALF_UP)));
                }else {
                    logger.error("未获取到折算牌价...crossRateUSD=currency="+currency+" deptCode="+deptCode);
                    throw new RuntimeException("can't get crossRate...crossRateUSD=currency="+currency+" deptCode="+deptCode);
                }

            }
        }*/
        return getMoreInfoForStatistic(grantAMTList);
    }

    @Override
    public Page getStatisticInfoPage(Map<String, Object> param) {
        //endDate=2019-03-15 09:48:21
        Page page = getPage(param);
        page.setOrderByField("mem.workdate");
        param.put("workdate",infAfpcmemMapper.getMaxWorkDate(StringUtil.objectToString(param.get("endDate"))));
        List statisticInfoList = bizMakeLoansMapper.getGrantMessageStandingBook(page,param);
        page.setRecords(getMoreInfoForStatistic(statisticInfoList));
        return page;
    }

    @Override
    public List getStatisticInfoList(Map<String, Object> param) {
        Page page = getPage(param);
//        page.setOrderByField("A.DATE_OF_LOAN");
//        page.setSize(99999999);
//        List statisticInfoList = bizMakeLoansMapper.getStatisticInfoList(page,param);
        page.setSize(99999999);
        page.setOrderByField("mem.workdate");
        param.put("workdate",infAfpcmemMapper.getMaxWorkDate(StringUtil.objectToString(param.get("endDate"))));
        List statisticInfoList = bizMakeLoansMapper.getGrantMessageStandingBook(page,param);
        List moreInfoForStatistic = getMoreInfoForStatistic(statisticInfoList);

        return moreInfoForStatistic;
    }

    private List getMoreInfoForStatistic(List statisticInfoList){

        if (statisticInfoList != null && statisticInfoList.size() > 0) {
            for (int i = 0; i < statisticInfoList.size(); i++) {
                Map grantAMT = (Map) statisticInfoList.get(i);

                BigDecimal totffamt = grantAMT.get("totffamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("totffamt"));
                BigDecimal tothkamt =  grantAMT.get("tothkamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("tothkamt"));
                //逾期本金金额
                BigDecimal ovramt =  grantAMT.get("ovramt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("ovramt"));
                BigDecimal closingBal = totffamt.subtract(tothkamt);
                grantAMT.put("closingBal", StringUtil.objectToString(closingBal));
                String currency = StringUtil.objectToString(grantAMT.get("currency"));
                String deptCode = StringUtil.objectToString(grantAMT.get("deptCode"));
                BigDecimal inamt =  grantAMT.get("inamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("inamt"));
                BigDecimal ofamt =  grantAMT.get("ofamt") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("ofamt"));
                grantAMT.put("inoffAmt", inamt.add(ofamt).setScale(2,BigDecimal.ROUND_HALF_UP));
                BigDecimal intrate =  grantAMT.get("intrate") == null ?new BigDecimal(0):new BigDecimal((String) grantAMT.get("intrate"));
                grantAMT.put("intrate", StringUtil.objectToString(intrate.multiply(new BigDecimal("0.000001"))));

                if("CNY".equals(currency)){
                    grantAMT.put("closingBalCNY", StringUtil.objectToString(closingBal));
                    grantAMT.put("tothkamtCNY", StringUtil.objectToString(tothkamt));
                    grantAMT.put("totffamtCNY", StringUtil.objectToString(totffamt));
                    grantAMT.put("ovramtCNY", StringUtil.objectToString(ovramt));
                }else if("USD".equals(currency)){
                    grantAMT.put("closingBalUSD", StringUtil.objectToString(closingBal));
                }else if("".equals(currency)){
                    throw new RuntimeException("ERROR.currency is null!");
                }

                BigDecimal crossRateCNY = infAfpaexoProvider.getCrossRate(currency,"CNY",null,deptCode);
                if(null != crossRateCNY){
                    grantAMT.put("closingBalCNY", StringUtil.objectToString(closingBal.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                    grantAMT.put("tothkamtCNY", StringUtil.objectToString(tothkamt.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                    grantAMT.put("totffamtCNY", StringUtil.objectToString(totffamt.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                    grantAMT.put("ovramtCNY", StringUtil.objectToString(ovramt.multiply(crossRateCNY).setScale(2,BigDecimal.ROUND_HALF_UP)));
                }else {
                    logger.error("未获取到折算牌价...crossRateCNY=currency="+currency+" deptCode="+deptCode);
                    throw new RuntimeException("can't get crossRate...crossRateCNY=currency="+currency+" deptCode="+deptCode);
                }
                BigDecimal crossRateUSD = infAfpaexoProvider.getCrossRate(currency,"USD",null,deptCode);
                if(null != crossRateUSD){
                    grantAMT.put("closingBalUSD", StringUtil.objectToString(closingBal.multiply(crossRateUSD).setScale(2,BigDecimal.ROUND_HALF_UP)));
                }else {
                    logger.error("未获取到折算牌价...crossRateUSD=currency="+currency+" deptCode="+deptCode);
                    throw new RuntimeException("can't get crossRate...crossRateUSD=currency="+currency+" deptCode="+deptCode);
                }

            }
        }

        /*if(statisticInfoList != null && statisticInfoList.size()>0){
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
        }*/
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
