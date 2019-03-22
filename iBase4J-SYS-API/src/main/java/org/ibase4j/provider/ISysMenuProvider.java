package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysMenu;

import java.util.List;
import java.util.Map;

public interface ISysMenuProvider extends BaseProvider<SysMenu> {
	/** 获取所有权限选项(value-text) */
	public List<Map<String, String>> getPermissions();

	public SysMenu queryByMenuName(String account);
	//根据powerType查询Menu
	public List<SysMenu> queryMenuList(int enable, String powerType);

	public Page<?> queryMenu(Map<String, Object> sysMenu);
}
