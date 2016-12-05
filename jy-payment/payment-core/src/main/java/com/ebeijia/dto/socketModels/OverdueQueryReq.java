package com.ebeijia.dto.socketModels;

/**
 * Created by LiYan on 2016/9/21.
 */
public class OverdueQueryReq {
    private String functionCode="1001";//功能代码
    private String terminalCode;//终端代码
    private String command;//口令
    private String userNo;//户号

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

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
}
