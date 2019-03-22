package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.ExportExcelUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.SysDept;
import org.ibase4j.provider.BizDebtGrantProvider;
import org.ibase4j.provider.BizMakeLoansProvider;
import org.ibase4j.provider.BizStandingBookProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: 台账
 * @Author: dj
 * @CreateDate: 2018-08-30
 */
@Service
public class BizStandingBookService extends BaseService<BizDebtGrantProvider, BizDebtGrant>{
    @Reference
    public void setProvider(BizDebtGrantProvider provider) {
        this.provider = provider;
    }
    @Reference
    private BizStandingBookProvider bizStandingBookProvider;
    @Reference
    private BizMakeLoansProvider bizMakeLoansProvider;

    public Page getDebtStandingBookList(Map<String, Object> param) {
        return bizStandingBookProvider.getDebtStandingBookList(param);
    }

    public Page getGrantStandingBookList(Map<String, Object> param) {
        Map map = settingDefaultParameters(param);
        Page dataPage = bizStandingBookProvider.getGrantStandingBookPage(map);
        return dataPage;
    }

    public Map getDebtInfoForStandingBook(Map<String, Object> params) {
        return bizStandingBookProvider.getDebtInfoForStandingBook(params);
    }

    public Map getGrantInfoForStandingBook(Map<String, Object> params) {
        return bizStandingBookProvider.getGrantInfoForStandingBook(params);
    }

    public List getGrantAMTDetailForStandingBook(Map<String,Object> param){
        return bizStandingBookProvider.getGrantAMTDetailForStandingBook(param);
    }

    public Page getStatisticInfoPage(Map<String,Object> param){
        Map map = settingDefaultParameters(param);
        Page dataPage = bizStandingBookProvider.getStatisticInfoPage(map);
        return dataPage;
    }

    public void exportExcelForStatisticInfo(Map<String, Object> params, HttpServletResponse response){
        // 查询出所有符合条件统计信息结果集
        Map map = settingDefaultParameters(params);
        List statisticInfoList = bizStandingBookProvider.getStatisticInfoList(map);
        // 统计信息表头
        String[] titles = {"债项方案编号", "发放条件编号", "业务编号", "所属机构", "信贷员", "项目名称", "产品名称",
                "合同编号", "币种", "本金期末余额 (原币)", "本金期末余额 (折人民币)", "本金期末余额 (折美元)",
                "起息日", "到期日", "业务期限", "发放金额", "担保方式（总）", "客户编号", "客户名称", "是否同业客户",
                "所属行业", "企业规模", "组织机构代码", "客户信用等级", "是否地方政府融资平台", "贷款产品类型",
                "创新业务类型", "背景国别", "贷款领域", "进出口货物及服务", "中国制造2025及战略新兴产业分类",
                "产业结构调整类型", "商务合同编码", "商务合同签订日期", "商务合同金额 (折美元)", "对外投资贷款分类",
                "是否工业转型升级", "是否文化产业", "是否精准扶贫", "贸金业务政策性属性","政策性属性分类",
                "行业投向", "支农投向", "是否银团","是否银团代理行", "我行银团地位", "是否 421","支付方式"};
        // 统计信息明细
        String[][] centerVal = transformStatisticInfoList(statisticInfoList,titles);
        // 导出
        ExportExcelUtil.exportExcel(true,titles,centerVal,response);
    }

    public List getDeptList(){
        List deptList = new ArrayList();
        // 查询当前登录用户所在机构编码
        Long userId = BizWebUtil.getCurrentUser();
        SysDept sysDept = bizMakeLoansProvider.getUserInstitutionCodeByUserId(userId);
        SysDept parentDept = bizMakeLoansProvider.getDeptByDeptCode(sysDept.getParentCode());
        // 1550000为总行贸金部机构编码
        if(!"1550000".equals(sysDept.getParentCode())){
            // 对于非总行贸金部机构只能查看本机构业务
            deptList.add(parentDept);
        }else{
            // 查询所有机构
            Map code = new HashMap();
            code.put("parentCode","1000000");
            deptList = bizStandingBookProvider.getAllInstitution(code);
        }
        return deptList;
    }

