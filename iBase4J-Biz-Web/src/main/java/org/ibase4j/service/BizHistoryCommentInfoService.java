package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizHistoryCommentInfo;
import org.ibase4j.provider.BizHistoryCommentInfoProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
@Service
public class BizHistoryCommentInfoService extends BaseService<BizHistoryCommentInfoProvider,BizHistoryCommentInfo> {

    @Reference
    public void setBizHistoryCommentInfoProvider(BizHistoryCommentInfoProvider provider){
        this.provider = provider;
    }

    @Autowired
    private BizUserService bizUserService;

    /**
    * @Description: 保存意见信息
    * @Param: [params]
    * @return: void
    */
    public void saveCommentInfo(Map<String, Object> params){
        // 通过userId查询用户角色信息
        Long userId = Long.valueOf(params.get("userId").toString());
        Map<String, Object> userNameAndRoleName = bizUserService.getUserNameAndRoleName(userId);
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
        provider.update(bizHistoryCommentInfo);
    }

    /**
    * @Description: 根据债项编码查询历史意见
    * @Param: [busiCode]
    * @return: java.util.List<org.ibase4j.model.BizHistoryCommentInfo>
    */
    public List<BizHistoryCommentInfo> getHistoryCommentInfo(String busiCode){
        return provider.getHistoryCommentInfo(busiCode);
    }
}
