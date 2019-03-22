/**
 * 
 */
package org.ibase4j.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizDebtGrant;

import java.util.List;
import java.util.Map;

/**
 * 功能：债项发放申请
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizDebtGrantMapper extends BaseMapper<BizDebtGrant>{

    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    /**
    * @Description: 查询满足发放条件的债项发放方案
    * @Param: [params]
    * @return: java.util.List
    */
    List getMakeLoansDebts(Page page, @Param("cm") Map<String, Object> params);
    /**
     * 功能：查询产品总共发放的次数
     * @param productId 产品id 
     * @return 次数
     */
    int getCountByProductId(@Param("productId") String productId);

    /**
     * @Description: 查询债项发放产品相关信息
     * @Param: [param]
     * @return: java.util.Map
     */
    Map getDebtInfoForMakeLoan(@Param("cm") Map<String, Object> param);

    /**
     * @Description: 查询产品用信主体信息
     * @Param: [param]
     * @return: java.util.List
     */
    List getProductCustomerInfo(@Param("cm") Map<String,Object> param);

    /**
     * @Description: 查询方案台账列表
     * @Param: [param]
     * @return: java.util.List
     */
    List getDebtStandingBookList(Page page, @Param("cm") Map<String,Object> param);
    
    /**
     * 功能：查询暂存的发放信息
     * @author czm
     * @param param
     * @return
     */
    List<Map<String,Object>> getTempList(@Param("cm") Map<String,Object> param);

    /**
     * 地区号
     * @param param
     * @return
     */
    String getAreaNumberByUserId(@Param("cm") Map<String,Object> param);
    
    
    List<Long> selectIdPage(Page page, @Param("cm") Map<String, Object> params);

}
