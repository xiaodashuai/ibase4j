/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizTRN;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：交易流水表
 * 日期：2018/7/6
 * @author czm
 */
@CacheConfig(cacheNames = "BizTRN")
@Service(interfaceClass = BizTRNProvider.class)
public class BizTRNProviderImpl extends BaseProviderImpl<BizTRN> implements BizTRNProvider {

    @Override
    public void saveDebtGrantTRN(Map<String, Object> params) {
        // 构造交易流水对象
        BizTRN bizTRN = new BizTRN();
        // 交易代码
        bizTRN.setInifrm(BizContant.DEBT_GRANT_APPRL);
        // 交易名称
        bizTRN.setIninam(BizContant.DEBT_GRANT_NAME);
        // 登录柜员
        bizTRN.setIniusr(Long.valueOf(params.get("userId").toString()));
        // 业务编号
        bizTRN.setOwnref(StringUtil.objectToString(params.get("debtCode")));
        // 业务表名称
        bizTRN.setObjtyp(BizContant.DEBT_GRANT_OBJTYP);
        // 业务表INR
        bizTRN.setObjinr(Long.valueOf(params.get("GrantId").toString()));
        // 业务种类
        bizTRN.setBusinessTypes(BizContant.RENTAL_FACTORING_BUSINESS_TYPES);
        // 执行日期
        bizTRN.setExedat(new Date());
        update(bizTRN);
    }
    @Override
    public void saveDebtMainTRN(Map<String, Object> params) {
        // 构造交易流水对象
        BizTRN bizTRN = new BizTRN();
        // 交易代码
        bizTRN.setInifrm(BizContant.DEBT_MAIN_APPRL);
        // 交易名称
        bizTRN.setIninam(BizContant.DEBT_MAIN_NAME);
        // 登录柜员
        bizTRN.setIniusr(Long.valueOf(params.get("userId").toString()));
        // 业务编号
        bizTRN.setOwnref(StringUtil.objectToString(params.get("debtCode")));
        // 业务表名称
        bizTRN.setObjtyp(BizContant.BIZ_DEBT_MAIN);
        // 业务表INR
        bizTRN.setObjinr(Long.valueOf(params.get("MainId").toString()));
        // 业务种类
        bizTRN.setBusinessTypes(BizContant.RENTAL_FACTORING_BUSINESS_TYPES);
        // 执行日期
        bizTRN.setExedat(new Date());
        update(bizTRN);
    }

    @Override
    public void saveMakeLoansTRN(Map<String, Object> params) {
        Map<String, Object> debtInfoForMakeLoan = (Map)params.get("debtInfoForMakeLoan");
        // 构造交易流水对象
        BizTRN bizTRN = new BizTRN();
        // 交易代码
        bizTRN.setInifrm(BizContant.MAKE_LOANS_APPRL);
        // 交易名称
        bizTRN.setIninam(BizContant.MAKE_LOANS_NAME);
        // 登录柜员
        bizTRN.setIniusr(Long.valueOf(params.get("userId").toString()));
        // 业务编号
        bizTRN.setOwnref(StringUtil.objectToString(debtInfoForMakeLoan.get("debtCode")));
        // 业务表名称
        bizTRN.setObjtyp(BizContant.MAKE_LOANS_MAIN);
        // 业务表INR
        bizTRN.setObjinr(StringUtil.objectToLong(params.get("objInr").toString()));
        // 业务种类
        bizTRN.setBusinessTypes(BizContant.RENTAL_FACTORING_BUSINESS_TYPES);
        // 执行日期
        bizTRN.setExedat(new Date());
        update(bizTRN);
    }

    @Override
    public Page<BizTRN> checkDebtSchem(String u,String bizTRN) {
        Map<String,Object> map=new HashMap<>();
        map.put("iniusr", u);
        map.put("ownref",bizTRN);
        map.put("objtyp", "BIZ_DEBT_MAIN");
        Page<BizTRN> pagr=query(map);
        return pagr;
    }
}
