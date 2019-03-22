/**
 * 
 */
package org.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.support.DateFormat;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.http.SessionUser;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.WebUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseController {
	protected final Logger logger = LogManager.getLogger(this.getClass());


	/** 获取当前用户Id(shiro) */
	protected SessionUser getCurrUser() {
		return (SessionUser) SecurityUtils.getSubject().getPrincipal();
	}

	/** 获取当前用户Id */
	protected Long getCurrUser(HttpServletRequest request) {
		SessionUser user = WebUtil.getCurrentUser(request);
		if (user == null) {
			return null;
		} else {
			return user.getId();
		}
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new DateFormat(), true));
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
		return setSuccessModelMap(modelMap, null);
	}

	/** 设置成功响应代码 */
	protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
		return setModelMap(modelMap, HttpCode.OK, data);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
		return setModelMap(modelMap, code, null);
	}

	/** 设置响应代码 */
	protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
		Map<String, Object> map = InstanceUtil.newLinkedHashMap();
		map.putAll(modelMap);
		modelMap.clear();
		for (String key : map.keySet()) {
			if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
				modelMap.put(key, map.get(key));
			}
		}
		if (data != null) {
			if (data instanceof Page) {
				Page<?> page = (Page<?>) data;
				modelMap.put("data", page.getRecords());
				modelMap.put("current", page.getCurrent());
				modelMap.put("size", page.getSize());
				modelMap.put("pages", page.getPages());
				modelMap.put("total", page.getTotal());
				modelMap.put("iTotalRecords", page.getTotal());
				modelMap.put("iTotalDisplayRecords", page.getTotal());
			} else if (data instanceof List<?>) {
				modelMap.put("data", data);
				modelMap.put("iTotalRecords", ((List<?>) data).size());
				modelMap.put("iTotalDisplayRecords", ((List<?>) data).size());
			} else {
				modelMap.put("data", data);
			}
		}
		modelMap.put("httpCode", code.value());
		modelMap.put("msg", code.msg());
		modelMap.put("timestamp", System.currentTimeMillis());
		logger.info("RESPONSE : " + JSON.toJSONString(modelMap));
		return ResponseEntity.ok(modelMap);
	}

}
