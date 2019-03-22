package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysNotice;
import org.ibase4j.provider.ISysNoticeProvider;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 通知管理
 * @author ShenHuaJie
 */
@Service
public class SysNoticeService extends BaseService<ISysNoticeProvider, SysNotice> {
	@Reference
	public void setProvider(ISysNoticeProvider provider) {
		this.provider = provider;
	}

	@Override
	public void update(SysNotice record) {		
		if (record != null && record.getStatus()==null) {
            record.setCreateBy(SysWebUtil.getCurrentUser());
			record.setCreateTime(new Date());
			record.setStatus("0");
			record.setReaderTimes(0);
		} else if (record.getSendTime()== null) {
			record.setSendTime(new Date());
		}	
		super.update(record);
	}

	public Boolean queryByNoticeTitle(String account) {
		SysNotice sysNotice = provider.queryByNoticeTitle(account);
        return sysNotice != null;
    }
	
	
}
