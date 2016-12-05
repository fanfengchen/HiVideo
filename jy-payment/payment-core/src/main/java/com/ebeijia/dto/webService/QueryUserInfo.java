package com.ebeijia.dto.webService;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Created by YJ on 2016/11/1.
 */
@XStreamAlias("NewDataSet")
public class QueryUserInfo {

    @XStreamAlias("Table")
    public UserInfo userInfo;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
