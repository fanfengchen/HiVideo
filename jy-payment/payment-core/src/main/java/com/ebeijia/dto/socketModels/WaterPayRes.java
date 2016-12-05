package com.ebeijia.dto.socketModels;

/**
 * Created by LiYan on 2016/9/26.
 */
public class WaterPayRes {
    private String resCode;//返回值
    private String copyPayment;//抄表系统流水

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getCopyPayment() {
        return copyPayment;
    }

    public void setCopyPayment(String copyPayment) {
        this.copyPayment = copyPayment;
    }
}
