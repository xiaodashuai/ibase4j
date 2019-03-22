package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.ZTreeRadioNode;
import org.ibase4j.provider.ISysMenuProvider;
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
public class SysMenuService extends BaseService<ISysMenuProvider, SysMenu> {
	@Reference
	public void setProvider(ISysMenuProvider provider) {
		this.provider = provider;
	}

	public List<Map<String, String>> getPermissions() {
		return provider.getPermissions();
	}

	@Override
	public SysMenu queryById(Long id) {
		return provider.queryById(id);
	}

	/**
	 * 功能：显示操作权限菜单树
	 * 
	 * @author czm
	 * @param sysMenu 
	 * @param menuIds 菜单编号字符串
	 * @return 返回菜单树
	 */
	public List<ZTreeRadioNode> getMenuTree(String roleType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		params.put("powerType", roleType);
		List<SysMenu> menuList = (List<SysMenu>) provider.queryMenuList(SysConstant.ENABLE_YES , roleType);
		// 下拉框使用的集合
		List<ZTreeRadioNode> treeNodeList = new ArrayList<ZTreeRadioNode>();
		for (SysMenu menu : menuList) {
			ZTreeRadioNode node = new ZTreeRadioNode();
			node.setnId(menu.getId().toString());
			if (menu.getParentId() != null) {
				node.setpId(menu.getParentId().toString());
			}
			node.setName(menu.getMenuName());
			node.setOpen(true);// 默认
			node.setNocheck(false);
			treeNodeList.add(node);
		}
		return treeNodeList;
	}

	public Boolean queryByMenuName(String account) {
		SysMenu sysMenu = provider.queryByMenuName(account);
		if (sysMenu != null) {
			return true;
		}
		return false;
	}

	public List<?> queryMenuList(Map<String, Object> sysMenu) {
		List<?> queryMenuList = provider.queryMenuList(1 , null);
		return queryMenuList;
	}

	public Page<?> queryMenu(Map<String, Object> sysMenu) {
		
		return provider.queryMenu(sysMenu);
	}

}
