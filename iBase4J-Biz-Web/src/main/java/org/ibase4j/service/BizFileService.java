package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.model.BizFile;
import org.ibase4j.provider.BizDebtGrantProvider;
import org.ibase4j.provider.BizDebtSummaryProvider;
import org.ibase4j.provider.BizFileProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
public class BizFileService extends BaseService<BizFileProvider,BizFile> {

    @Reference
    public void setBizFileProvider(BizFileProvider provider) {
        this.provider = provider;
    }
    @Reference
    private BizDebtSummaryProvider debtMainProvider;
    @Reference
    private BizDebtGrantProvider debtGrantProvider;


    public List<BizFile> getFileList(Map<String, Object> params ,boolean asc) throws UnsupportedEncodingException {

        String bizcode = StringUtil.objectToString(params.get("bizCode"));
        String bizType = StringUtil.objectToString(params.get("bizType"));
        //xxx 此处变更号应由前端传递
        if("A".equals(bizType)){
            if(bizcode.length()==13){
                BizDebtSummary selDebt = new BizDebtSummary();
                selDebt.setDebtCode(bizcode);
                BizDebtSummary lastDebt = debtMainProvider.selectOne(selDebt);
                bizcode = bizcode+lastDebt.getVerNumStr();
            }
        }else if("B".equals(bizType)){
            if(bizcode.length()==16){
                BizDebtGrant selGrant = new BizDebtGrant();
                selGrant.setGrantCode(bizcode);
                BizDebtGrant lastGrant = debtGrantProvider.selectOne(selGrant);
                bizcode = bizcode+lastGrant.getVerNumStr();
            }
        }

        EntityWrapper<BizFile> sel = new EntityWrapper<BizFile>();
        if (null != params.get("bizCode")){
            sel.eq("BIZ_CODE",bizcode);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(StringUtil.objectToString(params.get("bizType"))) ){
            sel.eq("BIZ_TYPE",bizType);
        }
        if (null != params.get("product")){
            sel.eq("PRODUCT_",StringUtil.objectToString(params.get("product")));
        }
        if (null != params.get("fieldName")){
            sel.eq("FIELD_NAME",StringUtil.objectToString(params.get("fieldName")));
        }
        if (null != params.get("realName")){

            sel.eq("REAL_NAME",StringUtil.objectToString(params.get("realName")));
        }
        if (null != params.get("fileType")){
            sel.eq("FILE_TYPE",StringUtil.objectToString(params.get("fileType")));
        }
        sel.orderBy("CREATE_TIME",asc);

        return provider.selectList(sel);

    }

    public BizFile getFile(Map<String, Object> params){
        BizFile sel = new BizFile();
        if (null != params.get("bizCode")){
            sel.setBizCode(StringUtil.objectToString(params.get("bizCode")));
        }
        if (null != params.get("bizType")){
            sel.setBizType(StringUtil.objectToString(params.get("bizType")));
        }
        if (null != params.get("product")){
            sel.setProduct(StringUtil.objectToString(params.get("product")));
        }
        if (null != params.get("fieldName")){
            sel.setFieldName(StringUtil.objectToString(params.get("fieldName")));
        }
        if (null != params.get("fileName")){
            sel.setFileName(StringUtil.objectToString(params.get("fileName")));
        }
        if (null != params.get("fileType")){
            sel.setFileType(StringUtil.objectToString(params.get("fileType")));
        }

        return provider.selectOne(new EntityWrapper<>(sel));

    }

    public boolean saveFile(BizFile file){
        return provider.saveFile(file);
    }
    public void delFile(Long id){
        provider.delete(id);
    }


}
