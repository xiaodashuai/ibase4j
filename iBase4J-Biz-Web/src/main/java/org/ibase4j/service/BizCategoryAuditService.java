
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizCategoryAudit;
import org.ibase4j.provider.BizCategoryAuditProvider;
import org.ibase4j.vo.MiddlePageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：请假流程
 * 
 * @author lwh
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizCategoryAuditService extends BaseService<BizCategoryAuditProvider, BizCategoryAudit> {
	@Reference
	public void setCategoryAuditProvider(BizCategoryAuditProvider provider) {
		this.provider = provider;
	}

//	@Autowired
//	private BizAuditCheckService bizAuditCheckService;
	@Autowired
	private BizBusiCategoryService bizBusiCategoryService;


	//页面展示内容查询
	public List<MiddlePageVo> getMiddlePage(Long id,Long sortNo){

		Map<String,Object> map = new HashMap<>();
//		map.put("categoryId",id);
//		map.put("sortNo",sortNo);
		List<BizCategoryAudit> categoryAuditList  =  provider.queryList(map);
		List<MiddlePageVo> voList = new ArrayList<>();
		for (BizCategoryAudit bizCategoryAudit:categoryAuditList
			 ) {
			if(bizCategoryAudit.getCategoryId().equals(id)){//判断业务
                MiddlePageVo vo = new MiddlePageVo();
//                BizAuditCheck auditCheck = bizAuditCheckService.queryById(bizCategoryAudit.getAuditId());
//				BizBusiCategory busiCategory = bizBusiCategoryService.queryById(id);
//                if(auditCheck.getSortNo().equals(sortNo)){//判断岗位
//					vo.setName(busiCategory.getCategoryNmae());
//					vo.setAuditName(auditCheck.getAuditNmae());
//					vo.setCode(bizCategoryAudit.getCode());
//					vo.setMid(sortNo);
//					voList.add(vo);
//				}
			}
		}
//		for (BizCategoryAudit bizCategoryAudit:categoryAuditList
//			 ) {
//			MiddlePageVo vo = new MiddlePageVo();
//			BizAuditCheck auditCheck = bizAuditCheckService.queryById(bizCategoryAudit.getAuditId());
//			BizBusiCategory busiCategory = bizBusiCategoryService.queryById(id);
//			vo.setName(busiCategory.getCategoryNmae());
//			vo.setAuditName(auditCheck.getAuditNmae());
//			vo.setCode(bizCategoryAudit.getCode());
//			voList.add(vo);
//		}
		return voList;
	}

}
