package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizLocking;

import java.util.Map;


public interface BizLockingProvider extends BaseProvider<BizLocking> {
    /**
     * @Description: 保存用户操作记录锁表
     * @Param: [params]
     * @return: void
     */
    BizLocking submitUserLog (Map<String,Object> params);
    /**
     * @Description: 解锁
     * @Param: [params]
     * @return: void
     */
    void unlockUser(Map<String,Object> params);

}
