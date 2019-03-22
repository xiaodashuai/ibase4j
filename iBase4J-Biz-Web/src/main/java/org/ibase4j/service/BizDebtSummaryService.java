package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.cache.RedisHelper;
import org.ibase4j.core.util.*;
import org.ibase4j.model.*;
import org.ibase4j.provider.*;
import org.ibase4j.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
=======
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiaoshuiquan
 * @version 2018年5月26日 下午3:47:21
 */
@Service
public class BizDebtSummaryService extends BaseService<BizDebtSummaryProvider,BizDebtSummary> {
    @Reference
    private IBizProductTypeProvider bizProductTypeProvider;
    @Reference
    private ISysUserRoleProvider sysUserRoleProvider;
    @Reference
    private ISysUserProvider sysUserProvider;
    @Reference
    private ISysDeptProvider sysDeptProvider;
    @Reference
    private ISysDicProvider sysDicProvider;
    @Reference
    private BizSingleProductRuleProvider singleProductRuleProvider;
    @Reference
    private BizCntProvider bizCntProvider;
    @Reference
    private BizDebtGrantProvider debtGrantProvider;
    @Reference
    private BizTRNProvider trnProvider;
    @Reference
    private BizGuaranteeTypeProvider guaranteeTypeProvider;
    @Reference
    private BizCustProvider bizCustProvider;
    @Reference
    private BizGuaranteeTypeProvider bizGuaranteeTypeProvider;
    @Reference
    private BizGuaranteeInfoProvider bizGuaranteeInfoProvider;
    @Reference
    private BizBetInformationProvider bizBetInformationProvider;
    @Reference
    private BizFileProvider bizFileProvider;
    @Reference
    private BizProductLinesTypeProvider bizProductLinesTypeProvider;
    @Reference
    private BizTheRentFactoringProvider bizTheRentFactoringProvider;
    @Reference
    private BizPTSProvider bizPTSProvider;
    @Reference
    private BizCreditLinesProvider bizCreditLinesProvider;
    @Reference
    private BizCbsErrorMessageProvider bizCbsErrorMessageProvider;
    @Reference
    private BizTemporarySaveProvider bizTemporarySaveProvider;
    @Autowired
    private RedisHelper redisHelper;
    @Reference
    private ISysCurrencyProvider iSysCurrencyProvider;
    @Reference
    private BizTRNProvider bizTRNProvider;
    @Reference
    private BizDebtSummaryProvider bizDebtSummaryProvider;
    @Reference
    private BizApprSummaryInfoProvider bizApprSummaryInfoProvider;

    @Reference
    public void setProvider(BizDebtSummaryProvider debtSummaryProvider) {
        this.provider = debtSummaryProvider;
    }
    /**
     * 功能:获取所有产品与品种的关联信息,
     * 2018/7/19日进行了修改，
     * 修改理由：对之前产品种类3张表合并成了一张表BIZ_PRODUCT_TYPES
     * 日期：2018/7/19 <br/>
     * @author 修改人czm
     * @param params
     * @return
     */
    public Set<PairVo> getAllBusinessType(Map<String, Object> params){
        Set<PairVo> resultSet = InstanceUtil.newHashSet();
        //生成债项方案id
        String debtCode="CLD";
        debtCode=debtCode+ DateUtil.getDateYearMonth();

        debtCode=bizCntProvider.getNextNumberFormat(debtCode, 4);

        //首先查询第一级节点
        List<BizProductTypes> result = bizProductTypeProvider.getRoot();
        for(BizProductTypes bean : result){
            String code = bean.getCode();
            String parentCode = bean.getParentCode();
            String name = bean.getName();
            Integer children = bean.getIsChild();
            //产品种类一级
            PairVo vo = new PairVo();
            vo.setDebtCode(debtCode);
            vo.setCode(code);
            vo.setParentCode(parentCode);
            vo.setName(name);
            //如果存在子节点。那么递归查询
            if(Integer.valueOf(1).equals(children)){
                vo.setChildren(true);
                setChild(code, vo,debtCode);
            }else{
                vo.setChildren(false);
            }
            resultSet.add(vo);
        }
        //
        return resultSet;
    }
    /**
     * 功能：递归查询所有子节点
     * @param parentCode
     * @param root
     */
    private void setChild(String parentCode,PairVo root,String debtCode){
        Set<PairVo> resultSet = InstanceUtil.newHashSet();
        List<BizProductTypes> result = bizProductTypeProvider.getByParentCode(parentCode);
        for(BizProductTypes bean : result){
            String code = bean.getCode();
            String name = bean.getName();
            String pCode = bean.getParentCode();
            Integer children = bean.getIsChild();
            //产品种类一级
            PairVo vo = new PairVo();
            vo.setDebtCode(debtCode);
            vo.setCode(code);
            vo.setParentCode(pCode);
            vo.setName(name);
            //如果存在子节点。那么递归查询
            if(Integer.valueOf(1).equals(children)){
                vo.setChildren(true);
                setChild(code, vo,debtCode);
            }else{
                vo.setChildren(false);
            }
            resultSet.add(vo);
        }
        //添加到父节点
        root.setChirenList(resultSet);
    }

    /**查询出信贷员*/
    public SysUser getUserList(){
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        return user;
    }

    /** 查询用户所在机构*/
    public SysDept getDeptList(){
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        SysDept dept=sysDeptProvider.queryDeptByCode(user.getDeptCode());
        return  dept;
    }


    /**查询字典的是否*/
    public List<SysDic> queryDicByType(String type) {
        Assert.notNull(type, "KEY");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type", type);
        params.put("orderBy", "sort_no");
        return sysDicProvider.queryList(params);
    }


    /**保存产品种类细类*/
    public void savePro(String proIds,String debtNum ) {
        singleProductRuleProvider.savePro(proIds,debtNum.toString());
    }


    /**
     * 功能：查询勾选的产品细类，2018/7/19日进行了修改，
     * 修改理由：对之前产品种类3张表合并成了一张表BIZ_PRODUCT_TYPES
     * 日期：2018/7/19 <br/>
     * @author czm
     * @param product
     * @return
     */
    public List<ProductBussinessVo> getProduct(Map<String, String> product) {
        Long debrNum=Long.valueOf(product.get("debtNum"));
        Map<String,Object> params = new HashMap<>();
        params.put("debtNum", debrNum);
        List<BizSingleProductRule> singleList = singleProductRuleProvider.queryList(params);
        List<ProductBussinessVo> bVoList= new ArrayList<ProductBussinessVo>();
        for (BizSingleProductRule b:singleList){
            ProductBussinessVo vo = new ProductBussinessVo();
            String code = b.getBusinessType();
            BizProductTypes bean = bizProductTypeProvider.getByCode(code);
            if(bean!=null){
                vo.setCode(bean.getCode());
                vo.setName(bean.getName());
                vo.setSingleId(b.getId().toString());
                vo.setParentCode(bean.getParentCode());
                vo.setPgEffectiveDate(null);
                vo.setPgExpiDate(null);
                vo.setPtEffectiveDate(null);
                vo.setPtExpiDate(null);
                vo.setPtLs(null);
            }
            //加入
            bVoList.add(vo);
        }
        return bVoList;
    }


