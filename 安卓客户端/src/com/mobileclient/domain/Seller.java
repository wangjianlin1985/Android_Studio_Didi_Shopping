package com.mobileclient.domain;

import java.io.Serializable;

public class Seller implements Serializable {
    /*商家账号*/
    private String sellUserName;
    public String getSellUserName() {
        return sellUserName;
    }
    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*商家名称*/
    private String sellerName;
    public String getSellerName() {
        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*入驻日期*/
    private java.sql.Timestamp addDate;
    public java.sql.Timestamp getAddDate() {
        return addDate;
    }
    public void setAddDate(java.sql.Timestamp addDate) {
        this.addDate = addDate;
    }

    /*商家地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*纬度*/
    private float latitude;
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /*经度*/
    private float longitude;
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}