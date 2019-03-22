package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.provider.*;
import org.ibase4j.vo.SumInformationVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 审批相关
 * @Author: dj
 * @CreateDate: 2018-08-08
 */
@Service
public class BizApprovalService extends BaseService<BizApprovalProvider, BizApprovalAuditValue> {

    @Reference
    public void setApprovalAuditValueProvider(BizApprovalProvider provider) {
        this.provider = provider;
    }

    @Reference
    private BizApprovalAuditValueProvider bizApprovalAuditValueProvider;

    @Reference
    private BizProStatementProvider bizProStatementProvider;

    @Reference
    private BizApprovalProvider bizApprovalProvider;

    @Reference
    private BizDebtSummaryProvider bizDebtSummaryProvider;

    @Reference
    private BizDebtGrantProvider bizDebtGrantProvider;

    @Reference
    private BizCbsErrorMessageProvider bizCbsErrorMessageProvider;

    @Reference
    private BizPTSProvider bizPTSProvider;

    @Reference
    private BizCustomerProvider bizCustomerProvider;

    @Reference
    private BizSingleProductRuleProvider singleProductRuleProvider;

    @Reference
    private IBizProductTypeProvider bizProductTypeProvider;

    @Reference
    private ISysCurrencyProvider iSysCurrencyProvider;

    @Reference
    private BizFECProvider bizFECProvider;

    /**
    * @Description: 保存债项发放审批check项的值
    * @Param: [params]
    * @return: void
    */
    public String submitApprovalForm(Map<String,Object> params){
        String debtCode=params.get("debtCode").toString();
        Map map=new HashMap();
        map.put("debtCode",debtCode);
        List<BizDebtSummary> bizDebtSummaries = bizDebtSummaryProvider.queryList(map);
        BizDebtSummary bizDebtSummary = bizDebtSummaries.get(0);
        Integer solutionState = bizDebtSummary.getSolutionState();
        String process=StringUtil.objectToString(params.get("process"));
        if("FFSH".equals(process)){
            String grantCode=params.get("grantCode").toString();
            Map map1=new HashMap();
            map1.put("grantCode",grantCode);
            List<BizDebtGrant> bizDebtGrants = bizDebtGrantProvider.queryList(map1);
            BizDebtGrant bizDebtGrant = bizDebtGrants.get(0);
            Integer enable = bizDebtGrant.getEnable();
            //判断一下发放失效时间
            Date endDate = bizDebtGrant.getEndDate();
            if (solutionState == 7 || solutionState == 8|| solutionState == 10 || enable==0) {
                logger.debug("=====debtCode={};============solutionState={};",debtCode,solutionState);
                params.put("solutionState",solutionState);
                String resply = provider.stopProcess(params);
                return resply;
            } else if (System.currentTimeMillis()>endDate.getTime()) {
                //发放条件时间过期  失效(自定义的solutionState=11)
                solutionState=11;
                logger.debug("=====debtCode={};============solutionState={};",debtCode,solutionState);
                params.put("solutionState",solutionState);
                String resply = provider.stopProcess(params);
                return resply;
            }
        }else if ("FASH".equals(process)){
            Integer enable = bizDebtSummary.getEnable();
            Date pgExpiDate = bizDebtSummary.getPgExpiDate();
            logger.debug("=====debtCode={};============solutionState={};",debtCode,solutionState);
            //方案失效或者删除后 停止工作流
            if (solutionState == 7 || solutionState == 8  || solutionState == 10||  enable == 0 ) {
                String resply = provider.stopProcess(params);
                return resply;
            }else if (System.currentTimeMillis()>pgExpiDate.getTime()) {
                //方案失效时间到期 停止流程
                String resply = provider.stopProcess(params);
                return resply;
            }
        }
        provider.submitApprovalForm(params);
        return null;
    }

    /**
    * @Description: 查询债项发放历史审批意见
     * @Param: [param]
    * @return: com.baomidou.mybatisplus.plugins.Page<org.ibase4j.model.BizApprovalWorkflowTask>
    */
    public Page<BizApprovalWorkflowTask> getHistoryCommentPage (Map<String,Object> param){
        Page<BizApprovalWorkflowTask> historyCommentPage = provider.getHistoryCommentPage(param);
        return historyCommentPage;
    }

    /**
    * @Description: 根据审批流程id查询check项的值
     * @Param: [params]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    */
   public Map<String, Object> getCheakValuesByApprovalId(Map<String, Object> params){
        Map<String, Object> cheakValues = bizApprovalAuditValueProvider.getCheakValuesByApprovalId(params);
        return cheakValues;
    }

