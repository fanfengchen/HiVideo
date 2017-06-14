package com.ebeijia.common;

public class EnumProperty {
	
	private final String mRegular;//正则表达式
	private final String mMessage;//提示消息
	private final boolean mNotNull;//不为空
	
	public EnumProperty(boolean notNull, String regular, String message){
		mNotNull = notNull;
		mRegular = regular;
		mMessage = message;
	}
	
	public String getRegular(){
		return mRegular;
	}
	
	public boolean getNotNull(){
		return mNotNull;
	}
	
	public String getMessage(){
		return  mMessage;
	}
	
}
