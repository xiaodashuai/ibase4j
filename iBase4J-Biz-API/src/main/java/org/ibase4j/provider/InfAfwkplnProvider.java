package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.InfAfwkpln;

/**
 * 功能：贷款还款计划文件
 * @author hannasong
 */
public interface InfAfwkplnProvider extends BaseProvider<InfAfwkpln> {

    /**
     * 查询还款计划,将项目相关的计划信息放置到redis中；提升查询效率；
     */
    void fetchRepaymentPlan();

}
