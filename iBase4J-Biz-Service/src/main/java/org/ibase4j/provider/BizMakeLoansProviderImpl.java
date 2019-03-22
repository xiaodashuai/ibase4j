/**
 *
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.*;
<<<<<<< HEAD
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.mapper.BizMakeLoansMapper;
import org.ibase4j.mapper.BizProductDefinitionMapper;
=======
import org.ibase4j.mapper.*;
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
import org.ibase4j.model.*;
import org.ibase4j.vo.BookkeepkingVo;
import org.ibase4j.vo.RepaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    private BizGuaranteeResultMapper bizGuaranteeResultMapper;
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
    private PlatformTransactionManager txManager;
	@Autowired
	private BizProductDefinitionMapper bizProductDefinitionMapper;
<<<<<<< HEAD
		

=======
    @Autowired
    private BizTRNMapper bizTRNMapper;

    @Autowired
    private BizGuaranteeInfoMapper bizGuaranteeInfoMapper;
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getMakeLoansDebtInfo(Map<String, Object> params) {//获取方案详情信息 sinosong
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

        // 手工进行数据库事务控制，对表的增删改操作都使用mapper的方法，或自己写的方法， 但是方法报错需要抛出异常
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            // 由于68环境不能测试行方接口 遂配置接口开关 on开  off关
            String interfaceStatus = PropertiesUtil.getString("interface.status");
            // 时间
            Date dat = new Date();
            String date = DateUtil.format(dat);
            // 日期
            String time = DateUtil.formatTime(dat);
            // 发放表主键id
            String objInr = StringUtil.objectToString(params.get("objInr"));
            // 介质识别号
            String identNumber = StringUtil.objectToString(params.get("identNumber"));
            // 借据编号
            String iouCode = StringUtil.objectToString(params.get("iouCode"));
            // 发放审核编号
            // 业务编号规则变化，去掉后三位。方案编号变成13位，条件编号变成16 ypf
            // String grantCode = StringUtil.objectToString(params.get("grantCode"));
            String grantCode = StringUtil.objectToString(params.get("grantCode")).substring(0, 16);

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
            BigDecimal grantAmt = new BigDecimal("0");
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
            Map resultThree =null;
            if ("on".equals(interfaceStatus)) {

                //条件新增，修改开关alterflag
                boolean addflag =true;//新增
                Map qryparam = new HashMap();
                qryparam.put("qryparam", grantCode);
                List<BizDebtGrant> grantlist = bizDebtGrantMapper.selectByMap(qryparam);
                if (null != grantlist && grantlist.size()>0) {
                    int changenum = grantlist.get(0).getChangeNum();
                    if (changenum == 0) {
                        addflag=false;
                    }

                }
                if(addflag){

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
                    } else if ("300".equals(codeTwo)) {
                        liceDate = StringUtil.objectToString(resultTwo.get("liceDate"));
                        returnSeqNo = StringUtil.objectToString(resultTwo.get("returnSeqNo"));
                        BizDebtGrant bizDebtGrant = new BizDebtGrant();
                        bizDebtGrant.setId(Long.valueOf(objInr));
                        bizDebtGrant.setLiceDate(liceDate);
                        bizDebtGrant.setSeqNo(returnSeqNo);
                        bizDebtGrant.setIdentNumber(identNumber);
                        // 改为使用mapper
                        // bizDebtGrantProvider.update(bizDebtGrant);
                        bizDebtGrantMapper.updateById(bizDebtGrant);
                    }
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
                resultThree = requestIntefaceThree(paramsThree);
                String codeThree = StringUtil.objectToString(resultThree.get("code"));
                if ("500".equals(codeThree)) {
                    String msgThree = StringUtil.objectToString(resultThree.get("msg"));
                    return msgThree;
                }

                // 业务与担保合同关系接口
                Map paramsFour = new HashMap();
                paramsFour.put("grantCode", grantCode);
                paramsFour.put("identNumber", identNumber);
                paramsFour.put("addflag", addflag);
                paramsFour.put("type", "0002");//0002 生效   0004解除  0009结清 0003 修改
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
            // bizDebtGrantProvider.update(bizDebtGrant1);
            bizDebtGrantMapper.updateById(bizDebtGrant1);

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
            dataMap.put("date", dat);
            BizMakeLoans makeLoan = updateBizMakeLoans(dataMap);
            Long objInr1 = Long.valueOf(makeLoan.getId());
            dataMap.put("objInr", objInr1);

            logger.debug("===================================================记录放款主表end");

            // 记录放款台账 enable = 0

            // 放款存一条流水 ypf

            BizTRN bizTRN = new BizTRN();
            Long trnId = IdWorker.getId();

            bizTRN.setBchkeyinr(makeLoan.getInstitutionCode());
            bizTRN.setIniusr(userId);
            bizTRN.setOwnref(grantCode);
            bizTRN.setObjtyp(BizContant.MAKE_LOANS_MAIN);
            bizTRN.setObjinr(objInr1);
            bizTRN.setExedat(dat);
            bizTRN.setInidattim(dat);
            bizTRN.setId(trnId);
            bizTRN.setCreateTime(dat);
            bizTRN.setCreateBy(userId);
            bizTRN.setEnable(1);
            //为*时代表最新的流水
            bizTRN.setRelflg("Y");
            //为*时代表业务中最新审批通过的数据
            bizTRN.setRelres("N");

            bizTRN.setInifrm(BizContant.MAKE_LOANS_APPRL);
            bizTRN.setIninam(BizContant.MAKE_LOANS_NAME);
            bizTRN.setProcessStatus(1);
            bizTRNProvider.updateTRNStatus(bizTRN);

            // 修改台账存储逻辑 ypf
        /*boolean b = updateMakeLoanStandingBook(dataMap);
        if (!b) {
            return "台账记录失败";
        }*/

            // 新增一条放款台账，LOANIN LOANSUM
            // 修改条件发放台账，GRANOUT GRANSUM
            boolean bookres1 = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_MAKE_LOANS", objInr1, trnId, MAKE_LOANS_IN_CBTTXT, BizContant.MAKE_LOANS_CBCTXT, currency, grantAmt, null, null, dat, makeLoan.getBankTellId()));
