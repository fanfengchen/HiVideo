package com.ebeijia.entity.coffee;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created with com.ebeijia.entity.coffee
 * User : zc
 * Date : 2016/10/24
 */
public class TblOrdersDetail {
    private Long id;
    private String payAccountNo;
    private String sentResult;
    private Long needPay;
    private String sentNo;
    private Long prestoreAmt;
    private Long orderId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false,length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "pay_account_no", length = 32)
    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    @Column(name = "sent_result", length = 2)
    public String getSentResult() {
        return sentResult;
    }

    public void setSentResult(String sentResult) {
        this.sentResult = sentResult;
    }

    @Column(name = "need_pay", length = 20)
    public Long getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Long needPay) {
        this.needPay = needPay;
    }

    @Column(name = "sent_no", length = 20)
    public String getSentNo() {
        return sentNo;
    }

    public void setSentNo(String sentNo) {
        this.sentNo = sentNo;
    }

    @Column(name = "prestore_amt", length = 20)
    public Long getPrestoreAmt() {
        return prestoreAmt;
    }

    public void setPrestoreAmt(Long prestoreAmt) {
        this.prestoreAmt = prestoreAmt;
    }

    @Column(name = "order_id", length = 20)
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
