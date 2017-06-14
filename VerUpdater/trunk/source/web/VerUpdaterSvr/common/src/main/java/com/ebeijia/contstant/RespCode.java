package com.ebeijia.contstant;

public class RespCode {
	public static String SUCCESS_CODE = "0000";
	public static String ERROR_CODE = "0001";
	public static String PARAM_ERROR = "0002";
	public static String PARAM_NULL = "0003";
	public static String CHANNEL_ERROR = "0004";
	public static String TOKEN_CODE = "0005";
	public static String CHANNEL_FORBIDDEN = "0006";
	public static String SIGN_CODE = "0007";
	public static String PACK_ERR_MSG = "0008";
	public static String RESULT_NULL = "0009";
	public static String GET_DATA_FILED="0010";

	public static String Msg(String code) {
		String result = null;
		switch (code) {
		case "0000":
			result = "操作成功";
			break;
		case "0001":
			result = "操作失败";
			break;
		case "0002":
			result = "数据错误";
			break;
		case "0003":
			result = "数据为空";
			break;
		case "0004":
			result = "渠道错误";
			break;
		case "0005":
			result = "TOKEK错误";
			break;
		case "0006":
			result = "该软件的渠道已禁用";
			break;
		case "0007":
			result = "验证签名失败";
			break;
		case "0008":
			result = " 报文验证失败";
			break;
		case "0009":
			result = "查询结果为空";
			break;
		case "0010":
			result = "获取数据失败";
			break;
		}
		return result;
	}
}
