package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizFileMapper;
import org.ibase4j.model.BizFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "BizFile")
@Service(interfaceClass = BizFileProvider.class)
public class BizFileProviderImpl extends BaseProviderImpl<BizFile> implements BizFileProvider {

    private static final Logger log = LogManager.getLogger(BizFileProviderImpl.class);
    @Autowired
    private BizFileMapper bizFileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFile(BizFile file) {

        file.setId(IdWorker.getId());
        if(1 == bizFileMapper.saveFile(file)){
            return true;
        }else{
            log.error("save BizFile error!");
            return false;
        }
    }

//    private static final Logger log = LogManager.getLogger(BizLeaveProviderImpl.class);




}
