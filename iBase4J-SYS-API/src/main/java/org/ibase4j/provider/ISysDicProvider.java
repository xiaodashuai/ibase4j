package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysDic;

import java.util.List;
import java.util.Map;

public interface ISysDicProvider extends BaseProvider<SysDic> {
	public Map<String, Map<String, String>> getAllDic();

	public void updateDic(SysDic record);

	public void deleteDic(Long id);

	public Page<SysDic> queryDic(Map<String, Object> params);

	public List<SysDic> queryCodeList(String type);

	public Map<String, String> queryDicByDicIndexKey(String key);

	public SysDic queryByCode(String account);

	public SysDic queryByCodeText(String account);

}