    /**保存债项概要页面的单一效期规则*/
    public void saveSingleRule(ProductBussinessVo proVo) {
        BizSingleProductRule single=new BizSingleProductRule();
        //单一产品规则表id
        single.setId(Long.valueOf(proVo.getSingleId()));
        //业务编号
        single.setDebtCode(proVo.getDebtNum());
        //业务名称
        single.setBusinessType(proVo.getCode());
        //产品生效日期
       /* single.setPtEffectiveDate(proVo.getPtEffectiveDate());
        //产品失效日期
        single.setPtExpiDate(proVo.getPtExpiDate());
        // 产品循环标志
        single.setPtLs(proVo.getPtLs());
        //方案生效日期
        single.setPgEffectiveDate(proVo.getPgEffectiveDate());
        //方案失效日期
        single.setPgExpiDate(proVo.getPgExpiDate());*/
        singleProductRuleProvider.update(single);
    }

    /**
     * 描述：根据主键获取单一产品规则
     * @param sprId
     * @return
     */
    public ProductVo getProductPairBySingleProductRuleId(Long sprId){
        return singleProductRuleProvider.getProductPairBySingleProductRuleId(sprId);
    }

    /**
     * 功能：查询方案中选中的所有产品
     * 作者：陈志明
     * 日期：2018/7/12
     * 参数：debtCode
     * 返回：返回债项方案中的产品列表
     */
    public List<ProductVo> getProduct(String debtCode){
        return singleProductRuleProvider.getProductList(debtCode);
    }

	/**
	 * 功能：查询已审核通过的债项方案 作者：陈志明 日期：2018/7/12 参数：查询参数map 返回：返回债项方案信息
	 */
	public Page<BizDebtSummary> queryByCompletedSolutions(Map<String, Object> params) {
		Long userId = BizWebUtil.getCurrentUser();
		SysUser loginUser = sysUserProvider.queryById(userId);
		// TODO 存入部门编号和岗位编号，进行数据权限过滤
		// params.put("roleNo", "");
		SysDept dept = sysDeptProvider.queryDeptByCode(loginUser.getDeptCode());
		params.put("institutionCode", dept.getParentCode());
		Page<BizDebtSummary> result = provider.queryByCompletedSolutions(params);
		for (BizDebtSummary model : result.getRecords()) {
			String code = model.getMpc();
			SysCurrency currency = iSysCurrencyProvider.getByCode(code);
			if(currency!=null){
				model.setMpc(currency.getCodeName());
			}
		}
		return result;
	}
    /**
     * 功能：通过业务编号查找债项方案
     * 作者：陈志明
     * 日期：2018/7/17
     * 参数：查询参数业务编号
     * 返回：返回债项方案信息
     */
    public BizDebtSummary getByCode(String debtCode){
        BizDebtSummary bizDebtSummary = new BizDebtSummary();
        bizDebtSummary.setDebtCode(debtCode);
        bizDebtSummary = bizDebtSummaryProvider.selectOneBizDebtSummary(bizDebtSummary);
        return bizDebtSummary;
    }

    /**删除债项概要页面展示的产品*/
    public void deleteProduct(Long singleId) {
        singleProductRuleProvider.delete(singleId);
    }

	/**
	 * 功能：查询发放约束条件(后台首先生成流水号返到页面)
	 * 
	 * @param pid
	 * @param debtCode
	 * @return
	 */
	public GrantRuleVerifVo getGrantRuleVoCode(Long pid, String debtCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (pid != null) {
			params.put("properInfo", pid);
		} else {
			params.put("properInfo", "0");
		}
		String debtNo = StringUtils.substring(debtCode, 0, 13);
		params.put("debtCode", debtNo);
		List<GrantRuleVerifVo> dsList = provider.getGrantRuleVo(params);
		GrantRuleVerifVo ds = new GrantRuleVerifVo();
		if (dsList != null && dsList.size() > 0) {
			ds = dsList.get(0);
			// 判断是否需要初始化发放编码
			if (StringUtils.isNotBlank(debtCode)) {
				// String debtNo = StringUtils.substring(debtCode, 0, 13);
				String grantCode = bizCntProvider.getNextNumberFormat(debtCode, 3);
				ds.setGrantCode(grantCode);
			}
		}
		return ds;
	}

