package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizFile;

public interface BizFileProvider extends BaseProvider<BizFile> {

    /**
     * 保存附件
     * @param file
     * @return
     */
    boolean saveFile(BizFile file);

}
