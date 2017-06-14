package com.ebeijia.robot.core.pojo;

import java.io.Serializable;

public class TRoleAccKey implements Serializable {
    private String accId;

    private String roleId;

    private static final long serialVersionUID = 1L;

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}