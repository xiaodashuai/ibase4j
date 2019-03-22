package org.ibase4j.core.base;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.Constants;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.WebUtil;

import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseService<P extends BaseProvider<T>, T extends BaseModel> {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected P provider;

	/** 修改 */
	public void update(T record) {
		Long uid = WebUtil.getCurrentUser();
		if (record.getId() == null) {
			record.setCreateBy(uid == null ? 1 : uid);
		}
		record.setUpdateBy(uid == null ? 1 : uid);
		provider.update(record);
	}

	/** 删除 */
	public void del(Long id) {
		Assert.notNull(id, "ID");
		provider.del(id, WebUtil.getCurrentUser());
	}

	/** 删除 */
	public void delete(Long id) {
		Assert.notNull(id, "ID");
		provider.delete(id);
	}

	/** 根据Id查询 */
	@SuppressWarnings("unchecked")
	public T queryById(Long id) {
		Assert.notNull(id, "ID");
		StringBuilder sb = new StringBuilder(Constants.CACHE_NAMESPACE);
		String className = this.getClass().getSimpleName().replace("Service", "");
		sb.append(className.substring(0, 1).toLowerCase()).append(className.substring(1));
		sb.append(":").append(id);
		T record = (T) CacheUtil.getCache().get(sb.toString());
		if (record == null) {
			record = provider.queryById(id);
		}
		return record;
	}
	
	/**
	 * 功能: 根据条件查询出符合条件的唯一一个对象
	 * @param params
	 * @return 数据库存在一个对象即返回,否则返回空
	 */
	public T queryOne(Map<String, Object> params) {
		List<T> list = this.queryList(params);
		Assert.notNull(list, "List");
		if(list!= null && list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
	}

	/** 条件查询 */
	public Page<T> query(Map<String, Object> params) {
		return provider.query(params);
	}

	/** 条件查询 */
	public List<T> queryList(Map<String, Object> params) {
		return provider.queryList(params);
	}

	/** 条件查询 */
	public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
		return provider.queryMap(params);
	}
}
