package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 分行业务经办相关
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-06-12
 */
@Service
public class BizBranchBusiSponsorService extends BaseService{

    @Reference
    private BizBranchBusiSponsorProvider bizBranchBusiSponsorProvider;
    @Reference
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizHistoryCommentInfoService bizHistoryCommentInfoService;

    /**
    * @Description: 分行业务经办提交check表单并进行业务流转
    * @Param: [params]
    * @return: void
    */
    @Transactional(rollbackFor = Exception.class)
    public void submitCheckForm(Map<String,Object> params){
        bizBranchBusiSponsorProvider.submitCheckForm(params);
    }

    /** 
    * @Description: 获取流程业务编码 
    * @Param: [userId, piid] 
    * @return: java.util.Map 
    */ 
    public Map getDebtOutlineMessage(String userId,String piid ){
        Map dataMap = new HashMap();
        String BizId = bizProStatementProvider.getProcessInsBizId(userId,piid);
        dataMap.put("BizId",BizId);
        return dataMap;

    }
}
