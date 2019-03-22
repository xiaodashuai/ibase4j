package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysRole;
import org.ibase4j.model.SysUserRole;

import java.util.List;
import java.util.Map;

public interface ISysRoleProvider extends BaseProvider<SysRole> {
	
	/**
	 * 功能：分页查询岗位
	 */
	Page<SysRole> queryPage(Map<String, Object> params);
	/**
	 * 功能：查询出所有的岗位，属性中包括岗位的权限字段
	 * 
	 * @param params
	 * @return
	 */
	List<SysRole> getAll(Map<String, Object> params);

	/**
	 * 功能: 查询一个用户有多少个岗位
	 * 
	 * @param userId
	 *            用户编号
	 * @return
	 */
	List<SysUserRole> getUserRoleByUserId(Long userId);

	/**
	 * 功能: 新增或修改岗位,并维护岗位和权限的关系
	 * 
	 * @param role
	 * @return 成功返回自增的id,否则返回空
	 */
	Long updateWithMenus(SysRole role);

	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	List<Map<String, Object>> findMenuAll();

	/**
	 * 查询指定菜单下的按钮
	 * 
	 * @param pId
	 * @return
	 */
	List<Map<String, Object>> queryButtonByPrentId(Long pId);

	/**
	 * 功能：批量保存用户与岗位关系
	 * 
	 * @param sysUserRoles
	 *            用户与岗位关系
	 * @param userId
	 *            用户编号
	 * @return
	 */
	boolean saveUserRole(List<SysUserRole> sysUserRoles, Long userId);

	/**
	 * 功能：根据岗位ID查询菜单ID
	 * 
	 * @param roleId
	 * @return
	 */
	List<Long> queryRoleMenuByRoleId(Long roleId);

	/**
	 * 功能：清空柜员的所有岗位
	 * 
	 * @param userId
	 *            柜员编号
	 * @return
	 */
	boolean deleteRoleByUser(Long userId);

	SysRole queryByAccount(String account);

	/**
	 * 查询所有后台管理系统岗位
	 * 
	 * @param sysRole
	 * @return
	 */
	List<SysRole> queryAllSysRole(String sysRole);
	SysRole queryByCode(String code);

	SysRole selectOneSysRole(SysRole sysRole);
}
