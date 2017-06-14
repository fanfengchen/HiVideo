package com.ebeijia.robot.core.constant;

public class CommonTypeConstant {

/**
 * 登陆类型
 * @author lijm
 *
 */
public enum AppType {
	
		AndStu("11", "android学生端"), AndTea("12", "android老师端"),IosStu("13", "IOS学生端"),IosTea("14", "IOS老师端"),Admin("01","后台"),Wang("03","网站");
		String value;
		String desc;

		AppType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public static String getDescByValue(String value) {
			if (AndStu.toString().equals(value)) {
				return AndStu.desc;
			} else if(AndTea.toString().equals(value)) {
				return AndTea.desc;				
			}else if(IosStu.toString().equals(value)){
				return IosStu.desc;
			}else if(Admin.toString().equals(value)){
				return Admin.desc;
			}else if(Wang.toString().equals(value)){
				return Wang.desc;
			}else{
				return IosTea.desc;
			}
		}
	}
	/**
	 * 课程类型
	 * @author lijm
	 *
	 */
	public enum LessonType {
		
		Lesson("01", "课程"), Onlone("02", "线下"),Offine("03", "线下时间计划"),Test("04", "题目"),Par("05","章节");
		String value;
		String desc;

		LessonType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Lesson.toString().equals(value)) {
				return Lesson.desc;
			} else if(Onlone.toString().equals(value)) {
				return Onlone.desc;				
			}else if(Offine.toString().equals(value)){
				return Offine.desc;
			}else if(Test.toString().equals(value)){
				return Test.desc;
			}else{
				return Par.desc;
			}
		}
	}
	
	/**
	 * 用户状态
	 * @author lijm
	 *
	 */
	public enum userState {
		User("01", "启用"), NotUser("02", "冻结");

		String value;
		String desc;

		userState(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (User.toString().equals(value)) {
				return User.desc;
			} else {
				return NotUser.desc;
			}
		}
	}
	/**
	 * 问答题题目类型
	 * @author lijm
	 *
	 */
	public enum EssayQuesType {
		Ques("04", "问答题"), Case("05", "案例分析");

		String value;
		String desc;

		EssayQuesType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Ques.toString().equals(value)) {
				return Ques.desc;
			} else {
				return Case.desc;
			}
		}
	}
	/**
	 * 是否有效
	 * @author lijm
	 *
	 */
	public enum IsActive {
		True("0", "有效"), False("1", "无效");

		String value;
		String desc;

		IsActive(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (True.toString().equals(value)) {
				return True.desc;
			} else {
				return False.desc;
			}
		}
	}
	/**
	 * 消息是否已读
	 * @author lijm
	 *
	 */
	public enum IsMsg {
		True("1", "已读"), False("0", "未读");

		String value;
		String desc;

		IsMsg(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (True.toString().equals(value)) {
				return True.desc;
			} else {
				return False.desc;
			}
		}
	}
	/**
	 * 是否通过
	 * @author lijm
	 *
	 */
	public enum isPass {
		True("1", "通过"), False("0", "不通过");

		String value;
		String desc;

		isPass(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (True.toString().equals(value)) {
				return True.desc;
			} else {
				return False.desc;
			}
		}
	}
	
	/**
	 * 是否通过
	 * @author lijm
	 *
	 */
	public enum randType {
		Nither("01", "题目和答案都随机"), Test("0", "只随机题目"),Case("03","只随机答案"),;

		String value;
		String desc;

		randType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Nither.toString().equals(value)) {
				return Nither.desc;
			} else if(Test.toString().equals(value)){
				return Test.desc;
			}else{
				return Case.desc;
			}
		}
	}
	/**
	 * 交易类型
	 * @author lijm
	 *
	 */
	public enum txnType {
		
		Frist("01", "首次入会"), Promote("02", "会员升级"),Renew("03","会员续费"),Refund("04","退款");

		String value;
		String desc;

		txnType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Frist.toString().equals(value)) {
				return Frist.desc;
			} else if(Promote.toString().equals(value)){
				return Promote.desc;
			}else if(Renew.toString().equals(value)){
				return Renew.desc;
			}else{
				return Refund.desc;
			}
		}
	}
   public enum DomainType {
		
	   Automatic("01", "手动推送"), Automaticw("02", "自动推送");

		String value;
		String desc;

		DomainType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Automatic.toString().equals(value)) {
				return Automatic.desc;
			} else {
				return Automaticw.desc;
			}
		}
	}
	/**
	 * 机器人状态
	 * @author lijm
	 *
	 */
	public enum RobotState {
		
		//01忙碌，02空闲
		Online("01", "忙碌"),Stop("02", "空闲");

		String value;
		String desc;

		RobotState(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Online.toString().equals(value)) {
				return Online.desc;
			}else{
				return Stop.desc;
			}
			
		}
	}
	/**
	 * 支付类型
	 * @author lijm
	 *
	 */
   public enum payType {
	
		Cash("01", "现金"), UnionPay("02", "银联"),Alipay("03", "支付宝"),Chat("04","微信支付");
		String value;
		String desc;
		payType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (Cash.toString().equals(value)) {
				return Cash.desc;
			} else if(UnionPay.toString().equals(value)){
				return UnionPay.desc;
			}else if(Alipay.toString().equals(value)){
				return Alipay.desc;
			}else{
				return Chat.desc;
			}
			
		}
	}
   /**
    * 支付状态
    * @author lijm
    *
    */
   public enum payState {
		
	   notPay("01", "未支付"), SuccPay("02", "支付成功"),FailPay("03", "支付失败");
		String value;
		String desc;
		payState(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}

		public static String getDescByValue(String value) {
			if (notPay.toString().equals(value)) {
				return notPay.desc;
			} else if(SuccPay.toString().equals(value)){
				return SuccPay.desc;
			}else {
				return FailPay.desc;
		    }
		}
	}
	/**
	 * 交易状态
	 * @author lijm
	 *
	 */
	public enum state {
		
		notState("31", "未对账"), stateIng("32", "对账中"),wrongState("33", "已招满"),finshState("34","已对账");

		String value;
		String desc;

		state(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public static String getDescByValue(String value) {
			if (notState.toString().equals(value)) {
				return notState.desc;
			} else if(stateIng.toString().equals(value)){
				return stateIng.desc;
			}else if(wrongState.toString().equals(value)){
				return wrongState.desc;
			}else{
				return finshState.desc;
			}			
		}
	}
	/**
	 * 学习进度状态
	 * @author lijm
	 *
	 */
	public enum proState {
		Succ("51", "测试合格"), Asucc("52", "不合格"),notSucc("53", "强制完成"),Default("54","默认解锁"),NDefault("55","默认不解锁");

		String value;
		String desc;

		proState(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public static String getDescByValue(String value) {
			if (Succ.toString().equals(value)) {
				return Succ.desc;
			} else if(Asucc.toString().equals(value)){
				return Asucc.desc;
			}else if(notSucc.toString().equals(value)){
				return notSucc.desc;
			}else if(Default.toString().equals(value)){
				return Default.desc;
			} {
				return NDefault.desc;
			}
		}
	}
	
	/**
	 * 推送状态
	 * @author lijm
	 *
	 */
	public enum DomaineState {
		Succing("01", "正在进行"), Succ("02", "推送成功"),Fail("03", "推送失败");

		String value;
		String desc;

		DomaineState(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public static String getDescByValue(String value) {
			if (Succing.toString().equals(value)) {
				return Succing.desc;
			} else if(Succ.toString().equals(value)){
				return Succ.desc;
			}else{
				return Fail.desc;
			}
		}
	}
	
	/**
	 * 题目类型
	 * @author lijm
	 *
	 */
	public enum testType {
		Only("01", "单选题"), Multiple("02", "多选"),Isor("03", "是否题"),
		Ques("04", "问答题"),Case("05", "案例分析");

		String value;
		String desc;

		testType(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}		

		public static String getDescByValue(String value) {
			if (Only.toString().equals(value)) {
				return Only.desc;
			} else if (Multiple.toString().equals(value)) {
				return Multiple.desc;
			} else if (Isor.toString().equals(value)) {
				return Isor.desc;
			}else if (Ques.toString().equals(value)) {
				return Ques.desc;
			}else {
				return Case.desc;
			}
		}
	}
	/**
	 * 测试类型
	 * @author lijm
	 *
	 */
	public enum testColumn {
		
		Test("01", "小测试"), Examination("02", "考试"),Task("03", "作业"),
		Paper("04", "论文");

		String value;
		String desc;

		testColumn(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public String getValue() {
			return this.value;
		}

		public String getDesc() {
			return this.desc;
		}		

		public static String getDescByValue(String value) {
			if (Test.toString().equals(value)) {
				return Test.desc;
			} else if (Examination.toString().equals(value)) {
				return Examination.desc;
			} else if (Task.toString().equals(value)) {
				return Task.desc;
			}else {
				return Paper.desc;
			}
		}
	}
}
