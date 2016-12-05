package com.ebeijia.entity;

import java.io.Serializable;

public class InterfaceDefine implements Serializable {
    private Integer id;

    private String ifName;

    private String ifType;

    private String ifRef;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName == null ? null : ifName.trim();
    }

    public String getIfType() {
        return ifType;
    }

    public void setIfType(String ifType) {
        this.ifType = ifType == null ? null : ifType.trim();
    }

    public String getIfRef() {
        return ifRef;
    }

    public void setIfRef(String ifRef) {
        this.ifRef = ifRef == null ? null : ifRef.trim();
    }
}