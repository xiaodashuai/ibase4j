package org.ibase4j.core.support;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.model.SysEvent;

import java.util.List;
import java.util.Map;

public interface ISysEventService {
	public void update(SysEvent sysEvent);

	public Page<?> queryMap(Map<String, Object> params);

	public List<SysEvent> queryByAccount(String account);
}
