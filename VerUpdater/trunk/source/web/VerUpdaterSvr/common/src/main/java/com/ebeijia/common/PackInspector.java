package com.ebeijia.common;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import com.ebeijia.util.exception.EPackException;


public class PackInspector {
	private static PackInspector mInstances = null;
	private final String mPackTypeClassPath = "com.ebeijia.common.IPackType$";
	private boolean mReflectForce = true;//强制反射
	
	private PackInspector(){
		
	}
	//单实例模式
	public static PackInspector getInstances() {
		if(mInstances == null)
			mInstances = new PackInspector();
		return mInstances;
	}
	
	public static void releaseInstances() {
		if(mInstances != null)
			mInstances = null;
	}
	
	/**
	 * 计算md5
	 * @param str
	 * @param upper 是否返回大写
	 * @return 返回md5
	 */
	public final String md5(String str, boolean upper) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				if (upper) {
					buf.append(Integer.toHexString(i).toUpperCase());
				} else {
					buf.append(Integer.toHexString(i));
				}
			}
			str = buf.toString();
		} catch (Exception e) {
			System.out.println("md5计算异常:" + e + " " + e.getMessage());
		}
		return str;
	}
	
	/**
	 * 计算md5
	 * @param data
	 * @return 返回大写md5
	 */
	public final String md5Upper(String data){
		return md5(data, true);
	}
	
	/**
	 * 比较报文md5是否相同
	 * @param buffer 比较报文
	 * @param md5 正确的md5
	 * @param upper 是否大写
	 * @return 返回是否相同
	 */
	public final boolean md5CompareByBuffer(String buffer, String md5, boolean upper){
		return md5(buffer, upper).equals(md5);
	}
	/**
	 * 比较报文md5是否相同
	 * @param buffer 比较报文
	 * @param md5 正确的md5
	 * @param upper 是否大写
	 * @return 返回是否相同
	 */
	public final boolean md5UpperCompareByBuffer(String buffer, String md5){
		return md5(buffer, true).equals(md5);
	}
	
	/**
	 * 通过包名称反射出对应的枚举类，包名称必须与枚举名称一致
	 * @param packName
	 * @return 返回枚举类
	 * @throws EPackException
	 */
	public final Class<? extends Enum> getPackType(String packName) throws EPackException{
		//反射类需要明确类的完整路径，反射内部类使用$来连接
		try {
			return (Class<? extends Enum>)Class.forName(mPackTypeClassPath + packName);
		} catch (Exception e) {
			System.out.println("反射不能找到类: " + e + " " + e.getMessage());
			if (!mReflectForce) return null;//非强制就返回null
			throw new EPackException("获取报文类型失败");
		}
	}
	
	/**
	 * true时反射失败抛异常，false时反射失败忽略
	 * @param isForce
	 */
	public final void setReflectForce(boolean isForce){
		mReflectForce = isForce;
	}
	
	/**
	 * 正则匹配
	 * @param regular 正则
	 * @param value 数据
	 * @return 匹配是否成功
	 */
	public final boolean regularMatches(String regular, String value){
		try {
			return Pattern.compile(regular).matcher(value).matches();
		} catch (Exception e) {
			System.out.println("正则匹配异常: " + e + " " + e.getMessage());
			return false;
		}
	}
	/**
	 * 判断string是否有效
	 * @param value 
	 * @return 是否有效
	 */
	public final boolean isValid(String value){
		return value != null && !value.equals("");
	}
	
	/**
	 * 检查参数完整性
	 * @param buffer json报文
	 * @param packType 报文的类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamIntegrity(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
		if (!mReflectForce && packType == null) return;//非强制就直接返回
		try {
			//通过反射获取方法
			Method valuesMethod = packType.getDeclaredMethod("values");
			Method getPropertyMethod = packType.getDeclaredMethod("getProperty");
			//执行方法，获取所有枚举值
			Enum[] enums = (Enum[]) valuesMethod.invoke(packType);
			//循环检查参数完整性
			for (Enum param : enums){
				//获取枚举的属性值
				EnumProperty property = (EnumProperty)getPropertyMethod.invoke(param);
				if (property.getNotNull() && !buffer.containsKey(param.name())){
					throw new EPackException("缺少参数：" + param.name());
				}
			}
		} catch (Exception e) {
			throw new EPackException("检查参数完整性异常：" + e + " " + e.getMessage());
		}
	}
	/**
	 * 检查参数完整性
	 * @param buffer json报文
	 * @param packName 包名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamIntegrity(JSONObject buffer, String packName) throws EPackException{
		checkParamIntegrity(buffer, getPackType(packName));
	}
	
	/**
	 * 检查参数有效性
	 * @param buffer json报文
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamValidity(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
		if (!mReflectForce && packType == null) return;//非强制就直接返回
		try {
			//通过反射获取方法
			Method valuesMethod = packType.getDeclaredMethod("values");
			Method getPropertyMethod = packType.getDeclaredMethod("getProperty");
			//执行方法，获取所有枚举值
			Enum[] enums = (Enum[]) valuesMethod.invoke(packType);
			
			for (Enum param : enums){
				//获取枚举的属性值
				EnumProperty property = (EnumProperty)getPropertyMethod.invoke(param);
				if (isValid(property.getRegular()) 
						&& !regularMatches(property.getRegular(), buffer.getString(param.name()))){
					throw new EPackException(property.getMessage());
				}
			}
		} catch (Exception e) {
			throw new EPackException("检查参数有效性异常：" + e + " " + e.getMessage());
		}
		
	}
	/**
	 * 检查参数有效性
	 * @param buffer json报文
	 * @param packName 包名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamValidity(JSONObject buffer, String packName) throws EPackException{
		checkParamValidity(buffer, getPackType(packName));
	}
	
	/**
	 * 检查参数，包括完整性、有效性
	 * @param buffer json报文
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParam(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
		if (!mReflectForce && packType == null) return;//非强制就直接返回
		try {
			//通过反射获取方法
			Method valuesMethod = packType.getDeclaredMethod("values");
			Method getPropertyMethod = packType.getDeclaredMethod("getProperty");
			//执行方法，获取所有枚举值
			Enum[] enums = (Enum[]) valuesMethod.invoke(packType);
			
			for (Enum param : enums){
				EnumProperty property = (EnumProperty)getPropertyMethod.invoke(param);
				if (!buffer.containsKey(param.name())){
					if (property.getNotNull()){
						throw new EPackException("缺少参数：" + param.name());
					}
				}
				else if (isValid(property.getRegular()) 
						&& !regularMatches(property.getRegular(), buffer.getString(param.name()))){
					throw new EPackException(property.getMessage());
				}
			}
		} catch (Exception e) {
			throw new EPackException("检查参数异常：" + e + " " + e.getMessage());
		}
	}
	/**
	 * 检查参数，包括完整性、有效性
	 * @param buffer json报文
	 * @param packName 包名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParam(JSONObject buffer, String packName) throws EPackException{
		checkParam(buffer, getPackType(packName));
	}
}