//        boolean bookres1 = bizCBEProvider.bookkeepking(new BizCBE(BizContant.MAKE_LOANS_MAIN,objInr1,trnId),new BizCBB(BizContant.MAKE_LOANS_MAIN,objInr1,BizContant.MAKE_LOANS_CBCTXT,currency,grantAmt,null,null),MAKE_LOANS_IN_CBTTXT,BizContant.MAKE_LOANS_CBCTXT,new Date());
            // boolean bookres2 = bizCBEProvider.bookkeepking(new BizCBE("BIZ_DEBT_MAIN",bizDebtId,trnId),new BizCBB("BIZ_DEBT_MAIN",bizDebtId,BizContant.DEBT_SUMMARY_DEBT_CBCTXT,bizDebtSummary.getMpc(),StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()),null,null),"DEBTIN",BizContant.DEBT_SUMMARY_DEBT_CBCTXT,dat);
            if (!bookres1) {
                return "台账记录失败";
<<<<<<< HEAD
            }
=======
            }*/
//添加额度占用台账 sinosong 记录额度占用台账,需要提供lim记录

//            String custstr = StringUtil.objectToString((Map)resultThree.get("custList"));
//            if(null != custstr && !"".equals(custstr)){
//                List<String> custList = Arrays.asList(custstr);
//                List<Map> list2=(List<Map>)JSONArray.parseObject((Map)resultThree.get("custList"));
//                if(null != list2 && list2.size()>0){
//                    for (int i = 0; i <list2.size() ; i++) {
//                        Map custmap = list2.get(i);
//                        String  limitId=StringUtil.objectToString(custmap.get("limitId"));
//                        String  rninr=StringUtil.objectToString(custmap.get("rninr"));
//                        String  cur=StringUtil.objectToString(custmap.get("cur"));
//                        String  amt=StringUtil.objectToString(custmap.get("amt"));
//                        String  updatetime=StringUtil.objectToString(custmap.get("updatetime"));
//                        String  banktellerid=StringUtil.objectToString(custmap.get("banktellerid"));
//                        boolean limRes = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_CUST_LIMIT",Long.valueOf(rninr),Long.valueOf(limitId),rninr,BizContant.LIMIT_IN_CBTTXT,BizContant.LIMITSUM_CBCTXT,cur,new BigDecimal(amt),null,null,new Date(updatetime),Long.valueOf(banktellerid));
//                    }
//                }
//            }



