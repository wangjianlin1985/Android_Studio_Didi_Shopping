package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Coupon {
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
    private Seller sellerObj;
    public Seller getSellerObj() {
        return sellerObj;
    }
    public void setSellerObj(Seller sellerObj) {
        this.sellerObj = sellerObj;
    }

    /*�����û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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