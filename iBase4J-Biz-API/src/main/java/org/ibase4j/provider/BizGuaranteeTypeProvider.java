/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizGuaranteeType;
import org.ibase4j.vo.PairVo;

import java.util.List;
import java.util.Map;


/**
 * 功能：担保类型表 
 * 日期：2018/7/17
 * @author czm
 * @version 1.0
 */
public interface BizGuaranteeTypeProvider extends BaseProvider<BizGuaranteeType> {
	
	/**
	 * 功能：根据编码查询
	 * @param code
	 * @return
	 */
	PairVo getByCode(String code,String type);
	/**
	 * 功能：通过类型和父类编码查询业务类型
	 * @param typeCode 类型编码
	 * @param parentCode 父编码，如果是空，查询第一级的数据
	 * @return
	 */
	List<PairVo> getByTypeCode(String typeCode,String parentCode);
	
	/**
	 * 功能：通过多个类型和父类编码查询业务类型
	 * @param typeCodes 类型编码
	 * @param parentCodes 父编码，如果是空，查询第一级的数据
	 * @return
	 */
	List<PairVo> getMulitByTypeCode(String typeCodes,String parentCodes);
	
	/**
	 * 功能：通过查询参数查询业务类型
	 * @return
	 */
	List<PairVo> getByParams(Map<String,Object> params);
	
	/**
	 * 功能：根据类型字符串（逗号分割）和编码字符串（逗号分割）查询
	 * @param typeCode
	 * @param codes
	 * @return
	 */
	List<PairVo> getTypeCodes(String typeCode,String codes);
}
