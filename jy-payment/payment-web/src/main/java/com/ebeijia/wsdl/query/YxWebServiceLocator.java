/**
 * YxWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ebeijia.wsdl.query;


import com.ebeijia.dto.webService.QueryUserInfo;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.XmlUtils;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import java.rmi.RemoteException;

public class YxWebServiceLocator extends org.apache.axis.client.Service implements  YxWebService {

    public YxWebServiceLocator() {
    }


    public YxWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public YxWebServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for YxWebServiceSoap12
    private String YxWebServiceSoap12_address = PropertiesUtils.getProperties("yx_user_address");;

    public String getYxWebServiceSoap12Address() {
        return YxWebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String YxWebServiceSoap12WSDDServiceName = "YxWebServiceSoap12";

    public String getYxWebServiceSoap12WSDDServiceName() {
        return YxWebServiceSoap12WSDDServiceName;
    }

    public void setYxWebServiceSoap12WSDDServiceName(String name) {
        YxWebServiceSoap12WSDDServiceName = name;
    }

    public YxWebServiceSoap getYxWebServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(YxWebServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getYxWebServiceSoap12(endpoint);
    }

    public YxWebServiceSoap getYxWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
          YxWebServiceSoap12Stub _stub = new YxWebServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getYxWebServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setYxWebServiceSoap12EndpointAddress(String address) {
        YxWebServiceSoap12_address = address;
    }


    // Use to get a proxy class for YxWebServiceSoap
    private String YxWebServiceSoap_address = PropertiesUtils.getProperties("yx_user_address");

    public String getYxWebServiceSoapAddress() {
        return YxWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String YxWebServiceSoapWSDDServiceName = "YxWebServiceSoap";

    public String getYxWebServiceSoapWSDDServiceName() {
        return YxWebServiceSoapWSDDServiceName;
    }

    public void setYxWebServiceSoapWSDDServiceName(String name) {
        YxWebServiceSoapWSDDServiceName = name;
    }

    public YxWebServiceSoap getYxWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(YxWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getYxWebServiceSoap(endpoint);
    }

    public YxWebServiceSoap getYxWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            YxWebServiceSoapStub _stub = new YxWebServiceSoapStub(portAddress, this);
            _stub.setPortName(getYxWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setYxWebServiceSoapEndpointAddress(String address) {
        YxWebServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if ( YxWebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                 YxWebServiceSoap12Stub _stub = new  YxWebServiceSoap12Stub(new java.net.URL(YxWebServiceSoap12_address), this);
                _stub.setPortName(getYxWebServiceSoap12WSDDServiceName());
                return _stub;
            }
            if ( YxWebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                 YxWebServiceSoapStub _stub = new  YxWebServiceSoapStub(new java.net.URL(YxWebServiceSoap_address), this);
                _stub.setPortName(getYxWebServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("YxWebServiceSoap12".equals(inputPortName)) {
            return getYxWebServiceSoap12();
        }
        else if ("YxWebServiceSoap".equals(inputPortName)) {
            return getYxWebServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://adong.org/", "YxWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://adong.org/", "YxWebServiceSoap12"));
            ports.add(new javax.xml.namespace.QName("http://adong.org/", "YxWebServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("YxWebServiceSoap12".equals(portName)) {
            setYxWebServiceSoap12EndpointAddress(address);
        }
        else 
if ("YxWebServiceSoap".equals(portName)) {
            setYxWebServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }


    public static void main(String[] args) throws ServiceException, RemoteException {
        YxWebServiceLocator locator=new YxWebServiceLocator();
        YxWebServiceSoap soap=locator.getYxWebServiceSoap12();
        StringHolder ho = new StringHolder();
        String st=	soap.findWaterUserInfo("10000465", ho);
        System.out.println("args = [" + st + "]");
        System.out.println("args = [" + ho.value + "]");
        /*QueryUserInfo queryUserInfo = XmlUtils.toBean(ho.value,QueryUserInfo.class);
        System.out.println(FastJson.toJson(queryUserInfo));*/
    }

}
