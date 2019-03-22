package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysMsgConfig;

/**
 * @author ShenHuaJie
 *
 */
public interface ISysMsgConfigProvider extends BaseProvider<SysMsgConfig>{

	SysMsgConfig queryByaccount(String account);

}
