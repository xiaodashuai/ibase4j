package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.DataUtil;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.*;
import org.ibase4j.model.*;
import org.ibase4j.vo.BizDebtInfo;
import org.ibase4j.vo.BookkeepkingVo;
import org.ibase4j.vo.GrantRuleVerifVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

import static org.ibase4j.core.config.BizContant.*;

/**
 * @author xiaoshuiquan
 * @date 2018/05/26
 */
@CacheConfig(cacheNames = "bizDebtSummary")
@Service(interfaceClass = BizDebtSummaryProvider.class)
public class BizDebtSummaryProviderImpl extends BaseProviderImpl<BizDebtSummary> implements BizDebtSummaryProvider {
    private static final Logger log = LogManager.getLogger(BizDebtSummaryProviderImpl.class);
    @Autowired
    private BizSingleProductRuleMapper singleProductRuleMapper;
    @Autowired
    private BizDebtSummaryMapper bizDebtSummaryMapper;
    @Autowired
    private BizGuaranteeInfoMapper bizGuaranteeInfoMapper;
    @Autowired
    private BizDebtProductMapper bizDebtProductMapper;
    @Autowired
    private BizTheRentFactoringMapper bizTheRentFactoringMapper;
    @Autowired
    private BizProductLinesTypeMapper bizProductLinesTypeMapper;
    @Autowired
    private BizCustMapper bizCustomerMapper;
    @Autowired
    private BizCustMapper bizCustMapper;
    @Autowired
    private BizTRNMapper bizTRNMapper;
    @Autowired
    private BizCBBMapper bizCBBMapper;
    @Autowired
    private BizCBEMapper bizCBEMapper;
    @Autowired
    private BizSingleProductRuleMapper bizSingleProductRuleMapper;
    @Autowired
    private BizCreditLinesMapper bizCreditLinesMapper;
    @Autowired
    private BizBetInformationMapper bizBetInformationMapper;
    @Autowired
    private BizPTSMapper bizPTSMapper;
    @Autowired
    private BizApprSummaryInfoMapper bizApprSummaryInfoMapper;
    @Autowired
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizDebtSummaryProvider bizDebtSummaryProvider;
    @Autowired
    private BizGuaranteeInfoProvider bizGuaranteeInfoProvider;
    @Autowired
    private BizBetInformationProvider bizBetInformationProvider;
    @Autowired
    private BizSingleProductRuleProvider singleProductRuleProvider;
    @Autowired
    private BizTheRentFactoringProvider bizTheRentFactoringProvider;
    @Autowired
    private BizProductLinesTypeProvider bizProductLinesTypeProvider;
    @Autowired
    private BizCustProvider bizCustomerProvider;
    @Autowired
    private BizPTSProvider bizPTSProvider;
    @Autowired
    private BizTRNProvider bizTRNProvider;
    @Autowired
    private BizCBEProvider bizCBEProvider;
    @Autowired
    private BizCBBProvider bizCBBProvider;
    @Autowired
    private BizCreditLinesProvider bizCreditLinesProvider;
    @Autowired
    private BizSingleProductRuleProvider bizSingleProductRuleProvider;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    private PlatformTransactionManager txManager;
    @Autowired
    private BizCntProvider bizCntProvider;
    @Autowired
    private BizTemporarySaveProvider bizTemporarySaveProvider;

