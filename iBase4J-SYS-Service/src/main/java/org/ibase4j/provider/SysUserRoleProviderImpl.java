package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysUserRoleMapper;
import org.ibase4j.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "SysUserRole")
@Service(interfaceClass = ISysUserRoleProvider.class)
public class SysUserRoleProviderImpl extends BaseProviderImpl<SysUserRole> implements  ISysUserRoleProvider{
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public List<SysUserRole> selectOneSysUserRole(Map<String, Object> params) {
        return sysUserRoleMapper.queryRoleIds(params);

    }

}
