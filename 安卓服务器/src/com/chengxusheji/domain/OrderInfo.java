package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*�������*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*��Ʒ��Ϣ*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*���͵ص�*/
    private String arrivePlace;
    public String getArrivePlace() {
        return arrivePlace;
    }
    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
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

    /*����״̬*/
    private OrderState stateObj;
    public OrderState getStateObj() {
        return stateObj;
    }
    public void setStateObj(OrderState stateObj) {
        this.stateObj = stateObj;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*�µ��û�*/
    private UserInfo orderUser;
    public UserInfo getOrderUser() {
        return orderUser;
    }
    public void setOrderUser(UserInfo orderUser) {
        this.orderUser = orderUser;
    }

    /*�µ�ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*�ӵ��̼�*/
    private String receiveSeller;
    public String getReceiveSeller() {
        return receiveSeller;
    }
    public void setReceiveSeller(String receiveSeller) {
        this.receiveSeller = receiveSeller;
    }

    /*�ӵ�ʱ��*/
    private String receiveTime;
    public String getReceiveTime() {
        return receiveTime;
    }
    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}