    /** 
    * @Description: 对发放审核台账查询条件设置默认参数 
    * @Param: [param] 
    * @return: java.util.Map 
    */ 
    public Map settingDefaultParameters(Map<String, Object> param){
        // 接收参数 2018-11-28 修改时间筛选条件
        String deptCode = StringUtil.objectToString(param.get("deptCode"));// 机构编码
        String endDate = StringUtil.objectToString(param.get("endDate")); // 截止日
        // 机构筛选
        // 查询当前登录用户所在机构编码
        Long userId = BizWebUtil.getCurrentUser();
        SysDept sysDept = bizMakeLoansProvider.getUserInstitutionCodeByUserId(userId);
        // 1550000为总行贸金部机构编码
        if("".equals(deptCode) && !"1550000".equals(sysDept.getParentCode())){
            // 对于非总行贸金部机构只能查看本机构业务
            param.put("deptCode",sysDept.getParentCode());
        }
        // 截止日默认当前时间
        if("".equals(endDate)){
/*           Date nowDate = new Date();
            endDate = DateUtil.format(nowDate,DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);*/
            endDate = DateUtil.format(new Date(), DateUtil.DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);


        }
//        String beginDate = StringUtil.objectToString(param.get("beginDate"));// 起始日
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            if("".equals(beginDate)){
//                // 起始日默认当前时间向前推一个月
//                Date date = sdf.parse(endDate);
//                Calendar calendar  = Calendar.getInstance();
//                calendar.setTime(date);
//                calendar.add(Calendar.MONTH,-1);
//                date=calendar.getTime();
//                beginDate = sdf.format(date);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        param.put("beginDate",beginDate);
        param.put("endDate",endDate);
        return param;
    }

    public void exportExcelForAMTDetail(Map<String, Object> params, HttpServletResponse response){
        // 查询出所有满足条件的发放业务编码
        List excelDataList = new ArrayList();
        Map map = settingDefaultParameters(params);
        List grantStandingBookList = bizStandingBookProvider.getGrantStandingBookList(map);
        // 查询每笔业务对应的金额流水
        if(grantStandingBookList != null && grantStandingBookList.size() >0){
            for (int i = 0; i < grantStandingBookList.size(); i++) {
                Map dataMap = new HashMap();
                Map grantStandingBook = (Map)grantStandingBookList.get(i);
                String debtCode = StringUtil.objectToString(grantStandingBook.get("debtCode"));
                String grantCode = StringUtil.objectToString(grantStandingBook.get("grantCode"));
                dataMap.put("debtCode",debtCode);
                dataMap.put("grantCode",grantCode);
                // 查询流水
                List grantAMTDetailForStandingBook = bizStandingBookProvider.getGrantAMTDetailForStandingBook(dataMap);
                excelDataList.addAll(grantAMTDetailForStandingBook);
            }
        }
        // 金额流水明细表头
        String[] titles = {"业务编号", "债项方案编号", "发放条件编号", "所属机构", "项目名称", "产品名称",
                "客户名称", "发放金额", "发生日期", "币种", "发生额类型","发生金额","本金期末余额 (原币)",
                "本金期末余额 (折人民币)", "本金期末余额 (折美元)", "本金累计收回金额（原币）", "本金累计收回金额 (折人民币)",
               "逾期本金余额",  "表内欠息余额", "表外欠息余额", "当前利率"};
        // 金额流水表格内容
        String[][] centerVal = transformGrantAMTDetailList(excelDataList, titles);
        // 导出
        ExportExcelUtil.exportExcel(true,titles,centerVal,response);
    }

    private String[][] transformGrantAMTDetailList(List list,String[] titles){
        String[][] centerVal = new String[list.size()][titles.length];
        if(list != null && list.size() >0){
            for (int i = 0; i < list.size(); i++) {
                Map amtInfo = (Map) list.get(i);
                    centerVal[i][0] =StringUtil.objectToString(amtInfo.get("identNumber"));
                    centerVal[i][1] = StringUtil.objectToString(amtInfo.get("debtCode"));
                    centerVal[i][2] = StringUtil.objectToString(amtInfo.get("grantCode"));
                    centerVal[i][3] = StringUtil.objectToString(amtInfo.get("deptName"));
                    centerVal[i][4] = StringUtil.objectToString(amtInfo.get("projectName"));
                    centerVal[i][5] = StringUtil.objectToString(amtInfo.get("productName"));
                    centerVal[i][6] = StringUtil.objectToString(amtInfo.get("proposer"));
                    centerVal[i][7] = StringUtil.objectToString(amtInfo.get("paymentAmt"));
                    centerVal[i][8] = StringUtil.objectToString(amtInfo.get("dateOfLoan"));
                    centerVal[i][9] = StringUtil.objectToString(amtInfo.get("currency"));
                    centerVal[i][10] = StringUtil.objectToString(amtInfo.get("cbtString"));
                    centerVal[i][11] = StringUtil.objectToString(amtInfo.get("amt"));
                    centerVal[i][12] = StringUtil.objectToString(amtInfo.get("amtBalance"));
                    centerVal[i][13] = StringUtil.objectToString(amtInfo.get("amtBalanceCNY"));
                    centerVal[i][14] = StringUtil.objectToString(amtInfo.get("amtBalanceUSD"));
                    centerVal[i][15] = StringUtil.objectToString(amtInfo.get("grantAMTInAll"));
                    centerVal[i][16] = StringUtil.objectToString(amtInfo.get("grantAMTInAllCNY"));
                    centerVal[i][17] = StringUtil.objectToString(amtInfo.get("oveduSum"));
                    centerVal[i][18] = StringUtil.objectToString(amtInfo.get("inateSum"));
                    centerVal[i][19] = StringUtil.objectToString(amtInfo.get("ouateSum"));
                    centerVal[i][20] = StringUtil.objectToString(amtInfo.get("rateVal"));
           }
        }
        return centerVal;
    }

    private String[][] transformStatisticInfoList(List list,String[] titles){
        String[][] centerVal = new String[list.size()][titles.length];
        if(list != null && list.size() >0){
            for (int i = 0; i < list.size(); i++) {
                Map statisticInfo = (Map) list.get(i);
                centerVal[i][0] = StringUtil.objectToString(statisticInfo.get("debtCode"));
                centerVal[i][1] = StringUtil.objectToString(statisticInfo.get("grantCode"));
                centerVal[i][2] = StringUtil.objectToString(statisticInfo.get("identNumber"));
                centerVal[i][3] = StringUtil.objectToString(statisticInfo.get("deptName"));
                centerVal[i][4] = StringUtil.objectToString(statisticInfo.get("banktellName"));
                centerVal[i][5] = StringUtil.objectToString(statisticInfo.get("projectName"));
                centerVal[i][6] = StringUtil.objectToString(statisticInfo.get("productName"));
                centerVal[i][7] = StringUtil.objectToString(statisticInfo.get("bizRentalFactoringCode"));
                centerVal[i][8] = StringUtil.objectToString(statisticInfo.get("currency"));
                centerVal[i][9] = StringUtil.objectToString(statisticInfo.get("amtBalance"));
                centerVal[i][10] = StringUtil.objectToString(statisticInfo.get("amtBalanceCNY"));
                centerVal[i][11] = StringUtil.objectToString(statisticInfo.get("amtBalanceUSD"));
                centerVal[i][12] = StringUtil.objectToString(statisticInfo.get("valueDate"));
                centerVal[i][13] = StringUtil.objectToString(statisticInfo.get("dueDate"));
                centerVal[i][14] = StringUtil.objectToString(statisticInfo.get("dayBetween"));
                centerVal[i][15] = StringUtil.objectToString(statisticInfo.get("paymentAmt"));
                centerVal[i][16] = StringUtil.objectToString(statisticInfo.get("guaranteeTypeTotal"));
                centerVal[i][17] = StringUtil.objectToString(statisticInfo.get("proposerNum"));
                centerVal[i][18] = StringUtil.objectToString(statisticInfo.get("proposer"));
                centerVal[i][19] = "否";
                centerVal[i][20] = StringUtil.objectToString(statisticInfo.get("mainBusiness"));
                centerVal[i][21] = StringUtil.objectToString(statisticInfo.get("custScale"));
                centerVal[i][22] = StringUtil.objectToString(statisticInfo.get("organizationCode"));
                centerVal[i][23] = StringUtil.objectToString(statisticInfo.get("creditRating"));
                centerVal[i][24] = StringUtil.objectToString(statisticInfo.get("financePlatform"));
                centerVal[i][25] = StringUtil.objectToString(statisticInfo.get("businessTypeString"));
                centerVal[i][26] = StringUtil.objectToString(statisticInfo.get("innovativeBusinessType"));
                centerVal[i][27] = StringUtil.objectToString(statisticInfo.get("backgroundNationality"));
                centerVal[i][28] = StringUtil.objectToString(statisticInfo.get("loanDomain"));
                centerVal[i][29] = StringUtil.objectToString(statisticInfo.get("impoertExportGoodsService"));
                centerVal[i][30] = StringUtil.objectToString(statisticInfo.get("emergingIndustryClassify"));
                centerVal[i][31] = StringUtil.objectToString(statisticInfo.get("tyoesIndustrial"));
                centerVal[i][32] = StringUtil.objectToString(statisticInfo.get("businessContractCode"));
                centerVal[i][33] = StringUtil.objectToString(statisticInfo.get("businessContractDate"));
                centerVal[i][34] = StringUtil.objectToString(statisticInfo.get("businessContractAmount"));
                centerVal[i][35] = StringUtil.objectToString(statisticInfo.get("loanCkassification"));
                centerVal[i][36] = StringUtil.objectToString(statisticInfo.get("industrialTransformation"));
                centerVal[i][37] = StringUtil.objectToString(statisticInfo.get("curturalProduct"));
                centerVal[i][38] = StringUtil.objectToString(statisticInfo.get("alleviationLoan"));
                centerVal[i][39] = StringUtil.objectToString(statisticInfo.get("tradeFinanceBusiness"));
                centerVal[i][40] = StringUtil.objectToString(statisticInfo.get("policyAttributreClassify"));
                centerVal[i][41] = StringUtil.objectToString(statisticInfo.get("industryInvestment"));
                centerVal[i][42] = StringUtil.objectToString(statisticInfo.get("compare"));
                centerVal[i][43] = StringUtil.objectToString(statisticInfo.get("isSyndicated"));
                centerVal[i][44] = StringUtil.objectToString(statisticInfo.get("isSyndicatedAgency"));
                centerVal[i][45] = StringUtil.objectToString(statisticInfo.get("syndicatedStatus"));
                centerVal[i][46] = StringUtil.objectToString(statisticInfo.get("is421"));
                centerVal[i][47] = StringUtil.objectToString(statisticInfo.get("paymentMode"));
            }
        }
        return centerVal;
    }
}
