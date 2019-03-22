/**
 * 
 */
package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizDebtGrant;

import java.util.List;
import java.util.Map;

/**
 * 功能：债项发放申请
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizDebtGrantProvider extends BaseProvider<BizDebtGrant> {

    /**
     * @Description: 查询满足发放条件的债项发放方案
     * @Param: [params]
     * @return: java.util.List
     */
    Page getMakeLoansDebts(Map<String, Object> params);


    /** 
    * @Description: 更新债项发放方案状态
    * @Param: [param]
    * @return: void 
    */ 
    void updateDebtGrantStatus(String grantCode,Integer grantStatus);

    /**
     * 功能：查询产品总共发放的次数
     * @param productId 产品id 
     * @return 次数
     */
    int getCountByProductId(String productId);
    
    /**
     * 功能：数据迁移保存发放信息，包括债项发放表、CBB、CBE、FEC、FEP、还款计划、政策信息、专有信息，不启动审核流程
     * @param record
     * @return
     */
    BizDebtGrant updateNofw(Map<String, Object> record);
    
    /**
     * 功能：修改发放信息，包括债项发放表、CBB、CBE、FEC、FEP、还款计划、政策信息、专有信息
     * @param record
     * @return
     */
    BizDebtGrant update(Map<String, Object> record) throws Exception;
    
    /**
     * 功能：保存发放信息，包括债项发放表、CBB、CBE、FEC、FEP、还款计划、政策信息、专有信息
     * @param record
     * @return 成功返回含有发放编码的发放对象，否则返回null
     */
    BizDebtGrant save(Map<String, Object> record) throws Exception;

    /**
     * @Description: 查询债项发放产品相关信息
     * @Param: [param]
     * @return: java.util.Map
     */
    Map getDebtInfoForMakeLoan(Map<String,Object> param);

    /**
    * @Description: 查询产品用信主体信息
    * @Param: [param]
    * @return: java.util.List
    */
    List getProductCustomerInfo(Map<String,Object> param);

    /**
     * 功能：债项发放相关表数据转换成页面需要的Map数据,同时生成新的流水号
     * @param
     * @return
     */
    Map<String,Object> getEditGrant(String grantCode, String debtCode);
    
    /**
     * 功能：发起废弃流程
     * @param record
     * @return
     */
    BizDebtGrant saveDiscard(Map<String, Object> record);
    
    /**
     * 功能：查询暂存的发放信息
     * @param param
     * @return
     */
    List<Map<String,Object>> getTempList(Map<String, Object> param);

    /** 
    * @Description: 通过id查询发放数据 
    * @Param: [grantId] 
    * @return: org.ibase4j.model.BizDebtGrant 
    */ 
    BizDebtGrant selectByGrantId(String grantId);

    /**
     * 100条数据分页查询
     * */
    Page<BizDebtGrant> queryByHundred(Map<String, Object> param);
}
