package org.ibase4j.provider;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.matrix.api.MFExecutionService;
import com.matrix.api.data.ReadWriteContainer;
import com.matrix.api.identity.MFPotentialOwner;
import com.matrix.api.instance.MFInstanceAssistantService;
import com.matrix.api.instance.activity.ActivityInstance;
import com.matrix.api.instance.assistant.CommentInfo;
import com.matrix.api.instance.process.ProcessInsStatus;
import com.matrix.api.instance.task.*;
import com.matrix.api.security.PotentialOwners;
import com.matrix.engine.proxy.MFExecutionServiceProxy;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.extend.WorkflowCacheUtil;
import org.ibase4j.extend.WorkflowConstant;

import org.ibase4j.mapper.WfInsTaskMapper;
import org.ibase4j.model.BizProStatement;
import org.ibase4j.model.SysCurrency;
import org.ibase4j.model.SysUser;
import org.ibase4j.vo.BizGrantTaskVo;
import org.ibase4j.vo.BizSchemeTaskVo;
import org.ibase4j.vo.ProStatementVo;
import org.ibase4j.vo.ProstatAuditVo;
import org.ibase4j.workflow.query.BizTaskBeanFactory;
import org.ibase4j.workflow.query.WorkFlowType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.*;

/**
 * @author mac19
 */
@CacheConfig(cacheNames = "BizProStatement")
@Service(interfaceClass = BizProStatementProvider.class)
public class BizProStaProviderImpl extends BaseProviderImpl<BizProStatement> implements BizProStatementProvider {


    @Autowired
    private WfInsTaskProvider wfInsTaskProvider;
    @Autowired
    private WFLogsProvider wfLogsProvider;
    @Autowired
    private BizCntProvider bizCntProvider;
    @Autowired
    private BizApprovalWorkflowTaskProvider bizApprovalWorkflowTaskProvider;

