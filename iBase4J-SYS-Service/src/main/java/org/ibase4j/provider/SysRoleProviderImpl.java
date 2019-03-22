package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysMenuMapper;
import org.ibase4j.mapper.SysRoleMapper;
import org.ibase4j.mapper.SysRoleMenuMapper;
import org.ibase4j.mapper.SysUserRoleMapper;
import org.ibase4j.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.*;

/**
 * @author ShenHuaJie
 * @version 2016年5月31日 上午11:01:33
 */
@CacheConfig(cacheNames = "sysRole")
@Service(interfaceClass = ISysRoleProvider.class)
public class SysRoleProviderImpl extends BaseProviderImpl<SysRole> implements ISysRoleProvider {
	@Autowired
	private ISysDeptProvider sysDeptProvider;
	@Autowired
	private ISysMenuProvider sysMenuProvider;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public Page<SysRole> queryPage(Map<String, Object> params) {
		Page<SysRole> pageInfo = query(params);
		// 权限信息
		for (SysRole bean : pageInfo.getRecords()) {
			String deptCode = bean.getDeptCode();
			if (StringUtils.isNotBlank(deptCode)) {
				SysDept sysDept = sysDeptProvider.queryDeptByCode(deptCode);
				if (sysDept != null) {
					bean.setDeptName(sysDept.getDeptName());
				}
			}
			List<String> permissions = sysRoleMenuMapper.queryPermission(bean.getId());
			Set<String> set = new HashSet<String>();
			set.addAll(permissions);
			permissions.clear();
			permissions.addAll(set);
			for (String permission : permissions) {
				if (StringUtils.isBlank(bean.getPermission())) {
					bean.setPermission(permission);
				} else {
					bean.setPermission(bean.getPermission() + ";" + permission);
				}
			}
		}
		return pageInfo;
	}

	@Override
	public List<SysRole> getAll(Map<String, Object> params) {
		List<SysRole> roles = super.queryList(params);
		// 权限信息
		for (SysRole bean : roles) {
			String deptCode = bean.getDeptCode();
			if (StringUtils.isNotBlank(deptCode)) {
				SysDept sysDept = sysDeptProvider.queryDeptByCode(deptCode);
				if (sysDept != null) {
					bean.setDeptName(sysDept.getDeptName());
				}
			}
			List<String> permissions = sysRoleMenuMapper.queryPermission(bean.getId());
			for (String permission : permissions) {
				if (StringUtils.isBlank(bean.getPermission())) {
					bean.setPermission(permission);
				} else {
					bean.setPermission(bean.getPermission() + ";" + permission);
				}
			}
		}
		return roles;
	}

	@Override
	public List<SysUserRole> getUserRoleByUserId(Long userId) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("user_id", userId);
		return sysUserRoleMapper.selectByMap(columnMap);
	}

	@Override
	public boolean saveUserRole(List<SysUserRole> sysUserRoles, Long userId) {
		// 1.首先删除旧关系
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("user_id", userId);
		// 1.删除旧关系
		Integer count = sysUserRoleMapper.deleteByMap(columnMap);
		logger.debug("delete row count:" + count);
		// 2.保存新关系
		for (SysUserRole entity : sysUserRoles) {
			sysUserRoleMapper.insert(entity);
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> findMenuAll() {
		List<Map<String, Object>> list = sysRoleMenuMapper.queryAllMenu();
		return list;
	}

	@Override
	public List<Map<String, Object>> queryButtonByPrentId(Long pId) {
		List<Map<String, Object>> list = sysRoleMenuMapper.queryButtonByPrentId(pId);
		return list;
	}

	@Override
	public List<Long> queryRoleMenuByRoleId(Long roleId) {
		List<Long> list = sysRoleMenuMapper.queryMenuIdByRoleId(roleId);
		return list;
	}

	@Override
	public SysRole queryByAccount(String account) {
		SysRole sysRole = sysRoleMapper.queryByAccount(account);
		return sysRole;
	}

	@Override
	public boolean deleteRoleByUser(Long userId) {
		return sysUserRoleMapper.deleteByUser(userId);
	}

	@Override
	public List<SysRole> queryAllSysRole(String sysRole) {
		Integer roleType = Integer.valueOf(sysRole);
		List<SysRole> list = sysRoleMapper.queryByRoleType(roleType);
		return list;
	}

	@Override
	public Long updateWithMenus(SysRole role) {
		// 1.修改或添加岗位
		SysRole sysRole = update(role);
		Long createId = sysRole.getCreateBy();
		Long roleId = sysRole.getId();
		String menuIds = role.getMenus();
		// 2.修改维护岗位与权限关系
		if (StringUtils.isNotBlank(menuIds)) {
			List<SysRoleMenu> result = parserRoleMenu(menuIds, createId, roleId);
			// 1.首先删除旧关系
			if (role.getId() != null) {
				sysRoleMenuMapper.deleteByRole(roleId);
			}
			for (SysRoleMenu entity : result) {
				
				sysRoleMenuMapper.insert(entity);
			}
		}
		return roleId;
	}

	/**
	 * 功能：根据岗位选中的菜单字符串（用逗号分割的菜单编号字符串），生成岗位与菜单的关系表
	 * 
	 * @param menuIds
	 * @param createId
	 * @param roleId
	 * @return
	 */
	private List<SysRoleMenu> parserRoleMenu(String menuIds, Long createId, Long roleId) {
		List<SysRoleMenu> result = new ArrayList<SysRoleMenu>();
		String[] ids = StringUtils.split(menuIds, ",");
		// 所有菜单的父类菜单
		Set<SysMenu> parentSet = new HashSet<SysMenu>();
		// 首先插入选中的菜单
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				Long menuId = Long.valueOf(id);
				// 新增父菜单
				SysMenu queryById = sysMenuProvider.queryById(menuId);
				List<SysMenu> menus = sysMenuMapper.queryParents(queryById.getParentId());
				parentSet.addAll(menus);
				// 岗位与菜单关系
				
				SysRoleMenu rm = bulidRoleMenu(menuId, createId, roleId);
				result.add(rm);
			}
		}
		// 再次插入选中菜单的父菜单
		if (!parentSet.isEmpty()) {
			for (SysMenu s : parentSet) {
				// 岗位与菜单关系
				SysRoleMenu rm = bulidRoleMenu(s.getId(), createId, roleId);
				result.add(rm);
			}
		}
		return result;
	}

	/**
	 * 功能：构造岗位与权限关系
	 * 
	 * @param menuId
	 *            菜单编号
	 * @param createId
	 * @param roleId
	 *            岗位编号
	 * @return 返回岗位与权限关系对象
	 */
	private SysRoleMenu bulidRoleMenu(Long menuId, Long createId, Long roleId) {
		SysRoleMenu sysRoleMenu = new SysRoleMenu();
		sysRoleMenu.setMenuId(menuId);
		sysRoleMenu.setRoleId(roleId);
		sysRoleMenu.setCreateBy(createId);
		sysRoleMenu.setCreateTime(new Date());
		sysRoleMenu.setEnable(1);
		sysRoleMenu.setRemark("角色id:" + roleId + "与菜单id:" + menuId);
		SysMenu menu = sysMenuMapper.selectById(menuId);
		sysRoleMenu.setPermission(menu.getPermissionShort());
		return sysRoleMenu;
	}

	@Override
	public SysRole queryByCode(String code) {
		SysRole sysRole = sysRoleMapper.queryByCode(code);
		return sysRole;
	}
	@Override
	public SysRole selectOneSysRole(SysRole sysRole){
		return selectOne(sysRole);
	}
}
