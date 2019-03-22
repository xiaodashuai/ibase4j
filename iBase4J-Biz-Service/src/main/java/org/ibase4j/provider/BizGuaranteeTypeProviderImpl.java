/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.CollectionUtil;
import org.ibase4j.mapper.BizGuaranteeTypeMapper;
import org.ibase4j.model.BizGuaranteeType;
import org.ibase4j.vo.PairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：担保类型表 
 * 日期：2018/7/17
 * @author czm
 * @version 1.0
 */
@CacheConfig(cacheNames = "BizGuaranteeType")
@Service(interfaceClass = BizGuaranteeTypeProvider.class)
public class BizGuaranteeTypeProviderImpl extends BaseProviderImpl<BizGuaranteeType> implements BizGuaranteeTypeProvider {
	private static final Logger log = LogManager.getLogger(BizGuaranteeTypeProviderImpl.class);
	
	@Autowired
	private BizGuaranteeTypeMapper bizGuaranteeTypeMapper;

	@Override
	public List<PairVo> getByTypeCode(String typeCode, String parentCode) {
		List<PairVo> result = new ArrayList<PairVo>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", typeCode);
		if(StringUtils.isNotBlank(parentCode)){
			params.put("parentCode", parentCode);
		}else{
			params.put("parentCode", BizContant.BIZ_GUARANTEE_TYPE_ROOT_CODE);
		}
		List<BizGuaranteeType> typeList = this.queryList(params);
		for(BizGuaranteeType type: typeList){
			PairVo vo = new PairVo();
			vo.setChildren(type.getChildren()==1);
			vo.setCode(type.getCode());
			vo.setName(type.getName());
			vo.setParentCode(type.getParentCode());
			
			result.add(vo);
		}
		return result;
	}

	@Override
	public List<PairVo> getByParams(Map<String, Object> params) {
		List<PairVo> result = new ArrayList<PairVo>();
		List<BizGuaranteeType> typeList = this.queryList(params);
		for(BizGuaranteeType type: typeList){
			PairVo vo = new PairVo();
			vo.setChildren(type.getChildren()==1);
			vo.setCode(type.getCode());
			vo.setName(type.getName());
			vo.setParentCode(type.getParentCode());
			
			result.add(vo);
		}
		return result;
	}

	@Override
	public List<PairVo> getTypeCodes(String typeCode, String codes) {
		List<PairVo> result = new ArrayList<PairVo>();
		if (StringUtils.isNotBlank(typeCode) && StringUtils.isNotBlank(codes)) {
			String[] types = StringUtils.split(typeCode, '#');
			String[] code = StringUtils.split(codes, '#');
			for (int i = 0; i < types.length; i++) {
				BizGuaranteeType param = new BizGuaranteeType();
				param.setCode(code[i]);
				param.setType(types[i]);
				BizGuaranteeType type = this.selectOne(param);
				if (type != null) {
					PairVo vo = new PairVo();
					vo.setChildren(type.getChildren() == 1);
					vo.setCode(type.getCode());
					vo.setName(type.getName());
					vo.setParentCode(type.getParentCode());
					result.add(vo);
				}
			}
		}
		return result;
	}

	@Override
	public PairVo getByCode(String code,String type) {
		BizGuaranteeType query = new BizGuaranteeType();
		query.setCode(code);
		query.setType(type);
		BizGuaranteeType model = this.selectOne(query);
		PairVo vo = new PairVo();
		if(model != null){
			vo.setCode(code);
			vo.setName(model.getName());
			vo.setParentCode(model.getParentCode());
			vo.setChildren(Integer.valueOf(1).equals(model.getChildren()));
		}
		return vo;
	}

	@Override
	public List<PairVo> getMulitByTypeCode(String typeCodes, String parentCodes) {
		if(StringUtils.isNotBlank(typeCodes)&&StringUtils.isNotBlank(parentCodes)){
			String[] types = typeCodes.split("#");
			List<String> typeSql = CollectionUtil.convertStringArraysToList(types);
			String[] pCodes = parentCodes.split("#");
			List<String> codeSql = CollectionUtil.convertStringArraysToList(pCodes);
			log.info("typeSql:"+typeSql+"----codeSql:"+codeSql);
			//执行in查询
			return bizGuaranteeTypeMapper.queryByType(typeSql, codeSql);
		}
		return null;
	}
}