    @Autowired
    private WfInsTaskMapper wfInsTaskMapper;
    @Autowired
    private BizDebtSummaryProvider bizDebtSummaryProvider;
    @Autowired
    private BizDebtGrantProvider bizDebtGrantProvider;
    @Reference
    private  ISysUserProvider sysUserProvider;
    @Reference
    private ISysAuthorizeProvider sysAuthorizeProvider;
    @Reference
    private ISysCurrencyProvider sysCurrencyProvider;
    /**
     * @Description: 查询发放待办任务
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    @Override
    public Map<String,Page> getGrantToDoTask(Map<String, Object> params) {
      /*String date=params.get("date").toString();*/
        String userId =String.valueOf(params.get("userId")) ;
        // 获取工作流核心对象
        MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);
        if(execution==null){
            execution = signonNew(userId);
        }
        else {
            if(execution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",execution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",execution.isValidSession(),execution.getSessionId());
                execution = signonNew(userId);
            }
        }
        List bizFields= new ArrayList();
        QueryBizField bizField1=new QueryBizField("GRANT_CODE","grantCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField1);
        QueryBizField bizField2=new QueryBizField("DEBT_CODE","debtCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField2);
        QueryBizField bizField3=new QueryBizField("USER_NAME","userName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField3);
        QueryBizField bizField4=new QueryBizField("CURRENCY","currency",QueryBizField.STRING_TYPE);
        bizFields.add(bizField4);
        QueryBizField bizField11=new QueryBizField("CODE_NAME","codeName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField11);
        QueryBizField bizField5=new QueryBizField("PAYMENT_AMT","paymentAmt",QueryBizField.DOUBLE_TYPE);
        bizFields.add(bizField5);
        QueryBizField bizField6=new QueryBizField("SCOPE_BUSIN_PERIOD","scopeBusinPeriod",QueryBizField.STRING_TYPE);
        bizFields.add(bizField6);
        QueryBizField bizField7=new QueryBizField("NAME_","name_",QueryBizField.STRING_TYPE);
        bizFields.add(bizField7);

        //新建查询模型
        QueryBizTaskModel queryModel=new QueryBizTaskModel();
        //设置查询业务类
        queryModel.setBizCls("org.ibase4j.vo.BizGrantTaskVo");
        //设置业务表
        queryModel.setBizTableName("BIZ_V_PROJECT_NAME");

        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.GRANT_CODE");

        queryModel.setQueryBizFields(bizFields);
        // 构造查询条件，状态是就绪的任务
        StringBuffer beforeFilter = new StringBuffer();
        beforeFilter.append("(STATUS=");
        beforeFilter.append(TaskStatus._READY);
        beforeFilter.append(" OR (STATUS=");
        beforeFilter.append(TaskStatus._CLAIMED);
        beforeFilter.append(" AND USER_ID='");
        beforeFilter.append(userId);
        beforeFilter.append("'))");
        /*beforeFilter.append("' AND t.RECEIVED_DATE>");
        beforeFilter.append("TO_DATE('"+date+"','yyyy-MM-dd HH24:mi:ss')");
        beforeFilter.append("))");*/
        String beforeFileterString = beforeFilter.toString();

        // 构造排序条件
        String order = " ORDER BY RECEIVED_DATE DESC ";

        // 分页参数
        int start = 1;// 返回记录起始行数
        int count = 10;// 每页返回得行数

        Map<String,Object> data = (Map)params.get("data");
        Integer pageNumber = (Integer) data.get("pageNumber");
        start = (pageNumber-1)*10 +1;
        // 声明返回数据集合
        Map<String,Page> dataMap = new HashMap<>();
        //查询待办任务
        com.matrix.api.util.Page page = execution.queryBizTasks(queryModel,beforeFileterString, order, start, count);
        List dataList = page.getDataList();
        if(dataList != null && dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                BizGrantTaskVo bizGrantTaskVo = (BizGrantTaskVo)dataList.get(i);
                String piid = bizGrantTaskVo.getPiid();
                Map param1=new HashMap();
                param1.put("piid",piid);
                Map map = wfInsTaskProvider.selectRoleId(param1);
                Map map1= wfInsTaskProvider.selectRoleIdPrevious(param1);
                Map map2= wfInsTaskProvider.selectUserIdStart(param1);
                logger.debug("=============BizProStaProviderImpl============map=====map={}",map);
                logger.debug("=============BizProStaProviderImpl============map1=====map1={}",map1);
                logger.debug("=============BizProStaProviderImpl============map2=====map2={}",map2);
                if(map !=null && map1 !=null && map2 !=null) {
                    String userName = map2.get("USER_NAME").toString();
                    String roleIdNow = map.get("ACTIVITY_NAME").toString();
                    String roleIdProvider = map1.get("ACTIVITY_NAME").toString();
                    bizGrantTaskVo.setRoleIdNow(roleIdNow);
                    bizGrantTaskVo.setRoleIdProvider(roleIdProvider);
                    bizGrantTaskVo.setUserNameStart(userName);

                }

            }
        }
        Page toGrantDoTaskPage = convertToMyBatisPage(page, count);
        toGrantDoTaskPage.setCurrent(pageNumber);
        dataMap.put("toGrantDoTaskPage",toGrantDoTaskPage);
        return dataMap;
    }

    /**
     * @Description: 查询方案待办任务
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    @Override
    public Map<String,Page> getSchemeToDoTask(Map<String, Object> params) {

        String userId =String.valueOf(params.get("userId")) ;

        // 获取工作流核心对象
        MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);
        if(execution==null){
            execution = signonNew(userId);
        }else {
            if(execution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",execution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",execution.isValidSession(),execution.getSessionId());
                execution = signonNew(userId);
            }
        }
        List bizFields= new ArrayList();
        QueryBizField bizField=new QueryBizField("PROJECT_NAME","projectName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField);
        QueryBizField bizField2=new QueryBizField("DEBT_CODE","debtCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField2);
        QueryBizField bizField8=new QueryBizField("M_CURRENCY","mainCurrency",QueryBizField.STRING_TYPE);
        bizFields.add(bizField8);
        QueryBizField bizField9=new QueryBizField("PROPOSER","proposer",QueryBizField.STRING_TYPE);
        bizFields.add(bizField9);
        QueryBizField bizField10=new QueryBizField("SOLUTION_AMT","solutionAmt",QueryBizField.DOUBLE_TYPE);
        bizFields.add(bizField10);
        bizFields.add(new QueryBizField("VERNUM_","verNum",QueryBizField.INT_TYPE));
        //新建查询模型
        QueryBizTaskModel queryModel=new QueryBizTaskModel();
        //设置查询业务类
        queryModel.setBizCls("org.ibase4j.vo.BizSchemeTaskVo");
        //设置业务表
        queryModel.setBizTableName("BIZ_APPRSUMMARY_INFO");

//        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.DEBT_CODE");
        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.DEBT_CODE || replace(lpad(to_char(bo.vernum_),3),' ','0')");

        queryModel.setQueryBizFields(bizFields);

        // 构造查询条件，状态是就绪的任务
        StringBuffer beforeFilter = new StringBuffer();
        beforeFilter.append("(STATUS=");
        beforeFilter.append(TaskStatus._READY);
        beforeFilter.append(" OR (STATUS=");
        beforeFilter.append(TaskStatus._CLAIMED);
        beforeFilter.append(" AND USER_ID='");
        beforeFilter.append(userId);
        beforeFilter.append("'))");
        String beforeFileterString = beforeFilter.toString();

        // 构造排序条件
        String order = " ORDER BY RECEIVED_DATE DESC ";

        // 分页参数
        int start = 1;// 返回记录起始行数
        int count = 10;// 每页返回得行数


        // 声明返回数据集合
        Map<String,Page> dataMap = new HashMap<>();
        //查询待办任务
        Map<String,Object> data = (Map)params.get("data");
        Integer pageNumber = (Integer) data.get("pageNumber");
        start = (pageNumber-1)*10 +1;
        com.matrix.api.util.Page page = execution.queryBizTasks(queryModel,beforeFileterString, order, start, count);

        //插入数据
        List dataList = page.getDataList();
        if(dataList != null && dataList.size()>0){
            for (int i = 0; i < dataList.size(); i++) {
                BizSchemeTaskVo bizSchemeTaskVo = (BizSchemeTaskVo)dataList.get(i);
                String piid = bizSchemeTaskVo.getPiid();
                Map param1=new HashMap();
                param1.put("piid",piid);
                Map map = wfInsTaskProvider.selectRoleId(param1);
                Map map1 = wfInsTaskProvider.selectRoleIdPrevious(param1);
                Map map2= wfInsTaskProvider.selectUserIdStart(param1);
                //查询主币种 name
                Map mapMpc = new HashMap();
                mapMpc.put("monCode",bizSchemeTaskVo.getMainCurrency());
                List<SysCurrency> sysCurrency = sysCurrencyProvider.queryList(mapMpc);
                if(sysCurrency!=null&&sysCurrency.size()>0){
                    SysCurrency sysCurrency1 = sysCurrency.get(0);
                    String codeName = sysCurrency1.getCodeName();
                    logger.debug("=============BizProStaProviderImpl============MainCodeName=====MainCodeName={}",codeName);

                    //设置主币种
                    bizSchemeTaskVo.setMainCodeName(codeName);
                }
                logger.debug("=============BizProStaProviderImpl============map=====map={}",map);
                logger.debug("=============BizProStaProviderImpl============map1=====map1={}",map1);
                logger.debug("=============BizProStaProviderImpl============map2=====map2={}",map2);
                if(map !=null && map1 !=null && map2 !=null) {
                    String userName = map2.get("USER_NAME").toString();
                    String roleIdNow = map.get("ACTIVITY_NAME").toString();
                    String roleIdProvider = map1.get("ACTIVITY_NAME").toString();
                    bizSchemeTaskVo.setRoleIdNow(roleIdNow);
                    bizSchemeTaskVo.setRoleIdProvider(roleIdProvider);
                    bizSchemeTaskVo.setUserNameStart(userName);
                }
                //待办中的debtcode添加版本号
                bizSchemeTaskVo.setDebtCode(bizSchemeTaskVo.getDebtCode()+bizSchemeTaskVo.getVerNumStr());
                //处理已办币种不显示
                bizSchemeTaskVo.setMainCodeName(bizSchemeTaskVo.getMainCurrency());
            }
        }
        Page toSchemeDoTaskPage = convertToMyBatisPage(page, count);
        toSchemeDoTaskPage.setCurrent(pageNumber);
        dataMap.put("toSchemeDoTaskPage",toSchemeDoTaskPage);
        return dataMap;
    }
    /**
     * @Description: 查询发放已办任务
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    @Override
    public Map<String,Page> getGrantHaveDoneTask(Map<String, Object> params) {
        /*String date=params.get("date").toString();*/
        String userId =String.valueOf(params.get("userId")) ;
        // 获取工作流核心对象
        MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);
        if(execution==null){
            execution = signonNew(userId);
        }else {
            if(execution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",execution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",execution.isValidSession(),execution.getSessionId());
                execution = signonNew(userId);
            }
        }
        List bizFields= new ArrayList();
        QueryBizField bizField1=new QueryBizField("GRANT_CODE","grantCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField1);
        QueryBizField bizField2=new QueryBizField("DEBT_CODE","debtCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField2);
        QueryBizField bizField3=new QueryBizField("USER_NAME","userName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField3);
        QueryBizField bizField4=new QueryBizField("CURRENCY","currency",QueryBizField.STRING_TYPE);
        bizFields.add(bizField4);
        QueryBizField bizField11=new QueryBizField("CODE_NAME","codeName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField11);
        QueryBizField bizField5=new QueryBizField("PAYMENT_AMT","paymentAmt",QueryBizField.DOUBLE_TYPE);
        bizFields.add(bizField5);
        QueryBizField bizField6=new QueryBizField("SCOPE_BUSIN_PERIOD","scopeBusinPeriod",QueryBizField.STRING_TYPE);
        bizFields.add(bizField6);
        QueryBizField bizField7=new QueryBizField("NAME_","name_",QueryBizField.STRING_TYPE);
        bizFields.add(bizField7);

        //新建查询模型
        QueryBizTaskModel queryModel=new QueryBizTaskModel();
        //设置查询业务类
        queryModel.setBizCls("org.ibase4j.vo.BizGrantTaskVo");
        //设置业务表
        queryModel.setBizTableName("BIZ_V_PROJECT_NAME");

        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.GRANT_CODE");

        queryModel.setQueryBizFields(bizFields);

        // 构造查询条件，状态是就绪的任务
        StringBuffer beforeFilter = new StringBuffer();
        beforeFilter.append(" STATUS =" + TaskStatus._COMPLETED);
        beforeFilter.append(" AND COMPLETE_TYPE =" + CompleteType.COMPLETED_TYPE);
        beforeFilter.append(" AND USER_ID='");
        beforeFilter.append(userId);
        beforeFilter.append("'");
        /*beforeFilter.append("' AND t.RECEIVED_DATE>");
        beforeFilter.append("TO_DATE('"+date+"','yyyy-MM-dd HH24:mi:ss')");
         */
        String beforeFileterString = beforeFilter.toString();

        // 构造排序条件
        String order = " ORDER BY RECEIVED_DATE DESC ";

        // 分页参数
        int start = 1;// 返回记录起始行数
        int count = 10;// 总记录数

        // 声明返回数据集合
        Map<String,Page> dataMap = new HashMap<>();

        Map<String,Object> data = (Map)params.get("data");
        Integer pageNumber = (Integer) data.get("pageNumber");
        start = (pageNumber-1)*10 +1;

        // 查询已办任务
        com.matrix.api.util.Page page = execution.queryBizTasks(queryModel,beforeFileterString, order, start, count);
        List dataList1 = page.getDataList();
        if(dataList1 != null && dataList1.size()>0){
            for (int i = 0; i < dataList1.size(); i++) {
                BizGrantTaskVo bizGrantTaskVo = (BizGrantTaskVo)dataList1.get(i);
                String piid = bizGrantTaskVo.getPiid();
                Map params1=new HashMap();
                params1.put("piid",piid);
                Map map = wfInsTaskProvider.selectRoleId(params1);
                Map map2= wfInsTaskProvider.selectUserIdStart(params1);
                logger.debug("=============BizProStaProviderImpl============map=====map={}",map);
                logger.debug("=============BizProStaProviderImpl============map2=====map2={}",map2);
                if(map==null){
                    bizGrantTaskVo.setRoleIdNow("已完成");
                }else{
                    String roleIdNow = map.get("ACTIVITY_NAME").toString();
                    bizGrantTaskVo.setRoleIdNow(roleIdNow);
                }
                if(map2 !=null){
                    String userName=map2.get("USER_NAME").toString();
                    bizGrantTaskVo.setUserNameStart(userName);
                }
            }
        }
        Page haveGrantDonePage = convertToMyBatisPage(page, count);
        haveGrantDonePage.setCurrent(pageNumber);
        dataMap.put("haveGrantDonePage",haveGrantDonePage);
        return dataMap;
    }
    /**
     * @Description: 查询方案已办任务
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    @Override
    public Map getSchemeHaveDoneTask(Map<String, Object> params) {
        /*String date=params.get("date").toString();*/
        String userId =String.valueOf(params.get("userId")) ;
        // 获取工作流核心对象
        MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);

        if(execution==null){
            execution = signonNew(userId);
        }else {
            if(execution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",execution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",execution.isValidSession(),execution.getSessionId());
                execution = signonNew(userId);

            }
        }
        List bizFields= new ArrayList();
        QueryBizField bizField=new QueryBizField("PROJECT_NAME","projectName",QueryBizField.STRING_TYPE);
        bizFields.add(bizField);
        QueryBizField bizField2=new QueryBizField("DEBT_CODE","debtCode",QueryBizField.STRING_TYPE);
        bizFields.add(bizField2);
        QueryBizField bizField8=new QueryBizField("M_CURRENCY","mainCurrency",QueryBizField.STRING_TYPE);
        bizFields.add(bizField8);
        QueryBizField bizField9=new QueryBizField("PROPOSER","proposer",QueryBizField.STRING_TYPE);
        bizFields.add(bizField9);
        QueryBizField bizField10=new QueryBizField("SOLUTION_AMT","solutionAmt",QueryBizField.DOUBLE_TYPE);
        bizFields.add(bizField10);
        bizFields.add(new QueryBizField("VERNUM_","verNum",QueryBizField.INT_TYPE));
        //新建查询模型
        QueryBizTaskModel queryModel=new QueryBizTaskModel();
        //设置查询业务类
        queryModel.setBizCls("org.ibase4j.vo.BizSchemeTaskVo");
        //设置业务表
        queryModel.setBizTableName("BIZ_APPRSUMMARY_INFO");

//        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.DEBT_CODE");
        queryModel.setAssosiationFilter("t.EXATTRIBUTED=bo.DEBT_CODE || replace(lpad(to_char(bo.vernum_),3),' ','0')");
        queryModel.setQueryBizFields(bizFields);

        // 构造查询条件，状态是就绪的任务
        StringBuffer beforeFilter = new StringBuffer();
        beforeFilter.append(" STATUS =" + TaskStatus._COMPLETED);
        beforeFilter.append(" AND COMPLETE_TYPE =" + CompleteType.COMPLETED_TYPE);
        beforeFilter.append(" AND USER_ID='");
        beforeFilter.append(userId);
        beforeFilter.append("'");
        /*beforeFilter.append("' AND t.RECEIVED_DATE>");
        beforeFilter.append("TO_DATE('"+date+"','yyyy-MM-dd HH24:mi:ss')");*/
        String beforeFileterString = beforeFilter.toString();

        // 构造排序条件
        String order = " ORDER BY RECEIVED_DATE DESC ";

        // 分页参数
        int start = 1;// 返回记录起始行数
        int count = 10;// 每页返回得行数


        // 声明返回数据集合
        Map<String,Page> dataMap = new HashMap<>();

        Map<String,Object> data = (Map)params.get("data");
        Integer pageNumber = (Integer) data.get("pageNumber");
        start = (pageNumber-1)*10 +1;
        //查询已办任务
        com.matrix.api.util.Page page = execution.queryBizTasks(queryModel,beforeFileterString, order, start, count);
        List dataList1 = page.getDataList();
        if(dataList1 != null && dataList1.size()>0){
            for (int i = 0; i < dataList1.size(); i++) {
                BizSchemeTaskVo bizSchemeTaskVo = (BizSchemeTaskVo)dataList1.get(i);
                String piid = bizSchemeTaskVo.getPiid();
                Map params1=new HashMap();
                params1.put("piid",piid);
                //查询主币种 name
                Map mapMpc = new HashMap();
                mapMpc.put("monCode",bizSchemeTaskVo.getMainCurrency());
                List<SysCurrency> sysCurrency = sysCurrencyProvider.queryList(mapMpc);
                if(sysCurrency!=null&&sysCurrency.size()>0){
                    SysCurrency sysCurrency1 = sysCurrency.get(0);
                    String codeName = sysCurrency1.getCodeName();
                    logger.debug("=============BizProStaProviderImpl============MainCodeName=====MainCodeName={}",codeName);

                    //设置主币种
                    bizSchemeTaskVo.setMainCodeName(codeName);
                }
                Map map = wfInsTaskProvider.selectRoleId(params1);
                Map map2= wfInsTaskProvider.selectUserIdStart(params1);
                logger.debug("=============BizProStaProviderImpl============map=====map={}",map);
                logger.debug("=============BizProStaProviderImpl============map2=====map2={}",map2);
                if(map==null){
                    bizSchemeTaskVo.setRoleIdNow("已完成");
                }else{
                    String roleIdNow = map.get("ACTIVITY_NAME").toString();
                    bizSchemeTaskVo.setRoleIdNow(roleIdNow);
                }
                if(map2 !=null){
                    String userName=map2.get("USER_NAME").toString();
                    bizSchemeTaskVo.setUserNameStart(userName);
                }
                //待办中的debtcode添加版本号
                bizSchemeTaskVo.setDebtCode(bizSchemeTaskVo.getDebtCode()+bizSchemeTaskVo.getVerNumStr());
                //处理已办币种不显示
                bizSchemeTaskVo.setMainCodeName(bizSchemeTaskVo.getMainCurrency());
            }
        }
        Page haveSchemeDonePage = convertToMyBatisPage(page, count);
        haveSchemeDonePage.setCurrent(pageNumber);
        dataMap.put("haveSchemeDonePage",haveSchemeDonePage);
        return dataMap;
    }
    /**
     * @Description: 获得待选步骤
     * @Param: [piid, ptid, adid, userId]
     * @return: java.util.List
     */
    @Override
    public List getAvailableTransitions(String piid, String ptid, String adid, String userId) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        if(executionService==null){
            executionService = signonNew(userId);
        } else {
            if(executionService.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",executionService.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",executionService.isValidSession(),executionService.getSessionId());
                executionService = signonNew(userId);
            }
        }
        List transitions = executionService.getAvailableTransitionsOfAct(piid,ptid,adid,null);
        return transitions;
    }

    /**
    * @Description: 根据选择的流转方向完成任务
    * @Param: [params]
    * @return: void
    */
    @Override
    public void completeTaskByTdid(Map<String, Object> params) {
        // 用户id
        String userId =String.valueOf(params.get("userId"));
        // 任务id
        String taskId =String.valueOf(params.get("taskId"));
        // 流程实例编码
        String piid =String.valueOf(params.get("piid"));
        // 流程模板编码
        String ptid =String.valueOf(params.get("ptid"));
        // 活动定义编码
        String adid =String.valueOf(params.get("adid"));
        // 流程流转编码
        String tdid =String.valueOf(params.get("tdid"));
        // 核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        } else {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }
        // 声明任务
        excution.claimTask(taskId);
        // 获得待选人员
        List potentialOwners = excution.getPotentialOwnersOfAct(piid, ptid, adid, null);
        PotentialOwners owners = new PotentialOwners();
        List ownersList = new ArrayList();
        for(Iterator<MFPotentialOwner> iterable = (Iterator<MFPotentialOwner>) potentialOwners.iterator(); iterable.hasNext();)
        {
            MFPotentialOwner potentialOwner=iterable.next();
            ownersList.add(potentialOwner.getId());
        }
        owners.setUsers(ownersList);
        //  4.完成审核到下一个节点
        excution.completeTask(taskId,tdid,owners);

    }

    /**
    * @Description: 根据任务id完成任务
     * @Param: [userId, taskId]
    * @return: void
    */
    @Override
    public void completeTaskByTaskId(String userId,String taskId){
        logger.debug("审批过程流转根据任务id完成任务=================userId={},taskId={}",userId,taskId);
        // 获取核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        } else {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }

        // 声明任务
        excution.claimTask(taskId);
        // 完成任务
        excution.completeTask(taskId);
    }

    /**
    * @Description: 获得前手taskid
    * @Param: [params]
    * @return: java.lang.String
    */
    @Override
    public String getBeforeTaskId(Map<String, Object> params){
        String userId =String.valueOf(params.get("userId"));
        // 活动实例id
        String aiid =String.valueOf(params.get("aiid"));
        // 前手任务人id
        String starterId =String.valueOf(params.get("starterId"));
        // 获取核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        }else {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }
        // 根据活动实例id获取活动实例

        ActivityInstance activityIns = excution.getActivityInsById(aiid);
        // 根据活动实例获取前手活动实例id
        String beforeActInsId = activityIns.getFromActInsId();
        // 根据前手活动实例id获得该实例id产生的task集合
        List<Task> tasks = excution.getTasksByActInsId(beforeActInsId);
        // 根据前手任务人id获得前手taskid
        String taskId = "";
        for (Task task :tasks){
            String owner = task.getOwner();
            if(starterId.equals(owner)){
                taskId = task.getTaskId();
            }
        }

        return taskId;
    }

    /**
    * @Description: 发起流程 创建流程实例
    * @Param: [params]
    * @return: void
    */
    @Override
    public void createAndstartProcess(Map<String, Object> params) {
        logger.debug("发起流程 创建流程实例params:"+params.toString());
        String userId =String.valueOf(params.get("userId"));
        logger.debug("---------------"+userId);
        // 流程定义id
        String pdid =String.valueOf(params.get("pdid"));
        // 业务编码
        String mBizId =String.valueOf(params.get("mBizId"));
        // 获取核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        } else {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }
        ReadWriteContainer input = excution.getProcessInContainer(pdid);
        // 设置流程变量mBizId为业务编码
        input.setString("mBizId",mBizId);
        // 创建并启动流程
        String piid = excution.createAndStartProcessInstance(pdid, input);
        //流程实例id
        params.put("piid",piid);
        //根据piid查询 WF_INS_TASK表 WfInsTask
        List<Map> tasks = wfInsTaskMapper.selectWfInsTaskListByPiid(params);
        //把经办的 taskId 存在 params中
        logger.info("根据piid查询 WF_INS_TASK表 WfInsTask:"+tasks.toString());
        String  taskId = null;
        if(tasks!=null&&tasks.size()>0){
            taskId = (String) tasks.get(0).get("TASK_IID");
        }
        params.put("taskId",taskId);
        //保存工作流 历史流程记录  提交 的过程
        params.put("commentInfo","提交");
        //这个表的 备注 字段 被占用，用来区分是 经办提交的，前端页面的详情需要依据此字段 来调用不同方法。
        params.put("remark","1");
        // 生成流程审核ID  debtCode 不能为空 实际存debtCode  grantCode 都无所谓
        String debtCode =  StringUtil.objectToString(params.get("debtCode"));
        String approvalId = bizCntProvider.getNextNumberFormat("APPR"+debtCode,3);
        params.put("approvalId",approvalId);
        bizApprovalWorkflowTaskProvider.saveApprovalWorkflowTask(params);
    }

    /**
     * @Description: 获取业务编码
     * @Param: [userId, piid]
     * @return: java.lang.String
     */
    @Override
    public String getProcessInsBizId(String userId,String piid){
        // 获取核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        }else {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }
        // 获取业务编码
        Object mBizId = excution.getProcessInsVariable(piid, "mBizId");
        return mBizId.toString();
    }

    /**
     * 根据taskId查询task相关数据
     * @param userId
     * @param taskId
     * @return
     */
    @Override
    public Task getTaskByTaskId(String userId,String taskId) {
        // 获取核心对象
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        if(excution==null){
            excution = signonNew(userId);
        }else
        {
            if(excution.isValidSession())
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====sessionid={}",excution.getSessionId());
            }else
            {
                logger.debug("=============BizProStaProviderImpl============getTaskByTaskId=====isValidSession={},sessionid={}",excution.isValidSession(),excution.getSessionId());
                excution = signonNew(userId);
            }
        }

        Task task = excution.getTaskById(taskId);
        return task;
    }

    /**
     * 后台重新登录
     * @param userId
     * @return
     */
    @Override
    public MFExecutionServiceProxy signonNew(String userId){
        if(logger.isDebugEnabled())
        {
            logger.debug("=============BizProStaProviderImpl============signonNew=====userId={}",userId);
        } else {
            logger.debug("=============BizProStaProviderImpl============signonNew=====userId={}",userId);

        }
        SysUser sysUser =new SysUser();
        sysUser.setId(Long.valueOf(userId));
        SysUser sysUser1 =sysUserProvider.selectOne(sysUser);
        String deptCode = sysUser1.getDeptCode();
        List deptCodes =new ArrayList();
        deptCodes.add(deptCode);
        if(logger.isDebugEnabled())
        {
            logger.debug("=============BizProStaProviderImpl============deptCodes=====deptCodes={}",deptCodes);
        } else{
            logger.debug("=============BizProStaProviderImpl============deptCodes=====deptCodes={}",deptCodes);
        }
        List<String> totalDeptCodes = sysAuthorizeProvider.queryParentDeptIdByDeptCode(deptCode);
        List<String> mfRoles = sysAuthorizeProvider.queryRoleIdsByUserId(Long.valueOf(userId));
        MFExecutionServiceProxy executionService = wfLogsProvider.signonNew(userId, deptCodes, totalDeptCodes, mfRoles);
        return executionService;
    }
    /* -----------------------------------以下代码暂无用start--------------------------------------------------*/

    /**
     * @Description: 根据选择的流转方向完成任务
     * @Param: [prostat]
     * @return: java.lang.String
     */
    @Override
    public String auditProSta(ProstatAuditVo prostat) {

        String userId = prostat.getUserId();
        String taskId = prostat.getTaskId();
        // 流程实例编码
        String piid = prostat.getPiid();
        // 流程模板编码
        String ptid = prostat.getPtid();
        // 活动定义编码
        String adid = prostat.getAdid();
        // 流程流转编码
        String tdid = prostat.getTdid();
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(userId);
        // 获取流程对应的实例变量
        MFInstanceAssistantService assistantService = excution.getInstanceAssistantService();
        // 添加审核意见信息
        prostat.setPiid(piid);
        CommentInfo info = BizTaskBeanFactory.getProstatCommentInfo(prostat);
        assistantService.createCommentInfo(info);
        // 声明任务
        excution.claimTask(taskId);

        // 获得待选人员
        List potentialOwners = excution.getPotentialOwnersOfAct(piid, ptid, adid, null);
        PotentialOwners owners = new PotentialOwners();
        List ownersList = new ArrayList();
        for(Iterator<MFPotentialOwner> iterable = (Iterator<MFPotentialOwner>) potentialOwners.iterator(); iterable.hasNext();)
        {
            MFPotentialOwner potentialOwner=iterable.next();
            ownersList.add(potentialOwner.getId());
        }
        owners.setUsers(ownersList);
        //  4.完成审核到下一个节点
        excution.completeTask(taskId,tdid,owners);
        return piid;
    }

    /**
    * @Description: 创建并启动流程实例
    * @Param: [proStatement]
    * @return: void
    */
    @Override
    public void updateProStatement(BizProStatement proStatement) {

        String pdid = proStatement.getPdid();
        MFExecutionService excution = WorkflowCacheUtil.getWfExecution(proStatement.getCreateBy().toString());

        // 获取流程对应的输入数据参数对象
        ReadWriteContainer input = excution.getProcessInContainer(pdid);
		// 根据参数对象进行流程参数对象初始化
        input.setString("mTitle", WorkflowConstant.WF_BIZ_FHZXSP_TITLE);
        input.setString("mBizId", "2222");
		// 调用接口创建并启动流程实例
        String piid = excution.createAndStartProcessInstance(pdid, input);
		//保存业务数据
        proStatement.setPiid(piid);
        proStatement.setActprocessname("2222");
        proStatement.setCreateddate(new Date());
        proStatement.setInsprocessname("3333");
        proStatement.setTitle(WorkflowConstant.WF_BIZ_FHZXSP_TITLE);
        update(proStatement);
    }

    /**
     * @Description: 查询待办任务的流程统计项列表
     * @Param: []
     * @return: java.util.List
     */
    @Override
    public List getTaskStatisticByProcess(String userId){
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        List statisticItems = new ArrayList();
        // 构造条件
        StringBuffer filter = new StringBuffer();
        filter.append(" ( STATUS=" + TaskStatus._READY );
        filter.append(" OR ( STATUS=" + TaskStatus._CLAIMED);
        filter.append(" AND OWNER ='" + userId + "') ");
        filter.append(" ) ");
        statisticItems= executionService.getTaskStatisticByProcess(filter.toString());
        return statisticItems;
    }

    /**
     * @Description: 根据流程定义编码查询待办任务
     * @Param: []
     * @return: com.baomidou.mybatisplus.plugins.Page<?>
     */
    @Override
    public Page<?> getWaitItemsByPdid(Map<String, Object> params){

        String userId =String.valueOf(params.get("userId")) ;
        String pdid =String.valueOf(params.get("pdid")) ;
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        StringBuffer filter = new StringBuffer();
        filter.append(" (( STATUS=" + TaskStatus._READY );
        filter.append(" OR (STATUS=" + TaskStatus._CLAIMED);
        filter.append(" AND OWNER ='" + userId + "') ");
        filter.append(" ) AND PROCESS_DEF_ID='");
        filter.append(pdid);
        filter.append("') ");
        String order = " ORDER BY RECEIVED_DATE DESC";

        // 查询待办
        int start = 0;// 返回记录起始行数
        int count = 10;// 总记录数
        // 查询业务字段
        QueryBizTaskModel queryModel = BizTaskBeanFactory.createBean(WorkFlowType.PROSTA);
        // 查询工作流中的待办任务
        com.matrix.api.util.Page wfPage = executionService.queryBizTasks(queryModel, filter.toString(), order, start, count);
        // 转化成mybatis的分页
        Page<?> page = new Page<ProStatementVo>(wfPage.getCurrentPageNumber(), count);
        page.setCurrent(wfPage.getCurrentPageNumber());
        page.setRecords(wfPage.getDataList());
        page.setSize(count);
        page.setTotal((int) wfPage.getTotalSize());
        return page;
    }

    /**
     * @Description: 查询已办任务
     * @Param: [params]
     * @return: com.baomidou.mybatisplus.plugins.Page<?>
     */
    @Override
    public Page<?> queryFinished(Map<String, Object> params) {

        String userId =String.valueOf(params.get("userId")) ;
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);

        //构造查询条件，状态为完成
        StringBuffer queryFilter = new StringBuffer();
        queryFilter.append(" ( STATUS =" + TaskStatus._COMPLETED);
        queryFilter.append("AND COMPLETE_TYPE =" + CompleteType.COMPLETED_TYPE);
        queryFilter.append(" ) ");
        //构造排序条件
        String orderFilter = " ORDER BY PRIORITY DESC,TASK_IID DESC ";
        //设置返回记录中的起始行数，默认为1;
        int start = 1;
        //设置显示记录总数
        int count = 10;
        //查询已办任务列表，返回已办任务分页对象page
        com.matrix.api.util.Page wfPage = executionService.queryTasks(queryFilter.toString(),orderFilter,start,count);
        //  4、执行工作任务 流程相关的参与人员查询出自己的待办任务后需要执行某些待办工作任务。执
        //  注:CompleteType完成类型共分为:
        //  已完成的任务(CompleteType.COMPLETED_TYPE)、
        //  已转交的任务(CompleteType.TRANSFERED_TYPE)、
        //  已委托的任务(CompleteType.SUBSTITUTED_TYPE)三种类型。

        // 转化成mybatis的分页
        Page<?> page = new Page<ProStatementVo>(wfPage.getCurrentPageNumber(), count);
        page.setCurrent(wfPage.getCurrentPageNumber());
        page.setRecords(wfPage.getDataList());
        page.setSize(count);
        page.setTotal((int) wfPage.getTotalSize());
        return page;
    }



    /**
    * @Description: 查询历史意见信息
    * @Param: [params]
    * @return: com.baomidou.mybatisplus.plugins.Page<?>
    */
    @Override
    public List getHistoryCommentInfos(Map<String, Object> params){
        String userId =String.valueOf(params.get("userId")) ;
        String piid =String.valueOf(params.get("piid")) ;
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 获得实例辅助对象
        MFInstanceAssistantService instanceAssistantService = executionService.getInstanceAssistantService();
        // 筛选条件
        StringBuffer filter = new StringBuffer();
        filter.append(" ( PIID='" + piid );
        filter.append("') ");
        // 排序方式
        String order = " ORDER BY CREATED_DATE DESC ";

        List list = instanceAssistantService.queryCommentInfos(filter.toString(), order);
        return list;
    }


    @Override
    public Page<?> queryAllGroupWait(Map<String, Object> params) {

        //  该种方式首先是将满足条件的任务项按流程分组，然后再以指定流程的定
        //  义编码作为条件来查询出该流程下的任务项，
        //  获得当前登录用户编码
        String userId =String.valueOf(params.get("userId")) ;
        String pdid =String.valueOf(params.get("pdid")) ;
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
//        String userId = executionService.getMFUser().getUserId();
//        List statisticItems = null;
        StringBuffer filter1 = new StringBuffer();
        filter1.append(" ( STATUS=" + TaskStatus._READY );
        filter1.append(" OR ( STATUS=" + TaskStatus._CLAIMED);
        filter1.append(" AND OWNER ='" + userId + "') ");
        filter1.append(" ) ");
        List statisticItems= executionService.getTaskStatisticByProcess(
               filter1.toString());
        //  参数说明:参数为查询任务的条件;
        //   查询结果为 com.matrix.api.instance.task.TaskStatisticItem 集 合，
        //  通过 TaskStatisticItem 的相关方法可以获得按流程统计任务项的信息
        //  如 getItemId()获得流程定义编码;getItemName()获得流程的名称;
        //  getItemNumber()获得该流程下满足条件的任务项个数。


//        流程定义编码作为条件查询任务项:
        //获得当前登录用户编码
//        String userId = executionService.getMFUser().getUserId();
        // 获得流程定义编码，通过流程统计项的 getItemId()方法可获得 String pdid = "testProcessDid";
        StringBuffer filter2 = new StringBuffer();
        filter2.append(" (( STATUS=" + TaskStatus._READY );
        filter2.append(" OR (STATUS=" + TaskStatus._CLAIMED);
        filter2.append(" AND OWNER ='" + userId + "') ");
        filter2.append(" ) AND PROCESS_DEF_ID='");
        filter2.append(pdid);
        filter2.append("') ");
        String order = " ORDER BY RECEIVED_DATE DESC";
        int start = 0;// 返回记录起始行数
        int count = 10;// 总记录数
        com.matrix.api.util.Page wfPage = executionService.queryTasks(filter2.toString(), order,start,count);

        // 转化成mybatis的分页
        Page<?> page = new Page<ProStatementVo>(wfPage.getCurrentPageNumber(), count);
        page.setCurrent(wfPage.getCurrentPageNumber());
        page.setRecords(wfPage.getDataList());
        page.setSize(count);
        page.setTotal((int) wfPage.getTotalSize());
        return page;
    }

    /**
     * @Description: 查询待办任务
     * @Param: [params]
     * @return: com.baomidou.mybatisplus.plugins.Page<?>
     */
    @Override
    public Page<?> queryWait(Map<String, Object> params) {

        String pdid = "";
        String userId =String.valueOf(params.get("userId")) ;
        MFExecutionService execution = WorkflowCacheUtil.getWfExecution(userId);
        // 构造查询条件，状态是就绪的任务
        StringBuffer queryFilter = new StringBuffer();
        queryFilter.append(" (STATUS=");
        queryFilter.append(TaskStatus._READY);
        queryFilter.append(" OR (STATUS=");
        queryFilter.append(TaskStatus._CLAIMED);
        queryFilter.append(" AND OWNER='");
        queryFilter.append(userId);
        queryFilter.append("')  )");
        // 构造排序条件
        String order = " ORDER BY PRIORITY,DESCRIPTION,TASK_IID DESC ";
        // 查询待办
        int start = 0;// 返回记录起始行数
        int count = 10;// 总记录数

        // 查询业务字段
        QueryBizTaskModel queryModel = BizTaskBeanFactory.createBean(WorkFlowType.PROSTA);
        // 查询工作流中的待办任务
        com.matrix.api.util.Page wfPage = execution.queryBizTasks(queryModel, queryFilter.toString(), order, start, count);
        // Page page = executionService.queryTasks(queryFilter.toString(),orderFilter,start,count);
        // page对象是分页对象，通过该对象的getDataList()方法可以获得列表中的数据，在此调用该方法可以获得Task对象列表数据，除此之外，还可以通过该对象 获得到其他的分页相关信息。

        // 转化成mybatis的分页
        Page<?> page = new Page<ProStatementVo>(wfPage.getCurrentPageNumber(), count);
        page.setCurrent(wfPage.getCurrentPageNumber());
        page.setRecords(wfPage.getDataList());
        page.setSize(count);
        page.setTotal((int) wfPage.getTotalSize());
        return page;
    }


    @Override
    public Task getByTaskId(String taskId, String bizId, String userId) {
        return null;
    }

    @Override
    public Task updateClaimTask(String taskId, String bizId, String userId) {
        return null;
    }

    @Override
    public List getPotentialOwners(String piid, String ptid, String adid, String userId) {
        //  设置流程定义编码
        String pdid = WorkflowConstant.WF_BIZ_FHZXSP;
        // 设置流程下一步执行活动定义编码,该编码是通过选择的流程流转对象
        // AvailableTransition的getToActDID()方法获得的
        //String adid = "ff808081_110971410230268";
        // 获得待选人员列表
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        //  List recipients = executionService.getPotentialOwnersOfStartProc(pdid,adid, null);
        //  MFPotentialOwner对象列表
        List transitions = executionService.getPotentialOwnersOfAct(piid,ptid,adid, null);
        JSONArray jsonArray=new JSONArray();
        for(Iterator<MFPotentialOwner> iterable = (Iterator<MFPotentialOwner>) transitions.iterator(); iterable.hasNext();)
        {
            MFPotentialOwner potentialOwner=iterable.next();
            logger.debug(JSON.toJSON(potentialOwner));
            JSONObject object=new JSONObject();
            object.put("id",potentialOwner.getId());
            object.put("name",potentialOwner.getName());
            jsonArray.add(object);
        }
       // return jsonArray.toJSONString();
        return transitions;
    }

    @Override
    public void queryProcessInses(String userId, boolean isAuthorized) {
        // 构造查询条件，如流程实例状态为运行中的
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        StringBuffer queryFilter = new StringBuffer();
        queryFilter.append(" ( STATUS=" + ProcessInsStatus._RUNNING);
        queryFilter.append(" ) ");
        // 构造排序条件
        String orderFilter = " ORDER BY STARTED_DATE DESC ";
        //是否约束查询当前用户有管理权限的实例，true为是，false为否
//        boolean isAuthorized = false;
        // 设置返回记录中的起始行数，默认为1;
        int start = 1;
        // 设置显示记录总数
        int count = 10;
        // 查询流程实例，返回所有具有管理权限的流程实例的分页对象page
        com.matrix.api.util.Page page = executionService.queryProcessInses(queryFilter.toString(),
                orderFilter,isAuthorized,start,count);
    }

    @Override
    public void suspendProcessIns(String userId, String piid, boolean isDeep) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 设置是否暂停子流程参数
