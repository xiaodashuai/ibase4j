package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizCust;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.provider.BizCustProvider;
import org.ibase4j.provider.BizProductLinesTypeProvider;
import org.ibase4j.provider.IBizProductTypeProvider;
import org.ibase4j.vo.ProductBussinessVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoshuiquan
 * @version 2018年6月13日 下午3:47:21
 */
@Service
public class BizUseLetterService extends BaseService<BizCustProvider, BizCust> {
	@Reference
	private IBizProductTypeProvider bizProductTypeProvider;
	@Reference
	private BizProductLinesTypeProvider productLinesTypeProvider;
//	@Reference
//	private BizCustProvider custProvider;

	@Reference
	public void setProvider(BizCustProvider provider) {
		this.provider = provider;
	}

	/** 查询客户 */
	public BizCust getCustomerList(String custNo) {
		Map<String,Object> map=new HashMap<>();
		map.put("custNo", custNo);
		List<BizCust> cusList=this.queryList(map);
		return cusList.get(0);
	}

	/**
	 * 功能 ：查询为客户绑定的产品；<br/>
	 * 调整理由 ：因为产品种类数据库有原来3个表改成了一个表BIZ_PRODUCT_TYPES<br/>
	 * 日期 ：2018/7/19<br/>
	 * 修改人： czm <br/>
	 * @param customer
	 * @return
	 */
	public List<ProductBussinessVo> queryCusPro(Map<String, Object> customer) {
		String proIds = customer.get("proIds").toString();
		String[] ids = StringUtils.split(proIds, ",");
		List<ProductBussinessVo> bLsit = new ArrayList<ProductBussinessVo>();
		//
		for (int i = 0; i < ids.length; i++) {
			ProductBussinessVo proBusinVo = new ProductBussinessVo();
			String typeCode = ids[i];
			BizProductTypes bizProductType = bizProductTypeProvider.getByCode(typeCode);
			if (bizProductType != null) {
				proBusinVo.setCode(bizProductType.getCode());
				proBusinVo.setName(bizProductType.getName());
				proBusinVo.setParentCode(bizProductType.getParentCode());
				proBusinVo.setCreditLinesId(null);
				proBusinVo.setCreditRatio(null);
				bLsit.add(proBusinVo);
			}
		}
		return bLsit;
	}


}
