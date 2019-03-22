package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.BizHistoryCommentInfo;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 历史意见信息
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
@CacheConfig(cacheNames = "bizHistoryCommentIn ")
@Service(interfaceClass = BizHistoryCommentInfoProvider.class)
public class BizHistoryCommentInfoProviderImpl extends BaseProviderImpl<BizHistoryCommentInfo> implements BizHistoryCommentInfoProvider {

    @Reference
    private ISysUserProvider sysUserProvider;
    
    @Override
    public void saveCommentInfo(Map<String, Object> params) {
        // 通过userId查询用户角色信息
        Long userId = Long.valueOf(params.get("userId").toString());
        Map<String, Object> userNameAndRoleName = sysUserProvider.getUserNameAndRoleName(userId);
        // 构建commentInfo对象
        BizHistoryCommentInfo bizHistoryCommentInfo = new BizHistoryCommentInfo();
        bizHistoryCommentInfo.setTaskId(((Map)params.get("processParams")).get("taskId").toString());
        bizHistoryCommentInfo.setUserId(userId);
        bizHistoryCommentInfo.setUserName(userNameAndRoleName.get("userName").toString());
        bizHistoryCommentInfo.setRoleId(Long.valueOf(userNameAndRoleName.get("roleId").toString()));
        bizHistoryCommentInfo.setRoleName(userNameAndRoleName.get("roleName").toString());
        bizHistoryCommentInfo.setCommentInfo(((Map)params.get("processParams")).get("commentValue").toString());
        bizHistoryCommentInfo.setCommentId(((Map)params.get("processParams")).get("tdid").toString());
        bizHistoryCommentInfo.setEnable(1);
        update(bizHistoryCommentInfo);
    }

    @Override
    public List<BizHistoryCommentInfo> getHistoryCommentInfo(String busiCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("busiCode",busiCode);
        map.put("enable",1);
        List<BizHistoryCommentInfo> bizHistoryCommentInfos = queryList(map);
        return bizHistoryCommentInfos;
    }
}
