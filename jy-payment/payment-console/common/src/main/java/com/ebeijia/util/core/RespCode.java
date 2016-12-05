package com.ebeijia.util.core;

/**
 * RespCode
 *
 * @author zhicheng.xu
 * @date 16/6/22
 */
public class RespCode {
    /**微信状态**/
    public static int WECHAT_SUCCESS = 0;
    public static int WECHAT_ERROR = 1;
    /**系统状态**/
    public static String SUCCESS_CODE = "00";
    public static String ERROR_CODE = "01";
    public static String PARAM_ERROR = "02";
    public static String LOG_CODE = "03";
    public static String TOKEN_CODE = "04";
    public static String SYS_ERR_MSG = "05";
    public static String PARAM_NULL_ERROR = "06";
    public static String PARAM_SQL_ERROR = "07";
    public static String PARAM_STR_ERROR = "08";
    public static String PARAM_NUM_ERROR = "09";
    public static String PARAM_NUM2_ERROR = "10";
    public static String PARAM_REG_ERROR = "11";

    /*操作状态*/
    public static String OPR_ERROR_CODE = "0001" ;
    public static String OPR_ERROR_ADD = "0002" ;
    /**参数对象为空**/
    public static String OBJECT_ERROR_NULL = "0007";
    //文件类型错误
    public static String FILE_TYPE_ERROR = "0008";
    /**状态异常**/
    public static String STATUS_ERROR = "0009";

    /**角色操作异常**/
    public static String OPER_ROLE_ERROR_UNIQ = "0040" ;
    public static String OPER_ROLE_ERROR_DONOT = "0041" ;
    public static String OPER_NAME_ERROR_CONFIT = "0042" ;
    public static String OPER_NAME_ERROR_DONOTDEL = "0043" ;
    public static String ROLE_NAME_ERROR_UNIQ = "0044" ;
    public static String OPER_PWD_ISEXIST = "0045" ;
    public static String OPER_PWDORNAME_ERROR = "0046" ;

    /**登录异常**/
    public static String lOGIN_USER_ERROR_EMPTY = "0050" ;
    public static String LOGIN_PWD_ERROR_NOTVALID = "0051" ;
    public static String lOGIN_USER_ERROR_EXIS = "0052" ;
    public static String LOGIN_USER_ERROR_INPUT = "0053" ;
    public static String LOGIN_USER_ERROR_WITHOUT = "0054" ;

    /**广告管理**/
    public static String BAN_ERROR_ADD = "0110" ;
    public static String BAN_ERROR_DEL = "0111" ;
    public static String BAN_ERROR_UPD = "0112" ;
    public static String BAN_ERROR_SEL = "0113" ;

    /**文章管理**/
    public static String DEP_ERROR_ADD = "0121" ;
    public static String DEP_ERROR_DEL = "0122" ;
    public static String DEP_ERROR_UPD = "0123" ;
    public static String DEP_ERROR_SEL = "0124" ;

    /**软件管理**/
    public static String SOFT_ERROR_ADD = "0131" ;
    public static String SOFT_ERROR_DEL = "0132" ;
    public static String SOFT_ERROR_UPD = "0133" ;
    public static String SOFT_ERROR_SEL = "0134" ;

    /**账号获取**/
    public static String OPENID_ERROR_GET = "0141" ;
    /**模板发送失败**/
    public static String TEMP_ERROR_SEND = "0142" ;

    /**学生管理**/
    public static String STU_ERROR_ADD = "0151" ;
    public static String STU_ERROR_DEL = "0152" ;
    public static String STU_ERROR_UPD = "0153" ;
    public static String STU_ERROR_SEL = "0154" ;
    public static String STU_ERROR_EXP = "0155" ;

    /**支付项目管理**/
    public static String PAY_ERROR_ADD = "0161" ;
    public static String PAY_ERROR_DEL = "0162" ;
    public static String PAY_ERROR_UPD = "0163" ;
    public static String PAY_ERROR_SEL = "0164" ;

    /**角色管理**/
    public static String ROLE_ERROR_ADD = "0171" ;
    public static String ROLE_ERROR_DEL = "0172" ;
    public static String ROLE_ERROR_UPD = "0173" ;
    public static String ROLE_ERROR_SEL = "0174" ;
    public static String ROLE_PWD_CONFLICT = "0175" ;
    public static String ROLE_NOTEXIST_ERROR = "0176" ;
    public static String ROLE_ADMIN_DELERROR = "0177" ;
    public static String ROLE_ISUSERING_NOW = "0178" ;

