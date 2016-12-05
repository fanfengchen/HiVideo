/**
 * WsServiceServletServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ebeijia.wsdl.pay;

import com.ebeijia.dto.webService.QueryUserDto;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.XmlUtils;
import org.apache.axis.utils.XMLUtils;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

public class WsServiceServletServiceLocator extends
		org.apache.axis.client.Service implements WsServiceServletService {

	public WsServiceServletServiceLocator() {
	}

	public WsServiceServletServiceLocator(
			org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public WsServiceServletServiceLocator(java.lang.String wsdlLoc,
			javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for yxws
	private java.lang.String yxws_address = PropertiesUtils.getProperties("yxws_address");
/*	private java.lang.String yxws_address ="http://117.175.30.76:28080/gxws/services/yxws";*/

	public java.lang.String getyxwsAddress() {
		return yxws_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String yxwsWSDDServiceName = "yxws";

	public java.lang.String getyxwsWSDDServiceName() {
		return yxwsWSDDServiceName;
	}

	public void setyxwsWSDDServiceName(java.lang.String name) {
		yxwsWSDDServiceName = name;
	}

	public WsServiceServlet getyxws() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(yxws_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getyxws(endpoint);
	}

	public WsServiceServlet getyxws(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException {
		try {
			YxwsSoapBindingStub _stub = new YxwsSoapBindingStub(portAddress,
					this);
			_stub.setPortName(getyxwsWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setyxwsEndpointAddress(java.lang.String address) {
		yxws_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		try {
			if (WsServiceServlet.class
					.isAssignableFrom(serviceEndpointInterface)) {
				YxwsSoapBindingStub _stub = new YxwsSoapBindingStub(
						new java.net.URL(yxws_address), this);
				_stub.setPortName(getyxwsWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException(
				"There is no stub implementation for the interface:  "
						+ (serviceEndpointInterface == null ? "null"
								: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
			Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("yxws".equals(inputPortName)) {
			return getyxws();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName(
				"http://117.175.30.76:28080/gxws/services/yxws",
				"WsServiceServletService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName(
					"http://117.175.30.76:28080/gxws/services/yxws", "yxws"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("yxws".equals(portName)) {
			setyxwsEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(
					" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,
			java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}
	
	public static void main(String[] args) throws ServiceException, RemoteException {
		WsServiceServlet ws=new WsServiceServletServiceLocator().getyxws();

			String str = ws.interfaceQueryUser("30027286");
			QueryUserDto queryUserDto = XmlUtils.toBean(str,QueryUserDto.class);
			System.out.println(FastJson.toJson(queryUserDto));

	}

}
