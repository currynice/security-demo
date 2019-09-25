package com.cxy.userDetailService;


import com.cxy.entity.UserDTO;
import com.cxy.entity.UserInfoPO;
import com.cxy.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
/**
 * @author chengxinyu
 * @version 1.1.0
 * @description CustomUserDteail 动态加载
 * @date 2019年06月05日10:13
 **/
@Component
@Slf4j(topic = "Logger")
public class CustomUserDteailService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;



    /**
     * 动态加载用户权限信息,登录时自动调用
     * @param userName 登陆时输入的用户名，去数据库匹配,找不到抛异常，找到了返回，进行密码匹配
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("CustomUserDetailService.loadUserByUsername()");
       UserInfoPO userInfo = userInfoRepository.findUserInfoPOByUsername(userName);
        if(userInfo == null) {
            throw new UsernameNotFoundException("not found");
        }
        //authorities在UserDTO中override过了
        UserDTO userDTO = new UserDTO(userInfo);
       return userDTO;
    }


}
