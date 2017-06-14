package com.ebeijia.util.core;


import com.ebeijia.util.http.HttpClientConnectionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetWxOrderno {
    public static DefaultHttpClient httpclient;

    static {
        httpclient = new DefaultHttpClient();
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient);
    }


    /**
     * description:获取预支付id
     *
     * @param url 地址
     * @param xmlParam 参数
     * @return prepay_id 支付id
     * @see
     */
    public static synchronized String getPayNo(String url, String xmlParam) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String prepay_id = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("jsonStr:"+jsonStr);
                if (jsonStr.indexOf("FAIL") != -1) {
                    return prepay_id;
                }
                Map map = doXMLParse(jsonStr);
                prepay_id = (String) map.get("prepay_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpost.abort();
        }
        return prepay_id;
    }


    public static synchronized String getPayMc(String url, String xmlParam) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        String result_code = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("jsonStr:"+jsonStr);
                if (jsonStr.indexOf("FAIL") != -1) {
                    if(jsonStr.indexOf("USERPAYING") != -1){
                        //查询订单
                        System.out.print("用户输入密码,此处查询订单！");

                    }
                    if(jsonStr.indexOf("SYSTEMERROR") != -1){
                        result_code =  "SYSTEMERROR";
                    }
                    return result_code;
                }
                if(jsonStr.indexOf("USERPAYING") != -1){
                    //查询订单
                    result_code = "ERROR";
                    return result_code;
                }
                Map map = doXMLParse(jsonStr);
                result_code = (String) map.get("result_code");
                if(result_code.equals("SUCCESS")){
                    String out_trade_no = (String) map.get("out_trade_no");
                    result_code = result_code + "|" + out_trade_no;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpost.abort();
        }
        return result_code;
    }
    /**
     *description:获取扫码支付连接
     * @param url 地址
     * @param xmlParam 参数
     *@return str 二维码地址
     * @see
     */
    public static String getCodeUrl(String url,String xmlParam){
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
        String code_url = "";
        try {
            httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
            HttpResponse response = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            if(jsonStr.indexOf("FAIL")!=-1){
                return code_url;
            }
            Map map = doXMLParse(jsonStr);
            code_url  = (String) map.get("code_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code_url;
    }
    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml xml参数
     * @throws JDOMException
     * @throws IOException
     */
    public static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            m.put(k, v);
        }
        //关闭流
        in.close();
        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children 子节点
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    //微信查询订单
    public static String sendQry(String url, Object xmlObj) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        String result = null;

        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        try {
            HttpResponse response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }

        return result;
    }
}