    /**
     * @Description: 根据审批流程id查询check项的值
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> getCheakValuesDetails(Map<String, Object> params){
        Map<String, Object> DetailsCheakValues = bizApprovalAuditValueProvider.getCheckValuesDetails(params);
        return DetailsCheakValues;
    }
    /**
     * @Description: 获取前手taskID
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public String getBeforeTaskId(Map<String, Object> params){
        String beforeTaskId = bizProStatementProvider.getBeforeTaskId(params);
        return beforeTaskId;
    }
    /**
     * @Description: 调用接口
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public boolean invokeInf(Map<String, Object> params){
        return bizApprovalProvider.invokeInf(params);
    }
    /**
     * @Description: 修改已办状态
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public void UpdateHaveDone(Map<String, Object> params){
        bizApprovalProvider.UpdateHaveDone(params);

    }


    public SumInformationVo getInformation(Map<String, Object> params) {
        String debtCode=params.get("debtCode").toString();
        Map mapDebtCode=new HashMap();
        mapDebtCode.put("debtCode",debtCode);
        List<BizDebtSummary> bizDebtList=bizDebtSummaryProvider.queryList(mapDebtCode);
        BizDebtSummary bizDebt=bizDebtList.get(0);
        SumInformationVo informationVo=new SumInformationVo();
        //债项方案总金额
        informationVo.setTotalAmont(bizDebt.getSolutionAmount());
        //方案主币种
        //informationVo.setMpc(bizDebt.getMpc());
        //方案辅助币种
        informationVo.setaCurrrency(bizDebt.getaCurrrency());
        //方案其他币种
        informationVo.setOc(bizDebt.getOc());
        //合同创建状态
        String transok = bizDebt.getTransok();
        informationVo.setTransok(transok);
        //返回信息码
        String errNo = bizDebt.getErrNo();
        informationVo.setErrNo(errNo);
        //中文信息码
        if(transok !=null && transok !=""){
            if("0".equals(transok)){
                informationVo.setErrorMessage("合同创建成功");
            }else{
                BizCbsErrorMessage bizCbsErrorMessage = new BizCbsErrorMessage();
                bizCbsErrorMessage.setErrorCode(errNo);
                BizCbsErrorMessage bizCbsErrorMessage1 = bizCbsErrorMessageProvider.selectOne(bizCbsErrorMessage);
                if(bizCbsErrorMessage1==null){
                    informationVo.setErrorMessage("数据库中无此错误码");
                }else {
                    String errorMessage = bizCbsErrorMessage1.getErrorMessage();
                    informationVo.setErrorMessage(errorMessage);
                }
            }
        }else{
            informationVo.setErrorMessage("接口调用失败");
        }
        informationVo.setIdentNumber(bizDebt.getIdentNumber());

        String grantCode = params.get("grantCode").toString();
        Map mapGrant = new HashMap();
        mapGrant.put("grantCode", grantCode);
        List<BizDebtGrant> bizDebtGrant = bizDebtGrantProvider.queryList(mapGrant);
        BizDebtGrant bizDebtGrant1 = bizDebtGrant.get(0);
        BigDecimal grantAmt = bizDebtGrant1.getGrantAmt();
        Integer processStatus = bizDebtGrant1.getProcessStatus();
        Integer grantStatus = bizDebtGrant1.getGrantStatus();
        String currency = bizDebtGrant1.getCurrency();

        //发放币种
        informationVo.setMpc(currency);
        Map mapMpc = new HashMap();
        mapMpc.put("monCode",currency);
        List<SysCurrency> sysCurrency = iSysCurrencyProvider.queryList(mapMpc);
        SysCurrency sysCurrency1 = sysCurrency.get(0);
        //发放币种中文
        String codeName = sysCurrency1.getCodeName();
        informationVo.setMpcName(codeName);
        //债项方案已批未放金额
        informationVo.setNoFoundAmont(grantAmt);
        //发放审核状态
        informationVo.setSolutionState(grantStatus);
        //发放审核流程状态
        informationVo.setProcessStatus(processStatus);
        //政策性分类
        informationVo.setClassificPolicies(Long.valueOf(bizDebt.getPolicy()));
        //政策性描述
        informationVo.setPoliciesDescribe(bizDebt.getPolicyDescription());
        //汇总期限
        informationVo.setSummaryPeriod(bizDebt.getDopo());
        //汇总费率
        informationVo.setSummaryRates(bizDebt.getPackageRate());
        //汇总费率最小值
        informationVo.setRateRangeMix(bizDebt.getRateRangeMix());
        //汇总费率最大值
        informationVo.setRateRangeMax(bizDebt.getRateRangeMax());

        //利率描述
        informationVo.setAggregateRates(bizDebt.getDescriptionRateRules());
        //objinr
        Long id = bizDebtGrant1.getId();
        Map objMap=new HashMap();
        objMap.put("objInr",id);
        List<BizFEC>  fecList= bizFECProvider.queryList(objMap);
        //汇总利率
        informationVo.setFecList(fecList);
        List<BizCustomer> custList=new ArrayList<>();
        //用信主体
        List<BizPTS>bizPTSList=bizPTSProvider.queryList(params);
        for (BizPTS bizPTS:bizPTSList){
            if(bizPTS.getRole().equals("LETS")){
                BizCustomer bizCustomer=bizCustomerProvider.queryById(Long.valueOf(bizPTS.getPtyinr()));
                custList.add(bizCustomer);
            }
        }
        informationVo.setCustList(custList);
        //产品组合
        List<BizSingleProductRule> singleList=singleProductRuleProvider.queryList(params);
        BizSingleProductRule bizSingleProductRule=singleList.get(0);
        BizProductTypes bizProductTypes= bizProductTypeProvider.queryByCode(bizSingleProductRule.getBusinessType());
        informationVo.setProName(bizProductTypes.getName());
        return informationVo;
    }
}
