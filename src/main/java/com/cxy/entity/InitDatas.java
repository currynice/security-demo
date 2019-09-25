package com.cxy.entity;

import com.cxy.repository.PermissionRepository;
import com.cxy.repository.RoleRepository;
import com.cxy.repository.UserInfoRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * state :关闭（防止重复执行）
 * @author chengxinyu
 * @version 1.1.0
 * @description InitDatas 初始化,数据库插入，定义角色
 * attention:保存多对多或一对多之类的映射关系，关联的表的相关对象都要执行save操作
 * TODO name都是英文，没有使用code,未考虑唯一性，权限
 * @date 2019年06月05日
 **/
@Component
public class InitDatas {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PermissionRepository permissionRepository;

    //加密
    @Autowired
    @Qualifier("BCEncryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Bean
    public InitializingBean initDatebase(){
        return ()->{
            Role adminRole= new Role("ADMIN","系统管理员");
            Role dbaRole= new Role("DBA","数据库管理员");
            Role userRole = new Role("USER","用户");
            //roleRepository.saveAll(Arrays.asList(adminRole,dbaRole,userRole));

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

           // userInfoRepository.saveAll(Arrays.asList(userRoot,userDBA,userUser));



            Permission adminP = new Permission();
            adminP.setUrl("/admin/**");
            adminP.setName("admin:P");
            adminP.setDescription("管理员的访问路径");
            adminP.setSuperId(-1);
            adminP.setRoles(rootRoles);


            Permission dbP = new Permission();
            dbP.setUrl("/db/**");
            dbP.setName("db:P");
            dbP.setDescription("DBA的访问路径");
            dbP.setSuperId(-1);
            dbP.setRoles(dbaRoles);

            Permission userP = new Permission();
            userP.setUrl("/user/**");
            userP.setName("user:P");
            userP.setDescription("user的访问路径");
            userP.setSuperId(-1);
            userP.setRoles(userRoles);
           // permissionRepository.saveAll(Arrays.asList(adminP,dbP,userP));
        };
    }


}
