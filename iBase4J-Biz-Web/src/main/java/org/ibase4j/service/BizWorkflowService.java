package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizProStatement;
import org.ibase4j.provider.BizProStatementProvider;
import org.ibase4j.provider.WfInsTaskProvider;
import org.ibase4j.vo.ProstatAuditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-05-31
 */
@Service
public class BizWorkflowService extends BaseService<BizProStatementProvider,BizProStatement> {
    @Reference
    private WfInsTaskProvider wfInsTaskProvider;
    @Reference
    public void setBizProStatementProvider(BizProStatementProvider provider) {
        this.provider = provider;
    }

    /**
     * @Description: 查询发放待办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    public Map<String,Page> getGrantToDoTask(Map<String, Object> params) {
        /*String value=params.get("data").toString();
        Date date=new Date();
        if(value !=null && value !="") {
            if ("1".equals(value)) {
                // 起始日默认当前时间向前推一周
                date = DateUtil.addDate(new Date(), 3, -1);
            } else if ("2".equals(value)) {
                // 起始日默认当前时间向前推一个月
                date = DateUtil.addDate(new Date(), 2, -1);
            } else if ("3".equals(value)) {
                // 起始日默认当前时间向前推三个月
                date = DateUtil.addDate(new Date(), 2, -3);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);
            params.put("date", format);
        }else {
            params.put("date", "2017-01-01 00:00:00");
        }*/
        return provider.getGrantToDoTask(params);
    }

    /**
     * @Description: 查询方案待办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    public Map<String,Page> getSchemeToDoTask(Map<String, Object> params) {
            /*String value=params.get("data").toString();
            Date date=new Date();
            if(value !=null && value !="") {
                if ("1".equals(value)) {
                    // 起始日默认当前时间向前推一周
                    date = DateUtil.addDate(new Date(), 3, -1);
                } else if ("2".equals(value)) {
                    // 起始日默认当前时间向前推一个月
                    date = DateUtil.addDate(new Date(), 2, -1);
                } else if ("3".equals(value)) {
                    // 起始日默认当前时间向前推三个月
                    date = DateUtil.addDate(new Date(), 2, -3);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format(date);
                params.put("date", format);
            }else {
                params.put("date", "2017-01-01 00:00:00");
            }*/
        return provider.getSchemeToDoTask(params);
    }

    /**
     * @Description: 查询发放已办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    public Map<String,Page> getGrantHaveDoneTask(Map<String, Object> params) {
       /* String value=params.get("data").toString();
        Date date=new Date();
        if(value !=null && value !="") {
            if ("1".equals(value)) {
                // 起始日默认当前时间向前推一周
                date = DateUtil.addDate(new Date(), 3, -1);
            } else if ("2".equals(value)) {
                // 起始日默认当前时间向前推一个月
                date = DateUtil.addDate(new Date(), 2, -1);
            } else if ("3".equals(value)) {
                // 起始日默认当前时间向前推三个月
                date = DateUtil.addDate(new Date(), 2, -3);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);
            params.put("date", format);
        }else {
            params.put("date", "2017-01-01 00:00:00");
        }*/
        return provider.getGrantHaveDoneTask(params);
    }
    /**
     * @Description: 查询方案已办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    public Map<String,Page> getSchemeHaveDoneTask(Map<String, Object> params) {
        /*String value=params.get("data").toString();
        Date date=new Date();
        if(value !=null && value !="") {
            if ("1".equals(value)) {
                // 起始日默认当前时间向前推一周
                date = DateUtil.addDate(new Date(), 3, -1);
            } else if ("2".equals(value)) {
                // 起始日默认当前时间向前推一个月
                date = DateUtil.addDate(new Date(), 2, -1);
            } else if ("3".equals(value)) {
                // 起始日默认当前时间向前推三个月
                date = DateUtil.addDate(new Date(), 2, -3);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(date);
            params.put("date", format);
        }else {
            params.put("date", "2017-01-01 00:00:00");
        }*/
        return provider.getSchemeHaveDoneTask(params);
    }


    /**
     * @Description: 获得待选步骤
     * @Param: [piid, ptid, adid, userId]
     * @return: java.util.List
     */
    public List getAvailableTransitions(String piid, String ptid, String adid, String userId){
        return provider.getAvailableTransitions(piid,ptid,adid,userId);
    }

    /**
    * @Description:  根据选择的流转方向完成任务(无业务数据)
    * @Param: [params]
    * @return: void
    */
    public void completeTaskByTdid(Map<String, Object> params){
        provider.completeTaskByTdid(params);
    }

    /** 
    * @Description:  根据选择的流转方向完成任务(有业务数据)
    * @Param: [prostat] 
    * @return: java.lang.String 
    */ 
    public String auditProSta(ProstatAuditVo prostat){
        return provider.auditProSta(prostat);
    };

    /** 
    * @Description: 创建并启动流程实例
    * @Param: [userId, pdid] 
    * @return: void 
    */ 
    public void createAndStartProcess(Map<String, Object> params){
        provider.createAndstartProcess(params);
    }

    /**
     * 修改已办状态
     * @param params
     */
    public void updateInfo(Map<String, Object> params){
        wfInsTaskProvider.updateInfo(params);
    }

}
