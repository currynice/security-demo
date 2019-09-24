package com.cxy.entity;

import com.cxy.repository.PermissionRepository;
import com.cxy.repository.RoleRepository;
import com.cxy.repository.UserInfoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description InitDatas 初始化,
 * TODO name都是英文，没有使用code,未考虑唯一性
 * @date 2019年06月05日
 **/
//@Component
public class InitDatas {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

    @Bean
    public InitializingBean initDatebase(){
        return ()->{


            Role adminRole= new Role("admin","管理员角色");
            Role normalRole = new Role("normal","员工角色");
            roleRepository.save(adminRole);
            roleRepository.save(normalRole);

            List<Role> bossRoles = Arrays.asList(adminRole,normalRole);
            UserInfo userBoss = new UserInfo();
            userBoss.setUserName("boss");
            userBoss.setPassword(passwordEncoder.encode("12345"));
            userBoss.setRoles(bossRoles);

            List<Role> employeeRoleList = Collections.singletonList(normalRole);
            UserInfo userEmployee = new UserInfo();
            userEmployee.setUserName("employee1");
            userEmployee.setPassword(passwordEncoder.encode("12345"));
            userEmployee.setRoles(employeeRoleList);

            userInfoRepository.save(userBoss);
            userInfoRepository.save(userEmployee);


            Permission permission1 = new Permission();
            permission1.setUrl("/notice");
            permission1.setName("manage");
            permission1.setDescription("管理员的访问路径");
            permission1.setRoles(bossRoles);
            permissionRepository.save(permission1);

            Permission permission2 = new Permission();
            permission2.setUrl("/view");
            permission2.setName("view");
            permission2.setDescription("员工的访问路径");

            permission2.setRoles(employeeRoleList);
            permissionRepository.save(permission2);
        };
    }


}
