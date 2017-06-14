package com.ebeijia.robot.core.pojo;

import java.io.Serializable;
import java.util.Date;

public class TRoleAcc extends TRoleAccKey implements Serializable {
    private String accName;

    private Date lastTime;

    private static final long serialVersionUID = 1L;

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}