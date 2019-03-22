/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizGuaranteeType;
import org.ibase4j.model.NameValuePair;
import org.ibase4j.provider.BizGuaranteeTypeProvider;
import org.ibase4j.vo.PairVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：业务类型表 
 * 日期：2018/7/17
 * @author czm
 * @version 1.0
 */
@Service
public class BizGuaranteeTypeService extends BaseService<BizGuaranteeTypeProvider,BizGuaranteeType> {
	@Reference
    public void setProvider(BizGuaranteeTypeProvider provider){
		this.provider = provider;
	}
	
	/**
	 * 功能：查询节点下的所有担保类型
	 * @param parentCode 父节点代码
	 * @param type 业务编码类型
	 * @return 返回子节点
	 */
	public List<NameValuePair> getGuaranteeTypes(String parentCode,String type){
		Map<String,Object> params =new HashMap<String,Object>();
		if(StringUtils.isNotBlank(parentCode)){
			//查询指定节点下的所有节点
			params.put("parentCode",parentCode);
		}else{
			//查询担保类型根节点
			params.put("parentCode", BizContant.BIZ_GUARANTEE_TYPE_ROOT_CODE);
		}
		//查询有效的节点
		params.put("enable", BizContant.ENABLE_YES);
		params.put("type",type);
		List<BizGuaranteeType> list = queryList(params);
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for(BizGuaranteeType node: list){
			NameValuePair pair = new NameValuePair();
			pair.setName(node.getName());
			pair.setValue(node.getCode());
			result.add(pair);
		}
		return result;
	}
	
	/**
	 * 功能：查询节点下的所有利率类型
	 * @param parentCode 父节点代码
	 * @return 返回子节点
	 */
	public List<NameValuePair> getInterestRateTypes(String parentCode){
		Map<String,Object> params =new HashMap<String,Object>();
		//查询指定节点下的所有节点
		if(StringUtils.isNotBlank(parentCode)){
			params.put("parentCode",parentCode);
		}
		//查询有效的节点
		params.put("enable", BizContant.ENABLE_YES);
		params.put("type",BizContant.BIZ_BNS_INTEREST_RATE_TYPE);
		List<BizGuaranteeType> list = queryList(params);
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for(BizGuaranteeType node: list){
			String srvStr = node.getSrv1();
			NameValuePair pair = new NameValuePair();
			String code = node.getCode();
			if("0".equals(srvStr)){
				String lineName = getPrefixLine(code);
				pair.setName(lineName+node.getName());
			}else{
				pair.setName(node.getName());
			}
			pair.setValue(code);
			result.add(pair);
		}
		return result;
	}

	/**
	 * 功能：自动生成符号(-)
	 * 
	 * @param number
	 * @return
	 */
	private String getPrefixLine(String number) {
		String splitStr = "";
		int len = StringUtil.getEndNotZeroNumberIndex(number);
		int result = len - 1;
		for (int i = 0; i < result; i++) {
			splitStr += "--";
		}
		return splitStr;
	}
	
	/**
	 * 功能：查询多级分类代码
	 * @param typeCode
	 * @param parentCode
	 * @return
	 */
	public List<PairVo> getByTypeCode(String typeCode, String parentCode){
		return provider.getByTypeCode(typeCode, parentCode);
	}
	
	/**
	 * 功能：根据类型和编码查询对象
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public PairVo getPairByTypeCode(String typeCode,String code){
		return provider.getByCode(code, typeCode);
	}
	
	/**
	 * 功能：通过多个类型查询多级分类代码
	 * @param typeCodes 多个类型编码
	 * @param parentCodes 多个父编码
	 * @return
	 */
	public Map<String,List<PairVo>> getByMulitTypeCode(String typeCodes, String parentCodes){
		Map<String,List<PairVo>> result = new HashMap<String,List<PairVo>>();
		if(StringUtils.isNotBlank(typeCodes)){
			//使用类型type作为key
			String[] keys = typeCodes.split("#");
			List<PairVo> voList = provider.getMulitByTypeCode(typeCodes, parentCodes);
			//变成组map
			for(String key : keys){
				if(!result.containsKey(key)){
					List<PairVo> resultList = filterList(voList,key);
					result.put(key, resultList);
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 获取类型一致的内容 
	 * @param voList
	 * @param type
	 * @return
	 */
	private List<PairVo> filterList(List<PairVo> voList,String type){
		List<PairVo> result = new ArrayList<PairVo>();
		for(PairVo vo : voList){
			if(vo.getType().equals(type)){
				result.add(vo);
			}
		}
		return result;
	}
	
	/**
	 * 功能：根据编码字符串（逗号分割）查询对象
	 * @param typeCode
	 * @param code
	 * @return
	 */
	public List<PairVo> getByCode(String typeCodes,String codes){
		return provider.getTypeCodes(typeCodes, codes);
	}
	
	/**
	 * 功能：查询多级分类代码
	 * @param typeCode
	 * @param parentCode
	 * @return
	 */
	public List<PairVo> getByTypeCode(String typeCode, String parentCode, String srv1){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", typeCode);
		params.put("srv1", srv1);
		if(StringUtils.isNotBlank(parentCode)){
			params.put("parentCode", parentCode);
		}else{
			params.put("parentCode", BizContant.BIZ_GUARANTEE_TYPE_ROOT_CODE);
		}
		return provider.getByParams(params);
	}
	
}