    /**操作流水**/
    public static String TXN_ERROR_SEL = "0181" ;

    /**微信素材**/
    public static String WM_ERROR_ADD = "0192" ;



    /**机构管理**/
    public static String BRH_ERROR_ADD = "0201";
    public static String BRH_ERROR_DEL = "0202";
    public static String BRH_ERROR_UPD = "0203";
    public static String BRH_ERROR_SEL = "0204";
    public static String BRH_ERROR_UNIQ = "0205";

    /**商户管理**/
    public static String MCHT_ERROR_ADD = "0210";
    public static String MCHT_ERROR_DEL = "0211";
    public static String MCHT_ERROR_UPD = "0212";
    public static String MCHT_ERROR_SEL = "0213";
    public static String MCHT_ERROR_UNIQ = "0214";

    /**微信图文**/
    public static String WECHAT_ERROR_UPLOAD_FODDER = "1111";
    public static String WECHAT_ERROR_UPLOAD_VIDEO = "1112";
    /**微信群发**/
    public static String WECHAT_ERROR_MASS_QUERY = "1113";
    public static String WECHAT_MASS_SUCCESS = "1114";
    public static String WECHAT_MASS_ERROR = "1115";
    /**群发筛选图文**/
    public static String WECHAT_MASS_FILTERFODDER_SUCCESS = "1116";
    /**群发筛选图文**/
    public static String WECHAT_MASS_FILTERFODDER_ERROE = "1117";

    /**微信账号绑定**/
    public static String WECHAT_ACCOUNT_REBAND_ERROE = "1121";
    public static String WECHAT_ACCOUNT_BAND_SUCCESS = "1122";
    public static String WECHAT_ACCOUNT_BAND_ERROE = "1123";
    public static String WECHAT_ACCOUNT_QRY_ERROE = "1124";
    public static String WECHAT_ACCOUNT_NOTEXIST = "1125";
    public static String WECHAT_ACCOUNT_UPDSUCCESS = "1126";
    public static String WECHAT_ACCOUNT_UPDERROR = "1127";
    public static String WECHAT_MCHT_MOTFUND = "1128";
    public static String WECHAT_ACCOUNT_DELERROR = "1129";


    /**微信多媒体**/
    public static String WECHAT_MEDIA_UPDERROR = "1131";
    public static String WECHAT_MEDIA_UPDERROR_FILENOTEXIST = "1132";
    public static String WECHAT_MEDIA_DELERROR = "1133";
    public static String WECHAT_MEDIA_FODDERNOTEXITS = "1134";
    public static String WECHAT_MEDIA_UPFODDERDERROR = "1135";
    public static String WECHAT_MEDIA_UPMEDIAERROR = "1136";
    public static String WECHAT_MEDIA_FODDERCOUNTERROR = "1137";
    public static String WECHAT_MEDIA_SELFODDERCOUNTERROR = "1138";
    public static String WECHAT_MEDIA_UPLOAD_MATERIAL_ERROR = "1139";
    public static String WECHAT_MEDIA_DOWNLOAD_MATERIAL_ERROR = "1140";
    public static String WECHAT_MEDIA_DOWNLOAD_FODDER_ERROR = "1141";


    /**微信消息**/
    public static String WECHAT_MSG_GET_ERROR = "1151";
    public static String WECHAT_MSG_FODDER_ERROR = "1152";
    public static String WECHAT_MSG_KEY_EXIST = "1153";
    public static String WECHAT_MSG_ADD_ERROR = "1154";
    public static String WECHAT_MSG_AUTORESSEL_ERROR = "1155";
    public static String WECHAT_MSG_REP_NOTEXITS_ERROR = "1156";
    public static String WECHAT_MSG_REP_SEL_ERROR = "1157";
    public static String WECHAT_MSG_REP_DEL_ERROR = "1158";


