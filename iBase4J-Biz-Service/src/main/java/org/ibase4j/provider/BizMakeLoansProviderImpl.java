/**
 *
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.*;
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.mapper.BizMakeLoansMapper;
import org.ibase4j.mapper.BizProductDefinitionMapper;
import org.ibase4j.model.*;
import org.ibase4j.vo.RepaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：放款
 * 日期：2018/7/24
 *
 * @author lwh
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizMakeLoans")
@Service(interfaceClass = BizMakeLoansProvider.class)
public class BizMakeLoansProviderImpl extends BaseProviderImpl<BizMakeLoans> implements BizMakeLoansProvider {
    @Autowired
    private BizMakeLoansMapper bizMakeLoansMapper;
    @Autowired
    private BizDebtGrantMapper bizDebtGrantMapper;
    @Autowired
    private BizDebtGrantProvider bizDebtGrantProvider;
    @Autowired
    private BizDebtSummaryProvider bizDebtSummaryProvider;
    @Autowired
    private BizTRNProvider bizTRNProvider;
    @Autowired
    private BizFEPProvider bizFEPProvider;
    @Autowired
    private BizFECProvider bizFECProvider;
    @Autowired
    private BizGuaranteeResultProvider bizGuaranteeResultProvider;
    @Autowired
    private BizRentalFactoringProvider bizRentalFactoringProvider;
    @Autowired
    private BizCBEProvider bizCBEProvider;
    @Autowired
    private BizCBBProvider bizCBBProvider;
    @Autowired
    private BizRepaymentPlanProvider bizRepaymentPlanProvider;
    @Autowired
    private BizRepaymentPricinalPlanProvider bizRepaymentPricinalPlanProvider;
    @Autowired
    private BizRepaymentLoanPlanProvider bizRepaymentLoanPlanProvider;
    @Autowired
    private BizCntProvider bizCntProvider;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizCbsErrorMessageProvider bizCbsErrorMessageProvider;
    @Reference
    private ISysUserProvider sysUserProvider;
    @Reference
    private ISysDeptProvider sysDeptProvider;
    @Autowired
    private BizInterfaceResultProvider bizInterfaceResultProvider;
    @Autowired
    private BizProductDefinitionMapper bizProductDefinitionMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getMakeLoansDebtInfo(Map<String, Object> params) {
        Map dataMap = new HashMap();
        // 产品相关信息
        Map debtInfoForMakeLoan = bizDebtGrantProvider.getDebtInfoForMakeLoan(params);
        // 用信主体相关信息
        List productCustomerInfoList = bizDebtGrantProvider.getProductCustomerInfo(params);
        // 获取费率相关信息
        List fepList = bizFEPProvider.getBizFEPByINR(params);
        // 获取利率相关信息
        List fecList = bizFECProvider.getBizFECByINR(params);
        // 获取担保相关信息
        List bizGuaranteeResultList = bizGuaranteeResultProvider.getBizGuaranteeResultList(params);
        String AllGuaranName = getAllGuaranNameFromList(bizGuaranteeResultList);
        debtInfoForMakeLoan.put("AllGuaranName", AllGuaranName);
        // 业务专有信息
        Map bizRentalFactoring = bizRentalFactoringProvider.getBizRentalFactoringForMakeLoan(params);
        dataMap.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
        dataMap.put("productCustomerInfo", productCustomerInfoList);
        dataMap.put("fepList", fepList);
        dataMap.put("fecList", fecList);
        dataMap.put("bizGuaranteeResultList", bizGuaranteeResultList);
        dataMap.put("bizRentalFactoring", bizRentalFactoring);
        return dataMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getRepaymentInfo(Map<String, Object> params) {
        Map dataMap = new HashMap();
        // 发放信息
        Map debtInfoForRepayment = bizDebtGrantProvider.getDebtInfoForMakeLoan(params);
        // 币种信息
        List bizFECList = bizFECProvider.getBizFECByINR(params);
        //还款计划表
        Map debtInfoForRepaymentPlan = bizRepaymentPlanProvider.getDebtInfoForRepayment(params);
        //还本计划表
        List debtInfoForRepaymentPricinal = bizRepaymentPricinalPlanProvider.getDebtInfoForRepaymentPricinal(params);
        //还息计划表
        List debtInfoForRepaymentLoan = bizRepaymentLoanPlanProvider.getDebtInfoForRepaymentLoan(params);

        dataMap.put("debtInfoForRepayment", debtInfoForRepayment);
        dataMap.put("bizFECList", bizFECList);
        dataMap.put("debtInfoForRepaymentPlan", debtInfoForRepaymentPlan);
        dataMap.put("debtInfoForRepaymentPricinal", debtInfoForRepaymentPricinal);
        dataMap.put("debtInfoForRepaymentLoan", debtInfoForRepaymentLoan);
        return dataMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String requestMakeLoansInterface(Map<String, Object> params) {
        // 由于68环境不能测试行方接口 遂配置接口开关 on开  off关
        String interfaceStatus = PropertiesUtil.getString("interface.status");
        // 时间
        String date = DateUtil.format(new Date());
        // 日期
        String time = DateUtil.formatTime(new Date());
        // 发放表主键id
        String objInr = StringUtil.objectToString(params.get("objInr"));
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 借据编号
        String iouCode = StringUtil.objectToString(params.get("iouCode"));
        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        // 发放币种
        String currency = "";
        // 利率浮动方式
        String floatMode = "";
        // 逾期利率浮动方式
        String overdueRateCalcMode = "";
        // 许可证日期
        String liceDate = "";
        // 创建借据许可证接口返回的借据序号
        String returnSeqNo = "";
        // 交易序号 每个介质识别号 每天从10001开始
        int seqNo = bizCntProvider.getNextSEQNO(identNumber + date.replace("-", "")) + 1;
        // 发放金额
        BigDecimal grantAmt;
        // 方案信息
        Map debtInfoForMakeLoan = (Map) params.get("debtInfoForMakeLoan");
        // 地区号
        Map map1 = new HashMap();
        SysUser sysUser = (SysUser) params.get("sysUser");
        Long userId = sysUser.getId();
        map1.put("userId", userId);
        String areaNumber = bizDebtGrantMapper.getAreaNumberByUserId(map1);
        // 利率信息
        Map fecDataMap = new HashMap();
        fecDataMap.put("objInr", objInr);
        List fecList = bizFECProvider.getBizFECByINR(fecDataMap);
        Map fecMap = new HashMap();
        if (fecList != null && fecList.size() > 0) {
            fecMap = (Map) fecList.get(0);
            currency = StringUtil.objectToString(fecMap.get("currency"));
            floatMode = StringUtil.objectToString(fecMap.get("floatMode"));
            overdueRateCalcMode = StringUtil.objectToString(fecMap.get("overdueRateCalcMode"));
            grantAmt = new BigDecimal(StringUtil.objectToString(fecMap.get("paymentAmt")));
            debtInfoForMakeLoan.put("grantAmt", grantAmt);
        }

        if ("on".equals(interfaceStatus)) {

            // 64002贷款合同状态查询接口所需参数
            Map paramsOne = new HashMap();
            paramsOne.put("grantCode", grantCode);
            paramsOne.put("areaNumber", areaNumber);
            paramsOne.put("identNumber", identNumber);
            paramsOne.put("date", date);
            paramsOne.put("time", time);
            Map resultOne = requestIntefaceOne(paramsOne);
            String codeOne = StringUtil.objectToString(resultOne.get("code"));
            if ("500".equals(codeOne)) {
                String msgOne = StringUtil.objectToString(resultOne.get("msg"));
                return msgOne;
            }

            // 调用64005创建借据许可证接口
            Map paramsTwo = new HashMap();
            paramsTwo.put("grantCode", grantCode);
            paramsTwo.put("areaNumber", areaNumber);
            paramsTwo.put("identNumber", identNumber);
            paramsTwo.put("seqNo", seqNo);
            paramsTwo.put("currency", currency);
            paramsTwo.put("iouCode", iouCode);
            paramsTwo.put("fecMap", fecMap);
            paramsTwo.put("floatMode", floatMode);
            paramsTwo.put("overdueRateCalcMode", overdueRateCalcMode);
            paramsTwo.put("date", date);
            paramsTwo.put("time", time);
            Map resultTwo = requestIntefaceTwo(paramsTwo);
            String codeTwo = StringUtil.objectToString(resultTwo.get("code"));
            if ("500".equals(codeTwo)) {
                String msgTwo = StringUtil.objectToString(resultTwo.get("msg"));
                return msgTwo;
            } else if ("300".equals(codeTwo)){
                liceDate = StringUtil.objectToString(resultTwo.get("liceDate"));
                returnSeqNo = StringUtil.objectToString(resultTwo.get("returnSeqNo"));
                BizDebtGrant bizDebtGrant = new BizDebtGrant();
                bizDebtGrant.setId(Long.valueOf(objInr));
                bizDebtGrant.setLiceDate(liceDate);
                bizDebtGrant.setSeqNo(returnSeqNo);
                bizDebtGrant.setIdentNumber(identNumber);
                bizDebtGrantProvider.update(bizDebtGrant);
            }

            // 调用额度占用接口
            Map paramsThree = new HashMap();
            paramsThree.put("grantCode", grantCode);
            paramsThree.put("areaNumber", areaNumber);
            paramsThree.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
            paramsThree.put("identNumber", identNumber);
            paramsThree.put("date", date);
            paramsThree.put("currency", currency);
            paramsThree.put("fecMap", fecMap);
            paramsThree.put("type", "1");//额度标志 1占用  0释放
            Map resultThree = requestIntefaceThree(paramsThree);
            String codeThree = StringUtil.objectToString(resultThree.get("code"));
            if ("500".equals(codeThree)) {
                String msgThree = StringUtil.objectToString(resultThree.get("msg"));
                return msgThree;
            }

            // 业务与担保合同关系接口
            Map paramsFour = new HashMap();
            paramsFour.put("grantCode", grantCode);
            paramsFour.put("identNumber", identNumber);
            paramsFour.put("type", "0002");//0002 生效   0004解除  0009结清
            Map resultFour = requestIntefaceFour(paramsFour);
            String codeFour = StringUtil.objectToString(resultFour.get("code"));
            if ("500".equals(codeFour)) {
                String msgFour = StringUtil.objectToString(resultFour.get("msg"));
                return msgFour;
            }
        }

        logger.debug("===================================================更新发放表信息start");

        // 更新发放表信息
        BizDebtGrant bizDebtGrant1 = new BizDebtGrant();
        bizDebtGrant1.setId(Long.valueOf(objInr));
        bizDebtGrant1.setGrantStatus(4);// 状态 3 已批未放 4 发放中 5 已发放
        bizDebtGrantProvider.update(bizDebtGrant1);

        logger.debug("===================================================更新发放表信息end");
        logger.debug("===================================================记录放款主表start");

        // 记录放款主表
        Map dataMap = new HashMap();
        SysDept sysDept = getUserInstitutionCodeByUserId(userId);
        debtInfoForMakeLoan.put("institutionCode", sysDept.getParentCode());
        dataMap.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
        dataMap.put("fecList", fecList);
        dataMap.put("userId", userId);
        dataMap.put("enable", 0);
        BizMakeLoans makeLoan = updateBizMakeLoans(dataMap);
        Long objInr1 = Long.valueOf(makeLoan.getId());
        dataMap.put("objInr", objInr1);

        logger.debug("===================================================记录放款主表end");

        // 记录放款台账 enable = 0
        boolean b = updateMakeLoanStandingBook(dataMap);
        if (!b) {
            return "台账记录失败";
        }

        return "等待核心处理结果";
    }

    /**
     * @Description: 发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendRepaymentPlan(Map<String, Object> params) {
        // 由于68环境不能测试行方接口 遂配置接口开关 on开  off关
        String interfaceStatus = PropertiesUtil.getString("interface.status");
        // 日期
        String date = DateUtil.format(new Date());
        // 时间
        String time = DateUtil.formatTime(new Date());
        // 方案信息
        Map<String, Object> debtInfoForMakeLoan = bizDebtGrantProvider.getDebtInfoForMakeLoan(params);
        // 发放id
        String grantId = StringUtil.objectToString(debtInfoForMakeLoan.get("objInr"));
        //发放信息
        BizDebtGrant bizDebtGrant = bizDebtGrantProvider.selectByGrantId(grantId);
        // 发放编码
        String grantCode = bizDebtGrant.getGrantCode();
        // 用户ID
        Long userId = Long.valueOf(params.get("userId").toString());
        // 许可证序号
        String seqNo = bizDebtGrant.getSeqNo() == null ? "" : bizDebtGrant.getSeqNo();
        // 许可证日期
        String liceDate = bizDebtGrant.getLiceDate() == null ? "" : bizDebtGrant.getLiceDate();
        // 借据编号
        String iouCode = bizDebtGrant.getIouCode() == null ? "" : bizDebtGrant.getIouCode();
        // 利率信息
        Map fecDataMap = new HashMap();
        fecDataMap.put("objInr", grantId);
        Map fecMap = null;
        List fecList = bizFECProvider.getBizFECByINR(fecDataMap);
        if (fecList != null && fecList.size() > 0) {
            fecMap = (Map) fecList.get(0);
        } else {
            return "64005借据许可证接口参数获取失败";
        }
        // 发放币种
        String currency = StringUtil.objectToString(fecMap.get("currency"));
        // 利率浮动方式
        String floatMode = StringUtil.objectToString(fecMap.get("floatMode"));
        // 逾期利率浮动方式
        String overdueRateCalcMode = StringUtil.objectToString(fecMap.get("overdueRateCalcMode"));
        // 地区号
        Map map1 = new HashMap();
        SysUser sysUser = (SysUser) params.get("sysUser");
        map1.put("userId", sysUser.getId());
        String areaNumber = bizDebtGrantMapper.getAreaNumberByUserId(map1);
        // 介质识别号
        String identNumber = StringUtil.objectToString(debtInfoForMakeLoan.get("identNumber"));

        if ("on".equals(interfaceStatus)) {

            // 640005借据许可证查询接口
            Map paramsFive = new HashMap();
            paramsFive.put("liceDate", liceDate);
            paramsFive.put("grantCode", grantCode);
            paramsFive.put("areaNumber", areaNumber);
            paramsFive.put("identNumber", identNumber);
            paramsFive.put("seqNo", seqNo);
            paramsFive.put("currency", currency);
            paramsFive.put("iouCode", iouCode);
            paramsFive.put("fecMap", fecMap);
            paramsFive.put("floatMode", floatMode);
            paramsFive.put("overdueRateCalcMode", overdueRateCalcMode);
            paramsFive.put("date", date);
            paramsFive.put("time", time);
            paramsFive.put("userId", userId);
            Map resultFive = requestIntefaceFive(paramsFive);
            String codeFive = StringUtil.objectToString(resultFive.get("code"));
            if ("500".equals(codeFive)) {
                String msgFive = StringUtil.objectToString(resultFive.get("msg"));
                return msgFive;
            }

            // 还款计划接口
            Map paramsSix = new HashMap();
            paramsSix.put("grantCode", grantCode);
            paramsSix.put("areaNumber", areaNumber);
            paramsSix.put("identNumber", identNumber);
            paramsSix.put("date", date);
            paramsSix.put("time", time);
            Map resultSix = requestIntefaceSix(paramsSix);
            String codeSix = StringUtil.objectToString(resultSix.get("code"));
            if ("500".equals(codeSix)) {
                String msgSix = StringUtil.objectToString(resultSix.get("msg"));
                return msgSix;
            }
        }

        Map dataMap = new HashMap();
        SysDept sysDept = getUserInstitutionCodeByUserId(userId);
        debtInfoForMakeLoan.put("institutionCode", sysDept.getParentCode());
        dataMap.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
        dataMap.put("fecList", fecList);
        dataMap.put("userId", userId);
        dataMap.put("enable", 1);
        dataMap.put("liceDate", liceDate);

        // 修改债项发放状态
        Integer grantStatus = 5;
        bizDebtGrantProvider.updateDebtGrantStatus(grantCode, grantStatus);
        // 更新放款主表enable = 1;
        BizMakeLoans bizMakeLoans = updateBizMakeLoans(dataMap);
        // 更新放款台账enable = 1
        dataMap.put("objInr", bizMakeLoans.getId());
        // 记录放款台账 enable = 1
        boolean b = updateMakeLoanStandingBook(dataMap);
        // 交易流水
        bizTRNProvider.saveMakeLoansTRN(dataMap);
        return "发送还款计划成功";
    }

    @Override
    @Transactional
    public String updateRepaymentPlan(Map<String, Object> params) {
        // 日期
        String date = DateUtil.format(new Date());
        // 时间
        String time = DateUtil.formatTime(new Date());
        // 方案信息
        Map<String, Object> debtInfoForMakeLoan = bizDebtGrantProvider.getDebtInfoForMakeLoan(params);
        // 发放id
        String grantId = StringUtil.objectToString(debtInfoForMakeLoan.get("objInr"));
        //发放信息
        BizDebtGrant bizDebtGrant = bizDebtGrantProvider.selectByGrantId(grantId);
        // 发放编码
        String grantCode = bizDebtGrant.getGrantCode();
        // 许可证日期
        String liceDate = bizDebtGrant.getLiceDate() == null ? "" : bizDebtGrant.getLiceDate();
        // 地区号
        Map map1 = new HashMap();
        SysUser sysUser = (SysUser) params.get("sysUser");
        map1.put("userId", sysUser.getId());
        String areaNumber = bizDebtGrantMapper.getAreaNumberByUserId(map1);
        // 介质识别号
        String identNumber = StringUtil.objectToString(debtInfoForMakeLoan.get("identNumber"));
        // 利率信息
        Map fecDataMap = new HashMap();
        fecDataMap.put("objInr", grantId);
        List fecList = bizFECProvider.getBizFECByINR(fecDataMap);
        // 还款计划接口
        Map paramsSix = new HashMap();
        paramsSix.put("grantCode", grantCode);
        paramsSix.put("areaNumber", areaNumber);
        paramsSix.put("identNumber", identNumber);
        paramsSix.put("date", date);
        paramsSix.put("time", time);
        Map resultSix = requestIntefaceSix(paramsSix);
        String codeSix = StringUtil.objectToString(resultSix.get("code"));
        if ("500".equals(codeSix)) {
            String msgSix = StringUtil.objectToString(resultSix.get("msg"));
            return msgSix;
        }

        // 修改债项发放状态
        Integer grantStatus = 5;
        Map dataMap = new HashMap();
        Long userId = Long.valueOf(params.get("userId").toString());
        SysDept sysDept = getUserInstitutionCodeByUserId(userId);
        debtInfoForMakeLoan.put("institutionCode", sysDept.getParentCode());
        dataMap.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
        dataMap.put("fecList", fecList);
        dataMap.put("userId", userId);
        dataMap.put("enable", 2);//0 新增未激活 1 更新未激活为激活 2 新增激活
        dataMap.put("liceDate", liceDate);
        bizDebtGrantProvider.updateDebtGrantStatus(grantCode, grantStatus);
        // 更新放款主表enable = 1;
        BizMakeLoans bizMakeLoans = updateBizMakeLoans(dataMap);
        // 更新放款台账enable = 1
        dataMap.put("objInr", bizMakeLoans.getId());
        // 记录放款台账 enable = 1
        boolean b = updateMakeLoanStandingBook(dataMap);
        // 交易流水
        bizTRNProvider.saveMakeLoansTRN(dataMap);
        return "还款计划发送成功";
    }

    /**
     * @Description: 废弃放款
     * @Param: [params]
     * @return: java.lang.String
     */
    @Override
    @Transactional
    public String discardMakeLoans(Map<String, Object> params) {

        logger.debug("===================================================discard makeloans start");
        // 时间
        String date = DateUtil.format(new Date());
        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        // 废弃理由
        String remark = StringUtil.objectToString(params.get("commentInfo"));
        BizDebtGrant bizDebtGrant1 = new BizDebtGrant();
        bizDebtGrant1.setGrantCode(grantCode);
        // 发放信息inr
        BizDebtGrant bizDebtGrant = bizDebtGrantProvider.selectOne(bizDebtGrant1);
        Long grantId = bizDebtGrant.getId();
        // 介质识别号
        String identNumber = bizDebtGrant.getIdentNumber();
        // 方案编号
        String debtCode = bizDebtGrant.getDebtCode();
        // 产品类型
        String businessTypes = bizDebtGrant.getBusinessTypes();
        // 方案信息
        Map debtMap = new HashMap();
        debtMap.put("debtCode", debtCode);
        debtMap.put("grantCode", grantCode);
        debtMap.put("businessTypes", businessTypes);
        Map debtInfoForMakeLoan = bizDebtGrantProvider.getDebtInfoForMakeLoan(debtMap);
        // 地区号
        Map map1 = new HashMap();
        String userId = StringUtil.objectToString(params.get("userId"));
        map1.put("userId", userId);
        String areaNumber = bizDebtGrantMapper.getAreaNumberByUserId(map1);
        // 利率信息
        Map fecDataMap = new HashMap();
        fecDataMap.put("objInr", grantId);
        List fecList = bizFECProvider.getBizFECByINR(fecDataMap);
        // 币种
        String currency = "";
        // 金额
        String paymentAmt = "";

        Map fecMap = new HashMap();
        if (fecList != null && fecList.size() > 0) {
            fecMap = (Map) fecList.get(0);
            currency = StringUtil.objectToString(fecMap.get("currency"));
            paymentAmt = StringUtil.objectToString(fecMap.get("paymentAmt"));
        }
        // 调用释放额度接口
        Map paramsThree = new HashMap();
        paramsThree.put("grantCode", grantCode);
        paramsThree.put("areaNumber", areaNumber);
        paramsThree.put("debtInfoForMakeLoan", debtInfoForMakeLoan);
        paramsThree.put("identNumber", identNumber);
        paramsThree.put("date", date);
        paramsThree.put("currency", currency);
        paramsThree.put("fecMap", fecMap);
        paramsThree.put("type", "0");//额度标志 1占用  0释放
        Map resultThree = requestIntefaceSeven(paramsThree);
        String codeThree = StringUtil.objectToString(resultThree.get("code"));
        if ("500".equals(codeThree)) {
            String msgThree = StringUtil.objectToString(resultThree.get("msg"));
            return msgThree;
        }

        // 解除担保关系接口
        Map paramsFour = new HashMap();
        paramsFour.put("grantCode", grantCode);
        paramsFour.put("identNumber", identNumber);
        paramsFour.put("type", "0004");//0002 生效   0004解除  0009结清
        Map resultFour = requestIntefaceEight(paramsFour);
        String codeFour = StringUtil.objectToString(resultFour.get("code"));
        if ("500".equals(codeFour)) {
            String msgFour = StringUtil.objectToString(resultFour.get("msg"));
            return msgFour;
        }

        Map releaseMap = new HashMap();
        releaseMap.put("grantId", grantId);
        releaseMap.put("paymentAmt", paymentAmt);
        releaseMap.put("debtCode", debtCode);
        releaseMap.put("remark", remark);

        // 释放方案余额
        Map resultDebtAmt = releaseDebtAmt(releaseMap);
        String codeDebtAmt = StringUtil.objectToString(resultDebtAmt.get("code"));
        if ("500".equals(codeDebtAmt)) {
            String msgDebtAmt = StringUtil.objectToString(resultDebtAmt.get("msg"));
            return msgDebtAmt;
        }
        // 释放方案可发放笔数
        Map resultDebtTDWLN = releaseDebtTDWLN(releaseMap);
        String codeDebtTDWLN = StringUtil.objectToString(resultDebtTDWLN.get("code"));
        if ("500".equals(codeDebtTDWLN)) {
            String msgDebtTDWLN = StringUtil.objectToString(resultDebtTDWLN.get("msg"));
            return msgDebtTDWLN;
        }
        // 执行任务
        String taskId = StringUtil.objectToString(params.get("taskId"));
        bizProStatementProvider.completeTaskByTaskId(userId, taskId);

        // 释放发放余额 保存废弃意见 修改发放状态
        Map resultGrantAmt = releaseGrantAmt(releaseMap);
        String codeGrantAmt = StringUtil.objectToString(resultGrantAmt.get("code"));
        if ("500".equals(codeGrantAmt)) {
            String msgGrantAmt = StringUtil.objectToString(resultGrantAmt.get("msg"));
            return msgGrantAmt;
        }
        logger.debug("===================================================discard makeloans end");
        return "废弃成功";
    }

    @Override
    public Map invokeInterface(String grantCode,String actionSet,String action,String args,Long objInr) {

        // 返回结果
        Map resultMap = new HashMap();
        String msg = ""; // 错误信息
        String code = ""; // 响应码值  200 接口响应成功 500 接口平台或者被调用接口失败
        String results = "";

        // 请求前记录
        BizInterfaceResult bizInterfaceResult = new BizInterfaceResult(objInr,grantCode, "", action, new Date(), args);
        BizInterfaceResult returnResult = bizInterfaceResultProvider.update(bizInterfaceResult);
        logger.debug("+++++++++++++++++++++++++++++" + action + " start " + "params is " + args.toString());

        // 返回结果判断
        Map returnMap = HttpUtil.invokeInfExecute2(actionSet, actionSet, new String[]{"" + args + ""});
        code = StringUtil.objectToString(returnMap.get("code"));

        if ("500".equals(code)) {
            msg = StringUtil.objectToString(returnMap.get("msg"));
        } else {
            results = StringUtil.objectToString(returnMap.get("results"));
        }

        // 响应后记录
        BizInterfaceResult responseResult = new BizInterfaceResult(returnResult.getId(), results, new Date(), msg);
        bizInterfaceResultProvider.update(responseResult);
        logger.debug("+++++++++++++++++++++++++++++" + action + " end " + "code is " + code);

        resultMap.put("code", code);
        resultMap.put("objInr", returnResult.getId());
        resultMap.put("msg", msg);
        resultMap.put("results", results);
        return resultMap;
    }

    /**
     * @Description: 查询接口是否请求成功
     * @Param: [params]
     * @return: java.util.Map
     */
    private Map queryInterfaceIsSuccess(String grantCode, String action) {

        Map resultMap = new HashMap();
        // 获取请求参数
        String code = ""; // code状态 100 表示不存在该条记录 200 表示该条记录存在并且请求成功 400 表示该条记录存在但是请求失败
        String responseStatus = ""; // 接口请求状态 -1 表示已经请求成功 其他失败
        String objInr = ""; // 业务表主键

        // 查询接口是否已经请求成功
        Map statusMap = queryInterfaceResult(grantCode, action);
        if (statusMap != null) {
            responseStatus = StringUtil.objectToString(statusMap.get("responseStatus"));
            objInr = StringUtil.objectToString(statusMap.get("objInr"));
            if (BizContant.INTERFACE_RESULT_SUCCESS.equals(responseStatus)) {
                code = "200";
            }else{
                code = "400";
            }
        }else{
            code = "100";
        }

        resultMap.put("code", code);
        resultMap.put("responseStatus", responseStatus);
        resultMap.put("objInr", objInr);
        return resultMap;
    }

    /**
     * @Description: 64002贷款合同查询接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceOne(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_ONE;
        String action = BizContant.INTERFACE_NAME_ONE;
        Long objInr = null;

        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
        }

        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 日期
        String date = StringUtil.objectToString(params.get("date"));
        // 时间
        String time = StringUtil.objectToString(params.get("time"));
        // 大交易号
        int big64002 = bizCntProvider.getNextNumber("big64002");
        // 小交易号
        int small64002 = bizCntProvider.getNextNumber("small64002") - 1;
        ArrayList paramsOne = new ArrayList();
        paramsOne.add(areaNumber);
        paramsOne.add(big64002);
        paramsOne.add(small64002);
        paramsOne.add(identNumber);
        paramsOne.add(date);
        paramsOne.add(time);
        String params64002 = joinInterfaceParams(paramsOne);

        // 请求接口
        Map returnResult = invokeInterface(grantCode,actionSet,action,params64002,objInr);
        String returnCode = StringUtil.objectToString(returnResult.get("code"));

        if ("500".equals(returnCode)) {
            map.put("code", "500");
            map.put("msg", "贷款合同查询接口调用失败，请联系管理员！");
            return map;
        }

        objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
        String inte64002 = StringUtil.objectToString(returnResult.get("results"));
        JSONArray jSONArray = JSONObject.parseArray(inte64002);

        if (jSONArray != null && jSONArray.size() > 0) {
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                String status = jsonObject.getString("STATUS");
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String transok = jsonObject.getString("TRANSOK");
                if ("0".equals(transok)) {
                    if ("1".equals(status)) {
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                        code = "200";
                    } else if ("2".equals(status)) {
                        msg = "贷款合同状态为冻结,贷款账号为:" + identNumber;
                    } else if ("8".equals(status)) {
                        msg = "贷款合同状态为发放完毕,贷款账号为:" + identNumber;
                    } else if ("9".equals(status)) {
                        msg = "贷款合同状态为已撤销,贷款账号为:" + identNumber;
                    } else if ("0".equals(status)) {
                        msg = "贷款合同未完成补录，请核查后重新发起发放,贷款账号为:" + identNumber;
                    } else {
                        msg = "贷款合同状态异常,贷款账号为:" + identNumber;
                    }
                } else {
                    // 根据错误码查找错误信息并返回
                    String errNo = jsonObject.getString("ERR_NO");
                    String cbsErrorMassage = getCbsErrorMassage(errNo);
                    msg = "错误信息:" + cbsErrorMassage + ",贷款账号:" + identNumber;
                }
            }
        } else {
            msg = "贷款合同查询接口返回数据解析异常";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    /**
     * @Description: 64005创建借据许可证接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceTwo(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_TWO;
        String action = BizContant.INTERFACE_NAME_TWO;
        Long objInr = null;

        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
        }

        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 许可证序号
        String seqNo = StringUtil.objectToString(params.get("seqNo"));
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 币种
        String currency = StringUtil.objectToString(params.get("currency"));
        // 借据编号
        String iouCode = StringUtil.objectToString(params.get("iouCode"));
        // 利率信息
        Map fecMap = (Map) params.get("fecMap");
        // 利率浮动方式 百分比3、加点2、固定利率为1
        String floatMode = StringUtil.objectToString(params.get("floatMode"));
        // 逾期加点
        String overdueRateCalcMode = StringUtil.objectToString(params.get("overdueRateCalcMode"));
        // 日期
        String date = StringUtil.objectToString(params.get("date"));
        // 时间
        String time = StringUtil.objectToString(params.get("time"));
        // 大交易号
        int big64005 = bizCntProvider.getNextNumber("big64005");
        // 协议子序号
        int son64005 = bizCntProvider.getNextNumber("son64005") - 1;
        List paramsTwo = new ArrayList();
        paramsTwo.add(areaNumber);
        paramsTwo.add(big64005);
        paramsTwo.add("4");// 动作标识 1查询 4新增
        paramsTwo.add(seqNo);
        paramsTwo.add(identNumber);
        paramsTwo.add(""); // 许可证日期--发放日期
        paramsTwo.add(getCurrencyNo(currency));
        paramsTwo.add(iouCode);
        paramsTwo.add("10202");// 10202为租金保理业务产品种类
        paramsTwo.add(selectProductSeqnum("10202",currency)); // 产品序号
        String paymentAmt = MathUtil.formatToNumber(new BigDecimal(StringUtil.objectToString(fecMap.get("paymentAmt"))));
        paramsTwo.add(paymentAmt);// 发放金额
        paramsTwo.add(StringUtil.objectToString(fecMap.get("irSign"))); // 计息标志
        paramsTwo.add(StringUtil.objectToString(fecMap.get("chgCycleUnit"))); // 利率变动周期单位
        paramsTwo.add(StringUtil.objectToString(fecMap.get("varCycle")));// 利率变动周期
        paramsTwo.add(StringUtil.objectToString(fecMap.get("rateVal"))); // 利率
        paramsTwo.add(StringUtil.objectToString(fecMap.get("irBk")));// 利率基准
        paramsTwo.add("".equals(floatMode) ? 1 : floatMode);
        paramsTwo.add(StringUtil.objectToString(fecMap.get("floatingRate"))); // 浮动率
        paramsTwo.add(StringUtil.objectToString(fecMap.get("irSign"))); // 计息标志
        paramsTwo.add(StringUtil.objectToString(fecMap.get("chgCycleUnit"))); // 利率变动周期单位
        paramsTwo.add(StringUtil.objectToString(fecMap.get("varCycle")));// 利率变动周期
        paramsTwo.add(StringUtil.objectToString(fecMap.get("rateVal"))); // 利率
        paramsTwo.add(StringUtil.objectToString(fecMap.get("irBk")));// 利率基准
        paramsTwo.add("".equals(overdueRateCalcMode) ? 1 : floatMode); // 逾期利率浮动方式 百分比3、加点2、固定利率为1
        paramsTwo.add(StringUtil.objectToString(fecMap.get("overdueIncrRatio")));
        paramsTwo.add(date);
        paramsTwo.add(time);
        paramsTwo.add(StringUtil.objectToString(fecMap.get("term"))); // 利率期限
        paramsTwo.add(StringUtil.objectToString(fecMap.get("term"))); // 利率期限
        paramsTwo.add(StringUtil.objectToString(fecMap.get("floatDirection"))); // 利率浮动方向 1 上浮 2 下浮
        paramsTwo.add(son64005);// 协议子序号
        String params64005 = joinInterfaceParams(paramsTwo);

        // 请求接口
        Map returnResult = invokeInterface(grantCode,actionSet,action,params64005,objInr);
        String returnCode = StringUtil.objectToString(returnResult.get("code"));

        if ("500".equals(returnCode)) {
            map.put("code", "500");
            map.put("msg", "创建借据许可证接口调用失败，请联系管理员！");
            return map;
        }

        objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
        String inte64005 = StringUtil.objectToString(returnResult.get("results"));
        JSONArray jSONArray = JSONObject.parseArray(inte64005);

        if (jSONArray != null && jSONArray.size() > 0) {
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String transok = jsonObject.getString("TRANSOK");
                String status = jsonObject.getString("STATUS");
                if ("0".equals(transok)) {
                    if ("3".equals(status)) {
                        msg = "借据许可证创建状态为异常,贷款账号为:" + identNumber;
                    } else if ("4".equals(status)) {
                        msg = "借据许可证创建状态为废弃,贷款账号为:" + identNumber;
                    } else {
                        String liceDate = jsonObject.getString("LICEDATE");
                        String returnSeqNo = jsonObject.getString("SEQNO");
                        map.put("liceDate", liceDate);
                        map.put("returnSeqNo", returnSeqNo);
                        code = "300";
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                    }
                } else {
                    // 根据错误码查找错误信息并返回
                    String errNo = jsonObject.getString("ERR_NO");
                    String cbsErrorMassage = getCbsErrorMassage(errNo);
                    msg = "错误信息:" + cbsErrorMassage + "贷款账号:" + identNumber;
                }
            }
        } else {
            msg = "创建借据许可证接口返回数据异常";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    /**
     * @Description: 信贷额度交易接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceThree(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_THREE;
        String action = BizContant.INTERFACE_NAME_THREE;
        Long objInr = null;
        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
        }

        // 额度交易接口参数
        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 组织机构代码
        String orgCode = areaNumber + "000";
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 交易日期
        String date = StringUtil.objectToString(params.get("date"));
        // 币种
        String currency = StringUtil.objectToString(params.get("currency"));
        // 额度标志 1占用  0释放
        String type = StringUtil.objectToString(params.get("type"));
        // 利率信息
        Map fecMap = (Map) (params.get("fecMap"));
        // 方案信息
        Map debtInfoForMakeLoan = (Map) (params.get("debtInfoForMakeLoan"));
        // 产品种类
        String businessTypes = StringUtil.objectToString(debtInfoForMakeLoan.get("businessTypes"));
        // 折算牌价
        debtInfoForMakeLoan.put("convertedPrice",StringUtil.objectToString(fecMap.get("convertedPrice")));
        // 发放金额
        debtInfoForMakeLoan.put("grantAmt",StringUtil.objectToString(fecMap.get("paymentAmt")));
        ArrayList org64003 = new ArrayList();
        org64003.add(identNumber);
        org64003.add(grantCode);
        org64003.add(date);
        org64003.add(orgCode);
        org64003.add(businessTypes);
        org64003.add(getCurrencyNo(currency));
        org64003.add(StringUtil.objectToString(fecMap.get("paymentAmt")));
        org64003.add(type);
        Map guaranteeInfoList = getGuaranteeInfoList(grantCode);
        org64003.add(StringUtil.objectToString(guaranteeInfoList.get("guaranteeType")));// 担保方式
        // 用信主体
        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        org64003.add(customInfoParams);
        org64003.add("".equals(guarantee) ? "no" : guarantee);// 保证担保
        org64003.add("".equals(mortgage) ? "no" : mortgage);//抵押质押担保方式
        org64003.add("".equals(credit) ? "no" : credit);// 信用担保方式
        String params64003 = joinInterfaceParams(org64003);

        Map returnResult = invokeInterface(grantCode,actionSet,action,params64003,objInr);
        String returnCode = StringUtil.objectToString(returnResult.get("code"));

        if ("500".equals(returnCode)) {
            map.put("code", "500");
            map.put("msg", "额度占用接口调用失败，请联系管理员！");
            return map;
        }

        objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
        String inte64003 = StringUtil.objectToString(returnResult.get("results"));
        JSONArray jSONArray = JSONObject.parseArray(inte64003);
        if (jSONArray != null && jSONArray.size() > 0) {
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                String RspCode = jsonObject.getString("RspCode");
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String ErrorNo = jsonObject.getString("ErrorNo");
                if ("000000".equals(RspCode)) {
                    if ("1".equals(ErrorNo)) {
                        code = "200";
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                    } else {
                        String ErrorInfo = jsonObject.getString("ErrorInfo");
                        msg = "额度占用失败,错误信息为: " + ErrorInfo;
                    }
                } else {
                    String RspMsg = jsonObject.getString("RspMsg");
                    msg = "额度占用失败,错误信息为: " + RspMsg;
                }
            }
        } else {
            msg = "额度占用失败,错误信息为: 额度占用接口返回数据异常";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }


    /**
     * @Description: 信贷额度释放接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceSeven(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_THREE;
        String action = BizContant.INTERFACE_NAME_SEVEN;
        Long objInr = null;
        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
        }

        // 额度交易接口参数
        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 组织机构代码
        String orgCode = areaNumber + "000";
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 交易日期
        String date = StringUtil.objectToString(params.get("date"));
        // 币种
        String currency = StringUtil.objectToString(params.get("currency"));
        // 额度标志 1占用  0释放
        String type = StringUtil.objectToString(params.get("type"));
        // 利率信息
        Map fecMap = (Map) (params.get("fecMap"));
        // 方案信息
        Map debtInfoForMakeLoan = (Map) (params.get("debtInfoForMakeLoan"));
        // 产品种类
        String businessTypes = StringUtil.objectToString(debtInfoForMakeLoan.get("businessTypes"));
        // 折算牌价
        debtInfoForMakeLoan.put("convertedPrice",StringUtil.objectToString(fecMap.get("convertedPrice")));
        // 发放金额
        debtInfoForMakeLoan.put("grantAmt",StringUtil.objectToString(fecMap.get("paymentAmt")));
        ArrayList org64003 = new ArrayList();
        org64003.add(identNumber);
        org64003.add(grantCode);
        org64003.add(date);
        org64003.add(orgCode);
        org64003.add(businessTypes);
        org64003.add(getCurrencyNo(currency));
        org64003.add(StringUtil.objectToString(fecMap.get("paymentAmt")));
        org64003.add(type);
        Map guaranteeInfoList = getGuaranteeInfoList(grantCode);
        org64003.add(StringUtil.objectToString(guaranteeInfoList.get("guaranteeType")));// 担保方式
        // 用信主体
        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        org64003.add(customInfoParams);
        org64003.add("".equals(guarantee) ? "no" : guarantee);// 保证担保
        org64003.add("".equals(mortgage) ? "no" : mortgage);//抵押质押担保方式
        org64003.add("".equals(credit) ? "no" : credit);// 信用担保方式
        String params64003 = joinInterfaceParams(org64003);

        Map returnResult = invokeInterface(grantCode,actionSet,action,params64003,objInr);
        String returnCode = StringUtil.objectToString(returnResult.get("code"));

        if ("500".equals(returnCode)) {
            map.put("code", "500");
            map.put("msg", "额度释放接口调用失败，请联系管理员！");
            return map;
        }

        objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
        String inte64003 = StringUtil.objectToString(returnResult.get("results"));
        JSONArray jSONArray = JSONObject.parseArray(inte64003);
        if (jSONArray != null && jSONArray.size() > 0) {
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                String RspCode = jsonObject.getString("RspCode");
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String ErrorNo = jsonObject.getString("ErrorNo");
                if ("000000".equals(RspCode)) {
                    if ("1".equals(ErrorNo)) {
                        code = "200";
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                    } else {
                        String ErrorInfo = jsonObject.getString("ErrorInfo");
                        msg = "额度释放失败,错误信息为: " + ErrorInfo;
                    }
                } else {
                    String RspMsg = jsonObject.getString("RspMsg");
                    msg = "额度释放失败,错误信息为: " + RspMsg;
                }
            }
        } else {
            msg = "额度释放失败,错误信息为: 额度释放接口返回数据异常";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }


    /**
     * @Description: 业务与担保合同关系接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceFour(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_FOUR;
        String action = BizContant.INTERFACE_NAME_FOUR;
        Long objInr = null;
        String responseStatus = "";
        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
            responseStatus  = StringUtil.objectToString(successMap.get("responseStatus"));
        }

        // 查询担保合同信息
        List guaranteeContract = getGuaranteeContract(grantCode);
        if (guaranteeContract != null && guaranteeContract.size() > 0) {
            int startNum = 0;
            int size = guaranteeContract.size();
            String identNumber = StringUtil.objectToString(params.get("identNumber"));
            String type = StringUtil.objectToString(params.get("type"));
            if (!"".equals(responseStatus)) {
                startNum = Integer.valueOf(responseStatus.toString());
            }
            for (int a = startNum; a < size; a++) {
                Map contract = (Map) guaranteeContract.get(a);
                String amount = "";
                String clearRatioAmt = StringUtil.objectToString(contract.get("clearRatioAmt"));
                String notClearAmt = StringUtil.objectToString(contract.get("notClearAmt"));
                if(clearRatioAmt != null && !"".equals(clearRatioAmt)){
                    amount = clearRatioAmt;
                }
                if(notClearAmt != null && !"".equals(notClearAmt)){
                    amount = notClearAmt;
                }
                ArrayList paramsFour = new ArrayList();
                paramsFour.add(StringUtil.objectToString(contract.get("guarantorCustId")));// 申请人客户编号
                paramsFour.add(identNumber);//介质识别号
                paramsFour.add(StringUtil.objectToString(contract.get("warrantyContractNumber")));//担保合同编号
                paramsFour.add(amount);//担保金额
                paramsFour.add(type);// 002 生效   004解除  009结清
                String params64001 = joinInterfaceParams(paramsFour);

                Map returnResult = invokeInterface(grantCode,actionSet,action,params64001,objInr);
                String returnCode = StringUtil.objectToString(returnResult.get("code"));

                if ("500".equals(returnCode)) {
                    map.put("code", "500");
                    map.put("msg", "业务与担保合同关系接口调用失败，请联系管理员！");
                    return map;
                }
                objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
                String inte64001 = StringUtil.objectToString(returnResult.get("results"));
                JSONArray jSONArray = JSONObject.parseArray(inte64001);
                for (int j = 0; j < jSONArray.size(); j++) {
                    JSONObject jsonObject = jSONArray.getJSONObject(j);
                    String RspCode = jsonObject.getString("RspCode");
                    String ErrorNo = jsonObject.getString("ErrorNo");
                    if ("000000".equals(RspCode)) {
                        if ("1".equals(ErrorNo)) {
                            if (a == size - 1) {
                                code = "200";
                                bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                            }
                        } else {
                            String ErrorInfo = jsonObject.getString("ErrorInfo");
                            bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), String.valueOf(a)));
                            msg = "业务与担保合同建立失败,错误信息为: " + ErrorInfo;
                            map.put("code", code);
                            map.put("msg", msg);
                            return map;
                        }
                    } else {
                        String ErrorInfo = jsonObject.getString("ErrorInfo");
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), String.valueOf(a)));
                        msg = "业务与担保合同建立失败,错误信息为: " + ErrorInfo;
                        map.put("code", code);
                        map.put("msg", msg);
                        return map;
                    }
                }
            }
        } else {
            code = "200";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }


    /**
     * @Description: 业务与担保合同关系解除接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceEight(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放审核编号
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_FOUR;
        String action = BizContant.INTERFACE_NAME_EIGHT;
        Long objInr = null;
        String responseStatus = "";
        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
            responseStatus  = StringUtil.objectToString(successMap.get("responseStatus"));
        }

        // 查询担保合同信息
        List guaranteeContract = getGuaranteeContract(grantCode);
        if (guaranteeContract != null && guaranteeContract.size() > 0) {
            int startNum = 0;
            int size = guaranteeContract.size();
            String identNumber = StringUtil.objectToString(params.get("identNumber"));
            String type = StringUtil.objectToString(params.get("type"));
            if (!"".equals(responseStatus)) {
                startNum = Integer.valueOf(responseStatus.toString());
            }
            for (int a = startNum; a < size; a++) {
                Map contract = (Map) guaranteeContract.get(a);
                String amount = "";
                String clearRatioAmt = StringUtil.objectToString(contract.get("clearRatioAmt"));
                String notClearAmt = StringUtil.objectToString(contract.get("notClearAmt"));
                if(clearRatioAmt != null && !"".equals(clearRatioAmt)){
                    amount = clearRatioAmt;
                }
                if(notClearAmt != null && !"".equals(notClearAmt)){
                    amount = notClearAmt;
                }
                ArrayList paramsFour = new ArrayList();
                paramsFour.add(StringUtil.objectToString(contract.get("guarantorCustId")));// 申请人客户编号
                paramsFour.add(identNumber);//介质识别号
                paramsFour.add(StringUtil.objectToString(contract.get("warrantyContractNumber")));//担保合同编号
                paramsFour.add(amount);//担保金额
                paramsFour.add(type);// 002 生效   004解除  009结清
                String params64001 = joinInterfaceParams(paramsFour);

                Map returnResult = invokeInterface(grantCode,actionSet,action,params64001,objInr);
                String returnCode = StringUtil.objectToString(returnResult.get("code"));

                if ("500".equals(returnCode)) {
                    map.put("code", "500");
                    map.put("msg", "业务与担保合同关系解除接口调用失败，请联系管理员！");
                    return map;
                }
                objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
                String inte64001 = StringUtil.objectToString(returnResult.get("results"));
                JSONArray jSONArray = JSONObject.parseArray(inte64001);
                for (int j = 0; j < jSONArray.size(); j++) {
                    JSONObject jsonObject = jSONArray.getJSONObject(j);
                    String RspCode = jsonObject.getString("RspCode");
                    String ErrorNo = jsonObject.getString("ErrorNo");
                    if ("000000".equals(RspCode)) {
                        if ("1".equals(ErrorNo)) {
                            if (a == size - 1) {
                                code = "200";
                                bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                            }
                        } else {
                            String ErrorInfo = jsonObject.getString("ErrorInfo");
                            bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), String.valueOf(a)));
                            msg = "业务与担保合同解除失败,错误信息为: " + ErrorInfo;
                            map.put("code", code);
                            map.put("msg", msg);
                            return map;
                        }
                    } else {
                        String ErrorInfo = jsonObject.getString("ErrorInfo");
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), String.valueOf(a)));
                        msg = "业务与担保合同解除失败,错误信息为: " + ErrorInfo;
                        map.put("code", code);
                        map.put("msg", msg);
                        return map;
                    }
                }
            }
        } else {
            code = "200";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }


    /**
     * @Description: 64005查询借据许可证接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceFive(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        // 发放编码
        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_TWO;
        String action = BizContant.INTERFACE_NAME_FIVE;
        Long objInr = null;
        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
        }

        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 许可证序号
        String seqNo = StringUtil.objectToString(params.get("seqNo"));
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 许可证日期
        String liceDate = StringUtil.objectToString(params.get("liceDate"));
        // 币种
        String currency = StringUtil.objectToString(params.get("currency"));
        // 用户编码
        String userId = StringUtil.objectToString(params.get("userId"));
        // 借据编号
        String iouCode = StringUtil.objectToString(params.get("iouCode"));
        // 利率信息
        Map fecMap = (Map) params.get("fecMap");
        // 利率浮动方式 百分比3、加点2、固定利率为1
        String floatMode = StringUtil.objectToString(params.get("floatMode"));
        // 逾期加点
        String overdueRateCalcMode = StringUtil.objectToString(params.get("overdueRateCalcMode"));
        // 日期
        String date = StringUtil.objectToString(params.get("date"));
        // 时间
        String time = StringUtil.objectToString(params.get("time"));

        ArrayList org64005 = new ArrayList();
        org64005.add(areaNumber);
        org64005.add(bizCntProvider.getNextNumber("big64005"));
        org64005.add("1");//动作标识 1查询 4新增
        org64005.add(seqNo);//许可证序号
        org64005.add(identNumber);// 介质识别号
        org64005.add(liceDate);
        org64005.add(getCurrencyNo(currency)); // 币种
        org64005.add(iouCode); // 借据编号
        org64005.add("10202");// 10202为租金保理业务产品种类
        org64005.add(selectProductSeqnum("10202",currency)); // 产品序号
        String paymentAmt = MathUtil.formatToNumber(new BigDecimal(StringUtil.objectToString(fecMap.get("paymentAmt"))));
        org64005.add(paymentAmt);// 发放金额
        org64005.add(StringUtil.objectToString(fecMap.get("irSign"))); // 计息标志
        org64005.add(StringUtil.objectToString(fecMap.get("chgCycleUnit"))); // 利率变动周期单位
        org64005.add(StringUtil.objectToString(fecMap.get("varCycle")));// 利率变动周期
        org64005.add(StringUtil.objectToString(fecMap.get("rateVal"))); // 利率
        org64005.add(StringUtil.objectToString(fecMap.get("irBk")));// 利率基准
        org64005.add("".equals(floatMode) ? 1 : floatMode); // 利率浮动方式 百分比3、加点2、固定利率为1
        org64005.add(StringUtil.objectToString(fecMap.get("floatingRate"))); // 浮动率
        org64005.add(StringUtil.objectToString(fecMap.get("irSign"))); // 计息标志
        org64005.add(StringUtil.objectToString(fecMap.get("chgCycleUnit"))); // 利率变动周期单位
        org64005.add(StringUtil.objectToString(fecMap.get("varCycle")));// 利率变动周期
        org64005.add(StringUtil.objectToString(fecMap.get("rateVal"))); // 利率
        org64005.add(StringUtil.objectToString(fecMap.get("irBk")));// 利率基准
        org64005.add("".equals(overdueRateCalcMode) ? 1 : floatMode); // 逾期利率浮动方式 百分比3、加点2、固定利率为1
        org64005.add(StringUtil.objectToString(fecMap.get("overdueIncrRatio"))); // 逾期加点
        org64005.add(date); // 日期
        org64005.add(time); // 时间
        org64005.add(StringUtil.objectToString(fecMap.get("term"))); // 利率期限
        org64005.add(StringUtil.objectToString(fecMap.get("term"))); // 利率期限
        org64005.add(StringUtil.objectToString(fecMap.get("floatDirection"))); // 利率浮动方向 1 上浮 2 下浮
        org64005.add(bizCntProvider.getNextNumber("small64005") - 1);
        String params64005 = joinInterfaceParams(org64005);

        Map returnResult = invokeInterface(grantCode,actionSet,action,params64005,objInr);
        String returnCode = StringUtil.objectToString(returnResult.get("code"));

        if ("500".equals(returnCode)) {
            map.put("code", "500");
            map.put("msg", "借据许可证查询接口调用失败，请联系管理员！");
            return map;
        }
        objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
        String inte64005 = StringUtil.objectToString(returnResult.get("results"));
        JSONArray jSONArray = JSONObject.parseArray(inte64005);
        if (jSONArray != null && jSONArray.size() > 0) {
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                String status = jsonObject.getString("STATUS");
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String transok = jsonObject.getString("TRANSOK");
                if ("0".equals(transok)) {
                    // 根据错误码查找错误信息并返回
                    if ("0".equals(status)) {
                        msg = "借据许可证状态为未补录,贷款账号为:"+identNumber;
                    } else if ("1".equals(status)) {
                        msg = "借据许可证状态为未处理,贷款账号为:"+identNumber;
                    } else if ("2".equals(status)) {
                        code = "200";
                        bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                        logger.debug("64005查询接口调用成功");
                    } else if ("3".equals(status)) {
                        msg = "借据许可证状态为异常,贷款账号为:"+identNumber;
                    } else if ("4".equals(status)) {
                        msg = "借据许可证状态为已作废,贷款账号为:"+identNumber;
                        logger.debug("==================================discard makeloans start");
                        // 发起废弃流程 根据不同角色发起不同流程
                        Map<String, Object> userNameAndRoleName = sysUserProvider.getUserNameAndRoleName(Long.valueOf(userId));
                        String roleId = StringUtil.objectToString(userNameAndRoleName.get("roleId"));
                        Map param1 = new HashMap();
                        param1.put("userId", userId);
                        param1.put("mBizId", grantCode);
                        if("451117558502785025".equals(roleId)){
                            param1.put("pdid", "fhfkfqprocess");
                        }else if("451117558506979339".equals(roleId)){
                            param1.put("pdid", "zhfkfqprocess");
                        }
                        param1.put("debtCode",grantCode);
                        bizProStatementProvider.createAndstartProcess(param1);
                        // 将状态置为冻结
                        bizDebtGrantProvider.updateDebtGrantStatus(grantCode,11);
                    } else {
                        msg = "借据许可证状态为异常,贷款账号为:"+identNumber;
                    }
                } else {
                    String errNo = jsonObject.getString("ERR_NO");
                    String cbsErrorMassage = getCbsErrorMassage(errNo);
                    msg = "错误信息:" + cbsErrorMassage +",贷款账号:" + identNumber;
                }
            }
        }else{
            msg = "借据许可证接口返回数据异常";
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    /**
     * @Description: 发送还款计划接口
     * @Param: []
     * @return: java.util.Map
     */
    private Map requestIntefaceSix(Map params) {

        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "500";
        String msg = "";

        String grantCode = StringUtil.objectToString(params.get("grantCode"));
        String actionSet = BizContant.INTERFACE_NAME_SIX;
        String action = BizContant.INTERFACE_NAME_SIX;
        Long objInr = null;
        String responseStatus = "";

        // 判断接口是否已经请求成功
        Map successMap = queryInterfaceIsSuccess(grantCode, action);
        String successCode = StringUtil.objectToString(successMap.get("code"));
        if ("200".equals(successCode)) {
            map.put("code", "200");
            return map;
        }else if("400".equals(successCode)){
            objInr = Long.valueOf(StringUtil.objectToString(successMap.get("objInr")));
            responseStatus = StringUtil.objectToString(successMap.get("responseStatus"));
        }

        // 地区号
        String areaNumber = StringUtil.objectToString(params.get("areaNumber"));
        // 介质识别号
        String identNumber = StringUtil.objectToString(params.get("identNumber"));
        // 日期
        String date = StringUtil.objectToString(params.get("date"));
        // 时间
        String time = StringUtil.objectToString(params.get("time"));
        // 调用64004查询接口
        Map dataMap = new HashMap();
        dataMap.put("grantCode", grantCode);
        dataMap.put("enable", 1);
        //还本计划
        List debtInfoForRepaymentPricinal = bizRepaymentPricinalPlanProvider.getDebtInfoForRepaymentPricinal(dataMap);
        // 还息计划
        List debtInfoForRepaymentLoan = bizRepaymentLoanPlanProvider.getDebtInfoForRepaymentLoan(dataMap);
        Map repaymentMap = new HashMap();
        repaymentMap.put("repaymentPricinalList", debtInfoForRepaymentPricinal);
        repaymentMap.put("repaymentLoanList", debtInfoForRepaymentLoan);
        List paramsList = transInter64004Params(repaymentMap);
        int pointsDataLimit = 20;//限制条数
        int startNum1 = 0; // 还本发送批次 第几次
        int size = paramsList.size();
        int part = getCeilInt(size, pointsDataLimit);// 如果存在21条数据 则分两次发送

        if (!"".equals(responseStatus)) {
            startNum1 = Integer.valueOf(responseStatus);
            paramsList.subList(0,startNum1 * pointsDataLimit).clear();
        }
        for (int a = startNum1; a < part; a++) {
            String params64004 = "";
            String type = "2";// 操作类型 1 查询 2 更改 3 新增 4 删除(第一次操作类型为更改,后续为新增)
            if (a != 0) {
                type = "3";
            }
            // 对于最后一次只发送size大小 不是最后一次发送20条
            if (a != part - 1) {
                List listPage = paramsList.subList(0, pointsDataLimit);
                String pricinalParams = joinInter64004Params(listPage);
                ArrayList org64004 = new ArrayList();
                org64004.add(areaNumber);//地区号
                org64004.add(identNumber);//介质识别号
                org64004.add("1");//借据序号
                org64004.add(bizCntProvider.getNextNumber("big64004"));//大交易号
                org64004.add(bizCntProvider.getNextNumber("small64004"));// 小交易号
                org64004.add(date);// 日期
                org64004.add(time);// 时间
                org64004.add(" ");
                org64004.add("0");
                org64004.add(type);
                org64004.add(pricinalParams);
                params64004 = joinInterfaceParams(org64004);
                paramsList.subList(0, pointsDataLimit).clear();
            } else {
                int lastSize = paramsList.size();
                List listPage = paramsList.subList(0, lastSize);
                String pricinalParams = joinInter64004Params(listPage);
                ArrayList org64004 = new ArrayList();
                org64004.add(areaNumber);//地区号
                org64004.add(identNumber);//介质识别号
                org64004.add("1");//借据序号
                org64004.add(bizCntProvider.getNextNumber("big64004"));//大交易号
                org64004.add(bizCntProvider.getNextNumber("small64004"));// 小交易号
                org64004.add(date);// 日期
                org64004.add(time);// 时间
                org64004.add(" ");
                org64004.add("0");
                org64004.add(type); // 操作类型 1 查询 2 更改 3 新增 4 删除
                org64004.add(pricinalParams);
                params64004 = joinInterfaceParams(org64004);
                paramsList.subList(0, lastSize).clear();
            }

            Map returnResult = invokeInterface(grantCode,actionSet,action,params64004,objInr);
            String returnCode = StringUtil.objectToString(returnResult.get("code"));

            if ("500".equals(returnCode)) {
                map.put("code", "500");
                map.put("msg", "还款计划接口调用失败，请联系管理员！");
                return map;
            }
            objInr = Long.valueOf(StringUtil.objectToString(returnResult.get("objInr")));
            String inte64004 = StringUtil.objectToString(returnResult.get("results"));
            JSONArray jSONArray = JSONObject.parseArray(inte64004);
            for (int j = 0; j < jSONArray.size(); j++) {
                JSONObject jsonObject = jSONArray.getJSONObject(j);
                // 返回交易状态（0-表示交易成功，1-表示交易失败）
                String transok = jsonObject.getString("TRANSOK");
                if ("0".equals(transok) && (a == part - 1)) {
                    code = "200";
                    bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), BizContant.INTERFACE_RESULT_SUCCESS));
                } else if (!"0".equals(transok)) {
                    // 根据错误码查找错误信息并返回
                    String errNo = jsonObject.getString("ERR_NO");
                    String cbsErrorMassage = getCbsErrorMassage(errNo);
                    // 记录第几次发送失败
                    bizInterfaceResultProvider.update(new BizInterfaceResult(Long.valueOf(objInr), String.valueOf(a)));
                    msg = "错误信息:" + cbsErrorMassage +",贷款账号:" + identNumber;
                    map.put("code", code);
                    map.put("msg", msg);
                    return map;
                }
            }
        }

        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    /**
    * @Description: 释放发放余额
    * @Param: []
    * @return: java.util.Map
    */
    private Map releaseGrantAmt(Map<String, Object> params){
        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "200";
        String msg= "";

        try {
            String grantId = StringUtil.objectToString(params.get("grantId"));
            String paymentAmt = StringUtil.objectToString(params.get("paymentAmt"));
            String remark = StringUtil.objectToString(params.get("remark"));
            BizCBB bizCBB = new BizCBB();
            bizCBB.setObjType(BizContant.BIZ_TABLE_GRANT);
            bizCBB.setObjInr(Long.valueOf(grantId));
            bizCBB.setCbc(BizContant.DEBT_GRANT_CBCTXT);
            bizCBB.setEnddat(DateUtil.parseDate(BizContant.END_DATE, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
            BizCBB bizCBB1 = bizCBBProvider.selectOneBizCBB(bizCBB);
            BigDecimal amt = bizCBB1.getAmt();
            BigDecimal payAmt = new BigDecimal(paymentAmt);
            bizCBB1.setAmt(amt.add(payAmt));
            bizCBBProvider.update(bizCBB1);
            // 保存废弃理由 修改债项发放的状态为废弃
            BizDebtGrant bizDebtGrant = bizDebtGrantProvider.selectByGrantId(grantId);
            bizDebtGrant.setRemark(remark);
            bizDebtGrant.setGrantStatus(6);
            bizDebtGrantProvider.update(bizDebtGrant);
        }catch (Exception e){
            code = "500";
            msg = "释放发放金额异常";
        }

        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    /**
     * @Description: 释放方案余额
     * @Param: []
     * @return: java.util.Map
     */
    private Map releaseDebtAmt(Map<String, Object> params){
        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "200";
        String msg= "";

        try {
            String debtCode = StringUtil.objectToString(params.get("debtCode"));
            String paymentAmt = StringUtil.objectToString(params.get("paymentAmt"));
            Long debtId = bizDebtSummaryProvider.selectDebtIdByDebtCode(debtCode);
            BizCBB bizCBB = new BizCBB();
            bizCBB.setObjType(BizContant.BIZ_DEBT_MAIN);
            bizCBB.setObjInr(debtId);
            bizCBB.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
            bizCBB.setEnddat(DateUtil.parseDate(BizContant.END_DATE, DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS));
            BizCBB bizCBB1 = bizCBBProvider.selectOneBizCBB(bizCBB);
            BigDecimal amt = bizCBB1.getAmt();
            BigDecimal payAmt = new BigDecimal(paymentAmt);
            bizCBB1.setAmt(amt.add(payAmt));
            bizCBBProvider.update(bizCBB1);
        }catch (Exception e){
            code = "500";
            msg = "释放方案金额异常";
        }

        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    /**
     * @Description: 释放方案可办理笔数
     * @Param: []
     * @return: java.util.Map
     */
    private Map releaseDebtTDWLN(Map<String, Object> params){
        // 接口返回样式 首先判断code 如果 500表示接口调用失败 返回msg错误信息 如果code值为200则表示调用成功
        Map map = new HashMap();
        String code = "200";
        String msg= "";

        try {
            String debtCode = StringUtil.objectToString(params.get("debtCode"));
            Long debtId = bizDebtSummaryProvider.selectDebtIdByDebtCode(debtCode);
            BizDebtSummary bizDebtSummary = bizDebtSummaryProvider.queryById(debtId);
            // 可办理笔数加1
            Long tdwln = bizDebtSummary.getTdwln();
            if(tdwln != 0){
                Long newTDWLN = tdwln + 1;
                bizDebtSummary.setTdwln(newTDWLN);
                bizDebtSummaryProvider.update(bizDebtSummary);
            }
        }catch (Exception e){
            code = "500";
            msg = "释放方案可办理笔数异常";
        }

        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    /**
     * @Description: 记录放款主表
     * @Param: [params]
     * @return: boolean
     */
    private BizMakeLoans updateBizMakeLoans(Map<String, Object> params) {
        BizMakeLoans returnMakeLoans = null;
        try {
            String enable = StringUtil.objectToString(params.get("enable"));
            BizMakeLoans bizMakeLoans = new BizMakeLoans();
            Map<String, Object> debtInfoForMakeLoan = (Map) params.get("debtInfoForMakeLoan");
            bizMakeLoans.setDebtCode(StringUtil.objectToString(debtInfoForMakeLoan.get("debtCode")));
            bizMakeLoans.setBusinessTypes(StringUtil.objectToString(debtInfoForMakeLoan.get("businessTypes")));
            bizMakeLoans.setGrantCode(StringUtil.objectToString(debtInfoForMakeLoan.get("grantCode")));
            bizMakeLoans.setProjectName(StringUtil.objectToString(debtInfoForMakeLoan.get("projectName")));
            bizMakeLoans.setProductName(StringUtil.objectToString(debtInfoForMakeLoan.get("productName")));
            bizMakeLoans.setProposer(StringUtil.objectToString(debtInfoForMakeLoan.get("proposer")));
            bizMakeLoans.setProposerNum(StringUtil.objectToLong(debtInfoForMakeLoan.get("proposerNum")));
            bizMakeLoans.setBankTellId(StringUtil.objectToLong(debtInfoForMakeLoan.get("bankTellId")));
            bizMakeLoans.setBankTellName(StringUtil.objectToString(debtInfoForMakeLoan.get("bankTellName")));
            bizMakeLoans.setDeptCode(StringUtil.objectToString(debtInfoForMakeLoan.get("deptCode")));
            bizMakeLoans.setDeptName(StringUtil.objectToString(debtInfoForMakeLoan.get("deptName")));
            bizMakeLoans.setScopeBusinPeriod(Integer.valueOf(StringUtil.objectToString(debtInfoForMakeLoan.get("scopeBusinPeriod"))));
            bizMakeLoans.setGracePeriod(StringUtil.objectToLong(debtInfoForMakeLoan.get("gracePeriod")));
            bizMakeLoans.setToierancePeriod(StringUtil.objectToLong(debtInfoForMakeLoan.get("toierancePeriod")));
            bizMakeLoans.setInstitutionCode(StringUtil.objectToString(debtInfoForMakeLoan.get("institutionCode")));
            //0 新增未激活 1 更新未激活为激活 2 新增激活
            if ("0".equals(enable)) {
                bizMakeLoans.setDateOfLoan(new Date());//暂存当前时间
                bizMakeLoans.setEnable(0);
                returnMakeLoans = update(bizMakeLoans);
            } else if ("1".equals(enable)) {
                BizMakeLoans bizMakeLoans2 = new BizMakeLoans();
                bizMakeLoans2.setDebtCode(StringUtil.objectToString(debtInfoForMakeLoan.get("debtCode")));
                bizMakeLoans2.setBusinessTypes(StringUtil.objectToString(debtInfoForMakeLoan.get("businessTypes")));
                bizMakeLoans2.setGrantCode(StringUtil.objectToString(debtInfoForMakeLoan.get("grantCode")));
                bizMakeLoans2.setEnable(0);
                BizMakeLoans bizMakeLoans1 = selectOne(bizMakeLoans2);
                // 许可证日期
                String liceDate = StringUtil.objectToString(params.get("liceDate"));
                Date date = DateUtil.parseDate(liceDate, DateUtil.DATE_PATTERN.YYYY_MM_DD);
                bizMakeLoans1.setDateOfLoan(date);
                bizMakeLoans1.setEnable(1);
                returnMakeLoans = update(bizMakeLoans1);
            } else {
                // 许可证日期
                String liceDate = StringUtil.objectToString(params.get("liceDate"));
                Date date = DateUtil.parseDate(liceDate, DateUtil.DATE_PATTERN.YYYY_MM_DD);
                bizMakeLoans.setDateOfLoan(date);
                bizMakeLoans.setEnable(1);
                returnMakeLoans = update(bizMakeLoans);
            }
        } catch (Exception e) {
            return null;
        }
        return returnMakeLoans;
    }

    /**
     * @Description: 放款台账记录
     * @Param: [params]
     * @return: void
     */
    private void standingBook(Map<String, Object> params) {

        // 当前时间
        Date currentDate = new Date();
        // endDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = null;
        try {
            endDate = dateFormat.parse(BizContant.END_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 放款相关常量
        List fecList = (List) params.get("fecList");
        String makeLoansObjType = BizContant.MAKE_LOANS_MAIN;
        Long makeLoansObjInr = StringUtil.objectToLong(params.get("objInr"));
        String makeLoansOutCbttxt = BizContant.MAKE_LOANS_OUT_CBTTXT;
        String makeLoansCbctxt = BizContant.MAKE_LOANS_CBCTXT;

        // 发放相关常量
        Map<String, Object> debtInfoForMakeLoan = (Map) params.get("debtInfoForMakeLoan");
        String debtGrantObjType = BizContant.BIZ_TABLE_GRANT;
        Long debtGrantObjInr = StringUtil.objectToLong(debtInfoForMakeLoan.get("objInr"));
        String debtGrantCbctxt = BizContant.DEBT_GRANT_CBCTXT;

        if (fecList != null && fecList.size() > 0) {
            for (int i = 0; i < fecList.size(); i++) {
                Map<String, Object> fec = (Map) fecList.get(i);
                // 币种
                String currency = StringUtil.objectToString(fec.get("currency"));
                // 金额
                BigDecimal amt = BigDecimal.valueOf(StringUtil.objectToLong(fec.get("paymentAmt")));

                // 记录放款发生额
                BizCBE makeloansBizCBE = new BizCBE();
                makeloansBizCBE.setObjType(makeLoansObjType);// 业务主表
                makeloansBizCBE.setObjInr(makeLoansObjInr); // 业务inr
                makeloansBizCBE.setCbt(makeLoansOutCbttxt); // 业务类型
                makeloansBizCBE.setDat(currentDate); // 发生日期
                makeloansBizCBE.setCur(currency); // 币种
                makeloansBizCBE.setAmt(amt);// 金额
                makeloansBizCBE.setCredat(currentDate); // 创建日期
                // TODO 折算币种
                // TODO amt折算后的金额
                makeloansBizCBE.setGledat(currentDate);// 记账日期
                BizCBE returnBizCBE = bizCBEProvider.update(makeloansBizCBE);

                // 记录放款余额
                // 先查询有没有已存在的放款余额
                BizCBB hadMakeloansCBB = new BizCBB();
                hadMakeloansCBB.setObjType(makeLoansObjType);
                hadMakeloansCBB.setObjInr(makeLoansObjInr);
                hadMakeloansCBB.setCbc(makeLoansCbctxt);
                hadMakeloansCBB.setEnddat(endDate);
                BizCBB hadMakeloansCBB1 = bizCBBProvider.selectOneBizCBB(hadMakeloansCBB);
                if (hadMakeloansCBB1 != null) {
                    // 如果已经存在放款余额 更新已存在余额endDate
                    hadMakeloansCBB1.setEnddat(currentDate);
                    bizCBBProvider.update(hadMakeloansCBB1);

                    BigDecimal amt1 = hadMakeloansCBB1.getAmt();
                    // 新增放款余额记录
                    BizCBB makeloansBizCBB = new BizCBB();
                    makeloansBizCBB.setObjType(makeLoansObjType);
                    makeloansBizCBB.setObjInr(makeLoansObjInr);
                    makeloansBizCBB.setCbc(makeLoansCbctxt);
                    makeloansBizCBB.setBegdat(currentDate);
                    makeloansBizCBB.setEnddat(endDate);
                    makeloansBizCBB.setCur(currency);
                    makeloansBizCBB.setAmt(amt.add(amt1));
                    makeloansBizCBB.setCbeInr(returnBizCBE.getId());
                    // TODO 折算币种
                    // TODO amt折算后的金额
                    BizCBB returnMakeloansBizCBB = bizCBBProvider.update(makeloansBizCBB);
                } else {
                    // 如果不存在放款余额记录 则新增
                    BizCBB makeloansBizCBB = new BizCBB();
                    makeloansBizCBB.setObjType(makeLoansObjType);
                    makeloansBizCBB.setObjInr(makeLoansObjInr);
                    makeloansBizCBB.setCbc(makeLoansCbctxt);
                    makeloansBizCBB.setBegdat(currentDate);
                    makeloansBizCBB.setEnddat(endDate);
                    makeloansBizCBB.setCur(currency);
                    makeloansBizCBB.setAmt(amt);
                    makeloansBizCBB.setCbeInr(returnBizCBE.getId());
                    // TODO 折算币种
                    // TODO amt折算后的金额
                    BizCBB returnMakeloansBizCBB = bizCBBProvider.update(makeloansBizCBB);
                }

                // 记录发放发生额
                BizCBE debtGrantBizCBE = new BizCBE();
                debtGrantBizCBE.setObjType(makeLoansObjType);// 业务主表
                debtGrantBizCBE.setObjInr(makeLoansObjInr); // 业务inr
                debtGrantBizCBE.setCbt(debtGrantCbctxt); // 业务类型
                debtGrantBizCBE.setDat(currentDate); // 发生日期
                debtGrantBizCBE.setCur(currency); // 币种
                debtGrantBizCBE.setAmt(amt);// 金额
                debtGrantBizCBE.setCredat(currentDate); // 创建日期
                // TODO 折算币种
                // TODO amt折算后的金额
                debtGrantBizCBE.setGledat(currentDate);// 记账日期
                BizCBE returnDebtGrantBizCBE = bizCBEProvider.update(debtGrantBizCBE);

                // 更新发放余额
                // 先查询有没有已存在的发放余额
                BizCBB debtGrantBizCBBBefore = new BizCBB();
                debtGrantBizCBBBefore.setObjType(debtGrantObjType);
                debtGrantBizCBBBefore.setObjInr(debtGrantObjInr);
                debtGrantBizCBBBefore.setCbc(debtGrantCbctxt);
                debtGrantBizCBBBefore.setEnddat(endDate);
                BizCBB debtGrantBizCBBBefore1 = bizCBBProvider.selectOneBizCBB(debtGrantBizCBBBefore);
                if (debtGrantBizCBBBefore1 != null) {
                    // 发放余额已存在 更新endDate
                    debtGrantBizCBBBefore1.setEnddat(currentDate);
                    bizCBBProvider.update(debtGrantBizCBBBefore1);
                    // 新增发放余额
                    BigDecimal currencyCBB = debtGrantBizCBBBefore1.getAmt();
                    BizCBB debtGrantBizCBB = new BizCBB();
                    debtGrantBizCBB.setObjType(debtGrantObjType);
                    debtGrantBizCBB.setObjInr(debtGrantObjInr);
                    debtGrantBizCBB.setCbc(debtGrantCbctxt);
                    debtGrantBizCBB.setBegdat(currentDate);
                    debtGrantBizCBB.setEnddat(endDate);
                    debtGrantBizCBB.setCur(currency);
                    debtGrantBizCBB.setAmt(currencyCBB.subtract(amt));//
                    debtGrantBizCBB.setCbeInr(returnDebtGrantBizCBE.getId());
                    // TODO 折算币种
                    // TODO amt折算后的金额
                    BizCBB returndebtGrantBizCBB = bizCBBProvider.update(debtGrantBizCBB);
                }
            }
        }
    }

    @Override
    public Map getDiscardMakeLoansInfo(String grantCode) {
        return bizMakeLoansMapper.getDiscardMakeLoansInfo(grantCode);
    }

    /**
     * @Description: 租金保理业务根据币种获取产品序号
     * @Param: [currency]
     * @return: java.lang.String
     */
    private String getSerialNoByCurrency(String currency) {
        if ("CNY".equals(currency)) {
            return "1562700";
        } else if ("USD".equals(currency)) {
            return "8402700";
        } else if ("EUR".equals(currency)) {
            return "9782300";
        } else if ("HKD".equals(currency)) {
            return "3442700";
        } else if ("MOP".equals(currency)) {
            return "0362700";
        } else if ("CAD".equals(currency)) {
            return "1242700";
        } else if ("JPY".equals(currency)) {
            return "3922700";
        } else if ("GBP".equals(currency)) {
            return "8262700";
        } else {
            return "";
        }
    }

    /**
    * @Description: 查询产品序号
    * @Param: [productType, currencyChar]
    * @return: java.lang.String
    */
    private String selectProductSeqnum(String productType,String currencyChar){
        Map dataMap = new HashMap();
        dataMap.put("productType",productType);
        dataMap.put("currencyChar",currencyChar);
        return bizProductDefinitionMapper.selectProductSeqnum(dataMap);
    }

    /**
     * @Description: 拼接请求接口参数
     * @Param: [list]
     * @return: java.lang.String
     */
    private String joinInterfaceParams(List list) {
        StringBuilder params = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    params.append("'").append(list.get(i).toString()).append("',");
                } else {
                    params.append("'").append(list.get(i).toString()).append("'");
                }
            }
        } else {
            return "";
        }
        return params.toString();
    }


    /**
     * @Description: 核心接口返回错误信息码值对应错误信息
     * @Param: [errorCode]
     * @return: java.lang.String
     */
    private String getCbsErrorMassage(String errorCode) {
        if (errorCode != null && !"".equals(errorCode)) {
            BizCbsErrorMessage bizCbsErrorMessage = new BizCbsErrorMessage();
            bizCbsErrorMessage.setErrorCode(errorCode);
            BizCbsErrorMessage bizCbsErrorMessage1 = bizCbsErrorMessageProvider.selectOne(bizCbsErrorMessage);
            if (bizCbsErrorMessage1 != null) {
                return bizCbsErrorMessage1.getErrorMessage();
            } else {
                return "接口调用失败";
            }
        } else {
            return "接口调用失败";
        }
    }

    /**
     * @Description: 通过币种英文码获取币种数字码
     * @Param: [currency]
     * @return: java.lang.String
     */
    private String getCurrencyNo(String currency) {
        Map dataMap = new HashMap();
        dataMap.put("currency", currency);
        return bizMakeLoansMapper.getCurrencyNo(dataMap);
    }

    /**
     * @Description: 根据用户id获取用户机构
     * @Param: [userId]
     * @return: org.ibase4j.model.SysDept
     */
    @Override
    public SysDept getUserInstitutionCodeByUserId(Long userId) {
        SysUser sysUser = sysUserProvider.queryById(userId);
        String deptCode = sysUser.getDeptCode();
        SysDept sysDept = sysDeptProvider.queryDeptByCode(deptCode);
        return sysDept;
    }

    @Override
    public SysDept getDeptByDeptCode(String deptCode) {
        SysDept sysDept = sysDeptProvider.queryDeptByCode(deptCode);
        return sysDept;
    }

    /**
     * @Description: 查询担保类型(总)
     * @Param: [bizGuaranteeResults]
     * @return: java.lang.String
     */
    private String getAllGuaranNameFromList(List bizGuaranteeResults) {
        List nameList = new ArrayList();
        if (bizGuaranteeResults != null && bizGuaranteeResults.size() > 0) {
            for (int i = 0; i < bizGuaranteeResults.size(); i++) {
                Map bizGuaranteeResult = (Map) bizGuaranteeResults.get(i);
                String guaranName = StringUtil.objectToString(bizGuaranteeResult.get("guaranName"));
                nameList.add(guaranName);
            }
        }
        String AllGuaranName = StringUtil.listToString(nameList, ",");
        return AllGuaranName;
    }

    /**
     * @Description: 整数相除向上取整
     * @Param: [size, limit]
     * @return: int
     */
    private int getCeilInt(int size, int limit) {
        int part = 0; // 分批批数
        if ((size % limit) != 0) {
            part = size / limit + 1;
        } else {
            part = size / limit;
        }
        return part;
    }

    /**
     * @Description: 放款台账
     * @Param: []
     * @return: boolean
     */
    private boolean updateMakeLoanStandingBook(Map params) {

        logger.debug("==================================================放款台账记录start");

        boolean flag = true;
        // 当前时间
        Date currentDate = new Date();
        // endDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endDate = null;
        // 放款相关常量
        List fecList = (List) params.get("fecList");
        String makeLoansObjType = BizContant.MAKE_LOANS_MAIN;
        Long makeLoansObjInr = StringUtil.objectToLong(params.get("objInr"));
        String makeLoansOutCbttxt = BizContant.MAKE_LOANS_OUT_CBTTXT;
        String makeLoansCbctxt = BizContant.MAKE_LOANS_CBCTXT;
        String enable = StringUtil.objectToString(params.get("enable"));
        try {
            Map<String, Object> fec = (Map) fecList.get(0);
            // 币种
            String currency = StringUtil.objectToString(fec.get("currency"));
            // 金额
            BigDecimal amt = BigDecimal.valueOf(StringUtil.objectToLong(fec.get("paymentAmt")));
            endDate = dateFormat.parse(BizContant.END_DATE);
            // 放款发生额
            BizCBE makeloansBizCBE = new BizCBE();
            makeloansBizCBE.setObjType(makeLoansObjType);// 业务主表
            makeloansBizCBE.setObjInr(makeLoansObjInr); // 业务inr
            makeloansBizCBE.setCbt(makeLoansOutCbttxt); // 业务类型
            makeloansBizCBE.setCur(currency); // 币种
            makeloansBizCBE.setAmt(amt);// 金额
            BizCBE returnBizCBE = null;
            // TODO 折算币种
            // TODO amt折算后的金额
            //0 新增未激活 1 更新未激活为激活 2 新增激活
            if ("0".equals(enable)) {
                makeloansBizCBE.setGledat(currentDate);// 记账日期
                makeloansBizCBE.setCredat(currentDate); // 创建日期
                makeloansBizCBE.setEnable(Integer.valueOf(enable));
                makeloansBizCBE.setDat(currentDate); // 发生日期
                returnBizCBE = bizCBEProvider.update(makeloansBizCBE);
            } else if ("0".equals(enable)) {
                makeloansBizCBE.setEnable(0);
                BizCBE bizCBE = bizCBEProvider.selectOne(makeloansBizCBE);
                bizCBE.setEnable(1);
                returnBizCBE = bizCBEProvider.update(bizCBE);
            } else {
                makeloansBizCBE.setGledat(currentDate);// 记账日期
                makeloansBizCBE.setCredat(currentDate); // 创建日期
                makeloansBizCBE.setEnable(1);
                makeloansBizCBE.setDat(currentDate); // 发生日期
                returnBizCBE = bizCBEProvider.update(makeloansBizCBE);
            }

            // 放款余额
            BizCBB makeloansBizCBB = new BizCBB();
            makeloansBizCBB.setObjType(makeLoansObjType);
            makeloansBizCBB.setObjInr(makeLoansObjInr);
            makeloansBizCBB.setCbc(makeLoansCbctxt);
            makeloansBizCBB.setEnddat(endDate);
            makeloansBizCBB.setCur(currency);
            makeloansBizCBB.setAmt(amt);
            makeloansBizCBB.setCbeInr(returnBizCBE.getId());
            // TODO 折算币种
            // TODO amt折算后的金额
            if ("0".equals(enable)) {
                makeloansBizCBB.setBegdat(currentDate);
                makeloansBizCBB.setEnable(Integer.valueOf(enable));
                bizCBBProvider.update(makeloansBizCBB);
            } else if ("1".equals(enable)) {
                makeloansBizCBB.setEnable(0);
                BizCBB bizCBB = bizCBBProvider.selectOne(makeloansBizCBB);
                bizCBB.setEnable(1);
                bizCBBProvider.update(bizCBB);
            } else {
                makeloansBizCBB.setBegdat(currentDate);
                makeloansBizCBB.setEnable(1);
                bizCBBProvider.update(makeloansBizCBB);
            }

        } catch (Exception e) {
            flag = false;
        }
        logger.debug("==================================================放款台账记录end");
        return flag;
    }

    /**
     * @Description: 还本还息计划参数
     * @Param: [params]
     * @return: java.util.List
     */
    private List transInter64004Params(Map params) {
        List list = new ArrayList();
        // 还本计划
        List repaymentPricinalList = (List) params.get("repaymentPricinalList");
        // 还息计划
        List repaymentLoanList = (List) params.get("repaymentLoanList");
        // 1：归还本金 2：归还利息 3:归还本息
        String intrtnf1 = "1";
        String intrtnf2 = "2";
        String intrtnf3 = "3";
        List sameList = new ArrayList();
        List allList = new ArrayList();
        // 将还本转化为对象
        if (repaymentPricinalList != null && repaymentPricinalList.size() > 0) {
            for (int i = 0; i < repaymentPricinalList.size(); i++) {
                Map repaymentPricinal = (Map) repaymentPricinalList.get(i);
                String payDate = StringUtil.objectToString(repaymentPricinal.get("payDate")).substring(0, 10);
                String principalAmt = MathUtil.formatToNumber(new BigDecimal(StringUtil.objectToString(repaymentPricinal.get("principalAmt"))));
                RepaymentVo repaymentVo = new RepaymentVo();
                repaymentVo.setGbdate(payDate);
                repaymentVo.setEndintd(payDate);
                repaymentVo.setGbamount(principalAmt);
                repaymentVo.setIntrtnf(intrtnf1);
                allList.add(repaymentVo);
            }
        }
        // 还息计划转化为对象
        if (repaymentLoanList != null && repaymentLoanList.size() > 0) {
            for (int i1 = 0; i1 < repaymentLoanList.size(); i1++) {
                Map repaymentLoan = (Map) repaymentLoanList.get(i1);
                String interestDate = StringUtil.objectToString(repaymentLoan.get("interestDate")).substring(0, 10);
                RepaymentVo repaymentVo = new RepaymentVo();
                repaymentVo.setGbdate(interestDate);
                repaymentVo.setEndintd(interestDate);
                repaymentVo.setGbamount("0");
                repaymentVo.setIntrtnf(intrtnf2);
                allList.add(repaymentVo);
            }
        }
        // 筛选出相同的时间对象
        if (repaymentPricinalList != null && repaymentPricinalList.size() > 0) {
            for (int i = 0; i < repaymentPricinalList.size(); i++) {
                Map repaymentPricinal = (Map) repaymentPricinalList.get(i);
                String payDate = StringUtil.objectToString(repaymentPricinal.get("payDate")).substring(0, 10);
                String principalAmt = MathUtil.formatToNumber(new BigDecimal(StringUtil.objectToString(repaymentPricinal.get("principalAmt"))));
                if (repaymentLoanList != null && repaymentLoanList.size() > 0) {
                    for (int i1 = 0; i1 < repaymentLoanList.size(); i1++) {
                        Map repaymentLoan = (Map) repaymentLoanList.get(i1);
                        String interestDate = StringUtil.objectToString(repaymentLoan.get("interestDate")).substring(0, 10);
                        if (payDate.equals(interestDate)) {
                            RepaymentVo repaymentVo = new RepaymentVo();
                            repaymentVo.setGbdate(payDate);
                            repaymentVo.setEndintd(payDate);
                            repaymentVo.setGbamount(principalAmt);
                            repaymentVo.setIntrtnf(intrtnf3);
                            sameList.add(repaymentVo);
                        }
                    }
                }
            }
        }
        // 将相同时间的只留下还款类型为本息的
        if (sameList != null && sameList.size() > 0) {
            for (int i = 0; i < sameList.size(); i++) {
                RepaymentVo same = (RepaymentVo) sameList.get(i);
                String sameDate = same.getGbdate();
                Iterator it = allList.iterator();
                while (it.hasNext()) {
                    RepaymentVo all = (RepaymentVo) it.next();
                    if (sameDate.equals(all.getGbdate())) {
                        it.remove();
                    }
                }
            }
            // 将相同的存入all
            for (int i = 0; i < sameList.size(); i++) {
                allList.add(sameList.get(i));
            }
            Collections.sort(allList);
        }
        return allList;
    }

    /**
     * @Description: 64004接口还本还息计划参数拼接
     * @Param: [params]
     * @return: java.lang.String
     */
    private String joinInter64004Params(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    RepaymentVo repaymentVo = (RepaymentVo) list.get(i);
                    String gbdate = repaymentVo.getGbdate();
                    String endintd = repaymentVo.getEndintd();
                    String gbamount = repaymentVo.getGbamount();
                    String intrtnf = repaymentVo.getIntrtnf();
                    if (i != list.size() - 1) {
                        stringBuilder.append(gbdate).append("|").append(endintd).append("|").append(gbamount).append("|")
                                .append(intrtnf).append("|+|");
                    } else {
                        stringBuilder.append(gbdate).append("|").append(endintd).append("|").append(gbamount).append("|")
                                .append(intrtnf).append("|");
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    /**
     * @Description: 获取额度交易接口参数
     * @Param: [grantCode]
     * @return: java.util.Map
     */
    private Map getGuaranteeInfoList(String grantCode) {
        Map paramMap = new HashMap();
        paramMap.put("grantCode", grantCode);
        // 获取担保信息
        List guaranteeInfoList = bizGuaranteeResultProvider.getGuaranteeInfoList(paramMap);
        Map dataMap = new HashMap();
        // 保证
        List guaranteeList = new ArrayList();
        // 抵押质押
        List mortgageList = new ArrayList();
        // 信用
        List creditList = new ArrayList();
        // 担保类型
        List type = new ArrayList();
        if (guaranteeInfoList != null && guaranteeInfoList.size() > 0) {
            for (int i = 0; i < guaranteeInfoList.size(); i++) {
                Map guaranteeInfo = (Map) guaranteeInfoList.get(i);
                String typePoint = StringUtil.objectToString(guaranteeInfo.get("typePoint"));
                if ("C101".equals(typePoint)) {
                    guaranteeList.add(guaranteeInfo);
                    type.add(typePoint);
                } else if ("C102".equals(typePoint) || "C103".equals(typePoint)) {
                    mortgageList.add(guaranteeInfo);
                    type.add(typePoint);
                } else if ("C000".equals(typePoint)) {
                    creditList.add(guaranteeInfo);
                    type.add(typePoint);
                }
            }
            String guaranteeType = StringUtil.listToString(type, "|");
            String guarantee = joinGuaranteeParams(guaranteeList);
            String mortgage = joinMortgageParams(mortgageList);
            String credit = joinCreditParams(creditList);
            dataMap.put("guarantee", guarantee);
            dataMap.put("mortgage", mortgage);
            dataMap.put("credit", credit);
            dataMap.put("guaranteeType", guaranteeType);
        }
        return dataMap;
    }

    /**
     * @Description: 担保类型为 保证 参数拼接
     * @Param: [params]
     * @return: java.lang.String
     */
    private String joinGuaranteeParams(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map guarantee = (Map) list.get(i);
                    String guarantorCustId = StringUtil.objectToString(guarantee.get("guarantorCustId"));
                    String guaranteeContractType = StringUtil.objectToString(guarantee.get("guaranteeContractType"));
                    String warrantyContractNumber = StringUtil.objectToString(guarantee.get("warrantyContractNumber"));
                    String srv1 = StringUtil.objectToString(guarantee.get("srv1"));
                    String clearRatioAmt = StringUtil.objectToString(guarantee.get("clearRatioAmt"));
                    String notClearAmt = StringUtil.objectToString(guarantee.get("notClearAmt"));
                    if (i != list.size() - 1) {
                        stringBuilder.append(guarantorCustId).append("|").append(guaranteeContractType).append("|").append(warrantyContractNumber).append("|")
                                .append(srv1).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|").append("".equals(notClearAmt) ? "0" : notClearAmt).append("|+|");
                    } else {
                        stringBuilder.append(guarantorCustId).append("|").append(guaranteeContractType).append("|").append(warrantyContractNumber).append("|")
                                .append(srv1).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|").append("".equals(notClearAmt) ? "0" : notClearAmt);
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    /**
     * @Description: 担保类型为 抵押 质押 参数拼接
     * @Param: [params]
     * @return: java.lang.String
     */
    private String joinMortgageParams(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map mortgage = (Map) list.get(i);
                    String mortgageType = "";
                    String guaranteeContractType = StringUtil.objectToString(mortgage.get("guaranteeContractType"));
                    String warrantyContractNumber = StringUtil.objectToString(mortgage.get("warrantyContractNumber"));
                    String srv1 = StringUtil.objectToString(mortgage.get("srv1"));
                    String clearRatioAmt = StringUtil.objectToString(mortgage.get("clearRatioAmt"));
                    String notClearAmt = StringUtil.objectToString(mortgage.get("notClearAmt"));
                    if (i != list.size() - 1) {
                        stringBuilder.append(mortgageType).append("|").append(guaranteeContractType).append("|").append(srv1).append("|")
                                .append(warrantyContractNumber).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|").append("".equals(notClearAmt) ? "0" : notClearAmt).append("|+|");
                    } else {
                        stringBuilder.append(mortgageType).append("|").append(guaranteeContractType).append("|").append(srv1).append("|")
                                .append(warrantyContractNumber).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|").append("".equals(notClearAmt) ? "0" : notClearAmt);
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    /**
     * @Description: 担保类型为 信用 参数拼接
     * @Param: [params]
     * @return: java.lang.String
     */
    private String joinCreditParams(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Map credit = (Map) list.get(i);
                    String guarantorCustId = StringUtil.objectToString(credit.get("guarantorCustId"));
                    String guaranteeContractType = StringUtil.objectToString(credit.get("guaranteeContractType"));
                    String clearRatioAmt = StringUtil.objectToString(credit.get("clearRatioAmt"));
                    String notClearAmt = StringUtil.objectToString(credit.get("notClearAmt"));
                    if (i != list.size() - 1) {
                        stringBuilder.append(guarantorCustId).append("|").append(guaranteeContractType).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|")
                                .append("".equals(notClearAmt) ? "0" : notClearAmt).append("|+|");
                    } else {
                        stringBuilder.append(guarantorCustId).append("|").append(guaranteeContractType).append("|").append("".equals(clearRatioAmt) ? "0" : clearRatioAmt).append("|")
                                .append("".equals(notClearAmt) ? "0" : notClearAmt);
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    /**
     * @Description: 额度占用接口 用信主体占用额度
     * @Param: [params]
     * @return: java.lang.String
     */
    private String getCustomInfoParams(Map params) {
        // 获取用信主体相关信息
        List productCustomerInfoList = bizDebtGrantProvider.getProductCustomerInfo(params);
        // 发放金额
        BigDecimal grantAmt = new BigDecimal(StringUtil.objectToString(params.get("grantAmt")));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (productCustomerInfoList != null && productCustomerInfoList.size() > 0) {
                for (int i = 0; i < productCustomerInfoList.size(); i++) {
                    Map credit = (Map) productCustomerInfoList.get(i);
                    String custNo = StringUtil.objectToString(credit.get("custNo"));
                    String amountType = StringUtil.objectToString(credit.get("amountType"));
                    BigDecimal creditRatio = new BigDecimal(StringUtil.objectToString(credit.get("creditRatio")));
                    BigDecimal convertedPrice = new BigDecimal(StringUtil.objectToString(params.get("convertedPrice")));
                    // 额度占用币种 默认为人民币
                    String currency = "156";
                    // 额度占用金额 发放金额 * 币种对应的折算牌价 * 额度用信比例(数据库为*100以后的整数) / 100
                    BigDecimal amt = grantAmt.multiply(convertedPrice).multiply(creditRatio).divide(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);
                    // 将信用额度筛除
                    if("0000".equals(amountType) || "TP03".equals(amountType) || "TP04".equals(amountType) || "TP05".equals(amountType) || "TP09".equals(amountType)){
                        if (i != productCustomerInfoList.size() - 1) {
                            stringBuilder.append(custNo).append("|").append(amountType).append("|").append(currency).append("|")
                                    .append(amt).append("|+|");
                        } else {
                            stringBuilder.append(custNo).append("|").append(amountType).append("|").append(currency).append("|")
                                    .append(amt);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return stringBuilder.toString();
    }

    /**
     * @Description: 最高额担保合同参数
     * @Param: [grantCode]
     * @return: java.util.List
     */
    private List getGuaranteeContract(String grantCode) {
        List list = new ArrayList();
        Map paramMap = new HashMap();
        paramMap.put("grantCode", grantCode);
        // 获取担保信息
        List guaranteeInfoList = bizGuaranteeResultProvider.getGuaranteeInfoList(paramMap);
        if (guaranteeInfoList != null && guaranteeInfoList.size() > 0) {
            for (int i = 0; i < guaranteeInfoList.size(); i++) {
                Map guaranteeInfo = (Map) guaranteeInfoList.get(i);
                String guaranteeContractType = StringUtil.objectToString(guaranteeInfo.get("guaranteeContractType"));
                if (!"".equals(guaranteeContractType)) {
                    switch (guaranteeContractType){
                        case("UE05"):list.add(guaranteeInfo);break;// 最高额保证合同
                        case("UE12"):list.add(guaranteeInfo);break;// 最高额保证合同（个人）
                        case("UE13"):list.add(guaranteeInfo);break;// 最高额保证合同（对公）
                        case("UE06"):list.add(guaranteeInfo);break;// 最高额抵押合同
                        case("UE15"):list.add(guaranteeInfo);break;// 最高额权利质押合同
                        case("UE14"):list.add(guaranteeInfo);break;// 最高额动产质押合同
                    }
                }
            }
        }
        return list;
    }
    
    /** 
    * @Description: 查询接口返回结果 
    * @Param: [grantCode, interfaceNname] 
    * @return: java.util.Map
    */ 
    private Map queryInterfaceResult(String tradeCode,String interfaceNname){
        Map interfaceMap = new HashMap();
        interfaceMap.put("tradeCode",tradeCode);
        interfaceMap.put("interfaceName",interfaceNname);
        return bizInterfaceResultProvider.getInterfaceResponseStatus(interfaceMap);
    }

}