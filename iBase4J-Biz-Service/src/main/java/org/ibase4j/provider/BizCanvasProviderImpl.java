package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizCanvasMapper;
import org.ibase4j.mapper.BizDebtGrantMapper;
import org.ibase4j.mapper.BizDebtSummaryMapper;
import org.ibase4j.model.BizCanvas;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizDebtSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.util.*;

/**
 * 快照服务实现类
 */
@CacheConfig(cacheNames = "BizCanvas")
@Service(interfaceClass = CanvasProvider.class)
public class BizCanvasProviderImpl extends BaseProviderImpl<BizCanvas> implements CanvasProvider {


    @Autowired
    private BizCanvasMapper bizCanvasMapper;
    @Autowired
    private BizDebtSummaryMapper bizDebtSummaryMapper;
    @Autowired
    private BizDebtGrantMapper bizDebtGrantMapper;
    @Autowired
    private BizCntProvider bizCntProvider;

    String basePath = PropertiesUtil.getString("canvas.dir");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savCanvas(String imgStr,BizCanvas canvas) {

        boolean res = false;
        Long uuid = IdWorker.getId();
        canvas.setId(uuid);
        canvas.setFilename(uuid+"");
        canvas.setVerNum(bizCntProvider.getNextNumber(canvas.getBizcode()+canvas.getType()+canvas.getNum()));
        if(null == canvas.getCreateTime()){
            Date creDat = new Date();
            canvas.setCreateTime(creDat);
            canvas.setUpdateTime(creDat);
        }
        String filePath = basePath + "/" + canvas.getBizcode() + "/" + canvas.getCreateBy() + "/" + DateUtil.formatYYYYMMDD(canvas.getCreateTime())+ "/" + uuid + ".b64";
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        OutputStream out = null;
        try {
            LZ4Factory factory = LZ4Factory.fastestInstance();
            byte[] strData = imgStr.getBytes("UTF-8");
            canvas.setExtra(strData.length+"");
            LZ4Compressor compressor = factory.fastCompressor();
            out = new FileOutputStream(file);
            out.write(compressor.compress(strData));
            out.flush();
            out.close();
            bizCanvasMapper.insert(canvas);
            res = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if(null!=out){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCanvas(Map<String, Object> params){

        String bizCode =  StringUtil.objectToString(params.get("bizcode"));
        String type =  StringUtil.objectToString(params.get("type"));

        if( ("A".equals(type) && bizCode.length()==16) || ("B".equals(type) && bizCode.length()==19)){
            Wrapper<BizCanvas> wrapper = new EntityWrapper<BizCanvas>();
            wrapper.eq("BIZ_CODE",bizCode).eq("TYPE_",type);
            List<BizCanvas>  canvasList = bizCanvasMapper.selectList(wrapper);
            if(null != canvasList){
                for (BizCanvas canvas : canvasList){
                    File sourcefile = new File(basePath + "/" + canvas.getBizcode() + "/" + canvas.getCreateBy() + "/" + DateUtil.formatYYYYMMDD(canvas.getCreateTime())+ "/" + canvas.getFilename() + ".b64");
                    if (sourcefile.exists() && sourcefile.isFile()) {
                       boolean res = sourcefile.delete();
                        if(res){
                            int i = bizCanvasMapper.deleteById(canvas.getId());
                            if(i == 1){
                                logger.debug("delete canvas,details= " + canvas.toString());
                            }else{
                                logger.error("delCanvas:del database Error! effects rows="+i);
                            }
                        }else{
                            logger.error("delCanvas:del file Error! sourcefile="+sourcefile.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> queryImgList(Map<String, Object> params) {

        if(null != params && null != params.get("type")){

            String bizType = (String)params.get("type");
            String bizcode = (String)params.get("bizcode");
            if("A".equals(bizType)){
                if(bizcode.length()==13){
                    BizDebtSummary selDebt = new BizDebtSummary();
                    selDebt.setDebtCode(bizcode);
                    BizDebtSummary lastDebt = bizDebtSummaryMapper.selectOne(selDebt);
                    params.put("bizcode",bizcode+lastDebt.getVerNumStr());
                }
            }else if("B".equals(bizType)){
                if(bizcode.length()==16){
                    BizDebtGrant selGrant = new BizDebtGrant();
                    selGrant.setGrantCode(bizcode);
                    BizDebtGrant lastGrant = bizDebtGrantMapper.selectOne(selGrant);
                    params.put("bizcode",bizcode+lastGrant.getVerNumStr());
                }
            }
        }

        BizCanvas selCanvas = new BizCanvas();
        selCanvas.setBizcode((String) params.get("bizcode"));
        selCanvas.setType((String)params.get("type"));
        selCanvas.setNum(1);
        selCanvas.setVerNum(1);

        List<BizCanvas> bizCanvas = null;
        if(null != bizCanvasMapper.selectOne(selCanvas)){
            bizCanvas = bizCanvasMapper.selectCanvasList(params);
        }else{
            bizCanvas = bizCanvasMapper.selectCanvasListOld(params);
        }

        List<String> imgStrlst = new ArrayList<String>();

        if(null != bizCanvas){

            FileInputStream in = null;
            byte[] filecontent = null;

            LZ4Factory factory = LZ4Factory.fastestInstance();
            LZ4FastDecompressor decompressor = factory.fastDecompressor();
            String basePath = PropertiesUtil.getString("canvas.dir");

            for (BizCanvas canvas: bizCanvas) {
                String imgFilePath = basePath + canvas.getBizcode() + "/" + canvas.getCreateBy() + "/" + DateUtil.formatYYYYMMDD(canvas.getCreateTime()) + "/" + canvas.getFilename() + ".b64";
                try {
                    File file = new File(imgFilePath);
                    Long filelength = file.length();
                    filecontent = new byte[filelength.intValue()];
                    in = new FileInputStream(file);
                    in.read(filecontent);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(null!=in){
                        try {
                            in.close();
                            in = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //添加不为空判断
                if(filecontent!=null){
                    if(null!=canvas.getVerNum()){
                        imgStrlst.add(new String(decompressor.decompress(filecontent,Integer.parseInt(canvas.getExtra()))));
                        filecontent = null;
                    }else{
                        try {
                            imgStrlst.add(new String(StringUtil.decrypt(filecontent,Constants.canvasSalt)));
                        } catch (Exception e) {
                            logger.error("查看旧版本快照异常=="+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if(imgStrlst.size()==0){
            logger.error("快照查询结果集为空。CanvasProvider-params："+params);
        }
        return imgStrlst;
    }

}