    /**微信菜单**/
    public static String WECHAT_MENU_ADD_ERROR = "1161";
    public static String WECHAT_MENU_SEL_ERROR = "1162";
    public static String WECHAT_MENU_SYS_ERROR = "1163";
    public static String WECHAT_MENU_NOTEXIST_ERROR = "1164";
    public static String WECHAT_MENU_UPD_ERROR = "1165";
    public static String WECHAT_MENU_DEL_ERROR = "1166";

    /**微信分组**/
    public static String WECHAT_GUP_ADD_ERROR = "1171";
    public static String WECHAT_GUP_KEY_EXITS = "1172";
    public static String WECHAT_GUP_SEL_ERROR = "1173";
    public static String WECHAT_GUP_NOTEXITS_ERROR = "1174";
    public static String WECHAT_GUP_UPD_ERROR = "1175";
    public static String WECHAT_GUP_DEL_ERROR = "1176";

    /**微信用户**/
    public static String WECHAT_USR_SEL_ERROR = "1181";
    public static String WECHAT_USR_SYS_ERROR = "1182";
    public static String WECHAT_USR_REMARK_ERROR = "1183";
    public static String WECHAT_USR_UPREMARK_ERROR = "1184";
    public static String WECHAT_USR_MOVE_ERROR = "1185";

    /**微信公告**/
    public static String WECHAT_NOTICE_SEL_ERROR = "1201";
    public static String WECHAT_NOTICE_ADD_ERROR = "1202";
    public static String WECHAT_NOTICE_DEL_ERROR = "1203";
    public static String WECHAT_NOTICE_UPD_ERROR = "1204";

    /**订单详情**/
    public static String WECHAT_ORDERDETAIL_SEL_ERROR = "1211";

    /**微信公告**/
    public static String WECHAT_ACCOUNTINFO_SEL_ERROR = "1221";

    //文章
    public static String DEP_STATE_OPERATE_FAIL = "3021";
    public static String DEP_DocContent_NULL = "3022";

    //广告
    public static String BAN_STATE_OPERATE_FALL="4022";
    public static String BAN_SEQ_LENGTH_ERROR="4023";

    //软件
    public static String SOFT_VERNO_OPERATE_ADD="5023";
    public static String SOFT_VERNO_OPERATE_UPDATE="5024";

    //上传图片提示
    public static String PIC_PNG_JPG_ERROR="6025";

    //coffee
    public static String COFFEE_DATA_NONENTITY="7001";
    public static String COFFEE_PARAM_ERROR="7002";
    public static String COFFEE_COUPON_OUT_DATE="7003";
    public static String COFFEE_COUPON_USE="7004";
    public static String COFFEE_COUPON_USE_ERROR="7005";
    public static String COUPON_ADD_ERROR="7010";
    public static String COUPON_UPDATE_ERROR="7011";
    public static String COUPON_ADD_ERRNULL="7012";
    public static String COUPON_UPDATE_ERRNULL="7013";
    public static String COUPON_ERR_LIMIT="7014";
    public static String COUPON_DELETE_ERR="7015";
    public static String COUPON_ADD_ERR="7016";
    public static String COFFEE_UPLOAD_ERROR="7017";

    //江油
    public static String JY_NOTNULL="7018";
    public static String JY_CHOOSEONE="7019";
    public static String JY_CHOOSEERR="7020";

    public static String JY_PARAM_ERROR="7021";
    public static String JY_PARAM_MONTH_ERROR="7022";
    public static String JY_PARAM_DATE_ERROR="7023";

    public static String JY_ERR_LIMIT="7024";
    public static String JY_DATE_ERR="7025";
    public static String JY_TIME_ERR="7026";