    /**
     * 保存债项方案
     * @param params
     * + 数据存储形式调整;
     */
    public void save(JSONObject params) {

        //得到债项方案的id
        String debtCode=(String)params.get("debtCode");
        Integer vernum = null;
        //PTS List保存用户的角色信息
        List<BizPTS> ptsList=new ArrayList<>();

        //流程改造，更新方案号，增加版本号
        if(null != debtCode && debtCode.length()==16){
            //版本号从前台获取，处理附件关联历史修订的问题（同时修订）
            vernum = Integer.parseInt(debtCode.substring(13));
            debtCode = debtCode.substring(0,13);
        }else{
            throw new RuntimeException("未获取到方案编号！BizDebtSummarySerivice Line : 342 ;");
        }

        //判断是补录的保存，还是修订的保存
        String state=params.get("state").toString();
        //得到债项信息表数据
        BizDebtSummary bizDebtSummary=JSON.parseObject(JSON.toJSONString(params.get("debtMain")),BizDebtSummary.class);
        Long idDebtSummary = null;

        if(state.equals("方案补录")){

            idDebtSummary = IdWorker.getId();
            bizDebtSummary.setId(idDebtSummary);
            bizDebtSummary.setVerNum(vernum);
            bizDebtSummary.setDebtCode(debtCode);

        }else if(state.equals("方案修订")){

//            vernum = bizCntProvider.getNextNumber(debtCode);
            BizDebtSummary selDebt = new BizDebtSummary();
            selDebt.setDebtCode(debtCode);
            BizDebtSummary oldDebt = provider.selectOne(new EntityWrapper<>(selDebt));
            idDebtSummary = oldDebt.getId();
            bizDebtSummary.setId(idDebtSummary);
            bizDebtSummary.setDebtCode(debtCode);
            bizDebtSummary.setVerNum(vernum);
        }

        Map<String, Object> tmpSaveParams = new HashMap<String, Object>();
        tmpSaveParams.put("bizcode",debtCode);
        tmpSaveParams.put("remark",vernum);
        tmpSaveParams.put("projectName","ReSubmit_debtMain");

        Map reviewObj = (Map)params;
//        boolean saveResSaveFile = provider.saveReSubmitFile(reviewObj, tmpSaveParams);
        boolean saveResSaveFile = bizTemporarySaveProvider.saveTemporary(new BizTemporarySave(reviewObj), tmpSaveParams);
        if(null == reviewObj || !saveResSaveFile){
            logger.error("saveReSubmitFile异常...");
            throw new RuntimeException("save ReSubmit_debtMain ERROR...");
        }

        Date dat = new Date();

        //全局规则
        bizDebtSummary.setRuleType(1L);
        //流程发起时间
        bizDebtSummary.setProcessInitiatTime(dat);
        bizDebtSummary.setCreateTime(dat);
        bizDebtSummary.setUpdateTime(dat);
        bizDebtSummary.setCreateBy(bizDebtSummary.getBankTellerId());
        bizDebtSummary.setUpdateBy(bizDebtSummary.getBankTellerId());
        bizDebtSummary.setEnable(1);
        bizDebtSummary.setCreatePersonName(bizDebtSummary.getCreatePersonName());

//        bizDebtSummary.setDebtCode(bizDebtSummary.getDebtCode());
//        bizDebtSummary.setVerNum(bizCntProvider.getNextNumber(bizDebtSummary.getDebtCode()));

        BizPTS mainPts = new BizPTS();
        BizCust selMainCust = new BizCust();
        selMainCust.setCustNo(bizDebtSummary.getProposerNum());
        mainPts.setPtyinr(bizCustProvider.selectOne(new EntityWrapper<>(selMainCust)).getId_());
        mainPts.setCustNameCN(bizDebtSummary.getProposer());
        mainPts.setObjtyp("BIZ_DEBT_MAIN");
        mainPts.setObjinr(idDebtSummary+"");
        mainPts.setRole("APPT");
<<<<<<< HEAD
        mainPts.setDebtCode(debtCode);
=======
        mainPts.setBizId(idDebtSummary+"");
        BizPTS selmainPts = bizPTSProvider.selectOne(new EntityWrapper<BizPTS>(mainPts));
        if(null != selmainPts){
            mainPts.setId(selmainPts.getId());
        }
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
        mainPts.setCreateBy(bizDebtSummary.getBankTellerId());
        mainPts.setUpdateBy(bizDebtSummary.getBankTellerId());
        ptsList.add(mainPts);

        //文件签发日期
        JSONArray bizFileList=JSON.parseArray(JSON.toJSONString(params.get("issueDateList")));
        BizFile bizfile = new BizFile();
        String debtCode2 = debtCode + "001";
        bizfile.setBizCode(debtCode2);
        List<BizFile> fieldList = bizFileProvider.selectList(new EntityWrapper<>(bizfile));
        for (BizFile fdn:fieldList) {
            if ("Meetingupload".equals(fdn.getFieldName()) && null == fdn.getIssueDate()){
                for (Object bz:bizFileList) {
                    String bbz = bz.toString();
                    Date bzz = DateUtil.stringToDate(bbz);
                    fdn.setIssueDate(bzz);
                    bizFileProvider.update(fdn);
                }
            }
        }

        //得到担保信息表数据
        List<BizGuaranteeInfo> bizGuaranteeInfoList=JSON.parseArray(JSON.toJSONString(params.get("bizGuaranteeInfoList")),BizGuaranteeInfo.class);
        List<BizBetInformation> bizBetInformationList = new ArrayList<>();
        for(BizGuaranteeInfo guarantee:bizGuaranteeInfoList){
            guarantee.setDebtCode(debtCode);
            Long idGuar = IdWorker.getId();
            guarantee.setId(idGuar);
            guarantee.setCreateBy(bizDebtSummary.getBankTellerId());
            guarantee.setUpdateBy(bizDebtSummary.getBankTellerId());
            guarantee.setCreateTime(dat);
            guarantee.setUpdateTime(dat);
            for (BizBetInformation bet:guarantee.getBetInformationList()){
                //担保合同编号
                bet.setGuarNo(guarantee.getWarrantyContractNumber());
                bet.setDebtCode(bizDebtSummary.getDebtCode());
                bet.setId(IdWorker.getId());
                bet.setCreateTime(dat);
                bet.setUpdateTime(dat);
                bet.setCreateBy(bizDebtSummary.getBankTellerId());
                bet.setUpdateBy(bizDebtSummary.getBankTellerId());
                bizBetInformationList.add(bet);
            }

            BizPTS guaranteePts = new BizPTS();
            BizCust selGuaranteeCust = new BizCust();
            selGuaranteeCust.setCustNo(guarantee.getGuarantorCustId());
            guaranteePts.setPtyinr(bizCustProvider.selectOne(new EntityWrapper<>(selGuaranteeCust)).getId_());
            guaranteePts.setCustNameCN(guarantee.getGuarantor());
            guaranteePts.setObjtyp("BIZ_GUARANTEE_INFO");
            guaranteePts.setObjinr(idGuar+"");
            guaranteePts.setRole("GUAR");
            guaranteePts.setDebtCode(debtCode);
            guaranteePts.setCreateBy(bizDebtSummary.getBankTellerId());
            guaranteePts.setUpdateBy(bizDebtSummary.getBankTellerId());
            ptsList.add(guaranteePts);

            //添加被担保人 warrantee
            if(null!=guarantee.getWarranteeCustId()){
                BizPTS warranteePts = new BizPTS();
                BizCust selWarranteeCust = new BizCust();
                selWarranteeCust.setCustNo(guarantee.getWarranteeCustId());
                warranteePts.setPtyinr(bizCustProvider.selectOne(new EntityWrapper<>(selWarranteeCust)).getId_());
                warranteePts.setCustNameCN(guarantee.getWarrantee());
                warranteePts.setObjtyp("BIZ_GUARANTEE_INFO");
                warranteePts.setObjinr(idGuar+"");
                warranteePts.setRole("WARR");
                warranteePts.setDebtCode(debtCode);
                warranteePts.setCreateBy(bizDebtSummary.getBankTellerId());
                warranteePts.setUpdateBy(bizDebtSummary.getBankTellerId());
                ptsList.add(warranteePts);
            }
        }

        List<BizProductTypes> rentFacList=JSON.parseArray(JSON.toJSONString(params.get("rentFacList")),BizProductTypes.class);
        List<BizSingleProductRule> bizSingleProductRuleList=new ArrayList<>();
        List<BizProductLinesType> productLinesTypesList=new ArrayList<>();
//        Set<BizCust> cuSet=new HashSet<>();

        List<BizTheRentFactoring> rentList=new ArrayList<>();
        List<BizCreditLines> bizCreditLinesList=new ArrayList<>();
        for (BizProductTypes productTypes:rentFacList){
            //得到单一规则表
            BizSingleProductRule s=new BizSingleProductRule();
            long sigleId = IdWorker.getId();
            s.setId(sigleId);
            s.setCreateTime(dat);
            s.setUpdateTime(dat);
            s.setCreateBy(bizDebtSummary.getBankTellerId());
            s.setUpdateBy(bizDebtSummary.getBankTellerId());
            s.setBusinessType(productTypes.getCode());
            s.setDebtCode(debtCode);
            s.setIndustryInvestment(productTypes.getIndustryTo());
            s.setBackgroundNationality(productTypes.getTbon());
            bizSingleProductRuleList.add(s);

            //得到租金保理表数据
            BizTheRentFactoring rent=new BizTheRentFactoring();
            Long idRent = IdWorker.getId();
            rent.setTolerancePertod(productTypes.getTolerancePertod());
            rent.setDebtCode(debtCode);
            rent.setBusinessTypes(productTypes.getCode());
            rent.setCustNo(productTypes.getCustNo());
            rent.setCustName(productTypes.getCustName());
            rent.setCustTating(productTypes.getCustTating());
            rent.setTolerancePertod(productTypes.getTolerancePertod());
            rent.setFinancePlatform(productTypes.getFinancePlatform());
            rent.setCreateTime(dat);
            rent.setUpdateTime(dat);
            rent.setCreateBy(bizDebtSummary.getBankTellerId());
            rent.setUpdateBy(bizDebtSummary.getBankTellerId());
            rentList.add(rent);
            BizCust selRentCust = new BizCust();
            selRentCust.setCustNo(productTypes.getCustNo());
            BizPTS rentPts = new BizPTS();
            rentPts.setPtyinr(bizCustProvider.selectOne(new EntityWrapper<>(selRentCust)).getId_());
            rentPts.setCustNameCN(productTypes.getCustName());
            rentPts.setObjtyp("BIZ_RENTAL_FACTORING_KEY");
            rentPts.setObjinr(idRent+"");
            rentPts.setRole("CONE");
            rentPts.setDebtCode(debtCode);
            rentPts.setCreateBy(bizDebtSummary.getBankTellerId());
            rentPts.setUpdateBy(bizDebtSummary.getBankTellerId());
            ptsList.add(rentPts);

            //得到额度类型表数据  用信主体
            List<BizCust> custList = productTypes.getCustomersList();

            //存储没有重复客户的list
//            List<BizCust> cuList = new ArrayList<>();

            for(BizCust cus:custList){
                //得到额度类型表数据
                BizProductLinesType lines=new BizProductLinesType();
                lines.setId(IdWorker.getId());
                lines.setDebtCode(debtCode);
                lines.setCustNo(cus.getCustNo());
                lines.setObjtyp("BIZ_SIGLE_PRODUCT_RULE");
                lines.setObjinr(sigleId+"");
                lines.setBusinessType(productTypes.getCode());
                lines.setCreditLinesId(cus.getCreditLinesId());
                lines.setCreditRatio(cus.getCreditRatio());
                lines.setCreateBy(bizDebtSummary.getBankTellerId());
                lines.setUpdateBy(bizDebtSummary.getBankTellerId());
                lines.setCreateTime(dat);
                lines.setUpdateTime(dat);
                productLinesTypesList.add(lines);
                //得到客户表数据
//                cuSet.add(cus);
                BizPTS custPts = new BizPTS();
                //前台传递的参数不包含BizCust的id，需要后台根据编号查询，编号唯一
                BizCust selCust = new BizCust();
                selCust.setCustNo(cus.getCustNo());
                long custid = bizCustProvider.selectOne(new EntityWrapper<>(selCust)).getId();
                custPts.setPtyinr(custid+"");
                custPts.setDebtCode(debtCode);
                custPts.setCustNameCN(cus.getCustNameCN());
                custPts.setObjtyp("BIZ_SIGLE_PRODUCT_RULE");
                custPts.setObjinr(sigleId+"");
                custPts.setRole("LETS");
                custPts.setDebtCode(debtCode);
                custPts.setCreateBy(bizDebtSummary.getBankTellerId());
                custPts.setUpdateBy(bizDebtSummary.getBankTellerId());
                ptsList.add(custPts);

                //保存用户主体授信信息表
                for (BizCreditLines li: cus.getCreditLinesList()){
                    li.setId(IdWorker.getId());
                    li.setDebtCode(debtCode);
                    li.setCustomerId(custid);
                    li.setCustNo(cus.getCustNo());
                    li.setObjtyp("BIZ_DEBT_MAIN");
                    li.setObjinr(idDebtSummary+"");
                    li.setCreateBy(bizDebtSummary.getBankTellerId());
                    li.setUpdateBy(bizDebtSummary.getBankTellerId());
                    li.setCreateTime(dat);
                    li.setUpdateTime(dat);
                    bizCreditLinesList.add(li);
                }
            }
        }

        Long userId = BizWebUtil.getCurrentUser();
        Map map=new HashMap();
        map.put("userId",userId);
        List<SysUserRole> list = sysUserRoleProvider.selectOneSysUserRole(map);

        Map<String,Object> mapObj=new HashMap<>();
        mapObj.put("bizDebtSummary", bizDebtSummary);
        mapObj.put("bizGuaranteeInfoList", bizGuaranteeInfoList);
        mapObj.put("bizSingleProductRuleList", bizSingleProductRuleList);
        mapObj.put("rentList", rentList);
        mapObj.put("productLinesTypesList", productLinesTypesList);
        mapObj.put("userId", userId.toString());
        mapObj.put("list", list);
        mapObj.put("ptsList", ptsList);
        mapObj.put("bizCreditLinesList", bizCreditLinesList);
        mapObj.put("bizBetInformationList", bizBetInformationList);

        for(BizPTS ptstmp : (List<BizPTS>)ptsList){
            if(null == ptstmp.getPtyinr()){
                logger.error("未获取到客户表biz_cust的id。");
                throw new RuntimeException("ptyinr is null!");
            }
        }
        provider.saveDebt(mapObj);
    }


