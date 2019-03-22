package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysDicType;

import java.util.Map;

public interface ISysDicTypeProvider extends BaseProvider<SysDicType> {
//	public Map<String, Map<String, String>> getAllDicType();

	public void updateDicType(SysDicType record);

	public void deleteDicType(Long id);

	public Page<SysDicType> queryDicType(Map<String, Object> params);

	public Map<String, String> queryDicByDicTypeIndexKey(String key);

	public SysDicType queryByCode(String account);

	public SysDicType queryByCodeText(String account);
}
