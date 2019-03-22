package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.HttpUtil;
import org.ibase4j.core.util.MathUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.mapper.BizMakeLoansMapper;
import org.ibase4j.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 审批相关
 * @Author: YLQ
 * @CreateDate: 2018-08-08
 */
@CacheConfig(cacheNames = "bizApprovalAuditValue")
@Service(interfaceClass = BizApprovalProvider.class)
public class BizApprovalProviderImpl extends BaseProviderImpl<BizApprovalAuditValue> implements BizApprovalProvider {

    @Autowired
    private BizApprovalAuditValueProvider bizApprovalAuditValueProvider;
    @Autowired
    private BizCntProvider bizCntProvider;
    @Autowired
    private BizApprovalWorkflowTaskProvider bizApprovalWorkflowTaskProvider;
    @Autowired
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizTRNProvider bizTRNProvider;
    @Autowired
    private BizDebtGrantProvider BizDebtGrantProvider;
    @Autowired
    private WfInsTaskProvider wfInsTaskProvider;
    @Autowired
    private BizDebtSummaryProvider bizDebtSummaryProvider;
    @Autowired
    private BizLockingProvider bizLockingProvider;
    @Autowired
    private BizGuaranteeInfoProvider bizGuaranteeInfoProvider;
    @Autowired
    private BizTheRentFactoringProvider bizTheRentFactoringProvider;
    @Autowired
    private BizDebtGrantMapper bizDebtGrantMapper;
    @Autowired
    private BizMakeLoansMapper bizMakeLoansMapper;
    @Autowired
    private BizSingleProductRuleProvider bizSingleProductRuleProvider;
    @Autowired
    private BizInterfaceResultProvider bizInterfaceResultProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitApprovalForm(Map<String,Object> params){
        boolean invokeInfValue=true;
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        Date passDate=null;
        try {
            passDate = sdf.parse(format);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 生成流程审核ID
        String debtCode =  StringUtil.objectToString(params.get("debtCode"));
        String approvalId = bizCntProvider.getNextNumberFormat(debtCode,3);
        params.put("approvalId",approvalId);
        // 产品ID
        Map categoryId =new HashMap();
        categoryId.put("debtCode",debtCode);
        BizSingleProductRule bizSingleProductRule = (BizSingleProductRule)bizSingleProductRuleProvider.queryList(categoryId).get(0);
        String businessType = bizSingleProductRule.getBusinessType();
        params.put("businessType",businessType);
        // 构造审核要素对象并保存数据
        bizApprovalAuditValueProvider.saveCheckValues(params);
        // 构造审批流程任务对象并保存数据
        bizApprovalWorkflowTaskProvider.saveApprovalWorkflowTask(params);
        // 流程流转
        Map processParams = (Map) params.get("processParams");
        processParams.put("userId",params.get("userId"));
        bizProStatementProvider.completeTaskByTdid(processParams);
        // 流水记录
        String trn=StringUtil.objectToString(params.get("trn"));
        if("main".equals(trn)){
            Map params2=new HashMap();
            params2.put("debtCode",debtCode);
            BizDebtSummary bizDebtSummary = (BizDebtSummary)bizDebtSummaryProvider.queryList(params2).get(0);
            Long MainId = bizDebtSummary.getId();
            params.put("MainId",MainId);
            bizTRNProvider.saveDebtMainTRN(params);



            Map params4=new HashMap();
            params4.put("code",debtCode);
            bizLockingProvider.unlockUser(params4);
        } else if ("grant".equals(trn)){
            String grantCode=params.get("grantCode").toString();
            Map params3=new HashMap();
            params3.put("grantCode",grantCode);
            BizDebtGrant bizDebtGrant = (BizDebtGrant)BizDebtGrantProvider.queryList(params3).get(0);
            Long GrantId = bizDebtGrant.getId();
            params.put("GrantId",GrantId);
            bizTRNProvider.saveDebtGrantTRN(params);

            Map params5=new HashMap();
            params5.put("code",grantCode);
            bizLockingProvider.unlockUser(params5);
        }
        //经办修改已办状态updateHaveDone
        String updateHaveDone= StringUtil.objectToString(params.get("updateHaveDone"));
        if(updateHaveDone != null && updateHaveDone !=""){
            Map map = (Map) params.get("processParams");
            String newPiid=map.get("piid").toString();
            Map param1=new HashMap();
            param1.put("piid",newPiid);
            wfInsTaskProvider.updateInfo(param1);
        }
        //处长修改方案补录与修订状态updateDebt
        String updateDebt= StringUtil.objectToString(params.get("updateDebt"));
        if(updateDebt != null && updateDebt !=""){

            String invokeInf= StringUtil.objectToString(params.get("invokeInf"));
            if(invokeInf != null && invokeInf !=""){
                Map map =new HashMap();
                map.put("debtCode",debtCode);
                map.put("userId",params.get("userId"));
                invokeInfValue = invokeInf(map);
            }
            Map<String, Object> param =new HashMap<>();
            //if(invokeInfValue==true){
                param.put("debtCode",debtCode);
                String commite=params.get("commite").toString();
                param.put("commite", commite);
                bizDebtSummaryProvider.getSchemeState(param);
            //}

        }
        //处长修改发放废弃、发放审核驳回及及变更状态updateGrant
        String updateGrant= StringUtil.objectToString(params.get("updateGrant"));
        if(updateGrant != null && updateGrant !=""){
            String commite=params.get("commite").toString();
            String grantCode=params.get("grantCode").toString();
            Map params1=new HashMap();
            params1.put("grantCode",grantCode);
            BizDebtGrant bizDebtGrant =(BizDebtGrant)BizDebtGrantProvider.queryList(params1).get(0);
            if("FFTT".equals(commite)){
                //已批未放
                bizDebtGrant.setGrantStatus(BizStatus.GRANUNPU);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRAPPR);
                bizDebtGrant.setPassDate(passDate);
            }else if("FFBH".equals(commite)){
                //已驳回
                bizDebtGrant.setGrantStatus(BizStatus.GRANDOWN);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRDOWN);
            }else if("TT".equals(commite)){
                //已废弃
                bizDebtGrant.setGrantStatus(BizStatus.GRANOBSO);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRAPPR);
            }else if("FQBH".equals(commite)){
                //已驳回
                bizDebtGrant.setGrantStatus(BizStatus.GRANUNPU);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRDOWN);
            }else if("BGTT".equals(commite)) {
                //失效
                String originalGrantCode = bizDebtGrant.getOriginalGrantCode();
                Map params2=new HashMap();
                params2.put("grantCode",originalGrantCode);
                BizDebtGrant bizDebtGrantBG= (BizDebtGrant)BizDebtGrantProvider.queryList(params2).get(0);
                bizDebtGrantBG.setGrantStatus(BizStatus.GRANINVA);
                bizDebtGrantBG.setProcessStatus(BizStatus.GRPRAPPR);
                bizDebtGrantBG.setPassDate(passDate);
                BizDebtGrantProvider.update(bizDebtGrantBG);

                //已变更
                bizDebtGrant.setGrantStatus(BizStatus.GRANCHAN);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRAPPR);
                bizDebtGrant.setPassDate(passDate);

            }else if("BGBH".equals(commite)){
                //已驳回
                bizDebtGrant.setGrantStatus(BizStatus.GRANDOWN);
                bizDebtGrant.setProcessStatus(BizStatus.GRPRDOWN);
                String originalGrantCode = bizDebtGrant.getOriginalGrantCode();
                Map params2=new HashMap();
                params2.put("grantCode",originalGrantCode);
                BizDebtGrant bizDebtGrantBG= (BizDebtGrant)BizDebtGrantProvider.queryList(params2).get(0);
                //冻结
                bizDebtGrantBG.setGrantStatus(BizStatus.GRANFROZ);
                bizDebtGrantBG.setProcessStatus(BizStatus.GRPRAPPR);
                BizDebtGrantProvider.update(bizDebtGrantBG);
            }
            BizDebtGrantProvider.update(bizDebtGrant);
        }
    }

    @Override
    public String stopProcess(Map<String,Object> params){
        String process=StringUtil.objectToString(params.get("process"));
        if("FFSH".equals(process)){
            Map processParams = (Map)params.get("processParams");
            String piid = processParams.get("piid").toString();
            Map map=new HashMap();
            map.put("piid",piid);
            wfInsTaskProvider.updateAllInfo(map);

            String grantCode=params.get("grantCode").toString();
            Map map1=new HashMap();
            map1.put("grantCode",grantCode);
            List<BizDebtGrant> bizDebtGrants = BizDebtGrantProvider.queryList(map1);
            BizDebtGrant bizDebtGrant = bizDebtGrants.get(0);
            Integer enable = bizDebtGrant.getEnable();
            if(enable!=0) {
                bizDebtGrant.setGrantStatus(BizStatus.GRANINVA);
                BizDebtGrantProvider.update(bizDebtGrant);
                Integer solutionState = (Integer) params.get("solutionState");
                if(solutionState==11){
                    return "此笔发放审核已失效";
                }
                return "发放对应方案已修订，此笔作废";
            }
            return "此笔发放审核已删除";
        }else{
            Map processParams = (Map)params.get("processParams");
            String piid = processParams.get("piid").toString();
            Map map=new HashMap();
            map.put("piid",piid);
            wfInsTaskProvider.updateAllInfo(map);
            return "此笔方案审核已失效";
        }
    }
    /**
    * @Description: 查询债项发放历史审批意见
    * @Param: [param]
    * @return: com.baomidou.mybatisplus.plugins.Page<org.ibase4j.model.BizApprovalWorkflowTask>
    */
    @Override
    public Page<BizApprovalWorkflowTask> getHistoryCommentPage (Map<String,Object> param){
        Page<BizApprovalWorkflowTask> historyCommentPage = bizApprovalWorkflowTaskProvider.getHistoryCommentPage(param);
        return historyCommentPage;
    }

    /** 
    * @Description: 根据审批流程id查询check项的值 
    * @Param: [params] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    */ 
    @Override
    public Map<String, Object> getCheakValuesByApprovalId(Map<String, Object> params) {
        Map<String, Object> cheakValues = bizApprovalAuditValueProvider.getCheakValuesByApprovalId(params);
        return cheakValues;
    }
    /**
     * @Description: 调64007接口
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public boolean invokeInf(Map<String, Object> params){
        String debtCode =  StringUtil.objectToString(params.get("debtCode"));
        Map map3=new HashMap();
        map3.put("debtCode",debtCode);
        BizDebtSummary bizDebtSummary1=(BizDebtSummary)bizDebtSummaryProvider.queryList(map3).get(0);
        logger.debug("==============invokeInf=============方案信息================================"+bizDebtSummary1);
        if(bizDebtSummary1!=null){
            Long id = bizDebtSummary1.getId();
            //地区号
            Map map1=new HashMap();
            map1.put("userId",params.get("userId").toString());
            String orgCode =bizDebtGrantMapper.getAreaNumberByUserId(map1);
            //大交易号
            int big64007 = bizCntProvider.getNextNumber("big64007");
            //小交易号
            int small64007 = bizCntProvider.getNextNumber("small64007") - 1;
            //客户号
            String proposerNum = bizDebtSummary1.getProposerNum();
            //贷款性质(政策性属性)
            String policy = bizDebtSummary1.getPolicy();
            //担保类型
            String typePoint="";
            Map map2=new HashMap();
            map2.put("debtCode",debtCode);
            List<BizGuaranteeInfo> list = bizGuaranteeInfoProvider.queryList(map2);
            for(BizGuaranteeInfo bizGuaranteeInfo2:list) {
                typePoint =typePoint+bizGuaranteeInfo2.getTypePoint();
            }
            logger.debug("=================invokeInf===============typePoint======================================"+typePoint);
            //产品种类

            //日期
            Date createTime = bizDebtSummary1.getCreateTime();
            String format = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
            //容忍期(BIZ_RENTAL_FACTORING_KEY)
            Map map=new HashMap();
            map.put("debtCode",debtCode);
            BizTheRentFactoring bizTheRentFactoring1 = (BizTheRentFactoring)bizTheRentFactoringProvider.queryList(map).get(0);
            Long tolerancePertod = bizTheRentFactoring1.getTolerancePertod();
            //方案币种
            String mpc = bizDebtSummary1.getMpc();
            Map dataMap = new HashMap();
            dataMap.put("currency",mpc);
            String currencyNo = bizMakeLoansMapper.getCurrencyNo(dataMap);
            //金额
            BigDecimal amount = bizDebtSummary1.getSolutionAmount();
            String solutionAmount=MathUtil.formatToNumber(amount);

            Date date=new Date();
            //当前年月日
            String nowBig = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //当前时分秒
            String nowSmall = new SimpleDateFormat("HH:mm:ss").format(date);


            String[] requestParams =  new String[]{
                                "'"+orgCode+"', " +
                                "'"+big64007+"', " +
                                "'"+small64007+"', " +
                                "'"+proposerNum+"', " +
                                "'"+policy+"', " +
                                "'"+typePoint+"', " +
                                "'10202', " +
                                "'"+solutionAmount+"', " +
                                "'"+format+"', " +
                                "'"+tolerancePertod+"', " +
                                "'"+currencyNo+"', " +
                                "'"+nowBig+"', " +
                                "'"+nowSmall+"', " +
                                "' '"};
            params.put("requestParams", Arrays.toString(requestParams));
            // 请求前记录
            BizInterfaceResult bizInterfaceResult = new BizInterfaceResult(null,debtCode, "", "cjdkhtxy", new Date(), params.toString());
            BizInterfaceResult returnResult = bizInterfaceResultProvider.update(bizInterfaceResult);
            logger.debug("=======Before calling 64007 interface:" + "cjdkhtxy:" + " start ;" + "params : " + params.toString());
            //调用接口
            Map resultMap = HttpUtil.invokeInfExecute2("cjdkhtxy", "cjdkhtxy", requestParams);

            String code = StringUtil.objectToString(resultMap.get("code"));
            String msg="";
            String results="";
            if ("500".equals(code)) {
                msg = StringUtil.objectToString(resultMap.get("msg"));
            } else {
                results = StringUtil.objectToString(resultMap.get("results"));
            }
            String value = (String )resultMap.get("results");
            logger.debug("==============invokeInf=============接口返回值================================"+value);
            // 响应后记录
            BizInterfaceResult responseResult = new BizInterfaceResult(returnResult.getId(), results, new Date(), msg);
            bizInterfaceResultProvider.update(responseResult);
            logger.debug("=======After calling  64007 interface:" + "cjdkhtxy:" + " end " + "code is " + code);

            if(value!=null && value !=""){
                JSONArray jSONArray = JSONObject.parseArray(value);
                for(int i = 0 ; i < jSONArray.size() ; i++ ){
                    JSONObject jsonObject = jSONArray.getJSONObject(i);
                    String mediumid = jsonObject.getString("MEDIUMID");
                    String transok = jsonObject.getString("TRANSOK");
                    String errNo = jsonObject.getString("ERR_NO");
                    logger.debug("============invokeInf===============接口返回值mediumid================================"+mediumid);
                    logger.debug("============invokeInf===============接口返回值transok================================"+transok);
                    logger.debug("============invokeInf===============接口返回值errNo================================"+errNo);

                    //存储接口返回值
                    BizDebtSummary bizDebtSummary2=new BizDebtSummary();
                    bizDebtSummary2.setId(id);
                    bizDebtSummary2.setTransok(transok);
                    if("0".equals(transok)){
                    bizDebtSummary2.setIdentNumber(mediumid);
                    }
                    bizDebtSummary2.setErrNo(errNo);
                    bizDebtSummaryProvider.update(bizDebtSummary2);
                }
            } else{
                logger.debug("===============invokeInf============接口返回值================================"+value);
                return false;
            }
        }else{
              logger.debug("=================invokeInf==========方案信息================================"+bizDebtSummary1);
              return false;
        }
        return true;
    }

    /**
     * 修改已办状态
     * @param params
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void UpdateHaveDone(Map<String, Object> params){
        String debtCode=params.get("debtCode").toString();
        String piid=params.get("piid").toString();
        Map params1=new HashMap();
        params1.put("piid",piid);
        wfInsTaskProvider.updateInfo(params1);

        Map params2=new HashMap();
        params2.put("debtCode",debtCode);
        BizDebtSummary bizDebtSummary1 = (BizDebtSummary)bizDebtSummaryProvider.queryList(params2).get(0);
        Long id = bizDebtSummary1.getId();
        BizDebtSummary bizDebtSummary=new BizDebtSummary();
        bizDebtSummary.setSolutionState(BizStatus.DEBTAVAI);
        bizDebtSummary.setId(id);
        bizDebtSummaryProvider.update(bizDebtSummary);
    }

}
