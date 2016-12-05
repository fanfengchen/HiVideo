package com.ebeijia.dto.webService;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by YJ on 2016/11/1.
 */
public class UserInfo {

    @XStreamAlias("USER_NO")
    private String userNo;

    @XStreamAlias("USER_NAME")
    private String userName;

    @XStreamAlias("ADDRESS")
    private String address;

    @XStreamAlias("WRITE_SECT_NO")
    private String writeSectNo;



    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWriteSectNo() {
        return writeSectNo;
    }

    public void setWriteSectNo(String writeSectNo) {
        this.writeSectNo = writeSectNo;
    }
}
