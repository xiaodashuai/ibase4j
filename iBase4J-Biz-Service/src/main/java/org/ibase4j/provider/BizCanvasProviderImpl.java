package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.mapper.BizCanvasMapper;
import org.ibase4j.model.BizCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.ibase4j.core.Constants;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;

/**
 * 快照服务实现类
 */
@CacheConfig(cacheNames = "BizCanvas")
@Service(interfaceClass = CanvasProvider.class)
public class BizCanvasProviderImpl extends BaseProviderImpl<BizCanvas> implements CanvasProvider {


    @Autowired
    private BizCanvasMapper bizCanvasMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savCanvas(String imgStr,BizCanvas canvas) {

        boolean res = false;
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        canvas.setFilename(uuid);
        String filePath = PropertiesUtil.getString("canvas.dir")  + uuid + ".b64";
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] result = this.encrypt(imgStr.getBytes(),Constants.canvasSalt);
            out.write(result);
            out.flush();
            out.close();
            this.update(canvas);
            res = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return res;
    }

    @Override
    public List<String> queryImgList(Map<String, Object> params) {

        logger.debug("CanvasProvider-CanvasProvider："+params);

        List<BizCanvas> bizCanvas = bizCanvasMapper.selectCanvasList(params);
        List<String> imgStrlst = new ArrayList<String>();
        FileInputStream in = null;
        byte[] filecontent = null;
        byte[] decryResult = null;
        for (BizCanvas canvas: bizCanvas) {

            String imgFilePath = PropertiesUtil.getString("canvas.dir")  + canvas.getFilename() + ".b64";

            try {
                File file = new File(imgFilePath);
                Long filelength = file.length();
                filecontent = new byte[filelength.intValue()];
                in = new FileInputStream(file);
                in.read(filecontent);
                decryResult = this.decrypt(filecontent, Constants.canvasSalt);

            } catch (IOException e) {
                e.printStackTrace();
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
            if(decryResult!=null){
                imgStrlst.add(new String(decryResult));
            }
        }
        return imgStrlst;
    }

    /**
     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    private  byte[] encrypt(byte[] datasource, String password) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象,ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource); //按单部分操作加密或解密数据，或者结束一个多部分操作
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    private  byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//返回实现指定转换的 Cipher 对象
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}
