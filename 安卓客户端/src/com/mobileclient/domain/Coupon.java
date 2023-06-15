package com.mobileclient.domain;

import java.io.Serializable;

public class Coupon implements Serializable {
    /*优惠券id*/
    private int couponId;
    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    /*优惠券名称*/
    private String couponName;
    public String getCouponName() {
        return couponName;
    }
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    /*金额*/
    private float couponMoney;
    public float getCouponMoney() {
        return couponMoney;
    }
    public void setCouponMoney(float couponMoney) {
        this.couponMoney = couponMoney;
    }

    /*发放商家*/
    private String sellerObj;
    public String getSellerObj() {
        return sellerObj;
    }
    public void setSellerObj(String sellerObj) {
        this.sellerObj = sellerObj;
    }

    /*发放用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*过期时间*/
    private String couponTime;
    public String getCouponTime() {
        return couponTime;
    }
    public void setCouponTime(String couponTime) {
        this.couponTime = couponTime;
    }

}