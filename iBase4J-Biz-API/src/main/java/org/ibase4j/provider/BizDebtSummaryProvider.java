package org.ibase4j.provider;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.vo.GrantRuleVerifVo;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 描述：债项概要
 * @author xiaoshuiquan
 * @date 2018/06/13
 */


public interface BizDebtSummaryProvider extends BaseProvider<BizDebtSummary> {
	
	/**
	 * 功能：查询已审核通过的债项方案<br/>
	 * 作者：陈志明<br/>
	 * 日期：2018/7/12<br/>
	 * 参数：查询参数map<br/>
	 * 返回：返回可发放的债项方案信息
	 */
	Page<BizDebtSummary> queryByCompletedSolutions(Map<String, Object> params);
	
	/**
	 * 功能：发放产品前需要验证发放的规则<br/>
	 * 作者：陈志明<br/>
	 * 日期：2018/7/12<br/>
	 * 参数：查询参数map<br/>
	 * 返回：返回可发放的债项方案信息
	 */
	List<GrantRuleVerifVo> getGrantRuleVo(Map<String, Object> params);


	Page getDebtInfo(Map<String, Object> params);


	boolean saveDebt(Map<String, Object> mapObj);


	/**
	 * @Description: 条件查询单条方案记录
	 * @Param: [BizDebtSummary]
	 * @return: org.ibase4j.model.BizDebtSummary
	 */
	 BizDebtSummary selectOneBizDebtSummary(BizDebtSummary bizDebtSummary);

	/**
	 * @Description: 方案信息台账 方案信息明细
	 * @Param: [params]
	 * @return: java.util.Map
	 */
	Map getDebtInfoForStandingBook(@Param("cm") Map<String, Object> params);

	/**
	 * @Description: 历史数据保存
	 * @Param: [params]
	 * @return: java.util.Map
	 */
    void historyDebtSave(Map<String, Object> params);
	
		/**
	 * 修改方案状态
	 * @param params
	 */
	void getSchemeState(Map<String, Object> params);

	//驳回后的重新提交
    void ReSaveDebt(Map<String, Object> mapObj);

    /** 
    * @Description: 通过债项方案编码查询债项方案主键 
    * @Param: [debtCode] 
    * @return: java.lang.Long 
    */ 
    Long selectDebtIdByDebtCode(String debtCode);

    	/**
    	* @Description: 刷新方案过期状态
    	* @Param:
    	* @return:
    	* @Author: xiaoshuiquan
    	* @Date: 2018/12/21
    	*/
    void refreshDebtExpired();
}
