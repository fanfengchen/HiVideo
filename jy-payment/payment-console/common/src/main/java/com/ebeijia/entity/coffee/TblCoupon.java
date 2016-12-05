package com.ebeijia.entity.coffee;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lf on 2016/9/19.
 */
@Entity
@Table(name = "tbl_coupon")
public class TblCoupon {

    private int id;
    private Date activeDate;        //开始时间
    private Date expireDate;        //取消时间
    private String couponName;      //奖品名称
    private Integer couponIntegral; //奖品积分
    private Integer recCount;       //
    private String couponDesc;
    private String imageUrl;        //图片地址
    private String couponType;
    private String state;       //状态

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false,length = 11)
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Column(name = "active_date" ,columnDefinition = "TIMESTAMP")
    public Date getActiveDate(){
        return activeDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    public void setActiveDate(Date activeDate){
        this.activeDate = activeDate;
    }

    @Column(name = "expire_date" , columnDefinition = "TIMESTAMP")
    public Date getExpireDate(){
        return expireDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    public void setExpireDate(Date expireDate){
        this.expireDate = expireDate;
    }

    @Column(name = "coupon_name",length = 255)
    public String getCouponName(){
        return couponName;
    }
    public void setCouponName(String couponName){
        this.couponName = couponName;
    }

    @Column(name = "coupon_integral",length = 11)
    public Integer getCouponIntegral(){
        return couponIntegral;
    }
    public void setCouponIntegral(Integer couponIntegral){
        this.couponIntegral = couponIntegral;
    }

    @Column(name = "rec_count" , length = 11)
    public Integer getRecCount(){
        return recCount;
    }
    public void setRecCount(Integer recCount){
        this.recCount = recCount;
    }

    @Column(name = "coupon_desc" , length = 255)
    public String getCouponDesc(){
        return couponDesc;
    }
    public void setCouponDesc(String couponDesc){
        this.couponDesc = couponDesc;
    }

    @Column(name = "image_url" , length = 255)
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    @Column(name = "coupon_type",length = 2)
    public String getCouponType(){
        return couponType;
    }
    public void setCouponType(String couponType){
        this.couponType = couponType;
    }

    @Column(name = "state",length = 2)
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }
}
