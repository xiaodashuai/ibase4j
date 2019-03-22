package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.provider.BizBranchBusiCommissionerProvider;
import org.ibase4j.provider.BizBranchBusiSponsorProvider;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 分行业务处长相关
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-06-12
 */
@Service
public class BizBranchBusiCommissionerService extends BaseService{

    @Reference
    private BizBranchBusiCommissionerProvider bizBranchBusiCommissionerProvider;

    /** 
    * @Description:  获得分行业务待办提交的check值
    * @Param: [params] 
    * @return: java.util.Map<java.lang.String,java.lang.Object> 
    */
    public Map<String,Object> getCheckValues(Map<String,Object> params){
        return bizBranchBusiCommissionerProvider.getCheckValues(params);
    }
    
}
