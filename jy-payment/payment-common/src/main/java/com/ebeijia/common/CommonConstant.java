package com.ebeijia.common;

/**
 * 定义常量
 *
 * @author chyulin
 *
 */
public interface CommonConstant {
	String SIGN_TYPE_MD5 = "01";

	/**
	 * 文件类型-视频
	 */
	String FILE_TYPE_VEDIO = "01";
	String FILE_TYPE_AUDIO = "02";
	String FILE_TYPE_HANDOUT = "11";
	String FILE_TYPE_REFERENCE = "12";

	String FILE_TYPE_IMG = "21";

	/**
	 * 文件状态 可删除
	 */
	String FILE_STATE_DEL = "43";
	/**
	 * 文件状态 为使用
	 */
	String FILE_STATE_NOUSE = "42";
	/**
	 * 文件状态 已用
	 */
	String FILE_STATE_USE = "41";

	/**
	 * 问题类型 问答题：04
	 */
	String ESSAYQUES_TYPE_EQ = "04";

	/**
	 * 问题类型 案例分析：05
	 */
	String ESSAYQUES_TYPE_CASE = "05";

	String SMS_V_CODE_REGISTER = "01";
	String SMS_V_CODE_MODIFY = "02";
	String SMS_V_CODE_UNDO = "03";

	/**
	 * 消息默认未读
	 */
	String SMS_IsRead = "0";

	/**
	 * 支付状态
	 */
	enum PayStatus {
		NotPay("00","未支付"),
		PayUnPay("01","支付终止"),
		PaySuccess("10","支付成功" ),
		PayFail("20","支付失败"),
		RefundSuccess("30","退款成功"),
		RefundFail("31","退款失败");
		private String code, desc;

		PayStatus(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return code;
		}

		public String getDesc(){
			return desc;
		}

	}

	/**
	 * 支付状态
	 */
	enum PayResult {
		ResultSuccess("1","缴费成功"), ResultFail("0","缴费失败");
		String name, value;

		PayResult(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}
	/**
	 * 水气、远传水类型代码
	 */
	String WATER_PAY="01";
	String WATER_FAY_PAY="02";
	String GAS_PAY="03";

	/**
	 * 缴费时间限制类型
	 */
	String DATE_LIMIT="01";//
	String TIME_LIMIT="02";
	/**
	 * socket返回码
	 */
	String SOC_RES_SUCC="1";//成功
	String SOC_PARAM_FAIL="-1";//参数不正确
	String SOC_TERMINAL_FAIL="-2";//终端代码验证不通过
	String SOC_USERNO_FAIL="-3";//户号不存在
	String SOC_AMT_LESS="-4";//用户缴费金额小于欠费金额
	/**
	 * 绑定用户信息返回错误码
	 */

	String socResUserNoErr="-3";//户号不存在
	String socResBillNoArre="-7";//用户不欠费，不用交费

	/**
	 * 账户账单查询，调用方法返回结果code，用于判断方法返回信息
	 */
	String arreaImplResConst_succ="0";//调用结果状态成功
	String arreaImplResConst_par_err="-1";//参数缺失
	String arreaImplResConst_res_null="-2";//数据不存在
	String arreaImplResConst_fail="-3";//查询数据错误
	String arreaImplResConst_balance="-4";//用户不欠费，不需要缴费，还有余额
	String arreaWebService_err = "-1";//调用webService失败

	/**
	 * 账户查询，调用方法返回结果code，用于判断方法返回信息
	 */
	String userBillAccConst_succ="0";//调用结果状态成功
	String userBillAccConst_par_err="-1";//参数缺失
	String userBillAccConst_res_null="-2";//数据不存在
	String userBillAccConst_data_exist="-3";//户号已经存在

	String userBillAccount_succ = "0";//调用成功
	String userBillAccount_par_err="-1";//参数缺失

	/**
	 * payController返回code
	 */

}
