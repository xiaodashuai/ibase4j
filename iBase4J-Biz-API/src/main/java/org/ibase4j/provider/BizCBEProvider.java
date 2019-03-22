package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCBE;
import org.ibase4j.vo.BookkeepkingVo;

import java.math.BigDecimal;


/**
 * 功能：发生额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizCBEProvider extends BaseProvider<BizCBE> {
	/**
	 * 功能：删除发放审核对应的CBE
	 * @param grantId
	 * @return
	 */
	int deleteByGrant(Long grantId);


	/**
	 * 金额参数为余额（下一阶段影响上一阶段的，如条件占方案额度，传递的金额需要计算之后再传递。折算规则可能不同）
	 * @param vo.objtyp 表名
	 * @param vo.objinr 表主键
	 * @param vo.trnId  流水表id
	 * @param vo.cbecbt 记账发生额类型
	 * @param vo.cbbcbc 记账余额类型
	 * @param vo.cur 主币种
	 * @param vo.amt 主金额（余额）
	 * @param vo.xrfcur 折算币种
	 * @param vo.xrfamt 折算金额 (折算之后的余额)
	 * @param vo.date 记账日期
	 * @param vo.bankTellerId 信贷员/客户号
	 * @return true 已记账; false 金额无变动
	 * @author hannasong
	 */
	public boolean bookkeepking(BookkeepkingVo vo);


    /**
     *
     * @param repaymentAmt 还款金额
     * @param cur   还款币种
     * @param grantCode 发放编号
     * @param bankTellId 信贷员客户经理
     * @return
     */
	public boolean bookLimit(BigDecimal repaymentAmt, String cur, String grantCode, Long bankTellId);


    /**
     * 根据流水主键，删除此条流水对应的发生额和余额
     * @param trninr
     */
    public void delCbeCbbByTrninr(Long trninr);

}
