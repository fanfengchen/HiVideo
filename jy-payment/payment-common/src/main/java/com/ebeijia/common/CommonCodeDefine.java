package com.ebeijia.common;

public enum CommonCodeDefine {
    E1001("1001","openId失效，需要重新发起操作"),
    E1002("1002","code 失效"),
    E2001("2001","绑定失败"),
    ERROR_AMT("0001","金额格式错误"),
    ERROR_SUCCESS("0000",""),
    ERROR_NO_BIND("0002","未绑定"),
    ERROR_SYS("9999","系统繁忙"),
    ERROR_PARAM("9002","参数错误"),
    ERROR_PAY_SUCCESS("3000","调用威富通成功"),
    ERROR_PAY_FAIL("3001","调用威富通返回失败");
    private String code;

    private String desc;

    private CommonCodeDefine(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
