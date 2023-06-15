package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*订单编号*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*商品信息*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*配送地点*/
    private String arrivePlace;
    public String getArrivePlace() {
        return arrivePlace;
    }
    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
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

    /*订单状态*/
    private OrderState stateObj;
    public OrderState getStateObj() {
        return stateObj;
    }
    public void setStateObj(OrderState stateObj) {
        this.stateObj = stateObj;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*下单用户*/
    private UserInfo orderUser;
    public UserInfo getOrderUser() {
        return orderUser;
    }
    public void setOrderUser(UserInfo orderUser) {
        this.orderUser = orderUser;
    }

    /*下单时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*接单商家*/
    private String receiveSeller;
    public String getReceiveSeller() {
        return receiveSeller;
    }
    public void setReceiveSeller(String receiveSeller) {
        this.receiveSeller = receiveSeller;
    }

    /*接单时间*/
    private String receiveTime;
    public String getReceiveTime() {
        return receiveTime;
    }
    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}