package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysMenuMapper;
import org.ibase4j.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysMenu")
@Service(interfaceClass = ISysMenuProvider.class)
public class SysMenuProviderImpl extends BaseProviderImpl<SysMenu> implements ISysMenuProvider {
	@Autowired
	private ISysDicProvider sysDicProvider;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	//delete by zhy 20180108 
//	public List<SysMenu> queryList(Map<String, Object> params) {
//		List<SysMenu> pageInfo = super.queryList(params);
//		Map<String, String> menuTypeMap = sysDicProvider.queryDicByDicIndexKey("MENUTYPE");
//		EntityWrapper<SysMenu> wrapper = new EntityWrapper<SysMenu>();
//		for (SysMenu sysMenu : pageInfo) {
//			if (sysMenu.getMenuType() != null) {
//				sysMenu.setTypeName(menuTypeMap.get(sysMenu.getMenuType().toString()));
//			}
//			SysMenu menu = new SysMenu();
//			menu.setParentId(sysMenu.getId());
//			wrapper.setEntity(menu);
//			int count = mapper.selectCount(wrapper);
//			if (count > 0) {
//				sysMenu.setLeaf(0);
//			}
//		}
//		return pageInfo;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ibase4j.provider.SysMenuProvider#getPermissions()
	 */
	@Override
	public List<Map<String, String>> getPermissions() {
		return ((SysMenuMapper) mapper).getPermissions();
	}

	@Override
	public SysMenu queryByMenuName(String account) {
		SysMenu sysMenu = sysMenuMapper.queryByMenuName(account);
		return sysMenu;
	}

	@Override
	public List<SysMenu> queryMenuList(int enable, String powerType) {
		List<SysMenu> list = sysMenuMapper.queryMenuList(enable , powerType);
		return list;
	}

	@Override
	public Page<?> queryMenu(Map<String, Object> sysMenu) {
		Page<Long> page = getPage(sysMenu);
		page.setRecords(mapper.selectIdPage(page, sysMenu));
		return getPage(page);
	}

}
