/**
 * 
 */
package org.ibase4j.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizTRN;
import org.ibase4j.vo.BizDebtInfo;

import java.util.List;
import java.util.Map;

/**
 * 功能：交易流水表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizTRNMapper extends BaseMapper<BizTRN>{

    /**
     * 查询方案概要信息
     * @param page
     * @param params
     * @return
     */
    List<BizDebtInfo> selectSummaryInfo(Pagination page, @Param("cm") Map<String, Object> params);

    /**
     * 查询放款概要信息
     * @param page
     * @param params
     * @return
     */
    List<BizDebtInfo> getMakeLoansDebts(Pagination page, @Param("cm") Map<String, Object> params);

}
