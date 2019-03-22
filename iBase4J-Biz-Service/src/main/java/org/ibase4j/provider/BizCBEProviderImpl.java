/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.mapper.BizCBBMapper;
import org.ibase4j.mapper.BizCustLimitMapper;
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.mapper.InfAfpaexoMapper;
import org.ibase4j.model.BizCBB;
import org.ibase4j.model.BizCBE;
import org.ibase4j.model.BizCustLimit;
import org.ibase4j.model.InfAfpaexo;
import org.ibase4j.vo.BookkeepkingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：发生额信息表
 * @author hannasong
 */
@CacheConfig(cacheNames = "BizCBE")
@Service(interfaceClass = BizCBEProvider.class)
public class BizCBEProviderImpl extends BaseProviderImpl<BizCBE> implements BizCBEProvider {

    @Autowired
    private BizCBBProvider bizCBBProvider;
    @Autowired
    private BizCBBMapper bizCBBMapper;
    @Autowired
    private BizCustLimitMapper bizCustLimitMapper;
    @Autowired
    private InfAfpaexoMapper infAfpaexoMapper;
    @Reference
    private ISysCurrencyProvider sysCurrencyProvider;
    @Autowired
    private BizDebtGrantMapper bizDebtGrantMapper;


	@Override
	public int deleteByGrant(Long grantId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("objType", BizContant.BIZ_TABLE_GRANT);
		params.put("objInr", grantId);
		
		return this.deleteByParams(params);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
	public void delCbeCbbByTrninr(Long trninr){

    try {

        Map<String, Object> cbeParams = new HashMap<String,Object>();
        cbeParams.put("trninr", trninr);
        List<BizCBE> result = queryList(cbeParams);
        if (result != null && !result.isEmpty()) {
            for (BizCBE entity : result) {
                //判断不为空再删除,一条发生额对应一条余额,若删除的余额存在上一条记录，则恢复日期
                if (entity != null) {
                    Long id = entity.getId();
                    BizCBB selDelCBB = new BizCBB();
                    selDelCBB.setCbeInr(id);
                    BizCBB delCBB = bizCBBMapper.selectOne(selDelCBB);

                    BizCBB sellastCBB = new BizCBB();
                    sellastCBB.setEnddat(delCBB.getBegdat());
                    sellastCBB.setObjType(delCBB.getObjType());
                    sellastCBB.setObjInr(delCBB.getObjInr());
                    sellastCBB.setCbc(delCBB.getCbc());
                    BizCBB lastCBB = bizCBBMapper.selectOne(sellastCBB);
                    Integer uplastcbb = null;
                    if(null != lastCBB){
                        lastCBB.setEnddat(DateUtil.stringToDate("22991231 23:59:59"));
                        uplastcbb = bizCBBMapper.updateById(lastCBB);
                    }

                    int delcbe = mapper.deleteById(entity);
                    int delcbb = bizCBBMapper.deleteById(delCBB);
                    if(delcbe != 1 || delcbb != 1 || (null!=uplastcbb && uplastcbb!=1)){
                        logger.error("删除历史台账出现异常。sqlcount=>"+delcbe+"="+delcbb+"="+uplastcbb);
                        throw new RuntimeException("台账数据更新异常!");
                    }
                }
            }
        }
        } catch (Exception es) {
            es.printStackTrace();
            throw new RuntimeException("delCbeCbbByTrninr error! reason=="+es.getMessage());
        }
    }


    //true 则标记已记录发生额和余额
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bookkeepking(BookkeepkingVo vo) {

        String objtyp = vo.getObjtyp();
        long objinr = vo.getObjinr();
        long trnId = vo.getTrnId();
        String cbecbt = vo.getCbecbt();
        String cbbcbc = vo.getCbbcbc();
        String cur = vo.getCur();
        BigDecimal amt = vo.getAmt();
        String xrfcur = vo.getXrfcur();
        BigDecimal xrfamt = vo.getXrfamt();
        Date date = vo.getDate();
        long bankTellerId = vo.getBankTellerId();
        Long enable = vo.getEnable();

        try{

            Date date2300 = DateUtil.stringToDate("22991231 23:59:59");
            if(null == date){
                date = new Date();
            }
            long cbeinr = IdWorker.getId();
            BizCBE addCBE = new BizCBE();
            addCBE.setId(cbeinr);
            addCBE.setTrninr(trnId);
            addCBE.setCbt(cbecbt);
            addCBE.setCur(cur);
            addCBE.setXrfcur(xrfcur);
            addCBE.setDat(date);
            addCBE.setObjType(objtyp);
            addCBE.setObjInr(objinr);
            addCBE.setCreateTime(date);
            addCBE.setCreateBy(bankTellerId);
            addCBE.setUpdateTime(date);
            addCBE.setUpdateBy(bankTellerId);
            BizCBB finalCBB = new BizCBB();
            finalCBB.setCbeInr(cbeinr);
            finalCBB.setId(IdWorker.getId());
            finalCBB.setCbc(cbbcbc);
            finalCBB.setCur(cur);
            finalCBB.setXrfcur(xrfcur);
            finalCBB.setObjType(objtyp);
            finalCBB.setObjInr(objinr);
            finalCBB.setBegdat(date);
            finalCBB.setEnddat(date2300);
            finalCBB.setCreateTime(date);
            finalCBB.setCreateBy(bankTellerId);
            finalCBB.setUpdateTime(date);
            finalCBB.setUpdateBy(bankTellerId);
            if(enable==0){
                addCBE.setEnable(0);
                finalCBB.setEnable(0);
            }else {
                addCBE.setEnable(1);
                finalCBB.setEnable(1);
            }

            //查询同类型的历史余额记录
            BizCBB oldCBB = bizCBBProvider.selectOne(new EntityWrapper<>(new BizCBB(objtyp, objinr, cbbcbc, null, null, null, null, null,date2300)));
            if(null != oldCBB){
                //额度不同，产生发生额，以cur、amt为准（条件记条件，条件影响的方案也是准确的）,币种不会修改，直接比对即可
                if(!oldCBB.getCur().equals(cur)){
                    logger.info("查询参数异常，币种发生了修改,原币种（"+oldCBB.getCur()+"、"+oldCBB.getXrfcur()+"）,新币种（"+cur+"、"+xrfcur+"）");
                    return false;
                }
                if( amt.compareTo(oldCBB.getAmt()) == 0 ){
                    return false;
                }else{

                    BigDecimal oldAmt = oldCBB.getAmt();
                    BigDecimal oldXrfAmt = oldCBB.getXrfamt();
                    BigDecimal absAmt = amt.subtract(oldAmt).abs();
                    BigDecimal absXrfAmt = null;
                    if(null != xrfamt){
                        if(null != oldXrfAmt){
                            absXrfAmt = xrfamt.subtract(oldXrfAmt).abs();
                        }else{
                            absXrfAmt = xrfamt.subtract(new BigDecimal(0)).abs();
                        }
                    }

                    addCBE.setAmt(absAmt);
                    addCBE.setXrfamt(absXrfAmt);
                    if( amt.compareTo(oldAmt) == 1 ){
                        finalCBB.setAmt(oldAmt.add(absAmt));
                    }else{
                        finalCBB.setAmt(oldAmt.subtract(absAmt));
                    }

                    if(null != xrfamt){
                        if(null != oldXrfAmt){
                            if( xrfamt.compareTo(oldXrfAmt) == 1 ){
                                finalCBB.setXrfamt(oldXrfAmt.add(absXrfAmt));
                            }else{
                                finalCBB.setXrfamt(oldXrfAmt.subtract(absXrfAmt));
                            }
                        }else{
                            finalCBB.setXrfamt(absXrfAmt);
                        }
                    }

                    //需要更新旧记录
                    oldCBB.setEnddat(date);
                    oldCBB.setUpdateTime(date);
                    oldCBB.setUpdateBy(bankTellerId);
                    if(enable!=0){
                        bizCBBMapper.updateById(oldCBB);
                    }
                }

            }else{
                //不存在历史直接记
                addCBE.setAmt(amt);
                addCBE.setXrfamt(xrfamt);
                finalCBB.setAmt(amt);
                finalCBB.setXrfamt(xrfamt);
            }

            mapper.insert(addCBE);
            bizCBBMapper.insert(finalCBB);
            return true;

        } catch (Exception es) {
            es.printStackTrace();
            throw new RuntimeException("bookkeepking error! reason=="+es.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bookLimit(BigDecimal repaymentAmt,String cur,String grantCode, Long bankTellId) {

	    BigDecimal xrfAmt = null;
	    String xrfcur = null;

        if(!"156".equals(cur)){
            //不为人民币时，金额需要折算
            xrfAmt = repaymentAmt;
            xrfcur = sysCurrencyProvider.getCodeByNO(cur);
            if(null == xrfcur || "N/A".equals(xrfcur)){
                throw new RuntimeException("未获取折算币种对应的码值;sel cur="+cur+" xrfcur="+xrfcur);
            }
            Map selMap = new HashMap();
            selMap.put("userId",bankTellId);
            String zoneno = bizDebtGrantMapper.getAreaNumberByUserId(selMap);
            if(null == zoneno || "".equals(zoneno)){
                throw new RuntimeException("未找到客户的地区号;sel bankTellId="+bankTellId);
            }
            InfAfpaexo afp = new InfAfpaexo();
            afp.setZoneno(zoneno);
            afp.setCurrtype(cur);
            afp.setEnable(1);
            afp = infAfpaexoMapper.selectOne(afp);
            if(afp==null){
                throw new RuntimeException("未获取到折算牌价;sel afp="+afp.toString());
            }
            repaymentAmt = repaymentAmt.multiply(new BigDecimal(afp.getCnyvrate()));
        }

        Wrapper<BizCustLimit> wrapper = new EntityWrapper<>();
        wrapper.eq("GRANT_CODE",grantCode).eq("ENABLE_",1);
        List<BizCustLimit> limitList = bizCustLimitMapper.selectList(wrapper);

        for ( BizCustLimit lim : limitList ) {
            repaymentAmt = repaymentAmt.multiply(lim.getOccRatio());
            BizCBB selCbb = new BizCBB("BIZ_CUST_LIMIT",lim.getFirstID(),BizContant.LIMITSUM_CBCTXT,DateUtil.stringToDate("22991231 23:59:59"));
            selCbb.setEnable(1);
            BizCBB lastCbb = bizCBBMapper.selectOne(selCbb);
            if(null != lastCbb){
                repaymentAmt = lastCbb.getAmt().subtract(repaymentAmt);
                BookkeepkingVo vo = new BookkeepkingVo("BIZ_CUST_LIMIT",
                        lim.getFirstID(),
                        lim.getTrninr(),
                        BizContant.LIMIT_REPOUT_CBTTXT,
                        BizContant.LIMITSUM_CBCTXT,"CNY",repaymentAmt,xrfcur,xrfAmt,lim.getUpdateTime(),lim.getBankTellerId());
                //标记为未生效的台账
                vo.setEnable(0);
                boolean limRes = this.bookkeepking(vo);
                if(!limRes){
                    throw new RuntimeException("bookLimit ERROR=repaymentAmt="+repaymentAmt+" lim="+lim.toString()+" vo="+vo.toString());
                }
            }else{
                throw new RuntimeException("未找到放款记录;sel lastCbb="+lastCbb.toString());
            }
        }

        return false;
    }


}
