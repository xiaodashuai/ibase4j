package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCustLimit;

import java.util.List;
import java.util.Map;

/**
 * 功能：额度占用表
 * @author hannasong
 */
public interface BizCustLimitMapper extends BaseMapper<BizCustLimit>{

    /**
     * @Description: 查询额度占用信息集合
     * @Param: [params]
     * @return: java.util.List
     */
    List getCustLimitList(@Param("cm")Map<String, Object> params);
	
}