//        boolean isDeep = true;
        // 调用暂停流程实例方法，暂停流程实例
        executionService.suspendProcessIns(piid,isDeep);
        // 参数说明:piid为暂停的流程实例编码;isDeep为是否暂停子流程标识
    }

    @Override
    public void resumeProcessIns(String userId, String piid, boolean isDeep) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 设置是否恢复子流程参数
//        boolean isDeep = true;
        // 调用恢复流程实例方法，恢复流程实例
        executionService.resumeProcessIns(piid,isDeep);
        // 参数说明:piid为要恢复的流程实例编码;isDeep为是否恢复子流程标识

    }

    @Override
    public void terminateProcessIns(String userId, String piid) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 调用终止流程实例方法，终止流程实例
        executionService.terminateProcessIns(piid);
        // 参数说明:piid为要终止的流程实例编码;

    }

    @Override
    public void withdrawTask(String userId, String taskId) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 调用撤销工作任务的方法，撤销工作任务
         executionService.withdrawTask(taskId);
        // 参数说明:taskId为要撤销的工作任务编码;
    }

    @Override
    public void goBackTask(String userId, String taskId) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 调用回退工作任务的方法，回退工作任务至上一流程环节
         executionService.gobackTask(taskId);
        // 参数说明:taskId 为要回退的工作任务项编码;

    }

    @Override
    public void gotoFlowTask(String userId, String taskId, String followingAdid, String atid) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 设置下一执行流程环节模板编码
        //  String atid = "ff808081_110971425369074";
        //  完成任务
        //  PotentialOwners owners= new PotentialOwners();
        //  该对象包含了执行人员信息，我们通过该对象的相关方法可以维护执行人员信息，
        //  如 adduser()添加一个执行人员、setUsers()添加多个执行人员、
        //  getUsers 获得执行 人员编码集合等等。设置选择人员代码示例如下:
        //  声明 PotentialOwners 对象
        PotentialOwners owners = new PotentialOwners();
        // 添加执行人员，其中 userId 为我们选择的执行人员编码,userIds 为执行人员编码 list 集合
        owners.addUser(userId);
