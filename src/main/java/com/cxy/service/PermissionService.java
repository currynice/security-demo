//package com.cxy.service;/**
// * Created by Administrator on 2019/6/5/005.
// */
//
//import com.cxy.entity.Permission;
//import com.cxy.entity.Role;
//import com.cxy.repository.PermissionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
///**
// * @author chengxinyu
// * @version 1.1.0
// * @description PermissionService
// * @date 2019年06月05日16:21
// **/
//@Service
//public class PermissionService {
//
//    @Autowired
//    PermissionRepository permissionRepository;
//
//    Map<RequestMatcher,Collection<ConfigAttribute>> result= null;
//
//    @PostConstruct
//    public void initPermission(){
//        result = new HashMap<>();
//        ConfigAttribute cfg;
//        Collection<ConfigAttribute> collection;
//        List<Permission> permissions = permissionRepository.findAll();
//        for(Permission p:permissions){
//            collection = new ArrayList<>();
//            for(Role role:p.getRoles()){
//                cfg = new SecurityConfig("ROLE_"+role.getRoleName());
//                collection.add(cfg);
//            }
//            result.put(new AntPathRequestMatcher(p.getUrl()),collection);
//        }
//        System.out.println(result);
//
//    }
//
//
//    /**
//     * like "/notice=[ROLE_admin, ROLE_normal]"
//     * @return
//     */
//    public Map<RequestMatcher,Collection<ConfigAttribute>> getPermissionMap(){
//        if(result.size()==0){
//            initPermission();
//        }
//        return result;
//    }
//}
