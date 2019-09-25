package com.cxy.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 很多地方都用到了，<strong>只能定义一个</strong>
 */
@Component
public class PasswordEncoderConfig {

    /**
     * 必须指定加密方式,测试
     * {@link PasswordEncoder} 也可以自己实现
     * @return
     */
//    @Bean(name = "testPasswordEncoder")
//    public PasswordEncoder noEncrypt(){
//       return NoOpPasswordEncoder.getInstance();
//    }

    /**
     * BCypt强哈希加密,相当于随机盐 也放在了密码里,方便安全,省掉维护salt字段和迭代次数的烦恼
     * strength :迭代次数(4-31,默认10)
     * {@link PasswordEncoder} 也可以自己实现
     * @return
     */
    @Bean(name = "BCEncryptPasswordEncoder")
    public PasswordEncoder BCEncrypt(){
        return new BCryptPasswordEncoder(10);
    }
}