    //查询出租金保理
    public List<BizProductTypes> getRentFactorList() {
        //得到经营单位代码
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        SysDept dept=sysDeptProvider.queryDeptByCode(user.getDeptCode());

        //生成债项方案id
        String debtCode=dept.getUnitId();
        if (debtCode==null || debtCode.equals(0)){
            debtCode="CLB";
        }
        debtCode=debtCode+ DateUtil.getDateYearMonth();
        debtCode=bizCntProvider.getNextNumberFormat(debtCode, 4);

//        debtCode=debtCode+"001";
        debtCode=bizCntProvider.getNextNumberFormat(debtCode, 3);
//        int versionNum = bizCntProvider.getNextNumber(debtCode);

        Map<String,Object> rentMap=new HashMap<>();
        rentMap.put("parentCode","PT0500000000");
        List<BizProductTypes>proList=bizProductTypeProvider.queryList(rentMap);
        for(BizProductTypes b:proList){
            b.setDebtCode(debtCode);
        }
        return proList;
    }

    //查看债项方案
    public Page<?> checkDebtScheme(Map<String,Object> ownref) {
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        SysDept sysDept=sysDeptProvider.queryDeptByCode(user.getDeptCode());
        String deptCod=sysDept.getParentCode();
        if(!deptCod.equals("1550000")){
            ownref.put("deptCod", deptCod);
        }
        //2是冻结，7是失效，8是删除
        ownref.put("solutionState2", 2);
        ownref.put("solutionState7", 7);
        ownref.put("solutionState8", 8);
        ownref.put("relflg", "Y");
        Page<BizDebtSummary> bizdebtList= (Page<BizDebtSummary>) provider.getDebtInfo(ownref);
        return bizdebtList;
    }

    //查询行业投向
    public List<BizGuaranteeType> getIndustry(String bizBnsIndustryType) {
        Map<String,Object> proMap=new HashMap<>();
        proMap.put("type", bizBnsIndustryType);
        List<BizGuaranteeType>guaranteeTypeList=guaranteeTypeProvider.queryList(proMap);
        return guaranteeTypeList;
    }

    //查询全部的静态客户
    public List<BizCust> getCustomerList(Map<String, Object> customer) {
        String pattern=StringUtil.objectToString(customer.get("custNo"));
        long startt = System.currentTimeMillis();
        Set<Object> custSets= redisHelper.getCustAll(Constants.CACHE_BIZCUST_NAMESPACE+"*"+pattern+"*");
        List<BizCust> custList=new ArrayList<BizCust>();
        for (Object custStr : custSets) {
            custList.add(JSON.parseObject(custStr.toString(),BizCust.class));
        }
        logger.debug("客户模糊查询时间==="+(System.currentTimeMillis()-startt));
        return custList;
    }

    //得到担保方式类型
    public List<BizGuaranteeType> getMortgageList(Map<String, Object> params) {
        String eve=params.get("eve").toString();
        Map<String,Object> map=new HashMap<>();
        map.put("parentCode", eve);
        map.put("type", "001");
        List<BizGuaranteeType>guList=bizGuaranteeTypeProvider.queryList(map);
        return guList;
    }

