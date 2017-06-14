package com.ebeijia.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import com.ebeijia.util.log.CustomLog;


public class Propert {
	private static Propert mInstances = null;
	// 单实例模式
		public static Propert getInstances() {
			if (mInstances == null)
				mInstances = new Propert();
			return mInstances;
		}
	
	 public String pro(String imgurl){
	Properties prop = new Properties();    
     try{
         //读取属性文件a.properties
         InputStream in = new BufferedInputStream (Propert.class.getResourceAsStream("config.properties"));
         prop.load(in);     ///加载属性列表
         Iterator<String> it=prop.stringPropertyNames().iterator();
         in.close();
     }
     catch(Exception e){
         System.out.println(e);
     }
	 
	 return prop.getProperty(imgurl);
}
}

