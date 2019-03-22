package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.mapper.SysAuthorizeMapper;
import org.ibase4j.mapper.SysDeptMapper;
import org.ibase4j.mapper.SysRoleMenuMapper;
import org.ibase4j.mapper.SysUserRoleMapper;
import org.ibase4j.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysAuthorize")
@Service(interfaceClass = ISysAuthorizeProvider.class)
public class SysAuthorizeProviderImpl extends BaseProviderImpl<SysMenu> implements ISysAuthorizeProvider {
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SysAuthorizeMapper sysAuthorizeMapper;
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private ISysMenuProvider sysMenuProvider;
	@Autowired
	private ISysDicProvider sysDicProvider;

	@Override
	public List<SysUserRole> getRolesByUserId(Long userId) {
		SysUserRole sysUserRole = new SysUserRole(userId, null);
		Wrapper<SysUserRole> wrapper = new EntityWrapper<SysUserRole>(sysUserRole);
		List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
		return userRoles;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = { Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
			Constants.CACHE_NAMESPACE + "userPermission",
			Constants.CACHE_NAMESPACE + "rolePermission" }, allEntries = true)
	public void updateUserRole(List<SysUserRole> sysUserRoles) {
		Long userId = null;
		for (SysUserRole sysUserRole : sysUserRoles) {
			if (sysUserRole != null && sysUserRole.getUserId() != null) {
				userId = sysUserRole.getUserId();
				break;
			}
		}
		if (userId != null) {
			sysAuthorizeMapper.deleteUserRole(userId);
		}
		for (SysUserRole sysUserRole : sysUserRoles) {
			if (sysUserRole != null && sysUserRole.getUserId() != null && sysUserRole.getRoleId() != null) {
				sysUserRoleMapper.insert(sysUserRole);
			}
		}
	}

