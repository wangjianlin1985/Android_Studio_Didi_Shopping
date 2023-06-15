package com.mobileclient.domain;

import java.io.Serializable;

public class Product implements Serializable {
    /*商品编号*/
    private String productNo;
    public String getProductNo() {
        return productNo;
    }
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /*商品类目*/
    private int productClassObj;
    public int getProductClassObj() {
        return productClassObj;
    }
    public void setProductClassObj(int productClassObj) {
        this.productClassObj = productClassObj;
    }

    /*商品名称*/
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*物品描述*/
    private String productDesc;
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /*物品价格*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*物品图片*/
    private String productPhoto;
    public String getProductPhoto() {
        return productPhoto;
    }
    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    /*物品存货*/
    private String stockDesc;
    public String getStockDesc() {
        return stockDesc;
    }
    public void setStockDesc(String stockDesc) {
        this.stockDesc = stockDesc;
    }

}