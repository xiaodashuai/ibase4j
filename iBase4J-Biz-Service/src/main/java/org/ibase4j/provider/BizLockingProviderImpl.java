package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.model.BizLocking;
import org.ibase4j.model.SysRole;
import org.ibase4j.model.SysUser;
import org.ibase4j.model.SysUserRole;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;
@CacheConfig(cacheNames = "bizLocking")
@Service(interfaceClass = BizLockingProvider.class)
public class BizLockingProviderImpl extends BaseProviderImpl<BizLocking> implements BizLockingProvider{

    @Override
    public BizLocking submitUserLog(Map<String, Object> params) {
        //查询用户名、角色名、角色id、用户id
        Long userId=Long.valueOf(params.get("userId").toString());
        SysUser sysUser =(SysUser)params.get("sysUser");
        String userName=sysUser.getUserName();
        //业务编号
        String code= params.get("code").toString();
        //查询数据库中是否有这条数据
        BizLocking bizLocking1=new BizLocking();
        bizLocking1.setCode(code);
        BizLocking bizLocking2 = selectOne(bizLocking1);
        //如果没有数据新插一条数据
        if(bizLocking2==null){

        String debtCode =params.get("debtCode").toString();

        //创建时间
        Date date=new Date();

        //失效时间
        Date date1 = DateUtil.addDate(date, 1, 1);


        BizLocking bizLocking=new BizLocking();

        bizLocking.setCode(code);
        bizLocking.setDebtCode(debtCode);
        bizLocking.setUserId(userId);
        bizLocking.setUserName(userName);
        bizLocking.setLockingDate(date);
        bizLocking.setPgExpiDate(date1);
        bizLocking.setStatus(1);
        update(bizLocking);
        }else{
            //如果有数据进行状态判断
            Long id = bizLocking2.getId();
            Integer status = bizLocking2.getStatus();
            //创建时间
            Date date2=new Date();
            long time = date2.getTime();
            long time1 = bizLocking2.getLockingDate().getTime();

            //状态为1表示表被锁定无法进行操作，状态为0表示可以进行操作
            if(status==1 && time-time1<=86400000){
                return bizLocking2;
            }else {
                BizLocking bizLocking3=new BizLocking();
                //创建时间
                Date date=new Date();
                //失效时间
                Date date1 = DateUtil.addDate(date, 1, 1);
                bizLocking3.setId(id);
                bizLocking3.setStatus(1);
                bizLocking3.setUserId(userId);
                bizLocking3.setUserName(userName);
                bizLocking3.setLockingDate(date);
                bizLocking3.setPgExpiDate(date1);
                update(bizLocking3);
            }
        }
        return null;

    }

    @Override
    public void unlockUser(Map<String, Object> params) {
        //业务编号
        String code= params.get("code").toString();
        BizLocking bizLocking1=new BizLocking();
        bizLocking1.setCode(code);
        BizLocking bizLocking2 = selectOne(bizLocking1);
        Long id = bizLocking2.getId();
        BizLocking bizLocking3=new BizLocking();
        bizLocking3.setId(id);
        bizLocking3.setStatus(0);
        update(bizLocking3);
    }
}
