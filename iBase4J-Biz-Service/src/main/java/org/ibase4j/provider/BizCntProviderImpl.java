package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.support.generator.Sequence;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.ThreadUtil;
import org.ibase4j.mapper.BizCntMapper;
import org.ibase4j.mapper.LockMapper;
import org.ibase4j.model.BizCnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = BizCntProvider.class)
public class BizCntProviderImpl extends BaseProviderImpl<BizCnt> implements BizCntProvider {
    @Autowired
    private BizCntMapper bizCntMapper;

    @Override
    public String getNextNumberFormat(String name, int format) {
        return getNextNumberFormat(name, format, 1);
    }

    @Override
    public int getNextNumber(String name) {
        return getNextNumber(name, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public int getNextNumber(String name, int times) {
        int number = 1;
        BizCnt cnt = null;
        String lockKey = getLockKey(name);
        String requestId = Sequence.next().toString();
        if (CacheUtil.getLock(lockKey,requestId)) {
            try {
                cnt = bizCntMapper.findByName(name);
                if (null == cnt) {
                    cnt = new BizCnt();
                    cnt.setName(name);
                    cnt.setStep(1);
                    cnt.setVal(2);
                    this.update(cnt);
                } else {
                    number = cnt.getVal();
                    cnt.setVal(number + cnt.getStep());
                    this.update(cnt);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CacheUtil.unLock(lockKey,requestId);
            }
        } else {
            if (times > 3) {
                logger.error("BizCntProviderImpl-getNextNumber未获取到锁。");
                throw new RuntimeException("BizCntProviderImpl-getNextNumber未获取到锁。");
            } else {
                logger.info(getClass().getSimpleName() + ":" + name + " retry getNextNumber.");
                ThreadUtil.sleep(1, 20);
                return getNextNumber(name, times + 1);
            }
        }
        return number;
    }

    @Transactional(rollbackFor = Exception.class)
    public int getNextSEQNO(String name, int times) {
        int number = 10000;
        BizCnt cnt = null;
        String lockKey = getLockKey(name);
        String requestId = Sequence.next().toString();
        if (CacheUtil.getLock(lockKey,requestId)) {
            try {
                cnt = bizCntMapper.findByName(name);
                if (null == cnt) {
                    cnt = new BizCnt();
                    cnt.setName(name);
                    cnt.setStep(1);
                    cnt.setVal(10001);
                    this.update(cnt);
                } else {
                    number = cnt.getVal();
                    cnt.setVal(number + cnt.getStep());
                    this.update(cnt);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CacheUtil.unLock(lockKey,requestId);
            }
        } else {
            if (times > 3) {
                logger.error("BizCntProviderImpl-getNextSEQNO未获取到锁。");
                throw new RuntimeException("BizCntProviderImpl-getNextSEQNO未获取到锁。");
            } else {
                logger.info(getClass().getSimpleName() + ":" + name + " retry getNextSEQNO.");
                ThreadUtil.sleep(1, 20);
                return getNextSEQNO(name, times + 1);
            }
        }
        return number;
    }

    @Override
    public int getNextSEQNO(String name) {
        return getNextSEQNO(name, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public String getNextNumberFormat(String name, int m, int times) {
        int number = 1;
        BizCnt cnt = null;
        String lockKey = getLockKey(name);
        String requestId = Sequence.next().toString();
        if (CacheUtil.getLock(lockKey,requestId)) {
            try {
                cnt = bizCntMapper.findByName(name);
                if (null == cnt) {
                    cnt = new BizCnt();
                    cnt.setName(name);
                    cnt.setStep(1);
                    cnt.setVal(2);
                    this.update(cnt);
                } else {
                    number = cnt.getVal();
                    cnt.setVal(number + cnt.getStep());
                    this.update(cnt);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                CacheUtil.unLock(lockKey,requestId);
            }
        } else {
            if (times > 3) {
                logger.error("BizCntProviderImpl-getNextNumberFormat未获取到锁。");
                throw new RuntimeException("BizCntProviderImpl-getNextNumberFormat未获取到锁。");
            } else {
                logger.info(getClass().getSimpleName() + ":" + name + " retry getNextNumberFormat.");
                ThreadUtil.sleep(1, 20);
                return getNextNumberFormat(name, times + 1);
            }
        }
        return m == 0 ? name + number + "" : name + String.format("%0" + m + "d", number);
    }

    @Override
    public int getNumber(String name){

        if(null != name){
            BizCnt cnt = bizCntMapper.findByName(name);
            if(null!=cnt){
                return cnt.getVal();
            }
        }
        return 0;
    }
}

