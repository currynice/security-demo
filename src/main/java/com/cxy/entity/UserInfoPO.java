package com.cxy.entity;


import javax.persistence.*;
import java.util.List;


/**
 * @author chengxinyu,只用来 存取
 * @version 1.1.0
 * @description UserInfoPO
 * @date 2019年06月05日9:51
 **/
@Entity
@Table(name = "user_info")
public class UserInfoPO {


    @Id
    @GeneratedValue
    private long uid;

    private String username;

    private String password;

    //是否可用[0不可用，1可用]默认1
    private boolean enabled;


    @ManyToMany(fetch=FetchType.EAGER)//立即加载
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    List<Role> roles;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
