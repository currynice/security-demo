package com.cxy.entity;


import cn.hutool.core.util.StrUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author chengxinyu
 * 不想自己定义可以用这个 {@link org.springframework.security.core.userdetails} 参考
 *  逻辑层
 * @description UserDTO
 * @date 2019年06月05日9:51
 **/

public class UserDTO implements UserDetails {

    private long uid;

    private String username;

    private String password;

    //是否可用[0不可用，1可用]默认1
    private boolean enabled;

    List<Role> roles;


    public long getUid() {
        return uid;
    }

    public UserDTO(UserInfoPO userInfoPO) {
        this.uid = userInfoPO.getUid();
        this.username = userInfoPO.getUsername();
        this.password = userInfoPO.getPassword();
        this.enabled = userInfoPO.isEnabled();
        this.roles = userInfoPO.getRoles();
    }



    //method from UserDetail
    //所有角色信息todo
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role:this.roles){
            String roleName = StrUtil.addPrefixIfNot(role.getRoleName(),"ROLE_");
            authorities.add(new SimpleGrantedAuthority(roleName));
        }
        return authorities;
    }

    //获取对象密码
    //不匹配throw BadCredentialsException
    @Override
    public String getPassword() {
        return password;
    }

    //获取对象名字
    @Override
    public String getUsername() {
        return username;
    }

    //账号未过期(enabled为true)
    //false 返回AccountExpiredException
    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    //账号未锁定(enabled为true)
    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    //凭证未过期(enabled为true)
    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
