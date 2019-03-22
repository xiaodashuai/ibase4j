package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.*;
import org.ibase4j.model.*;
import org.ibase4j.vo.BizDebtInfo;
import org.ibase4j.vo.GrantRuleVerifVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.SimpleDateFormat;
import java.util.*;

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
	private BizCustomerMapper bizCustomerMapper;
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
	private BizCustomerProvider bizCustomerProvider;
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

	@Override
	public Page<BizDebtSummary> queryByCompletedSolutions(Map<String, Object> params) {
		log.info("开始查询所有已审核通过的债项...");
		//TODO 此处以后加入数据权限过滤，登录用户只能发放自己管理权限内的债项
		params.put("inSolutionState", BizContant.SOLUTION_STATE_06);
		Page<BizDebtSummary> result = query(params);
		return result;
	}

	@Override
	public List<GrantRuleVerifVo> getGrantRuleVo(Map<String, Object> params) {
		log.info("开始查询发放约束规则...");
		return bizDebtSummaryMapper.getGrantRuleVo(params);
	}

	@Override
	public Page getDebtInfo(Map<String, Object> params) {
		Page page = getPage(params);
		page.setSize(StringUtil.objToInteger(params.get("countPage")));
		params.remove("countPage");
		List<BizDebtInfo> bizDebtSummaryList = bizDebtSummaryMapper.getDebtInfo(page,params);
		page.setRecords(bizDebtSummaryList);
		return page;
	}

	//保存债项方案
	@Override

	public boolean saveDebt(Map<String, Object> mapObj) {
		log.info("====================保存方案的方法开始执行==========================");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

		TransactionStatus status = txManager.getTransaction(def);
		try {
			Date dat = new Date();

			//保存债项信息表
			BizDebtSummary bizDebtSummary = (BizDebtSummary) mapObj.get("bizDebtSummary");
			log.info("=========保存债项信息表（BIZ_DEBT_MAIN）开始执行-方案编号："+bizDebtSummary+"==========================");
			Long idDebtSummary = IdWorker.getId();
			bizDebtSummary.setId(idDebtSummary);
			//全局规则
			bizDebtSummary.setRuleType((long) 1);
			//流程发起时间
			bizDebtSummary.setProcessInitiatTime(dat);
			bizDebtSummary.setCreateTime(dat);
			bizDebtSummary.setUpdateTime(dat);
			bizDebtSummary.setCreateBy(bizDebtSummary.getBankTellerId());
			bizDebtSummary.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizDebtSummary.setEnable(1);
			bizDebtSummaryMapper.insert(bizDebtSummary);
			log.info("============保存债项信息表（BIZ_DEBT_MAIN）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存客户信息
			log.info("==========保存用信主体的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
			Set<BizCustomer> bizCustomerList = (Set<BizCustomer>) mapObj.get("cuSet");
			for (BizCustomer cu : bizCustomerList) {
				Long idCust = IdWorker.getId();
				cu.setDebtCode(bizDebtSummary.getDebtCode());
				cu.setId(idCust);
				cu.setCreateTime(dat);
				cu.setUpdateTime(dat);
				cu.setCreateBy(bizDebtSummary.getBankTellerId());
				cu.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizCustomerMapper.insert(cu);
				log.info("===========保存用信主体的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

				//保存PTS表 用信客户
				log.info("====================保存用信主体的PTS表（BIZ_TRN）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizPTS bizPTS = new BizPTS();
				bizPTS.setDebtCode(bizDebtSummary.getDebtCode());
				bizPTS.setObjtyp("BIZ_CUSTOMER");
				bizPTS.setObjinr(idCust.toString());
				bizPTS.setRole("LETS");
				bizPTS.setPtyinr(idCust.toString());
				bizPTS.setCreateTime(dat);
				bizPTS.setUpdateTime(dat);
				bizPTS.setCreateBy(bizDebtSummary.getBankTellerId());
				bizPTS.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizPTSMapper.insert(bizPTS);
				log.info("====================保存用信主体的PTS表（BIZ_TRN）执行成功-方案编号："+bizDebtSummary+"==========================");

				//保存用户主体授信信息表
				log.info("=================保存用户主体授信信息表（BIZ_CREDIT_LINES）开始执行-方案编号："+bizDebtSummary+"==========================");
				List<BizCreditLines> bizProductLinesTypeList = cu.getCreditLinesList();
				for (BizCreditLines li : bizProductLinesTypeList) {
					li.setDebtCode(bizDebtSummary.getDebtCode());
					li.setCustomerId(idCust);
					li.setCustNo(cu.getCustNo());
					li.setObjtyp("BIZ_DEBT_MAIN");
					li.setObjinr(idDebtSummary.toString());
					Long idCre = IdWorker.getId();
					li.setId(idCre);
					li.setCreateTime(dat);
					li.setUpdateTime(dat);
					li.setCreateBy(bizDebtSummary.getBankTellerId());
					li.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizCreditLinesMapper.insert(li);
					log.info("====================保存用户主体授信信息表（BIZ_CREDIT_LINES）执行成功-方案编号："+bizDebtSummary+"==========================");
				}
			}

			//保存额度类型表
			log.info("====================保存保存额度类型表（BIZ_PRODUCT_LINESTYPE）开始执行-方案编号："+bizDebtSummary+"==========================");
			List<BizProductLinesType> productLineList = (List<BizProductLinesType>) mapObj.get("productLinesTypesList");
			for (BizProductLinesType proLine : productLineList) {
				Long idProLine = IdWorker.getId();
				proLine.setId(idProLine);
				proLine.setCreateTime(dat);
				proLine.setUpdateTime(dat);
				proLine.setCreateBy(bizDebtSummary.getBankTellerId());
				proLine.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizProductLinesTypeMapper.insert(proLine);
				log.info("====================保存保存额度类型表（BIZ_PRODUCT_LINESTYPE）执行成功-方案编号："+bizDebtSummary+"==========================");
			}

			//保存交易流水表 trn
			log.info("====================保存交易流水表（BIZ_TRN）开始执行-方案编号："+bizDebtSummary+"==========================");
			BizTRN bizTRN = new BizTRN();
			bizTRN.setBchkeyinr(bizDebtSummary.getInstitutionCode());
			bizTRN.setInifrm("ADEBT");
			bizTRN.setIninam("方案补录");
			bizTRN.setIniusr(bizDebtSummary.getBankTellerId());
			bizTRN.setOwnref(bizDebtSummary.getDebtCode());
			bizTRN.setObjtyp("BIZ_DEBT_MAIN");
			bizTRN.setObjinr(idDebtSummary);
			bizTRN.setExedat(dat);
			bizTRN.setInidattim(dat);
			Long id = IdWorker.getId();
			bizTRN.setId(id);
			bizTRN.setCreateTime(dat);
			bizTRN.setUpdateTime(dat);
			bizTRN.setCreateBy(bizDebtSummary.getBankTellerId());
			bizTRN.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizTRNMapper.insert(bizTRN);
			log.info("====================保存交易流水表（BIZ_TRN）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存发生额信息表cbe 方案金额
			log.info("====================保存方案金额的发生额信息表cbe（BIZ_CBE）开始执行-方案编号："+bizDebtSummary+"==========================");
			BizCBE bizCBE = new BizCBE();
			Long idCbe = IdWorker.getId();
			bizCBE.setId(idCbe);
			bizCBE.setObjType("BIZ_DEBT_MAIN");
			bizCBE.setObjInr(idDebtSummary);
			bizCBE.setCbt("SOLUIN");
			bizCBE.setDat(dat);
			bizCBE.setCur(bizDebtSummary.getMpc());
			bizCBE.setAmt(StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()));
			bizCBE.setCredat(dat);
			//bizCBE.setXrfcur(bizDebtSummary.getMpc());
			//bizCBE.setXrfamt(StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()));
			bizCBE.setCreateTime(dat);
			bizCBE.setUpdateTime(dat);
			bizCBE.setCredat(dat);
			bizCBE.setCreateBy(bizDebtSummary.getBankTellerId());
			bizCBE.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizCBEMapper.insert(bizCBE);
			log.info("====================保存方案金额的发生额信息表cbe（BIZ_CBE）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存余额信息表cbb 方案金额
			log.info("====================保存方案金额的余额信息表cbb（BIZ_CBB）开始执行-方案编号："+bizDebtSummary+"==========================");
			BizCBB bizCBB = new BizCBB();
			Long idCbb = IdWorker.getId();
			bizCBB.setId(idCbb);
			bizCBB.setObjType("BIZ_DEBT_MAIN");
			bizCBB.setObjInr(idDebtSummary);
			bizCBB.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
			bizCBB.setAmt(StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount().toString()));
			bizCBB.setBegdat(dat);
			bizCBB.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
			bizCBB.setCur(bizDebtSummary.getMpc());
			bizCBB.setAmt(StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()));
			//bizCBB.setXrfcur(bizDebtSummary.getMpc());
			//bizCBB.setXrfamt(StringUtil.stringToBigDecimal(bizDebtSummary.getSolutionAmount()));
			bizCBB.setCbeInr(idCbe);
			bizCBB.setCreateTime(dat);
			bizCBB.setUpdateTime(dat);
			bizCBB.setCreateBy(bizDebtSummary.getBankTellerId());
			bizCBB.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizCBBMapper.insert(bizCBB);
			log.info("====================保存方案金额的余额信息表cbb（BIZ_CBB）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存单一规则表
			log.info("====================保存单一规则表（BIZ_SIGLE_PRODUCT_RULE）开始执行-方案编号："+bizDebtSummary+"==========================");
			List<BizSingleProductRule> bizSingleProductRuleList = (List<BizSingleProductRule>) mapObj.get("bizSingleProductRuleList");
			for (BizSingleProductRule sing : bizSingleProductRuleList) {
				Long idSing = IdWorker.getId();
				sing.setId(idSing);
				sing.setCreateTime(dat);
				sing.setUpdateTime(dat);
				sing.setCreateBy(bizDebtSummary.getBankTellerId());
				sing.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizSingleProductRuleMapper.insert(sing);
				log.info("====================保存单一规则表（BIZ_SIGLE_PRODUCT_RULE）执行成功-方案编号："+bizDebtSummary+"==========================");
			}

			int a = 0;
			//保存PTS表 申请人
			for (BizCustomer cucu : bizCustomerList) {
				if (cucu.getCustNo().equals(bizDebtSummary.getProposerNum())) {
					a++;
				}
			}
			if (a == 0) {
				//说明申请人没有插入数据库
				log.info("====================保存申请人的客户信息（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizCust custt = new BizCust();
				custt.setCustNo(bizDebtSummary.getProposerNum());
				BizCust cuuu = bizCustMapper.selectOne(custt);
				BizCustomer cult = new BizCustomer();
				BeanUtils.copyProperties(cuuu, cult);
				Long idCust = IdWorker.getId();
				cult.setDebtCode(bizDebtSummary.getDebtCode());
				cult.setId(idCust);
				cult.setCreateTime(dat);
				cult.setUpdateTime(dat);
				cult.setCreateBy(bizDebtSummary.getBankTellerId());
				cult.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizCustomerMapper.insert(cult);
				log.info("====================保存申请人的客户信息（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

				log.info("====================保存申请人的PTS（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizPTS bizPTSPro = new BizPTS();
				bizPTSPro.setDebtCode(bizDebtSummary.getDebtCode());
				bizPTSPro.setObjtyp("BIZ_DEBT_MAIN");
				bizPTSPro.setObjinr(idDebtSummary.toString());
				bizPTSPro.setRole("APPT");
				bizPTSPro.setPtyinr(idCust.toString());
				bizPTSPro.setCreateTime(dat);
				bizPTSPro.setUpdateTime(dat);
				bizPTSPro.setCreateBy(bizDebtSummary.getBankTellerId());
				bizPTSPro.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizPTSMapper.insert(bizPTSPro);
				log.info("====================保存申请人的PTS（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
			} else {
				log.info("====================保存申请人的PTS（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizPTS bizPTSPro = new BizPTS();
				bizPTSPro.setDebtCode(bizDebtSummary.getDebtCode());
				bizPTSPro.setObjtyp("BIZ_DEBT_MAIN");
				bizPTSPro.setObjinr(idDebtSummary.toString());
				bizPTSPro.setRole("APPT");
				bizPTSPro.setPtyinr(bizDebtSummary.getProposerNum());
				bizPTSPro.setCreateTime(dat);
				bizPTSPro.setUpdateTime(dat);
				bizPTSPro.setCreateBy(bizDebtSummary.getBankTellerId());
				bizPTSPro.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizPTSMapper.insert(bizPTSPro);
				log.info("====================保存申请人的PTS（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
			}

			//保存担保信息
			log.info("====================保存担保信息表（BIZ_GUARANTEE_INFO）开始执行-方案编号："+bizDebtSummary+"==========================");
			List<BizGuaranteeInfo> bizGuaranteeInfoList = (List<BizGuaranteeInfo>) mapObj.get("bizGuaranteeInfoList");
			for (BizGuaranteeInfo gu : bizGuaranteeInfoList) {
				Long idGuar = IdWorker.getId();
				gu.setCreateTime(dat);
				gu.setUpdateTime(dat);
				gu.setCreateBy(bizDebtSummary.getBankTellerId());
				gu.setUpdateBy(bizDebtSummary.getBankTellerId());
				gu.setId(idGuar);
				bizGuaranteeInfoMapper.insert(gu);
				log.info("====================保存担保信息表（BIZ_GUARANTEE_INFO）执行成功-方案编号："+bizDebtSummary+"==========================");

				//保存押品信息表
				log.info("====================保存押品信息表（BIZ_CONTRACT_COLLATERAL）开始执行-方案编号："+bizDebtSummary+"==========================");
				if (gu.getBetInformationList() != null) {
					List<BizBetInformation> betList = gu.getBetInformationList();
					for (BizBetInformation bet : betList) {
						//担保合同编号
						bet.setGuarNo(gu.getWarrantyContractNumber());
						Long idbetInf = IdWorker.getId();
						bet.setDebtCode(bizDebtSummary.getDebtCode());
						bet.setId(idbetInf);
						bet.setCreateTime(dat);
						bet.setUpdateTime(dat);
						bet.setCreateBy(bizDebtSummary.getBankTellerId());
						bet.setUpdateBy(bizDebtSummary.getBankTellerId());
						bizBetInformationMapper.insert(bet);
						log.info("====================保存押品信息表（BIZ_CONTRACT_COLLATERAL）执行成功-方案编号："+bizDebtSummary+"==========================");
					}
				}

				//保存担保金额cbe 方案
				log.info("====================保存担保金额的cbe表（BIZ_CBE）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizCBE bizCBE2 = new BizCBE();
				Long idCb = IdWorker.getId();
				bizCBE2.setId(idCb);
				bizCBE2.setObjType("BIZ_GUARANTEE_INFO");
				bizCBE2.setObjInr(idGuar);
				bizCBE2.setCbt("SOLUIN");
				bizCBE2.setDat(dat);
				bizCBE2.setCur(gu.getCurrencyGuarantee());
				bizCBE2.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
				bizCBE2.setCredat(dat);
				//bizCBE2.setXrfcur(gu.getCurrencyGuarantee());
				//bizCBE2.setXrfamt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
				bizCBE2.setCreateTime(dat);
				bizCBE2.setUpdateTime(dat);
				bizCBE2.setCredat(dat);
				bizCBE2.setCreateBy(bizDebtSummary.getBankTellerId());
				bizCBE2.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizCBEMapper.insert(bizCBE2);
				log.info("====================保存担保金额cbe（BIZ_CBE） 执行成功-方案编号："+bizDebtSummary+"==========================");

				//保存担保金额cbb 方案
				log.info("====================保存担保金额cbb（BIZ_CBB）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizCBB bizCBB2 = new BizCBB();
				Long idCbb2 = IdWorker.getId();
				bizCBB2.setId(idCbb2);
				bizCBB2.setObjType("BIZ_GUARANTEE_INFO");
				bizCBE2.setObjInr(idGuar);
				bizCBB2.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
				bizCBB2.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
				bizCBB2.setBegdat(dat);
				bizCBB2.setEnddat(DateUtil.stringToDate(BizContant.END_DATE));
				bizCBB2.setCur(gu.getCurrencyGuarantee());
				bizCBB2.setAmt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
				//bizCBB2.setXrfcur(gu.getCurrencyGuarantee());
				//bizCBB2.setXrfamt(StringUtil.stringToBigDecimal(gu.getGuaranteeAmount()));
				bizCBB2.setCbeInr(idCb);
				bizCBB2.setCreateTime(dat);
				bizCBB2.setUpdateTime(dat);
				bizCBB2.setCreateBy(bizDebtSummary.getBankTellerId());
				bizCBB2.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizCBBMapper.insert(bizCBB2);
				log.info("====================保存担保金额cbb（BIZ_CBB）执行成功-方案编号："+bizDebtSummary+"==========================");

				//PTS表，担保人
				int b = 0;
				for (BizCustomer cucu : bizCustomerList) {
					if (cucu.getCustNo().equals(gu.getGuarantorCustId().toString())) {
						b++;
					}
				}
				if (b == 0) {
					//说明担保人没有插入数据库
					log.info("====================保存担保人的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
					BizCust custt = new BizCust();
					custt.setCustNo(gu.getGuarantorCustId().toString());
					BizCust cuuu = bizCustMapper.selectOne(custt);
					if (cuuu != null) {
						BizCustomer cult1 = new BizCustomer();
						BeanUtils.copyProperties(cuuu, cult1);
						Long idCust1 = IdWorker.getId();
						cult1.setDebtCode(bizDebtSummary.getDebtCode());
						cult1.setId(idCust1);
						cult1.setCreateTime(dat);
						cult1.setUpdateTime(dat);
						cult1.setCreateBy(bizDebtSummary.getBankTellerId());
						cult1.setUpdateBy(bizDebtSummary.getBankTellerId());
						bizCustomerMapper.insert(cult1);
						log.info("====================保存担保人的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

						log.info("====================保存担保人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
						BizPTS bizPTSguaran = new BizPTS();
						bizPTSguaran.setDebtCode(bizDebtSummary.getDebtCode());
						bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
						bizPTSguaran.setObjinr(idGuar.toString());
						bizPTSguaran.setRole("GUAR");
						bizPTSguaran.setPtyinr(idCust1.toString());
						bizPTSguaran.setCreateTime(dat);
						bizPTSguaran.setUpdateTime(dat);
						bizPTSguaran.setCreateBy(bizDebtSummary.getBankTellerId());
						bizPTSguaran.setUpdateBy(bizDebtSummary.getBankTellerId());
						bizPTSMapper.insert(bizPTSguaran);
						log.info("====================保存担保人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
					}
				} else {
					log.info("====================保存担保人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
					BizPTS bizPTSguaran = new BizPTS();
					bizPTSguaran.setDebtCode(bizDebtSummary.getDebtCode());
					bizPTSguaran.setObjtyp("BIZ_GUARANTEE_INFO");
					bizPTSguaran.setObjinr(idGuar.toString());
					bizPTSguaran.setRole("GUAR");
					bizPTSguaran.setPtyinr(gu.getGuarantorCustId().toString());
					bizPTSguaran.setCreateTime(dat);
					bizPTSguaran.setUpdateTime(dat);
					bizPTSguaran.setCreateBy(bizDebtSummary.getBankTellerId());
					bizPTSguaran.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizPTSMapper.insert(bizPTSguaran);
					log.info("====================保存担保人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
				}
			}
			//保存租金保理表
			log.info("====================保存租金保理表（BIZ_RENTAL_FACTORING_KEY）开始执行-方案编号："+bizDebtSummary+"==========================");
			List<BizTheRentFactoring> bizTheRentFactorList = (List<BizTheRentFactoring>) mapObj.get("rentList");
			for (BizTheRentFactoring re : bizTheRentFactorList) {
				Long idRent = IdWorker.getId();
				re.setId(idRent);
				re.setCreateTime(dat);
				re.setUpdateTime(dat);
				re.setCreateBy(bizDebtSummary.getBankTellerId());
				re.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizTheRentFactoringMapper.insert(re);
				log.info("====================保存租金保理表（BIZ_RENTAL_FACTORING_KEY）执行成功-方案编号："+bizDebtSummary+"==========================");

				//PTS表，担保人
				int c = 0;
				for (BizCustomer cucu : bizCustomerList) {
					if (cucu.getCustNo().equals(re.getCustNo())) {
						c++;
					}
				}
				if (c == 0) {
					//说明承销人没有插入数据库
					log.info("====================保存承销人的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
					BizCust cust3 = new BizCust();
					cust3.setCustNo(re.getCustNo());
					BizCust cuuu = bizCustMapper.selectOne(cust3);
					BizCustomer cult3 = new BizCustomer();
					BeanUtils.copyProperties(cuuu, cult3);
					Long idCust3 = IdWorker.getId();
					cult3.setDebtCode(bizDebtSummary.getDebtCode());
					cult3.setId(idCust3);
					cult3.setCreateTime(dat);
					cult3.setUpdateTime(dat);
					cult3.setCreateBy(bizDebtSummary.getBankTellerId());
					cult3.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizCustomerMapper.insert(cult3);
					log.info("====================保存承销人的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

					//保存PTS表 承销人
					log.info("====================保存承销人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
					BizPTS bizPTSRen3 = new BizPTS();
					bizPTSRen3.setDebtCode(bizDebtSummary.getDebtCode());
					bizPTSRen3.setObjtyp("BIZ_RENTAL_FACTORING_KEY");
					bizPTSRen3.setObjinr(idRent.toString());
					bizPTSRen3.setRole("CONE");
					bizPTSRen3.setPtyinr(idCust3.toString());
					bizPTSRen3.setCreateTime(dat);
					bizPTSRen3.setUpdateTime(dat);
					bizPTSRen3.setCreateBy(bizDebtSummary.getBankTellerId());
					bizPTSRen3.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizPTSMapper.insert(bizPTSRen3);
					log.info("====================保存承销人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
				} else {
					//保存PTS表 承销人
					log.info("====================保存承销人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
					BizPTS bizPTSRen3 = new BizPTS();
					bizPTSRen3.setDebtCode(bizDebtSummary.getDebtCode());
					bizPTSRen3.setObjtyp("BIZ_RENTAL_FACTORING_KEY");
					bizPTSRen3.setObjinr(idRent.toString());
					bizPTSRen3.setRole("CONE");
					bizPTSRen3.setPtyinr(re.getCustNo());
					bizPTSRen3.setCreateTime(dat);
					bizPTSRen3.setUpdateTime(dat);
					bizPTSRen3.setCreateBy(bizDebtSummary.getBankTellerId());
					bizPTSRen3.setUpdateBy(bizDebtSummary.getBankTellerId());
					bizPTSMapper.insert(bizPTSRen3);
					log.info("====================保存承销人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
				}

			}
			log.info("====================保存方案的方法执行完成-方案编号："+bizDebtSummary+"==========================");
		}catch (Exception ex) {
				txManager.rollback(status);
				throw ex;
			}
			txManager.commit(status);

		String userIdNew = mapObj.get("userId").toString();
		log.info("userIdNew============="+userIdNew);
		BizDebtSummary bizDebtSummary = (BizDebtSummary) mapObj.get("bizDebtSummary");
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
		param1.put("debtCode",bizDebtSummary.getDebtCode());
		bizProStatementProvider.createAndstartProcess(param1);
			return true;
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
		bizCBB.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
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
			bizCBBGuar.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
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
			BizCustomer cult1 = new BizCustomer();
			cult1.setDebtCode(debtCode);
			cult1.setCustNo(guarantee.getGuarantorCustId().toString());
			BizCustomer cuuu=bizCustomerMapper.selectOne(cult1);
			if(cuuu==null){
				BizCustomer cust12=bizCustomerProvider.update(cult1);
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

			List<BizCustomer> customerList=productTypes.getCustomersList();
			for(BizCustomer cus:customerList){
				//保存额度类型表数据
				BizProductLinesType lines=new BizProductLinesType();
				Long idProLine = IdWorker.getId();
				lines.setId(idProLine);
				lines.setDebtCode(debtCode);
				lines.setCustNo(cus.getCustNo());
				lines.setBusinessType(productTypes.getCode());
				lines.setCreditLinesId(cus.getCreditLinesId());
				lines.setCreditRatio(cus.getCreditRatio());
				lines.setCreateTime(date1);
				lines.setUpdateTime(date1);
				lines.setCreateBy(bizDebtSummary.getBankTellerId());
				lines.setUpdateBy(bizDebtSummary.getBankTellerId());
				bizProductLinesTypeMapper.insert(lines);

				//判断用信主体在customer表是否重复
				BizCustomer customer1=new BizCustomer();
				customer1.setDebtCode(debtCode);
				customer1.setCustNo(cus.getCustNo());
				customer1.setCustNameCN(cus.getCustNameCN());
				BizCustomer cuuu1=bizCustomerMapper.selectOne(customer1);
				if(cuuu1==null){
					//保存客户信息表
					cus.setDebtCode(debtCode);
					BizCustomer bizCustomer1=bizCustomerProvider.update(cus);

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
	public void getSchemeState(Map<String, Object> params) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(date);
		Date passDate=null;
		try {
			passDate = sdf.parse(format);
		}catch (Exception e){
			e.printStackTrace();
		}

		List<BizDebtSummary> bizDebtList=queryList(params);
		BizDebtSummary bizDebt=bizDebtList.get(0);
		//补录，修订通过
		if("TT".equals(params.get("commite").toString())){
			bizDebt.setSolutionState(BizStatus.DEBTAVAI);
			bizDebt.setProcessStatus(BizStatus.DEPRAPPR);
			bizDebt.setUpdateTime(passDate);
			update(bizDebt);
		} if("XDTT".equals(params.get("commite").toString())){
			bizDebt.setSolutionState(BizStatus.DEBTAVAI);
			bizDebt.setProcessStatus(BizStatus.DEPRAPPR);
			bizDebt.setUpdateTime(passDate);
			update(bizDebt);

			String debtCode=params.get("debtCode").toString();
			String debtCode1=debtCode.substring(0, 13);
			String debtCode2=debtCode.substring(13);
			Long debtCode3=StringUtil.stringToLong(debtCode2);
			debtCode3=debtCode3-1;
			String debtCode4=debtCode3.toString();
			String debtCode5=null;
			if(debtCode4.length()==1){
				debtCode5=debtCode1+"00"+debtCode4;
			} else if(debtCode4.length()==2){
				debtCode5=debtCode1+"0"+debtCode4;
			}

			Map<String,Object>oldMap=new HashMap<>();
			oldMap.put("debtCode", debtCode5);
			List<BizDebtSummary>bizDebt1List=queryList(oldMap);
			BizDebtSummary bizDebt1=bizDebt1List.get(0);
			bizDebt1.setSolutionState(BizStatus.DEBTINVA);
			bizDebt1.setProcessStatus(BizStatus.DEPRAPPR);
			bizDebt1.setUpdateTime(passDate);
			update(bizDebt1);
		}
		//补录驳回
		if("BLBH".equals(params.get("commite").toString())){
			bizDebt.setSolutionState(BizStatus.DEBTDOWN);
			bizDebt.setProcessStatus(BizStatus.DEPRDOWN);
			update(bizDebt);
		}
		//修订驳回
		if("XDBH".equals(params.get("commite").toString())){
			String debtCode=params.get("debtCode").toString();
			String debtCode1=debtCode.substring(0, 13);
			String debtCode2=debtCode.substring(13);
			Long debtCode3=StringUtil.stringToLong(debtCode2);
			debtCode3=debtCode3-1;
			String debtCode4=debtCode3.toString();
			String debtCode5=null;
			if(debtCode4.length()==1){
				debtCode5=debtCode1+"00"+debtCode4;
			} else if(debtCode4.length()==2){
				debtCode5=debtCode1+"0"+debtCode4;
			}
			//把原来的方案重新制成冻结
			Map<String,Object>oldMap=new HashMap<>();
			oldMap.put("debtCode", debtCode5);
			List<BizDebtSummary>bizDebt1List=queryList(oldMap);
			BizDebtSummary bizDebt1=bizDebt1List.get(0);
			bizDebt1.setSolutionState(BizStatus.DEBTFROZ);
			bizDebt.setProcessStatus(BizStatus.DEPRAPPR);
			update(bizDebt1);

			//现有方案已驳回
			Map<String,Object>newMap=new HashMap<>();
			newMap.put("debtCode", debtCode);
			List<BizDebtSummary>bizDebt2List=queryList(newMap);
			BizDebtSummary bizDebt2=bizDebt2List.get(0);
			bizDebt2.setSolutionState(BizStatus.DEBTDOWN);
			bizDebt2.setProcessStatus(BizStatus.DEPRDOWN);
			update(bizDebt2);
		}

	}

	/**
	 * 驳回的重新提交
	 * **/
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void ReSaveDebt(Map<String, Object> mapObj) {
		log.info("====================重新提交保存方案的方法开始执行==========================");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = txManager.getTransaction(def);
		try {
		Date dat = new Date();

		//保存债项信息表
		BizDebtSummary bizDebtSummary = (BizDebtSummary) mapObj.get("bizDebtSummary");
		//全局规则
		bizDebtSummary.setRuleType((long) 0);
		log.info("=========重新提交保存债项信息表（BIZ_DEBT_MAIN）开始执行-方案编号："+bizDebtSummary+"==========================");
		BizDebtSummary bizDebtSummary1 = bizDebtSummaryProvider.update(bizDebtSummary);
			log.info("============重新提交保存债项信息表（BIZ_DEBT_MAIN）执行成功-方案编号："+bizDebtSummary+"==========================");

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
		log.info("==========重新提交保存用信主体的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
		Set<BizCustomer> bizCustomerList = (Set<BizCustomer>) mapObj.get("cuSet");
		for (BizCustomer cu : bizCustomerList) {
			Long idCust = IdWorker.getId();
			cu.setDebtCode(bizDebtSummary1.getDebtCode());
			cu.setId(idCust);
			cu.setCreateTime(dat);
			cu.setUpdateTime(dat);
			cu.setCreateBy(bizDebtSummary1.getBankTellerId());
			cu.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCustomerMapper.insert(cu);
			log.info("===========重新提交保存用信主体的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存PTS表 用信客户
			log.info("====================重新提交保存用信主体的PTS表（BIZ_TRN）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存用信主体的PTS表（BIZ_TRN）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存用户主体授信信息表
			log.info("=================重新提交保存用户主体授信信息表（BIZ_CREDIT_LINES）开始执行-方案编号："+bizDebtSummary+"==========================");
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
				log.info("====================重新提交保存用户主体授信信息表（BIZ_CREDIT_LINES）执行成功-方案编号："+bizDebtSummary+"==========================");
			}
		}

		//保存额度类型表
			log.info("====================重新提交保存重新提交保存额度类型表（BIZ_PRODUCT_LINESTYPE）开始执行-方案编号："+bizDebtSummary+"==========================");
		List<BizProductLinesType> productLineList = (List<BizProductLinesType>) mapObj.get("productLinesTypesList");
		for (BizProductLinesType proLine : productLineList) {
			Long idProLine = IdWorker.getId();
			proLine.setId(idProLine);
			proLine.setCreateTime(dat);
			proLine.setUpdateTime(dat);
			proLine.setCreateBy(bizDebtSummary.getBankTellerId());
			proLine.setUpdateBy(bizDebtSummary.getBankTellerId());
			bizProductLinesTypeMapper.insert(proLine);
			log.info("====================重新提交保存重新提交保存额度类型表（BIZ_PRODUCT_LINESTYPE）执行成功-方案编号："+bizDebtSummary+"==========================");
		}

		//保存交易流水表 trn
			log.info("====================重新提交保存交易流水表（BIZ_TRN）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存交易流水表（BIZ_TRN）执行成功-方案编号："+bizDebtSummary+"==========================");

		//保存发生额信息表cbe 方案金额
			log.info("====================重新提交保存方案金额的发生额信息表cbe（BIZ_CBE）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存方案金额的发生额信息表cbe（BIZ_CBE）执行成功-方案编号："+bizDebtSummary+"==========================");

		//保存余额信息表cbb 方案金额
			log.info("====================重新提交保存方案金额的余额信息表cbb（BIZ_CBB）开始执行-方案编号："+bizDebtSummary+"==========================");
		BizCBB bizCBB = new BizCBB();
		bizCBB.setObjType("BIZ_DEBT_MAIN");
		bizCBB.setObjInr(bizDebtSummary1.getId());
		bizCBB.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
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
			log.info("====================重新提交保存方案金额的余额信息表cbb（BIZ_CBB）执行成功-方案编号："+bizDebtSummary+"==========================");


		//保存单一规则表
			log.info("====================重新提交保存单一规则表（BIZ_SIGLE_PRODUCT_RULE）开始执行-方案编号："+bizDebtSummary+"==========================");
		List<BizSingleProductRule> bizSingleProductRuleList = (List<BizSingleProductRule>) mapObj.get("bizSingleProductRuleList");
		for (BizSingleProductRule sing : bizSingleProductRuleList) {
			bizSingleProductRuleProvider.update(sing);
			log.info("====================重新提交保存单一规则表（BIZ_SIGLE_PRODUCT_RULE）执行成功-方案编号："+bizDebtSummary+"==========================");
		}

		int a = 0;
		//保存PTS表 申请人
		for (BizCustomer cucu : bizCustomerList) {
			if (cucu.getCustNo().equals(bizDebtSummary1.getProposerNum())) {
				a++;
			}
		}
		if (a == 0) {
			//说明申请人没有插入数据库
			log.info("====================重新提交保存申请人的客户信息（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
			BizCust custt = new BizCust();
			custt.setCustNo(bizDebtSummary1.getProposerNum());
			BizCust cuuu = bizCustMapper.selectOne(custt);
			BizCustomer cult = new BizCustomer();
			BeanUtils.copyProperties(cuuu, cult);
			Long idCust = IdWorker.getId();
			cult.setDebtCode(bizDebtSummary1.getDebtCode());
			cult.setId(idCust);
			cult.setCreateTime(dat);
			cult.setUpdateTime(dat);
			cult.setCreateBy(bizDebtSummary1.getBankTellerId());
			cult.setUpdateBy(bizDebtSummary1.getBankTellerId());
			bizCustomerMapper.insert(cult);
			log.info("====================重新提交保存申请人的客户信息（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

			log.info("====================重新提交保存申请人的PTS（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存申请人的PTS（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
		} else {
			log.info("====================重新提交保存申请人的PTS（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存申请人的PTS（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
		}

		//保存担保信息
			log.info("====================重新提交保存担保信息表（BIZ_GUARANTEE_INFO）开始执行-方案编号："+bizDebtSummary+"==========================");
		List<BizGuaranteeInfo> bizGuaranteeInfoList = (List<BizGuaranteeInfo>) mapObj.get("bizGuaranteeInfoList");
		for (BizGuaranteeInfo gu : bizGuaranteeInfoList) {
			BizGuaranteeInfo bizGuaranteeInfo1 = bizGuaranteeInfoProvider.update(gu);
			log.info("====================重新提交保存担保信息表（BIZ_GUARANTEE_INFO）执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存押品信息表
			log.info("====================重新提交保存押品信息表（BIZ_CONTRACT_COLLATERAL）开始执行-方案编号："+bizDebtSummary+"==========================");
			if (gu.getBetInformationList() != null) {
				List<BizBetInformation> betList = gu.getBetInformationList();
				for (BizBetInformation bet : betList) {
					//担保合同编号
					bet.setGuarNo(gu.getWarrantyContractNumber());
					bet.setDebtCode(bizDebtSummary.getDebtCode());
					BizBetInformation bizBetInformation1 = bizBetInformationProvider.update(bet);
					log.info("====================重新提交保存押品信息表（BIZ_CONTRACT_COLLATERAL）执行成功-方案编号："+bizDebtSummary+"==========================");
				}
			}

			//保存担保金额cbe 方案
			log.info("====================重新提交保存担保金额的cbe表（BIZ_CBE）开始执行-方案编号："+bizDebtSummary+"==========================");
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
			log.info("====================重新提交保存担保金额cbe（BIZ_CBE） 执行成功-方案编号："+bizDebtSummary+"==========================");

			//保存担保金额cbb 方案
			log.info("====================重新提交保存担保金额cbb（BIZ_CBB）开始执行-方案编号："+bizDebtSummary+"==========================");
			BizCBB bizCBB4 = new BizCBB();
			Long idCbb2 = IdWorker.getId();
			bizCBB4.setId(idCbb2);
			bizCBB4.setObjType("BIZ_GUARANTEE_INFO");
			bizCBB4.setObjInr(idCb);
			bizCBB4.setCbc(BizContant.DEBT_SUMMARY_CBCTXT);
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
			log.info("====================重新提交保存担保金额cbb（BIZ_CBB）执行成功-方案编号："+bizDebtSummary+"==========================");

			//PTS表，担保人
			int b = 0;
			for (BizCustomer cucu : bizCustomerList) {
				if (cucu.getCustNo().equals(gu.getGuarantorCustId().toString())) {
					b++;
				}
			}
			if (b == 0) {
				//说明担保人没有插入数据库
				log.info("====================重新提交保存担保人的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizCust custt = new BizCust();
				custt.setCustNo(gu.getGuarantorCustId().toString());
				BizCust cuuu = bizCustMapper.selectOne(custt);
				if (cuuu != null) {
					BizCustomer cult1 = new BizCustomer();
					BeanUtils.copyProperties(cuuu, cult1);
					Long idCust1 = IdWorker.getId();
					cult1.setDebtCode(bizDebtSummary1.getDebtCode());
					cult1.setId(idCust1);
					cult1.setCreateTime(dat);
					cult1.setUpdateTime(dat);
					cult1.setCreateBy(bizDebtSummary1.getBankTellerId());
					cult1.setUpdateBy(bizDebtSummary1.getBankTellerId());
					bizCustomerMapper.insert(cult1);
					log.info("====================重新提交保存担保人的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

					log.info("====================重新提交保存担保人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
					log.info("====================重新提交保存担保人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
				}
			} else {
				log.info("====================重新提交保存担保人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
				log.info("====================重新提交保存担保人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
			}
		}

		//保存租金保理表
			log.info("====================重新提交保存租金保理表（BIZ_RENTAL_FACTORING_KEY）开始执行-方案编号："+bizDebtSummary+"==========================");
		List<BizTheRentFactoring> bizTheRentFactorList = (List<BizTheRentFactoring>) mapObj.get("rentList");
		for (BizTheRentFactoring re : bizTheRentFactorList) {
			BizTheRentFactoring bizTheRentFactoring = bizTheRentFactoringProvider.update(re);
			log.info("====================重新提交保存租金保理表（BIZ_RENTAL_FACTORING_KEY）执行成功-方案编号："+bizDebtSummary+"==========================");

			//PTS表，担保人
			int c = 0;
			for (BizCustomer cucu : bizCustomerList) {
				if (cucu.getCustNo().equals(re.getCustNo())) {
					c++;
				}
			}
			if (c == 0) {
				//说明承销人没有插入数据库
				log.info("====================重新提交保存承销人的客户信息表（BIZ_CUSTOMER）开始执行-方案编号："+bizDebtSummary+"==========================");
				BizCust cust3 = new BizCust();
				cust3.setCustNo(re.getCustNo());
				BizCust cuuu = bizCustMapper.selectOne(cust3);
				BizCustomer cult3 = new BizCustomer();
				BeanUtils.copyProperties(cuuu, cult3);
				Long idCust3 = IdWorker.getId();
				cult3.setDebtCode(bizDebtSummary1.getDebtCode());
				cult3.setId(idCust3);
				cult3.setCreateTime(dat);
				cult3.setUpdateTime(dat);
				cult3.setCreateBy(bizDebtSummary1.getBankTellerId());
				cult3.setUpdateBy(bizDebtSummary1.getBankTellerId());
				bizCustomerMapper.insert(cult3);
				log.info("====================重新提交保存承销人的客户信息表（BIZ_CUSTOMER）执行成功-方案编号："+bizDebtSummary+"==========================");

				//保存PTS表 承销人
				log.info("====================重新提交保存承销人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
				log.info("====================重新提交保存承销人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
			} else {
				//保存PTS表 承销人
				log.info("====================重新提交保存承销人的PTS表（BIZ_PTS）开始执行-方案编号："+bizDebtSummary+"==========================");
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
				log.info("====================重新提交保存承销人的PTS表（BIZ_PTS）执行成功-方案编号："+bizDebtSummary+"==========================");
			}

		}
			log.info("====================重新提交保存方案的方法执行完成-方案编号："+bizDebtSummary+"==========================");

		}catch (Exception ex) {
			txManager.rollback(status);
			throw ex;
		}
		txManager.commit(status);

		BizDebtSummary bizDebtSummary = (BizDebtSummary) mapObj.get("bizDebtSummary");
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
		param1.put("debtCode",bizDebtSummary.getDebtCode());
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
		log.info("定时任务刷新方案过期时间执行："+date);
		for (BizDebtSummary debtSummary:bizDebtSummaryList){
			if(debtSummary.getPgExpiDate().before(date)){
				debtSummary.setSolutionState(BizStatus.DEBTREGULAR);
				update(debtSummary);
		}
			System.out.println("定时任务刷新方案过期时间执行完成："+date);
			log.info("定时任务刷新方案过期时间执行完成："+date);
	}
}
}
