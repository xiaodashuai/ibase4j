package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysRole;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.provider.ISysRoleProvider;
import org.ibase4j.vo.SysRoleCheckVo;
import org.ibase4j.vo.SysRoleMenuVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:09:29
 */
@Service
public class SysRoleService extends BaseService<ISysRoleProvider, SysRole> {
	@Reference
	public void setProvider(ISysRoleProvider provider) {
		this.provider = provider;
	}
	
	public Page<SysRole> getList(Map<String, Object> sysRole){
		return provider.queryPage(sysRole);
	}

	/**
	 * 查询所有可用的角色列表
	 */
	public List<SysRoleCheckVo> getAllRole(Map<String, Object> params) {
		List<SysRoleCheckVo> voList = new ArrayList<SysRoleCheckVo>();
		String roleType = StringUtil.objectToString(params.get("roleType"));
		if(SysConstant.SYSROLE.equals(roleType)){
			roleType = SysConstant.SYSROLE;
		}else{
			roleType = SysConstant.BIZROLE;
		}
		List<SysRole> roleList = provider.queryAllSysRole(roleType);
		for (SysRole role : roleList) {
			SysRoleCheckVo vo = new SysRoleCheckVo();
			vo.setChecked(false);
			vo.setRoleId(role.getId());
			vo.setRoleName(role.getRoleName());
			vo.setPermission(role.getPermission());
			vo.setRoleType(role.getRoleType());
			voList.add(vo);
		}
		return voList;
	}

	/**
	 * 功能: 查询一个用户邦定的所有角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> getUserRoleByUserId(Long userId) {
		return provider.getUserRoleByUserId(userId);
	}

	/**
	 * 查询菜单和 其下面所包含的按钮
	 */
	public List<SysRoleMenuVo> findMenuAll(Long id) {

		List<Map<String, Object>> list = provider.findMenuAll();
		List<SysRoleMenuVo> list1 = new ArrayList<>();

		List<Long> menuIdList = provider.queryRoleMenuByRoleId(id);
		if (menuIdList != null && menuIdList.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SysRoleMenuVo sysRoleMenuVO = new SysRoleMenuVo();
				Map<String, Object> map = list.get(i);
				sysRoleMenuVO.setMenuId(Long.parseLong(map.get("ID_").toString()));
				sysRoleMenuVO.setMenuName((String) map.get("MENU_NAME"));
				sysRoleMenuVO.setMenuType(Integer.parseInt(map.get("MENU_TYPE").toString()));
				sysRoleMenuVO.setPermission((String) map.get("PERMISSION_SHORT"));
				sysRoleMenuVO.setRemark((String) map.get("REMARK_"));

				if (menuIdList.contains(sysRoleMenuVO.getMenuId())) {
					sysRoleMenuVO.setCheckState(true);
				} else {
					sysRoleMenuVO.setCheckState(false);
				}

				List<Map<String, Object>> buttonList = provider.queryButtonByPrentId(sysRoleMenuVO.getMenuId());
				ArrayList<SysMenu> list2 = new ArrayList<SysMenu>();
				for (int j = 0; j < buttonList.size(); j++) {
					SysMenu sysMenu = new SysMenu();
					Map<String, Object> map1 = buttonList.get(j);
					sysMenu.setId(Long.parseLong(map1.get("ID_").toString()));
					sysMenu.setMenuName((String) map1.get("MENU_NAME"));
					sysMenu.setMenuType(Integer.parseInt(map1.get("MENU_TYPE").toString()));
					if (menuIdList.contains(sysMenu.getId())) {
						sysMenu.setCheckState(true);
					} else {
						sysMenu.setCheckState(false);
					}

					list2.add(sysMenu);
				}
				sysRoleMenuVO.setMenuList(list2);

				list1.add(sysRoleMenuVO);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				SysRoleMenuVo sysRoleMenuVO = new SysRoleMenuVo();
				Map<String, Object> map = list.get(i);

				sysRoleMenuVO.setMenuId(Long.parseLong(map.get("ID_").toString()));
				sysRoleMenuVO.setMenuName((String) map.get("MENU_NAME"));
				sysRoleMenuVO.setMenuType(Integer.parseInt(map.get("MENU_TYPE").toString()));
				sysRoleMenuVO.setPermission((String) map.get("PERMISSION_SHORT"));
				sysRoleMenuVO.setRemark((String) map.get("REMARK_"));

				List<Map<String, Object>> buttonList = provider.queryButtonByPrentId(sysRoleMenuVO.getMenuId());
				ArrayList<SysMenu> list2 = new ArrayList<SysMenu>();
				for (int j = 0; j < buttonList.size(); j++) {
					SysMenu sysMenu = new SysMenu();
					Map<String, Object> map1 = buttonList.get(j);
					sysMenu.setId(Long.parseLong(map1.get("ID_").toString()));
					sysMenu.setMenuName((String) map1.get("MENU_NAME"));
					sysMenu.setMenuType(Integer.parseInt(map1.get("MENU_TYPE").toString()));
					sysMenu.setEnable(Integer.parseInt(map1.get("ENABLE_").toString()));
					sysMenu.setRemark((String) map1.get("REMARK_"));

					list2.add(sysMenu);
				}
				sysRoleMenuVO.setMenuList(list2);

				list1.add(sysRoleMenuVO);
			}
		}

		return list1;
	}

	/**
	 * 功能：保存岗位并且保存岗位与操作菜单的关系
	 * 
	 * @param sysRole
	 * @return
	 */
	public boolean updateModel(SysRole sysRole) {
		sysRole.setEnable(SysConstant.ENABLE_YES);
		Long id = provider.updateWithMenus(sysRole);
		return id > 0;
	}

	/**
	 * 功能: 查询岗位关联的权限菜单
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SysRoleMenu> getSelectedMenuIds(Long roleId) {
		List<SysRoleMenu> result = InstanceUtil.newArrayList();
		List<Long> ids =provider.queryRoleMenuByRoleId(roleId);
		for(Long id : ids){
			SysRoleMenu model = new SysRoleMenu();
			model.setMenuId(id);
			result.add(model);
		}
		return result;
	}

	public Boolean queryByAccount(String account) {
		SysRole sysRole = provider.queryByAccount(account);
		if (sysRole != null) {
			return true;
		}
		return false;
	}

	public Boolean queryByCode(String code) {
		SysRole sysRole = provider.queryByCode(code);
		if (sysRole != null) {
			return true;
		}
		return false;
	}

}
