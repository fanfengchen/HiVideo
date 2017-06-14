package com.ebeijia.robot.core.pojo;

import java.io.Serializable;
import java.util.Date;

public class TRole implements Serializable {
    private Integer roleId;

    private String name;

    private Date lastTime;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}