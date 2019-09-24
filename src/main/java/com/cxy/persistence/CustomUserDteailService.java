package com.cxy.persistence;

import com.cxy.entity.Role;
import com.cxy.entity.UserInfo;
import com.cxy.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description CustomUserDteail 动态加载
 * @date 2019年06月05日10:13
 **/
//@Component
public class CustomUserDteailService implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;


    /**
     * 动态加载用户权限信息
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailService.loadUserByUsername()");
       UserInfo userInfo = userInfoService.findByUsername(userName);
        if(userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }
        List<GrantedAuthority>  authorities = new ArrayList<>();
        for(Role role:userInfo.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

       User userDteails = new User(userInfo.getUserName(),userInfo.getPassword(),authorities);
       return userDteails;
    }


}
