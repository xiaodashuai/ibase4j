package org.ibase4j.web;

import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizFile;
import org.ibase4j.service.BizFileService;
import org.ibase4j.vo.FileNameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping(value = "/upload", method = RequestMethod.POST)
public class FileUpLoadController extends BaseController {

    String filePath = PropertiesUtil.getString("uploader.dir");
    @Autowired
    private BizFileService bizFileService;

    @ApiOperation(value = "文件上传")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public Object saveUploadFile(ModelMap modelMap, @RequestParam(value = "file", required = false)
                                 MultipartFile[] files,HttpServletRequest params) throws Exception {

        String bizCode =  StringUtil.objectToString(params.getParameter("bizCode"));
        String bizType =  StringUtil.objectToString(params.getParameter("bizType"));
        String product =  StringUtil.objectToString(params.getParameter("product"));
        String fieldName =  StringUtil.objectToString(params.getParameter("fieldName"));
        logger.debug(bizCode+"--"+ bizType+"--"+ product+"--"+ fieldName);
        FileOutputStream fos = null;
        InputStream in = null;
        try {
            for (MultipartFile file : files) {
                logger.debug("=====saveUploadFile=====开始解析文件列表=file="+file.getOriginalFilename());
                BizFile bizFile = new BizFile();
                String newFileName = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//                String realName = URLEncoder.encode(file.getOriginalFilename(),"utf-8");
                String realName=file.getOriginalFilename();
                bizFile.setRealName(realName);
                bizFile.setFileName(newFileName);
                bizFile.setBizCode(bizCode);
                bizFile.setBizType(bizType);
                bizFile.setProduct(product);
                bizFile.setFileType(file.getContentType());
                bizFile.setFieldName(fieldName);
                bizFile.setSize_(file.getSize());
                String ext = realName.substring(realName.lastIndexOf(".") + 1);
                bizFile.setExt(ext);
                if(file.getContentType().contains("image")){
                    String thumbTempPath = filePath+"/temp/thumb/";
                    File thumbTemp = new File(thumbTempPath);
                    if (!thumbTemp.exists())
                    {thumbTemp.mkdirs();}
                    //生成缩略图并转换为base64存储
                    String thumbFilePath = thumbTempPath+UUID.randomUUID().toString().replace("-", "").toLowerCase()+"."+ext;
                    Thumbnails.of(file.getInputStream()).forceSize(110, 110).toFile(thumbFilePath);
                    String imageBase64 = "data:image/"+file.getContentType().split("/")[1]+";base64,"+ imageToBase64(thumbFilePath);
                    bizFile.setURL(imageBase64);

                }
                logger.debug("======saveUploadFile=====保存文件==bizFile="+bizFile);
                if(bizFileService.saveFile(bizFile)){
                    logger.debug("======saveUploadFile====生成新文件==bizFile="+newFileName);
                    String path =filePath +"/";
                    File f = new File(path);
                    if (!f.exists())
                    {f.mkdirs();}
                    if (!file.isEmpty()) {
                        fos = new FileOutputStream(path + newFileName);
                        in = file.getInputStream();
                        int b = 0;
                        while ((b = in.read()) != -1) {
                            fos.write(b);
                        }
                        fos.close();
                        in.close();
                        String[] cmd = new String[]{"sh","-c","chmod 777 "+ path + newFileName};
                        Process process = Runtime.getRuntime().exec(cmd);
                    }
                }else{
                    throw new RuntimeException("error save BizFile error!");
                }
            }
        } catch (Exception e) {
            if (fos != null) {
                fos.close();
            }
            if (in != null) {
                in.close();
            }
        }
        return setSuccessModelMap(modelMap);

    }

    @ApiOperation(value = "文件删除")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/deleteFile")
    @ResponseBody
    public Object delete(ModelMap modelMap,@RequestBody Map<String,Object> params) throws UnsupportedEncodingException {

        String bizCode =  StringUtil.objectToString(params.get("bizCode"));
        String bizType =  StringUtil.objectToString(params.get("bizType"));
        String product =  StringUtil.objectToString(params.get("product"));
        String fieldName =  StringUtil.objectToString(params.get("fieldName"));
//        String realName =  URLEncoder.encode(StringUtil.objectToString(params.get("realName")),"utf-8");
        String realName = StringUtil.objectToString(params.get("realName"));

        String fileType =  StringUtil.objectToString(params.get("fileType"));
        boolean res = false;

        List<BizFile> bizFileList = bizFileService.getFileList(params,false);
        BizFile bizFile = bizFileList == null?null:bizFileList.get(0);
        if(null!=bizFile){
            File sourcefile = new File(filePath+"/"+bizFile.getFileName());
            if (sourcefile.exists() && sourcefile.isFile()) {
                res = sourcefile.delete();
                if(res){
                    bizFileService.delete(bizFile.getId());
                }
            }
        }else{
            throw new RuntimeException("删除附件失败：未查询到已上传的附件!");
        }
        return setSuccessModelMap(modelMap,res);
    }

