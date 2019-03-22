package org.ibase4j.service;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class BizUserServiceTest {

    @Test
    public void updatePassword()
    {
        String account="chaojichaxun05";
        String password="qwe123";

        String newSalt = account+password;
        String newPasswordEncryption  = new SimpleHash("MD5", password, newSalt, 2).toString();
        System.out.println(newPasswordEncryption);
    }
}
