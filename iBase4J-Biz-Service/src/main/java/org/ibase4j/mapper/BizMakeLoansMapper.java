/**
 * 
 */
package org.ibase4j.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizMakeLoans;

import java.util.List;
import java.util.Map;

/**
 * 功能：放款
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizMakeLoansMapper extends BaseMapper<BizMakeLoans>{
    /**
     * @Description: 查询发放台账列表 带分页
     * @Param: [param]
     * @return: java.util.List
     */
    List getGrantStandingBookList(Page page, @Param("cm") Map<String,Object> param);

    /**
     * @Description: 查询发放台账列表
     * @Param: [param]
     * @return: java.util.List
     */
    List getGrantStandingBookList(@Param("cm") Map<String,Object> param);

    /**
     * @Description: 发放金额流水明细
     * @Param: [param]
     * @return: java.util.List
     */
    List getGrantAMTDetailForStandingBook(@Param("cm") Map<String,Object> param);

    /** 
    * @Description: 统计信息查询列表 带分页
    * @Param: [page, param] 
    * @return: java.util.List 
    */ 
    List getStatisticInfoList(Page page,@Param("cm") Map<String,Object> param);

    /**
     * @Description: 统计信息查询列表
     * @Param: [page, param]
     * @return: java.util.List
     */
    List getStatisticInfoList(@Param("cm") Map<String,Object> param);

    /**
    * @Description: 通过币种英文码获取币种数字码
    * @Param: [param]
    * @return: java.lang.String
    */
    String getCurrencyNo(@Param("cm") Map<String,Object> param);
    
    /** 
    * @Description: 查询废弃放款信息 
    * @Param: [grantCode] 
    * @return: java.util.Map 
    */ 
    Map getDiscardMakeLoansInfo(@Param("grantCode") String grantCode);
}
