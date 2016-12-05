package com.ebeijia.dto.socketModels;

/**
 * Created by LiYan on 2016/9/26.
 */
public class WaterPayReq {
    private String functionCode="2001";//功能代码
    private String terminalCode;//终端代码
    private String command;//口令
    private String userNo;//户号

    public String getThridPayment() {
        return thridPayment;
    }

    public void setThridPayment(String thridPayment) {
        this.thridPayment = thridPayment;
    }

    private String thridPayment;//第三方业务流水
    private String amt;//实缴金额

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
