package com.ebeijia.entity;

import java.io.Serializable;

public class InterfaceColnumInfo implements Serializable {
    private Integer id;

    private String colnum;

    private String colDesc;

    private Integer colLength;

    private String colDataType;

    private String colPadType;

    private String isAutoPad;

    private String padChar;

    private Integer colOrder;

    private Integer defineId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColnum() {
        return colnum;
    }

    public void setColnum(String colnum) {
        this.colnum = colnum == null ? null : colnum.trim();
    }

    public String getColDesc() {
        return colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc == null ? null : colDesc.trim();
    }

    public Integer getColLength() {
        return colLength;
    }

    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    public String getColDataType() {
        return colDataType;
    }

    public void setColDataType(String colDataType) {
        this.colDataType = colDataType == null ? null : colDataType.trim();
    }

    public String getColPadType() {
        return colPadType;
    }

    public void setColPadType(String colPadType) {
        this.colPadType = colPadType == null ? null : colPadType.trim();
    }

    public String getIsAutoPad() {
        return isAutoPad;
    }

    public void setIsAutoPad(String isAutoPad) {
        this.isAutoPad = isAutoPad == null ? null : isAutoPad.trim();
    }

    public String getPadChar() {
        return padChar;
    }

    public void setPadChar(String padChar) {
        this.padChar = padChar == null ? null : padChar.trim();
    }

    public Integer getColOrder() {
        return colOrder;
    }

    public void setColOrder(Integer colOrder) {
        this.colOrder = colOrder;
    }

    public Integer getDefineId() {
        return defineId;
    }

    public void setDefineId(Integer defineId) {
        this.defineId = defineId;
    }
}