/**
 * YxWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ebeijia.wsdl.query;

public interface YxWebService extends javax.xml.rpc.Service {
    public String getYxWebServiceSoap12Address();

    public  YxWebServiceSoap getYxWebServiceSoap12() throws javax.xml.rpc.ServiceException;

    public  YxWebServiceSoap getYxWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getYxWebServiceSoapAddress();

    public  YxWebServiceSoap getYxWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public  YxWebServiceSoap getYxWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
