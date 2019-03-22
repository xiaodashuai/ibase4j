package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizGuaranteeResult;

import java.util.List;
import java.util.Map;

/**
 * 功能： 发放流程中的担保信息 
 * 日期：2018/7/6
 * @author czm
 */
public interface BizGuaranteeResultMapper extends BaseMapper<BizGuaranteeResult> {
	
    /** 
    * @Description: 根据发放编码查询担保信息
    * @Param: [params] 
    * @return: java.util.List 
    */ 
    List getBizGuaranteeResultList(@Param("cm") Map<String, Object> params);

    /**
     * @Description: 额度占用接口参数
     * @Param: [params]
     * @return: java.util.List
     */
    List getGuaranteeInfoList(@Param("cm") Map<String, Object> params);
}
