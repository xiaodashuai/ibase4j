/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.BizStatus;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizTRN;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

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
<<<<<<< HEAD
        bizTRN.setExedat(new Date());
        update(bizTRN);
=======
        bizTRN1.setExedat(new Date());
        // 存盘时间
        bizTRN1.setInidattim(new Date());
        //发放条件状态已审批
        bizTRN1.setBizStatus(BizStatus.GRANUNPU);
        //发放流程--已审批
        bizTRN1.setProcessStatus(BizStatus.GRPRAPPR);
        //登录柜员
        bizTRN1.setIniusr(Long.valueOf(params.get("userId").toString()));
        bizTRN1.setRelflg("Y");
        bizTRN1.setRelres("Y");
        update(bizTRN1);
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
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

        // 重新定义了交易码和交易名 ypf
        // 交易代码
        // bizTRN.setInifrm(BizContant.MAKE_LOANS_APPRL);
        bizTRN.setInifrm(BizContant.LOANS_PLAN_APPRL);
        // 交易名称
        // bizTRN.setIninam(BizContant.MAKE_LOANS_NAME);
        bizTRN.setIninam(BizContant.LOANS_PLAN_NAME);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTRNStatus(BizTRN bizTRN){

        Date updateTime = new Date();

        if("Y".equals(bizTRN.getRelflg())){
            BizTRN selRelflgTrn = new BizTRN();
            selRelflgTrn.setOwnref(bizTRN.getOwnref());
            selRelflgTrn.setObjtyp(bizTRN.getObjtyp());
            selRelflgTrn.setObjinr(bizTRN.getObjinr());
            selRelflgTrn.setRelflg("Y");
            BizTRN flgTrn = mapper.selectOne(selRelflgTrn);
            if(null != flgTrn){
                flgTrn.setRelflg("N");
                flgTrn.setUpdateTime(updateTime);
                if("Y".equals(bizTRN.getRelres())){
                    bizTRN.setExedat(updateTime);
                    if("Y".equals(flgTrn.getRelres())){
                        flgTrn.setRelres("N");
                    }else{
                        BizTRN selRelresTrn = new BizTRN();
                        selRelresTrn.setOwnref(bizTRN.getOwnref());
                        selRelresTrn.setObjtyp(bizTRN.getObjtyp());
                        selRelresTrn.setObjinr(bizTRN.getObjinr());
                        selRelresTrn.setRelres("Y");
                        BizTRN resTrn = mapper.selectOne(selRelresTrn);
                        if(null != resTrn && !resTrn.getId_().equals(flgTrn.getId_())){
                            resTrn.setRelres("N");
                            resTrn.setUpdateTime(updateTime);
                            if(mapper.updateById(resTrn) != 1){
                                logger.error("更新BizTRN relres 失败");
                                throw new RuntimeException("更新BizTRN relres 失败"+resTrn.toString());
                            }
                        }
                    }
                }
                if(mapper.updateById(flgTrn) != 1){
                    logger.error("更新BizTRN relflg 失败");
                    throw new RuntimeException("更新BizTRN relflg 失败"+flgTrn.toString());
                }
            }

        }else {
            logger.error("未设置BizTRN relflg");
        }
        if((null!=bizTRN.getId() && null != mapper.selectById(bizTRN.getId())) || "DEBTDOWN".equals(bizTRN.getInifrm())){
            bizTRN.setUpdateTime(updateTime);
            mapper.updateById(bizTRN);
        }else{
            mapper.insert(bizTRN);
        }
    }

}
