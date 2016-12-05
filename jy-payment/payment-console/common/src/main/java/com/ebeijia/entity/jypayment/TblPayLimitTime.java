package com.ebeijia.entity.jypayment;

import javax.persistence.*;

/**
 * Created by lf on 2016/10/24.
 */
@Entity
@Table(name = "tbl_pay_limit_time")
public class TblPayLimitTime {

    private int id;
    private String feeType;      //缴费类型：01-普水、02-远程、03-燃气
    private String limitType;    //限制类型：01-按日期、02-按每天时间
    private String limitDateStart;       //限制开始时间：YYYY-MM-DDHH:mm:ss
    private String limitDateEnd;         //限制结束时间：YYYY-MM-DDHH:mm:ss
    private String limitTimeStart;       //按具体时间点-限制开始时间，hhmiss
    private String limitTimeEnd;         //按具体时间点-限制结束时间，hhmiss

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false,length = 2)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "fee_type",length = 2)
    public String getFeeType(){
        return feeType;
    }
    public void setFeeType(String feeType){
        this.feeType = feeType;
    }

    @Column(name = "limit_type",length = 2)
    public String getLimitType(){
        return limitType;
    }
    public void setLimitType(String limitType){
        this.limitType = limitType;
    }

    @Column(name = "limit_date_start",length = 14)
    public String getLimitDateStart(){
        return limitDateStart;
    }
    public void setLimitDateStart(String limitDateStart){
        this.limitDateStart = limitDateStart;
    }

    @Column(name = "limit_date_end",length = 14)
    public String getLimitDateEnd(){
        return limitDateEnd;
    }
    public void setLimitDateEnd(String limitDateEnd){
        this.limitDateEnd = limitDateEnd;
    }

    @Column(name = "limit_time_start",length = 8)
    public String getLimitTimeStart(){
        return limitTimeStart;
    }
    public void setLimitTimeStart(String limitTimeStart){
        this.limitTimeStart = limitTimeStart;
    }

    @Column(name = "limit_time_end",length = 8)
    public String getLimitTimeEnd(){
        return limitTimeEnd;
    }
    public void setLimitTimeEnd(String limitTimeEnd) {
        this.limitTimeEnd = limitTimeEnd;
    }
}
