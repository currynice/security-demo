package com.cxy.entity;

import com.cxy.repository.RoleRepository;
import com.cxy.repository.UserInfoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * state :关闭（防止重复执行）
 * @author chengxinyu
 * @version 1.1.0
 * @description InitDatas 初始化,数据库插入，定义角色
 * TODO name都是英文，没有使用code,未考虑唯一性，权限
 * @date 2019年06月05日
 **/
//@Component
public class InitDatas {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;


//    @Autowired
//    private PermissionRepository permissionRepository;

    //加密
    @Autowired
    @Qualifier("BCEncryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

   // @Bean
    public InitializingBean initDatebase(){
        return ()->{
            Role adminRole= new Role("ADMIN","系统管理员");
            Role dbaRole= new Role("DBA","数据库管理员");
            Role userRole = new Role("USER","用户");
            roleRepository.saveAll(Arrays.asList(adminRole,dbaRole,userRole));

            List<Role> rootRoles = Arrays.asList(adminRole,dbaRole);
            UserInfoPO userRoot = new UserInfoPO();
            userRoot.setUsername("root");
            userRoot.setPassword(passwordEncoder.encode("123"));
            userRoot.setRoles(rootRoles);
            userRoot.setEnabled(true);

            List<Role> dbaRoles = Collections.singletonList(dbaRole);
            UserInfoPO userDBA = new UserInfoPO();
            userDBA.setUsername("cxy");
            userDBA.setPassword(passwordEncoder.encode("123"));
            userDBA.setRoles(dbaRoles);
            userDBA.setEnabled(true);

            List<Role> userRoles = Collections.singletonList(userRole);
            UserInfoPO userUser= new UserInfoPO();
            userUser.setUsername("xpy");
            userUser.setPassword(passwordEncoder.encode("123"));
            userUser.setRoles(userRoles);
            userUser.setEnabled(true);

            userInfoRepository.saveAll(Arrays.asList(userRoot,userDBA,userUser));



//            Permission permission1 = new Permission();
//            permission1.setUrl("/notice");
//            permission1.setName("manage");
//            permission1.setDescription("管理员的访问路径");
//            permission1.setRoles(bossRoles);
//            permissionRepository.save(permission1);
//
//            Permission permission2 = new Permission();
//            permission2.setUrl("/view");
//            permission2.setName("view");
//            permission2.setDescription("员工的访问路径");
//
//            permission2.setRoles(employeeRoleList);
//            permissionRepository.save(permission2);
        };
    }


}
