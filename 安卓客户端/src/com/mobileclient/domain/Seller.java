package com.mobileclient.domain;

import java.io.Serializable;

public class Seller implements Serializable {
    /*�̼��˺�*/
    private String sellUserName;
    public String getSellUserName() {
        return sellUserName;
    }
    public void setSellUserName(String sellUserName) {
        this.sellUserName = sellUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*�̼�����*/
    private String sellerName;
    public String getSellerName() {
        return sellerName;
    }
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*��פ����*/
    private java.sql.Timestamp addDate;
    public java.sql.Timestamp getAddDate() {
        return addDate;
    }
    public void setAddDate(java.sql.Timestamp addDate) {
        this.addDate = addDate;
    }

    /*�̼ҵ�ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*γ��*/
    private float latitude;
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /*����*/
    private float longitude;
    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}