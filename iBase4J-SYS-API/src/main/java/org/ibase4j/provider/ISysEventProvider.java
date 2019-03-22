package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysEvent;

import java.util.List;

public interface ISysEventProvider extends BaseProvider<SysEvent>{

	List<SysEvent> queryByAccount(String account);

}
