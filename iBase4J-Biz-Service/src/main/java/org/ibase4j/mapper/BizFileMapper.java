package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizFile;

public interface BizFileMapper extends BaseMapper<BizFile> {

    /**
     * 保存附件
     * @param BizFile
     * @return
     */
    int saveFile(@Param("cm")BizFile BizFile);
}
