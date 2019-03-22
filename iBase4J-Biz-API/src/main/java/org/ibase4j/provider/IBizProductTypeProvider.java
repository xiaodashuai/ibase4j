/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.BizProductTypesDept;

import java.util.List;
import java.util.Map;

/**
 * 描述：产品种类管理
 * 日期：2018/7/19
 * @author czm
 * @version 1.0
 */
public interface IBizProductTypeProvider extends BaseProvider<BizProductTypes> {
	
	List<String> getProductTypeByDeptId(long deptId);
	
	boolean saveDeptProductTypes(List<BizProductTypesDept> deptProductTypes, Long deptId);
	
	List<BizProductTypes> queryProductList(Map<String, Object> params);
	
	BizProductTypes queryByNoticeTitle(String account);
	
	/**
	 * 功能 ：通过产品编码查产品对象
	 * @param code
	 * @return
	 */
	BizProductTypes getByCode(String code);
	/**
	 * 功能：查询产品种类根节点
	 * @return
	 */
	List<BizProductTypes> getRoot();
	/**
	 * 功能：通过父节点编码查询所有子节点
	 * @param parentCode
	 * @return
	 */
	List<BizProductTypes> getByParentCode(String parentCode);

	List<BizProductTypes> queryChildProduct();

	BizProductTypes queryByCode(String productTyepsCode);

	BizProductTypes queryByParentCode(String parentCode);
}
