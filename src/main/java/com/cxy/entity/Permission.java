package com.cxy.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author chengxinyu TODO url为 xxx.html检验
 * @version 1.1.0
 * @description Permission
 * @date 2019年06月05日15:59
 **/
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    //权限名称.
    private String name;

    //权限描述.
    private String description;
    /**
     *  注意：Permission 表的url通配符为两颗星，比如说 /user下的所有url，应该写成 /user/**;
     */
    //授权链接
    private String url;

    //父节点id.
    private long superId;


    // 角色 - 权限是多对多的关系
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns= {@JoinColumn(name="permission_id")} , inverseJoinColumns= {@JoinColumn(name="role_id")})
    private List<Role> roles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSuperId() {
        return superId;
    }

    public void setSuperId(long superId) {
        this.superId = superId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
