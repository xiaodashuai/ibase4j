package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.mapper.WfInsTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "wfInsTask")
@Service(interfaceClass = WfInsTaskProvider.class)
public class WfInsTaskProviderImpl implements WfInsTaskProvider {

    @Autowired
    private WfInsTaskMapper wfInsTaskMapper;

    @Override
    public Map selectRoleId(Map<String,Object> params){
        return wfInsTaskMapper.selectRoleId(params);
    }
    @Override
    public Map selectRoleIdPrevious(Map<String,Object> params){
        return wfInsTaskMapper.selectRoleIdPrevious(params);
    }
    @Override
    public Map selectUserIdStart(Map<String,Object> params){
        return wfInsTaskMapper.selectUserIdStart(params);
    }
    @Override
    public void updateInfo(Map<String,Object> params){
        wfInsTaskMapper.updateInfo(params);
    }
    @Override
    public void updateAllInfo(Map<String,Object> params){
        wfInsTaskMapper.updateAllInfo(params);
    }
}