//                boolean limRes = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_CUST_LIMIT",lim.getId(),lim.getTrninr(),BizContant.LIMIT_IN_CBTTXT,BizContant.LIMITSUM_CBCTXT,lim.getCur(),lim.getAmt(),null,null,lim.getUpdateTime(),lim.getBankTellerId()));
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a

            return "等待核心处理结果";
        }catch (Exception es) {
            txManager.rollback(status);
            es.printStackTrace();
            throw new RuntimeException("save makeLoanInfo error! reason=="+es.getMessage());
        }
    }

    /**
     * @Description: 发送还款计划
     * @Param: [params]
     * @return: java.lang.String
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendRepaymentPlan(Map<String, Object> params) {

        // 手工进行数据库事务控制，对表的增删改操作都使用mapper的方法，或自己写的方法， 但是方法报错需要抛出异常
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {
            // 由于68环境不能测试行方接口 遂配置接口开关 on开  off关
            String interfaceStatus = PropertiesUtil.getString("interface.status");

            Date dat = new Date();
            // 日期
            String date = DateUtil.format(dat);
            // 时间
            String time = DateUtil.formatTime(dat);
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


                //条件新增，修改开关alterflag
                boolean addflag =true;//新增
                Map qryparam = new HashMap();
                qryparam.put("qryparam", grantCode);
                List<BizDebtGrant> grantlist = bizDebtGrantMapper.selectByMap(qryparam);
                if (null != grantlist && grantlist.size()>0) {
                    int changenum = grantlist.get(0).getChangeNum();
                    if (changenum == 0) {
                        addflag=false;
                    }

                }
                if(addflag) {


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
            dataMap.put("date", dat);

            // 修改债项发放状态
            Integer grantStatus = 5;
            // bizDebtGrantProvider.updateDebtGrantStatus(grantCode, grantStatus);
            BizDebtGrant bizDebtGrant1 = new BizDebtGrant();
            bizDebtGrant1.setGrantCode(grantCode);
             BizDebtGrant bizDebtGrant2 = bizDebtGrantProvider.selectOne(bizDebtGrant1);
             // 修改债项发放方案状态
             bizDebtGrant2.setGrantStatus(grantStatus);
             bizDebtGrantMapper.updateById(bizDebtGrant2);

            // 更新放款主表enable = 1;
            BizMakeLoans bizMakeLoans = updateBizMakeLoans(dataMap);
            // 更新放款台账enable = 1
            dataMap.put("objInr", bizMakeLoans.getId());

            // 台账改到放款时记录，发送还款计划和更新还款计划都不再处理台账
            // 记录放款台账 enable = 1
            // boolean b = updateMakeLoanStandingBook(dataMap);
            // 交易流水
            bizTRNProvider.saveMakeLoansTRN(dataMap);
            return "发送还款计划成功";

        }catch (Exception es) {
            txManager.rollback(status);
            es.printStackTrace();
            throw new RuntimeException("save makeLoanInfo（repay） error! reason=="+es.getMessage());
        }
    }

    @Override
    @Transactional
    public String updateRepaymentPlan(Map<String, Object> params) {

        // 手工进行数据库事务控制，对表的增删改操作都使用mapper的方法，或自己写的方法， 但是方法报错需要抛出异常
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try {

            Date dat = new Date();
            // 日期
            String date = DateUtil.format(dat);
            // 时间
            String time = DateUtil.formatTime(dat);
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
            dataMap.put("date", dat);// 使用同一个日期
            bizDebtGrantProvider.updateDebtGrantStatus(grantCode, grantStatus);
            // 更新放款主表enable = 1;
            BizMakeLoans bizMakeLoans = updateBizMakeLoans(dataMap);
            // 更新放款台账enable = 1
            dataMap.put("objInr", bizMakeLoans.getId());
            // 记录放款台账 enable = 1
            // boolean b = updateMakeLoanStandingBook(dataMap);
            // 交易流水
            bizTRNProvider.saveMakeLoansTRN(dataMap);
            return "还款计划发送成功";
        }catch (Exception es) {
            txManager.rollback(status);
            es.printStackTrace();
            throw new RuntimeException("update makeLoanInfo（repay） error! reason=="+es.getMessage());
        }
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
        Map<String, Object> custMap = getCustomInfoByParams(debtInfoForMakeLoan);
        String mdelstr = StringUtil.objectToString(custMap.get("mdellist"));;
        if (!"".equals(mdelstr)&& null != mdelstr){
            map.put("code", "500");
            map.put("msg", "数据异常，请联系管理员！");
            return map;
        }
        org64003.add(StringUtil.objectToString(custMap.get("type")));
//        Map guaranteeInfoList = getGuaranteeInfoList(grantCode);
        Map guaranteeInfoList = getGuaranteeInfoList1(grantCode);
        org64003.add(StringUtil.objectToString(guaranteeInfoList.get("guaranteeType")));// 担保方式
        // 用信主体
<<<<<<< HEAD
        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        org64003.add(customInfoParams);
=======
        debtInfoForMakeLoan.put("grantCode",grantCode);


//        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        String guarModified = StringUtil.objectToString(guaranteeInfoList.get("guarModified"));
//        org64003.add(customInfoParams)

        org64003.add("".equals(StringUtil.objectToString(custMap.get("max"))) ? "no" : StringUtil.objectToString(custMap.get("max")));//公开额度、最高综合授信额度、专项额度
        org64003.add("".equals(StringUtil.objectToString(custMap.get("credit"))) ? "no" : StringUtil.objectToString(custMap.get("credit")));// 信用（免担保）
        org64003.add("".equals(StringUtil.objectToString(custMap.get("maxGuarantee"))) ? "no" : StringUtil.objectToString(custMap.get("maxGuarantee")));//最高保证额度列表
        org64003.add(guarModified);//担保方式是否修改标识
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
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
<<<<<<< HEAD
=======
                Map param =new HashMap();
                param.put("grantCode",grantCode);
                param.put("bizStatus","3");
                param.put("orderBy","CREATE_TIME");
                List<BizTRN> trnList = bizTRNMapper.selectByMap(param);
                Long trnid=null;
                if(null != trnList && trnList.size()>0){
                    BizTRN trn = trnList.get(0);
                    trnid=trn.getId();
                }
                List allcustList = (List)custMap.get("allcustlist");
                if(null!=allcustList &&allcustList.size()>0 ){
                    for (int i = 0; i <allcustList.size() ; i++) {
                        Map custMapa = (Map)allcustList.get(i);
                        String limId = StringUtil.objectToString(custMapa.get("limId"));
                        String cur = StringUtil.objectToString(custMapa.get("Cur"));
                        BigDecimal amt = new BigDecimal(StringUtil.objectToString(custMapa.get("amt")));
                        String createBy = StringUtil.objectToString(custMapa.get("createBy"));
                    //添加额度占用台账 sinosong 记录额度占用台账,需要提供lim记录
                    boolean limRes = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_CUST_LIMIT",Long.valueOf(limId),trnid,BizContant.LIMIT_IN_CBTTXT,BizContant.LIMITSUM_CBCTXT,cur,amt,null,null,new Date(),Long.valueOf(createBy)));
                    }
                }




>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
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
//        Map guaranteeInfoList = getGuaranteeInfoList(grantCode);
        Map guaranteeInfoList = getGuaranteeInfoList1(grantCode);
        org64003.add(StringUtil.objectToString(guaranteeInfoList.get("guaranteeType")));// 担保方式
        // 用信主体
<<<<<<< HEAD
        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        org64003.add(customInfoParams);
=======
        debtInfoForMakeLoan.put("grantCode",grantCode);
        Map<String, Object> custMap = getCustomInfoByParams(debtInfoForMakeLoan);

//        String customInfoParams = getCustomInfoParams(debtInfoForMakeLoan);
        String guarantee = StringUtil.objectToString(guaranteeInfoList.get("guarantee"));
        String mortgage = StringUtil.objectToString(guaranteeInfoList.get("mortgage"));
        String credit = StringUtil.objectToString(guaranteeInfoList.get("credit"));
        String guarModified = StringUtil.objectToString(guaranteeInfoList.get("guarModified"));
//        org64003.add(customInfoParams)

        org64003.add("".equals(StringUtil.objectToString(custMap.get("max"))) ? "no" : StringUtil.objectToString(custMap.get("max")));//公开额度、最高综合授信额度、专项额度
        org64003.add("".equals(StringUtil.objectToString(custMap.get("credit"))) ? "no" : StringUtil.objectToString(custMap.get("credit")));// 信用（免担保）
        org64003.add("".equals(StringUtil.objectToString(custMap.get("maxGuarantee"))) ? "no" : StringUtil.objectToString(custMap.get("maxGuarantee")));//最高保证额度列表
        org64003.add(guarModified);//担保方式是否修改标识
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
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
        boolean addflag = (boolean)params.get("addflag");
        if (addflag) {//新增条件
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
        }else {
            // 查询担保合同信息
            List guaranteeContract = getGuaranteeContract(grantCode);
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
        Map qrymap = new HashMap();
        qrymap.put("grantCode",grantCode);
        List<BizDebtGrant> grant = bizDebtGrantMapper.selectByMap(qrymap);
        if (null ==grant || grant.size()<=0) {
            map.put("code", "500");
            map.put("msg", "业务与担保合同关系解除接口调用失败，请联系管理员！");
            return map;
        }
        int cn = grant.get(0).getChangeNum();
        List guaranteeContract =new ArrayList();
        if (cn ==0 ){
           guaranteeContract = getGuaranteeContract(grantCode);
        }else {
           guaranteeContract = getGuaranteeContractbyGrantAlter(grantCode);
        }
        // 查询担保合同信息


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

                if(cn ==0){
                    paramsFour.add(amount);//担保金额
                    paramsFour.add(type);// 002 生效   004解除  009结清
                }else{
                    paramsFour.add(StringUtil.objectToString(contract.get("type")));// 002 生效   004解除  009结清
                    paramsFour.add(StringUtil.objectToString(contract.get("amount")));//担保金额
                }

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
                bizMakeLoans.setDateOfLoan((Date) params.get("date"));//暂存当前时间
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
            // 异常抛到外层，用于手工控制事务，统一回滚
            // return null;
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
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


    private Map getGuaranteeInfoList1(String grantCode) {
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
        // 保证
        List oldguaranteeList = new ArrayList();
        // 抵押质押
        List oldmortgageList = new ArrayList();
        // 信用
        List oldcreditList = new ArrayList();
        //担保方式是否修改标识 0——是 ；1——否
        int guarModified= 1;
        // 担保类型
        List type = new ArrayList();
        if (guaranteeInfoList != null && guaranteeInfoList.size() > 0) {
            Map gimap  = (Map) guaranteeInfoList.get(0);
            int a = Integer.valueOf(StringUtil.objectToString(gimap.get("changeNum")));

            for (int i = 0; i < guaranteeInfoList.size(); i++) {
                Map guaranteeInfo = (Map) guaranteeInfoList.get(i);
                String typePoint = StringUtil.objectToString(guaranteeInfo.get("typePoint"));
                int changeNum = Integer.valueOf(StringUtil.objectToString(gimap.get("changeNum")));
                if(a==changeNum){
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
                }else if(a>changeNum){
                    if ("C101".equals(typePoint)) {
                        oldguaranteeList.add(guaranteeInfo);
                        guarModified =0;

                    } else if ("C102".equals(typePoint) || "C103".equals(typePoint)) {
                        oldmortgageList.add(guaranteeInfo);
                        guarModified=0;
                    } else if ("C000".equals(typePoint)) {
                        oldcreditList.add(guaranteeInfo);
                        guarModified=0;
                    }
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
            dataMap.put("guarModified", guarModified);
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
<<<<<<< HEAD

=======
    /**
     * @Description: 额度占用接口 用信主体占用额度
     * @Param: [params]
     * @return: java.lang.String
     */
    private Map<String,Object> getCustomInfoByParams(Map params) {
  /**      // 获取用信主体相关信息
        List productCustomerInfoList = bizDebtGrantProvider.getProductCustomerInfo(params);
        // 发放金额
        BigDecimal grantAmt = new BigDecimal(StringUtil.objectToString(params.get("grantAmt")));
        //公开
        StringBuilder stringBuilder = new StringBuilder();
        //信用
        StringBuilder creditStrBuilder = new StringBuilder();
        //最高保证
        StringBuilder maxGuaranteeStrBuilder = new StringBuilder();
        try {
            if (productCustomerInfoList != null && productCustomerInfoList.size() > 0) {
                //todo
                Map map = new HashMap();
                Map qrymap = new HashMap();
                qrymap.put("GRANT_CODE",params.get("grantCode"));
                List<BizDebtGrant> debtgrantList= bizDebtGrantMapper.selectByMap(qrymap);
                List<BizGuaranteeInfo> guranlist=new ArrayList<BizGuaranteeInfo>();
                if(null != debtgrantList && debtgrantList.size()>0){
                    map.put("DEBT_CODE",debtgrantList.get(0).getDebtCode());
                    guranlist=bizGuaranteeInfoMapper.selectByMap(map);
                }

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
                    // 信用
                    if("0001".equals(amountType) || "0002".equals(amountType) || "0003".equals(amountType) || "0004".equals(amountType) || "0007".equals(amountType)){

                        if (i != productCustomerInfoList.size() - 1) {
                            creditStrBuilder.append(custNo).append("|").append(amountType).append("|").append(currency).append("|")
                                    .append(amt).append("|+|");
                        } else {
                            creditStrBuilder.append(custNo).append("|").append(amountType).append("|").append(currency).append("|")
                                    .append(amt);
                        }

                    }
                    // 最高保证
                    if("0006".equals(amountType) ){
                        if(null!=guranlist && guranlist.size()>0 ){
                            for (int j = 0; j < guranlist.size(); j++) {
                                BizGuaranteeInfo    guar=guranlist.get(j);
                                if (null !=guar && custNo.equals(guar.getGuarantor())){
                                    if (i != productCustomerInfoList.size() - 1) {
                                        maxGuaranteeStrBuilder.append(custNo).append("|").append(guar.getGuarantor()).append("|").append(amountType).append("|").append(currency).append("|")
                                                .append(amt).append("|+|");
                                    } else {
                                        maxGuaranteeStrBuilder.append(custNo).append("|").append(guar.getGuarantor()).append("|").append(amountType).append("|").append(currency).append("|")
                                                .append(amt);
                                    }
                                }
                            }


                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        Map resultmap = new HashMap();
        resultmap.put("max",stringBuilder.toString());
        resultmap.put("credit",creditStrBuilder.toString());
        resultmap.put("maxGuarantee",maxGuaranteeStrBuilder.toString());;**/

        // 获取用信主体相关信息 按修改次数倒叙排列
        List mlCustomerInfoList = bizDebtGrantProvider.getMakeLoansCustomerInfo(params);
        //拼装用信主体数据
        Map custMap=getCustInfByLimitType(mlCustomerInfoList);
        return custMap;
    }

    private Map getCustInfByLimitType(List mlCustomerInfoList) {

        //公开,最高，专项
        StringBuilder  maxsb  = new StringBuilder();
        //信用
        StringBuilder  creditsb  = new StringBuilder();
        // 最高担保
        StringBuilder  guransb  = new StringBuilder();
        String type ="1";  //额度标志 1：占用，2：修改
        Map resultmap = new HashMap();
        List allcustlist = new ArrayList();
        if (null != mlCustomerInfoList && mlCustomerInfoList.size()>0) {
            int a = 0;


            List oldmaxlist = new ArrayList();
            List newmaxlist = new ArrayList();
            List maddlist = new ArrayList();
            List maddrelist = new ArrayList();
            List mdellist = new ArrayList();
            List mdelrelist = new ArrayList();

            List oldcreditlist = new ArrayList();
            List newcreditlist = new ArrayList();
            List caddlist = new ArrayList();
            List caddrelist = new ArrayList();
            List cdellist = new ArrayList();
            List cdelrelist = new ArrayList();


            List oldmaxGuaranteelist = new ArrayList();
            List newmaxGuaranteelist = new ArrayList();
            List gaddlist = new ArrayList();
            List gaddrelist = new ArrayList();
            List gdellist = new ArrayList();
            List gdelrelist = new ArrayList();




            Map fcust = (Map) mlCustomerInfoList.get(0);
            a = Integer.valueOf(StringUtil.objectToString(fcust.get("changeNum")));
            for (int i = 0; i < mlCustomerInfoList.size(); i++) {
                Map mlc = (Map) mlCustomerInfoList.get(0);
                String amountType = StringUtil.objectToString(fcust.get("amountType"));//额度类型
                String custNo = StringUtil.objectToString(fcust.get("custNo"));//客户号
                if (Integer.valueOf(StringUtil.objectToString(fcust.get("changeNum"))) == a) {
                    if ("0000".equals(amountType) || "TP03".equals(amountType) || "TP04".equals(amountType) || "TP05".equals(amountType) || "TP09".equals(amountType)) {
                        //公开，最高，专项
                        newmaxlist.add(mlc);
                        maddlist.add(custNo);
                        mdelrelist.add(custNo);
                    } else if ("0001".equals(amountType) || "0002".equals(amountType) || "0003".equals(amountType) || "0004".equals(amountType) || "0007".equals(amountType)) {
                        //信用
                        newcreditlist.add(mlc);
                        caddlist.add(custNo);
                        cdelrelist.add(custNo);
                    } else if ("0006".equals(amountType)) {
                        //最高保证
                        newmaxGuaranteelist.add(mlc);
                        gaddlist.add(mlc);
                        gdelrelist.add(mlc);
                    }


                } else if (Integer.valueOf(StringUtil.objectToString(fcust.get("changeNum"))) < a) {
                    if ("0000".equals(amountType) || "TP03".equals(amountType) || "TP04".equals(amountType) || "TP05".equals(amountType) || "TP09".equals(amountType)) {
                        //公开，最高，专项
                        oldmaxlist.add(mlc);
                        mdelrelist.add(custNo);
                        mdellist.add(custNo);
                    } else if ("0001".equals(amountType) || "0002".equals(amountType) || "0003".equals(amountType) || "0004".equals(amountType) || "0007".equals(amountType)) {
                        //信用
                        oldcreditlist.add(mlc);
                        cdelrelist.add(custNo);
                        cdellist.add(custNo);
                    } else if ("0006".equals(amountType)) {
                        //最高保证
                        oldmaxGuaranteelist.add(mlc);
                        gdellist.add(mlc);
                        gaddrelist.add(mlc);
                    }
                }
            }
            //===================
            //公开,最高，专项
            //add
            maddlist.removeAll(maddrelist);
            //del
            mdellist.removeAll(mdelrelist);

            if(null !=mdellist && mdellist.size()>0){
                resultmap.put("mdellist",1);
                return resultmap;
            }

            for (int i = 0; i < newmaxlist.size(); i++) {
                Map newmaxcust =  (Map) newmaxlist.get(i);
                String nmcustno = StringUtil.objectToString(newmaxcust.get("custNo"));
                String nmamt = StringUtil.objectToString(newmaxcust.get("amt"));
                String nmamountType = StringUtil.objectToString(newmaxcust.get("amountType"));
                String nmcur = StringUtil.objectToString(newmaxcust.get("cur"));
                String nfirstId = StringUtil.objectToString(newmaxcust.get("firstId"));


                for (int j = 0; j <oldmaxlist.size() ; j++) {
                    Map oldmaxcust =  (Map) newmaxlist.get(j);
                    String omcustno = StringUtil.objectToString(oldmaxcust.get("custNo"));
                    String omamt = StringUtil.objectToString(oldmaxcust.get("amt"));
                    //余额=总额-释放
                    Map  paramap =  new HashMap();
                    paramap.put("custno",nmcustno);
                    paramap.put("firstId",nfirstId);

                    BigDecimal repaySumAmt = new BigDecimal(0);
                    List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                    if (null == list && list.size() >0) {

                        repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                    }

                    if (nmcustno.equals(omcustno)){
                        type="2";
                        if(nmamt.equals(omamt)){//same
//                            maxsb.append(nmcustno).append("|").append(nmamountType).append("|").append(nmcur).append("|")
//                                    .append(nmamt).append("|").append(new BigDecimal(nmamt).subtract(repaySumAmt)).append("|").append("04").append("|+|");
                        }else {//upt
                            maxsb.append(nmcustno).append("|").append(nmamountType).append("|").append(nmcur).append("|")
                                    .append(nmamt).append("|").append(new BigDecimal(nmamt).subtract(repaySumAmt)).append("|").append("02").append("|+|");

                            allcustlist.add(newmaxcust);
                        }
                    }
                }


                for (int x = 0; x <maddlist.size(); x++) {//add
                    //余额=总额-释放
                    Map  paramap =  new HashMap();
                    paramap.put("custno",nmcustno);
                    paramap.put("firstId",nfirstId);

                    BigDecimal repaySumAmt = new BigDecimal(0);
                    List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                    if (null == list && list.size() >0) {

                        repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                    }
                    String addcustno = StringUtil.objectToString(maddlist.get(x));
                    if (nmcustno.equals(addcustno)) {
                        maxsb.append(nmcustno).append("|").append(nmamountType).append("|").append(nmcur).append("|")
                                .append(nmamt).append("|").append(new BigDecimal(nmamt).subtract(repaySumAmt)).append("|").append("01").append("|+|");
                        allcustlist.add(newmaxcust);
                    }
                }
            }

  /**          for (int y = 0; y <mdellist.size() ; y++) {//del
                type="2";
                Map oldmaxcust =  (Map) mdellist.get(y);
                String omcustno = StringUtil.objectToString(oldmaxcust.get("custNo"));
                String omamt = StringUtil.objectToString(oldmaxcust.get("amt"));
                String omamountType = StringUtil.objectToString(oldmaxcust.get("amountType"));
                String omcur = StringUtil.objectToString(oldmaxcust.get("cur"));
                String ofirstId = StringUtil.objectToString(oldmaxcust.get("firstId"));
                //余额=总额-释放
                Map  paramap =  new HashMap();
                paramap.put("custno",omcustno);
                paramap.put("firstId",ofirstId);

                BigDecimal repaySumAmt = new BigDecimal(0);
                List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                if (null == list && list.size() >0) {

                    repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                }



                maxsb.append(omcustno).append("|").append(omamountType).append("|").append(omcur).append("|")
                            .append(omamt).append("|").append(new BigDecimal(omamt).subtract(repaySumAmt)).append("|").append("03").append("|+|");
            }**/

            //===============================
            //信用
            //add
            caddlist.removeAll(caddrelist);
            //del
            cdellist.removeAll(cdelrelist);
            for (int i = 0; i < newcreditlist.size(); i++) {
                Map newcreditcust =  (Map) newcreditlist.get(i);
                String nccustno = StringUtil.objectToString(newcreditcust.get("custNo"));
                String ncamt = StringUtil.objectToString(newcreditcust.get("amt"));
                String ncamountType = StringUtil.objectToString(newcreditcust.get("amountType"));
                String nccur = StringUtil.objectToString(newcreditcust.get("cur"));

                for (int j = 0; j <oldcreditlist.size() ; j++) {
                    Map oldcreditcust =  (Map) newcreditlist.get(j);
                    String occustno = StringUtil.objectToString(oldcreditcust.get("custNo"));
                    String ocamt = StringUtil.objectToString(oldcreditcust.get("amt"));
                    //余额=总额-释放


                    if (nccustno.equals(occustno)){
                        type="2";
                        if(ncamt.equals(ocamt)){
                            creditsb.append(nccustno).append("|").append(ncamountType).append("|").append(nccur).append("|")
                                    .append(ncamt).append("|").append(ncamt).append("|").append("04").append("|+|");
                        }else {
                            creditsb.append(nccustno).append("|").append(ncamountType).append("|").append(nccur).append("|")
                                    .append(ncamt).append("|").append("02").append("|+|");
                            allcustlist.add(newcreditcust);
                        }
                    }
                }

                for (int x = 0; x <caddlist.size(); x++) {
                    type="2";
                    String addcustno = StringUtil.objectToString(caddlist.get(x));
                    if (nccustno.equals(addcustno)) {
                        creditsb.append(nccustno).append("|").append(ncamountType).append("|").append(nccur).append("|")
                                .append(ncamt).append("|").append("01").append("|+|");
                        allcustlist.add(newcreditcust);
                    }
                }
            }

            for (int y = 0; y <cdellist.size() ; y++) {
                type="2";
                Map oldcreditcust =  (Map) cdellist.get(y);
                String occustno = StringUtil.objectToString(oldcreditcust.get("custNo"));
                String ocamt = StringUtil.objectToString(oldcreditcust.get("amt"));
                String ocamountType = StringUtil.objectToString(oldcreditcust.get("amountType"));
                String occur = StringUtil.objectToString(oldcreditcust.get("cur"));

                creditsb.append(occustno).append("|").append(ocamountType).append("|").append(occur).append("|")
                                .append(ocamt).append("|").append(ocamt).append("|").append("03").append("|+|");
                allcustlist.add(oldcreditcust);
           }
            // 最高担保
            //add
            gaddlist.removeAll(gaddrelist);
            //del
            gdellist.removeAll(gdelrelist);
            for (int i = 0; i < newmaxGuaranteelist.size(); i++) {
                Map newmaxGuaranteecust =  (Map) newmaxGuaranteelist.get(i);
                String ngcustno = StringUtil.objectToString(newmaxGuaranteecust.get("custNo"));
                String ngamt = StringUtil.objectToString(newmaxGuaranteecust.get("amt"));
                String ngamountType = StringUtil.objectToString(newmaxGuaranteecust.get("amountType"));
                String ngcur = StringUtil.objectToString(newmaxGuaranteecust.get("cur"));
                String nfirstId = StringUtil.objectToString(newmaxGuaranteecust.get("firstId"));

                for (int j = 0; j <oldmaxGuaranteelist.size() ; j++) {
                    Map oldmaxGuaranteecust =  (Map) newmaxGuaranteelist.get(j);
                    String ogcustno = StringUtil.objectToString(oldmaxGuaranteecust.get("custNo"));
                    String ogamt = StringUtil.objectToString(oldmaxGuaranteecust.get("amt"));
                    //余额=总额-释放


                    if (ngcustno.equals(ogcustno)){

                        Map  paramap =  new HashMap();
                        paramap.put("custno",ngcustno);
                        paramap.put("firstId",nfirstId);

                        BigDecimal repaySumAmt = new BigDecimal(0);
                        List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                        if (null == list && list.size() >0) {

                            repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                        }


                        type="2";
                        if(ngamt.equals(ogamt)){
                            guransb.append(ngcustno).append("|").append(ngamountType).append("|").append(ngcur).append("|")
                                    .append(ngamt).append("|").append(repaySumAmt).append("|").append("04").append("|+|");
                        }else {
                            guransb.append(ngcustno).append("|").append(ngamountType).append("|").append(ngcur).append("|")
                                    .append(ngamt).append("|").append(repaySumAmt).append("|").append("02").append("|+|");
                            allcustlist.add(newmaxGuaranteecust);
                        }
                    }
                }

                for (int x = 0; x <gaddlist.size(); x++) {

                    String addcustno = StringUtil.objectToString(caddlist.get(x));
                    Map  paramap =  new HashMap();
                    paramap.put("custno",ngcustno);
                    paramap.put("firstId",nfirstId);

                    BigDecimal repaySumAmt = new BigDecimal(0);
                    List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                    if (null == list && list.size() >0) {

                        repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                    }
                    if (ngcustno.equals(addcustno)) {
                        guransb.append(ngcustno).append("|").append(ngamountType).append("|").append(ngcur).append("|")
                                .append(ngamt).append("|").append(repaySumAmt).append("|").append("01").append("|+|");
                        allcustlist.add(newmaxGuaranteecust);
                    }
                }
            }

            for (int y = 0; y <gdellist.size() ; y++) {
                type="2";
                Map oldmaxGuaranteecust =  (Map) gdellist.get(y);
                String ogcustno = StringUtil.objectToString(oldmaxGuaranteecust.get("custNo"));
                String ogamt = StringUtil.objectToString(oldmaxGuaranteecust.get("amt"));
                String ogamountType = StringUtil.objectToString(oldmaxGuaranteecust.get("amountType"));
                String ogcur = StringUtil.objectToString(oldmaxGuaranteecust.get("cur"));
                String ogfirstId = StringUtil.objectToString(oldmaxGuaranteecust.get("firstId"));


                Map  paramap =  new HashMap();
                paramap.put("custno",ogcustno);
                paramap.put("firstId",ogfirstId);

                BigDecimal repaySumAmt = new BigDecimal(0);
                List list = bizDebtGrantProvider.getSumRepayAmtbyCust(paramap);
                if (null == list && list.size() >0) {

                    repaySumAmt=new BigDecimal ( StringUtil.objectToString(((Map) list.get(0)).get("sumAmt"))) ;
                }

                guransb.append(ogcustno).append("|").append(ogamountType).append("|").append(ogcur).append("|")
                            .append(ogamt).append("|").append(repaySumAmt).append("|").append("03").append("|+|");
                allcustlist.add(oldmaxGuaranteecust);

            }
            allcustlist.addAll(newmaxlist);

        }



        resultmap.put("max",maxsb.substring(0,maxsb.length()-2));
        resultmap.put("credit",creditsb.substring(0,creditsb.length()-2));
        resultmap.put("maxGuarantee",guransb.substring(0,guransb.length()-2));
        resultmap.put("type",type);
        resultmap.put("allcustlist",allcustlist);
        return resultmap;
    }
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
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
//        List guaranteeInfoList = bizGuaranteeResultProvider.getGuaranteeRelationInfoList(paramMap);
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
     * @Description: 发放条件修改最高额担保合同参数
     * @Param: [grantCode]
     * @return: java.util.List
     */
    private List getGuaranteeContractbyGrantAlter(String grantCode) {
        List list = new ArrayList();
        List newlist = new ArrayList();
        List oldlist = new ArrayList();
        List addlist = new ArrayList();
        List dellist = new ArrayList();
        List samelist = new ArrayList();
        List uptlist = new ArrayList();
        Map paramMap = new HashMap();
        paramMap.put("grantCode", grantCode);
        // 获取担保信息

        List guaranteeInfoList = bizGuaranteeResultProvider.getGuaranteeRelationInfoList(paramMap);

        if (guaranteeInfoList != null && guaranteeInfoList.size() > 0) {
            Map guar = (Map) guaranteeInfoList.get(0);
            int a = Integer.valueOf(StringUtil.objectToString(guar.get("changeNum")));
            String guarantorCustId = StringUtil.objectToString(guar.get("guarantorCustId"));// 申请人客户编号
            String warrantyContractNumber = StringUtil.objectToString(guar.get("warrantyContractNumber"));// 担保合同编号

            String clearRatioAmt = StringUtil.objectToString(guar.get("clearRatioAmt"));//可明确划分金额
            String notClearAmt = StringUtil.objectToString(guar.get("notClearAmt"));//不可明确划分金额
            for (int i = 0; i < guaranteeInfoList.size(); i++) {

                Map guaranteeInfo = (Map) guaranteeInfoList.get(i);
                if (Integer.valueOf(StringUtil.objectToString(guaranteeInfo.get("changeNum"))) == a) {

                    newlist.add(guaranteeInfo);
                    addlist.add(guaranteeInfo);
                }else if(Integer.valueOf(StringUtil.objectToString(guaranteeInfo.get("changeNum"))) < a) {
                    oldlist.add(guaranteeInfo);
                    dellist.add(guaranteeInfo);
                }

            }


            for (int m = 0; m <newlist.size() ; m++) {
                Map nguar = (Map) guaranteeInfoList.get(m);
                String nguarantorCustId = StringUtil.objectToString(nguar.get("guarantorCustId"));//申请人客户编号
                String nwarrantyContractNumber = StringUtil.objectToString(nguar.get("warrantyContractNumber"));//担保合同编号
                String nclearRatioAmt = StringUtil.objectToString(nguar.get("clearRatioAmt"));
                String nnotClearAmt = StringUtil.objectToString(nguar.get("notClearAmt"));
                for (int n = 0; n<oldlist.size() ; n++) {
                    Map oguar = (Map) guaranteeInfoList.get(n);
                    String oguarantorCustId = StringUtil.objectToString(oguar.get("guarantorCustId"));//申请人客户编号
                    String owarrantyContractNumber = StringUtil.objectToString(oguar.get("warrantyContractNumber"));//担保合同编号
                    String oclearRatioAmt = StringUtil.objectToString(oguar.get("clearRatioAmt"));
                    String onotClearAmt = StringUtil.objectToString(oguar.get("notClearAmt"));
                    if(null!=nguarantorCustId && !"".equals(nguarantorCustId) && null!=oguarantorCustId && !"".equals(oguarantorCustId) && nguarantorCustId.equals(oguarantorCustId)
                            &&null!=nwarrantyContractNumber && !"".equals(nwarrantyContractNumber) && null!=owarrantyContractNumber && !"".equals(owarrantyContractNumber) && nwarrantyContractNumber.equals(owarrantyContractNumber)
                            &&(null!=nclearRatioAmt && !"".equals(nclearRatioAmt) && null!=oclearRatioAmt && !"".equals(oclearRatioAmt) && nclearRatioAmt.equals(oclearRatioAmt)
                            ||null!=nnotClearAmt && !"".equals(nnotClearAmt) && null!=onotClearAmt && !"".equals(onotClearAmt) && nnotClearAmt.equals(onotClearAmt))
                            ){
                        //same
                        samelist.add(nguar);
                    }else if(null!=nguarantorCustId && !"".equals(nguarantorCustId) && null!=oguarantorCustId && !"".equals(oguarantorCustId) && nguarantorCustId.equals(oguarantorCustId)
                            &&null!=nwarrantyContractNumber && !"".equals(nwarrantyContractNumber) && null!=owarrantyContractNumber && !"".equals(owarrantyContractNumber) && nwarrantyContractNumber.equals(owarrantyContractNumber)
                            &&(! nclearRatioAmt.equals(oclearRatioAmt)||! nnotClearAmt.equals(onotClearAmt))
                            ){

                        String namount = "";

                        if(nclearRatioAmt != null && !"".equals(nclearRatioAmt)){
                            namount = nclearRatioAmt;
                        }
                        if(nnotClearAmt != null && !"".equals(nnotClearAmt)){
                            namount = nnotClearAmt;
                        }
                        String oamount = "";

                        if(oclearRatioAmt != null && !"".equals(oclearRatioAmt)){
                            oamount = nclearRatioAmt;
                        }
                        if(onotClearAmt != null && !"".equals(onotClearAmt)){
                            oamount = onotClearAmt;
                        }


                        nguar.put("amount",new BigDecimal(namount).subtract(new BigDecimal(oamount)));
                        nguar.put("type","0003");
                        String guaranteeContractType = StringUtil.objectToString(nguar.get("guaranteeContractType"));
                        if (!"".equals(guaranteeContractType)) {
                            switch (guaranteeContractType){
                                case("UE05"):list.add(nguar);break;// 最高额保证合同
                                case("UE12"):list.add(nguar);break;// 最高额保证合同（个人）
                                case("UE13"):list.add(nguar);break;// 最高额保证合同（对公）
                                case("UE06"):list.add(nguar);break;// 最高额抵押合同
                                case("UE15"):list.add(nguar);break;// 最高额权利质押合同
                                case("UE14"):list.add(nguar);break;// 最高额动产质押合同
                            }
                        }
                        //upt
                        uptlist.add(nguar);
                    }

                }
            }
            for (int x = 0; x <addlist.size() ; x++) {
                Map xguar = (Map) guaranteeInfoList.get(x);
                String xguarantorCustId = StringUtil.objectToString(xguar.get("guarantorCustId"));//申请人客户编号
                String xwarrantyContractNumber = StringUtil.objectToString(xguar.get("warrantyContractNumber"));//担保合同编号
                String xclearRatioAmt = StringUtil.objectToString(xguar.get("clearRatioAmt"));
                String xnotClearAmt = StringUtil.objectToString(xguar.get("notClearAmt"));
                for (int s = 0; s < samelist.size(); s++) {
                    Map sguar = (Map) guaranteeInfoList.get(s);
                    String sguarantorCustId = StringUtil.objectToString(sguar.get("guarantorCustId"));//申请人客户编号
                    String swarrantyContractNumber = StringUtil.objectToString(sguar.get("warrantyContractNumber"));//担保合同编号
                    String sclearRatioAmt = StringUtil.objectToString(sguar.get("clearRatioAmt"));
                    String snotClearAmt = StringUtil.objectToString(sguar.get("notClearAmt"));
                    if (null != xguarantorCustId && !"".equals(xguarantorCustId) && null != sguarantorCustId && !"".equals(sguarantorCustId) && xguarantorCustId.equals(sguarantorCustId)
                            && null != xwarrantyContractNumber && !"".equals(xwarrantyContractNumber) && null != swarrantyContractNumber && !"".equals(swarrantyContractNumber) && xwarrantyContractNumber.equals(swarrantyContractNumber)
                            ) {
                        //add
                        addlist.remove(sguar);
                    }

                }
            }
            for (int de = 0; de <dellist.size() ; de++) {
                Map deguar = (Map) guaranteeInfoList.get(de);
                String deguarantorCustId = StringUtil.objectToString(deguar.get("guarantorCustId"));//申请人客户编号
                String dewarrantyContractNumber = StringUtil.objectToString(deguar.get("warrantyContractNumber"));//担保合同编号
                String declearRatioAmt = StringUtil.objectToString(deguar.get("clearRatioAmt"));
                String denotClearAmt = StringUtil.objectToString(deguar.get("notClearAmt"));
                for (int sa = 0; sa<samelist.size() ; sa++) {
                    Map saguar = (Map) guaranteeInfoList.get(sa);
                    String saguarantorCustId = StringUtil.objectToString(saguar.get("guarantorCustId"));//申请人客户编号
                    String sawarrantyContractNumber = StringUtil.objectToString(saguar.get("warrantyContractNumber"));//担保合同编号
                    String saclearRatioAmt = StringUtil.objectToString(saguar.get("clearRatioAmt"));
                    String sanotClearAmt = StringUtil.objectToString(saguar.get("notClearAmt"));
                    if(null!=deguarantorCustId && !"".equals(deguarantorCustId) && null!=saguarantorCustId && !"".equals(saguarantorCustId) && deguarantorCustId.equals(saguarantorCustId)
                            &&null!=dewarrantyContractNumber && !"".equals(dewarrantyContractNumber) && null!=sawarrantyContractNumber && !"".equals(sawarrantyContractNumber) && dewarrantyContractNumber.equals(sawarrantyContractNumber)
                            ){
                        //add
                        dellist.remove(saguar);
                    }

                }
            }
            for (int ad = 0; ad <addlist.size() ; ad++) {
                Map adguar = (Map) addlist.get(ad);
                String adguarantorCustId = StringUtil.objectToString(adguar.get("guarantorCustId"));//申请人客户编号
                String adwarrantyContractNumber = StringUtil.objectToString(adguar.get("warrantyContractNumber"));//担保合同编号
                String adclearRatioAmt = StringUtil.objectToString(adguar.get("clearRatioAmt"));
                String adnotClearAmt = StringUtil.objectToString(adguar.get("notClearAmt"));
                String adamount = "";

                if(adclearRatioAmt != null && !"".equals(adclearRatioAmt)){
                    adamount = adclearRatioAmt;
                }
                if(adnotClearAmt != null && !"".equals(adnotClearAmt)){
                    adamount = adnotClearAmt;
                }
                adguar.put("amount",adamount);
                adguar.put("type","0002");
                String guaranteeContractType = StringUtil.objectToString(adguar.get("guaranteeContractType"));
                if (!"".equals(guaranteeContractType)) {
                    switch (guaranteeContractType){
                        case("UE05"):list.add(adguar);break;// 最高额保证合同
                        case("UE12"):list.add(adguar);break;// 最高额保证合同（个人）
                        case("UE13"):list.add(adguar);break;// 最高额保证合同（对公）
                        case("UE06"):list.add(adguar);break;// 最高额抵押合同
                        case("UE15"):list.add(adguar);break;// 最高额权利质押合同
                        case("UE14"):list.add(adguar);break;// 最高额动产质押合同
                    }
                }

            }
            for (int del = 0; del <dellist.size() ; del++) {
                Map delguar = (Map) dellist.get(del);
                String delguarantorCustId = StringUtil.objectToString(delguar.get("guarantorCustId"));//申请人客户编号
                String delwarrantyContractNumber = StringUtil.objectToString(delguar.get("warrantyContractNumber"));//担保合同编号
                String delclearRatioAmt = StringUtil.objectToString(delguar.get("clearRatioAmt"));
                String delnotClearAmt = StringUtil.objectToString(delguar.get("notClearAmt"));
                String delamount = "";

                if(delclearRatioAmt != null && !"".equals(delclearRatioAmt)){
                    delamount = delclearRatioAmt;
                }
                if(delnotClearAmt != null && !"".equals(delnotClearAmt)){
                    delamount = delnotClearAmt;
                }
                delguar.put("amount",delamount);
                delguar.put("type","0004");
                String guaranteeContractType = StringUtil.objectToString(delguar.get("guaranteeContractType"));
                if (!"".equals(guaranteeContractType)) {
                    switch (guaranteeContractType){
                        case("UE05"):list.add(delguar);break;// 最高额保证合同
                        case("UE12"):list.add(delguar);break;// 最高额保证合同（个人）
                        case("UE13"):list.add(delguar);break;// 最高额保证合同（对公）
                        case("UE06"):list.add(delguar);break;// 最高额抵押合同
                        case("UE15"):list.add(delguar);break;// 最高额权利质押合同
                        case("UE14"):list.add(delguar);break;// 最高额动产质押合同
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