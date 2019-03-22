package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizHistoryCommentInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
public interface BizHistoryCommentInfoProvider extends BaseProvider<BizHistoryCommentInfo> {
    
    /** 
    * @Description: 保存意见信息
    * @Param: [params] 
    * @return: void 
    */ 
    void saveCommentInfo(Map<String, Object> params);
    
    /** 
    * @Description: 根据债项编码查询历史意见信息 
    * @Param: [busiCode] 
    * @return: java.util.List<org.ibase4j.model.BizHistoryCommentInfo> 
    */ 
    List<BizHistoryCommentInfo> getHistoryCommentInfo(String busiCode);
}
