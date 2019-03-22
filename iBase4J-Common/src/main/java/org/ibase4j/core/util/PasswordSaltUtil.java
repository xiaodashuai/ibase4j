package org.ibase4j.core.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @program: iBase4J
 * @description: 密码加密 加盐工具类
 * @author: lzy
 * @create: 2018-11-04 18:54
 */
public class PasswordSaltUtil {

    /**
     * 对密码加盐 二次迭代
     * @param password  待加密的密码
     * @param saltString  盐值
     * @return
     */
    public static String passwordSaltMD5Two(Object password,String saltString){
        ByteSource salt = ByteSource.Util.bytes(saltString);//以账号+密码 作为盐值
        return  new SimpleHash("MD5",password,salt,2).toString();
    }
}
