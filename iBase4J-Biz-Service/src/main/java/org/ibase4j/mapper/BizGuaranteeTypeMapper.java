package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizGuaranteeType;
import org.ibase4j.vo.PairVo;

import java.util.List;

/**
 * 功能：担保类型表 日期：2018/7/17
 * 
 * @author czm
 * @version 1.0
 */
public interface BizGuaranteeTypeMapper extends BaseMapper<BizGuaranteeType> {
	
	List<Long> selectIdPage(@Param("cm") BizGuaranteeType guaranteeType);
	
	List<PairVo> queryByType(@Param("types")List<String> types,@Param("parentCodes")List<String> parentCodes);
	
}
