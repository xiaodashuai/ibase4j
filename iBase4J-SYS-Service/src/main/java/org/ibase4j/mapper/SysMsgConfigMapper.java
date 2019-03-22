package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysMsgConfig;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-03-12
 */
public interface SysMsgConfigMapper extends BaseMapper<SysMsgConfig> {

	SysMsgConfig queryByAccount(String account);

}