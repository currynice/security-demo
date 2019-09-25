package com.cxy.service;


import com.cxy.entity.Permission;
import com.cxy.entity.Role;
import com.cxy.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description PermissionService
 * @date 2019年06月05日16:21
 **/
@Service
@Slf4j(topic = "Logger")
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    Map<RequestMatcher,Collection<ConfigAttribute>> result= null;

    /**
     * 1.只加载一次
     * 2.该方法不能抛异常
     * 3.为什么不用构造方法？需要使用依赖注入的bean,使用@PostConstruct来完成初始化,在依赖注入后调用
     * todo 缓存实现
     */
    @PostConstruct
    public void initPermission(){
        result = new HashMap<>();
        ConfigAttribute cfg;
        Collection<ConfigAttribute> collection;
        List<Permission> permissions = permissionRepository.findAll();
        for(Permission p:permissions){
            collection = new ArrayList<>();
            for(Role role:p.getRoles()){
                cfg = new SecurityConfig("ROLE_"+role.getRoleName());
                collection.add(cfg);
            }
            result.put(new AntPathRequestMatcher(p.getUrl()),collection);
        }
        log.info("初始化权限关系"+result);

    }


    /**
     * like "key:/notice value:[ROLE_admin, ROLE_normal]"
     * @return
     */
    public Map<RequestMatcher,Collection<ConfigAttribute>> getPermissionMap(){
        if(result.size()==0){
            initPermission();
        }
        return result;
    }
}
