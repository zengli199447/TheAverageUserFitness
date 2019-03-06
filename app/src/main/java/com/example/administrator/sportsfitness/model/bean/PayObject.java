package com.example.administrator.sportsfitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PayObject {

    int payType;
    int payStatus;
    String orderCode;

    public PayObject(int payType, int payStatus, String orderCode) {
        this.payType = payType;
        this.payStatus = payStatus;
        this.orderCode = orderCode;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
