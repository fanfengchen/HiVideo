package com.ebeijia.util.core;


/**
 * 功能菜单编码
 * 验证权限
 * @author xiong.wang
 * 2016/6/27
 */
public class FuncCode {
    /**管理员管理**/
    public static String OPR_FUNC_ADDITION = "100001";//增加
    public static String OPR_FUNC_MAINTAIN = "100002";//维护
    public static String OPR_FUNC_QUERY = "100003";//查询

    /**角色管理**/
    public static String ROLE_FUNC_ADDITION = "100011";
    public static String ROLE_FUNC_MAINTAIN = "100012";
    public static String ROLE_FUNC_QUERY = "100013";

    /**流水管理**/
    public static String ACC_FUNC_OPRLOG = "100021";
    public static String ACC_FUNC_ORDLOG = "100022";
    public static String ACC_FUNC_TRANSLOG = "100023";

    /**文章管理**/
    public static String DEP_FUNC_MAINTAIN = "300001";
    public static String DEP_FUNC_ADDITION = "300002";

    /**广告管理**/
    public static String BAN_FUNC_MAINTAIN = "300011";
    public static String BAN_FUNC_ADDITION = "300012";


    /**软件管理**/
    public static String SOFT_FUNC_MAINTAIN = "300021";
    public static String SOFT_FUNC_ADDITION = "300022";



    /**学生缴费管理**/
    public static String STU_FUNC_ADDITION = "100061";
    public static String STU_FUNC_MAINTAIN = "100062";

    /**机构管理**/
    public static String BRH_FUNC_ADDITION = "100030";
    public static String BRH_FUNC_MAINTAIN = "100030";
    public static String BRH_FUNC_QUERY = "100030";

    /**机构管理**/
    public static String MCHT_FUNC_ADDITION = "100040";
    public static String MCHT_FUNC_MAINTAIN = "100040";
    public static String MCHT_FUNC_QUERY = "100040";

    /**微信账号管理**/
    public static String MCHT_FUNC_BANDACCOUNT = "100031";
    public static String MCHT_FUNC_ACCOUNTQRY = "100032";

    /**微信管理**/
    public static String WECHAT_FUNC_ORDER = "200000";//账单管理
    public static String WECHAT_FUNC_NOTICE = "200010";//通知公告
    public static String WECHAT_FUNC_REMINDER = "200020";//缴费提醒reminder
}
