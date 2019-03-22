package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysUserRole;

import java.util.List;
import java.util.Map;

public interface ISysUserRoleProvider extends BaseProvider<SysUserRole> {
    /**
     * @Description: 条件查询roleId
     * @Param: [SysUserRole]
     * @return: org.ibase4j.model.SysUserRole
     */
    List<SysUserRole> selectOneSysUserRole(Map<String, Object> params);
}