//        owners.setUsers(userIds);
         executionService.completeTask2(taskId,followingAdid,owners);
        // 参数说明:taskId:工作任务编码;
        //  followingAdid: 指定后续目标活动定义编码，通常用于任意跳转;
        // owners: 后续环节潜在执行人员;
    }

    @Override
    public void releaseTask(String userId, String taskId) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 从session中获得流程服务对象
//        MFExecutionService executionService = (MFExecutionService)
//                session.getAttribute("executionService"); //释放已声明执行的工作任务
        executionService.releaseTask(taskId);
    }

    @Override
    public void claimTask(String userId, String taskId) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 通过任务编码声明执行任务
        executionService.claimTask(taskId);
    }

    @Override
    public void updateProcessInsVariable(String taskId, String userId, String piid, String ptid, String name, Object value) {
        MFExecutionService executionService = WorkflowCacheUtil.getWfExecution(userId);
        // 调用修改流程变量方法
        executionService.updateProcessInsVariable(piid,ptid,
                name,value);
        //  参数说明:piid 为流程实例编码;
        // ptid 为流程模板编码;
        // variableName 为 流程变量名称;
        // variableValue 是为流程变量设置的值;
        executionService.completeTask (taskId);
        //  参数说明:taskId 为工作任务编码。
    }

    /**
    * @Description: 将工作流查询出来的分页对象转换为mybatis分页对象
    * @Param: [matrixPage, count]
    * @return: com.baomidou.mybatisplus.plugins.Page
    */
    private Page convertToMyBatisPage(com.matrix.api.util.Page matrixPage,int count){
        Page<?> page = new Page<ProStatementVo>(matrixPage.getCurrentPageNumber(), count);
        page.setCurrent(matrixPage.getCurrentPageNumber());
        page.setRecords(matrixPage.getDataList());
        page.setSize(count);
        page.setTotal((int) matrixPage.getTotalSize());
        return page;
    }
}