	@Override
	public List<String> queryMenuIdsByRoleId(Long roleId) {
		List<String> resultList = InstanceUtil.newArrayList();
		List<Long> list = sysRoleMenuMapper.queryMenuIdsByRoleId(roleId);
		for (Long id : list) {
			resultList.add(id.toString());
		}
		return resultList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = { Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
			Constants.CACHE_NAMESPACE + "userPermission",
			Constants.CACHE_NAMESPACE + "rolePermission" }, allEntries = true)
	public void updateRoleMenu(List<SysRoleMenu> sysRoleMenus) {
		Long roleId = null;
		for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
			if (sysRoleMenu != null && sysRoleMenu.getRoleId() != null && "read".equals(sysRoleMenu.getPermission())) {
				roleId = sysRoleMenu.getRoleId();
				break;
			}
		}
		if (roleId != null) {
			sysAuthorizeMapper.deleteRoleMenu(roleId, "read");
		}
		for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
			if (sysRoleMenu != null && sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
					&& "read".equals(sysRoleMenu.getPermission())) {
				sysRoleMenuMapper.insert(sysRoleMenu);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = { Constants.CACHE_NAMESPACE + "menuPermission", Constants.CACHE_NAMESPACE + "sysPermission",
			Constants.CACHE_NAMESPACE + "userPermission",
			Constants.CACHE_NAMESPACE + "rolePermission" }, allEntries = true)
	public void updateRolePermission(List<SysRoleMenu> sysRoleMenus) {
		Long roleId = null;
		for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
			if (sysRoleMenu != null && sysRoleMenu.getRoleId() != null) {
				roleId = sysRoleMenu.getRoleId();
			}
		}
		if (roleId != null) {
			Map<String, Object> dicParam = InstanceUtil.newHashMap();
			dicParam.put("type", "CRUD");
			List<SysDic> sysDics = sysDicProvider.queryList(dicParam);
			for (SysDic sysDic : sysDics) {
				if (sysDic != null && !"read".equals(sysDic.getCode())) {
					sysAuthorizeMapper.deleteRoleMenu(roleId, sysDic.getCode());
				}
			}
		}
		for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
			if (sysRoleMenu != null && sysRoleMenu.getRoleId() != null && sysRoleMenu.getMenuId() != null
					&& !"read".equals(sysRoleMenu.getPermission())) {
				sysRoleMenuMapper.insert(sysRoleMenu);
			}
		}
	}

	@Override
	@Cacheable(value = Constants.CACHE_NAMESPACE + "menuPermission")
	public List<SysMenu> queryAuthorizeByUserId(Long userId, String userType) {
		// 根据用户ID获取菜单ID
		List<Long> menuIds = sysAuthorizeMapper.getAuthorize(userId, userType);
		// 根据菜单ID获取菜单对象
		List<SysMenu> menus = sysMenuProvider.queryList(menuIds);
		Map<Long, List<SysMenu>> map = InstanceUtil.newHashMap();
		for (SysMenu sysMenu : menus) {
			if (sysMenu != null) {
				if(map.get(sysMenu.getParentId())==null){
					List<SysMenu> menuBeans = InstanceUtil.newArrayList();
					menuBeans.add(sysMenu);
					map.put(sysMenu.getParentId(), menuBeans);
				}else{
					List<SysMenu> menuBeans = map.get(sysMenu.getParentId());
					menuBeans.add(sysMenu);
					map.put(sysMenu.getParentId(), menuBeans);
				}
			}
//			map.get(sysMenu.getParentId()).add(sysMenu);
		}
		List<SysMenu> result = InstanceUtil.newArrayList();
		for (SysMenu sysMenu : menus) {
			if (sysMenu != null && sysMenu.getParentId() == 0) {
				sysMenu.setLeaf(0);
				sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
				result.add(sysMenu);
			}
		}
		return result;
	}

	// 递归获取子菜单
	private List<SysMenu> getChildMenu(Map<Long, List<SysMenu>> map, Long id) {
		List<SysMenu> childmenus = map.get(id);
		if (childmenus != null) {
			for (SysMenu sysMenu : childmenus) {
				if (sysMenu != null) {
					sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
				}
			}
		}
		return childmenus;
	}

	@Override
	@Cacheable(Constants.CACHE_NAMESPACE + "sysPermission")
	public List<String> queryPermissionByUserId(Long userId) {
		return sysAuthorizeMapper.queryPermissionByUserId(userId);
	}

	@Cacheable(Constants.CACHE_NAMESPACE + "rolePermission")
	public List<String> queryRolePermission(Long roleId) {
		return sysRoleMenuMapper.queryPermission(roleId);
	}

	@Override
	public List<SysMenu> queryMenusPermission() {
		return sysAuthorizeMapper.queryMenusPermission();
	}

	@Override
	public List<Map<String, Object>> queryRolePermissions(SysRoleMenu sysRoleMenu) {
		List<Map<String, Object>> list = sysRoleMenuMapper.queryPermissions(sysRoleMenu.getRoleId());
		return list;
	}

	@Override
	public List<String> queryRoleIdsByUserId(Long userId) {
		List<String> roleIds = new ArrayList<String>();
		List<Long> roleIdLongs = sysUserRoleMapper.queryRoleIdsByUserId(userId);
		for (Long id : roleIdLongs) {
			roleIds.add(id.toString());
		}
		return roleIds;
	}

	@Override
	public List<String> queryParentDeptIdByDeptCode(String deptCode) {
		List<String> deptIds = new ArrayList<String>();
		int parentLeaf = 0;
		addDept(deptCode, deptIds, parentLeaf);
		return deptIds;
	}

	/**
	 * 嵌套查询上级机构（部门)
	 * 
	 * @param deptId
	 * @param ids
	 */
	private void addDept(String deptCode, List<String> ids, int parentLeaf) {
		SysDept dept = sysDeptMapper.queryDeptByCode(deptCode);
		String parentCode = dept.getParentCode();
		Integer leaf = dept.getLeaf();
		// 只父编号不是0，且leaf不等于0:树枝节点时递归,最多递归6次
		if (StringUtils.isNotBlank(parentCode) && !String.valueOf(0).equals(parentCode) && leaf.intValue() > 0
				&& parentLeaf < 5) {
			parentLeaf++;
			ids.add(parentCode);
			addDept(parentCode, ids, parentLeaf);
		}
	}

	@Override
	public List<String> queryRoleCodeByUserId(Long userId) {
		List<String> roleCodes = sysUserRoleMapper.queryRoleCodesByUserId(userId);
		return roleCodes;
	}

}
