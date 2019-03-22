package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizTemporarySave;

import java.util.List;

public interface BizTemporarySaveMapper extends BaseMapper<BizTemporarySave> {

    List queryLikeBizCode(String bizCode);

}
