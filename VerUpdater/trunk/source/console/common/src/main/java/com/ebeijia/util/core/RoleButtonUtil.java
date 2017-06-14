package com.ebeijia.util.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoleButtonUtil {

    //状态
    private static String STATE_ON = "ON";
    private static String STATE_OFF = "OFF";

    //管理员按钮
    private static String BTN_SYS_UPDNAME = "管理员修改";
    private static String BTN_SYS_ADDNAME = "管理员增加";
    private static String BTN_SYS_DELNAME = "管理员删除";

    //角色按钮
    private static String BTN_ROLE_UPDNAME = "角色修改";
    private static String BTN_ROLE_ADDNAME = "角色增加";
    private static String BTN_ROLE_DELNAME = "角色删除";

    //机构按钮
    private static String BTN_BRH_UPDNAME = "机构修改";
    private static String BTN_BRH_ADDNAME = "机构增加";
    private static String BTN_BRH_DELNAME = "机构删除";

    //商户按钮
    private static String BTN_MCHT_UPDNAME = "商户修改";
    private static String BTN_MCHT_ADDNAME = "商户增加";
    private static String BTN_MCHT_DELNAME = "商户删除";

    //微信账户按钮
    private static String BTN_ACCOUNT_BOND = "微信账户绑定";
    private static String BTN_ACCOUNT_ADDNAME = "微信账户增加";
    private static String BTN_ACCOUNT_UPDNAME = "微信账户修改";
    private static String BTN_ACCOUNT_DELNAME = "微信账户删除";

    //关注者按钮
    private static String BTN_SUBSCRIBE_UPDGROUP = "组别编辑";
    private static String BTN_SUBSCRIBE_DELGROUP = "组别删除";
    private static String BTN_SUBSCRIBE_ADDGROUP = "组别新增";
    private static String BTN_SUBSCRIBE_CONFIGGROUP = "组别配置";
    private static String BTN_SUBSCRIBE_SYN = "关注着同步";

    //消息按钮
    private static String BTN_MSG_AUTORESP = "自动回复保存";
    private static String BTN_MSG_KEYWORDDEL = "关键字删除";

    //自定义菜单
    private static String BTN_MENU_UPDNAME = "自定义菜单保存";
    private static String BTN_MENU_DELNAME = "自定义菜单删除";
    private static String BTN_MENU_SYN = "自定义菜单发布";

    //素材按钮
    private static String BTN_MASS_ADDNAME = "自定义菜单保存";
    private static String BTN_MASS_DELNAME = "自定义菜单删除";

    //图文按钮
    private static String BTN_FODDER_ADDNAME = "图文保存";
    private static String BTN_FODDER_UPDNAME = "图文编辑";
    private static String BTN_FODDER_DELNAME = "图文删除";

    //群发按钮
    private static String BTN_SEND_GROUP = "分组群发";
    private static String BTN_SEND_USR = "筛选群发";

    public static java.util.Map<String, List<Map<String, String>>> getRoleButton(List<String> list1, List<String> list2) {
        Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        for (String funcId : list2) {
            Map<String, String> mapfuc = new HashMap<String, String>();
            if (list1.contains(funcId)) {
                switch (funcId) {
                    case "100004":
                        mapfuc.put(funcId, BTN_SYS_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100005":
                        mapfuc.put(funcId, BTN_SYS_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100006":
                        mapfuc.put(funcId, BTN_SYS_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100014":
                        mapfuc.put(funcId, BTN_ROLE_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100015":
                        mapfuc.put(funcId, BTN_ROLE_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100016":
                        mapfuc.put(funcId, BTN_ROLE_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100034":
                        mapfuc.put(funcId, BTN_BRH_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100035":
                        mapfuc.put(funcId, BTN_BRH_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100036":
                        mapfuc.put(funcId, BTN_BRH_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100044":
                        mapfuc.put(funcId, BTN_MCHT_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100045":
                        mapfuc.put(funcId, BTN_MCHT_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "100046":
                        mapfuc.put(funcId, BTN_MCHT_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200003":
                        mapfuc.put(funcId, BTN_ACCOUNT_BOND);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200004":
                        mapfuc.put(funcId, BTN_ACCOUNT_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200005":
                        mapfuc.put(funcId, BTN_ACCOUNT_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200006":
                        mapfuc.put(funcId, BTN_ACCOUNT_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200012":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_UPDGROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200013":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_DELGROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200014":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_ADDGROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200015":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_CONFIGGROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200016":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_SYN);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200023":
                        mapfuc.put(funcId, BTN_MSG_AUTORESP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200024":
                        mapfuc.put(funcId, BTN_MSG_KEYWORDDEL);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200032":
                        mapfuc.put(funcId, BTN_MENU_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200033":
                        mapfuc.put(funcId, BTN_MENU_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200034":
                        mapfuc.put(funcId, BTN_MENU_SYN);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200043":
                        mapfuc.put(funcId, BTN_MASS_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200044":
                        mapfuc.put(funcId, BTN_MASS_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200052":
                        mapfuc.put(funcId, BTN_FODDER_ADDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200053":
                        mapfuc.put(funcId, BTN_FODDER_UPDNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200054":
                        mapfuc.put(funcId, BTN_FODDER_DELNAME);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200065":
                        mapfuc.put(funcId, BTN_SEND_GROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200066":
                        mapfuc.put(funcId, BTN_SEND_USR);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                }
            }else{
                switch (funcId) {
                    case "100004":
                        mapfuc.put(funcId, BTN_SYS_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100005":
                        mapfuc.put(funcId, BTN_SYS_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100006":
                        mapfuc.put(funcId, BTN_SYS_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100014":
                        mapfuc.put(funcId, BTN_ROLE_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100015":
                        mapfuc.put(funcId, BTN_ROLE_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100016":
                        mapfuc.put(funcId, BTN_ROLE_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100034":
                        mapfuc.put(funcId, BTN_BRH_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100035":
                        mapfuc.put(funcId, BTN_BRH_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100036":
                        mapfuc.put(funcId, BTN_BRH_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100044":
                        mapfuc.put(funcId, BTN_MCHT_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100045":
                        mapfuc.put(funcId, BTN_MCHT_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "100046":
                        mapfuc.put(funcId, BTN_MCHT_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200003":
                        mapfuc.put(funcId, BTN_ACCOUNT_BOND);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200004":
                        mapfuc.put(funcId, BTN_ACCOUNT_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200005":
                        mapfuc.put(funcId, BTN_ACCOUNT_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200006":
                        mapfuc.put(funcId, BTN_ACCOUNT_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200012":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_UPDGROUP);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200013":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_DELGROUP);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200014":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_ADDGROUP);
                        mapfuc.put("state", STATE_ON);
                        list.add(mapfuc);
                        break;
                    case "200015":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_CONFIGGROUP);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200016":
                        mapfuc.put(funcId, BTN_SUBSCRIBE_SYN);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200023":
                        mapfuc.put(funcId, BTN_MSG_AUTORESP);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200024":
                        mapfuc.put(funcId, BTN_MSG_KEYWORDDEL);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200032":
                        mapfuc.put(funcId, BTN_MENU_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200033":
                        mapfuc.put(funcId, BTN_MENU_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200034":
                        mapfuc.put(funcId, BTN_MENU_SYN);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200043":
                        mapfuc.put(funcId, BTN_MASS_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200044":
                        mapfuc.put(funcId, BTN_MASS_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200052":
                        mapfuc.put(funcId, BTN_FODDER_ADDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200053":
                        mapfuc.put(funcId, BTN_FODDER_UPDNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200054":
                        mapfuc.put(funcId, BTN_FODDER_DELNAME);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200065":
                        mapfuc.put(funcId, BTN_SEND_GROUP);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                    case "200066":
                        mapfuc.put(funcId, BTN_SEND_USR);
                        mapfuc.put("state", STATE_OFF);
                        list.add(mapfuc);
                        break;
                }
            }
            map.put("btnList",list);
        }
        return map;
    }
}
