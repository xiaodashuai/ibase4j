/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizRentalFactoring;

import java.util.List;
import java.util.Map;

/**
 * 功能：租金保理
 * @author lwh
 * 日期：2018/7/6
 */
public interface BizRemtalFactoringMapper extends BaseMapper<BizRentalFactoring>{
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    /**
    * @Description: 发放页面业务专有信息
    * @Param: [params]
    * @return: java.util.Map
    */
    Map getBizRentalFactoringForMakeLoan(@Param("cm")Map<String, Object> params);
}
