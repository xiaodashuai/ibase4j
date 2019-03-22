/**
 * 
 */
package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizGuaranteeInfo;

import java.util.List;

/**
 * 功能： 担保信息表
 * 日期：2018/7/6
 * @author czm
 */
public interface BizGuaranteeInfoMapper extends BaseMapper<BizGuaranteeInfo>{
	/**
	 * 查询方案的担保信息
	 * @param debtNum 方案编码
	 * @return
	 */
	List<String> queryStrDebtNum(String debtNum);
}
