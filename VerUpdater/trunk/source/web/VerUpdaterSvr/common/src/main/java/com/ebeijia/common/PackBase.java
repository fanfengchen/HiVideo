package com.ebeijia.common;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ebeijia.util.exception.EPackException;
import com.ebeijia.util.log.CustomLog;



//报文基类
public class PackBase {
	private JSONObject mBuffer = new JSONObject();//数据 
	private String mInterfaceName = "";//接口名称
	private static boolean mReflectForce = true;//强制反射
	
	public PackBase(){

	}
	
	public PackBase(String jsonString) throws EPackException{
		Unpack(jsonString);
	}
	
	public PackBase(Map<String, String[]> map) throws EPackException{
		Unpack(map);
	}
	
	/**
	 * 计算md5
	 * @param str
	 * @param upper 是否返回大写
	 * @return 返回md5
	 */
	public static String md5(String str, boolean upper) {
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
	public static String md5Upper(String data){
		return md5(data, true);
	}
	
	/**
	 * 比较报文md5是否相同
	 * @param buffer 比较报文
	 * @param md5 正确的md5
	 * @param upper 是否大写
	 * @return 返回是否相同
	 */
	public static boolean md5CompareByBuffer(String buffer, String md5, boolean upper){
		return md5(buffer, upper).equals(md5);
	}
	/**
	 * 比较报文md5是否相同
	 * @param buffer 比较报文
	 * @param md5 正确的md5
	 * @param upper 是否大写
	 * @return 返回是否相同
	 */
	public static boolean md5UpperCompareByBuffer(String buffer, String md5){
		return md5(buffer, true).equals(md5);
	}
	
	/**
	 * 通过接口名称反射出对应的枚举类，接口名称必须与枚举名称一致,静态方法
	 * @param interfaceName
	 * @return 返回枚举类
	 * @throws EPackException
	 */
	public static Class<? extends Enum> getPackType(String interfaceName) throws EPackException{
		//反射类需要明确类的完整路径，反射内部类使用$来连接
		try {
			return (Class<? extends Enum>)Class.forName("com.ebeijia.common.IPackType$" + interfaceName);
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
	public static void setReflectForce(boolean isForce){
		mReflectForce = isForce;
	}
	
	/**
	 * 正则匹配，静态方法
	 * @param regular 正则
	 * @param value 数据
	 * @return 匹配是否成功
	 */
	public static boolean regularMatches(String regular, String value){
		try {
			return Pattern.compile(regular).matcher(value).matches();
		} catch (Exception e) {
			System.out.println("正则匹配异常: " + e + " " + e.getMessage());
			return false;
		}
	}
	/**
	 * 判断string是否有效，静态方法
	 * @param value 
	 * @return 是否有效
	 */
	public static boolean isValid(String value){
		return value != null && !value.equals("");
	}
	/**
	 * 检查参数完整性，静态方法
	 * @param buffer json报文
	 * @param packType 报文的类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParamIntegrity(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
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
					CustomLog.getInstances().logInfo("缺少参数"+ param.name());
					throw new EPackException("缺少参数：" + param.name());
				}
			}
		} catch (Exception e) {
			throw new EPackException("检查参数完整性异常：" + e + " " + e.getMessage());
		}
	}
	/**
	 * 检查参数完整性，静态方法
	 * @param buffer json报文
	 * @param interfaceName 接口名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParamIntegrity(JSONObject buffer, String interfaceName) throws EPackException{
		checkParamIntegrity(buffer, getPackType(interfaceName));
	}
	/**
	 * 检查自身的参数完整性
	 * @param packType 报文的类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamIntegrity(Class<? extends Enum> packType) throws EPackException{
		checkParamIntegrity(getBuffer(), packType);
	}
	/**
	 * 检查自身的参数完整性，子类可重写
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public void checkParamIntegrity() throws EPackException{
		
	}
	/**
	 * 检查参数有效性，静态方法
	 * @param buffer json报文
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParamValidity(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
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
	 * 检查参数有效性，静态方法
	 * @param buffer json报文
	 * @param interfaceName 接口名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParamValidity(JSONObject buffer, String interfaceName) throws EPackException{
		checkParamValidity(buffer, getPackType(interfaceName));
	}
	/**
	 * 检查自身的参数有效性
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamValidity(Class<? extends Enum> packType) throws EPackException{
		checkParamValidity(getBuffer(), packType);
	}
	/**
	 * 检查自身的参数有效性，子类可重写
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public void checkParamValidity() throws EPackException{
		
	}
	/**
	 * 检查参数，包括完整性、有效性，静态方法
	 * @param buffer json报文
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParam(JSONObject buffer, Class<? extends Enum> packType) throws EPackException{
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
	 * 检查参数，包括完整性、有效性，静态方法
	 * @param buffer json报文
	 * @param interfaceName 接口名称
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public static void checkParam(JSONObject buffer, String interfaceName) throws EPackException{
		checkParam(buffer, getPackType(interfaceName));
	}
	/**
	 * 检查自身的参数，包括完整性、有效性
	 * @param packType 报文类型
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParam(Class<? extends Enum> packType) throws EPackException{
		checkParam(getBuffer(), packType);
	}
	/**
	 * 检查自身的参数，包括完整性、有效性，子类可重写
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public void checkParam() throws EPackException{
		
	}
	/**
	 * 通过自身的接口名称，检查自身的参数，包括完整性、有效性
	 * @throws EPackException 检查参数不完整通过异常返回
	 */
	public final void checkParamByInterfaceName() throws EPackException{
		checkParam(getPackType(getInterfaceName()));
	}
	/**
	 * 返回报文的接口名称,子类可重写。或者通过setInterfaceName方法来设置接口名称
	 * @return
	 */
	public String getInterfaceName() {
		return mInterfaceName;
	}
	
	/**
	 * 设置接口名称
	 * @param interfaceName
	 */
	public final void setInterfaceName(String interfaceName) {
		mInterfaceName = interfaceName;
	}
	/**
	 * 清空参数
	 */
	public final void clearParam(){
		mBuffer.clear();
	}
	/**
	 * 添加参数
	 * @param name 
	 * @param value
	 * @return 返回添加后的JSONObject
	 */
	private final JSONObject putParam(String name, Object value){
		return mBuffer.element(name, value);
	}
	/**
	 * 添加参数
	 * @param name 
	 * @param value
	 * @return 返回添加后的JSONObject
	 */
	public final JSONObject putParam(IPackType name, Object value){
		return putParam(name.name(), value);
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回Object参数
	 */
	private final Object getParam(String name){
		return mBuffer.get(name);
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回Object参数
	 */
	public final Object getParam(IPackType name){
		return getParam(name.name());
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回String参数
	 */
	private final String getParamString(String name){
		return mBuffer.getString(name);
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回String参数
	 */
	public final String getParamString(IPackType name){
		return getParamString(name.name());
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回JSONArray参数
	 */
	private final JSONArray getParamJSONArray(String name){
		return mBuffer.getJSONArray(name);
	}
	/**
	 * 获取参数
	 * @param name
	 * @return 返回JSONArray参数
	 */
	public final JSONArray getParamJSONArray(IPackType name){
		return getParamJSONArray(name.name());
	}
	/**
	 * 参数是否包含
	 * @param name
	 * @return 返回是否包含
	 */
	public final boolean containsParam(IPackType name){
		return containsParam(name.name());
	}
	/**
	 * 参数是否包含
	 * @param name
	 * @return 返回是否包含
	 */
	private final boolean containsParam(String name){
		return mBuffer.containsKey(name);
	}
	/**
	 * 获取整个json报文对象
	 * @return 返回JSONObject
	 */
	public final JSONObject getBuffer(){
		return mBuffer;
	}
	/**
	 * 获取整个json报文
	 * @return 返回String
	 */
	public final String getBufferString(){
		return mBuffer.toString();
	}
	/**
	 * 组包后，获取整个json报文
	 * @return 返回JSONObject
	 */
	public final JSONObject getBufferByPack(){
		Pack();
		return getBuffer();
	}
	/**
	 * 组包后，获取整个json报文
	 * @return 返回String
	 */
	public final String getBufferStringByPack(){
		Pack();
		return getBufferString();
	}
	/**
	 * 组报方法,子类可重写
	 */
	public void Pack(){
		
	}
	/**
	 * 解包方法,子类可重写
	 * @param jsonString json报文
	 * @throws EPackException 
	 */
	public void Unpack(String jsonString) throws EPackException{
		System.out.println("解包: " + jsonString);
		try {
			mBuffer = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			System.out.println("解包异常: " + e + " " + e.getMessage());
			throw new EPackException("无效报文");
		}
	}
	
	/**
	 * 解包方法,子类可重写
	 * @param map map报文
	 * @throws EPackException 
	 */
	public void Unpack(Map<String, String[]> map) throws EPackException{
		try {
			mBuffer.putAll(map);
			System.out.println("解包: " + mBuffer.toString());
		} catch (Exception e) {
			System.out.println("解包异常: " + e + " " + e.getMessage());
			throw new EPackException("无效报文");
		}
	}
	
	
	
}
