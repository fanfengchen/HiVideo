package com.ebeijia.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * ClassName:Xml Function: XML的工具方法 Date: 2015/6/23.
 * 
 * @author
 */

public class XmlUtils {

	/**
	 * <一句话功能简述> <功能详细描述>request转字符串
	 * 
	 * @param request
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String parseRequst(HttpServletRequest request) {
		String body = "";
		try {
			ServletInputStream inputStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			while (true) {
				String info = br.readLine();
				if (info == null) {
					break;
				}
				if (body == null || "".equals(body)) {
					body = info;
				} else {
					body += info;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String parseXML(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
				sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	public static SortedMap getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		SortedMap returnMap = new TreeMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value.trim());
		}
		returnMap.remove("method");
		return returnMap;
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map getObjectMap(Object object) {

        try {
			if(object == null){
			    return null;
			}

			Map<String, Object> map = new HashMap<String, Object>();

			Field[] declaredFields = object.getClass().getDeclaredFields();

			for (Field field : declaredFields) {
			    field.setAccessible(true);
			    map.put(field.getName(), field.get(object));
			    System.out.println("name:"+field.getName());
			    System.out.println("object:"+field.get(object));
			}

			return map;
		} catch (Exception e) {
			com.ebeijia.util.LoggerUtil.error(e.getMessage(), e);
			return null;
		}
    }

	/**
	 * 转XMLmap
	 * 
	 * @author
	 * @param xmlBytes
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> toMap(byte[] xmlBytes, String charset)
			throws Exception {
		SAXReader reader = new SAXReader(false);
		InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
		source.setEncoding(charset);
		Document doc = reader.read(source);
		Map<String, String> params = XmlUtils.toMap(doc.getRootElement());
		return params;
	}

	/**
	 * 转MAP
	 * 
	 * @author
	 * @param element
	 * @return
	 */
	public static Map<String, String> toMap(Element element) {
		Map<String, String> rest = new HashMap<String, String>();
		List<Element> els = element.elements();
		for (Element el : els) {
			rest.put(el.getName().toLowerCase(), el.getTextTrim());
		}
		return rest;
	}

	public static String toXml(Map<String, String> params) {
		StringBuilder buf = new StringBuilder();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		buf.append("<xml>");
		for (String key : keys) {
			buf.append("<").append(key).append(">");
			buf.append("<![CDATA[").append(params.get(key)).append("]]>");
			buf.append("</").append(key).append(">\n");
		}
		buf.append("</xml>");
		return buf.toString();
	}

	/**
	 * java 转换成xml
	 *
	 * @Title: toXml
	 * @Description: TODO
	 * @param obj
	 *            对象实例
	 * @return String xml字符串
	 */
	public static String toXml(Object obj) {
		XStream xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xstream.processAnnotations(obj.getClass()); // 通过注解方式的，一定要有这句话
		return xstream.toXML(obj);
	}

	/**
	 * 将传入xml文本转换成Java对象
	 *
	 * @Title: toBean
	 * @Description: TODO
	 * @param xmlStr
	 * @param cls xml对应的class类
	 * @return T xml对应的class类的实例对象
	 *
	 *         调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr,
	 *         PersonBean.class);
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		xstream.ignoreUnknownElements();
		T obj = (T)xstream.fromXML(xmlStr);
		return obj;
	}
}
