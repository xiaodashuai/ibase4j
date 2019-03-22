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
<<<<<<< HEAD
	
=======

    @Override
    public String getCodeByNO(String monNO) {

	    if("".equals(monNO) || null == monNO){
            return null;
        }

        String redisKey = Constants.CACHE_SYS_CURRENCY_NAMESPACE + monNO;

        if(redisHelper.exists(redisKey)){
            return (String) redisHelper.get(redisKey);
        }else {
            SysCurrency selCur = new SysCurrency();
            selCur.setMonCode(monNO);
            List<SysCurrency> curList = mapper.selectList(new EntityWrapper<SysCurrency>(selCur));
            if(curList.size()==0){
                redisHelper.set(redisKey,"N/A",86400);
                return "N/A";
            }else if(curList.size()==1){
                SysCurrency resCur = curList.get(0);
                redisHelper.set(redisKey,resCur.getMonCode(),86400);
                return resCur.getMonCode();
            }else{
                logger.error("sysCurrency ERROR===币种码表中不应该存在多条记录。币种号：("+monNO+") 表名==（SYS_CURRENCY）");
                throw new RuntimeException("sysCurrency ERROR:more than one record:table=SYS_CURRENCY:monNO="+monNO);
            }
        }
    }

>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
}