    @Transactional(rollbackFor = Exception.class)
    public void dealWithEntity(Object list, Map<String,Object> paramMap){
        //理想状态：拿到单一实体，查询库是否存在,判断关键项是否被修改，再选择新增修改或者删除 ==》 装到List里统一处理

        Date dat = new Date();
        List entityList;
        
        if(list instanceof List){
            entityList = (List)list;
        }else{
            entityList = new ArrayList();
            entityList.add(list);
        }

        if(entityList.get(0) instanceof BizTheRentFactoring){
            Map resMap = DataUtil.getMinusMap(entityList,bizTheRentFactoringProvider.queryList(paramMap),new String[]{"debtCode","businessTypes","custNo","tolerancePertod","financePlatform","custName","custTating"},true);
            for(BizTheRentFactoring obj : (List<BizTheRentFactoring>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizTheRentFactoringMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizTheRentFactoring ud : (List<BizTheRentFactoring>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                int resNum = bizTheRentFactoringMapper.updateById(ud);
                if(resNum != 1){
                    throw new RuntimeException("update error,entity ==> "+ud.toString());
                }
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                int resNum = bizTheRentFactoringMapper.deleteById(Long.parseLong(delid));
                if(resNum != 1){
                    throw new RuntimeException("delete error,del id ==> "+delid);
                }
            }
        }else if(entityList.get(0) instanceof BizCreditLines){
            Map resMap = DataUtil.getMinusMap(entityList,bizCreditLinesProvider.queryList(paramMap),new String[]{"createTime","updateTime"},false);
            for(BizCreditLines obj : (List<BizCreditLines>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizCreditLinesMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizCreditLines ud : (List<BizCreditLines>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                bizCreditLinesMapper.updateById(ud);
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                bizCreditLinesMapper.deleteById(Long.parseLong(delid));
            }
        }else if(entityList.get(0) instanceof BizSingleProductRule){
            Map resMap = DataUtil.getMinusMap(entityList,bizSingleProductRuleProvider.queryList(paramMap),new String[]{"createTime","updateTime"},false);
            for(BizSingleProductRule obj : (List<BizSingleProductRule>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizSingleProductRuleMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizSingleProductRule ud : (List<BizSingleProductRule>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                bizSingleProductRuleMapper.updateById(ud);
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                bizSingleProductRuleMapper.deleteById(Long.parseLong(delid));
            }
        }else if(entityList.get(0) instanceof BizGuaranteeInfo){
            Map resMap = DataUtil.getMinusMap(entityList,bizGuaranteeInfoProvider.queryList(paramMap),new String[]{"createTime","updateTime","betInformationList"},false);
            for(BizGuaranteeInfo obj : (List<BizGuaranteeInfo>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizGuaranteeInfoMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizGuaranteeInfo ud : (List<BizGuaranteeInfo>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                bizGuaranteeInfoMapper.updateById(ud);
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                bizGuaranteeInfoMapper.deleteById(Long.parseLong(delid));
            }
        }
        else if(entityList.get(0) instanceof BizPTS){

            List<Map<String,Object>> ptslist = new ArrayList();

            for(BizPTS pts : (List<BizPTS>) entityList){

               String ptsOinr = pts.getObjinr();
               String ptsOtyp = pts.getObjtyp();
               if((null!=ptsOtyp && !"".equals(ptsOtyp)) && (null!=ptsOinr && !"".equals(ptsOinr))){

                   String ptsKey = ptsOtyp + ptsOinr;
                   boolean addPts = true;
                   for(Map ptsmap : ptslist){
                       if(ptsKey.equals(ptsmap.get("ptsKey"))){
                           addPts = false;
                           List tmpList = (List)ptsmap.get("ptsList");
                           tmpList.add(pts);
                       }
                   }
                   if(addPts){
                       Map newMap = new HashMap<>();
                       Map newSelMap = new HashMap<>();
                       List newList = new ArrayList();
                       newList.add(pts);
                       newMap.put("ptsKey",ptsKey);
                       newMap.put("ptsList",newList);
                       newSelMap.put("objtyp",ptsOtyp);
                       newSelMap.put("objinr",ptsOinr);
                       newMap.put("selMap",newSelMap);
                       ptslist.add(newMap);
                   }
               }else{
                   log.error("pts error=="+pts.toString());
                   throw new RuntimeException("pts error=="+pts.toString());
               }

            }
            for(Map<String,Object> iserMap : ptslist){

                Map resMap = DataUtil.getMinusMap((List)iserMap.get("ptsList"),bizPTSMapper.selectByMap((Map)iserMap.get("selMap")),new String[]{"role","custNameCN"},true);

                for(BizPTS obj : (List<BizPTS>)resMap.get("addEntityList")){
                    obj.setCreateTime(dat);
                    obj.setUpdateTime(dat);
                    int resNum = bizPTSMapper.insert(obj);
                    if(resNum != 1){
                        throw new RuntimeException("insert error,entity ==> "+obj.toString());
                    }
                }
                for(BizPTS ud : (List<BizPTS>)resMap.get("updateEntityList")){
                    ud.setUpdateTime(dat);
                    bizPTSMapper.updateById(ud);
                }
                for(String delid : (List<String>)resMap.get("delIDList")){
                    bizPTSMapper.deleteById(Long.parseLong(delid));
                }
            }
        }
        else if(entityList.get(0) instanceof BizProductLinesType){
            Map resMap = DataUtil.getMinusMap(entityList,bizProductLinesTypeProvider.queryList(paramMap),new String[]{"createTime","updateTime"},false);
            for(BizProductLinesType obj : (List<BizProductLinesType>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizProductLinesTypeMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizProductLinesType ud : (List<BizProductLinesType>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                bizProductLinesTypeMapper.updateById(ud);
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                bizProductLinesTypeMapper.deleteById(Long.parseLong(delid));
            }
        }
        else if(entityList.get(0) instanceof BizBetInformation){
            Map resMap = DataUtil.getMinusMap(entityList,bizBetInformationProvider.queryList(paramMap),new String[]{"createTime","updateTime","betInformationList"},false);
            for(BizBetInformation obj : (List<BizBetInformation>)resMap.get("addEntityList")){
                obj.setCreateTime(dat);
                obj.setUpdateTime(dat);
                int resNum = bizBetInformationMapper.insert(obj);
                if(resNum != 1){
                    throw new RuntimeException("insert error,entity ==> "+obj.toString());
                }
            }
            for(BizBetInformation ud : (List<BizBetInformation>)resMap.get("updateEntityList")){
                ud.setUpdateTime(dat);
                bizBetInformationMapper.updateById(ud);
            }
            for(String delid : (List<String>)resMap.get("delIDList")){
                bizBetInformationMapper.deleteById(Long.parseLong(delid));
            }
        }

    }

	@Override
	public Page<BizDebtSummary> queryByCompletedSolutions(Map<String, Object> params) {
		log.debug("开始查询所有已审核通过的债项...");
		//TODO 此处以后加入数据权限过滤，登录用户只能发放自己管理权限内的债项
		params.put("solutionState", BizContant.SOLUTION_STATE_06);
		Page<BizDebtSummary> result = query(params);
		return result;
	}

	@Override
	public List<GrantRuleVerifVo> getGrantRuleVo(Map<String, Object> params) {
		log.debug("开始查询发放约束规则...");
		return bizDebtSummaryMapper.getGrantRuleVo(params);
	}

	@Override
	public Page getDebtInfo(Map<String, Object> params) {
		Page page = getPage(params);
		page.setSize(StringUtil.objToInteger(params.get("countPage")));
		params.remove("countPage");
        List<BizDebtInfo> bizDebtSummaryList = bizTRNMapper.selectSummaryInfo(page,params);
		page.setRecords(bizDebtSummaryList);
		return page;
	}

	//保存债项方案（新增、修订、驳回时的数据恢复）
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveDebt(Map<String, Object> mapObj) {

		Date dat= new Date();

        BizTRN bizTRN = null;
        BizDebtSummary bizDebtSummary = null;
        Long bizDebtId = null;
        Long trnId = null;

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus status = txManager.getTransaction(def);

        try {

            //true=驳回
            if(null != mapObj.get("fromTempSave") && (boolean)mapObj.get("fromTempSave") == true){

                //当前流水
                Wrapper<BizTRN> wrapper = new EntityWrapper<>();
                wrapper.eq("RELFLG","Y").eq("OWNREF",(String)mapObj.get("ownref")).ne("RELRES","Y");
                bizTRN = bizTRNProvider.selectOne(wrapper);
                //历史审批通过流水
                Wrapper<BizTRN> oldResWrapper = new EntityWrapper<>();
                oldResWrapper.eq("RELRES","Y").eq("OWNREF",(String)mapObj.get("ownref")).ne("RELFLG","Y");
                BizTRN oldResTRN = bizTRNProvider.selectOne(oldResWrapper);

                trnId = bizTRN.getId();

                if(bizTRN == null || oldResTRN == null){
                    logger.debug("未查询到通过审批的历史方案，驳回时不会恢复数据。selRelresTrn+"+wrapper.getParamAlias());
                }else{

                    bizTRN.setInifrm("DEBTDOWN");
                    bizTRN.setIninam("修订驳回");
                    bizTRN.setBizStatus(BizStatus.DEBTDOWN);
                    bizTRN.setProcessStatus(BizStatus.DEPRDOWN);

                    Map<String, Object> oldParams = new HashMap<String, Object>();
                    oldParams.put("taskid",oldResTRN.getId());
                    oldParams.put("projectName","Trn_debtMain");
//                  oldParams.put("bizcode",resTrn.getOwnref());
                    Map<String, Object> selResTempSave = (Map<String, Object>)bizTemporarySaveProvider.getTemporary(oldParams);
                    if(null != selResTempSave){
                        //存库的数据从文件中获得
                        mapObj = selResTempSave;
                        bizDebtSummary=(BizDebtSummary)mapObj.get("bizDebtSummary");
                        bizDebtSummaryMapper.updateById(bizDebtSummary);
                        bizDebtId = bizDebtSummary.getId();
                        //撤销台账（根据cbe：where ==trninr,cbe: where cbeinr）
                        bizCBEProvider.delCbeCbbByTrninr(bizTRN.getId());
                    }else{
                        logger.error("(BizTempSave)No records queried from the database!params="+oldParams.toString());
                    }
                }

                bizTRNMapper.updateById(bizTRN);

            }else{

                bizDebtSummary=(BizDebtSummary)mapObj.get("bizDebtSummary");
                bizDebtId = bizDebtSummary.getId();
                trnId = IdWorker.getId();
                bizTRN=new BizTRN();
                bizTRN.setBchkeyinr(bizDebtSummary.getInstitutionCode());
                bizTRN.setIniusr(bizDebtSummary.getBankTellerId());
                bizTRN.setOwnref(bizDebtSummary.getDebtCode());
                bizTRN.setObjtyp("BIZ_DEBT_MAIN");
                bizTRN.setObjinr(bizDebtId);
                //执行日期-->标识为审批通过的时间
//                bizTRN.setExedat(dat);
                bizTRN.setInidattim(dat);
                bizTRN.setId(trnId);
                bizTRN.setCreateTime(dat);
                bizTRN.setUpdateTime(dat);
                bizTRN.setCreateBy(bizDebtSummary.getBankTellerId());
                bizTRN.setUpdateBy(bizDebtSummary.getBankTellerId());
                bizTRN.setEnable(1);
                bizTRN.setVerNum(bizDebtSummary.getVerNum());
                //补充概要信息
                //项目名称
                bizTRN.setObjnam(bizDebtSummary.getProjectName());
                //币种
                bizTRN.setReloricur(bizDebtSummary.getMpc());
                //金额
                bizTRN.setReloriamt(bizDebtSummary.getSolutionAmount());
                //为Y时代表最新的流水
                bizTRN.setRelflg("Y");
                //为Y时代表业务中最新审批通过的数据
                bizTRN.setRelres("N");
                if(bizDebtSummary.getVerNum() == 1){
                    // 发起补录流程
                    bizTRN.setInifrm("DEBOPN");
                    bizTRN.setIninam("方案补录");
                    bizTRN.setBizStatus(BizStatus.DEBTSUAT);
                    bizTRN.setProcessStatus(BizStatus.DEPRNEAT);
                    bizDebtSummaryMapper.insert(bizDebtSummary);
                }else {
                    Wrapper<BizTRN> wrapper = new EntityWrapper<BizTRN>();
                    wrapper.eq("RELRES","Y");
                    List<BizTRN> oldResLst = bizTRNMapper.selectList(wrapper);
                    if(null != oldResLst && oldResLst.size()>0){
                        //发起修订流程
                        bizTRN.setInifrm("DEBAME");
                        bizTRN.setIninam("方案修订");
                        bizTRN.setBizStatus(BizStatus.DEBTREAT);
                        bizTRN.setProcessStatus(BizStatus.DEPRNEAT);
                    }else{
                        // 发起补录流程
                        bizTRN.setInifrm("DEBOPN");
                        bizTRN.setIninam("方案补录");
                        bizTRN.setBizStatus(BizStatus.DEBTSUAT);
                        bizTRN.setProcessStatus(BizStatus.DEPRNEAT);
                    }
                    bizDebtSummaryMapper.updateById(bizDebtSummary);
                }
                //记两条台账，方案金额和可发放金额
                //判断 1.增额还是减额 2.余额是多少
                String solucbt = DEBT_SOLU_IN_CBTTXT;
                String debtcbt = DEBT_DEBT_IN_CBTTXT;
                BizCBB lastCbb = bizCBBMapper.selectOne(new BizCBB("BIZ_DEBT_MAIN",bizDebtId,BizContant.DEBT_SUMMARY_SOLU_CBCTXT,DateUtil.stringToDate("22991231 23:59:59")));
                if(null != lastCbb && lastCbb.getAmt().compareTo(bizDebtSummary.getSolutionAmount()) == 1){
                    solucbt = DEBT_SOLU_OUT_CBTTXT;
                    debtcbt = DEBT_DEBT_OUT_CBTTXT;
                }
                boolean bookres1 = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_DEBT_MAIN",bizDebtId,trnId,solucbt,BizContant.DEBT_SUMMARY_SOLU_CBCTXT,bizDebtSummary.getMpc(),StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()),null,null,dat,bizDebtSummary.getBankTellerId()));
                boolean bookres2 = bizCBEProvider.bookkeepking(new BookkeepkingVo("BIZ_DEBT_MAIN",bizDebtId,trnId,debtcbt,BizContant.DEBT_SUMMARY_DEBT_CBCTXT,bizDebtSummary.getMpc(),StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()),null,null,dat,bizDebtSummary.getBankTellerId()));
                logger.debug("记方案台账结果：bookres1=="+bookres1+" bookres2=="+bookres2);
                bizTRNProvider.updateTRNStatus(bizTRN);

                BizApprSummaryInfo bizApprSummaryInfo = new BizApprSummaryInfo();
                //项目名称
                bizApprSummaryInfo.setProjectName(bizDebtSummary.getProjectName());
                //申请人名称
                bizApprSummaryInfo.setProposer(bizDebtSummary.getProposer());
                //币种
                bizApprSummaryInfo.setMpc(bizDebtSummary.getMpc());
                //金额
                bizApprSummaryInfo.setSolutionAmount(bizDebtSummary.getSolutionAmount());
                //失效日期
                bizApprSummaryInfo.setPgExpiDate(bizDebtSummary.getPgExpiDate());
                //关联流水
                bizApprSummaryInfo.setTrnInr(trnId);
                bizApprSummaryInfo.setDebtCode(bizDebtSummary.getDebtCode());
                bizApprSummaryInfo.setVerNum(bizDebtSummary.getVerNum());

                bizApprSummaryInfo.setaCurrrency(bizDebtSummary.getaCurrrency());
                bizApprSummaryInfo.setApprove(bizDebtSummary.getApprove());
                bizApprSummaryInfo.setBankTellerId(bizDebtSummary.getBankTellerId());
                bizApprSummaryInfo.setBrifBackground(bizDebtSummary.getBrifBackground());
                bizApprSummaryInfo.setBusinessBackgroundBrief(bizDebtSummary.getBusinessBackgroundBrief());
                bizApprSummaryInfo.setDeptName(bizDebtSummary.getDeptName());
                bizApprSummaryInfo.setDescriptionProgramQuoqate(bizDebtSummary.getDescriptionProgramQuoqate());
                bizApprSummaryInfo.setDopo(bizDebtSummary.getDopo());
                bizApprSummaryInfo.setErrNo(bizDebtSummary.getErrNo());
                bizApprSummaryInfo.setGoodsSketch(bizDebtSummary.getGoodsSketch());
                bizApprSummaryInfo.setIdentNumber(bizDebtSummary.getIdentNumber());
                bizApprSummaryInfo.setLs(bizDebtSummary.getLs());
                bizApprSummaryInfo.setLtnopa(bizDebtSummary.getLtnopa());
                bizApprSummaryInfo.setOc(bizDebtSummary.getOc());
                bizApprSummaryInfo.setDeptName(bizDebtSummary.getDeptName());
                bizApprSummaryInfo.setInstitutionCode(bizDebtSummary.getInstitutionCode());
                bizApprSummaryInfo.setPgEffectivDate(bizDebtSummary.getPgEffectivDate());
                bizApprSummaryInfo.setRaaa(bizDebtSummary.getRaaa());
                bizApprSummaryInfo.setSingleBtch(bizDebtSummary.getSingleBtch());
                bizApprSummaryInfo.setCreateTime(bizDebtSummary.getCreateTime());
                bizApprSummaryInfo.setUpdateTime(bizDebtSummary.getUpdateTime());
                bizApprSummaryInfo.setCreateBy(bizDebtSummary.getCreateBy());
                bizApprSummaryInfo.setUpdateBy(bizDebtSummary.getUpdateBy());

                bizApprSummaryInfo.setTransok(bizDebtSummary.getTransok());
                bizApprSummaryInfo.setPolicy(bizDebtSummary.getPolicy());
                bizApprSummaryInfo.setPolicyDescription(bizDebtSummary.getPolicyDescription());
                bizApprSummaryInfo.setPackageRate(bizDebtSummary.getPackageRate());
                bizApprSummaryInfo.setRateRangeMix(bizDebtSummary.getRateRangeMix());
                bizApprSummaryInfo.setRateRangeMax(bizDebtSummary.getRateRangeMax());
                bizApprSummaryInfo.setDescriptionRateRules(bizDebtSummary.getDescriptionRateRules());
                bizApprSummaryInfoMapper.insert(bizApprSummaryInfo);
            }



            //获取客户角色信息
            List<BizPTS> ptsList = (List<BizPTS>)mapObj.get("ptsList");

            this.dealWithEntity(ptsList,null);

            Map<String,Object> debtCodeSelMap = new HashMap<>();
            debtCodeSelMap.put("debtCode", bizDebtSummary.getDebtCode());

            List<BizCreditLines> bizCreditLinesList = (List<BizCreditLines>)mapObj.get("bizCreditLinesList");

            this.dealWithEntity(bizCreditLinesList,debtCodeSelMap);

            //保存额度类型表
            List<BizProductLinesType> productLineList=(List<BizProductLinesType>)mapObj.get("productLinesTypesList");

            this.dealWithEntity(productLineList,debtCodeSelMap);


            //保存单一规则表
            List<BizSingleProductRule> bizSingleProductRuleList=(List<BizSingleProductRule>)mapObj.get("bizSingleProductRuleList");

            this.dealWithEntity(bizSingleProductRuleList,debtCodeSelMap);

            //保存担保信息
            List<BizGuaranteeInfo> bizGuaranteeInfoList = (List<BizGuaranteeInfo>)mapObj.get("bizGuaranteeInfoList");

            this.dealWithEntity(bizGuaranteeInfoList,debtCodeSelMap);

            //保存押品信息
            List<BizBetInformation> bizBetInformationList = (List<BizBetInformation>)mapObj.get("bizBetInformationList");

            this.dealWithEntity(bizBetInformationList,debtCodeSelMap);

            //保存租金保理表
            List<BizTheRentFactoring> bizTheRentFactorList=(List<BizTheRentFactoring>)mapObj.get("rentList");

            this.dealWithEntity(bizTheRentFactorList,debtCodeSelMap);


            String userIdNew = mapObj.get("userId").toString();
            log.info("userIdNew============="+userIdNew);
            String debtCodeNew=bizDebtSummary.getDebtCode() + bizDebtSummary.getVerNumStr();
            List<SysUserRole> list=(List)mapObj.get("list");
            // 发起补录流程
            Map<String, Object> param1 = new HashMap<String, Object>();
            for(SysUserRole sysUserRole:list){
                String roleId = sysUserRole.getRoleId().toString();
//                if("001".equals(debtCodeNew1)){
                if(null!=bizTRN && bizTRN.getBizStatus()==BizStatus.DEBTSUAT){
                    // 发起补录流程
                    param1.put("userId", userIdNew);
                    param1.put("mBizId", debtCodeNew);
                    if("451117558502785025".equals(roleId)){
                        param1.put("pdid", "blprocess");
                    }else if("451117558506979339".equals(roleId)){
                        param1.put("pdid", "zhblprocess");
                    }
                }else if(null!=bizTRN && bizTRN.getBizStatus()==BizStatus.DEBTREAT){
                    // 发起修订流程
                    param1.put("userId", userIdNew);
                    param1.put("mBizId", debtCodeNew);
                    if("451117558502785025".equals(roleId)){
                        param1.put("pdid", "xdprocess");
                    }else if("451117558506979339".equals(roleId)){
                        param1.put("pdid", "zhxdprocess");
                    }
                }else{
                    logger.info("其它状态不发起流程！bizTRN.getBizStatus()="+bizTRN.getBizStatus());
                }
            }
            if(null!=bizTRN && bizTRN.getBizStatus()==BizStatus.DEBTDOWN){
                txManager.commit(status);
                return true;
            }else{
			    param1.put("debtCode",bizDebtSummary.getDebtCode()+bizDebtSummary.getVerNumStr());
                bizProStatementProvider.createAndstartProcess(param1);
                Map<String, Object> tmpSaveParams = new HashMap<String, Object>();
                tmpSaveParams.put("taskid",trnId);
                tmpSaveParams.put("bizcode",bizDebtSummary.getDebtCode());
                tmpSaveParams.put("remark",bizDebtSummary.getVerNum());
                tmpSaveParams.put("projectName","Trn_debtMain");
                if(bizTemporarySaveProvider.saveTemporary(new BizTemporarySave(mapObj), tmpSaveParams)){
                    txManager.commit(status);
                    return true;
                }else{
                    return false;
                }
            }
        } catch (Exception es) {
            txManager.rollback(status);
            es.printStackTrace();
            throw new RuntimeException("save debtMain error! reason=="+es.getMessage());
        }
	}

	@Override
	public BizDebtSummary selectOneBizDebtSummary(BizDebtSummary bizDebtSummary) {
		return selectOne(bizDebtSummary);
	}

	@Override
	public Map getDebtInfoForStandingBook(Map<String, Object> params) {
		return bizDebtSummaryMapper.getDebtInfoForStandingBook(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void historyDebtSave(Map<String, Object> params) {
		Date date1=new Date();
		//得到债项方案的编号
		String debtCode=params.get("debtCode").toString();
		//保存债项信息表数据
		BizDebtSummary bizDebtSummary= JSON.parseObject(JSON.toJSONString(params.get("debtMain")),BizDebtSummary.class);
		bizDebtSummary.setHistoryState("2");
		bizDebtSummary.setCreateBy(Long.valueOf(params.get("userId").toString()));
		bizDebtSummary.setUpdateBy(Long.valueOf(params.get("userId").toString()));
		BizDebtSummary bizDebtSummary1=bizDebtSummaryProvider.update(bizDebtSummary);

		//先删除原来的客户信息
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("debtCode", debtCode);
		//删除原来的额度类型信息
		bizProductLinesTypeProvider.deleteByParams(customerMap);
		//删除原来方案的授信信息
		bizCreditLinesProvider.deleteByParams(customerMap);
		//删除原来的pts
		bizPTSProvider.deleteByParams(customerMap);


		//保存交易流水表 trn
		BizTRN bizTRN=new BizTRN();
		bizTRN.setBchkeyinr(bizDebtSummary1.getInstitutionCode());
		bizTRN.setInifrm("ADEBT");
		bizTRN.setIninam("历史数据迁移");
		bizTRN.setIniusr(bizDebtSummary1.getBankTellerId());
		bizTRN.setOwnref(debtCode);
		bizTRN.setObjtyp("BIZ_DEBT_MAIN");
		bizTRN.setObjinr(bizDebtSummary1.getId());
		bizTRN.setExedat(date1);
		bizTRN.setInidattim(date1);
		BizTRN bizTRN2=bizTRNProvider.update(bizTRN);

		//保存发生额信息表cbe 方案金额
		BizCBE bizCBE=new BizCBE();
		bizCBE.setObjType("BIZ_DEBT_MAIN");
		bizCBE.setObjInr(bizDebtSummary1.getId());
		bizCBE.setCbt("SOLUIN");
		bizCBE.setDat(date1);
		bizCBE.setCur(bizDebtSummary1.getMpc());
		bizCBE.setAmt(bizDebtSummary1.getSolutionAmount());
		bizCBE.setCredat(date1);
		//bizCBE.setXrfcur(bizDebtSummary1.getMpc());
		//bizCBE.setXrfamt(StringUtil.stringToBigDecimal(bizDebtSummary1.getSolutionAmount()));
		BizCBE bizCBE2=bizCBEProvider.update(bizCBE);

		//保存余额信息表cbb 方案金额
		BizCBB bizCBB=new BizCBB();
		bizCBB.setObjType("BIZ_DEBT_MAIN");
		bizCBB.setObjInr(bizDebtSummary1.getId());
		bizCBB.setCbc(BizContant.DEBT_SUMMARY_DEBT_CBCTXT);
		bizCBB.setAmt(bizDebtSummary1.getSolutionAmount());
		bizCBB.setBegdat(date1);
		bizCBB.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
		bizCBB.setCur(bizDebtSummary1.getMpc());
		bizCBB.setAmt(bizDebtSummary1.getSolutionAmount());
		//bizCBB.setXrfcur(bizDebtSummary1.getMpc());
		//bizCBB.setXrfamt(bizDebtSummary1.getSolutionAmount());
		bizCBB.setCbeInr(bizCBE2.getId());
		BizCBB bizCBB1=bizCBBProvider.update(bizCBB);

		List<BizGuaranteeInfo> bizGuaranteeInfoList=JSON.parseArray(JSON.toJSONString(params.get("bizGuaranteeInfoList")),BizGuaranteeInfo.class);
		for(BizGuaranteeInfo guarantee:bizGuaranteeInfoList){
			//保存担保信息表数据
			BizGuaranteeInfo bizGuaranteeInfo1=bizGuaranteeInfoProvider.update(guarantee);

			//保存担保金额cbe 方案
			BizCBE bizCBEGuar=new BizCBE();
			bizCBEGuar.setObjType("BIZ_GUARANTEE_INFO");


			bizCBEGuar.setObjInr(guarantee.getId());
			bizCBEGuar.setCbt("SOLUIN");
			bizCBEGuar.setDat(date1);
			bizCBEGuar.setCur(guarantee.getCurrencyGuarantee());
			bizCBEGuar.setAmt(guarantee.getGuaranteeAmount());
			bizCBEGuar.setCredat(date1);
			//bizCBEGuar.setXrfcur(guarantee.getCurrencyGuarantee());
			//bizCBEGuar.setXrfamt(guarantee.getGuaranteeAmount());
			BizCBE bizCBEGuar1=bizCBEProvider.update(bizCBEGuar);

			//保存担保金额cbb 方案
			BizCBB bizCBBGuar=new BizCBB();
			bizCBBGuar.setObjType("BIZ_GUARANTEE_INFO");
			bizCBBGuar.setObjInr(guarantee.getId());
			bizCBBGuar.setCbc(BizContant.DEBT_SUMMARY_DEBT_CBCTXT);
			bizCBBGuar.setAmt(guarantee.getGuaranteeAmount());
			bizCBBGuar.setBegdat(date1);
			bizCBBGuar.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
			bizCBBGuar.setCur(guarantee.getCurrencyGuarantee());
			bizCBBGuar.setAmt(guarantee.getGuaranteeAmount());
			//bizCBBGuar.setXrfcur(guarantee.getCurrencyGuarantee());
			//bizCBBGuar.setXrfamt(guarantee.getGuaranteeAmount());
			bizCBBGuar.setCbeInr(bizCBEGuar1.getId());
			BizCBB bizCBBGuar1=bizCBBProvider.update(bizCBBGuar);

			//判断担保人在customer表是否重复
            BizCust cult1 = new BizCust();
			cult1.setDebtCode(debtCode);
			cult1.setCustNo(guarantee.getGuarantorCustId().toString());
            BizCust cuuu=bizCustomerMapper.selectOne(cult1);
			if(cuuu==null){
                BizCust cust12=bizCustomerProvider.update(cult1);
				//保存担保人的pts
				BizPTS bizPTSguaran=new BizPTS();
				bizPTSguaran.setDebtCode(debtCode);
				bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
				bizPTSguaran.setObjinr(bizGuaranteeInfo1.getId().toString());
				bizPTSguaran.setRole("GUAR");
				bizPTSguaran.setPtyinr(cust12.getId().toString());
				bizPTSProvider.update(bizPTSguaran);
			}else {
				//保存担保人的pts
				BizPTS bizPTSguaran=new BizPTS();
				bizPTSguaran.setDebtCode(debtCode);
				bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
				bizPTSguaran.setObjinr(bizGuaranteeInfo1.getId().toString());
				bizPTSguaran.setRole("GUAR");
				bizPTSguaran.setPtyinr(cuuu.getId().toString());
				bizPTSProvider.update(bizPTSguaran);
			}

			//保存押品信息表
			List<BizBetInformation> betInformationList= guarantee.getBetInformationList();
			for(BizBetInformation bizBetInformation:betInformationList){
				//设置担保合同编号
				bizBetInformation.setGuarNo(guarantee.getWarrantyContractNumber());
				//设置债项方案编号
				bizBetInformation.setDebtCode(guarantee.getDebtCode());
				BizBetInformation bizBetInformation1=bizBetInformationProvider.update(bizBetInformation);
			}
		}


		List<BizProductTypes> rentFacList=JSON.parseArray(JSON.toJSONString(params.get("rentFacList")),BizProductTypes.class);
		for (BizProductTypes productTypes:rentFacList){
			//保存单一规则表
			BizSingleProductRule s=new BizSingleProductRule();
			//设置单一规则表的id
			s.setId(StringUtil.stringToLong(productTypes.getSingleId()));
			//设置单一规则表的产品规则
			s.setBusinessType(productTypes.getCode());
			s.setDebtCode(debtCode);
			//设置行业投向，背景国别
			s.setIndustryInvestment(productTypes.getIndustryTo());
			s.setBackgroundNationality(productTypes.getTbon());
			BizSingleProductRule bizSingleProductRule1=singleProductRuleProvider.update(s);

			//保存租金保理表数据
			BizTheRentFactoring rent=new BizTheRentFactoring();
			//设置容忍期，产品的code，承租人，是否是地方性融资平台客户名称，客户评级
			rent.setTolerancePertod(productTypes.getTolerancePertod());
			rent.setDebtCode(debtCode);
			rent.setBusinessTypes(productTypes.getCode());
			rent.setCustNo(productTypes.getCustNo());
			rent.setCustName(productTypes.getCustName());
			rent.setCustTating(productTypes.getCustTating());
			rent.setTolerancePertod(productTypes.getTolerancePertod());
			rent.setFinancePlatform(productTypes.getFinancePlatform());
			rent.setId(StringUtil.stringToLong(productTypes.getTheRentFactorId()));
			BizTheRentFactoring rent1=bizTheRentFactoringProvider.update(rent);

			List<BizCust> customerList=productTypes.getCustomersList();
			for(BizCust cus:customerList){
				//保存额度类型表数据
				BizProductLinesType lines=new BizProductLinesType();
				Long idProLine = IdWorker.getId();
				lines.setId(idProLine);
				lines.setDebtCode(debtCode);
				lines.setCustNo(cus.getCustNo());
				lines.setBusinessType(productTypes.getCode());
				lines.setCreditLinesId(cus.getCreditLinesId()+"");
				lines.setCreditRatio(cus.getCreditRatio());
				lines.setCreateTime(date1);
				lines.setUpdateTime(date1);
				lines.setCreateBy(bizDebtSummary.getBankTellerId());
				lines.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizProductLinesTypeMapper.insert(lines);

				//判断用信主体在customer表是否重复
                BizCust customer1=new BizCust();
				customer1.setDebtCode(debtCode);
				customer1.setCustNo(cus.getCustNo());
				customer1.setCustNameCN(cus.getCustNameCN());
                BizCust cuuu1=bizCustomerMapper.selectOne(customer1);
				if(cuuu1==null){
					//保存客户信息表
					cus.setDebtCode(debtCode);
                    BizCust bizCustomer1=bizCustomerProvider.update(cus);

					//保存PTS表 用信客户
					BizPTS bizPTS=new BizPTS();
					bizPTS.setDebtCode(debtCode);
					bizPTS.setObjtyp("BIZ_CUSTOMER");
					bizPTS.setObjinr(bizCustomer1.getId().toString());
					bizPTS.setRole("LETS");
					bizPTS.setPtyinr(bizCustomer1.getId().toString());
					bizPTSProvider.update(bizPTS);
				}else {
					//保存PTS表 用信客户
					BizPTS bizPTS=new BizPTS();
					bizPTS.setDebtCode(debtCode);
					bizPTS.setObjtyp("BIZ_CUSTOMER");
					bizPTS.setObjinr(cuuu1.getId().toString());
					bizPTS.setRole("LETS");
					bizPTS.setPtyinr(cuuu1.getId().toString());
					bizPTSProvider.update(bizPTS);
				}

				//保存授信额度信息表
				List<BizCreditLines>bizCreditLinesList=cus.getCreditLinesList();
				for(BizCreditLines bizCreditLines:bizCreditLinesList){
					Long idCre = IdWorker.getId();
					bizCreditLines.setId(idCre);
					bizCreditLines.setCreateTime(date1);
					bizCreditLines.setUpdateTime(date1);
					bizCreditLines.setCreateBy(bizDebtSummary.getBankTellerId());
					bizCreditLines.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizCreditLines.setDebtCode(debtCode);
					bizCreditLines.setCustNo(cus.getCustNo());
					bizCreditLines.setObjtyp("BIZ_DEBT_MAIN");
					bizCreditLines.setObjinr(bizDebtSummary1.getId().toString());
					bizCreditLinesMapper.insert(bizCreditLines);
				}
			}
		}
		redisHelper.deleteHistoryState("iBase4J:bizDebtSummary*");
	}

	@Override
    @Transactional(rollbackFor = Exception.class)
	public boolean getSchemeState(Map<String, Object> params) {

		List<BizDebtSummary> bizDebtList=queryList(params);
		BizDebtSummary bizDebt=bizDebtList.get(0);
        BizTRN selTrn = new BizTRN();
        selTrn.setObjtyp("BIZ_DEBT_MAIN");
        selTrn.setObjinr(bizDebt.getId());
        selTrn.setRelflg("Y");
        BizTRN debtTrn = bizTRNProvider.selectOne(new EntityWrapper<>(selTrn));

        if("TT".equals(params.get("commite").toString()) || "XDTT".equals(params.get("commite").toString())){
            //可发放
            debtTrn.setBizStatus(BizStatus.DEBTAVAI);
            //已审批
            debtTrn.setProcessStatus(BizStatus.DEPRAPPR);
            //标记为最新的审批通过状态
            debtTrn.setRelres("Y");
            bizTRNProvider.updateTRNStatus(debtTrn);
            return true;
        }
        //补录驳回
        if("BLBH".equals(params.get("commite").toString())){
            debtTrn.setInifrm("DEBTDOWN");
            debtTrn.setIninam("补录驳回");
            debtTrn.setBizStatus(BizStatus.DEBTDOWN);
            debtTrn.setProcessStatus(BizStatus.DEPRDOWN);
            if(bizTRNMapper.updateById(debtTrn) == 1){
                return true;
            }else{
                return false;
            }
        }
        if("XDBH".equals(params.get("commite").toString())){
            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("fromTempSave",true);
            mapObj.put("ownref",debtTrn.getOwnref());
            if(!this.saveDebt(mapObj)){
                logger.error("驳回时回滚数据异常...");
                throw new RuntimeException("驳回时回滚数据异常...");
            }else{
                return true;
            }
        }
        logger.error("getSchemeState error, params==" + params.toString());
        return false;
	}

	/**
	 * 驳回的重新提交
	 * **/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void ReSaveDebt(Map<String, Object> mapObj) {
		Date dat = new Date();

		//保存债项信息表
		BizDebtSummary bizDebtSummary = (BizDebtSummary) mapObj.get("bizDebtSummary");
		//全局规则
		bizDebtSummary.setRuleType((long) 0);
		BizDebtSummary bizDebtSummary1 = bizDebtSummaryProvider.update(bizDebtSummary);

		//先删除原来的客户信息
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("debtCode", bizDebtSummary.getDebtCode());
		bizCustomerProvider.deleteByParams(customerMap);
		//删除原来的额度类型信息
		bizProductLinesTypeProvider.deleteByParams(customerMap);
		//删除原来方案的授信信息
		bizCreditLinesProvider.deleteByParams(customerMap);
		//删除原来的pts
		bizPTSProvider.deleteByParams(customerMap);

		//保存客户信息
		Set<BizCust> bizCustomerList = (Set<BizCust>) mapObj.get("cuSet");
		for (BizCust cu : bizCustomerList) {
			Long idCust = IdWorker.getId();
			cu.setDebtCode(bizDebtSummary1.getDebtCode());
			cu.setId(idCust);
			cu.setCreateTime(dat);
			cu.setUpdateTime(dat);
			cu.setCreateBy(bizDebtSummary1.getBankTellerId());
			cu.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCustomerMapper.insert(cu);

			//保存PTS表 用信客户
			BizPTS bizPTS = new BizPTS();
			bizPTS.setDebtCode(bizDebtSummary1.getDebtCode());
			bizPTS.setObjtyp("BIZ_CUSTOMER");
			bizPTS.setObjinr(idCust.toString());
			bizPTS.setRole("LETS");
			bizPTS.setPtyinr(idCust.toString());
			bizPTS.setCreateTime(dat);
			bizPTS.setUpdateTime(dat);
			bizPTS.setCreateBy(bizDebtSummary1.getBankTellerId());
			bizPTS.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizPTSMapper.insert(bizPTS);

			//保存用户主体授信信息表
			List<BizCreditLines> bizProductLinesTypeList = cu.getCreditLinesList();
			for (BizCreditLines li : bizProductLinesTypeList) {
				li.setDebtCode(bizDebtSummary1.getDebtCode());
				li.setCustomerId(idCust);
				li.setCustNo(cu.getCustNo());
				li.setObjtyp("BIZ_DEBT_MAIN");
				li.setObjinr(bizDebtSummary1.getId().toString());
				Long idCre = IdWorker.getId();
				li.setId(idCre);
				li.setCreateTime(dat);
				li.setUpdateTime(dat);
				li.setCreateBy(bizDebtSummary1.getBankTellerId());
				li.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizCreditLinesMapper.insert(li);
			}
		}

		//保存额度类型表
		List<BizProductLinesType> productLineList = (List<BizProductLinesType>) mapObj.get("productLinesTypesList");
		for (BizProductLinesType proLine : productLineList) {
			Long idProLine = IdWorker.getId();
			proLine.setId(idProLine);
			proLine.setCreateTime(dat);
			proLine.setUpdateTime(dat);
			proLine.setCreateBy(bizDebtSummary.getBankTellerId());
			proLine.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizProductLinesTypeMapper.insert(proLine);
		}

		//保存交易流水表 trn
		BizTRN bizTRN = new BizTRN();
		bizTRN.setBchkeyinr(bizDebtSummary1.getInstitutionCode());
		bizTRN.setInifrm("ADEBT");
		bizTRN.setIninam("方案补录");
		bizTRN.setIniusr(bizDebtSummary1.getBankTellerId());
		bizTRN.setOwnref(bizDebtSummary1.getDebtCode());
		bizTRN.setObjtyp("BIZ_DEBT_MAIN");
		bizTRN.setObjinr(bizDebtSummary1.getId());
		bizTRN.setExedat(dat);
		bizTRN.setInidattim(dat);
		Long id = IdWorker.getId();
		bizTRN.setId(id);
		bizTRN.setCreateTime(dat);
		bizTRN.setUpdateTime(dat);
		bizTRN.setCreateBy(bizDebtSummary1.getBankTellerId());
		bizTRN.setUpdateBy(bizDebtSummary1.getBankTellerId());
		bizTRNMapper.insert(bizTRN);

		//保存发生额信息表cbe 方案金额
		BizCBE bizCBE = new BizCBE();
		bizCBE.setObjType("BIZ_DEBT_MAIN");
		bizCBE.setCbt("SOLUIN");
		bizCBE.setObjInr(bizDebtSummary1.getId());
		BizCBE bizCBE1 = bizCBEProvider.selectOne(bizCBE);
		bizCBE1.setCur(bizDebtSummary1.getMpc());
		bizCBE1.setAmt(bizDebtSummary1.getSolutionAmount());
		bizCBE1.setDat(dat);
		//bizCBE1.setXrfcur(bizDebtSummary1.getMpc());
		//bizCBE1.setXrfamt(bizDebtSummary1.getSolutionAmount());
		BizCBE bizCBE2 = bizCBEProvider.update(bizCBE1);

		//保存余额信息表cbb 方案金额
		BizCBB bizCBB = new BizCBB();
		bizCBB.setObjType("BIZ_DEBT_MAIN");
		bizCBB.setObjInr(bizDebtSummary1.getId());
		bizCBB.setCbc(BizContant.DEBT_SUMMARY_DEBT_CBCTXT);
		bizCBB.setCbeInr(bizCBE2.getId());
		BizCBB bizCBB1 = bizCBBProvider.selectOne(bizCBB);
		bizCBB1.setAmt(StringUtil.stringToBigDecimal(bizDebtSummary1.getSolutionAmount().toString()));
		bizCBB1.setBegdat(dat);
		bizCBB1.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
		bizCBB1.setCur(bizDebtSummary1.getMpc());
		bizCBB1.setAmt(StringUtil.stringToBigDecimal(bizDebtSummary1.getSolutionAmount()));
		//bizCBB1.setXrfcur(bizDebtSummary1.getMpc());
		//bizCBB1.setXrfamt(StringUtil.stringToBigDecimal(bizDebtSummary1.getSolutionAmount()));
		BizCBB bizCBB2 = bizCBBProvider.update(bizCBB1);


		//保存单一规则表
		List<BizSingleProductRule> bizSingleProductRuleList = (List<BizSingleProductRule>) mapObj.get("bizSingleProductRuleList");
		for (BizSingleProductRule sing : bizSingleProductRuleList) {
			bizSingleProductRuleProvider.update(sing);
		}

		int a = 0;
		//保存PTS表 申请人
		for (BizCust cucu : bizCustomerList) {
			if (cucu.getCustNo().equals(bizDebtSummary1.getProposerNum())) {
				a++;
			}
		}
		if (a == 0) {
			//说明申请人没有插入数据库
			BizCust custt = new BizCust();
			custt.setCustNo(bizDebtSummary1.getProposerNum());
			BizCust cuuu = bizCustMapper.selectOne(custt);
            BizCust cult = new BizCust();
			BeanUtils.copyProperties(cuuu, cult);
			Long idCust = IdWorker.getId();
			cult.setDebtCode(bizDebtSummary1.getDebtCode());
			cult.setId(idCust);
			cult.setCreateTime(dat);
			cult.setUpdateTime(dat);
			cult.setCreateBy(bizDebtSummary1.getBankTellerId());
			cult.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCustomerMapper.insert(cult);

			BizPTS bizPTSPro = new BizPTS();
			bizPTSPro.setDebtCode(bizDebtSummary1.getDebtCode());
			bizPTSPro.setObjtyp("BIZ_DEBT_MAIN");
			bizPTSPro.setObjinr(bizDebtSummary1.getId().toString());
			bizPTSPro.setRole("APPT");
			bizPTSPro.setPtyinr(idCust.toString());
			bizPTSPro.setCreateTime(dat);
			bizPTSPro.setUpdateTime(dat);
			bizPTSPro.setCreateBy(bizDebtSummary1.getBankTellerId());
			bizPTSPro.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizPTSMapper.insert(bizPTSPro);
		} else {
			BizPTS bizPTSPro = new BizPTS();
			bizPTSPro.setDebtCode(bizDebtSummary1.getDebtCode());
			bizPTSPro.setObjtyp("BIZ_DEBT_MAIN");
			bizPTSPro.setObjinr(bizDebtSummary1.getId().toString());
			bizPTSPro.setRole("APPT");
			bizPTSPro.setPtyinr(bizDebtSummary1.getProposerNum());
			bizPTSPro.setCreateTime(dat);
			bizPTSPro.setUpdateTime(dat);
			bizPTSPro.setCreateBy(bizDebtSummary1.getBankTellerId());
			bizPTSPro.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizPTSMapper.insert(bizPTSPro);
		}

		//保存担保信息
		List<BizGuaranteeInfo> bizGuaranteeInfoList = (List<BizGuaranteeInfo>) mapObj.get("bizGuaranteeInfoList");
		for (BizGuaranteeInfo gu : bizGuaranteeInfoList) {
			BizGuaranteeInfo bizGuaranteeInfo1 = bizGuaranteeInfoProvider.update(gu);

			//保存押品信息表
			if (gu.getBetInformationList() != null) {
				List<BizBetInformation> betList = gu.getBetInformationList();
				for (BizBetInformation bet : betList) {
					//担保合同编号
					bet.setGuarNo(gu.getWarrantyContractNumber());
					bet.setDebtCode(bizDebtSummary.getDebtCode());
					BizBetInformation bizBetInformation1 = bizBetInformationProvider.update(bet);
				}
			}

			//保存担保金额cbe 方案
			BizCBE bizCBE3 = new BizCBE();
			Long idCb = IdWorker.getId();
			bizCBE3.setId(idCb);
			bizCBE3.setObjType("BIZ_GUARANTEE_INFO");
			bizCBE3.setObjInr(bizGuaranteeInfo1.getId());
			bizCBE3.setCbt("SOLUIN");
			bizCBE3.setDat(dat);
			bizCBE3.setCur(gu.getCurrencyGuarantee());
			bizCBE3.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
			bizCBE3.setCredat(dat);
			//bizCBE3.setXrfcur(gu.getCurrencyGuarantee());
			//bizCBE3.setXrfamt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
			bizCBE3.setCreateTime(dat);
			bizCBE3.setUpdateTime(dat);
			bizCBE3.setCredat(dat);
			bizCBE3.setCreateBy(bizDebtSummary1.getBankTellerId());
			bizCBE3.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCBEMapper.insert(bizCBE3);

			//保存担保金额cbb 方案
			BizCBB bizCBB4 = new BizCBB();
			Long idCbb2 = IdWorker.getId();
			bizCBB4.setId(idCbb2);
			bizCBB4.setObjType("BIZ_GUARANTEE_INFO");
			bizCBB4.setObjInr(idCb);
			bizCBB4.setCbc(BizContant.DEBT_SUMMARY_DEBT_CBCTXT);
			bizCBB4.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
			bizCBB4.setBegdat(dat);
			bizCBB4.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
			bizCBB4.setCur(gu.getCurrencyGuarantee());
			bizCBB4.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
			//bizCBB4.setXrfcur(gu.getCurrencyGuarantee());
			//bizCBB4.setXrfamt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
			bizCBB4.setCbeInr(idCb);
			bizCBB4.setCreateTime(dat);
			bizCBB4.setUpdateTime(dat);
			bizCBB4.setCreateBy(bizDebtSummary1.getBankTellerId());
			bizCBB4.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCBBMapper.insert(bizCBB4);

			//PTS表，担保人
			int b = 0;
			for (BizCust cucu : bizCustomerList) {
				if (cucu.getCustNo().equals(gu.getGuarantorCustId().toString())) {
					b++;
				}
			}
			if (b == 0) {
				//说明担保人没有插入数据库
				BizCust custt = new BizCust();
				custt.setCustNo(gu.getGuarantorCustId().toString());
				BizCust cuuu = bizCustMapper.selectOne(custt);
				if (cuuu != null) {
                    BizCust cult1 = new BizCust();
					BeanUtils.copyProperties(cuuu, cult1);
					Long idCust1 = IdWorker.getId();
					cult1.setDebtCode(bizDebtSummary1.getDebtCode());
					cult1.setId(idCust1);
					cult1.setCreateTime(dat);
					cult1.setUpdateTime(dat);
					cult1.setCreateBy(bizDebtSummary1.getBankTellerId());
					cult1.setUpdateBy(bizDebtSummary1.getBankTellerId());
					bizCustomerMapper.insert(cult1);

					BizPTS bizPTSguaran = new BizPTS();
					bizPTSguaran.setDebtCode(bizDebtSummary1.getDebtCode());
					bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
					bizPTSguaran.setObjinr(bizDebtSummary1.getId().toString());
					bizPTSguaran.setRole("GUAR");
					bizPTSguaran.setPtyinr(idCust1.toString());
					bizPTSguaran.setCreateTime(dat);
					bizPTSguaran.setUpdateTime(dat);
					bizPTSguaran.setCreateBy(bizDebtSummary1.getBankTellerId());
					bizPTSguaran.setUpdateBy(bizDebtSummary1.getBankTellerId());
					bizPTSMapper.insert(bizPTSguaran);
				}
			} else {
				BizPTS bizPTSguaran = new BizPTS();
				bizPTSguaran.setDebtCode(bizDebtSummary1.getDebtCode());
				bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
				bizPTSguaran.setObjinr(bizDebtSummary1.getId().toString());
				bizPTSguaran.setRole("GUAR");
				bizPTSguaran.setPtyinr(gu.getGuarantorCustId().toString());
				bizPTSguaran.setCreateTime(dat);
				bizPTSguaran.setUpdateTime(dat);
				bizPTSguaran.setCreateBy(bizDebtSummary1.getBankTellerId());
				bizPTSguaran.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizPTSMapper.insert(bizPTSguaran);
			}
		}

		//保存租金保理表
		List<BizTheRentFactoring> bizTheRentFactorList = (List<BizTheRentFactoring>) mapObj.get("rentList");
		for (BizTheRentFactoring re : bizTheRentFactorList) {
			BizTheRentFactoring bizTheRentFactoring = bizTheRentFactoringProvider.update(re);

			//PTS表，担保人
			int c = 0;
			for (BizCust cucu : bizCustomerList) {
				if (cucu.getCustNo().equals(re.getCustNo())) {
					c++;
				}
			}
			if (c == 0) {
				//说明承销人没有插入数据库
				BizCust cust3 = new BizCust();
				cust3.setCustNo(re.getCustNo());
				BizCust cuuu = bizCustMapper.selectOne(cust3);
                BizCust cult3 = new BizCust();
				BeanUtils.copyProperties(cuuu, cult3);
				Long idCust3 = IdWorker.getId();
				cult3.setDebtCode(bizDebtSummary1.getDebtCode());
				cult3.setId(idCust3);
				cult3.setCreateTime(dat);
				cult3.setUpdateTime(dat);
				cult3.setCreateBy(bizDebtSummary1.getBankTellerId());
				cult3.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizCustomerMapper.insert(cult3);

				//保存PTS表 承销人
				BizPTS bizPTSRen3 = new BizPTS();
				bizPTSRen3.setDebtCode(bizDebtSummary1.getDebtCode());
				bizPTSRen3.setObjtyp("BIZ_RENTAL_FACTORING_KEY");
				bizPTSRen3.setObjinr(bizTheRentFactoring.getId().toString());
				bizPTSRen3.setRole("CONE");
				bizPTSRen3.setPtyinr(idCust3.toString());
				bizPTSRen3.setCreateTime(dat);
				bizPTSRen3.setUpdateTime(dat);
				bizPTSRen3.setCreateBy(bizDebtSummary1.getBankTellerId());
				bizPTSRen3.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizPTSMapper.insert(bizPTSRen3);
			} else {
				//保存PTS表 承销人
				BizPTS bizPTSRen3 = new BizPTS();
				bizPTSRen3.setDebtCode(bizDebtSummary1.getDebtCode());
				bizPTSRen3.setObjtyp("BIZ_RENTAL_FACTORING_KEY");
				bizPTSRen3.setObjinr(bizTheRentFactoring.getId().toString());
				bizPTSRen3.setRole("CONE");
				bizPTSRen3.setPtyinr(re.getCustNo());
				bizPTSRen3.setCreateTime(dat);
				bizPTSRen3.setUpdateTime(dat);
				bizPTSRen3.setCreateBy(bizDebtSummary1.getBankTellerId());
				bizPTSRen3.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizPTSMapper.insert(bizPTSRen3);
			}

		}

		String userIdNew = mapObj.get("userId").toString();
		log.info("userIdNew============="+userIdNew);
		String debtCodeNew=bizDebtSummary.getDebtCode();
		String debtCodeNew1=debtCodeNew.substring(13);
		List<SysUserRole> list=(List)mapObj.get("list");
		// 发起补录流程
		Map<String, Object> param1 = new HashMap<String, Object>();
		for(SysUserRole sysUserRole:list){
			String roleId = sysUserRole.getRoleId().toString();
			if("001".equals(debtCodeNew1)){
				// 发起补录流程
				param1.put("userId", userIdNew);
				param1.put("mBizId", debtCodeNew);
				if("451117558502785025".equals(roleId)){
					param1.put("pdid", "blprocess");
				}else if("451117558506979339".equals(roleId)){
					param1.put("pdid", "zhblprocess");
				}
			}else {
				// 发起修订流程
				param1.put("userId", userIdNew);
				param1.put("mBizId", debtCodeNew);
				if("451117558502785025".equals(roleId)){
					param1.put("pdid", "xdprocess");
				}else if("451117558506979339".equals(roleId)){
					param1.put("pdid", "zhxdprocess");
				}
			}
		}
		param1.put("debtCode",bizDebtSummary.getDebtCode()+bizDebtSummary.getVerNumStr());
		bizProStatementProvider.createAndstartProcess(param1);
		}

    @Override
    public Long selectDebtIdByDebtCode(String debtCode) {
        return bizDebtSummaryMapper.selectDebtIdByDebtCode(debtCode);
    }


	@Override
	public void refreshDebtExpired() {
		Date date=new Date();
		Map<String,Object>debtMap =new HashMap<>();
		List<BizDebtSummary>bizDebtSummaryList=queryList(debtMap);
		System.out.println("定时任务刷新方案过期时间执行："+date);
		for (BizDebtSummary debtSummary:bizDebtSummaryList){
			if(debtSummary.getPgExpiDate().before(date)){
			    //sinosong 待测
//				debtSummary.setSolutionState(BizStatus.DEBTREGULAR);
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("objinr", debtSummary.getId());
                params.put("objtyp", "BIZ_DEBT_MAIN");
                params.put("ownref", debtSummary.getDebtCode());
                params.put("relres", "relres");
                List<BizTRN> trnList = bizTRNProvider.queryList(params);
                if(null!=trnList && trnList.size()==1){
                    BizTRN trn = trnList.get(0);
                    trn.setBizStatus(BizStatus.DEBTREGULAR);
                    logger.info("检测方案已过期，后台更新状态。方案编号为=（"+debtSummary.getDebtCode()+"）");
                    bizTRNProvider.update(trn);
                }else{
                    logger.error("方案已失效,未查询到已审批通过的方案流水或查询出多条，trnList="+trnList);
                }
//				update(debtSummary);
		}
			System.out.println("定时任务刷新方案过期时间执行完成："+date);
	    }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectAndDel(BizDebtSummary bizDebt){

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            BizTRN selTrn = new BizTRN();
            selTrn.setObjtyp("BIZ_DEBT_MAIN");
            selTrn.setOwnref(bizDebt.getDebtCode());
            selTrn.setRelflg("Y");
            BizTRN willDel = bizTRNProvider.selectOne(new EntityWrapper<>(selTrn));
            if(null == willDel){
                logger.error("数据异常：未查到之前的流水记录！");
                throw new RuntimeException("Exception=未查到之前的流水记录！");
            }
            willDel.setBizStatus(BizStatus.DEBTDELE);
            willDel.setInifrm("DBEDEL");
            willDel.setIninam("方案删除");
            willDel.setRelflg("N");
            bizTRNMapper.updateById(willDel);
            selTrn.setRelflg("N");
            selTrn.setRelres("Y");
            BizTRN updatTrn = bizTRNProvider.selectOne(new EntityWrapper<>(selTrn));
            if(null!=updatTrn){
                //修订才会改历史流水
                updatTrn.setRelflg("Y");
                bizTRNMapper.updateById(updatTrn);
            }
        } catch (Exception es) {
            txManager.rollback(status);
            es.printStackTrace();
            throw new RuntimeException("rejectAndDel error! reason=="+es.getMessage());
        }
    }



}
