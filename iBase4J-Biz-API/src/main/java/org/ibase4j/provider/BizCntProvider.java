package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCnt;

public interface BizCntProvider extends BaseProvider<BizCnt> {

    /**
     * 根据参数名获取下个序号，不存在返回1
     */
    int getNextNumber(String name);

    /**
     * 获取序列号
     */
    int getNextSEQNO(String name);

    /**
     * 根据参数名获取下个序号，不存在返回1,前面补0,m为长度(4:0001)
     */
    String getNextNumberFormat(String name, int format);
}
