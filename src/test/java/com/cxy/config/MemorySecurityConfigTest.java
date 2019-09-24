package com.cxy.config;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemorySecurityConfigTest {

    @Autowired
    @Qualifier("BCEncryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Test
    public void getBCEncryptPsd() {
        for(int i=0;i<10;i++){
            System.out.println(StrUtil.format("第{}次:",i+1));
            String encodedPassword = passwordEncoder.encode("123");
            System.out.println(encodedPassword);
            System.out.println(encodedPassword.length());
            System.out.println("\n");
        }


    }
}