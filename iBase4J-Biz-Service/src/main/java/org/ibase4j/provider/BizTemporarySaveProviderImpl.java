package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizTemporarySaveMapper;
import org.ibase4j.model.BizTemporarySave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CacheConfig(cacheNames = "BizTemporarySave")
@Service(interfaceClass = BizTemporarySaveProvider.class)
public class BizTemporarySaveProviderImpl extends BaseProviderImpl<BizTemporarySave> implements BizTemporarySaveProvider {
    @Autowired
    private BizTemporarySaveMapper bizTemporarySaveMapper;
    String basePath = PropertiesUtil.getString("tempsav.dir");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveGrantTemporary(Object t, Map params){
        Wrapper<BizTemporarySave> wrapper = new EntityWrapper<BizTemporarySave>();
        wrapper.eq("DEPT_CODE",StringUtil.objectToString(params.get("deptCode"))).eq("TASK_ID",StringUtil.objectToString(params.get("taskId")));
        params.put("wrapper",wrapper);
        return this.saveTemporary(t, params);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveTemporary(Object t, Map params) {
        boolean res = false;
        BizTemporarySave temp = null;
        BizTemporarySave selmod = null;
        BizTemporarySave sel = new BizTemporarySave();
        String cDateStr = DateUtil.getDateYYYYMMDD();
        Date cDate = new Date();

        if (null != params.get("id")){
            sel.setId((Long)params.get("id"));
        }
        if (null != params.get("bizcode")){
            sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
        }
        if (null != params.get("type")){
            sel.setType(StringUtil.objectToString(params.get("type")));
        }
        if (null != params.get("rolid")){
            sel.setRolid(StringUtil.objectToString(params.get("rolid")));
        }
        if (null != params.get("taskid")){
            sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
        }
        if (null != params.get("userid")){
            sel.setUserid(StringUtil.objectToString(params.get("userid")));
        }
        if (null != params.get("remark")){
            sel.setRemark(StringUtil.objectToString(params.get("remark")));
        }
        if (null != params.get("projectName")){
            sel.setProjectName(StringUtil.objectToString(params.get("projectName")));
        }
        if (null != params.get("deptCode")){
            sel.setDeptCode(StringUtil.objectToString(params.get("deptCode")));
        }

        if (null != params.get("wrapper")){
            //自定义查询条件，查询时若存在则优先使用（方案暂存时仅保留一条）
            Wrapper<BizTemporarySave> wrapper = (EntityWrapper<BizTemporarySave>)params.get("wrapper");
            selmod = this.selectOne(wrapper);
        }else{
            selmod = this.selectOne(sel);
        }

        if (null != selmod && ((t instanceof BizTemporarySave && "0".equals(selmod.getType()))
                || (!(t instanceof BizTemporarySave) && "1".equals(selmod.getType())))) {
            // 数据库中原暂存数据上更新
            if ("0".equals(selmod.getType())) {
                temp = (BizTemporarySave) t;
                if (null != temp.getObj()) {
                    selmod.setObj(temp.getObj());
                    selmod.setUpdateTime(cDate);
                    if (this.saveTmpObj(selmod, selmod.getFilename(),DateUtil.formatYYYYMMDD(selmod.getCreateTime()))) {
                        int i = bizTemporarySaveMapper.updateById(selmod);
                        if(i==1){
                            res = true;
                        }
                    } else {
                        throw new RuntimeException("BizTemporarySave:Save object error!");
                    }
                } else {
                    throw new RuntimeException("this BizTemporarySave.obj is null!");
                }
            } else {
                selmod.setObj(temp.getObj());
                selmod.setUpdateTime(cDate);
                if (this.saveTmpObj(t, selmod.getFilename(),DateUtil.formatYYYYMMDD(selmod.getCreateTime()))) {
                    int i = bizTemporarySaveMapper.updateById(selmod);
                    if(i==1){
                        res = true;
                    }
                } else {
                    throw new RuntimeException("BizTemporarySave:Save object error!");
                }
            }
        } else{
            String filename = IdWorker.getIdStr();
            if (t instanceof BizTemporarySave) {
                temp = (BizTemporarySave) t;
                if (null != temp.getObj()) {
                    sel.setId(Long.parseLong(filename));
                    sel.setType("0");
                    sel.setFilename(filename);
                    sel.setObj(temp.getObj());
                    if (this.saveTmpObj(sel, filename,cDateStr)) {
                        // selmod.setObj(null);
                        sel.setCreateTime(cDate);
                        sel.setUpdateTime(cDate);
                        int i = bizTemporarySaveMapper.insert(sel);
                        if(i==1){
                            res = true;
                        }
                    } else {
                        throw new RuntimeException("BizTemporarySave:Save object error!");
                    }
                } else {
                    throw new RuntimeException("this BizTemporarySave.obj is null!");
                }
            } else {
                sel.setId(Long.parseLong(filename));
                sel.setType("1");
                sel.setFilename(filename);
                if (this.saveTmpObj(t, filename,cDateStr)) {
                    sel.setCreateTime(cDate);
                    sel.setUpdateTime(cDate);
                    int i = bizTemporarySaveMapper.insert(sel);
                    if(i==1){
                        res = true;
                    }
                } else {
                    throw new RuntimeException("BizTemporarySave:Save pojo error!");
                }
            }
        }
		return res;
	}

	/**
	 * xiaoshuiquan
	 * @date 2018.11.1
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveSchemeTemporary(Object t, Map params) {

        Wrapper<BizTemporarySave> wrapper = new EntityWrapper<BizTemporarySave>();
        wrapper.eq("BIZ_CODE",StringUtil.objectToString(params.get("bizcode"))).ne("PROJECT_NAME","ReSubmit_debtMain").ne("PROJECT_NAME","Trn_debtMain");
        params.put("wrapper",wrapper);
		return this.saveTemporary(t, params);

	}

	private boolean saveTmpObj(Object obj, String filename,String cDateStr) {

        if (null == filename || null == obj){
            return false;
        }
        Output output = null;
        if(basePath.endsWith("/")){
            basePath = basePath.substring(0,basePath.length()-1);
        }
        String filePath = basePath +"/"+cDateStr+"/"+ filename + ".tss";
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()){
            fileParent.mkdirs();
        }
        try {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            kryo.setRegistrationRequired(false);
            kryo.register(obj.getClass());
            output = new Output(new FileOutputStream(file));
            kryo.writeClassAndObject(output,obj);
            output.flush();
            output.close();
            return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (output != null) {
                output.close();
            }
        }
		return false;
	}

    @Override
    public Object getTemporary(Map<String, Object> params){
	    return this.getTemporary(params,BizTemporarySave.class);
    }

	@Override
    public <T extends Serializable> Object getTemporary(Map<String, Object> params,Class<T> clazz) {

		BizTemporarySave sel = new BizTemporarySave();
        if (null != params.get("id")){
            sel.setId((Long)params.get("id"));
        }
		if (null != params.get("bizcode")){
            sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
        }
		if (null != params.get("rolid")){
            sel.setRolid(StringUtil.objectToString(params.get("rolid")));
        }
		if (null != params.get("taskid")){
            sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
        }
		if (null != params.get("userid")){
            sel.setUserid(StringUtil.objectToString(params.get("userid")));
        }
		if (null != params.get("remark")){
            sel.setRemark(StringUtil.objectToString(params.get("remark")));
        }
		if (null != params.get("projectName")){
            sel.setProjectName(StringUtil.objectToString(params.get("projectName")));
        }
		BizTemporarySave temp = this.selectOne(sel);
		if (null != temp) {
            if(basePath.endsWith("/")){
                basePath = basePath.substring(0,basePath.length()-1);
            }
			String filePath = basePath+ "/" + DateUtil.formatYYYYMMDD(temp.getCreateTime()) + "/" + temp.getFilename() + ".tss";
			FileInputStream fileIS = null;
            Input input;
            try {
                fileIS = new FileInputStream(filePath);
                Kryo kryo = new Kryo();
                kryo.setReferences(false);
                kryo.setRegistrationRequired(false);
                kryo.register(clazz);
                input = new Input(fileIS);
                Object ts = null;
                while((ts=kryo.readClassAndObject(input)) != null){
                    input.close();
                    if ("0".equals(temp.getType())){
                        return ((BizTemporarySave)ts).getObj();
                    }else{
                        return (T)ts;
                    }
                }
			} catch (Exception e) {
                logger.error(e.getMessage());
				e.printStackTrace();
			} finally {
                try {
                    if (fileIS != null) {
                        fileIS.close();
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delTemporary(Map params) {
		BizTemporarySave sel = new BizTemporarySave();
        if (null != params.get("id")){
            sel.setId((Long)params.get("id"));
        }
        if (null != params.get("bizcode")){
            sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
        }
        if (null != params.get("rolid")){
            sel.setRolid(StringUtil.objectToString(params.get("rolid")));
        }
        if (null != params.get("taskid")){
            sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
        }
        if (null != params.get("userid")){
            sel.setUserid(StringUtil.objectToString(params.get("userid")));
        }
        if (null != params.get("remark")){
            sel.setRemark(StringUtil.objectToString(params.get("remark")));
        }
        if (null != params.get("projectName")){
            sel.setProjectName(StringUtil.objectToString(params.get("projectName")));
        }
		BizTemporarySave temp = this.selectOne(sel);
		if (null != temp) {
			this.delete(temp.getId());
            if(basePath.endsWith("/")){
                basePath = basePath.substring(0,basePath.length()-1);
            }
			String filePath = basePath + "/" + DateUtil.formatYYYYMMDD(temp.getCreateTime()) + "/" + temp.getFilename() + ".tss";
			File file = new File(filePath);
			if (file.exists()) {
				if (file.delete()){
                    logger.debug("已删除暂存文件：" + temp.getFilename());
                    return true;
                }
				else{
                    throw new RuntimeException("删除暂存文件失败:" + filePath);
                }
			} else {
				throw new RuntimeException("暂存文件文件不存在:" + filePath);
			}
		}
        return false;
	}

	@Override
	public List<BizTemporarySave> getTemporaryList(Map<String, Object> params) {
		return queryList(params);
	}

	// 方案暂存条件查询
	@Override
	public List<BizTemporarySave> queryScheme(EntityWrapper<BizTemporarySave> ew) {
		List<BizTemporarySave> tmpList = mapper.selectList(ew);
		return tmpList;
	}

    @Override
	public Object getById(Long id) {
        Map params = new HashMap();
        params.put("id",id);
	    return this.getTemporary(params);
	}

	@Override
	public boolean delById(Long id) {
        Map params = new HashMap();
        params.put("id",id);
        return this.delTemporary(params);
	}

	@Override
	public int deleteTemporaryByParam(Map<String, Object> params) {
		List<BizTemporarySave> tempList = this.queryList(params);
		int count = 0;
		//判断是否有暂存数据
		if(tempList==null || tempList.isEmpty()){
			return count;
		}
		//如果有多个暂存则循环删除
		for(BizTemporarySave temp : tempList){
			if (null != temp) {
				this.delete(temp.getId());
				logger.info("已删除暂存数据：" + temp.getBizcode());
                if(basePath.endsWith("/")){
                    basePath = basePath.substring(0,basePath.length()-1);
                }
				String filePath = basePath + "/" + DateUtil.formatYYYYMMDD(temp.getCreateTime())  + "/" + temp.getFilename() + ".tss";
				File file = new File(filePath);
				if (file.exists()) {
					if (file.delete()) {
						logger.info("已删除暂存文件：" + temp.getFilename());
						count++;
					} else {
						logger.error("删除暂存文件失败:" + filePath);
						throw new RuntimeException("删除暂存文件失败:" + filePath);
					}
				} else {
					logger.warn("暂存文件文件不存在:" + filePath);
				}
			}
		}
		return count;
	}
}