    public static String Msg(String code){
        String result = null;
        switch (code) {
            case "00":result = "操作成功";break;
            case "01":result="操作失败";break;
            case "02":result="参数长度或格式不正确";break;
            case "04":result="TOKEK超时";break;
            case "05":result="系统异常,请联系管理员或稍后重试";break;
            case "06":result="参数为空";break;
            case "07":result="参数存在非法字符";break;
            case "08":result="字符串长度不正确";break;
            case "09":result="数字长度不正确";break;
            case "10":result="精度长度不正确";break;
            case "11":result="参数格式不正确";break;
            case "0001":result="操作员用户名称已经存在";break;
            case "0002":result="新增作员失败";break;
            case "0007":result="对象参数不能为空";break;
            case "0008":result="文件格式错误";break;
            case "0009":result="状态修改失败";break;
            case "0040":result="管理员只能有一个";break;
            case "0041":result="管理员为最高权限不能修改";break;
            case "0042":result="角色名称已存在";break;
            case "0043":result="管理员不能删除";break;
            case "0044":result="角色名已存在";break;
            case "0045":result="新密码与旧密码不能相同";break;
            case "0046":result="账号或密码错误";break;


            case "0050":result="用户名和密码不能为空！";break;
            case "0051":result="密码长度不得小于六位，请重新输入！";break;
            case "0052":result="用户名不存在，请重新输入！";break;
            case "0053":result="用户名或密码有误，请重新输入！";break;
            case "0054":result="该用户没用权限!请联系管理员分配权限!";break;
            case "0110":result="新增广告失败";break;
            case "0111":result="删除广告失败";break;
            case "0112":result="修改广告失败";break;
            case "0113":result="查询广告失败";break;
            case "0121":result="新增文章失败";break;
            case "0122":result="删除文章失败";break;
            case "0123":result="修改文章失败";break;
            case "0124":result="查询文章失败";break;
            case "0131":result="新增软件失败";break;
            case "0132":result="删除软件失败";break;
            case "0133":result="修改软件失败";break;
            case "0134":result="查询软件失败";break;
            case "0141":result="获取账号失败，请稍后重试";break;
            case "0142":result="模板发送失败";break;
            case "0151":result="新增学生失败";break;
            case "0152":result="删除学生失败";break;
            case "0153":result="修改学生失败";break;
            case "0154":result="查询学生失败";break;
            case "0155":result="学生信息导入失败";break;
            case "0161":result="新增支付项目失败";break;
            case "0162":result="删除支付项目失败";break;
            case "0163":result="修改支付项目失败";break;
            case "0164":result="查询支付项目失败";break;
            case "0171":result="新增角色失败";break;
            case "0172":result="删除角色失败";break;
            case "0173":result="修改角色失败";break;
            case "0174":result="查询角色失败";break;
            case "0176":result="该角色不存在";break;
            case "0177":result="管理员角色不予删除";break;
            case "0178":result="该角色被使用不予删除";break;


            case "0181":result="流水查询失败";break;
            case "0191":result="素材新增失败";break;
            case "0201":result="新增机构失败";break;
            case "0202":result="删除机构失败";break;
            case "0203":result="修改机构失败";break;
            case "0204":result="查询机构失败";break;
            case "0205":result="机构编码已存在";break;
            case "0214":result="商户号已存在";break;
            case "1111":result="微信上传群发图文失败,请联系管理员或稍后再试";break;
            case "1112":result="微信上传群发视频失败,请联系管理员或稍后再试";break;
            case "1113":result="微信群发查询失败,请联系管理员或稍后再试";break;
            case "1114":result="群发消息成功(注:一个用户在一个月之内可以使用群发接口发4次消息,之后此月便收不到信息)";break;
            case "1115":result="群发消息失败,请联系管理员或稍后再试";break;
            case "1116":result="微信筛选群发消息成功";break;
            case "1117":result="微信筛选群发消息失败,请联系管理员或稍后再试";break;
            case "1121":result="该账户已经绑定";break;
            case "1122":result="绑定微信账户成功";break;
            case "1123":result="绑定失败,请联系管理员或稍后再试";break;
            case "1124":result="微信账号查询失败,请联系管理员或稍后再试";break;
            case "1125":result="微信账号不存在";break;
            case "1126":result="微信账号修改成功";break;
            case "1127":result="微信账户修改失败,请联系管理员或稍后再试";break;
            case "1128":result="没有找到该商户的微信信息";break;
            case "1129":result="微信账户删除失败,请联系管理员或稍后再试";break;
            case "1131":result="微信多媒体修改失败";break;
            case "1132":result="多媒体不存在";break;
            case "1133":result="微信永久素材删除失败,请联系管理员或稍后再试";break;
            case "1134":result="图文不存在";break;
            case "1135":result="微信修改图文失败,请联系管理员或稍后再试";break;
            case "1136":result="微信多媒体查询失败,请联系管理员或稍后再试";break;
            case "1137":result="查询素材总数失败";break;
            case "1138":result="微信图文查询失败,请联系管理员或稍后再试";break;
            case "1139":result="上传素材失败,请联系管理员或稍后再试";break;
            case "1140":result="下载多媒体失败,请联系管理员或稍后再试";break;
            case "1141":result="上传图文失败,请联系管理员或稍后再试";break;
            case "1151":result="微信收到消息查询失败,请联系管理员或稍后再试";break;
            case "1152":result="微信图文格式不正确";break;
            case "1153":result="关键字已存在！";break;
            case "1154":result="微信回复消息新增失败,请联系管理员或稍后再试！";break;
            case "1155":result="微信自动回复查询失败,请联系管理员或稍后再试！";break;
            case "1156":result="改回复消息不存在";break;
            case "1157":result="微信回复消息修改失败,请联系管理员或稍后再试";break;
            case "1158":result="删除回复消息失败,请联系管理员或稍后再试";break;
            case "1161":result="新增微信菜单失败";break;
            case "1162":result="微信菜单查询失败,请联系管理员或稍后再试";break;
            case "1163":result="同步微信菜单失败";break;
            case "1164":result="菜单不存在";break;
            case "1165":result="修改菜单失败,请联系管理员或稍后再试";break;
            case "1166":result="删除菜单失败,请联系管理员或稍后再试";break;
            case "1171":result="新增用户组别失败,请联系管理员或稍后再试";break;
            case "1172":result="一个公众账号，最多支持创建100个分组";break;
            case "1173":result="关注者组别查询失败,请联系管理员或稍后再试";break;
            case "1174":result="组别不存在";break;
            case "1175":result="修改用户组别失败,请联系管理员或稍后再试";break;
            case "1176":result="删除用户组别失败,请联系管理员或稍后再试";break;
            case "1181":result="微信用户查询失败,请联系管理员或稍后再试";break;
            case "1182":result="微信同步用户失败,请联系管理员或稍后再试";break;
            case "1183":result="该关注者不存在，请重新选择";break;
            case "1184":result="微信用户修改备注失败,请联系管理员或稍后再试";break;
            case "1185":result="微信移动用户分组失败,请联系管理员或稍后再试";break;
            case "1201":result="公告通知查询失败,请联系管理员或稍后再试";break;
            case "1202":result="新增公告通知失败,请联系管理员或稍后再试";break;
            case "1203":result="通知公告删除失败,请联系管理员或稍后再试";break;
            case "1204":result="通知公告内容修改失败,请联系管理员或稍后再试";break;
            case "1211":result="订单详情查询失败,请联系管理员或稍后再试";break;
            case "1212":result="綁定賬戶信息查询失败,请联系管理员或稍后再试";break;

            case "3021":result="文章在开启状态下不能删除";break;
            case "3022":result="文章内容不能为空";break;
            case "4022":result="广告在开启状态下不能删除";break;
            case "4023":result="顺序请保持在两位数之内";break;
            case "5023":result="版本号重复";break;
            case "5024":result="版本号重复，请更新版本";break;

            case "6025":result="图片格式不正确，请上传jpg或png格式";break;

            case "7001":result="查询的券号不存在，请确认后再查询";break;
            case "7002":result="传入的参数错误";break;
            case "7003":result="券已经过期";break;
            case "7004":result="券已经使用过了";break;
            case "7005":result="券还没有被使用不能修改";break;
            case "7010":result="此奖品已存在，不能重复添加";break;
            case "7011":result="此奖品号已存在，无法修改";break;
            case "7012":result="添加数据不能为空";break;
            case "7013":result="请选中一条进行修改";break;
            case "7014":result="输入的分页参数错误";break;
            case "7015":result="删除失败，请重新操作";break;
            case "7016":result="使用时间不能大于结束时间";break;
            case "7017":result="文件上传失败";break;
            case "7018":result="输入的参数缺失";break;
            case "7019":result="请输入限制金额";break;
            case "7020":result="输入的限制金额错误，最多只能输入两位小数";break;

            case "7021":result="输入的参数缺失";break;
            case "7022":result="选择的日期区间不能大于九十天";break;
            case "7023":result="选择的开始时间不能大于结束时间";break;

            case "7024":result="输入的分页参数错误";break;
            case "7025":result="开始日期不能大于结束日期";break;
            case "7026":result="开始时间不能大于结束时间";break;

        }
        return result;
    }
}
