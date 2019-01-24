package com.example.administrator.sportsFitness.model.bean;

/**
 * Created by Administrator on 2018/8/22.
 * BUS 信息载体
 */

public class BusSelectDicersifiedBean {

    int intValue;
    String stringValueId;
    String stringValueText;

    public BusSelectDicersifiedBean(String stringValueId, String stringValueText) {
        this.stringValueId = stringValueId;
        this.stringValueText = stringValueText;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValueId() {
        return stringValueId;
    }

    public void setStringValueId(String stringValueId) {
        this.stringValueId = stringValueId;
    }

    public String getStringValueText() {
        return stringValueText;
    }

    public void setStringValueText(String stringValueText) {
        this.stringValueText = stringValueText;
    }
}
