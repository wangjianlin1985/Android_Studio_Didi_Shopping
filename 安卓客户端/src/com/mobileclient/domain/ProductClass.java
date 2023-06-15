package com.mobileclient.domain;

import java.io.Serializable;

public class ProductClass implements Serializable {
    /*类别编号*/
    private int classId;
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /*类别名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*添加日期*/
    private java.sql.Timestamp addTime;
    public java.sql.Timestamp getAddTime() {
        return addTime;
    }
    public void setAddTime(java.sql.Timestamp addTime) {
        this.addTime = addTime;
    }

    /*类别描述*/
    private String classDesc;
    public String getClassDesc() {
        return classDesc;
    }
    public void setClassDesc(String classDesc) {
        this.classDesc = classDesc;
    }

}