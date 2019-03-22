package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizLocking;
import org.ibase4j.model.SysUser;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.provider.BizLockingProvider;
import org.ibase4j.provider.ISysUserProvider;
import org.ibase4j.provider.ISysUserRoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BizLockingService extends BaseService<BizLockingProvider,BizLocking> {
    @Reference
    public void setProvider(BizLockingProvider provider) {
        this.provider = provider;
    }
    @Reference
    private ISysUserProvider sysUserProvider;
    /**
     * 保存用户记录信息加锁
     * @param params
     */
    public BizLocking submitUserLog(Map<String, Object> params) {
        String userId = params.get("userId").toString();
        SysUser sysUser = sysUserProvider.queryById(Long.valueOf(userId));
        params.put("sysUser",sysUser);
        BizLocking bizLocking = provider.submitUserLog(params);
        return bizLocking;
    }

    /**
     * 解锁
     * @param params
     */
    public void unlockUser(Map<String,Object> params){
        provider.unlockUser(params);
    }
}
