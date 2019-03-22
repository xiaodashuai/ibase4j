package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCanvas;

import java.util.List;
import java.util.Map;

/**
 * 快照
 */
public interface CanvasProvider extends BaseProvider<BizCanvas> {

    /**
     * 保存快照;
     * @return 结果
     */
    boolean savCanvas(String imgStr,BizCanvas canvas);

    /**
     * 暂存时删除快照，节省磁盘占用
     * @param params
     */
    void delCanvas(Map<String, Object> params);

    /**
     * 查询快照;
     * @param params
     * @return base64 List
     */
    List<String> queryImgList(Map<String, Object> params);

}
