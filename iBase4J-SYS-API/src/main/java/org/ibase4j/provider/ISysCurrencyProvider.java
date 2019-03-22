/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysCurrency;

/**
 * <p>
 * 货币单位管理
 * </p>
 * 
 * @author czm
 * @version 1.0
 * @since 2018-07-17
 */
public interface ISysCurrencyProvider extends BaseProvider<SysCurrency>{
	/**
	 * 根据编码查询对象
	 * @param code
	 * @return
	 */
	SysCurrency getByCode(String code);
}