    @ApiOperation(value = "文件列表")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/getFileMenu")
    @ResponseBody
    public Object getFileMenu(ModelMap modelMap) {
        //File file = new File("E:/maojin/eximbank-ui/eximbank-club/eximbank-club-ui/lib/generic/web/abc");
        File file = new File(filePath);
        File[] fileList = file.listFiles();
        List<FileNameVo> fileNamesVo = new ArrayList<>();
        FileNameVo fileNameVo=null;
        for (File f:fileList) {
            fileNameVo = new FileNameVo();
            fileNameVo.setFileName(f.getName());
            fileNamesVo.add(fileNameVo);
        }
        return setSuccessModelMap(modelMap,fileNamesVo);
    }


    @ApiOperation(value = "查看预览")
    @PostMapping(value = "/getPreview")
    @ResponseBody
    public Object getPreview(ModelMap modelMap, @RequestBody Map<String, Object> params) throws UnsupportedEncodingException {

        String bizCode =  StringUtil.objectToString(params.get("bizCode"));
        String bizType =  StringUtil.objectToString(params.get("bizType"));
        String product =  StringUtil.objectToString(params.get("product"));
        String fieldName =  StringUtil.objectToString(params.get("fieldName"));
        String rN =  StringUtil.objectToString(params.get("realName"));
        String realName = URLDecoder.decode(rN, "UTF-8");
        String fileType =  StringUtil.objectToString(params.get("fileType"));
        logger.debug("--------------------------------------------------");
        logger.debug(bizCode+"--"+ bizType+"--"+ product+"--"+ fieldName+"--realName=="+realName+"----"+fileType);
        logger.debug("--------------------------------------------------");
        Object res = null;
        if(null != fileType){
            List<BizFile> bizFileList = bizFileService.getFileList(params,false);
            BizFile bizFile = bizFileList == null?null:bizFileList.get(0);
            if(null!=bizFile){
                if(fileType.contains("image")){
                    res = "data:image/"+fileType.split("/")[1]+";base64,"+imageToBase64(filePath+"/"+bizFile.getFileName());
                }else{
                    //生成临时文件提供预览（解决文件名问题）
                    File sourcefile = new File(filePath+"/"+bizFile.getFileName());
                    String targetDirPath = filePath+"/temp/"+bizType+"/"+bizCode+"/"+fieldName;
                    File targetDir = new File(targetDirPath);
                    if (!targetDir.exists())
                    {targetDir.mkdirs();}
                    File targetFile = new File(targetDirPath+"/"+realName);
                    try {
                        copyFile(sourcefile,targetFile);
                        res = "temp/"+bizType+"/"+bizCode+"/"+fieldName+"/"+realName;
                    } catch (IOException e) {
                        throw new RuntimeException("复制文件失败，无法预览!");
                    }
                }
            }else{
                throw new RuntimeException("获取预览失败：未查询到已上传的附件!");
            }

        }else{
            res = null;
        }
       return  setSuccessModelMap(modelMap, res);
    }

    public String imageToBase64(String imgFile) {

        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    public void copyFile(File sourcefile,File targetFile) throws IOException{

        //新建文件输入流并对它进行缓冲
        FileInputStream input=new FileInputStream(sourcefile);
        BufferedInputStream inbuff=new BufferedInputStream(input);

        //新建文件输出流并对它进行缓冲
        FileOutputStream out=new FileOutputStream(targetFile);
        BufferedOutputStream outbuff=new BufferedOutputStream(out);

        //缓冲数组
        byte[] b=new byte[1024*5];
        int len=0;
        while((len=inbuff.read(b))!=-1){
            outbuff.write(b, 0, len);
        }

        //刷新此缓冲的输出流
        outbuff.flush();

        //关闭流
        inbuff.close();
        outbuff.close();
        out.close();
        input.close();

    }

    @ApiOperation(value = "附件回显")
    @PostMapping(value = "/getFileList")
    @ResponseBody
    public List<BizFile> getFileList(ModelMap modelMap, @RequestBody Map<String, Object> params) throws UnsupportedEncodingException {
        String bizCode =  StringUtil.objectToString(params.get("bizCode"));
        String bizType =  StringUtil.objectToString(params.get("bizType"));
        String product =  StringUtil.objectToString(params.get("product"));
        String fieldName =  StringUtil.objectToString(params.get("fieldName"));
        List<BizFile> bizFileList = bizFileService.getFileList(params,false);

        logger.debug("----------------------------------------------");
        for (int i = 0; i < bizFileList.size(); i++) {
            logger.debug(bizFileList.get(i).toString());
        }
        logger.debug("----------------------------------------------");
        return bizFileList;
    }

}