    //方案修订的回显
//    public Map<String,Object> getAllSchemeInformation(Map<String, Object> param) {
//
//        String debtCode=param.get("debtCode").toString();
//        String olddebtCode=param.get("debtCode").toString();
//
//        if(null != debtCode && debtCode.length()==16){
//            Integer vernum = Integer.parseInt(debtCode.substring(13));
//            debtCode = debtCode.substring(0,13);
//
//            Map<String, Object> oldParams = new HashMap<String, Object>();
//            oldParams.put("bizcode",debtCode);
//            oldParams.put("remark",vernum);
//            oldParams.put("projectName","ReSubmit_debtMain");
//            Object oldTempSave = bizTemporarySaveProvider.getTemporary(oldParams);
//            //存库的数据从文件中获得
//            if(null != oldTempSave){
//                //附件和变更号绑定 + 需要更新变更号
//                JSONObject oldDebt = (JSONObject)oldTempSave;//(Map<String, Object>)
//                String newCode = bizCntProvider.getNextNumberFormat("FA_"+debtCode,3);
//
//                //将旧数据方案号更新
//                oldDebt.put("debtCode",newCode);
//                Map<String, Object> newDebtMain = (Map<String, Object>)oldDebt.get("debtMain");
//                newDebtMain.put("debtCode",newCode);
//                JSONArray bizGuaranteeInfoList = (JSONArray)oldDebt.get("bizGuaranteeInfoList");
//                if(null != bizGuaranteeInfoList && bizGuaranteeInfoList.size()>0){
//                    for (int i = 0; i < bizGuaranteeInfoList.size(); i++) {
//                        JSONObject bizGuaranteeInfo = bizGuaranteeInfoList.getJSONObject(i);
//                        if(null != bizGuaranteeInfo.getString("debtCode")){
//                            bizGuaranteeInfo.put("debtCode",newCode);
//                        }
//                    }
//                }
//                JSONArray rentFacList = (JSONArray)oldDebt.get("rentFacList");
//                if(null != rentFacList && rentFacList.size()>0){
//                    for (int i = 0; i < rentFacList.size(); i++) {
//                        JSONObject rentFac = rentFacList.getJSONObject(i);
//                        if(null != rentFac.getString("debtCode")){
//                            rentFac.put("debtCode",newCode);
//                        }
//                    }
//                }
//
//                //附件复制（数据复制、文件复制）
//                EntityWrapper<BizFile> sel = new EntityWrapper<BizFile>();
//                sel.eq("BIZ_CODE",olddebtCode).orderBy("CREATE_TIME",true);
//                List<BizFile> bizFileList = bizFileProvider.selectList(sel);
//                if(null!=bizFileList && bizFileList.size()>0){
//                    for(BizFile bizfile : bizFileList){
//                        bizfile.setBizCode(newCode);
//
//                        try {
//                            String filePath = PropertiesUtil.getString("uploader.dir");
//                            String newfilePath = filePath+"/"+newCode+"/"+bizfile.getCreateBy()+"/"+DateUtil.getDateYYYYMMDD()+"/";
//                            File f = new File(newfilePath);
//                            if (!f.exists())
//                            {f.mkdirs();}
//                            File sourcefile = new File(filePath+"/"+olddebtCode+"/"+bizfile.getCreateBy()+"/"+DateUtil.formatYYYYMMDD(bizfile.getCreateTime())+"/"+bizfile.getFileName());
//                            if (sourcefile.exists() && sourcefile.isFile()) {
//                                boolean copyFileRes = DataUtil.copyFile(sourcefile,new File(newfilePath+bizfile.getFileName()));
//                                String[] cmd = new String[]{"sh","-c","chmod 777 "+ newfilePath+bizfile.getRealName()};
//                                Process process = Runtime.getRuntime().exec(cmd);
//                                if(copyFileRes){
//                                    if(!bizFileProvider.saveFile(bizfile)){
//                                        logger.error("copy file success, save bizfile error!"+bizfile.toString());
//                                        throw new RuntimeException("copy file success, save bizfile error!");
//                                    }
//                                }else{
//                                    logger.error("copy file failed!");
//                                    throw new RuntimeException("copy file failed!");
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } finally {
//                        }
//                    }
//                }
//                return (Map<String,Object>)oldDebt;
//            }else{
//                logger.error("(BizTempSave)No records queried from the database!params="+oldParams.toString());
//                throw new RuntimeException("再次修订获取数据失败！BizDebtSummarySerivice Line : 684 ;");
//            }
//
//        }else{
//            throw new RuntimeException("未获取到方案编号！BizDebtSummarySerivice Line : 688 ;debtCode="+debtCode);
//        }
//    }
    public Map<String,Object> getAllSchemeInformation(Map<String, Object> param) {
        String debtCode=param.get("debtCode").toString();
        String debtCode2 = debtCode + "001" ;
        Map<String,Object> ddet=new HashMap();
        ddet.put("bizCode",debtCode2);
        //得到债项信息表数据
        List<BizDebtSummary>debtSummaryList=provider.queryList(param);
        BizDebtSummary bizDebtSummary=debtSummaryList.get(0);

<<<<<<< HEAD
            Map<String, Object> oldParams = new HashMap<String, Object>();
            oldParams.put("bizcode",debtCode);
            oldParams.put("remark",vernum);
            oldParams.put("projectName","ReSubmit_debtMain");
            Object oldTempSave = bizTemporarySaveProvider.getTemporary(oldParams);
            //存库的数据从文件中获得
            if(null != oldTempSave){
                //附件和变更号绑定 + 需要更新变更号
                JSONObject oldDebt = (JSONObject)oldTempSave;//(Map<String, Object>)
                String newCode = bizCntProvider.getNextNumberFormat(debtCode,3);

                //将旧数据方案号更新
                oldDebt.put("debtCode",newCode);
                Map<String, Object> newDebtMain = (Map<String, Object>)oldDebt.get("debtMain");
                newDebtMain.put("debtCode",newCode);
                JSONArray bizGuaranteeInfoList = (JSONArray)oldDebt.get("bizGuaranteeInfoList");
                if(null != bizGuaranteeInfoList && bizGuaranteeInfoList.size()>0){
                    for (int i = 0; i < bizGuaranteeInfoList.size(); i++) {
                        JSONObject bizGuaranteeInfo = bizGuaranteeInfoList.getJSONObject(i);
                        if(null != bizGuaranteeInfo.getString("debtCode")){
                            bizGuaranteeInfo.put("debtCode",newCode);
                        }
                    }
                }
                JSONArray rentFacList = (JSONArray)oldDebt.get("rentFacList");
                if(null != rentFacList && rentFacList.size()>0){
                    for (int i = 0; i < rentFacList.size(); i++) {
                        JSONObject rentFac = rentFacList.getJSONObject(i);
                        if(null != rentFac.getString("debtCode")){
                            rentFac.put("debtCode",newCode);
                        }
                    }
                }
=======
        //得到文件签发日期
        List<BizFile> issueDateList = bizFileProvider.queryList(ddet);
        ArrayList issueDate = new ArrayList();
        for (BizFile issd:issueDateList) {
            Map<String,Object> issdMap=new HashMap();
            if (issd.getIssueDate() != null){
            issdMap.put("issueDate",issd.getIssueDate());
            issueDate.add(issdMap);
         }
        }
        //得到担保信息表数据
        List<BizGuaranteeInfo>bizGuaranteeInfoList =bizGuaranteeInfoProvider.queryList(param);
        //通过担保信息中的担保合同查询押品信息
        for(BizGuaranteeInfo guar:bizGuaranteeInfoList){
            Map<String,Object> betMap=new HashMap();
            betMap.put("debtCode", guar.getDebtCode());
            betMap.put("guarNo", guar.getWarrantyContractNumber());
            List<BizBetInformation>betList= bizBetInformationProvider.queryList(betMap);
            guar.setBetInformationList(betList);
        }
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a

        //得到客户信息表,先查PTS，再查客户
        List<BizPTS>bizPTSList =bizPTSProvider.queryList(param);
        List<BizCust>customersList=new ArrayList<>();
        for(BizPTS bizt:bizPTSList){
            if(bizt.getRole().equals("LETS")){
                String idCuss=bizt.getPtyinr();
                BizCust cu= bizCustProvider.queryById(Long.valueOf(idCuss));
                //得到额度类型表
                Map<String,Object> proLinesMap=new HashMap();
                proLinesMap.put("debtCode", debtCode);
                proLinesMap.put("custNo",cu.getCustNo());
                List<BizProductLinesType>productLinesTypesList =bizProductLinesTypeProvider.queryList(proLinesMap);

                BizProductLinesType productLinesType=productLinesTypesList.get(0);
                cu.setCreditLinesId(productLinesType.getCreditLinesId().toString());
                cu.setCreditRatio(productLinesType.getCreditRatio());
                //cu.setProductLinesTypeId(productLinesType.getId().toString());


                //得到用户主体授信信息表
                Map<String,Object> crelinesMap=new HashMap();
                crelinesMap.put("debtCode", debtCode);
                crelinesMap.put("custNo",cu.getCustNo());
                crelinesMap.put("objtyp","BIZ_DEBT_MAIN");
                crelinesMap.put("objinr",bizDebtSummary.getId());
                List<BizCreditLines>bizCreditLinesList =bizCreditLinesProvider.queryList(crelinesMap);
                cu.setCreditLinesList(bizCreditLinesList);
                customersList.add(cu);
            }
        }

        //从单一规则表中拿到行业投向和国别背景
        List<BizSingleProductRule>singleList =singleProductRuleProvider.queryList(param);
        BizSingleProductRule singleProductRule=singleList.get(0);
        //从单一规则表中拿到产品code值去得到租金保理信息
        Map<String,Object>productMap=new HashMap<>();
        productMap.put("code", singleProductRule.getBusinessType());
        List<BizProductTypes>productTypesList=bizProductTypeProvider.queryList(productMap);
        BizProductTypes bizProductTypes=productTypesList.get(0);

        //得到专有信息页面的承租人
        List<BizTheRentFactoring>theRentFactoringList =bizTheRentFactoringProvider.queryList(param);
        BizTheRentFactoring bizTheRentFactoring=theRentFactoringList.get(0);

        //设置行业投向和国别背景
        bizProductTypes.setSingleId(singleProductRule.getId().toString());
        bizProductTypes.setIndustryTo(singleProductRule.getIndustryInvestment());
        bizProductTypes.setTbon(singleProductRule.getBackgroundNationality());
        //设置承租人
        bizProductTypes.setCustNo(bizTheRentFactoring.getCustNo());
        //设置承租人名称
        bizProductTypes.setCustName(bizTheRentFactoring.getCustName());
        //设置承租人评级
        bizProductTypes.setCustTating(bizTheRentFactoring.getCustTating());
        //设置容忍期
        bizProductTypes.setTolerancePertod(bizTheRentFactoring.getTolerancePertod());
        bizProductTypes.setTheRentFactorId(bizTheRentFactoring.getId().toString());
        //设置承租人是否是地方融资平台
        bizProductTypes.setFinancePlatform(bizTheRentFactoring.getFinancePlatform());
        //设置客户信息，产品使用额度，产品用信比例
        bizProductTypes.setCustomersList(customersList);
        List<BizProductTypes>bizProductTypesList=new ArrayList<>();
        bizProductTypesList.add(bizProductTypes);

        Map<String,Object> mapObj=new HashMap<>();
        mapObj.put("debtMain", bizDebtSummary);
        mapObj.put("issueDateList", issueDate);
        mapObj.put("rentFacList", bizProductTypesList);
        mapObj.put("bizGuaranteeInfoList",bizGuaranteeInfoList);
        return  mapObj;
    }

    //查询担保类型分
    public List<BizGuaranteeType> getGuaranteeInfo(Map<String, Object> params) {
        params.put("parentCode", "0000");
        params.put("type", "004");
        List<BizGuaranteeType>bizGuarList= bizGuaranteeTypeProvider.queryList(params);
        return bizGuarList;
    }

    //得到担保合同类型/占用额度类型
    public List<BizGuaranteeType> getContractTypeList(Map<String, Object> params) {
        String eve = params.get("eve").toString();
        Map<String, Object> map = new HashMap<>();
        if (eve.equals("C102")) {
            map.put("parentCode", "C102");
            map.put("type", "004");
            List<BizGuaranteeType> guList1 = bizGuaranteeTypeProvider.queryList(map);
            return guList1;
        }
        if (eve.equals("C103")) {
            map.put("parentCode", "C103");
            map.put("type", "004");
            List<BizGuaranteeType> guList2 = bizGuaranteeTypeProvider.queryList(map);
            return guList2;
        }
        if (eve.equals("C101")) {
            map.put("parentCode", "C101");
            map.put("type", "004");
            List<BizGuaranteeType> guList3 = bizGuaranteeTypeProvider.queryList(map);
            return guList3;
        }
        if (eve.equals("C000")) {
            map.put("parentCode", "C000");
            map.put("type", "004");
            List<BizGuaranteeType> guList4 = bizGuaranteeTypeProvider.queryList(map);
            return guList4;
        }
        if (eve.equals("C104")) {
            map.put("parentCode", "C104");
            map.put("type", "004");
            List<BizGuaranteeType> guList5 = bizGuaranteeTypeProvider.queryList(map);
            return guList5;
        }
        return null;
    }

    //得到押品编号
    public String getRemandNum(Map<String, Object> params) {
        String remandNum="MJDBPI"+DateUtil.getDateYearNow();
        remandNum=bizCntProvider.getNextNumberFormat(remandNum, 8);
        return  remandNum;
    }


    public BizDebtSummary selectOneBizDebtSummary(BizDebtSummary entity) {
        return provider.selectOneBizDebtSummary(entity);
    }

    //得到摘要信息
    public SumInformationVo getSumInformation(Map<String, Object> params) {
        String debtCode = (String)params.get("debtCode");
        if(debtCode.length()==16){
            params.put("debtCode",debtCode.substring(0,13));
            params.put("verNum",Integer.parseInt(debtCode.substring(13)));
        }else{
            logger.error("获取摘要信息查询失败，debtCode="+debtCode);
        }

        Wrapper<BizApprSummaryInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("VERNUM_",(Integer)params.get("verNum")).eq("DEBT_CODE",(String)params.get("debtCode"));
        BizApprSummaryInfo bizDebt = bizApprSummaryInfoProvider.selectOne(wrapper);
        SumInformationVo sumInformationVo=new SumInformationVo();
        //债项方案总金额
        sumInformationVo.setTotalAmont(bizDebt.getSolutionAmount());
        //方案主币种
        sumInformationVo.setMpc(bizDebt.getMpc());
        Map mapMpc = new HashMap();
        mapMpc.put("monCode",bizDebt.getMpc());
        List<SysCurrency> sysCurrency = iSysCurrencyProvider.queryList(mapMpc);
        SysCurrency sysCurrency1 = sysCurrency.get(0);
        //方案币种中文
        String codeName = sysCurrency1.getCodeName();
        sumInformationVo.setMpcName(codeName);
        //方案辅助币种
        sumInformationVo.setaCurrrency(bizDebt.getaCurrrency());
        //方案其他币种
        sumInformationVo.setOc(bizDebt.getOc());

        BizTRN selTrn = new BizTRN();
        selTrn.setObjtyp("BIZ_DEBT_MAIN");
        selTrn.setOwnref((String)params.get("debtCode"));
        selTrn.setVerNum((Integer) params.get("verNum"));

        BizTRN debtTrn = bizTRNProvider.selectOne(new EntityWrapper<>(selTrn));
        //方案状态
        sumInformationVo.setSolutionState(debtTrn.getBizStatus());
        //方案审核状态
        sumInformationVo.setProcessStatus(debtTrn.getProcessStatus());
        //合同创建状态
        String transok = bizDebt.getTransok();
        sumInformationVo.setTransok(transok);
        //返回信息码
        String errNo = bizDebt.getErrNo();
        sumInformationVo.setErrNo(errNo);
        //中文信息码
        if(transok !=null && transok !=""){
            if("0".equals(transok)){
                sumInformationVo.setErrorMessage("合同创建成功");
            }else{
                BizCbsErrorMessage bizCbsErrorMessage = new BizCbsErrorMessage();
                bizCbsErrorMessage.setErrorCode(errNo);
                BizCbsErrorMessage bizCbsErrorMessage1 = bizCbsErrorMessageProvider.selectOne(new EntityWrapper<>(bizCbsErrorMessage));
                if(bizCbsErrorMessage1==null){
                    sumInformationVo.setErrorMessage("数据库中无此错误码");
                }else {
                    String errorMessage = bizCbsErrorMessage1.getErrorMessage();
                    sumInformationVo.setErrorMessage(errorMessage);
                }
            }
        }else{
            sumInformationVo.setErrorMessage("接口调用失败");
        }
        sumInformationVo.setIdentNumber(bizDebt.getIdentNumber());
        //债项方案已批未放金额
        sumInformationVo.setNoFoundAmont(BigDecimal.valueOf(0));
        //政策性分类
        sumInformationVo.setClassificPolicies(Long.valueOf(bizDebt.getPolicy()));
        //政策性描述
        sumInformationVo.setPoliciesDescribe(bizDebt.getPolicyDescription());
        //汇总期限
        sumInformationVo.setSummaryPeriod(bizDebt.getDopo());
        //汇总费率
        sumInformationVo.setSummaryRates(bizDebt.getPackageRate());
        //汇总费率最小值
        sumInformationVo.setRateRangeMix(bizDebt.getRateRangeMix());
        //汇总费率最大值
        sumInformationVo.setRateRangeMax(bizDebt.getRateRangeMax());
        //利率描述
        sumInformationVo.setAggregateRates(bizDebt.getDescriptionRateRules());

        List<BizCust>custList=new ArrayList<>();
        //用信主体 用信主体不展示
        /*List<BizPTS> bizPTSList=bizPTSProvider.queryList(params);
        for (BizPTS bizPTS:bizPTSList){
            if(bizPTS.getRole().equals("LETS")){
                BizCust bizCustomer=bizCustProvider.queryById(Long.valueOf(bizPTS.getPtyinr()));
                custList.add(bizCustomer);
            }
        }*/
        sumInformationVo.setCustList(custList);
        //产品组合
        List<BizSingleProductRule> singleList=singleProductRuleProvider.queryList(params);
        BizSingleProductRule bizSingleProductRule=singleList.get(0);
        BizProductTypes bizProductTypes= bizProductTypeProvider.queryByCode(bizSingleProductRule.getBusinessType());
        sumInformationVo.setProName(bizProductTypes.getName());
        return sumInformationVo;
    }

    //债项方案数据迁移列表
    public Page<?> checkHistoryDebt(Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        SysDept sysDept=sysDeptProvider.queryDeptByCode(user.getDeptCode());
        String deptCod=sysDept.getParentCode();
        if(!deptCod.equals("1550000")){
            params.put("institutionCode", deptCod);
        }
        Page<BizDebtSummary> bizdebtList= (Page<BizDebtSummary>) provider.getDebtInfo(params);

        return bizdebtList;
    }

    //债项方案数据迁移
    public Map<String,Object> historyDebt(Map<String, Object> param) {
        String debtCode=param.get("debtCode").toString();
        //得到债项信息表数据
        List<BizDebtSummary>debtSummaryList=provider.queryList(param);
        BizDebtSummary bizDebtSummary=debtSummaryList.get(0);
        SysDept sysDept=sysDeptProvider.queryDeptByCode(bizDebtSummary.getInstitutionCode());
        bizDebtSummary.setDeptName(sysDept.getDeptName());

        //得到担保信息表数据
        List<BizGuaranteeInfo>bizGuaranteeInfoList =bizGuaranteeInfoProvider.queryList(param);
        //通过担保信息中的担保合同查询押品信息
        for(BizGuaranteeInfo guar:bizGuaranteeInfoList){
            Map<String,Object> betMap=new HashMap();
            betMap.put("debtCode", guar.getDebtCode());
            List<BizBetInformation>betList= bizBetInformationProvider.queryList(betMap);
            guar.setBetInformationList(betList);
        }

        //得到客户信息表,先查PTS，再查客户
        List<BizPTS>bizPTSList =bizPTSProvider.queryList(param);
        List<BizCust> customersList=new ArrayList<>();
        if(bizPTSList!=null){
            for(BizPTS bizt:bizPTSList){
                if(bizt.getRole().equals("LETS")){
                    String idCuss=bizt.getPtyinr();
                    BizCust cu= bizCustProvider.queryById(Long.valueOf(idCuss));
                    //得到额度类型表
                    Map<String,Object> proLinesMap=new HashMap();
                    proLinesMap.put("debtCode", cu.getDebtCode());
                    proLinesMap.put("custNo",cu.getCustNo());
                    List<BizProductLinesType>productLinesTypesList =bizProductLinesTypeProvider.queryList(proLinesMap);

                    BizProductLinesType productLinesType=productLinesTypesList.get(0);
                    cu.setCreditLinesId(productLinesType.getCreditLinesId().toString());
                    cu.setCreditRatio(productLinesType.getCreditRatio());
                    cu.setProductLinesTypeId(productLinesType.getId().toString());


                    //得到用户主体授信信息表
                    Map<String,Object> crelinesMap=new HashMap();
                    crelinesMap.put("debtCode", cu.getDebtCode());
                    crelinesMap.put("custNo",cu.getCustNo());
                    crelinesMap.put("objtyp","BIZ_DEBT_MAIN");
                    crelinesMap.put("objinr",bizDebtSummary.getId());
                    List<BizCreditLines>bizCreditLinesList =bizCreditLinesProvider.queryList(crelinesMap);
                    cu.setCreditLinesList(bizCreditLinesList);
                    customersList.add(cu);
                }
            }
        }

        //从单一规则表中拿到行业投向和国别背景
        List<BizSingleProductRule>singleList =singleProductRuleProvider.queryList(param);
        BizSingleProductRule singleProductRule=singleList.get(0);
        //从单一规则表中拿到产品code值去得到租金保理信息
        Map<String,Object>productMap=new HashMap<>();
        productMap.put("code", singleProductRule.getBusinessType());
        List<BizProductTypes>productTypesList=bizProductTypeProvider.queryList(productMap);
        BizProductTypes bizProductTypes=productTypesList.get(0);

        //得到专有信息页面的承租人
        List<BizTheRentFactoring>theRentFactoringList =bizTheRentFactoringProvider.queryList(param);
        BizTheRentFactoring bizTheRentFactoring=theRentFactoringList.get(0);

        //设置行业投向和国别背景
        bizProductTypes.setIndustryTo(singleProductRule.getIndustryInvestment());
        bizProductTypes.setTbon(singleProductRule.getBackgroundNationality());
        bizProductTypes.setSingleId(singleProductRule.getId().toString());

        //设置承租人
        bizProductTypes.setCustNo(bizTheRentFactoring.getCustNo());
        //承租人名称
        bizProductTypes.setCustName(bizTheRentFactoring.getCustName());
        //承租人评级
        bizProductTypes.setCustTating(bizTheRentFactoring.getCustTating());
        //设置租金保理的id
        bizProductTypes.setTheRentFactorId(bizTheRentFactoring.getId().toString());
        //设置承租人是否是地方融资平台
        bizProductTypes.setFinancePlatform(bizTheRentFactoring.getFinancePlatform());
        //设置客户信息，产品使用额度，产品用信比例
        bizProductTypes.setCustomersList(customersList);
        //设置租金保理到list
        List<BizProductTypes>bizProductTypesList=new ArrayList<>();
        bizProductTypesList.add(bizProductTypes);

        Map<String,Object> mapObj=new HashMap<>();
        mapObj.put("debtMain", bizDebtSummary);
        mapObj.put("rentFacList", bizProductTypesList);
        mapObj.put("bizGuaranteeInfoList",bizGuaranteeInfoList);
        return  mapObj;

    }

    //历史数据的保存
    public void historyDebtSave(Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId);
        provider.historyDebtSave(params);
    }

    //已驳回的删除
    public void checkState(BizDebtSummary bizDebtSummary) {
        String debtCode=bizDebtSummary.getDebtCode();
//        String debtCode1=debtCode.substring(0, 13);
//        String debtCode2=debtCode.substring(13);
        String vernum = debtCode.substring(13);
        debtCode=debtCode.substring(0, 13);

        //通过debtCode2来判断是否补录的后的驳回，还是修订后的驳回
        bizDebtSummary.setVerNum(Integer.parseInt(vernum));
        bizDebtSummary.setDebtCode(debtCode);
        //修改流水
        provider.rejectAndDel(bizDebtSummary);
    }

    //已驳回的重新提交
    public void ReSaveDebt(Map<String, Object> param) {
        //得到债项方案的id
       /* String debtCode=param.get("debtCode").toString();

        String debtCode1=debtCode.substring(0, 13);
        String debtCode2=debtCode.substring(13);

        //得到债项信息表数据
        BizDebtSummary bizDebtSummary=JSON.parseObject(JSON.toJSONString(param.get("debtMain")),BizDebtSummary.class);

        //通过debtCode2来判断是否补录的后的驳回，还是修订后的驳回
        if(debtCode2.equals("001")){
            bizDebtSummary.setSolutionState(BizStatus.DEBTSUAT);
            bizDebtSummary.setProcessStatus(BizStatus.DEPRNEAT);
        }else{
            bizDebtSummary.setSolutionState(BizStatus.DEBTREAT);
            bizDebtSummary.setProcessStatus(BizStatus.DEPRNEAT);
        }

        //得到担保信息表数据
        List<BizGuaranteeInfo> bizGuaranteeInfoList=JSON.parseArray(JSON.toJSONString(param.get("bizGuaranteeInfoList")),BizGuaranteeInfo.class);
        for(BizGuaranteeInfo guarantee:bizGuaranteeInfoList){
            guarantee.setDebtCode(debtCode);
        }

        List<BizProductTypes> rentFacList=JSON.parseArray(JSON.toJSONString(param.get("rentFacList")),BizProductTypes.class);
        List<BizSingleProductRule> bizSingleProductRuleList=new ArrayList<>();
        List<BizProductLinesType> productLinesTypesList=new ArrayList<>();
        Set<BizCust> cuSet=new HashSet<>();
        List<BizTheRentFactoring> rentList=new ArrayList<>();
        for (BizProductTypes productTypes:rentFacList){
            //得到单一规则表
            BizSingleProductRule s=new BizSingleProductRule();
            s.setId(Long.valueOf(productTypes.getSingleId()));
            s.setBusinessType(productTypes.getCode());
            s.setDebtCode(debtCode);
            s.setIndustryInvestment(productTypes.getIndustryTo());
            s.setBackgroundNationality(productTypes.getTbon());
            bizSingleProductRuleList.add(s);

            //得到租金保理表数据
            BizTheRentFactoring rent=new BizTheRentFactoring();
            rent.setId(Long.valueOf(productTypes.getTheRentFactorId()));
            rent.setTolerancePertod(productTypes.getTolerancePertod());
            rent.setDebtCode(debtCode);
            rent.setBusinessTypes(productTypes.getCode());
            rent.setCustNo(productTypes.getCustNo());
            rent.setTolerancePertod(productTypes.getTolerancePertod());
            rent.setFinancePlatform(productTypes.getFinancePlatform());
            rentList.add(rent);

            //得到额度类型表数据
            List<BizCust> customerList=productTypes.getCustomersList();

            for(BizCust cus:customerList){
                cus.setDebtCode(debtCode);
                //得到额度类型表数据
                BizProductLinesType lines=new BizProductLinesType();
                lines.setId(Long.valueOf(cus.getProductLinesTypeId()));
                lines.setDebtCode(debtCode);
                lines.setCustNo(cus.getCustNo());
                lines.setBusinessType(productTypes.getCode());
                lines.setCreditLinesId(cus.getCreditLinesId());
                lines.setCreditRatio(cus.getCreditRatio());
                productLinesTypesList.add(lines);
                //得到客户表数据
                cuSet.add(cus);
            }
        }
        Long userId = BizWebUtil.getCurrentUser();
        Map map=new HashMap();
        map.put("userId",userId);
        List<SysUserRole> list = sysUserRoleProvider.selectOneSysUserRole(map);

        Map<String,Object> mapObj=new HashMap<>();
        mapObj.put("bizDebtSummary", bizDebtSummary);
        mapObj.put("bizGuaranteeInfoList", bizGuaranteeInfoList);
        mapObj.put("bizSingleProductRuleList", bizSingleProductRuleList);
        mapObj.put("rentList", rentList);
        mapObj.put("cuSet", cuSet);
        mapObj.put("productLinesTypesList", productLinesTypesList);
        mapObj.put("userId", userId.toString());
        mapObj.put("list", list);
        provider.ReSaveDebt(mapObj);*/


    }
    /*
    查询所有机构
    刘常坤
     */
    public List getAllDeptList() {
        List deptList;
        // 查询当前登录用户所在机构编码
        Long userId = BizWebUtil.getCurrentUser();
        SysUser user=sysUserProvider.queryById(userId);
        SysDept sysDept=sysDeptProvider.queryDeptByCode(user.getDeptCode());
        String deptCod=sysDept.getParentCode();
        // 1550000为总行贸金部机构编码
        if(!"1550000".equals(deptCod)){
            // 对于非总行贸金部机构只能查看本机构业务
            Map code = new HashMap();
            code.put("code", deptCod);
            deptList=sysDeptProvider.queryList(code);
        }else{
            // 查询所有机构
            Map code = new HashMap();
            deptList=sysDeptProvider.queryList(code);
        }
        return deptList;
    }


    /*
    担保合同唯一校验
    肖水泉
    2018.11.9
     */
    public Boolean queryConNum(Map<String, Object> params) {
        String debtCodeCon=params.get("debtCodeCon").toString();
        debtCodeCon=debtCodeCon.substring(0, 13);
        params.put("debtCodeCon", debtCodeCon);
        List<BizGuaranteeInfo>bizGuarList=bizGuaranteeInfoProvider.queryList(params);
        if(bizGuarList.size()>0){
            return  true;
        }else {
            return  false;
        }
    }

    /*
   历史数据迁移编辑提醒
   肖水泉
   2018.11.9
    */
    public Boolean queryTem(Map<String, Object> params) {
        List<BizTemporarySave>temList= bizTemporarySaveProvider.queryList(params);
        if(temList.size()>0){
            return  true;
        }else {
            return  false;
        }

    }
    /*
查询背景国别
肖水泉
2018.11.27
*/
    public List<SysDic> getBackground(Map<String, Object> params) {
        List<SysDic>sysDics=sysDicProvider.queryList(params);
        return sysDics;
    }
}
