package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.InfAfpcmem;

/**
 * 功能：口行贷款合同文件
 * @author hannasong
 */
public interface InfAfpcmemProvider extends BaseProvider<InfAfpcmem> {

    /**
     *  查询合同接口，根据介质识别号获取协议编号
     * @return Protseno
     */
    String getProtseno(String identNumber);

}
