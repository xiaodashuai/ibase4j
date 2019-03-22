package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysCurrency;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 货币管理Mapper接口
 * </p>
 *
 * @author czm
 * @since 2018-07-17
 */
@CacheConfig(cacheNames = "sysCurrency")
@Service(interfaceClass = ISysCurrencyProvider.class)
public class SysCurrencyProviderImpl extends BaseProviderImpl<SysCurrency> implements ISysCurrencyProvider {

	@Override
	public SysCurrency getByCode(String code) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("monCode", code);
		List<SysCurrency> list = this.queryList(params);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
