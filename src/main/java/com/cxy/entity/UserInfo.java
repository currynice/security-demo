package com.cxy.entity;


import javax.persistence.*;
import java.util.List;


/**
 * @author chengxinyu
 * @version 1.1.0
 * @description UserInfo
 * @date 2019年06月05日9:51
 **/
@Entity
public class UserInfo {


    @Id
    @GeneratedValue
    private long uid;

    private String userName;

    private String password;

//    @Enumerated(value = EnumType.STRING)
//    private Role role;


    @ManyToMany(fetch=FetchType.EAGER)//立即加载
    @JoinTable(name = "UserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    List<Role> roles;

    public long getId() {
        return uid;
    }

    public void setId(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
