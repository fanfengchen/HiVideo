package com.ebeijia.wsdl.query;

/**
 * YxWebServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */



public interface YxWebServiceSoap extends java.rmi.Remote {

    /**
     * 查询燃气用户数据
     */
    public java.lang.String findGasUserInfo(java.lang.String userInfo, javax.xml.rpc.holders.StringHolder recinfo) throws java.rmi.RemoteException;

    /**
     * 查询自来水用户数据
     */
    public java.lang.String findWaterUserInfo(java.lang.String userInfo, javax.xml.rpc.holders.StringHolder recinfo) throws java.rmi.RemoteException;

    /**
     * 查询水历史缴费数据
     */
    public java.lang.String waterPayMoney(java.lang.String userInfo, javax.xml.rpc.holders.StringHolder recinfo) throws java.rmi.RemoteException;

    /**
     * 查询气历史缴费数据
     */
    public java.lang.String gasPayMoney(java.lang.String userInfo, javax.xml.rpc.holders.StringHolder recinfo) throws java.rmi.RemoteException;
}
