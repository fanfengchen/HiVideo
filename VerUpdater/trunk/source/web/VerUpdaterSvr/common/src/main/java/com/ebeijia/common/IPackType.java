package com.ebeijia.common;


public interface IPackType {
	
	public String name();
	public EnumProperty getProperty();
	
		//枚举值,格式enum(是否必填，正则验证，提示消息)
		//请求包头
		enum PTHeadReq implements IPackType{
			sendTime(true, "", "格式错误"), 
			version(true, "", ""), 
			appType(true, "", ""), 
			authToken(true, "", ""), 
			devId(false, "",""),
			data(true,"","");
			
			//自定义方法
			private final EnumProperty mProperty;
			
			private PTHeadReq(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
		
		//应答包头
		enum PTHeadRsp implements IPackType{
			sendTime(true, "^[1][3-8]\\d{9}$", "格式错误"), 
			rspCode(true, "", ""), 
			rspMsg(true, "", "");
			
			//自定义方法
			private final EnumProperty mProperty;
			
			private PTHeadRsp(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
			
		//包尾校验
		enum PTMacVerify implements IPackType{
			//枚举值
			signType(true, "", ""), 
			sign(true, "", "");
			//自定义方法
			private final EnumProperty mProperty;
			
			private PTMacVerify(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
		
		//包体
		enum register implements IPackType{
			//枚举值
			nullParam(false, "", "");
			
			private final EnumProperty mProperty;
			
			private register(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
		enum getSoftVerList implements IPackType{
			//枚举值
			chnlId(true, "", ""),
			typeId(false,"",""),
			limit(false,"",""),
			offset(false,"","");
			
			
			private final EnumProperty mProperty;
			
			private getSoftVerList(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
		
		// 包体
		enum getSoftTypeList implements IPackType {
			// 枚举值
			nullParam(false, "", "");

			private final EnumProperty mProperty;

			private getSoftTypeList(boolean notNull, String regular, String message) {
				mProperty = new EnumProperty(notNull, regular, message);
			}

			public EnumProperty getProperty() {
				return mProperty;
			}
		}
		
		//包体
		enum getSoftVerInfo implements IPackType{
			//枚举值
			chnlId(true, "", ""),
			softId(true, "", "");
			
			
			private final EnumProperty mProperty;
			
			private getSoftVerInfo(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}
		
		//包体
		enum searchSoftVer implements IPackType{
			//枚举值
			
			chnlId(true, "", ""),
			content(true, "", ""),
			limit(false, "", ""),
			offset(false, "", "");
			
			private final EnumProperty mProperty;
			
			private searchSoftVer(boolean notNull, String regular, String message){
				mProperty = new EnumProperty(notNull, regular, message);
			}
			
			public EnumProperty getProperty(){
				return mProperty;
			} 
		}

	
}



	
	
