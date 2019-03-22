package org.ibase4j.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.ibase4j.model.Lock;

public interface LockMapper extends BaseMapper<Lock> {

	void cleanExpiredLock();

}
