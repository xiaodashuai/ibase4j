/**
 *
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.SerializeUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.model.*;
import org.ibase4j.vo.PairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 描述：发放申请流程 日期：2018/7/24
 * 
 * @author czm
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizDebtGrant")
@Service(interfaceClass = BizDebtGrantProvider.class)
public class BizDebtGrantProviderImpl extends BaseProviderImpl<BizDebtGrant> implements BizDebtGrantProvider {
	private static final Logger log = LogManager.getLogger(BizDebtGrantProviderImpl.class);
	@Autowired
	private BizDebtGrantMapper bizDebtGrantMapper;
	@Autowired
	private BizCBBProvider bizCBBProvider;
	@Autowired
	private BizCBEProvider bizCBEProvider;
	@Autowired
	private BizFECProvider bizFECProvider;
	@Autowired
	private BizFEPProvider bizFEPProvider;
	@Autowired
	private BizDebtSummaryProvider bizDebtSummaryProvider;
	@Autowired
	private BizGuaranteeResultProvider bizGuaranteeResultProvider;
	@Autowired
	private BizTRNProvider bizTRNProvider;
	@Autowired
	private BizRentalFactoringProvider bizRentalFactoringProvider;
	@Autowired
	private BizInterestRateProvider bizInterestRateProvider;
	@Autowired
	private BizTemporarySaveProvider bizTempProvider;
	@Autowired
	private BizRepaymentPlanProvider bizRepaymentPlanProvider;
	@Autowired
	private BizGuaranteeContractProvider bizGuaranteeContractProvider;
	@Autowired
	private BizCustomerProvider bizCustomerProvider;
	@Autowired
	private BizTheRentFactoringProvider bizTheRentFactoringProvider;
	@Autowired
	private BizRepaymentPricinalPlanProvider bizRepaymentPricinalPlanProvider;
	@Autowired
	private BizRepaymentLoanPlanProvider bizRepaymentLoanPlanProvider;
	@Autowired
	private IBizProductTypeProvider bizProductTypeProvider;
	@Autowired
	private BizGuaranteeTypeProvider bizGuaranteeTypeProvider;
	@Autowired
	private BizCntProvider bizCntProvider;
	@Autowired
	private BizCreditLinesProvider bizCreditLinesProvider;
	@Autowired
	private BizGuaranteeInfoProvider bizGuaranteeInfoProvider;
	@Autowired
	private BizMakeLoansProvider bizMakeLoansProvider;
	@Autowired
	private BizSingleProductRuleProvider singleProductRuleProvider;
	@Autowired
	private BizProStatementProvider bizProStatementProvider;
	@Autowired
	private RedisHelper redisHelper;

	/**
	 * @Description: 查询满足放款条件的债项方案申请
	 * @Param: [param]
	 * @return: com.baomidou.mybatisplus.plugins.Page
	 *          <org.ibase4j.model.BizDebtGrant>
	 */
	@Override
	public Page getMakeLoansDebts(Map<String, Object> params) {
		// 查询发放信息
		Page page = getPage(params);
		page.setOrderByField("A.PASS_DATE");
		Long userId = StringUtil.objectToLong(params.get("userId"));
		SysDept sysDept = bizMakeLoansProvider.getUserInstitutionCodeByUserId(userId);
		params.put("institutionCode", sysDept.getParentCode());
		List makeLoansDebtsList = bizDebtGrantMapper.getMakeLoansDebts(page, params);
		// 通过发放信息查询币种信息
		if (makeLoansDebtsList != null && makeLoansDebtsList.size() > 0) {
			for (int i = 0; i < makeLoansDebtsList.size(); i++) {
				Map makeLoansDebts = (Map) makeLoansDebtsList.get(i);
				String objInr = StringUtil.objectToString(makeLoansDebts.get("objInr"));
				Map paramsMap = new HashMap();
				paramsMap.put("objInr", objInr);
				List bizFECList = bizFECProvider.getBizFECByINR(paramsMap);
				makeLoansDebts.put("bizFECList", bizFECList);
			}
		}
		page.setRecords(makeLoansDebtsList);
		return page;
	}

	/**
	 * @Description: 修改债项发放方案状态及台账记录
	 * @Param: [grantCode,grantStatus]
	 * @return: void
	 */
	@Override
	public void updateDebtGrantStatus(String grantCode, Integer grantStatus) {
		BizDebtGrant bizDebtGrant = new BizDebtGrant();
		bizDebtGrant.setGrantCode(grantCode);
		BizDebtGrant bizDebtGrant1 = selectOne(bizDebtGrant);
		// 修改债项发放方案状态
		bizDebtGrant1.setGrantStatus(grantStatus);
		BizDebtGrant returnDebtGrant = update(bizDebtGrant1);
	}

	@Override
	public int getCountByProductId(String productId) {
		return bizDebtGrantMapper.getCountByProductId(productId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BizDebtGrant updateNofw(Map<String, Object> record) {
		log.info("开始保存债项发放迁移...");
		for (String key : record.keySet()) {
			System.out.println("key= " + key + " and value= " + record.get(key));
		}
		Integer isRate = StringUtil.objToInteger(record.get("isRate"));// 有无费用1有,2无
		Long createBy = StringUtil.objectToLong(record.get("createBy"));
		String grantCode = StringUtil.objectToString(record.get("grantCode"));
		String debtCode = StringUtil.objectToString(record.get("debtCode"));
		String businessCode = StringUtil.objectToString(record.get("businessCode"));
		String currencyListStr = StringUtil.objectToString(record.get("currencyList"));
		String paymentAmtListStr = StringUtil.objectToString(record.get("paymentAmtList"));
		// 发放币种和金额
		String[] currencyList = StringUtils.split(currencyListStr, '#');
		String[] paymentAmtList = StringUtils.split(paymentAmtListStr, '#');
		// 查询租金保理专有信息
		Map<String, Object> theRentFactoringMap = new HashMap<String, Object>();
		theRentFactoringMap.put("debtCode", debtCode);
		BizTheRentFactoring bizTheRentFactoring = bizTheRentFactoringProvider.queryList(theRentFactoringMap).get(0);
		// 1.发放申请
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grantCode", grantCode);
		params.put("debtCode", debtCode);
		List<BizDebtGrant> resultList = this.queryList(params);
		BizDebtGrant grant = null;
		if (resultList != null && resultList.size() > 0) {
			grant = resultList.get(0);
		} else {
			throw new RuntimeException("发放编号不存在或编号为空！");
		}
		// 查询方案
		Map<String, Object> debtMap2 = new HashMap<>();
		debtMap2.put("debtCode", debtCode);
		BizDebtSummary summary = bizDebtSummaryProvider.queryList(debtMap2).get(0);
		// 把页面修改的数据覆盖到发放条件对象中
		grant.setBusinessTypes(businessCode);
		grant.setCreateBy(createBy);
		grant.setCreateTime(new Date());
		grant.setProperInfo(StringUtil.objectToLong(record.get("properInfo")));// 产品专有信息外键
		grant.setDebtCode(debtCode);
		grant.setGrantAmt(StringUtil.stringToBigDecimal(paymentAmtList[0]));// 发放金额,获取第一个索引处的金额为发放金额
		grant.setCurrency(currencyList[0]);// 发放币种
		grant.setProposer(summary.getProposer());
		grant.setBusinessName(StringUtil.objectToString(record.get("businessName")));// 产品名称
		grant.setBusinessTypeName(summary.getProjectName());
		grant.setEnable(BizContant.ENABLE_YES);
		grant.setProcessStatus(BizContant.ENABLE_YES);
		grant.setHistoryState(String.valueOf(4));
		// 数据迁移，状态不改变
		// grant.setGrantStatus(BizContant.GRANALIS);//状态为已发放
		grant.setGracePeriod(StringUtil.objToInteger(record.get("gracePeriod")));//
		grant.setGrantCode(grantCode);// 流水号
		grant.setRemark(StringUtil.objectToString(record.get("businessName")) + "进行发放");
		grant.setScopeBusinPeriod(StringUtil.objectToString(record.get("scopeBusinPeriod")));
		grant.setSendDate(StringUtil.objectToDate(record.get("sendDate")));
		grant.setEndDate(StringUtil.objectToDate(record.get("sendEndDate")));
		grant.setToierancePeriod(isRate);// 容忍期限存入了是否有费用字段
		grant.setInstitutionCode(StringUtil.objectToString(record.get("deptCode")));// 经办机构
		grant.setDescriptionProgramQuoqate(StringUtil.objectToString(record.get("descriptionProgramQuoqate")));
		grant.setDescriptionRateRules(StringUtil.objectToString(record.get("descriptionRateRules")));
		grant.setOriginalGrantCode(StringUtil.objectToString(record.get("originalGrantCode")));
		grant.setIouCode(StringUtil.objectToString(record.get("iouCode")));
		BizDebtGrant dbGrant = update(grant);
		log.info("修改" + dbGrant == null ? "失败！" : "成功！");
		Long objInr = dbGrant.getId();

		// 2.多个收费信息
		String chargeStr = StringUtil.objectToString(record.get("chargeTypeList"));
		String chargeVals = StringUtil.objectToString(record.get("rateValueList"));
		String[] chargeList = StringUtils.split(chargeStr, '#');
		String[] chargeValues = StringUtils.split(chargeVals, '#');
		int chargeLen = chargeList.length;
		// 如果有新增的费用类型或没有费用，则删除原来的费用fep
		if (chargeLen > 0 || isRate == 2) {
			// 首先删除就的FEP数据
			log.info("首先删除旧的费用表(FEP)的数据...");
			int size = bizFEPProvider.deleteByGrantCode(grant.getId_());
			log.info(size > 0 ? "成功删除:" + size + "条" : "删除失败");
		}
		if (chargeLen > 0) {
			for (int i = 0; i < chargeList.length; i++) {
				// 然后新增新的费用（FEP）
				String charge = chargeList[i];
				if (StringUtils.isNotEmpty(charge)) {
					log.info("开始保存FEP（收息收费表）... ");
					BizFEP fep = new BizFEP();
					fep.setCreateBy(createBy);
					fep.setCreateTime(new Date());
					fep.setEnable(BizContant.ENABLE_YES);
					fep.setFeecod(charge);// 费用代码
					fep.setObjInr(objInr);// 业务表INR(XXD的ID)
					fep.setObjTyp(BizContant.BIZ_TABLE_GRANT);
					fep.setRateType(charge);
					fep.setRateValue(StringUtil.stringToBigDecimal(chargeValues[i]));
					fep.setRemark("发放费用存盘");
					// 执行保存
					BizFEP dbFep = bizFEPProvider.update(fep);
					log.info("保存" + dbFep == null ? "失败！" : "成功！");
				}
			}
		}

		// 3.业务流水
		log.info("开始修改TRN（业务流水表）... ");
		BizTRN trnQuery = new BizTRN();
		trnQuery.setObjtyp(BizContant.BIZ_TABLE_GRANT);//
		trnQuery.setObjinr(grant.getId());
		trnQuery.setInifrm(grantCode);
		BizTRN trn = bizTRNProvider.selectOne(trnQuery);
		// 如果不存在TRN，则新建
		if (trn == null) {
			trn = new BizTRN();
		}
		// 修改trn
		trn.setCreateBy(createBy);
		trn.setCreateTime(new Date());
		trn.setEnable(BizContant.ENABLE_YES);
		// trn.setEtyextkey(etyextkey);
		trn.setExedat(new Date());// 执行日期
		trn.setInidattim(new Date());// 存盘时间
		trn.setInifrm(grantCode);// 交易代码(流水号)自定义
		trn.setIninam("债项发放申请流程");// 交易名称(业务名称)
		trn.setIniusr(createBy);// 登录柜员
		trn.setObjinr(objInr);// 业务表INR(XXD的ID)
		trn.setObjnam(StringUtil.objectToString(record.get("businessName")) + "进行发放");
		trn.setObjtyp(BizContant.BIZ_TABLE_GRANT);//
		trn.setOwnref(StringUtil.objectToString(record.get("debtCode")));// 业务编号
		trn.setBusinessTypes(businessCode);
		trn.setBchkeyinr(StringUtil.objectToString(record.get("deptCode")));// 经办机构
		// TODO 应该匹配哪些属性？
		// trn.setRelamt(relamt);//授权金额
		trn.setRelcur(summary.getaCurrrency());// 授权币种
		trn.setXrecurblk(summary.getOc());// 可用货币
		trn.setReloricur(summary.getMpc());// 主币种
		trn.setReloriamt(StringUtil.stringToBigDecimal(record.get("grantAmt")));// 金额
		// 数据迁移，状态为已发放
		trn.setRelres(String.valueOf(BizStatus.GRANALIS));// 状态为已发放

		BizTRN dbTrn = bizTRNProvider.update(trn);
		log.info("修改" + dbTrn == null ? "失败！" : "成功！");
		// 3.多个利率信息
		String calcDaysListStr = StringUtil.objectToString(record.get("calcDaysList"));
		String chgCycleUnitListStr = StringUtil.objectToString(record.get("chgCycleUnitList"));
		String convertedPriceListStr = StringUtil.objectToString(record.get("convertedPriceList"));

		String floatDirectioinListStr = StringUtil.objectToString(record.get("floatDirectioinList"));
		String floatingRateListStr = StringUtil.objectToString(record.get("floatingRateList"));
		String floatModeListStr = StringUtil.objectToString(record.get("floatModeList"));
		String irBkListStr = StringUtil.objectToString(record.get("irBkList"));
		String overdueIncrRatioListStr = StringUtil.objectToString(record.get("overdueIncrRatioList"));
		String overdueRateCalcModeListStr = StringUtil.objectToString(record.get("overdueRateCalcModeList"));

		String paymentModeListStr = StringUtil.objectToString(record.get("paymentModeList"));
		String rateValListStr = StringUtil.objectToString(record.get("rateValList"));
		String rateTypeListStr = StringUtil.objectToString(record.get("rateTypeList"));// 利率类型
		String varCycleListStr = StringUtil.objectToString(record.get("varCycleList"));
		// 利率信息
		String[] calcDaysList = StringUtils.split(calcDaysListStr, '#');
		String[] chgCycleUnitList = StringUtils.split(chgCycleUnitListStr, '#');
		String[] convertedPriceList = StringUtils.split(convertedPriceListStr, '#');

		String[] floatDirectioinList = StringUtils.split(floatDirectioinListStr, '#');
		String[] floatingRateList = StringUtils.split(floatingRateListStr, '#');
		String[] floatModeList = StringUtils.split(floatModeListStr, '#');
		String[] irBkList = StringUtils.split(irBkListStr, '#');
		String[] overdueIncrRatioList = StringUtils.split(overdueIncrRatioListStr, '#');
		String[] overdueRateCalcModeList = StringUtils.split(overdueRateCalcModeListStr, '#');
		String[] paymentModeList = StringUtils.split(paymentModeListStr, '#');
		String[] rateValList = StringUtils.split(rateValListStr, '#');
		String[] varCycleList = StringUtils.split(varCycleListStr, '#');
		String[] rateTypeList = StringUtils.split(rateTypeListStr, '#');
		// 判断币种如果重新选择后。则更新
		int curLen = currencyList.length;
		if (curLen > 0) {
			// 清空旧的FEC
			log.info("首先删除旧的FEC（利率表）... ");
			int size = bizFECProvider.deleteByGrantId(grant.getId());
			log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
			// 清空就的CBB
			log.info("首先删除旧的CBB（金额流水信息表）... ");
			size = bizCBBProvider.deleteByGrant(grant.getId());
			log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
			// 清空就的CBE
			log.info("首先删除旧的CBE（余额信息表）... ");
			size = bizCBEProvider.deleteByGrant(grant.getId());
			log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
		}
		// 分拆数组，组合fec
		for (int i = 0; i < curLen; i++) {
			// 新建FEC
			String currency = currencyList[i];
			if (StringUtils.isNotEmpty(currency)) {
				log.info("开始保存FEC（利率表）... ");
				BizFEC fec = new BizFEC();
				fec.setType(StringUtil.objToInteger(rateTypeList[i]));
				// 浮动利率必填项,固定利率不能填写
				if ("2".equals(rateTypeList[i])) {
					BizGuaranteeType guaranteeType = getBizGuaranteeType(irBkList[i]);
					fec.setIrBk(guaranteeType.getSrv1());// 利率基准
					if (guaranteeType != null) {
						fec.setTerm(guaranteeType.getSrv2());// 利率期限
					}
					fec.setFloatDirectioin(floatDirectioinList[i]);// 利率浮动方向
					fec.setFloatMode(floatModeList[i]);// 利率浮动方式
					fec.setFloatingRate(StringUtil.stringToBigDecimal(floatingRateList[i]));// 浮动率
					fec.setVarCycle(StringUtil.objToInteger(varCycleList[i]));// 利率变动周期
					fec.setChgCycleUnit(StringUtil.objToInteger(chgCycleUnitList[i]));// 利率变动周期单位
				}
				fec.setCalcDays(StringUtil.objToInteger(calcDaysList[i]));
				fec.setConvertedPrice(StringUtil.stringToBigDecimal(convertedPriceList[i]));
				fec.setCreateBy(createBy);
				fec.setCreateTime(new Date());
				fec.setCurrency(currency);
				fec.setEnable(BizContant.ENABLE_YES);
				fec.setObjInr(objInr);
				fec.setObjTyp(BizContant.BIZ_TABLE_GRANT);
				fec.setOverdueIncrRatio(StringUtil.stringToBigDecimal(overdueIncrRatioList[i]));
				fec.setOverdueRateCalcMode(overdueRateCalcModeList[i]);
				fec.setPaymentAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));
				fec.setPaymentMode(paymentModeList[i]);
				fec.setRemark("债项发放流程");
				// fec.setRules(rules);//
				fec.setType(StringUtil.objToInteger(rateTypeList[i]));// 利息类型（1固定、2浮动）
				// 只有固定利率需要填写利率的值，浮动利率由接口计算并返回
				if ("1".equals(rateTypeList[i])) {
					fec.setRateVal(StringUtil.stringToBigDecimal(rateValList[i]));
				} else {
					// TODO 调用利率计算接口
				}
				BizFEC dbFec = bizFECProvider.update(fec);
				log.info("保存" + dbFec == null ? "失败！" : "成功！");
				// 6.CBE余额信息表
				log.info("开始保存CBE（余额信息表）... ");
				BizCBE cbe = new BizCBE();
				// cbe.setAcc(acc);//核心账号1
				// cbe.setAcc2(acc2);//核心账号2
				cbe.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
				// cbe.setCbt(cbt);//业务类型CBS ENTRY 类型
				cbe.setCreateBy(createBy);
				cbe.setCreateTime(new Date());//
				cbe.setCur(currencyList[i]);// 币种
				cbe.setDat(new Date());// 发生日期
				cbe.setEnable(BizContant.ENABLE_YES);
				// cbe.setExtid(extid);//退出id
				cbe.setGledat(new Date());// 记账日期
				// cbe.setNam(nam);//入口名称
				cbe.setObjInr(objInr);// 业务表INR
				cbe.setObjType(BizContant.BIZ_TABLE_GRANT);
				// cbe.setOptdat(optdat);//可选日期
				cbe.setRemark("债项发放流程");//
				cbe.setTrninr(dbTrn.getId());// TRN表的INR，标识该发生额来自哪一笔交易
				cbe.setTrntyp(BizContant.TRN_TYPE_GRANT);// 初始交易类型
				cbe.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// amt折算后的金额
				cbe.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种，即外币需要折算成人民币（系统默认赋值人民币）
				cbe.setRelflg("Released");// 授权标志 R: Released P: Preliminary
											// E:// Entered
				BizCBE dbCbe = bizCBEProvider.update(cbe);
				log.info("保存" + dbCbe == null ? "失败！" : "成功！");
				// 7.CBB金额流水信息表
				log.info("开始保存CBB（金额流水）... ");
				BizCBB cbb = new BizCBB();
				cbb.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
				cbb.setBegdat(new Date());// 创建日期
				cbb.setCbc(BizContant.CBC_TYPE);// 金额类型
				cbb.setCbeInr(dbCbe.getId());// CBE的INR,生成该余额的CBE的INR
				// cbb.setComamt(comamt);//提交余额金额
				// cbb.setComcur(comcur);//提交余额币种
				cbb.setCreateBy(createBy);
				cbb.setCreateTime(new Date());
				cbb.setCur(currencyList[i]);// 币种
				cbb.setEnable(BizContant.ENABLE_YES);
				cbb.setEnddat(DateUtil.parseDate(BizContant.END_DATE, "yyyy-MM-dd HH:mm:ss"));// 结束日期
				// cbb.setExtid(extid);//金额种类
				cbb.setObjInr(objInr);// 业务表INR
				cbb.setObjType(BizContant.BIZ_TABLE_GRANT);// 业务类型
				cbb.setRemark("债项发放流程");
				// cbb.setXcoamt(xcoamt);//修改提交余额金额
				// cbb.setXcocur(xcocur);//修改提交余额币种
				cbb.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// 折算后金额
				cbb.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种(默认只折算人民币)
				// 执行保存
				BizCBB dbCbb = bizCBBProvider.update(cbb);
				log.info("保存" + dbCbb == null ? "失败！" : "成功！");
			}
		}

		// 4.多个担保信息
		String guaranTypeStr = StringUtil.objectToString(record.get("guaranTypeList"));
		String guaranPortTypeListStr = StringUtil.objectToString(record.get("guaranPortTypeList"));
		String notClearAmtListStr = StringUtil.objectToString(record.get("notClearAmtList"));
		String clearRatioListStr = StringUtil.objectToString(record.get("clearRatioList"));
		String clearRatioAmtListStr = StringUtil.objectToString(record.get("clearRatioAmtList"));
		String pledgeTypeListStr = StringUtil.objectToString(record.get("pledgeTypeList"));
		// String pledgeNameListStr =
		// StringUtil.objectToString(record.get("pledgeNameList"));
		// String pledgeStateListStr =
		// StringUtil.objectToString(record.get("pledgeStateList"));
		String pledgeNoListStr = StringUtil.objectToString(record.get("pledgeNoList"));
		// String schemeNoListStr =
		// StringUtil.objectToString(record.get("schemeNoList"));
		// 分解成数组
		String[] guaranTypeList = StringUtils.split(guaranTypeStr, '#');
		String[] guaranPortTypeList = StringUtils.split(guaranPortTypeListStr, '#');
		String[] notClearAmtList = StringUtils.split(notClearAmtListStr, '#');
		String[] clearRatioList = StringUtils.split(clearRatioListStr, '#');
		String[] clearRatioAmtList = StringUtils.split(clearRatioAmtListStr, '#');
		String[] pledgeTypeList = StringUtils.split(pledgeTypeListStr, '#');
		// String[] pledgeNameList = StringUtils.split(pledgeNameListStr,
		// '#');
		// String[] pledgeStateList = StringUtils.split(pledgeStateListStr,
		// '#');
		String[] pledgeNoList = StringUtils.split(pledgeNoListStr, '#');
		// String[] schemeNoList = StringUtils.split(schemeNoListStr, '#');
		int guarLen = guaranTypeList.length;
		if (guarLen > 0) {
			// 清空旧的FEC
			log.info("首先删除旧的担保... ");
			int size = bizGuaranteeResultProvider.deleteByGrantCode(grant.getGrantCode());
			log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
		}
		for (int i = 0; i < guarLen; i++) {
			// 创建新的担保信息
			String type = guaranTypeList[i];
			if (StringUtils.isNotEmpty(type)) {
				log.info("开始保存担保信息... ");
				BizGuaranteeResult gResult = new BizGuaranteeResult();
				gResult.setCreateBy(createBy);
				gResult.setCreateTime(new Date());
				gResult.setEnable(BizContant.ENABLE_YES);
				gResult.setGuaranPortType(guaranPortTypeList[i]);
				// 可明确划分份额
				if ("1".equals(guaranPortTypeList[i])) {
					gResult.setClearRatio(StringUtil.stringToBigDecimal(clearRatioList[i]));
					gResult.setClearRatioAmt(StringUtil.stringToBigDecimal(clearRatioAmtList[i]));
				} else {
					gResult.setNotClearAmt(StringUtil.stringToBigDecimal(notClearAmtList[i]));
				}
				gResult.setGuaranType(guaranTypeList[i]);
				// gResult.setSchemeNo(schemeNoList[i]);
				// 判断是否存在押品信息
				if (pledgeNoList != null && i < pledgeNoList.length) {
					gResult.setPledgeNo(pledgeNoList[i]);
					gResult.setPledgeType(pledgeTypeList[i]);
					// gResult.setPledgeName(pledgeNameList[i]);
					// gResult.setPledgeState(pledgeStateList[i]);
				}
				gResult.setGrantCode(grantCode);// 发放流水号码
				//
				BizGuaranteeResult dbRst = bizGuaranteeResultProvider.update(gResult);
				log.info("保存" + dbRst == null ? "失败！" : "成功！");
			}
		}
		// 5.租金保理
		BizRentalFactoring rentalFactoringQuery = new BizRentalFactoring();
		rentalFactoringQuery.setDebtCode(debtCode);
		rentalFactoringQuery.setGrantCode(grantCode);
		BizRentalFactoring bizRentalFactoring = bizRentalFactoringProvider.selectOne(rentalFactoringQuery);
		// 如果专有信息不存在则新建
		if (bizRentalFactoring == null) {
			bizRentalFactoring = new BizRentalFactoring();
		}
		theRentFactoringMap.put("custNo", bizTheRentFactoring.getCustNo());
		List<BizCustomer> bizCustomerList = bizCustomerProvider.queryList(theRentFactoringMap);
		//
		if (bizCustomerList != null && bizCustomerList.size() > 0) {
			BizCustomer bizCustomer = bizCustomerList.get(0);
			bizRentalFactoring.setLesseName(bizCustomer.getCustNameCN());
			bizRentalFactoring.setLesseCode(bizCustomer.getCustNo());
			bizRentalFactoring.setLesseRate(bizCustomer.getCreditRating());
		}
		bizRentalFactoring.setFinancePlatform(bizTheRentFactoring.getFinancePlatform());
		bizRentalFactoring.setBusinessTypes(businessCode);
		bizRentalFactoring.setDebtCode(debtCode);
		bizRentalFactoring.setGrantCode(grantCode);
		bizRentalFactoring.setRepaymentType(StringUtil.objectToString(record.get("repaymentType")));
		// bizRentalFactoring.setDueDate(StringUtil.objectToDate(record.get("dueDate")));
		// bizRentalFactoring.setRfLessor(StringUtil.objectToLong(record.get("rfLessor")));
		// bizRentalFactoring.setLesse(StringUtil.objectToLong(record.get("lesse")));
		// bizRentalFactoring.setValueDate(StringUtil.objectToDate(record.get("valueDate")));
		bizRentalFactoring.setLeasehold(StringUtil.objectToString(record.get("leasehold")));
		bizRentalFactoring.setEnable(BizContant.ENABLE_YES);
		bizRentalFactoring.setIouCode(StringUtil.objectToString(record.get("iouCode")));
		// bizRentalFactoring.setDebtCode(StringUtil.objectToString(record.get("debtNum")));
		bizRentalFactoring.setBizRentalFactoringCode(StringUtil.objectToString(record.get("bizRentalFactoringCode")));

		bizRentalFactoringProvider.update(bizRentalFactoring);
		// 6.政策属性
		BizInterestRate interestRateQuery = new BizInterestRate();
		interestRateQuery.setGrantCode(grantCode);
		interestRateQuery.setDebtNum(debtCode);
		BizInterestRate bizInterestRate = bizInterestRateProvider.selectOne(interestRateQuery);
		if (bizInterestRate == null) {
			bizInterestRate = new BizInterestRate();
		}
		bizInterestRate.setEnable(BizContant.ENABLE_YES);
		bizInterestRate.setAlleviationLoan(StringUtil.objectToLong(record.get("alleviationLoan")));
		bizInterestRate.setBusinessContractAmount(StringUtil.stringToBigDecimal(record.get("businessContractAmount")));
		bizInterestRate.setBusinessContractCode(StringUtil.objectToString(record.get("businessContractCode")));
		bizInterestRate.setBusinessType(StringUtil.objectToString(record.get("businessType")));
		bizInterestRate.setBusinessContractDate(StringUtil.objectToDate(record.get("businessContractDate")));
		bizInterestRate.setCompare(StringUtil.objectToString(record.get("compare")));
		bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
		bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
		bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
		bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("policyAttributeClassify")));
		bizInterestRate.setDebtNum(debtCode);
		bizInterestRate.setGrantCode(grantCode);
		bizInterestRate.setEmergingIndustryClassify(StringUtil.objectToString(record.get("emergingIndustryClassify")));

		bizInterestRate.setInOverseasTo(StringUtil.objectToString(record.get("inOverseasTo")));
		bizInterestRate.setTraneFinanceBusiness(StringUtil.objectToLong(record.get("traneFinanceBusiness")));
		bizInterestRate.setPolicyLendingStatus(StringUtil.objectToLong(record.get("policyLendingStatus")));
		bizInterestRate.setPolicyDescription(StringUtil.objectToString(record.get("policyDescription")));
		bizInterestRate.setLoanNumberAvailable(StringUtil.objectToLong(record.get("loanNumberAvailable")));
		bizInterestRate.setCreditLoanNum(StringUtil.objectToString(record.get("creditLoanNum")));
		bizInterestRate.setNaturnLoan(StringUtil.objectToLong(record.get("naturnLoan")));
		bizInterestRate.setTwoHeadTaller(StringUtil.objectToLong(record.get("twoHeadTaller")));
		bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
		bizInterestRate.setMediumEnterpriseLoans(StringUtil.objectToLong(record.get("mediumEnterpriseLoans")));
		bizInterestRate.setShipFinance(StringUtil.objectToLong(record.get("shipFinance")));
		bizInterestRate.setTyoesIndustrial(StringUtil.objectToLong(record.get("tyoesIndustrial")));
		bizInterestRate.setIndustrialTransformation(StringUtil.objectToLong(record.get("industrialTransformation")));
		bizInterestRate.setStrategicEmerg(StringUtil.objectToLong(record.get("strategicEmerg")));
		bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
		bizInterestRate.setTradeInterestRate(StringUtil.objToInteger(record.get("tradeInterestRate")));
		bizInterestRate.setPolicyAttributeState(StringUtil.objToInteger(record.get("policyAttributeState")));
		bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
		bizInterestRate.setLoanServicePeopleNum(StringUtil.objectToLong(record.get("loanServicePeopleNum")));
		bizInterestRate.setLoanFunResource(StringUtil.objectToString(record.get("loanFunResource")));
		bizInterestRate.setPovertyProperty(StringUtil.objectToString(record.get("povertyProperty")));
		bizInterestRate.setPovertyAddress(StringUtil.objectToString(record.get("povertyAddress")));
		bizInterestRate.setPovertySort(StringUtil.objectToString(record.get("povertySort")));
		bizInterestRate.setInnovativeBusinessType(StringUtil.objectToString(record.get("innovativeBusinessType")));
		bizInterestRate.setIs421(StringUtil.objectToLong(record.get("is421")));
		bizInterestRate.setLoanDomain(StringUtil.objectToString(record.get("loanDomain")));
		bizInterestRate.setImportExportGoodsService(StringUtil.objectToString(record.get("importExportGoodsService")));
		bizInterestRate
				.setInvestmentLoanCkassifcation(StringUtil.objectToString(record.get("investmentLoanCkassifcation")));
		bizInterestRate.setIsSyndicated(StringUtil.objToInteger(record.get("isSyndicated")));
		bizInterestRate.setIsSyndicatedAgency(StringUtil.objToInteger(record.get("isSyndicatedAgency")));
		bizInterestRate.setSyndicatedStatus(StringUtil.objectToLong(record.get("syndicatedStatus")));
		bizInterestRate.setProjectName(StringUtil.objectToString(record.get("projectName")));
		bizInterestRate.setBackgroundNationality(StringUtil.objectToString(record.get("backgroundNationality")));
		bizInterestRate.setIndustryInvestment(StringUtil.objectToString(record.get("industryInvestment")));
		// 政策性属性分类
		bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("traneFinanceTypeList")));
		bizInterestRateProvider.update(bizInterestRate);

		// 7还款计划
		BizRepaymentPlan repaymentPlanQuery = new BizRepaymentPlan();
		repaymentPlanQuery.setGrantCode(grantCode);
		repaymentPlanQuery.setDebtCode(debtCode);
		BizRepaymentPlan bizRepaymentPlan = bizRepaymentPlanProvider.selectOne(repaymentPlanQuery);
		if (bizRepaymentPlan == null) {
			bizRepaymentPlan = new BizRepaymentPlan();
		}
		bizRepaymentPlan.setEnable(BizContant.ENABLE_YES);
		bizRepaymentPlan.setDebtCode(debtCode);
		bizRepaymentPlan.setFirstDay(StringUtil.objectToDate(record.get("firstDay")));
		bizRepaymentPlan.setFirstRepayDay(StringUtil.objectToDate(record.get("fristRepayDate")));
		bizRepaymentPlan.setLastRepaymentDay(StringUtil.objectToDate(record.get("lastRepayDay")));
		bizRepaymentPlan.setRepaymentCostMode(StringUtil.objectToLong(record.get("repaymentCostMode")));
		bizRepaymentPlan.setCaclWay(StringUtil.objectToLong(record.get("caclWay")));
		bizRepaymentPlan.setGrantCode(grantCode);
		bizRepaymentPlan.setPayCny(currencyList[0]);
		BizRepaymentPlan dbRst = bizRepaymentPlanProvider.update(bizRepaymentPlan);

		// 获取还本信息和还息信息
		String payDateStr = StringUtil.objectToString(record.get("payDateList"));
		String principalAmyStr = StringUtil.objectToString(record.get("principalAmyList"));
		String clearinterestDateRatioStr = StringUtil.objectToString(record.get("clearinterestDateRatioList"));
		// 分解成数组
		String[] payDateList = StringUtils.split(payDateStr, '#');
		String[] principalAmyList = StringUtils.split(principalAmyStr, '#');
		String[] clearinterestDateRatioList = StringUtils.split(clearinterestDateRatioStr, '#');
		// 清空旧的还本计划
		log.info("首先删除旧的还本计划... ");
		int psize = bizRepaymentPricinalPlanProvider.deleteByGrantCode(grantCode, debtCode);
		log.info(psize > 0 ? "成功删除:" + psize + "个!" : "删除失败!");
		for (int i = 0; i < payDateList.length; i++) {
			// 创建新的还本计划
			String type = payDateList[i];
			if (StringUtils.isNotEmpty(type)) {
				log.info("开始保存还本计划信息... ");
				BizRepaymentPricinalPlan bizRepaymentPricinalPlan = new BizRepaymentPricinalPlan();
				bizRepaymentPricinalPlan.setEnable(BizContant.ENABLE_YES);
				bizRepaymentPricinalPlan.setPayDate(StringUtil.objectToDate(payDateList[i]));
				bizRepaymentPricinalPlan.setPrincipalAmy(StringUtil.stringToBigDecimal(principalAmyList[i]));
				bizRepaymentPricinalPlan.setDebtCode(debtCode);
				bizRepaymentPricinalPlan.setGrantCode(grantCode);
				BizRepaymentPricinalPlan rpRst = bizRepaymentPricinalPlanProvider.update(bizRepaymentPricinalPlan);
				log.info("保存" + dbRst == null ? "失败！" : "成功！");
			}
		}
		// 清空旧的还息计划
		log.info("首先删除旧的还息计划... ");
		int delsize = bizRepaymentLoanPlanProvider.deleteByGrantCode(grantCode, debtCode);
		log.info(delsize > 0 ? "成功删除:" + delsize + "个!" : "删除失败!");
		for (int i = 0; i < clearinterestDateRatioList.length; i++) {
			// 创建新的还息计划
			String type = clearinterestDateRatioList[i];
			if (StringUtils.isNotEmpty(type)) {
				log.info("开始保存还息计划信息... ");
				BizRepaymentLoanPlan bizRepaymentLoanPlan = new BizRepaymentLoanPlan();
				bizRepaymentLoanPlan.setEnable(BizContant.ENABLE_YES);
				bizRepaymentLoanPlan.setInterestDate(StringUtil.objectToDate(clearinterestDateRatioList[i]));
				bizRepaymentLoanPlan.setDebtCode(debtCode);
				bizRepaymentLoanPlan.setGrantCode(grantCode);
				BizRepaymentLoanPlan rpRst = bizRepaymentLoanPlanProvider.update(bizRepaymentLoanPlan);
				log.info("保存" + dbRst == null ? "失败！" : "成功！");
			}
		}
		// 担保合同
		String bizGuaranteeContractListStr = StringUtil.objectToString(record.get("bizGuaranteeContractList"));
		if (StringUtils.isNotBlank(bizGuaranteeContractListStr)) {
			System.out.println(bizGuaranteeContractListStr);
			// 清空旧的担保合同
			log.info("首先删除旧的担保合同... ");
			int size = bizGuaranteeContractProvider.deleteByGrantCode(grantCode, debtCode);
			log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
			// 分解成数组
			String[] bizGuaranteeContractList = StringUtils.split(bizGuaranteeContractListStr, '$');
			for (int i = 0; i < bizGuaranteeContractList.length; i++) {
				String item = bizGuaranteeContractList[i];
				BizGuaranteeContract bizGuaranteeContract = convertStrToModel(item);
				System.out.println("------------" + item);
				BizGuaranteeContract bizGuaranteeContract1 = new BizGuaranteeContract();
				bizGuaranteeContract1.setEnable(BizContant.ENABLE_YES);
				bizGuaranteeContract1.setGrantCode(grantCode);
				bizGuaranteeContract1.setDebtCode(debtCode);
				bizGuaranteeContract1.setGuarNo(bizGuaranteeContract.getGuarNo());
				bizGuaranteeContract1.setGuarManualNo(bizGuaranteeContract.getGuarManualNo());
				bizGuaranteeContract1.setGuarCustName(bizGuaranteeContract.getGuarCustName());
				bizGuaranteeContract1.setGuarCustNo(bizGuaranteeContract.getGuarCustNo());
				bizGuaranteeContract1.setGuarAmount(bizGuaranteeContract.getGuarAmount());
				bizGuaranteeContract1.setCurrency(bizGuaranteeContract.getCurrency());
				bizGuaranteeContract1.setStartDate(bizGuaranteeContract.getStartDate());
				bizGuaranteeContract1.setDueDate(bizGuaranteeContract.getDueDate());
				bizGuaranteeContract1.setTerm(bizGuaranteeContract.getTerm());
				bizGuaranteeContract1.setTermUnit(bizGuaranteeContract.getTermUnit());
				bizGuaranteeContract1.setWarrandice(bizGuaranteeContract.getWarrandice());
				bizGuaranteeContract1.setGuarContType(bizGuaranteeContract.getGuarContType());
				bizGuaranteeContract1.setGuarContState(bizGuaranteeContract.getGuarContState());
				bizGuaranteeContract1.setGuarMaxAmt(bizGuaranteeContract.getGuarMaxAmt());
				bizGuaranteeContract1.setAgent(bizGuaranteeContract.getAgent());
				bizGuaranteeContract1.setAgencies(bizGuaranteeContract.getAgencies());
				bizGuaranteeContractProvider.update(bizGuaranteeContract1);
			}
		}

		// 授信信息
		if (record.get("custUserLetter") != null) {
			// 创建新的用户主体授信
			String json = JSON.toJSONString(record.get("custUserLetter"));
			List<BizCustomer> bizCustomerLisbt = JSON.parseArray(json, BizCustomer.class);
			int i = 0;
			for (BizCustomer cu : bizCustomerLisbt) {
				// 保存用户主体授信信息表
				List<BizCreditLines> bizProductLinesTypeList = cu.getCreditLinesList();
				Long bizCustomerId = cu.getId();
				// 判断非空验证
				if (CollectionUtils.isNotEmpty(bizProductLinesTypeList)) {
					if (i == 0) {
						// 清空旧的用户主体授信
						log.info("首先删除旧的用户主体授信... ");
						int size = bizCreditLinesProvider.deleteByGrantId(grant.getId_(), grant.getDebtCode());
						log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
					}
					for (BizCreditLines li : bizProductLinesTypeList) {
						li.setCustomerId(bizCustomerId);
						li.setDebtCode(debtCode);
						li.setEnable(BizContant.ENABLE_YES);
						li.setCustNo(cu.getCustNo());
						li.setObjtyp(BizContant.BIZ_TABLE_GRANT);
						li.setObjinr(StringUtil.objectToString(objInr));
						bizCreditLinesProvider.update(li);
					}
					i++;
				}
			}
		}
		// 如果是暂存的内容进行的提交。那么提交后删除暂存文件
		Long tempId = StringUtil.objectToLong(record.get("tempId"));
		if (tempId != null) {
			log.info("开始删除暂存文件信息... ");
			boolean success = bizTempProvider.delById(tempId);
			log.info("删除暂存" + (success ? "失败！" : "成功！"));
		} else {
			log.info("开始删除数据迁移所有的暂存文件... ");
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("bizcode", grantCode);
			bizTempProvider.deleteByParams(query);
			log.info("删除暂存成功！");
		}
		redisHelper.deleteHistoryState("iBase4J:bizDebtGrant*");
		return dbGrant;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BizDebtGrant update(Map<String, Object> record) throws Exception {
		log.info("开始修改债项发放申请后重新提交...");
		try{
			for (String key : record.keySet()) {
				System.out.println("key= " + key + " and value= " + record.get(key));
			}
			Integer isRate = StringUtil.objToInteger(record.get("isRate"));// 有无费用1有,2无
			Long createBy = StringUtil.objectToLong(record.get("createBy"));
			String grantCode = StringUtil.objectToString(record.get("grantCode"));
			String debtCode = StringUtil.objectToString(record.get("debtCode"));
			String businessCode = StringUtil.objectToString(record.get("businessCode"));
			String currencyListStr = StringUtil.objectToString(record.get("currencyList"));
			String paymentAmtListStr = StringUtil.objectToString(record.get("paymentAmtList"));
			// 旧的发放申请编码
			String origCode = StringUtil.objectToString(record.get("originalGrantCode"));
			// 发放币种和金额
			String[] currencyList = StringUtils.split(currencyListStr, '#');
			String[] paymentAmtList = StringUtils.split(paymentAmtListStr, '#');
			// 查询租金保理专有信息
			Map<String, Object> theRentFactoringMap = new HashMap<String, Object>();
			theRentFactoringMap.put("debtCode", debtCode);
			BizTheRentFactoring bizTheRentFactoring = bizTheRentFactoringProvider.queryList(theRentFactoringMap).get(0);
			// 1.发放申请
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("grantCode", grantCode);
			params.put("debtCode", debtCode);
			List<BizDebtGrant> resultList = this.queryList(params);
			BizDebtGrant grant = null;
			if (resultList != null && resultList.size() > 0) {
				grant = resultList.get(0);
			} else {
				throw new RuntimeException("发放编号不存在或编号为空！");
			}
			// 查询方案
			Map<String, Object> debtMap2 = new HashMap<>();
			debtMap2.put("debtCode", debtCode);
			BizDebtSummary summary = bizDebtSummaryProvider.queryList(debtMap2).get(0);
			// 把页面修改的数据覆盖到发放条件对象中
			grant.setBusinessTypes(businessCode);
			grant.setCreateBy(createBy);
			grant.setCreateTime(new Date());
			grant.setProperInfo(StringUtil.objectToLong(record.get("properInfo")));// 产品专有信息外键
			grant.setDebtCode(debtCode);
			grant.setGrantAmt(StringUtil.stringToBigDecimal(paymentAmtList[0]));// 发放金额,获取第一个索引处的金额为发放金额
			grant.setCurrency(currencyList[0]);// 发放币种
			grant.setProposer(summary.getProposer());
			grant.setBusinessName(StringUtil.objectToString(record.get("businessName")));// 产品名称
			grant.setBusinessTypeName(summary.getProjectName());
			grant.setEnable(BizContant.ENABLE_YES);
			grant.setProcessStatus(BizContant.ENABLE_YES);
			// 判断应该是什么状态
			if (StringUtils.isNotBlank(origCode)) {
				log.warn("变更流程中的重新提交将驳回状态修改为变更审核中...");
				grant.setGrantStatus(BizStatus.GRANCHAT);// 状态为变更审核中
			} else {
				log.warn("重新提交将驳回状态修改为审核中...");
				grant.setGrantStatus(BizStatus.GRANNEAT);// 状态为审核中
			}
			grant.setGracePeriod(StringUtil.objToInteger(record.get("gracePeriod")));//
			grant.setGrantCode(grantCode);// 流水号
			grant.setRemark(StringUtil.objectToString(record.get("businessName")) + "进行发放");
			grant.setScopeBusinPeriod(StringUtil.objectToString(record.get("scopeBusinPeriod")));
			grant.setSendDate(StringUtil.objectToDate(record.get("sendDate")));
			grant.setEndDate(StringUtil.objectToDate(record.get("sendEndDate")));
			grant.setToierancePeriod(isRate);// 容忍期限存入了是否有费用字段
			grant.setInstitutionCode(StringUtil.objectToString(record.get("deptCode")));// 经办机构
			grant.setDescriptionProgramQuoqate(StringUtil.objectToString(record.get("descriptionProgramQuoqate")));
			grant.setDescriptionRateRules(StringUtil.objectToString(record.get("descriptionRateRules")));
			grant.setOriginalGrantCode(StringUtil.objectToString(record.get("originalGrantCode")));
			grant.setIouCode(StringUtil.objectToString(record.get("iouCode")));
			BizDebtGrant dbGrant = update(grant);
			log.info("修改" + dbGrant == null ? "失败！" : "成功！");
			Long objInr = dbGrant.getId();
	
			// 2.多个收费信息
			String chargeStr = StringUtil.objectToString(record.get("chargeTypeList"));
			String chargeVals = StringUtil.objectToString(record.get("rateValueList"));
			String[] chargeList = StringUtils.split(chargeStr, '#');
			String[] chargeValues = StringUtils.split(chargeVals, '#');
			int chargeLen = chargeList.length;
			// 如果有新增的费用类型或没有费用，则删除原来的费用fep
			if (chargeLen > 0 || isRate == 2) {
				// 首先删除就的FEP数据
				log.info("首先删除旧的费用表(FEP)的数据...");
				int size = bizFEPProvider.deleteByGrantCode(grant.getId_());
				log.info(size > 0 ? "成功删除:" + size + "条" : "删除失败");
			}
			if (chargeLen > 0) {
				for (int i = 0; i < chargeList.length; i++) {
					// 然后新增新的费用（FEP）
					String charge = chargeList[i];
					if (StringUtils.isNotEmpty(charge)) {
						log.info("开始保存FEP（收息收费表）... ");
						BizFEP fep = new BizFEP();
						fep.setCreateBy(createBy);
						fep.setCreateTime(new Date());
						fep.setEnable(BizContant.ENABLE_YES);
						fep.setFeecod(charge);// 费用代码
						fep.setObjInr(objInr);// 业务表INR(XXD的ID)
						fep.setObjTyp(BizContant.BIZ_TABLE_GRANT);
						fep.setRateType(charge);
						fep.setRateValue(StringUtil.stringToBigDecimal(chargeValues[i]));
						fep.setRemark("发放费用存盘");
						// 执行保存
						BizFEP dbFep = bizFEPProvider.update(fep);
						log.info("保存" + dbFep == null ? "失败！" : "成功！");
					}
				}
			}
	
			// 3.业务流水
			log.info("开始修改TRN（业务流水表）... ");
			BizTRN trnQuery = new BizTRN();
			trnQuery.setObjtyp(BizContant.BIZ_TABLE_GRANT);//
			trnQuery.setObjinr(grant.getId());
			trnQuery.setInifrm(grantCode);
			BizTRN trn = bizTRNProvider.selectOne(trnQuery);
			// 如果不存在TRN，则新建
			if (trn == null) {
				trn = new BizTRN();
			}
			// 修改trn
			trn.setCreateBy(createBy);
			trn.setCreateTime(new Date());
			trn.setEnable(BizContant.ENABLE_YES);
			// trn.setEtyextkey(etyextkey);
			trn.setExedat(new Date());// 执行日期
			trn.setInidattim(new Date());// 存盘时间
			trn.setInifrm(grantCode);// 交易代码(流水号)自定义
			trn.setIninam("债项发放申请流程");// 交易名称(业务名称)
			trn.setIniusr(createBy);// 登录柜员
			trn.setObjinr(objInr);// 业务表INR(XXD的ID)
			trn.setObjnam(StringUtil.objectToString(record.get("businessName")) + "进行发放");
			trn.setObjtyp(BizContant.BIZ_TABLE_GRANT);//
			trn.setOwnref(StringUtil.objectToString(record.get("debtCode")));// 业务编号
			trn.setBusinessTypes(businessCode);
			trn.setBchkeyinr(StringUtil.objectToString(record.get("deptCode")));// 经办机构
			// TODO 应该匹配哪些属性？
			// trn.setRelamt(relamt);//授权金额
			trn.setRelcur(summary.getaCurrrency());// 授权币种
			trn.setXrecurblk(summary.getOc());// 可用货币
			trn.setReloricur(summary.getMpc());// 主币种
			trn.setReloriamt(StringUtil.stringToBigDecimal(record.get("grantAmt")));// 金额
			// 重新提交状态为审核中
			trn.setRelres(String.valueOf(BizStatus.GRANNEAT));// 状态为审核中
	
			BizTRN dbTrn = bizTRNProvider.update(trn);
			log.info("修改" + dbTrn == null ? "失败！" : "成功！");
			// 3.多个利率信息
			String calcDaysListStr = StringUtil.objectToString(record.get("calcDaysList"));
			String chgCycleUnitListStr = StringUtil.objectToString(record.get("chgCycleUnitList"));
			String convertedPriceListStr = StringUtil.objectToString(record.get("convertedPriceList"));
	
			String floatDirectioinListStr = StringUtil.objectToString(record.get("floatDirectioinList"));
			String floatingRateListStr = StringUtil.objectToString(record.get("floatingRateList"));
			String floatModeListStr = StringUtil.objectToString(record.get("floatModeList"));
			String irBkListStr = StringUtil.objectToString(record.get("irBkList"));
			String overdueIncrRatioListStr = StringUtil.objectToString(record.get("overdueIncrRatioList"));
			String overdueRateCalcModeListStr = StringUtil.objectToString(record.get("overdueRateCalcModeList"));
	
			String paymentModeListStr = StringUtil.objectToString(record.get("paymentModeList"));
			String rateValListStr = StringUtil.objectToString(record.get("rateValList"));
			String rateTypeListStr = StringUtil.objectToString(record.get("rateTypeList"));// 利率类型
			String varCycleListStr = StringUtil.objectToString(record.get("varCycleList"));
			// 利率信息
			String[] calcDaysList = StringUtils.split(calcDaysListStr, '#');
			String[] chgCycleUnitList = StringUtils.split(chgCycleUnitListStr, '#');
			String[] convertedPriceList = StringUtils.split(convertedPriceListStr, '#');
	
			String[] floatDirectioinList = StringUtils.split(floatDirectioinListStr, '#');
			String[] floatingRateList = StringUtils.split(floatingRateListStr, '#');
			String[] floatModeList = StringUtils.split(floatModeListStr, '#');
			String[] irBkList = StringUtils.split(irBkListStr, '#');
			String[] overdueIncrRatioList = StringUtils.split(overdueIncrRatioListStr, '#');
			String[] overdueRateCalcModeList = StringUtils.split(overdueRateCalcModeListStr, '#');
			String[] paymentModeList = StringUtils.split(paymentModeListStr, '#');
			String[] rateValList = StringUtils.split(rateValListStr, '#');
			String[] varCycleList = StringUtils.split(varCycleListStr, '#');
			String[] rateTypeList = StringUtils.split(rateTypeListStr, '#');
			// 页面输入的币种信息
			int cyLen = currencyList.length;
			// 如果修改了币种，则首先删除之前旧的币种对应信息，包含fec,cbb,cbe
			if (cyLen > 0) {
				// 清空旧的FEC
				log.info("首先删除旧的FEC（利率表）... ");
				int size = bizFECProvider.deleteByGrantId(grant.getId());
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
				// 清空就的CBB
				log.info("首先删除旧的CBB（金额流水信息表）... ");
				size = bizCBBProvider.deleteByGrant(grant.getId());
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
				// 清空就的CBE
				log.info("首先删除旧的CBE（余额信息表）... ");
				size = bizCBEProvider.deleteByGrant(grant.getId());
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
			}
			// 分拆数组，组合fec
			for (int i = 0; i < cyLen; i++) {
				// 新建FEC
				String currency = currencyList[i];
				if (StringUtils.isNotEmpty(currency)) {
					log.info("开始保存FEC（利率表）... ");
					BizFEC fec = new BizFEC();
					fec.setType(StringUtil.objToInteger(rateTypeList[i]));
					// 浮动利率必填项,固定利率不能填写
					if ("2".equals(rateTypeList[i])) {
						BizGuaranteeType guaranteeType = getBizGuaranteeType(irBkList[i]);
						fec.setIrBk(guaranteeType.getSrv1());// 利率基准
						if (guaranteeType != null) {
							fec.setTerm(guaranteeType.getSrv2());// 利率期限
						}
						fec.setFloatDirectioin(floatDirectioinList[i]);// 利率浮动方向
						fec.setFloatMode(floatModeList[i]);// 利率浮动方式
						fec.setFloatingRate(StringUtil.stringToBigDecimal(floatingRateList[i]));// 浮动率
						fec.setVarCycle(StringUtil.objToInteger(varCycleList[i]));// 利率变动周期
						fec.setChgCycleUnit(StringUtil.objToInteger(chgCycleUnitList[i]));// 利率变动周期单位
					}
					fec.setCalcDays(StringUtil.objToInteger(calcDaysList[i]));
					fec.setConvertedPrice(StringUtil.stringToBigDecimal(convertedPriceList[i]));
					fec.setCreateBy(createBy);
					fec.setCreateTime(new Date());
					fec.setCurrency(currency);
					fec.setEnable(BizContant.ENABLE_YES);
					fec.setObjInr(objInr);
					fec.setObjTyp(BizContant.BIZ_TABLE_GRANT);
					fec.setOverdueIncrRatio(StringUtil.stringToBigDecimal(overdueIncrRatioList[i]));
					fec.setOverdueRateCalcMode(overdueRateCalcModeList[i]);
					fec.setPaymentAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));
					fec.setPaymentMode(paymentModeList[i]);
					fec.setRemark("债项发放流程");
					// fec.setRules(rules);//
					fec.setType(StringUtil.objToInteger(rateTypeList[i]));// 利息类型（1固定、2浮动）
					// 只有固定利率需要填写利率的值，浮动利率由接口计算并返回
					if ("1".equals(rateTypeList[i])) {
						fec.setRateVal(StringUtil.stringToBigDecimal(rateValList[i]));
					} else {
						// TODO 调用利率计算接口
					}
					BizFEC dbFec = bizFECProvider.update(fec);
					log.info("保存" + dbFec == null ? "失败！" : "成功！");
					// 6.CBE余额信息表
					log.info("开始保存CBE（余额信息表）... ");
					BizCBE cbe = new BizCBE();
					// cbe.setAcc(acc);//核心账号1
					// cbe.setAcc2(acc2);//核心账号2
					cbe.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
					// cbe.setCbt(cbt);//业务类型CBS ENTRY 类型
					cbe.setCreateBy(createBy);
					cbe.setCreateTime(new Date());//
					cbe.setCur(currencyList[i]);// 币种
					cbe.setDat(new Date());// 发生日期
					cbe.setEnable(BizContant.ENABLE_YES);
					// cbe.setExtid(extid);//退出id
					cbe.setGledat(new Date());// 记账日期
					// cbe.setNam(nam);//入口名称
					cbe.setObjInr(objInr);// 业务表INR
					cbe.setObjType(BizContant.BIZ_TABLE_GRANT);
					// cbe.setOptdat(optdat);//可选日期
					cbe.setRemark("债项发放流程");//
					cbe.setTrninr(dbTrn.getId());// TRN表的INR，标识该发生额来自哪一笔交易
					cbe.setTrntyp(BizContant.TRN_TYPE_GRANT);// 初始交易类型
					cbe.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// amt折算后的金额
					cbe.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种，即外币需要折算成人民币（系统默认赋值人民币）
					cbe.setRelflg("Released");// 授权标志 R: Released P: Preliminary //
												// E: // Entered
					BizCBE dbCbe = bizCBEProvider.update(cbe);
					log.info("保存" + dbCbe == null ? "失败！" : "成功！");
					// 7.CBB金额流水信息表
					log.info("开始保存CBB（金额流水）... ");
					BizCBB cbb = new BizCBB();
					cbb.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
					cbb.setBegdat(new Date());// 创建日期
					cbb.setCbc(BizContant.CBC_TYPE);// 金额类型
					cbb.setCbeInr(dbCbe.getId());// CBE的INR,生成该余额的CBE的INR
					// cbb.setComamt(comamt);//提交余额金额
					// cbb.setComcur(comcur);//提交余额币种
					cbb.setCreateBy(createBy);
					cbb.setCreateTime(new Date());
					cbb.setCur(currencyList[i]);// 币种
					cbb.setEnable(BizContant.ENABLE_YES);
					cbb.setEnddat(DateUtil.parseDate(BizContant.END_DATE, "yyyy-MM-dd HH:mm:ss"));// 结束日期
					// cbb.setExtid(extid);//金额种类
					cbb.setObjInr(objInr);// 业务表INR
					cbb.setObjType(BizContant.BIZ_TABLE_GRANT);// 业务类型
					cbb.setRemark("债项发放流程");
					// cbb.setXcoamt(xcoamt);//修改提交余额金额
					// cbb.setXcocur(xcocur);//修改提交余额币种
					cbb.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// 折算后金额
					cbb.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种(默认只折算人民币)
					// 执行保存
					BizCBB dbCbb = bizCBBProvider.update(cbb);
					log.info("保存" + dbCbb == null ? "失败！" : "成功！");
				}
			}
	
			// 4.多个担保信息
			String guaranTypeStr = StringUtil.objectToString(record.get("guaranTypeList"));
			String guaranPortTypeListStr = StringUtil.objectToString(record.get("guaranPortTypeList"));
			String notClearAmtListStr = StringUtil.objectToString(record.get("notClearAmtList"));
			String clearRatioListStr = StringUtil.objectToString(record.get("clearRatioList"));
			String clearRatioAmtListStr = StringUtil.objectToString(record.get("clearRatioAmtList"));
			String pledgeTypeListStr = StringUtil.objectToString(record.get("pledgeTypeList"));
			// String pledgeNameListStr =
			// StringUtil.objectToString(record.get("pledgeNameList"));
			// String pledgeStateListStr =
			// StringUtil.objectToString(record.get("pledgeStateList"));
			String pledgeNoListStr = StringUtil.objectToString(record.get("pledgeNoList"));
			// String schemeNoListStr =
			// StringUtil.objectToString(record.get("schemeNoList"));
			// 分解成数组
			String[] guaranTypeList = StringUtils.split(guaranTypeStr, '#');
			String[] guaranPortTypeList = StringUtils.split(guaranPortTypeListStr, '#');
			String[] notClearAmtList = StringUtils.split(notClearAmtListStr, '#');
			String[] clearRatioList = StringUtils.split(clearRatioListStr, '#');
			String[] clearRatioAmtList = StringUtils.split(clearRatioAmtListStr, '#');
			String[] pledgeTypeList = StringUtils.split(pledgeTypeListStr, '#');
			// String[] pledgeNameList = StringUtils.split(pledgeNameListStr,
			// '#');
			// String[] pledgeStateList = StringUtils.split(pledgeStateListStr,
			// '#');
			String[] pledgeNoList = StringUtils.split(pledgeNoListStr, '#');
			// String[] schemeNoList = StringUtils.split(schemeNoListStr, '#');
			int gtLen = guaranTypeList.length;
			// 删除旧的担保信息
			if (gtLen > 0) {
				// 清空旧的数据
				log.info("首先删除旧的担保... ");
				int size = bizGuaranteeResultProvider.deleteByGrantCode(grant.getGrantCode());
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
			}
			// 然后添加新的担保信息
			for (int i = 0; i < gtLen; i++) {
				// 创建新的担保信息
				String type = guaranTypeList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存担保信息... ");
					BizGuaranteeResult gResult = new BizGuaranteeResult();
					gResult.setCreateBy(createBy);
					gResult.setCreateTime(new Date());
					gResult.setEnable(BizContant.ENABLE_YES);
					gResult.setGuaranPortType(guaranPortTypeList[i]);
					// 可明确划分份额
					if ("1".equals(guaranPortTypeList[i])) {
						gResult.setClearRatio(StringUtil.stringToBigDecimal(clearRatioList[i]));
						gResult.setClearRatioAmt(StringUtil.stringToBigDecimal(clearRatioAmtList[i]));
					} else {
						gResult.setNotClearAmt(StringUtil.stringToBigDecimal(notClearAmtList[i]));
					}
					gResult.setGuaranType(guaranTypeList[i]);
					// gResult.setSchemeNo(schemeNoList[i]);
					// 判断是否存在押品信息
					if (pledgeNoList != null && i < pledgeNoList.length) {
						gResult.setPledgeNo(pledgeNoList[i]);
						gResult.setPledgeType(pledgeTypeList[i]);
						// gResult.setPledgeName(pledgeNameList[i]);
						// gResult.setPledgeState(pledgeStateList[i]);
					}
					gResult.setGrantCode(grantCode);// 发放流水号码
					//
					BizGuaranteeResult dbRst = bizGuaranteeResultProvider.update(gResult);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			// 5.租金保理
			BizRentalFactoring rentalFactoringQuery = new BizRentalFactoring();
			rentalFactoringQuery.setDebtCode(debtCode);
			rentalFactoringQuery.setGrantCode(grantCode);
			BizRentalFactoring bizRentalFactoring = bizRentalFactoringProvider.selectOne(rentalFactoringQuery);
			// 如果专有信息不存在则新建
			if (bizRentalFactoring == null) {
				bizRentalFactoring = new BizRentalFactoring();
			}
			theRentFactoringMap.put("custNo", bizTheRentFactoring.getCustNo());
			List<BizCustomer> bizCustomerList = bizCustomerProvider.queryList(theRentFactoringMap);
			//
			if (bizCustomerList != null && bizCustomerList.size() > 0) {
				BizCustomer bizCustomer = bizCustomerList.get(0);
				bizRentalFactoring.setLesseName(bizCustomer.getCustNameCN());
				bizRentalFactoring.setLesseCode(bizCustomer.getCustNo());
				bizRentalFactoring.setLesseRate(bizCustomer.getCreditRating());
			}
			bizRentalFactoring.setFinancePlatform(bizTheRentFactoring.getFinancePlatform());
			bizRentalFactoring.setBusinessTypes(businessCode);
			bizRentalFactoring.setDebtCode(debtCode);
			bizRentalFactoring.setGrantCode(grantCode);
			bizRentalFactoring.setRenFileName(StringUtil.objectToString(record.get("renFileName")));
			bizRentalFactoring.setRepaymentType(StringUtil.objectToString(record.get("repaymentType")));
			// bizRentalFactoring.setDueDate(StringUtil.objectToDate(record.get("dueDate")));
			// bizRentalFactoring.setRfLessor(StringUtil.objectToLong(record.get("rfLessor")));
			// bizRentalFactoring.setLesse(StringUtil.objectToLong(record.get("lesse")));
			// bizRentalFactoring.setValueDate(StringUtil.objectToDate(record.get("valueDate")));
			bizRentalFactoring.setLeasehold(StringUtil.objectToString(record.get("leasehold")));
			bizRentalFactoring.setEnable(BizContant.ENABLE_YES);
			bizRentalFactoring.setIouCode(StringUtil.objectToString(record.get("iouCode")));
			// bizRentalFactoring.setDebtCode(StringUtil.objectToString(record.get("debtNum")));
			bizRentalFactoring.setBizRentalFactoringCode(StringUtil.objectToString(record.get("bizRentalFactoringCode")));
	
			bizRentalFactoringProvider.update(bizRentalFactoring);
			// 6.政策属性
			BizInterestRate interestRateQuery = new BizInterestRate();
			interestRateQuery.setGrantCode(grantCode);
			interestRateQuery.setDebtNum(debtCode);
			BizInterestRate bizInterestRate = bizInterestRateProvider.selectOne(interestRateQuery);
			if (bizInterestRate == null) {
				bizInterestRate = new BizInterestRate();
			}
			bizInterestRate.setEnable(BizContant.ENABLE_YES);
			bizInterestRate.setAlleviationLoan(StringUtil.objectToLong(record.get("alleviationLoan")));
			bizInterestRate.setBusinessContractAmount(StringUtil.stringToBigDecimal(record.get("businessContractAmount")));
			bizInterestRate.setBusinessContractCode(StringUtil.objectToString(record.get("businessContractCode")));
			bizInterestRate.setBusinessType(StringUtil.objectToString(record.get("businessType")));
			bizInterestRate.setBusinessContractDate(StringUtil.objectToDate(record.get("businessContractDate")));
			bizInterestRate.setCompare(StringUtil.objectToString(record.get("compare")));
			bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
			bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
			bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
			bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("policyAttributeClassify")));
			bizInterestRate.setDebtNum(debtCode);
			bizInterestRate.setGrantCode(grantCode);
			bizInterestRate.setEmergingIndustryClassify(StringUtil.objectToString(record.get("emergingIndustryClassify")));
	
			bizInterestRate.setInOverseasTo(StringUtil.objectToString(record.get("inOverseasTo")));
			bizInterestRate.setTraneFinanceBusiness(StringUtil.objectToLong(record.get("traneFinanceBusiness")));
			bizInterestRate.setPolicyLendingStatus(StringUtil.objectToLong(record.get("policyLendingStatus")));
			bizInterestRate.setPolicyDescription(StringUtil.objectToString(record.get("policyDescription")));
			bizInterestRate.setLoanNumberAvailable(StringUtil.objectToLong(record.get("loanNumberAvailable")));
			bizInterestRate.setCreditLoanNum(StringUtil.objectToString(record.get("creditLoanNum")));
			bizInterestRate.setNaturnLoan(StringUtil.objectToLong(record.get("naturnLoan")));
			bizInterestRate.setTwoHeadTaller(StringUtil.objectToLong(record.get("twoHeadTaller")));
			bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
			bizInterestRate.setMediumEnterpriseLoans(StringUtil.objectToLong(record.get("mediumEnterpriseLoans")));
			bizInterestRate.setShipFinance(StringUtil.objectToLong(record.get("shipFinance")));
			bizInterestRate.setTyoesIndustrial(StringUtil.objectToLong(record.get("tyoesIndustrial")));
			bizInterestRate.setIndustrialTransformation(StringUtil.objectToLong(record.get("industrialTransformation")));
			bizInterestRate.setStrategicEmerg(StringUtil.objectToLong(record.get("strategicEmerg")));
			bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
			bizInterestRate.setTradeInterestRate(StringUtil.objToInteger(record.get("tradeInterestRate")));
			bizInterestRate.setPolicyAttributeState(StringUtil.objToInteger(record.get("policyAttributeState")));
			bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
			bizInterestRate.setLoanServicePeopleNum(StringUtil.objectToLong(record.get("loanServicePeopleNum")));
			bizInterestRate.setLoanFunResource(StringUtil.objectToString(record.get("loanFunResource")));
			bizInterestRate.setPovertyProperty(StringUtil.objectToString(record.get("povertyProperty")));
			bizInterestRate.setPovertyAddress(StringUtil.objectToString(record.get("povertyAddress")));
			bizInterestRate.setPovertySort(StringUtil.objectToString(record.get("povertySort")));
			bizInterestRate.setInnovativeBusinessType(StringUtil.objectToString(record.get("innovativeBusinessType")));
			bizInterestRate.setIs421(StringUtil.objectToLong(record.get("is421")));
			bizInterestRate.setLoanDomain(StringUtil.objectToString(record.get("loanDomain")));
			bizInterestRate.setImportExportGoodsService(StringUtil.objectToString(record.get("importExportGoodsService")));
			bizInterestRate
					.setInvestmentLoanCkassifcation(StringUtil.objectToString(record.get("investmentLoanCkassifcation")));
			bizInterestRate.setIsSyndicated(StringUtil.objToInteger(record.get("isSyndicated")));
			bizInterestRate.setIsSyndicatedAgency(StringUtil.objToInteger(record.get("isSyndicatedAgency")));
			bizInterestRate.setSyndicatedStatus(StringUtil.objectToLong(record.get("syndicatedStatus")));
			bizInterestRate.setProjectName(StringUtil.objectToString(record.get("projectName")));
			bizInterestRate.setBackgroundNationality(StringUtil.objectToString(record.get("backgroundNationality")));
			bizInterestRate.setIndustryInvestment(StringUtil.objectToString(record.get("industryInvestment")));
			// 政策性属性分类
			bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("traneFinanceTypeList")));
			bizInterestRateProvider.update(bizInterestRate);
	
			// 7还款计划
			BizRepaymentPlan repaymentPlanQuery = new BizRepaymentPlan();
			repaymentPlanQuery.setGrantCode(grantCode);
			repaymentPlanQuery.setDebtCode(debtCode);
			BizRepaymentPlan bizRepaymentPlan = bizRepaymentPlanProvider.selectOne(repaymentPlanQuery);
			if (bizRepaymentPlan == null) {
				bizRepaymentPlan = new BizRepaymentPlan();
			}
			bizRepaymentPlan.setEnable(BizContant.ENABLE_YES);
			bizRepaymentPlan.setDebtCode(debtCode);
			bizRepaymentPlan.setFirstDay(StringUtil.objectToDate(record.get("firstDay")));
			bizRepaymentPlan.setFirstRepayDay(StringUtil.objectToDate(record.get("fristRepayDate")));
			bizRepaymentPlan.setLastRepaymentDay(StringUtil.objectToDate(record.get("lastRepayDay")));
			bizRepaymentPlan.setRepaymentCostMode(StringUtil.objectToLong(record.get("repaymentCostMode")));
			bizRepaymentPlan.setCaclWay(StringUtil.objectToLong(record.get("caclWay")));
			bizRepaymentPlan.setGrantCode(grantCode);
			bizRepaymentPlan.setPayCny(currencyList[0]);
			BizRepaymentPlan dbRst = bizRepaymentPlanProvider.update(bizRepaymentPlan);
	
			// 获取还本信息和还息信息
			String payDateStr = StringUtil.objectToString(record.get("payDateList"));
			String principalAmyStr = StringUtil.objectToString(record.get("principalAmyList"));
			String clearinterestDateRatioStr = StringUtil.objectToString(record.get("clearinterestDateRatioList"));
			// 分解成数组
			String[] payDateList = StringUtils.split(payDateStr, '#');
			String[] principalAmyList = StringUtils.split(principalAmyStr, '#');
			String[] clearinterestDateRatioList = StringUtils.split(clearinterestDateRatioStr, '#');
			int payLen = payDateList.length;
			// 清空旧的还本计划
			if (payLen > 0) {
				log.info("首先删除旧的还本计划... ");
				int osize = bizRepaymentPricinalPlanProvider.deleteByGrantCode(grantCode, debtCode);
				log.info(osize > 0 ? "成功删除:" + osize + "个!" : "删除失败!");
			}
			// 新增新的还本计划
			for (int i = 0; i < payLen; i++) {
				// 创建新的还本计划
				String type = payDateList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存还本计划信息... ");
					BizRepaymentPricinalPlan bizRepaymentPricinalPlan = new BizRepaymentPricinalPlan();
					bizRepaymentPricinalPlan.setEnable(BizContant.ENABLE_YES);
					bizRepaymentPricinalPlan.setPayDate(StringUtil.objectToDate(payDateList[i]));
					bizRepaymentPricinalPlan.setPrincipalAmy(StringUtil.stringToBigDecimal(principalAmyList[i]));
					bizRepaymentPricinalPlan.setDebtCode(debtCode);
					bizRepaymentPricinalPlan.setGrantCode(grantCode);
					BizRepaymentPricinalPlan rpRst = bizRepaymentPricinalPlanProvider.update(bizRepaymentPricinalPlan);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			int itLen = clearinterestDateRatioList.length;
			// 删除旧的还息计划
			if (itLen > 0) {
				// 清空旧的还息计划
				log.info("首先删除旧的还息计划... ");
				int dsize = bizRepaymentLoanPlanProvider.deleteByGrantCode(grantCode, debtCode);
				log.info(dsize > 0 ? "成功删除:" + dsize + "个!" : "删除失败!");
			}
			// 新增新的还息计划
			for (int i = 0; i < itLen; i++) {
				// 创建新的还息计划
				String type = clearinterestDateRatioList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存还息计划信息... ");
					BizRepaymentLoanPlan bizRepaymentLoanPlan = new BizRepaymentLoanPlan();
					bizRepaymentLoanPlan.setEnable(BizContant.ENABLE_YES);
					bizRepaymentLoanPlan.setInterestDate(StringUtil.objectToDate(clearinterestDateRatioList[i]));
					bizRepaymentLoanPlan.setDebtCode(debtCode);
					bizRepaymentLoanPlan.setGrantCode(grantCode);
					BizRepaymentLoanPlan rpRst = bizRepaymentLoanPlanProvider.update(bizRepaymentLoanPlan);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			// 担保合同
			String bizGuaranteeContractListStr = StringUtil.objectToString(record.get("bizGuaranteeContractList"));
			if (StringUtils.isNotBlank(bizGuaranteeContractListStr)) {
				System.out.println(bizGuaranteeContractListStr);
				// 清空旧的担保合同
				log.info("首先删除旧的担保合同... ");
				int size = bizGuaranteeContractProvider.deleteByGrantCode(grantCode, debtCode);
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
				// 分解成数组
				String[] bizGuaranteeContractList = StringUtils.split(bizGuaranteeContractListStr, '$');
				for (int i = 0; i < bizGuaranteeContractList.length; i++) {
					String item = bizGuaranteeContractList[i];
					BizGuaranteeContract bizGuaranteeContract = convertStrToModel(item);
					System.out.println("------------" + item);
					BizGuaranteeContract bizGuaranteeContract1 = new BizGuaranteeContract();
					bizGuaranteeContract1.setEnable(BizContant.ENABLE_YES);
					bizGuaranteeContract1.setGrantCode(grantCode);
					bizGuaranteeContract1.setDebtCode(debtCode);
					bizGuaranteeContract1.setGuarNo(bizGuaranteeContract.getGuarNo());
					bizGuaranteeContract1.setGuarManualNo(bizGuaranteeContract.getGuarManualNo());
					bizGuaranteeContract1.setGuarCustName(bizGuaranteeContract.getGuarCustName());
					bizGuaranteeContract1.setGuarCustNo(bizGuaranteeContract.getGuarCustNo());
					bizGuaranteeContract1.setGuarAmount(bizGuaranteeContract.getGuarAmount());
					bizGuaranteeContract1.setCurrency(bizGuaranteeContract.getCurrency());
					bizGuaranteeContract1.setStartDate(bizGuaranteeContract.getStartDate());
					bizGuaranteeContract1.setDueDate(bizGuaranteeContract.getDueDate());
					bizGuaranteeContract1.setTerm(bizGuaranteeContract.getTerm());
					bizGuaranteeContract1.setTermUnit(bizGuaranteeContract.getTermUnit());
					bizGuaranteeContract1.setWarrandice(bizGuaranteeContract.getWarrandice());
					bizGuaranteeContract1.setGuarContType(bizGuaranteeContract.getGuarContType());
					bizGuaranteeContract1.setGuarContState(bizGuaranteeContract.getGuarContState());
					bizGuaranteeContract1.setGuarMaxAmt(bizGuaranteeContract.getGuarMaxAmt());
					bizGuaranteeContract1.setAgent(bizGuaranteeContract.getAgent());
					bizGuaranteeContract1.setAgencies(bizGuaranteeContract.getAgencies());
					bizGuaranteeContractProvider.update(bizGuaranteeContract1);
				}
			}
			// 授信信息
			if (record.get("custUserLetter") != null) {
				// 清空旧的用户主体授信
				log.info("首先删除旧的用户主体授信... ");
				int size = bizCreditLinesProvider.deleteByGrantId(grant.getId_(), grant.getDebtCode());
				log.info(size > 0 ? "成功删除:" + size + "个!" : "删除失败!");
				// 创建新的用户主体授信
				String json = JSON.toJSONString(record.get("custUserLetter"));
				List<BizCustomer> bizCustomerLisbt = JSON.parseArray(json, BizCustomer.class);
				for (BizCustomer cu : bizCustomerLisbt) {
					// 保存用户主体授信信息表
					List<BizCreditLines> bizProductLinesTypeList = cu.getCreditLinesList();
					Long bizCustomerId = cu.getId();
					//判断如果存在授信信息才更新，否则不更新
					if(bizProductLinesTypeList!=null && bizProductLinesTypeList.size()>0){
						
						for (BizCreditLines li : bizProductLinesTypeList) {
							li.setCustomerId(bizCustomerId);
							li.setDebtCode(debtCode);
							li.setEnable(BizContant.ENABLE_YES);
							li.setCustNo(cu.getCustNo());
							li.setObjtyp(BizContant.BIZ_TABLE_GRANT);
							li.setObjinr(StringUtil.objectToString(objInr));
							bizCreditLinesProvider.update(li);
						}
					}
				}
			}
			// 提交后删除所有相关的暂存文件
			log.info("开始删除暂存文件信息... ");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			// 根据方案编号like查询
			queryMap.put("schemeNum", debtCode);
			int count = bizTempProvider.deleteTemporaryByParam(queryMap);
			log.info("成功删除(" + count + ")条暂存!");
			// 启动审核流程，发起流程相关的查询
			List<SysUserRole> list = (List<SysUserRole>) record.get("list");
			Map<String, Object> fwParams = new HashMap<String, Object>();
			for (SysUserRole sysUserRole : list) {
				String roleId = sysUserRole.getRoleId().toString();
				System.out.println("-----------------" + roleId + "--------------");
				// 发起审核流程
				fwParams.put("userId", createBy);
				fwParams.put("mBizId", grantCode);
				if ("451117558502785025".equals(roleId)) {
					// 判断是否变更流程的提交
					if (StringUtils.isNotBlank(origCode) && origCode.length() > 2) {
						fwParams.put("pdid", "bgprocess");// 发起变更流程
					} else {
						fwParams.put("pdid", "fhzxff");// 发起审核流程
					}
				} else if ("451117558506979339".equals(roleId)) {
					// 判断是否变更流程的提交
					if (StringUtils.isNotBlank(origCode) && origCode.length() > 2) {
						fwParams.put("pdid", "zhbgprocess");// 发起变更流程
					} else {
						fwParams.put("pdid", "zhzxff");// 发起审核流程
					}
				}
			}
			fwParams.put("debtCode",debtCode);
			bizProStatementProvider.createAndstartProcess(fwParams);
			return dbGrant;
		}catch(Exception e){
			throw new Exception("发放审核变更提交出错："+e.getMessage());
		}finally{
			redisHelper.deleteHistoryState("iBase4J:bizDebtGrant*");
		}
	}

	/**
	 * 功能：保存发放申请内容
	 *
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BizDebtGrant save(Map<String, Object> record) throws Exception {
		log.info("开始保存债项发放申请...");
		try{
			for (String key : record.keySet()) {
				System.out.println("key= " + key + " and value= " + record.get(key));
			}
			Integer isRate = StringUtil.objToInteger(record.get("isRate"));// 有无费用1有,2无
			Long createBy = StringUtil.objectToLong(record.get("createBy"));
			String grantCode = StringUtil.objectToString(record.get("grantCode"));
			String debtCode = StringUtil.objectToString(record.get("debtCode"));
			String businessCode = StringUtil.objectToString(record.get("businessCode"));
			String currencyListStr = StringUtil.objectToString(record.get("currencyList"));
			String paymentAmtListStr = StringUtil.objectToString(record.get("paymentAmtList"));
			String originalGrantCode = StringUtil.objectToString(record.get("originalGrantCode"));
			// 发放币种和金额
			String[] currencyList = StringUtils.split(currencyListStr, '#');
			String[] paymentAmtList = StringUtils.split(paymentAmtListStr, '#');
	
			Map<String, Object> debtMap2 = new HashMap<>();
			debtMap2.put("debtCode", debtCode);
			BizDebtSummary bizDebtSummary = bizDebtSummaryProvider.queryList(debtMap2).get(0);
			Integer solutionState = bizDebtSummary.getSolutionState();
			// 如果方案状态不是可发放申请状态，则退出
			if (!Integer.valueOf(BizStatus.DEBTAVAI).equals(solutionState)) {
				return null;
			}
	
			Map<String, Object> theRentFactoringMap = new HashMap<String, Object>();
			theRentFactoringMap.put("debtCode", debtCode);
			BizTheRentFactoring bizTheRentFactoring = bizTheRentFactoringProvider.queryList(theRentFactoringMap).get(0);
			// 1.发放申请
			BizDebtGrant model = new BizDebtGrant();
			model.setToierancePeriod(isRate);// 容忍期限存入了是否有费用字段
			model.setBusinessTypes(businessCode);
			model.setCreateBy(createBy);
			model.setCreateTime(new Date());
			model.setProperInfo(StringUtil.objectToLong(record.get("properInfo")));// 产品专有信息外键
			model.setDebtCode(debtCode);
			model.setGrantAmt(StringUtil.stringToBigDecimal(paymentAmtList[0]));// 发放金额,获取第一个索引处的金额为发放金额
			model.setCurrency(currencyList[0]);// 发放币种
			model.setProposer(bizDebtSummary.getProposer());
			model.setBusinessName(StringUtil.objectToString(record.get("businessName")));// 产品名称
			model.setBusinessTypeName(bizDebtSummary.getProjectName());
			model.setEnable(BizContant.ENABLE_YES);
			model.setProcessStatus(BizContant.ENABLE_YES);
			model.setGracePeriod(StringUtil.objToInteger(record.get("gracePeriod")));//
			model.setGrantCode(grantCode);// 流水号
			model.setRemark(StringUtil.objectToString(record.get("businessName")) + "进行发放");
			model.setScopeBusinPeriod(StringUtil.objectToString(record.get("scopeBusinPeriod")));
			model.setSendDate(StringUtil.objectToDate(record.get("sendDate")));
			model.setEndDate(StringUtil.objectToDate(record.get("sendEndDate")));
			model.setInstitutionCode(StringUtil.objectToString(record.get("deptCode")));// 经办机构
			model.setDescriptionProgramQuoqate(StringUtil.objectToString(record.get("descriptionProgramQuoqate")));
			model.setDescriptionRateRules(StringUtil.objectToString(record.get("descriptionRateRules")));
			model.setOriginalGrantCode(StringUtil.objectToString(record.get("originalGrantCode")));
			model.setIouCode(StringUtil.objectToString(record.get("iouCode")));
			// 如果是变更的申请状态初始化状态为变更审核中，否则状态初始化为审核中
			if (StringUtils.isNotEmpty(originalGrantCode)) {
				model.setGrantStatus(BizStatus.GRANCHAT);
			} else {
				model.setGrantStatus(BizStatus.GRANNEAT);
			}
			BizDebtGrant dbGrant = update(model);
			log.info("保存" + dbGrant == null ? "失败！" : "成功！");
			Long objInr = dbGrant.getId();
			// 2.多个收费信息
			String chargeStr = StringUtil.objectToString(record.get("chargeTypeList"));
			String chargeVals = StringUtil.objectToString(record.get("rateValueList"));
			String[] chargeList = StringUtils.split(chargeStr, '#');
			String[] chargeValues = StringUtils.split(chargeVals, '#');
			int chargeLen = chargeList.length;
			if (chargeLen > 0) {
				for (int i = 0; i < chargeLen; i++) {
					String charge = chargeList[i];
					if (StringUtils.isNotEmpty(charge)) {
						log.info("开始保存FEP（收息收费表）... ");
						BizFEP fep = new BizFEP();
						fep.setCreateBy(createBy);
						fep.setCreateTime(new Date());
						fep.setEnable(BizContant.ENABLE_YES);
						fep.setFeecod(charge);// 费用代码
						fep.setObjInr(objInr);// 业务表INR(XXD的ID)
						fep.setObjTyp(BizContant.BIZ_TABLE_GRANT);
						fep.setRateType(charge);
						fep.setRateValue(StringUtil.stringToBigDecimal(chargeValues[i]));
						fep.setRemark("发放费用存盘");
						// 执行保存
						BizFEP dbFep = bizFEPProvider.update(fep);
						log.info("保存" + dbFep == null ? "失败！" : "成功！");
					}
				}
			}
			// 3.业务流水
			log.info("开始保存TRN（业务流水表）... ");
			BizTRN trn = new BizTRN();
			trn.setCreateBy(createBy);
			trn.setCreateTime(new Date());
			trn.setEnable(BizContant.ENABLE_YES);
			// trn.setEtyextkey(etyextkey);
			trn.setExedat(new Date());// 执行日期
			trn.setInidattim(new Date());// 存盘时间
			trn.setInifrm(grantCode);// 交易代码(流水号)自定义
			trn.setIninam("债项发放申请流程");// 交易名称(业务名称)
			trn.setIniusr(createBy);// 登录柜员
			trn.setObjinr(objInr);// 业务表INR(XXD的ID)
			trn.setObjnam(StringUtil.objectToString(record.get("businessName")) + "进行发放");
			trn.setObjtyp(BizContant.BIZ_TABLE_GRANT);//
			trn.setOwnref(StringUtil.objectToString(record.get("debtCode")));// 业务编号
			trn.setBusinessTypes(businessCode);
			trn.setBchkeyinr(StringUtil.objectToString(record.get("deptCode")));// 经办机构
			// TODO 应该匹配哪些属性？
			// trn.setRelamt(relamt);//授权金额
			// trn.setRelcur(relcur);//授权币种
			trn.setReloriamt(StringUtil.stringToBigDecimal(record.get("grantAmt")));// 金额
			trn.setReloricur(bizDebtSummary.getMpc());// 币种
			// trn.setXrecurblk(xrecurblk);//可用货币
			trn.setRelres(String.valueOf(BizStatus.GRANNEAT));// 已提交状态
			BizTRN dbTrn = bizTRNProvider.update(trn);
			log.info("保存" + dbTrn == null ? "失败！" : "成功！");
			// 3.多个利率信息
			String calcDaysListStr = StringUtil.objectToString(record.get("calcDaysList"));
			String chgCycleUnitListStr = StringUtil.objectToString(record.get("chgCycleUnitList"));
			String convertedPriceListStr = StringUtil.objectToString(record.get("convertedPriceList"));
	
			String floatDirectioinListStr = StringUtil.objectToString(record.get("floatDirectioinList"));
			String floatingRateListStr = StringUtil.objectToString(record.get("floatingRateList"));
			String floatModeListStr = StringUtil.objectToString(record.get("floatModeList"));
			String irBkListStr = StringUtil.objectToString(record.get("irBkList"));
			String overdueIncrRatioListStr = StringUtil.objectToString(record.get("overdueIncrRatioList"));
			String overdueRateCalcModeListStr = StringUtil.objectToString(record.get("overdueRateCalcModeList"));
	
			String paymentModeListStr = StringUtil.objectToString(record.get("paymentModeList"));
			String rateValListStr = StringUtil.objectToString(record.get("rateValList"));
			String termInterestRateListStr = StringUtil.objectToString(record.get("termInterestRateList"));
			String rateTypeListStr = StringUtil.objectToString(record.get("rateTypeList"));// 利率类型
			String varCycleListStr = StringUtil.objectToString(record.get("varCycleList"));
			// 利率信息
			String[] calcDaysList = StringUtils.split(calcDaysListStr, '#');
			String[] chgCycleUnitList = StringUtils.split(chgCycleUnitListStr, '#');
			String[] convertedPriceList = StringUtils.split(convertedPriceListStr, '#');
	
			String[] floatDirectioinList = StringUtils.split(floatDirectioinListStr, '#');
			String[] floatingRateList = StringUtils.split(floatingRateListStr, '#');
			String[] floatModeList = StringUtils.split(floatModeListStr, '#');
			String[] irBkList = StringUtils.split(irBkListStr, '#');
			String[] overdueIncrRatioList = StringUtils.split(overdueIncrRatioListStr, '#');
			String[] overdueRateCalcModeList = StringUtils.split(overdueRateCalcModeListStr, '#');
			String[] paymentModeList = StringUtils.split(paymentModeListStr, '#');
			String[] rateValList = StringUtils.split(rateValListStr, '#');
			String[] termInterestRateList = StringUtils.split(termInterestRateListStr, '#');
			String[] varCycleList = StringUtils.split(varCycleListStr, '#');
			String[] rateTypeList = StringUtils.split(rateTypeListStr, '#');
			// 分拆数组，组合fec
			for (int i = 0; i < currencyList.length; i++) {
				String currency = currencyList[i];
				if (StringUtils.isNotEmpty(currency)) {
					log.info("开始保存FEC（利率表）... ");
					BizFEC fec = new BizFEC();
					fec.setType(StringUtil.objToInteger(rateTypeList[i]));
					// 浮动利率必填项,固定利率不能填写
					if ("2".equals(rateTypeList[i])) {
						// 根据利率基准查询利率期限
						BizGuaranteeType guaranteeType = getBizGuaranteeType(irBkList[i]);
						fec.setIrBk(guaranteeType.getSrv1());// 利率基准
						if (guaranteeType != null) {
							fec.setTerm(guaranteeType.getSrv2());// 利率期限
						}
						fec.setFloatDirectioin(floatDirectioinList[i]);// 利率浮动方向
						fec.setFloatMode(floatModeList[i]);// 利率浮动方式
						fec.setFloatingRate(StringUtil.stringToBigDecimal(floatingRateList[i]));// 浮动率
						fec.setVarCycle(StringUtil.objToInteger(varCycleList[i]));// 利率变动周期
						fec.setChgCycleUnit(StringUtil.objToInteger(chgCycleUnitList[i]));// 利率变动周期单位
					}
					fec.setCalcDays(StringUtil.objToInteger(calcDaysList[i]));
					fec.setConvertedPrice(StringUtil.stringToBigDecimal(convertedPriceList[i]));
					fec.setCreateBy(createBy);
					fec.setCreateTime(new Date());
					fec.setCurrency(currency);
					fec.setEnable(BizContant.ENABLE_YES);
					fec.setObjInr(objInr);
					fec.setObjTyp(BizContant.BIZ_TABLE_GRANT);
					fec.setOverdueIncrRatio(StringUtil.stringToBigDecimal(overdueIncrRatioList[i]));
					fec.setOverdueRateCalcMode(overdueRateCalcModeList[i]);
					fec.setPaymentAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));
					fec.setPaymentMode(paymentModeList[i]);
					fec.setRemark("债项发放流程");
					// fec.setRules(rules);//
					fec.setType(StringUtil.objToInteger(rateTypeList[i]));// 利息类型（1固定、2浮动）
					// 只有固定利率需要填写利率的值，浮动利率由接口计算并返回
					if ("1".equals(rateTypeList[i])) {
						fec.setRateVal(StringUtil.stringToBigDecimal(rateValList[i]));
					} else {
						// TODO 调用利率计算接口
					}
					BizFEC dbFec = bizFECProvider.update(fec);
					log.info("保存" + dbFec == null ? "失败！" : "成功！");
					// 6.CBE余额信息表
					log.info("开始保存CBE（余额信息表）... ");
					BizCBE cbe = new BizCBE();
					// cbe.setAcc(acc);//核心账号1
					// cbe.setAcc2(acc2);//核心账号2
					cbe.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
					// cbe.setCbt(cbt);//业务类型CBS ENTRY 类型
					cbe.setCreateBy(createBy);
					cbe.setCreateTime(new Date());//
					cbe.setCur(currencyList[i]);// 币种
					cbe.setDat(new Date());// 发生日期
					cbe.setEnable(BizContant.ENABLE_YES);
					// cbe.setExtid(extid);//退出id
					cbe.setGledat(new Date());// 记账日期
					// cbe.setNam(nam);//入口名称
					cbe.setObjInr(objInr);// 业务表INR
					cbe.setObjType(BizContant.BIZ_TABLE_GRANT);
					// cbe.setOptdat(optdat);//可选日期
					cbe.setRemark("债项发放流程");//
					cbe.setTrninr(dbTrn.getId());// TRN表的INR，标识该发生额来自哪一笔交易
					cbe.setTrntyp(BizContant.TRN_TYPE_GRANT);// 初始交易类型
					cbe.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// amt折算后的金额
					cbe.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种，即外币需要折算成人民币（系统默认赋值人民币）
					cbe.setRelflg("Released");// 授权标志 R: Released P: Preliminary
												// E:
												// Entered
					BizCBE dbCbe = bizCBEProvider.update(cbe);
					log.info("保存" + dbCbe == null ? "失败！" : "成功！");
					// 7.CBB金额流水信息表
					log.info("开始保存CBB（金额流水）... ");
					BizCBB cbb = new BizCBB();
					cbb.setAmt(StringUtil.stringToBigDecimal(paymentAmtList[i]));// 金额
					cbb.setBegdat(new Date());// 创建日期
					cbb.setCbc(BizContant.CBC_TYPE);// 金额类型
					cbb.setCbeInr(dbCbe.getId());// CBE的INR,生成该余额的CBE的INR
					// cbb.setComamt(comamt);//提交余额金额
					// cbb.setComcur(comcur);//提交余额币种
					cbb.setCreateBy(createBy);
					cbb.setCreateTime(new Date());
					cbb.setCur(currencyList[i]);// 币种
					cbb.setEnable(BizContant.ENABLE_YES);
					cbb.setEnddat(DateUtil.parseDate(BizContant.END_DATE, "yyyy-MM-dd HH:mm:ss"));// 结束日期
					// cbb.setExtid(extid);//金额种类
					cbb.setObjInr(objInr);// 业务表INR
					cbb.setObjType(BizContant.BIZ_TABLE_GRANT);// 业务类型
					cbb.setRemark("债项发放流程");
					// cbb.setXcoamt(xcoamt);//修改提交余额金额
					// cbb.setXcocur(xcocur);//修改提交余额币种
					cbb.setXrfamt(StringUtil.stringToBigDecimal(convertedPriceList[i]));// 折算后金额
					cbb.setXrfcur(BizContant.CURRENCY_CNY);// 折算币种(默认只折算人民币)
	
					BizCBB dbCbb = bizCBBProvider.update(cbb);
					log.info("保存" + dbCbb == null ? "失败！" : "成功！");
				}
			}
			// 4.多个担保信息
			String guaranTypeStr = StringUtil.objectToString(record.get("guaranTypeList"));
			String guaranPortTypeListStr = StringUtil.objectToString(record.get("guaranPortTypeList"));
			String notClearAmtListStr = StringUtil.objectToString(record.get("notClearAmtList"));
			String clearRatioListStr = StringUtil.objectToString(record.get("clearRatioList"));
			String clearRatioAmtListStr = StringUtil.objectToString(record.get("clearRatioAmtList"));
			String pledgeTypeListStr = StringUtil.objectToString(record.get("pledgeTypeList"));
			// String pledgeNameListStr =
			// StringUtil.objectToString(record.get("pledgeNameList"));
			// String pledgeStateListStr =
			// StringUtil.objectToString(record.get("pledgeStateList"));
			String pledgeNoListStr = StringUtil.objectToString(record.get("pledgeNoList"));
			String guaranRemarkListStr = StringUtil.objectToString(record.get("guaranRemarkList"));
			// String schemeNoListStr =
			// StringUtil.objectToString(record.get("schemeNoList"));
			// 分解成数组
			String[] guaranTypeList = StringUtils.split(guaranTypeStr, '#');
			String[] guaranPortTypeList = StringUtils.split(guaranPortTypeListStr, '#');
			String[] notClearAmtList = StringUtils.split(notClearAmtListStr, '#');
			String[] clearRatioList = StringUtils.split(clearRatioListStr, '#');
			String[] clearRatioAmtList = StringUtils.split(clearRatioAmtListStr, '#');
			String[] pledgeTypeList = StringUtils.split(pledgeTypeListStr, '#');
			// String[] pledgeNameList = StringUtils.split(pledgeNameListStr,
			// '#');
			// String[] pledgeStateList = StringUtils.split(pledgeStateListStr,
			// '#');
			String[] pledgeNoList = StringUtils.split(pledgeNoListStr, '#');
			String[] guaranRemarkList = StringUtils.split(guaranRemarkListStr, '#');
			// String[] schemeNoList = StringUtils.split(schemeNoListStr, '#');
	
			for (int i = 0; i < guaranTypeList.length; i++) {
				String type = guaranTypeList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存担保信息... ");
					BizGuaranteeResult gResult = new BizGuaranteeResult();
					gResult.setCreateBy(createBy);
					gResult.setCreateTime(new Date());
					gResult.setEnable(BizContant.ENABLE_YES);
					gResult.setGuaranPortType(guaranPortTypeList[i]);
					// 可明确划分份额
					if ("1".equals(guaranPortTypeList[i])) {
						gResult.setClearRatio(StringUtil.stringToBigDecimal(clearRatioList[i]));
						gResult.setClearRatioAmt(StringUtil.stringToBigDecimal(clearRatioAmtList[i]));
					} else {
						gResult.setNotClearAmt(StringUtil.stringToBigDecimal(notClearAmtList[i]));
					}
					gResult.setGuaranType(guaranTypeList[i]);
					// gResult.setSchemeNo(schemeNoList[i]);
					if (pledgeNoList != null && i < pledgeNoList.length) {
						gResult.setPledgeNo(pledgeNoList[i]);
						gResult.setPledgeType(pledgeTypeList[i]);
						// gResult.setPledgeName(pledgeNameList[i]);
						// gResult.setPledgeState(pledgeStateList[i]);
					}
					gResult.setGrantCode(grantCode);// 发放流水号码
					gResult.setRemark(guaranRemarkList[i]);// 备注
					//
					BizGuaranteeResult dbRst = bizGuaranteeResultProvider.update(gResult);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			// 5.租金保理
			BizRentalFactoring bizRentalFactoring = new BizRentalFactoring();
			theRentFactoringMap.put("custNo", bizTheRentFactoring.getCustNo());
			BizCustomer bizCustomer = bizCustomerProvider.queryList(theRentFactoringMap).get(0);
			bizRentalFactoring.setLesseName(bizCustomer.getCustNameCN());
			bizRentalFactoring.setLesseCode(bizCustomer.getCustNo());
			bizRentalFactoring.setLesseRate(bizCustomer.getCreditRating());
			bizRentalFactoring.setFinancePlatform(bizTheRentFactoring.getFinancePlatform());
			bizRentalFactoring.setBusinessTypes(businessCode);
			bizRentalFactoring.setDebtCode(debtCode);
			bizRentalFactoring.setGrantCode(grantCode);
			bizRentalFactoring.setRenFileName(StringUtil.objectToString(record.get("renFileName")));
			bizRentalFactoring.setRepaymentType(StringUtil.objectToString(record.get("repaymentType")));
			// bizRentalFactoring.setDueDate(StringUtil.objectToDate(record.get("dueDate")));
			// bizRentalFactoring.setRfLessor(StringUtil.objectToLong(record.get("rfLessor")));
			// bizRentalFactoring.setLesse(StringUtil.objectToLong(record.get("lesse")));
			// bizRentalFactoring.setValueDate(StringUtil.objectToDate(record.get("valueDate")));
			bizRentalFactoring.setLeasehold(StringUtil.objectToString(record.get("leasehold")));
			bizRentalFactoring.setEnable(BizContant.ENABLE_YES);
			bizRentalFactoring.setIouCode(StringUtil.objectToString(record.get("iouCode")));
			// bizRentalFactoring.setDebtCode(StringUtil.objectToString(record.get("debtNum")));
			bizRentalFactoring.setBizRentalFactoringCode(StringUtil.objectToString(record.get("bizRentalFactoringCode")));
	
			bizRentalFactoringProvider.update(bizRentalFactoring);
			// 6.政策属性
			BizInterestRate bizInterestRate = new BizInterestRate();
			bizInterestRate.setEnable(BizContant.ENABLE_YES);
			bizInterestRate.setAlleviationLoan(StringUtil.objectToLong(record.get("alleviationLoan")));
			bizInterestRate.setBusinessContractAmount(StringUtil.stringToBigDecimal(record.get("businessContractAmount")));
			bizInterestRate.setBusinessContractCode(StringUtil.objectToString(record.get("businessContractCode")));
			bizInterestRate.setBusinessType(StringUtil.objectToString(record.get("businessType")));
			bizInterestRate.setBusinessContractDate(StringUtil.objectToDate(record.get("businessContractDate")));
			bizInterestRate.setCompare(StringUtil.objectToString(record.get("compare")));
			bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
			bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
			bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
			bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("policyAttributeClassify")));
			bizInterestRate.setDebtNum(debtCode);
			bizInterestRate.setGrantCode(grantCode);
			bizInterestRate.setEmergingIndustryClassify(StringUtil.objectToString(record.get("emergingIndustryClassify")));
	
			bizInterestRate.setInOverseasTo(StringUtil.objectToString(record.get("inOverseasTo")));
			bizInterestRate.setTraneFinanceBusiness(StringUtil.objectToLong(record.get("traneFinanceBusiness")));
			bizInterestRate.setPolicyLendingStatus(StringUtil.objectToLong(record.get("policyLendingStatus")));
			bizInterestRate.setPolicyDescription(StringUtil.objectToString(record.get("policyDescription")));
			bizInterestRate.setLoanNumberAvailable(StringUtil.objectToLong(record.get("loanNumberAvailable")));
			bizInterestRate.setCreditLoanNum(StringUtil.objectToString(record.get("creditLoanNum")));
			bizInterestRate.setNaturnLoan(StringUtil.objectToLong(record.get("naturnLoan")));
			bizInterestRate.setTwoHeadTaller(StringUtil.objectToLong(record.get("twoHeadTaller")));
			bizInterestRate.setContrySpecificLoans(StringUtil.objectToLong(record.get("contrySpecificLoans")));
			bizInterestRate.setMediumEnterpriseLoans(StringUtil.objectToLong(record.get("mediumEnterpriseLoans")));
			bizInterestRate.setShipFinance(StringUtil.objectToLong(record.get("shipFinance")));
			bizInterestRate.setTyoesIndustrial(StringUtil.objectToLong(record.get("tyoesIndustrial")));
			bizInterestRate.setIndustrialTransformation(StringUtil.objectToLong(record.get("industrialTransformation")));
			bizInterestRate.setStrategicEmerg(StringUtil.objectToLong(record.get("strategicEmerg")));
			bizInterestRate.setCurturalProduct(StringUtil.objectToLong(record.get("curturalProduct")));
			bizInterestRate.setTradeInterestRate(StringUtil.objToInteger(record.get("tradeInterestRate")));
			bizInterestRate.setPolicyAttributeState(StringUtil.objToInteger(record.get("policyAttributeState")));
			bizInterestRate.setCreditLoanNo(StringUtil.objToInteger(record.get("creditLoanNo")));
			bizInterestRate.setLoanServicePeopleNum(StringUtil.objectToLong(record.get("loanServicePeopleNum")));
			bizInterestRate.setLoanFunResource(StringUtil.objectToString(record.get("loanFunResource")));
			bizInterestRate.setPovertyProperty(StringUtil.objectToString(record.get("povertyProperty")));
			bizInterestRate.setPovertyAddress(StringUtil.objectToString(record.get("povertyAddress")));
			bizInterestRate.setPovertySort(StringUtil.objectToString(record.get("povertySort")));
			bizInterestRate.setInnovativeBusinessType(StringUtil.objectToString(record.get("innovativeBusinessType")));
			bizInterestRate.setIs421(StringUtil.objectToLong(record.get("is421")));
			bizInterestRate.setLoanDomain(StringUtil.objectToString(record.get("loanDomain")));
			bizInterestRate.setImportExportGoodsService(StringUtil.objectToString(record.get("importExportGoodsService")));
			bizInterestRate
					.setInvestmentLoanCkassifcation(StringUtil.objectToString(record.get("investmentLoanCkassifcation")));
			bizInterestRate.setIsSyndicated(StringUtil.objToInteger(record.get("isSyndicated")));
			bizInterestRate.setIsSyndicatedAgency(StringUtil.objToInteger(record.get("isSyndicatedAgency")));
			bizInterestRate.setSyndicatedStatus(StringUtil.objectToLong(record.get("syndicatedStatus")));
			bizInterestRate.setProjectName(StringUtil.objectToString(record.get("projectName")));
			bizInterestRate.setBackgroundNationality(StringUtil.objectToString(record.get("backgroundNationality")));
			bizInterestRate.setIndustryInvestment(StringUtil.objectToString(record.get("industryInvestment")));
			// 政策性属性分类
			bizInterestRate.setPolicyAttributeClassify(StringUtil.objectToString(record.get("traneFinanceTypeList")));
			bizInterestRateProvider.update(bizInterestRate);
	
			// 7还款计划
			BizRepaymentPlan bizRepaymentPlan = new BizRepaymentPlan();
			bizRepaymentPlan.setEnable(BizContant.ENABLE_YES);
			bizRepaymentPlan.setDebtCode(debtCode);
			bizRepaymentPlan.setFirstDay(StringUtil.objectToDate(record.get("firstDay")));
			bizRepaymentPlan.setFirstRepayDay(StringUtil.objectToDate(record.get("fristRepayDate")));
			bizRepaymentPlan.setLastRepaymentDay(StringUtil.objectToDate(record.get("lastRepayDay")));
			bizRepaymentPlan.setRepaymentCostMode(StringUtil.objectToLong(record.get("repaymentCostMode")));
			bizRepaymentPlan.setCaclWay(StringUtil.objectToLong(record.get("caclWay")));
			bizRepaymentPlan.setGrantCode(grantCode);
			bizRepaymentPlan.setPayCny(currencyList[0]);
			BizRepaymentPlan dbRst = bizRepaymentPlanProvider.update(bizRepaymentPlan);
	
			// 获取还本信息和还息信息
			String payDateStr = StringUtil.objectToString(record.get("payDateList"));
			String principalAmyStr = StringUtil.objectToString(record.get("principalAmyList"));
			String clearinterestDateRatioStr = StringUtil.objectToString(record.get("clearinterestDateRatioList"));
			// 分解成数组
			String[] payDateList = StringUtils.split(payDateStr, '#');
			String[] principalAmyList = StringUtils.split(principalAmyStr, '#');
			String[] clearinterestDateRatioList = StringUtils.split(clearinterestDateRatioStr, '#');
	
			for (int i = 0; i < payDateList.length; i++) {
				String type = payDateList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存还本计划信息... ");
					Date payDate = StringUtil.objectToDate(payDateList[i]);
					Date now = new Date();
					BizRepaymentPricinalPlan bizRepaymentPricinalPlan = new BizRepaymentPricinalPlan();
					bizRepaymentPricinalPlan.setPayDate(payDate);
					bizRepaymentPricinalPlan.setPrincipalAmy(StringUtil.stringToBigDecimal(principalAmyList[i]));
					bizRepaymentPricinalPlan.setDebtCode(debtCode);
					bizRepaymentPricinalPlan.setGrantCode(grantCode);
					bizRepaymentPricinalPlan.setEnable(BizContant.ENABLE_YES);
					// 如果是变更新增的前提下
					if (StringUtils.isNotEmpty(originalGrantCode)) {
						// 如果小于今天设置过期标记
						int day = DateUtil.getDayBetween(payDate, now);
						if (day > 0) {
							bizRepaymentPricinalPlan.setEnable(BizContant.ENABLE_NO);
						}
					}
					BizRepaymentPricinalPlan rpRst = bizRepaymentPricinalPlanProvider.update(bizRepaymentPricinalPlan);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			for (int i = 0; i < clearinterestDateRatioList.length; i++) {
				String type = clearinterestDateRatioList[i];
				if (StringUtils.isNotEmpty(type)) {
					log.info("开始保存还息计划信息... ");
					Date interestDate = StringUtil.objectToDate(clearinterestDateRatioList[i]);
					Date now = new Date();
					BizRepaymentLoanPlan bizRepaymentLoanPlan = new BizRepaymentLoanPlan();
					bizRepaymentLoanPlan.setInterestDate(interestDate);
					bizRepaymentLoanPlan.setDebtCode(debtCode);
					bizRepaymentLoanPlan.setGrantCode(grantCode);
					bizRepaymentLoanPlan.setEnable(BizContant.ENABLE_YES);
					// 如果是变更新增的前提下
					if (StringUtils.isNotEmpty(originalGrantCode)) {
						// 如果小于今天设置过期标记
						int day = DateUtil.getDayBetween(interestDate, now);
						if (day > 0) {
							bizRepaymentLoanPlan.setEnable(BizContant.ENABLE_NO);
						}
					}
					BizRepaymentLoanPlan rpRst = bizRepaymentLoanPlanProvider.update(bizRepaymentLoanPlan);
					log.info("保存" + dbRst == null ? "失败！" : "成功！");
				}
			}
			// 担保合同
			String bizGuaranteeContractListStr = StringUtil.objectToString(record.get("bizGuaranteeContractList"));
			System.out.println(bizGuaranteeContractListStr);
			// 分解成数组
			String[] bizGuaranteeContractList = StringUtils.split(bizGuaranteeContractListStr, '$');
			for (int i = 0; i < bizGuaranteeContractList.length; i++) {
				String item = bizGuaranteeContractList[i];
				BizGuaranteeContract bizGuaranteeContract = convertStrToModel(item);
				System.out.println("------------" + item);
				BizGuaranteeContract bizGuaranteeContract1 = new BizGuaranteeContract();
				bizGuaranteeContract1.setEnable(BizContant.ENABLE_YES);
				bizGuaranteeContract1.setGrantCode(grantCode);
				bizGuaranteeContract1.setDebtCode(debtCode);
				bizGuaranteeContract1.setGuarNo(bizGuaranteeContract.getGuarNo());
				bizGuaranteeContract1.setGuarManualNo(bizGuaranteeContract.getGuarManualNo());
				bizGuaranteeContract1.setGuarCustName(bizGuaranteeContract.getGuarCustName());
				bizGuaranteeContract1.setGuarCustNo(bizGuaranteeContract.getGuarCustNo());
				bizGuaranteeContract1.setGuarAmount(bizGuaranteeContract.getGuarAmount());
				bizGuaranteeContract1.setCurrency(bizGuaranteeContract.getCurrency());
				bizGuaranteeContract1.setStartDate(bizGuaranteeContract.getStartDate());
				bizGuaranteeContract1.setDueDate(bizGuaranteeContract.getDueDate());
				bizGuaranteeContract1.setTerm(bizGuaranteeContract.getTerm());
				bizGuaranteeContract1.setTermUnit(bizGuaranteeContract.getTermUnit());
				bizGuaranteeContract1.setWarrandice(bizGuaranteeContract.getWarrandice());
				bizGuaranteeContract1.setGuarContType(bizGuaranteeContract.getGuarContType());
				bizGuaranteeContract1.setGuarContState(bizGuaranteeContract.getGuarContState());
				bizGuaranteeContract1.setGuarMaxAmt(bizGuaranteeContract.getGuarMaxAmt());
				bizGuaranteeContract1.setAgent(bizGuaranteeContract.getAgent());
				bizGuaranteeContract1.setAgencies(bizGuaranteeContract.getAgencies());
				bizGuaranteeContractProvider.update(bizGuaranteeContract1);
			}
			// 授信信息
			if (record.get("custUserLetter") != null) {
				List<BizCustomer> bizCustomerLisbt = JSON.parseArray(JSON.toJSONString(record.get("custUserLetter")),
						BizCustomer.class);
				for (BizCustomer cu : bizCustomerLisbt) {
					// 保存用户主体授信信息表
					List<BizCreditLines> bizProductLinesTypeList = cu.getCreditLinesList();
					Long bizCustomerId = cu.getId();
					//判断如果存在授信信息才更新，否则不更新
					if(bizProductLinesTypeList!=null && bizProductLinesTypeList.size()>0){
						for (BizCreditLines li : bizProductLinesTypeList) {
							li.setCustomerId(bizCustomerId);
							li.setDebtCode(bizDebtSummary.getDebtCode());
							li.setEnable(BizContant.ENABLE_YES);
							li.setCustNo(cu.getCustNo());
							li.setObjtyp("BIZ_DEBT_GRANT");
							li.setObjinr(StringUtil.objectToString(objInr));
							bizCreditLinesProvider.update(li);
						}
					}
				}
			}
			// 提交后删除所有相关的暂存文件
			log.info("开始删除暂存文件信息... ");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			// 根据方案编号like查询
			queryMap.put("schemeNum", debtCode);
			int count = bizTempProvider.deleteTemporaryByParam(queryMap);
			log.info("成功删除(" + count + ")条暂存!");
			// 如果是变更流程进行的提交。那么修改原来发放记录的状态为失效
			if (StringUtils.isNotEmpty(originalGrantCode)) {
				log.info("把原来的发放(" + originalGrantCode + ")状态修改成发放审核中...");
				BizDebtGrant query = new BizDebtGrant();
				query.setGrantCode(originalGrantCode);
				BizDebtGrant orgModel = selectOne(query);
				if (orgModel != null) {
					orgModel.setGrantStatus(BizStatus.GRANFROZ);
					update(orgModel);
					log.info("修改成功!");
				}
			}
			// 发起流程相关的查询
			List<SysUserRole> list = (List) record.get("list");
			Map<String, Object> params = new HashMap<String, Object>();
			for (SysUserRole sysUserRole : list) {
				String roleId = sysUserRole.getRoleId().toString();
				System.out.println("-----------------" + roleId + "--------------");
				// 发起审核流程
				params.put("userId", createBy);
				params.put("mBizId", model.getGrantCode());
				String origCode = StringUtil.objectToString(record.get("originalGrantCode"));
				if ("451117558502785025".equals(roleId)) {
					// 判断是否变更流程的提交
					if (StringUtils.isNotBlank(origCode) && origCode.length() > 2) {
						params.put("pdid", "bgprocess");// 发起变更流程
					} else {
						params.put("pdid", "fhzxff");// 发起审核流程
					}
				} else if ("451117558506979339".equals(roleId)) {
					// 判断是否变更流程的提交
					if (StringUtils.isNotBlank(origCode) && origCode.length() > 2) {
						params.put("pdid", "zhbgprocess");// 发起变更流程
					} else {
						params.put("pdid", "zhzxff");
					}
				}
			}
			params.put("debtCode",debtCode);
			bizProStatementProvider.createAndstartProcess(params);
			return dbGrant;
		}catch(Exception e){
			throw new Exception("发放审核提交出错："+e.getMessage());
		}finally{
			redisHelper.deleteHistoryState("iBase4J:bizDebtGrant*");
		}
	}

	/**
	 * 功能：将字符串转换成合同对象
	 * 
	 * @param itemStr
	 * @return
	 */
	private BizGuaranteeContract convertStrToModel(String item) {
		BizGuaranteeContract contract = new BizGuaranteeContract();
		if (StringUtils.isNotBlank(item)) {
			String[] items = StringUtils.split(item, '#');
			String[] guarNo = items[0].split(":");//担保合同编号
			String[] guarManualNo =  items[1].split(":");//担保合同手工编号
			String[] guarCustName =  items[2].split(":");//担保人客户名称
			String[] guarCustNo =  items[3].split(":");//担保人客户编号
			String[] guarAmount =  items[4].split(":");//担保合同金额
			String[] startDate =  items[5].split(":");//开始日期
			String[] dueDate =  items[6].split(":");//结束日期
			String[] term =  items[7].split(":");//期限
			String[] termUnit =  items[8].split(":");//期限单位
			String[] warrandice =  items[9].split(":");//保证担保类型
			String[] guarContType =  items[10].split(":");//担保合同类型
			String[] guarContState =  items[11].split(":");//担保合同状态
			String[] guarMaxAmt =  items[12].split(":");//是否最高额担保合同
			String[] currency =  items[13].split(":");//币种
			String[] agent =  items[14].split(":");//经办人
			String[] agencies =  items[15].split(":");//经办机构
			//赋值
			if("guarNo".equals(guarNo[0])){
				if(guarNo.length==2){
					contract.setGuarNo(guarNo[1]);
				}
			}
			if("guarManualNo".equals(guarManualNo[0])){
				if(guarManualNo.length==2){
					contract.setGuarManualNo(guarManualNo[1]);
				}
			}
			if("guarCustName".equals(guarCustName[0])){
				if(guarCustName.length==2){
					contract.setGuarCustName(guarCustName[1]);
				}
			}
			if("guarCustNo".equals(guarCustNo[0])){
				if(guarCustNo.length==2){
					contract.setGuarCustNo(guarCustNo[1]);
				}
			}
			if("guarAmount".equals(guarAmount[0])){
				if(guarAmount.length==2){
					contract.setGuarAmount(StringUtil.stringToBigDecimal(guarAmount[1]));
				}
			}
			if("startDate".equals(startDate[0])){
				if(startDate.length==2){
					contract.setStartDate(StringUtil.objectToDate(startDate[1]));
				}
			}
			if("dueDate".equals(dueDate[0])){
				if(dueDate.length==2){
					contract.setDueDate(StringUtil.objectToDate(dueDate[1]));
				}
			}
			if("term".equals(term[0])){
				if(term.length==2){
					contract.setTerm(StringUtil.objectToLong(term[1]));
				}
			}
			if("termUnit".equals(termUnit[0])){
				if(termUnit.length==2){
					contract.setTermUnit(termUnit[1]);
				}
			}
			if("warrandice".equals(warrandice[0])){
				if(warrandice.length==2){
					contract.setWarrandice(warrandice[1]);
				}
			}
			if("guarContType".equals(guarContType[0])){
				if(guarContType.length==2){
					contract.setGuarContType(guarContType[1]);
				}
			}
			if("guarContState".equals(guarContState[0])){
				if(guarContState.length==2){
					contract.setGuarContState(guarContState[1]);
				}
			}
			if("guarMaxAmt".equals(guarMaxAmt[0])){
				if(guarMaxAmt.length==2){
					contract.setGuarMaxAmt(StringUtil.stringToInteger(guarMaxAmt[1]));
				}
			}
			if("currency".equals(currency[0])){
				if(currency.length==2){
					contract.setCurrency(currency[1]);
				}
			}
			if("agent".equals(agent[0])){
				if(agent.length==2){
					contract.setAgent(agent[1]);
				}
			}
			if("agencies".equals(agencies[0])){
				if(agencies.length==2){
					contract.setAgencies(agencies[1]);
				}
			}
		}
		return contract;
	}

	@Override
	public Map getDebtInfoForMakeLoan(Map<String, Object> param) {
		return bizDebtGrantMapper.getDebtInfoForMakeLoan(param);
	}

	@Override
	public List getProductCustomerInfo(Map<String, Object> param) {
		return bizDebtGrantMapper.getProductCustomerInfo(param);
	}
	/**
	 * 功能：从缓存中获取所有币种信息
	 * @return
	 */
	private List<SysCurrency> getCurrencyListByRedis(){
		Set<Object> currencySet =  redisHelper.getAll("iBase4J:sysCurrency*");
		List<SysCurrency> result = new ArrayList<SysCurrency>();
		if(currencySet !=null && currencySet.size()>0){
			for(Iterator<Object> it=currencySet.iterator();it.hasNext();){
				byte[] bytes = SerializeUtil.serialize(it.next());
				SysCurrency item = SerializeUtil.deserialize(bytes, SysCurrency.class);
				result.add(item);
			}
		}
		return result;
	}
	/**
	 * 功能：通过币种编码从所有币种中查询币种对象
	 * @param code
	 * @return
	 */
	private SysCurrency getItemCurrencyByCode(String code, List<SysCurrency> result){
		if(result!=null && result.size()>0 && StringUtils.isNotBlank(code)){
			for(SysCurrency item: result){
				if(item.getMonCode().equals(code)){
					return item;
				}
			}
		}
		return null;
	}

	@Override
	public Map getEditGrant(String grantCode, String debtCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("grantCode", grantCode);
		param.put("debtCode", debtCode);
		// 返回的结果集
		Map<String, Object> record = new HashMap<String, Object>();
		// 租金保理回显信息
		List<BizRentalFactoring> rentalFactoringList = bizRentalFactoringProvider.queryList(param);
		if (CollectionUtils.isNotEmpty(rentalFactoringList)) {
			BizRentalFactoring bizRentalFactoring = rentalFactoringList.get(0);
			record.put("leasehold", bizRentalFactoring.getLeasehold());
			record.put("bizRentalFactoringCode", bizRentalFactoring.getBizRentalFactoringCode());
			if (StringUtils.isNotBlank(bizRentalFactoring.getRepaymentType())) {
				record.put("repaymentType", bizRentalFactoring.getRepaymentType());
			} else {
				record.put("repaymentType", "1");// 还款方式默认是不规则还款1
			}
			record.put("iouCode", bizRentalFactoring.getIouCode());
			record.put("customer", bizRentalFactoring.getLesseName());// 用信主体默认等于承租人（债务人）名称
			record.put("renFileName", bizRentalFactoring.getRenFileName());//租金保理合同附件名称
		} else {
			// record.put("iouCode",IDBulider.getInstance().generatorIouCode());
			record.put("repaymentType", "1");// 还款方式默认是不规则还款1
		}
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("debtCode", debtCode);
		List<BizDebtSummary> summaryList = bizDebtSummaryProvider.queryList(query);
		//获取方案选中的贸金业务政策性属性
		if (summaryList != null && summaryList.size() > 0) {
			BizDebtSummary summary = summaryList.get(0);
			record.put("policy", summary.getPolicy());
		}
		// 发放主表
		BizDebtGrant queryGrant = new BizDebtGrant();
		queryGrant.setGrantCode(grantCode);
		queryGrant.setDebtCode(debtCode);
		BizDebtGrant grantModel = this.selectOne(queryGrant);
		record.put("properInfo", 0);
		record.put("businessCode", grantModel.getBusinessTypes());
		record.put("sendDate", DateUtil.format(grantModel.getSendDate()));
		record.put("sendEndDate", DateUtil.format(grantModel.getEndDate()));
		record.put("scopeBusinPeriod", grantModel.getScopeBusinPeriod());
		record.put("gracePeriod", grantModel.getGracePeriod());
		record.put("isRate", grantModel.getToierancePeriod());// 容忍期用作是否有费用
		if (StringUtils.isNotBlank(grantModel.getDescriptionProgramQuoqate())) {
			record.put("descriptionProgramQuoqate", grantModel.getDescriptionProgramQuoqate());
		} else {
			if (summaryList != null && summaryList.size() > 0) {
				BizDebtSummary summary = summaryList.get(0);
				record.put("descriptionProgramQuoqate", summary.getDescriptionProgramQuoqate());
			}
		}
		if (StringUtils.isNotBlank(grantModel.getDescriptionRateRules())) {
			record.put("descriptionRateRules", grantModel.getDescriptionRateRules());
		} else {
			if (summaryList != null && summaryList.size() > 0) {
				BizDebtSummary summary = summaryList.get(0);
				record.put("descriptionRateRules", summary.getDescriptionRateRules());
			}
		}

		String bCode = grantModel.getBusinessTypes();
		if (StringUtils.isNotEmpty(bCode)) {
			record.put("businessName", bizProductTypeProvider.getByCode(bCode).getName());
		}
		// 多个收费信息
		Map<String, Object> queryFep = new HashMap<String, Object>();
		queryFep.put("objType", BizContant.BIZ_TABLE_GRANT);
		queryFep.put("objInr", grantModel.getId());
		List<BizFEP> fepList = bizFEPProvider.queryList(queryFep);
		if (CollectionUtils.isNotEmpty(fepList)) {
			StringBuffer chargeTypeBuf = new StringBuffer();
			StringBuffer rateValueBuf = new StringBuffer();
			for (BizFEP fep : fepList) {
				String rateType = fep.getRateType();
				BigDecimal rateValue = fep.getRateValue();
				chargeTypeBuf.append(rateType).append("#");
				rateValueBuf.append(rateValue).append("#");
			}
			record.put("chargeTypeList", chargeTypeBuf.toString());
			record.put("rateValueList", rateValueBuf.toString());
		}
		// 多个利率信息
		Map<String, Object> queryFec = new HashMap<String, Object>();
		queryFec.put("objType", BizContant.BIZ_TABLE_GRANT);
		queryFec.put("objInr", grantModel.getId());
		List<BizFEC> fecList = bizFECProvider.queryList(queryFec);
		if (CollectionUtils.isNotEmpty(fecList)) {
			StringBuffer currencyBuf = new StringBuffer();
			StringBuffer rateTypeBuf = new StringBuffer();
			StringBuffer convertedPriceBuf = new StringBuffer();
			StringBuffer paymentAmtBuf = new StringBuffer();
			StringBuffer irBkBuf = new StringBuffer();
			StringBuffer termInterestRateBuf = new StringBuffer();
			StringBuffer floatDirectioinBuf = new StringBuffer();
			StringBuffer floatModeBuf = new StringBuffer();
			StringBuffer floatingRateBuf = new StringBuffer();
			StringBuffer rateValBuf = new StringBuffer();
			StringBuffer paymentModeBuf = new StringBuffer();
			StringBuffer chgCycleUnitBuf = new StringBuffer();
			StringBuffer varCycleBuf = new StringBuffer();
			StringBuffer overdueRateCalcModeBuf = new StringBuffer();
			StringBuffer overdueIncrRatioBuf = new StringBuffer();
			StringBuffer calcDaysBuf = new StringBuffer();
			StringBuffer firstLoanFlagBuf = new StringBuffer();
			StringBuffer lastLoanFlagBuf = new StringBuffer();
			for (BizFEC fec : fecList) {
				currencyBuf.append(fec.getCurrency()).append("#");
				rateTypeBuf.append(fec.getType()).append("#");
				// 浮动利率必填项,固定利率不能填写
				if (Integer.valueOf(2).equals(fec.getType())) {
					// 为了使页面数组个数和顺序对应上没有数据的写0
					rateValBuf.append("0").append("#");// 利率值
					// 下面是有数据的回显
					irBkBuf.append(fec.getIrBk()).append("#");// 利率基准
					termInterestRateBuf.append(fec.getTerm()).append("#");// 利率期限
					floatDirectioinBuf.append(fec.getFloatDirectioin()).append("#");// 利率浮动方向
					floatModeBuf.append(fec.getFloatMode()).append("#");// 利率浮动方式
					floatingRateBuf.append(fec.getFloatingRate()).append("#");// 浮动率
					varCycleBuf.append(fec.getVarCycle()).append("#");// 利率变动周期
					chgCycleUnitBuf.append(fec.getChgCycleUnit()).append("#");// 利率变动周期单位
				} else if (Integer.valueOf(1).equals(fec.getType())) {
					rateValBuf.append(fec.getRateVal()).append("#");
					// 为了使页面数组个数和顺序对应上，都需要设置值
					irBkBuf.append("0").append("#");// 利率基准
					termInterestRateBuf.append("0").append("#");// 利率期限
					floatDirectioinBuf.append("0").append("#");// 利率浮动方向
					floatModeBuf.append("0").append("#");// 利率浮动方式
					floatingRateBuf.append("0").append("#");// 浮动率
					varCycleBuf.append("0").append("#");// 利率变动周期
					chgCycleUnitBuf.append("0").append("#");// 利率变动周期单位
				}
				convertedPriceBuf.append(fec.getConvertedPrice()).append("#");
				paymentAmtBuf.append(fec.getPaymentAmt()).append("#");
				paymentModeBuf.append(fec.getPaymentMode()).append("#");
				overdueRateCalcModeBuf.append(fec.getOverdueRateCalcMode()).append("#");
				overdueIncrRatioBuf.append(fec.getOverdueIncrRatio()).append("#");
				calcDaysBuf.append(fec.getCalcDays()).append("#");
				firstLoanFlagBuf.append(fec.getFirstLoanFlag()).append("#");
				lastLoanFlagBuf.append(fec.getLastLoanFlag()).append("#");
			}
			record.put("currencyList", currencyBuf.toString());
			record.put("rateTypeList", rateTypeBuf.toString());
			record.put("convertedPriceList", convertedPriceBuf.toString());
			record.put("paymentAmtList", paymentAmtBuf.toString());
			record.put("irBkList", irBkBuf.toString());
			record.put("termInterestRateList", termInterestRateBuf.toString());
			record.put("floatDirectioinList", floatDirectioinBuf.toString());
			record.put("floatModeList", floatModeBuf.toString());
			record.put("floatingRateList", floatingRateBuf.toString());
			record.put("rateValList", rateValBuf.toString());
			record.put("paymentModeList", paymentModeBuf.toString());
			record.put("chgCycleUnitList", chgCycleUnitBuf.toString());
			record.put("varCycleList", varCycleBuf.toString());
			record.put("overdueRateCalcModeList", overdueRateCalcModeBuf.toString());
			record.put("overdueIncrRatioList", overdueIncrRatioBuf.toString());
			record.put("calcDaysList", calcDaysBuf.toString());
			record.put("firstLoanFlagList", firstLoanFlagBuf.toString());
			record.put("lastLoanFlagList", lastLoanFlagBuf.toString());
		}
		// 多个担保信息
		Map<String, Object> queryGuarantee = new HashMap<String, Object>();
		queryGuarantee.put("grantCode", grantCode);
		List<BizGuaranteeResult> guaranteeList = bizGuaranteeResultProvider.queryList(queryGuarantee);
		if (CollectionUtils.isNotEmpty(guaranteeList)) {
			StringBuffer typeBuf = new StringBuffer();
			StringBuffer typeParentBuf = new StringBuffer();
			StringBuffer guaranPortTypeBuf = new StringBuffer();
			StringBuffer notClearAmtBuf = new StringBuffer();
			StringBuffer clearRatioBuf = new StringBuffer();
			StringBuffer clearRatioAmtBuf = new StringBuffer();
			StringBuffer pledgeNameBuf = new StringBuffer();
			StringBuffer pledgeStateBuf = new StringBuffer();
			StringBuffer pledgeNoBuf = new StringBuffer();
			StringBuffer schemeNoBuf = new StringBuffer();
			StringBuffer pledgeTypeBuf = new StringBuffer();
			StringBuffer pledgeTypeParentBuf = new StringBuffer();
			for (BizGuaranteeResult item : guaranteeList) {
				String gId = item.getGuaranType();
				typeBuf.append(gId).append("#");
				// 通过编码查询父编码
				if (StringUtils.isNotEmpty(gId)) {
					BizGuaranteeInfo guarantee = bizGuaranteeInfoProvider.queryById(Long.valueOf(gId));
					if (guarantee != null) {
						String code = guarantee.getTypePoint();
						typeParentBuf.append(code).append("#");
					}
				}
				guaranPortTypeBuf.append(item.getGuaranPortType()).append("#");
				// 可明确划分份额
				if ("1".equals(item.getGuaranPortType())) {
					clearRatioBuf.append(item.getClearRatio()).append("#");
					clearRatioAmtBuf.append(item.getClearRatioAmt()).append("#");
					// 与页面数组个数对应
					notClearAmtBuf.append("0").append("#");
				} else {
					// 与页面数组个数对应
					clearRatioBuf.append("0").append("#");
					clearRatioAmtBuf.append("0").append("#");
					//
					notClearAmtBuf.append(item.getNotClearAmt()).append("#");
				}
				pledgeNameBuf.append(item.getPledgeName()).append("#");
				pledgeStateBuf.append(item.getPledgeState()).append("#");
				pledgeNoBuf.append(item.getPledgeNo()).append("#");
				schemeNoBuf.append(item.getSchemeNo()).append("#");
				pledgeTypeBuf.append(item.getPledgeType()).append("#");
				// 通过押品编码查询父编码
				if (StringUtils.isNotEmpty(item.getPledgeType())) {
					PairVo codeVo = bizGuaranteeTypeProvider.getByCode(item.getPledgeType(),
							BizContant.BIZ_BNS_GUARANTEE_TYPE);
					if (codeVo != null) {
						pledgeTypeParentBuf.append(codeVo.getParentCode()).append("#");
					}
				}
			}
			record.put("guaranTypeList", typeBuf.toString());
			record.put("guaranTypeLists", typeParentBuf.toString());// 担保下拉框第一级
			record.put("guaranPortTypeList", guaranPortTypeBuf.toString());
			record.put("notClearAmtList", notClearAmtBuf.toString());
			record.put("clearRatioList", clearRatioBuf.toString());
			record.put("clearRatioAmtList", clearRatioAmtBuf.toString());
			record.put("pledgeNameList", pledgeNameBuf.toString());
			record.put("pledgeStateList", pledgeStateBuf.toString());
			record.put("pledgeNoList", pledgeNoBuf.toString());
			record.put("schemeNoList", schemeNoBuf.toString());
			record.put("pledgeTypeList", pledgeTypeBuf.toString());
			record.put("pledgeTypeList0", pledgeTypeParentBuf.toString());// 押品类型一级
		}
		// 政策属性回显信息
		param.put("debtNum", debtCode);
		List<BizInterestRate> bizInterestRateList = bizInterestRateProvider.queryList(param);
		if (CollectionUtils.isNotEmpty(bizInterestRateList)) {
			System.out.println("------------------查询出了政策属性------");
			BizInterestRate bizInterestRate = bizInterestRateList.get(0);
			String projectName = bizInterestRate.getProjectName();
			if (StringUtils.isNotBlank(projectName)) {
				record.put("projectName", projectName);
			} else {
				if (summaryList != null && summaryList.size() > 0) {
					BizDebtSummary summary = summaryList.get(0);
					System.out.println("------------------查询出了方案------");
					record.put("policy", summary.getPolicy());
				}
			}
			// 如果发放中没有保存政策性属性描述，则使用方案填写的描述
			String policyDescription = bizInterestRate.getPolicyDescription();
			String industryInvestment = bizInterestRate.getIndustryInvestment();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("debtCode", debtCode);
			List<BizSingleProductRule> singleList = singleProductRuleProvider.queryList(queryMap);
			// 如果发放内容是空，则从方案中取
			if (StringUtils.isNotBlank(policyDescription)) {
				record.put("policyDescription", policyDescription);
			} else if (summaryList != null && summaryList.size() > 0) {
				BizDebtSummary summary = summaryList.get(0);
				record.put("policyDescription", summary.getPolicyDescription());
			}
			if (StringUtils.isNotBlank(industryInvestment)) {
				record.put("industryInvestment", industryInvestment);
			} else {
				if (singleList != null && singleList.size() > 0) {
					BizSingleProductRule rule = singleList.get(0);
					record.put("industryInvestment", rule.getIndustryInvestment());
				}
			}
			record.put("traneFinanceTypeList", bizInterestRate.getPolicyAttributeClassify());
			record.put("traneFinanceBusiness", StringUtil.longToString(bizInterestRate.getTraneFinanceBusiness()));
			record.put("policyLendingStatus", StringUtil.longToString(bizInterestRate.getPolicyLendingStatus()));
			record.put("loanNumberAvailable", StringUtil.longToString(bizInterestRate.getLoanNumberAvailable()));
			record.put("creditLoanNum", bizInterestRate.getCreditLoanNum());
			record.put("naturnLoan", StringUtil.longToString(bizInterestRate.getNaturnLoan()));
			String bgnt = bizInterestRate.getBackgroundNationality();
			// 如果管理信息中的背景国别为空，则使用方案的背景国别
			if (StringUtils.isNotBlank(bgnt)) {
				record.put("backgroundNationality", bgnt);
			} else {
				if (singleList != null && singleList.size() > 0) {
					record.put("backgroundNationality", singleList.get(0).getBackgroundNationality());
				}
			}
			record.put("compare", bizInterestRate.getCompare());
			record.put("twoHeadTaller", StringUtil.longToString(bizInterestRate.getTwoHeadTaller()));
			record.put("contrySpecificLoans", StringUtil.longToString(bizInterestRate.getContrySpecificLoans()));
			record.put("mediumEnterpriseLoans", StringUtil.longToString(bizInterestRate.getMediumEnterpriseLoans()));
			record.put("shipFinance", StringUtil.longToString(bizInterestRate.getShipFinance()));
			record.put("tyoesIndustrial", StringUtil.longToString(bizInterestRate.getTyoesIndustrial()));
			record.put("industrialTransformation",
					StringUtil.longToString(bizInterestRate.getIndustrialTransformation()));
			record.put("emergingIndustryClassify", bizInterestRate.getEmergingIndustryClassify());
			record.put("curturalProduct", StringUtil.longToString(bizInterestRate.getCurturalProduct()));
			record.put("alleviationLoan", StringUtil.longToString(bizInterestRate.getAlleviationLoan()));
			record.put("loanServicePeopleNum", StringUtil.longToString(bizInterestRate.getLoanServicePeopleNum()));
			record.put("loanFunResource", bizInterestRate.getLoanFunResource());
			record.put("povertyProperty", bizInterestRate.getPovertyProperty());
			record.put("povertyAddress", bizInterestRate.getPovertyAddress());
			record.put("povertySort", bizInterestRate.getPovertySort());
			record.put("businessContractCode", bizInterestRate.getBusinessContractCode());
			record.put("businessContractDate", DateUtil.format(bizInterestRate.getBusinessContractDate()));
			record.put("businessContractAmount", bizInterestRate.getBusinessContractAmount());
			record.put("businessType", bizInterestRate.getBusinessType());
			record.put("innovativeBusinessType", bizInterestRate.getInnovativeBusinessType());
			record.put("inOverseasTo", bizInterestRate.getInOverseasTo());
			record.put("is421", StringUtil.longToString(bizInterestRate.getIs421()));
			record.put("loanDomain", bizInterestRate.getLoanDomain());
			record.put("importExportGoodsService", bizInterestRate.getImportExportGoodsService());
			record.put("investmentLoanCkassifcation", bizInterestRate.getInvestmentLoanCkassifcation());
			record.put("isSyndicated", bizInterestRate.getIsSyndicated());
			record.put("isSyndicatedAgency", bizInterestRate.getIsSyndicatedAgency());
			record.put("syndicatedStatus", StringUtil.longToString(bizInterestRate.getSyndicatedStatus()));
		}
		// 担保合同回显
		List<BizGuaranteeContract> bizGuaranteeContractList = bizGuaranteeContractProvider.queryList(param);
		record.put("bizGuaranteeContractList", bizGuaranteeContractList);
		// 还款计划回显
		List<BizRepaymentPlan> bizRepaymentPlanList = bizRepaymentPlanProvider.queryList(param);
		if (CollectionUtils.isNotEmpty(bizRepaymentPlanList)) {
			BizRepaymentPlan bizRepaymentPlan = bizRepaymentPlanList.get(0);
			record.put("fristRepayDate", DateUtil.format(bizRepaymentPlan.getFirstRepayDay()));
			record.put("lastRepayDay", DateUtil.format(bizRepaymentPlan.getLastRepaymentDay()));
			record.put("repaymentCostMode", StringUtil.longToString(bizRepaymentPlan.getRepaymentCostMode()));
			record.put("firstDay", DateUtil.format(bizRepaymentPlan.getFirstDay()));
			record.put("caclWay", StringUtil.longToString(bizRepaymentPlan.getCaclWay()));
		}
		//获取币种信息
		List<SysCurrency> currencyList =  getCurrencyListByRedis();
		// 还本信息
		Page queryPage = getPage(param);
		queryPage.setSize(1000);
		queryPage.setAsc(true);
		queryPage.setOrderByField("PAY_DATE");
		List<Long> bizRepaymentPricinalPlanIds = bizRepaymentPricinalPlanProvider.selectIdPage(queryPage, param);
		List<BizRepaymentPricinalPlan> pricinalPlanList = bizRepaymentPricinalPlanProvider.queryList(bizRepaymentPricinalPlanIds);
		List<Map<String, Object>> payList = new ArrayList<Map<String, Object>>();
		for (BizRepaymentPricinalPlan bizRepaymentPricinalPlan : pricinalPlanList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("payDate", DateUtil.format(bizRepaymentPricinalPlan.getPayDate()));
			map.put("principalAmy", bizRepaymentPricinalPlan.getPrincipalAmy());
			String code = grantModel.getCurrency();
			//通过币种编码获取币种名称
			SysCurrency currency = this.getItemCurrencyByCode(code, currencyList);
			if(currency != null){
				map.put("currency", currency.getCodeName());
			}else{
				map.put("currency", code);
			}
			payList.add(map);
		}
		// 还息信息
		queryPage.setOrderByField("INTEREST_DATE");
		List<Long> bizRepaymentLoanPlanIds = bizRepaymentLoanPlanProvider.selectIdPage(queryPage, param);
		List<BizRepaymentLoanPlan> loanPlanList = bizRepaymentLoanPlanProvider.queryList(bizRepaymentLoanPlanIds);
		List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
		for (BizRepaymentLoanPlan bizRepaymentLoanPlan : loanPlanList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("interestDate", DateUtil.format(bizRepaymentLoanPlan.getInterestDate()));
			loanList.add(map);
		}
		record.put("payList", payList);
		record.put("loanList", loanList);
		// 客户主体信息
		Map<String, Object> debtCodeMap = new HashMap<>();
		debtCodeMap.put("debtCode", debtCode);
		List<BizCustomer> customerList = bizCustomerProvider.getBizCustomerList(debtCodeMap);
		record.put("custUserLetter", customerList);
		// 如果是变更流程，则把老编号放到页面去
		String oldGrantCode = grantModel.getOriginalGrantCode();
		if (StringUtils.isNotBlank(oldGrantCode)) {
			// 原流水号传递到页面
			record.put("originalGrantCode", oldGrantCode);
		}
		// 生成新的流水号与原申请关联
		// String debtNo = StringUtils.substring(debtCode, 0, 13);
		String nextCode = bizCntProvider.getNextNumberFormat(debtCode, 3);
		// 新生成的发放编码
		record.put("NewCode", nextCode);
		record.put("grantCode", grantCode);
		record.put("debtCode", debtCode);
		return record;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BizDebtGrant saveDiscard(Map<String, Object> record) {
		Long id = StringUtil.objectToLong(record.get("id"));
		Long createBy = StringUtil.objectToLong(record.get("createBy"));
		String reason = StringUtil.objectToString(record.get("reason"));
		BizDebtGrant model = this.queryById(id);
		String grantCode = model.getGrantCode();
		// 修改债项发放方案状态
		model.setGrantStatus(BizStatus.GRANABAT);
		model.setRemark(reason);
		model.setUpdateBy(createBy);
		model.setUpdateTime(new Date());
		BizDebtGrant result = update(model);
		if (result != null) {
			// 发起流程相关的查询
			List<SysUserRole> list = (List) record.get("list");
			Map<String, Object> params = new HashMap<String, Object>();
			for (SysUserRole sysUserRole : list) {
				String roleId = sysUserRole.getRoleId().toString();
				// 发起审核流程
				params.put("userId", createBy);
				params.put("mBizId", grantCode);
				if ("451117558502785025".equals(roleId)) {
					params.put("pdid", "fqprocess");
				} else if ("451117558506979339".equals(roleId)) {
					params.put("pdid", "zhfqprocess");
				}
			}
			String debtCode = StringUtil.objectToString(record.get("debtCode"));
			params.put("debtCode",debtCode);
			bizProStatementProvider.createAndstartProcess(params);
		}
		return result;
	}

	/**
	 * 功能：根据编码查询担保类型对象
	 * 
	 * @param code
	 * @return
	 */
	private BizGuaranteeType getBizGuaranteeType(String code) {
		// 查询所有担保类型
		BizGuaranteeType params = new BizGuaranteeType();
		params.setEnable(BizContant.ENABLE_YES);
		params.setType(BizContant.BIZ_BNS_INTEREST_RATE_TYPE);
		params.setCode(code);
		params.setParentCode("0");
		BizGuaranteeType type = bizGuaranteeTypeProvider.selectOne(params);
		return type;
	}

	@Override
	public List<Map<String, Object>> getTempList(Map<String, Object> param) {
		return bizDebtGrantMapper.getTempList(param);
	}

	@Override
	public BizDebtGrant selectByGrantId(String grantId) {
		return bizDebtGrantMapper.selectById(grantId);
	}

	@Override
	public Page<BizDebtGrant> queryByHundred(Map<String, Object> param) {
		Page page = getPage(param);
		List<BizDebtGrant> debtGrantList = this.queryList(param);
		Integer compareIndex = debtGrantList.size() / 100;
		compareIndex = compareIndex + 1;
		if (compareIndex < page.getCurrent()) {
			page.setCurrent(compareIndex);
		}
		page.setSize(100);
		List<Long> bizDebtGrantIdList = bizDebtGrantMapper.selectIdPage(page, param);
		page.setRecords(bizDebtGrantIdList);
		return getPage(page);
	};
}
