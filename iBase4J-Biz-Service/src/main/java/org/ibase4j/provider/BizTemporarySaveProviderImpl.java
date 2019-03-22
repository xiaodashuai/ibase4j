package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import org.apache.commons.collections.CollectionUtils;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizTemporarySave;
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
public class BizTemporarySaveProviderImpl extends BaseProviderImpl<BizTemporarySave>
		implements BizTemporarySaveProvider {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveTemporary(Object t, Map params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode"));//根据方案编号查询
		String deptCode = StringUtil.objectToString(params.get("deptCode"));//部门编码
		String taskId = StringUtil.objectToString(params.get("taskid"));//业务类型（发放/变更/重新提交/数据迁移）
		String bizcode =StringUtil.objectToString(params.get("bizcode"));//业务编号
		String remark =  StringUtil.objectToString(params.get("remark"));//发放申请人
		String projectName= StringUtil.objectToString(params.get("projectName"));//项目名称
		String roleId = StringUtil.objectToString(params.get("rolid"));//角色id 
		String userId = StringUtil.objectToString(params.get("userid"));//用户id 
		//返回参数
		boolean res = false;
		BizTemporarySave temp = null;
		//查询暂存的对象列表
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("schemeNum", debtCode);//根据方案编号查询
		queryMap.put("deptCode",deptCode );//部门编码
		queryMap.put("taskid", taskId);//业务类型（发放/变更/重新提交/数据迁移）
		List<BizTemporarySave> selmodList = queryList(queryMap);
		//暂存对象
		BizTemporarySave selmod = null;
		if(CollectionUtils.isNotEmpty(selmodList)){
			logger.debug("查询出"+selmodList.size()+"符合条件的暂存信息...");
			selmod = selmodList.get(0);
		}
		if (null != selmod && ((t instanceof BizTemporarySave && "0".equals(selmod.getType()))
				|| (!(t instanceof BizTemporarySave) && "1".equals(selmod.getType())))) {
			// 数据库中原暂存数据上更新
			if ("0".equals(selmod.getType())) {
				temp = (BizTemporarySave) t;
				if (null != temp.getObj()) {
					selmod.setObj(temp.getObj());
					if (this.saveTmpObj(selmod, selmod.getFilename())) {
						selmod.setDeptCode(deptCode);
						this.update(selmod);
						res = true;
					} else {
						throw new RuntimeException("BizTemporarySave:Save object error!");
					}
				} else {
					throw new RuntimeException("this BizTemporarySave.obj is null!");
				}
			} else {
				if (this.saveTmpObj(t, selmod.getFilename())) {
					this.update(selmod);
					res = true;
				} else {
					throw new RuntimeException("BizTemporarySave:Save object error!");
				}
			}
		} else {
			BizTemporarySave sel = new BizTemporarySave();
			sel.setDeptCode(deptCode);
			sel.setTaskid(taskId);
			sel.setBizcode(bizcode);
			sel.setRemark(remark);	
			sel.setProjectName(projectName);
			sel.setRolid(roleId);
			sel.setUserid(userId);
			// 新增一条数据
			String filename = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			if (t instanceof BizTemporarySave) {
				temp = (BizTemporarySave) t;
				if (null != temp.getObj()) {
					sel.setType("0");
					sel.setFilename(filename);
					sel.setObj(temp.getObj());
					if (this.saveTmpObj(sel, filename)) {
						this.update(sel);
						res = true;
					} else {
						throw new RuntimeException("BizTemporarySave:Save object error!");
					}
				} else {
					throw new RuntimeException("this BizTemporarySave.obj is null!");
				}
			} else {
				sel.setType("1");
				sel.setFilename(filename);
				if (this.saveTmpObj(t, filename)) {
					this.update(sel);
					res = true;
				} else {
					throw new RuntimeException("BizTemporarySave:Save pojo error!");
				}
			}
		}
		return res;
	}

	/**
	 *
	 *
	 * xiaoshuiquan
	 * 
	 * @date 2018.11.1
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveSchemeTemporary(Object t, Map params) {
		boolean res = false;
		BizTemporarySave temp = null;
		BizTemporarySave sel = new BizTemporarySave();
		BizTemporarySave se2 = new BizTemporarySave();

		if (null != params.get("bizcode"))
			sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
		se2.setBizcode(StringUtil.objectToString(params.get("bizcode")));
		if (null != params.get("rolid"))
			sel.setRolid(StringUtil.objectToString(params.get("rolid")));
		if (null != params.get("taskid"))
			sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
		if (null != params.get("userid"))
			sel.setUserid(StringUtil.objectToString(params.get("userid")));
		if (null != params.get("remark"))
			sel.setRemark(StringUtil.objectToString(params.get("remark")));
		if (null != params.get("projectName"))
			sel.setProjectName(StringUtil.objectToString(params.get("projectName")));
		if (null != params.get("deptCode"))
			sel.setDeptCode(StringUtil.objectToString(params.get("deptCode")));
		/**
		 * 判断是否是第一次暂存
		 */
		BizTemporarySave selmod = this.selectOne(se2);

		if (null != selmod && ((t instanceof BizTemporarySave && "0".equals(selmod.getType()))
				|| (!(t instanceof BizTemporarySave) && "1".equals(selmod.getType())))) {
			// 数据库中原暂存数据上更新
			if ("0".equals(selmod.getType())) {
				temp = (BizTemporarySave) t;
				if (null != temp.getObj()) {
					selmod.setObj(temp.getObj());
					if (this.saveTmpObj(selmod, selmod.getFilename())) {
						if (null != params.get("bizcode"))
							selmod.setBizcode(StringUtil.objectToString(params.get("bizcode")));
						se2.setBizcode(StringUtil.objectToString(params.get("bizcode")));
						if (null != params.get("rolid"))
							selmod.setRolid(StringUtil.objectToString(params.get("rolid")));
						if (null != params.get("taskid"))
							selmod.setTaskid(StringUtil.objectToString(params.get("taskid")));
						if (null != params.get("userid"))
							selmod.setUserid(StringUtil.objectToString(params.get("userid")));
						if (null != params.get("remark"))
							selmod.setRemark(StringUtil.objectToString(params.get("remark")));
						if (null != params.get("projectName"))
							selmod.setProjectName(StringUtil.objectToString(params.get("projectName")));
						if (null != params.get("deptCode"))
							selmod.setDeptCode(StringUtil.objectToString(params.get("deptCode")));
						selmod.setCreateTime(new Date());
						this.update(selmod);
						res = true;
					} else {
						throw new RuntimeException("BizTemporarySave:Save object error!");
					}
				} else {
					throw new RuntimeException("this BizTemporarySave.obj is null!");
				}
			} else {
				if (this.saveTmpObj(t, selmod.getFilename())) {
					this.update(selmod);
					res = true;
				} else {
					throw new RuntimeException("BizTemporarySave:Save object error!");
				}
			}
		} else {
			// 新增一条数据
			String filename = UUID.randomUUID().toString().replace("-", "").toLowerCase();
			if (t instanceof BizTemporarySave) {
				temp = (BizTemporarySave) t;
				if (null != temp.getObj()) {
					sel.setType("0");
					sel.setFilename(filename);
					sel.setObj(temp.getObj());
					if (this.saveTmpObj(sel, filename)) {
						// selmod.setObj(null);
						this.update(sel);
						res = true;
					} else {
						throw new RuntimeException("BizTemporarySave:Save object error!");
					}
				} else {
					throw new RuntimeException("this BizTemporarySave.obj is null!");
				}
			} else {
				sel.setType("1");
				sel.setFilename(filename);
				if (this.saveTmpObj(t, filename)) {
					this.update(sel);
					res = true;
				} else {
					throw new RuntimeException("BizTemporarySave:Save pojo error!");
				}
			}
		}
		return res;
	}

	private boolean saveTmpObj(Object obj, String filename) {

		if (null == filename || null == obj)
			return false;
		String filePath = PropertiesUtil.getString("tempsav.dir") + filename + ".tss";
		File file = new File(filePath);
		File fileParent = file.getParentFile();
		if (!fileParent.exists())
			fileParent.mkdirs();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(obj);
			oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object getTemporary(Map params) {
		BizTemporarySave sel = new BizTemporarySave();
		if (null != params.get("bizcode"))
			sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
		if (null != params.get("rolid"))
			sel.setRolid(StringUtil.objectToString(params.get("rolid")));
		if (null != params.get("taskid"))
			sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
		if (null != params.get("userid"))
			sel.setUserid(StringUtil.objectToString(params.get("userid")));
		BizTemporarySave temp = this.selectOne(sel);
		if (null != temp) {
			String filePath = PropertiesUtil.getString("tempsav.dir") + temp.getFilename() + ".tss";
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
				if ("0".equals(temp.getType())) {// 序列化后getobj
					BizTemporarySave rd = (BizTemporarySave) ois.readObject();
					ois.close();
					return rd.getObj();
				} else if ("1".equals(temp.getType())) {// 直接回传
					Object o = ois.readObject();
					ois.close();
					return o;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delTemporary(Map params) {
		BizTemporarySave sel = new BizTemporarySave();
		if (null != params.get("bizcode"))
			sel.setBizcode(StringUtil.objectToString(params.get("bizcode")));
		if (null != params.get("rolid"))
			sel.setRolid(StringUtil.objectToString(params.get("rolid")));
		if (null != params.get("taskid"))
			sel.setTaskid(StringUtil.objectToString(params.get("taskid")));
		if (null != params.get("userid"))
			sel.setUserid(StringUtil.objectToString(params.get("userid")));
		BizTemporarySave temp = this.selectOne(sel);
		if (null != temp) {
			this.delete(temp.getId());
			String filePath = PropertiesUtil.getString("tempsav.dir") + temp.getFilename() + ".tss";
			File file = new File(filePath);
			if (file.exists()) {
				if (file.delete())
					logger.debug("已删除暂存文件：" + temp.getFilename());
				else
					throw new RuntimeException("删除暂存文件失败:" + filePath);
			} else {
				throw new RuntimeException("暂存文件文件不存在:" + filePath);
			}
		}
	}

	@Override
	public List<BizTemporarySave> getTemporaryList(Map<String, Object> params) {
		EntityWrapper<BizTemporarySave> sel = new EntityWrapper();
		mapper.selectList(sel);
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
		BizTemporarySave temp = queryById(id);
		String filePath = PropertiesUtil.getString("tempsav.dir") + temp.getFilename() + ".tss";
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
			if ("0".equals(temp.getType())) {// 序列化后getobj
				BizTemporarySave rd = (BizTemporarySave) ois.readObject();
				ois.close();
				return rd.getObj();
			} else if ("1".equals(temp.getType())) {// 直接回传
				Object o = ois.readObject();
				ois.close();
				return o;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delById(Long id) {
		BizTemporarySave temp = this.queryById(id);
		if (null != temp) {
			this.delete(temp.getId());
			String filePath = PropertiesUtil.getString("tempsav.dir") + temp.getFilename() + ".tss";
			File file = new File(filePath);
			if (file.exists()) {
				if (file.delete()) {
					logger.info("已删除暂存文件：" + temp.getFilename());
					return true;
				} else {
					logger.error("删除暂存文件失败:" + filePath);
				}
			} else {
				logger.warn("暂存文件文件不存在:" + filePath);
			}
		}
		return false;
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
				String filePath = PropertiesUtil.getString("tempsav.dir") + temp.getFilename() + ".tss";
				File file = new File(filePath);
				if (file.exists()) {
					if (file.delete()) {
						logger.info("已删除暂存文件：" + temp.getFilename());
						count++;
					} else {
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
