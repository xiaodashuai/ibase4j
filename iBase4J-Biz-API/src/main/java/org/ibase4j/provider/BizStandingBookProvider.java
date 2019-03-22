package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizDebtGrant;

import java.util.List;
import java.util.Map;

/**
 * @Description: 台账
 * @Author: dj
 * @CreateDate: 2018-08-31
 */
public interface BizStandingBookProvider extends BaseProvider<BizDebtGrant>{
    /**
     * @Description: 查询方案台账列表
     * @Param: [param]
     * @return: com.baomidou.mybatisplus.plugins.Page
     */
    Page getDebtStandingBookList(Map<String,Object> param);

    /**
    * @Description: 查询发放台账列表
    * @Param: [param]
    * @return: com.baomidou.mybatisplus.plugins.Page
    */
    Page getGrantStandingBookPage(Map<String,Object> param);

    List getGrantStandingBookList(Map<String,Object> param);
    /**
     * @Description: 方案信息台账 方案信息明细
     * @Param: [params]
     * @return: java.util.Map
     */
    Map getDebtInfoForStandingBook(@Param("cm") Map<String, Object> params);

    /**
     * @Description: 发放信息台账 发放信息明细
     * @Param: [params]
     * @return: java.util.Map
     */
    Map getGrantInfoForStandingBook(@Param("cm") Map<String, Object> params);

    /** 
    * @Description: 查询所有机构
    * @Param: [params] 
    * @return: java.util.List 
    */ 
    List getAllInstitution(Map<String, Object> params);

    /**
     * @Description: 发放金额流水明细
     * @Param: [param]
     * @return: java.util.List
     */
    List getGrantAMTDetailForStandingBook(Map<String,Object> param);

    /**
     * @Description: 统计信息查询列表
     * @Param: [param]
     * @return: com.baomidou.mybatisplus.plugins.Page
     */
    Page getStatisticInfoPage(Map<String,Object> param);

    /**
    * @Description: 统计信息查询列表
    * @Param: [param]
    * @return: java.util.List
    */
    List getStatisticInfoList(Map<String,Object> param);
}
