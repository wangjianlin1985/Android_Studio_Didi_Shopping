package com.mobileclient.domain;

import java.io.Serializable;

public class Product implements Serializable {
    /*��Ʒ���*/
    private String productNo;
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /*��Ʒ��Ŀ*/
    private int productClassObj;
    public int getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(int productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*��Ʒ����*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*��Ʒ����*/
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*��Ʒ�۸�*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*��ƷͼƬ*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    /*��Ʒ���*/
    private String stockDesc;
    public String getStockDesc() {
        return stockDesc;
    }
    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

}