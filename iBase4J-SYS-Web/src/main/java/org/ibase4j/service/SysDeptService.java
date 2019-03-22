package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.ZTreeRadioNode;
import org.ibase4j.provider.ISysDeptProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:07
 */
@Service
public class SysDeptService extends BaseService<ISysDeptProvider, SysDept> {
	@Reference
	public void setProvider(ISysDeptProvider provider) {
		this.provider = provider;
	}
	/**
	 * 功能：查询所有部门
	 * @author czm
	 * @param selectedDeptCode 选中的部门编号
	 * @return 返回所有有效的部门
	 */
	public List<ZTreeRadioNode> getAllDept(String selectedDeptCode){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		List<SysDept> deptList = provider.queryList(params);
		//下拉框使用的集合
		List<ZTreeRadioNode> treeNodeList = new ArrayList<ZTreeRadioNode>();
		for(SysDept dept : deptList){
			ZTreeRadioNode treeNode = new ZTreeRadioNode();
			treeNode.setnId(dept.getCode());
			treeNode.setpId(dept.getParentCode());
			treeNode.setName(dept.getDeptName());
			treeNode.setOpen(true);//默认
			if(selectedDeptCode != null && selectedDeptCode.equals(dept.getCode())){
				//设置选中状态
				treeNode.setNocheck(true);
			}else{
				treeNode.setNocheck(false);
			}
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}

	public boolean queryByDeptName(String name) {
		SysDept model = provider.queryByDeptName(name);
		if (model != null) {
			return true;
		}
		return false;
	}
	
	public Page<SysDept> findList(Map<String, Object> params){
		Page<SysDept> page = provider.query(params);
		List<SysDept> result = (List<SysDept>) page.getRecords();
		if(result!=null && !result.isEmpty()){
			for(SysDept dept : result	){
				String parentCode = dept.getParentCode();
				String parentName = getParentNameString(parentCode);
				dept.setParentName(parentName);
			}
		}
		return page;
	}
	
	/**
	 * 功能：查询部门的父节点
	 * @param parentCode
	 * @return
	 */
	public String getParentNameString(String parentCode){
		StringBuffer strBuf = new StringBuffer();
		loopParentDept(parentCode,strBuf);
		if(strBuf.length()>0){
			String name = strBuf.toString();
			name = StringUtils.removeEnd(name, "-");
			return name;
		}else{
			return strBuf.toString();
		}
	}
	
	/**
	 * 功能：递归全部的父节点
	 * @param code
	 * @param str
	 */
	private void loopParentDept(String code,StringBuffer str){
		SysDept dept = provider.queryDeptByCode(code);
		if(dept!=null ){
			String pCode = dept.getParentCode();
			String thisCode = dept.getCode();
			str.append(dept.getDeptName());
			if(!"0".equals(thisCode)){
				str.append("-");
				loopParentDept(pCode, str);
			}
		}
	}

	public Boolean queryByCode(String code) {
		SysDept sysDept = provider.queryDeptByCode(code);
		if(sysDept != null){
			return true;
		}
		return false;
	}

	public void updateOne(SysDept record) {
		SysDept queryDeptByCode = provider.queryDeptByCode(record.getParentCode());
		record.setParentName(queryDeptByCode.getDeptName());
		provider.update(record);
	}
	
}
