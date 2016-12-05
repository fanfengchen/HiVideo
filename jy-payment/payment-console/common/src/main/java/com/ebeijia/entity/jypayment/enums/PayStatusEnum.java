package com.ebeijia.entity.jypayment.enums;

/**
 * Created with com.ebeijia.entity.jypayment.enums
 * User : zc
 * Date : 2016/10/25
 */
public interface PayStatusEnum {

    /**
     * 支付状态
     */
    enum PayStatus {
        PayUnPay("01","待支付"),
        IsPay("02","支付中" ),
        PaySuccess("03","支付成功"),
        PayFail("04","支付失败"),
        RefundSuccess("05","已退款"),
        IsRefund("06","退款中"),
        RefundFail("07","退款失败");
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

        public static String getDesc(String code) {
            for (PayStatus c : PayStatus.values()) {
                if (code.equals(c.getCode())) {
                    return c.desc;
                }
            }
            return null;
        }
    }

    /**
     * 缴费结果
     */
    enum SendStatus {
        sendSuccess("01","缴费成功"),
        sendFail("02","缴费失败" );
        private String code, desc;

        SendStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }

        public String getDesc(){
            return desc;
        }

        public static String getDesc(String code) {
            for (SendStatus c : SendStatus.values()) {
                if (code.equals(c.getCode())) {
                    return c.desc;
                }
            }
            return null;
        }

    }

    enum AccountType {
        water("01","自来水"),
        watermeter("02","远传水表"),
        naturalgas("03","然气" );
        private String code, desc;
        AccountType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public String getCode(){
            return code;
        }

        public String getDesc(){
            return desc;
        }

        public static String getDesc(String code) {
            for (AccountType c : AccountType.values()) {
                if (code.equals(c.getCode())) {
                    return c.desc;
                }
            }
            return null;
        }
    }

}
