package com.ebeijia.videocore.constant;

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
	String FILE_STATE_DEL = "14";
	/**
	 * 文件状态 为使用
	 */
	String FILE_STATE_NOUSE = "13";
	/**
	 * 文件状态 已用
	 */
	String FILE_STATE_USE = "12";

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
	 * 角色列表
	 */
	String ROLE_MANAGE ="管理员";
	String ROLE_STUDENT ="学生";
	String ROLE_TEACHER ="老师";
	/**
	 * 消息默认未读
	 */
	String SMS_IsRead ="0";
}
