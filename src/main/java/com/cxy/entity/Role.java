package com.cxy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description Role
 * @date 2019年06月05日14:12
 **/
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long rid;

    //角色名称
    private String roleName;

    //角色描述
    private String description;

    public Role() {
       super();
    }

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
