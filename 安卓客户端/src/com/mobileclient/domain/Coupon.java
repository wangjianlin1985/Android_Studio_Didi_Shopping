package com.mobileclient.domain;

import java.io.Serializable;

public class Coupon implements Serializable {
    /*�Ż�ȯid*/
    private int couponId;
    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    /*�Ż�ȯ����*/
    private String couponName;
    public String getCouponName() {
        return couponName;
    }
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    /*���*/
    private float couponMoney;
    public float getCouponMoney() {
        return couponMoney;
    }
    public void setCouponMoney(float couponMoney) {
        this.couponMoney = couponMoney;
    }

    /*�����̼�*/
    private String sellerObj;
    public String getSellerObj() {
        return sellerObj;
    }
    public void setSellerObj(String sellerObj) {
        this.sellerObj = sellerObj;
    }

    /*�����û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����ʱ��*/
    private String couponTime;
    public String getCouponTime() {
        return couponTime;
    }
    public void setCouponTime(String couponTime) {
        this.couponTime = couponTime;
    }

}