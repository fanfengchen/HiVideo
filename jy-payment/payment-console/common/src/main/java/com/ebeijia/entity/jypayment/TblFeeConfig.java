package com.ebeijia.entity.jypayment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lf on 2016/10/9.
 */
@Entity
@Table(name = "tbl_fee_config")
public class TblFeeConfig implements Serializable{
    private int id;
    private String oprType;
    private String feeType;
    private String amt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false,length = 11)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "opr_type", length = 2)
    public String getOprType(){
        return oprType;
    }
    public void setOprType(String oprType){
        this.oprType = oprType;
    }

    @Column(name = "fee_type",length = 2)
    public String getFeeType(){
        return feeType;
    }
    public void setFeeType(String feeType){
        this.feeType = feeType;
    }

    @Column(name = "amt" ,length = 12)
    public String getAmt(){
        return amt;
    }
    public void setAmt(String amt){
        this.amt = amt;
    }
}
