package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCanvas;

import java.util.List;
import java.util.Map;

public interface BizCanvasMapper extends BaseMapper<BizCanvas> {


    //根据条件,查询最新的快照信息
    List<BizCanvas> selectCanvasList(@Param("cm")Map<String, Object> params);


}
