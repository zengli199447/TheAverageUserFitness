package com.example.administrator.sportsFitness.model.event;


import com.example.administrator.sportsFitness.model.bean.BusObject;
import com.example.administrator.sportsFitness.model.bean.BusSelectDicersifiedBean;

/**
 * Created by Administrator on 2018/1/5.
 */

public class CommonEvent {

    private int code;
    private String temp_str;
    private int temp_value;
    private boolean temp_boolean;

    private Object busObject;

    private BusSelectDicersifiedBean busSelectDicersifiedBean;

    public CommonEvent(int code) {
        this.code = code;
    }

    public CommonEvent(int code, BusSelectDicersifiedBean busSelectDicersifiedBean) {
        this.code = code;
        this.busSelectDicersifiedBean = busSelectDicersifiedBean;
    }

    public CommonEvent(int code, String temp_str) {
        this.code = code;
        this.temp_str = temp_str;
    }

    public CommonEvent(int code, int temp_value) {
        this.code = code;
        this.temp_value = temp_value;
    }

    public CommonEvent(int code, boolean temp_boolean) {
        this.code = code;
        this.temp_boolean = temp_boolean;
    }

    public CommonEvent(int code, String temp_str, int temp_value) {
        this.code = code;
        this.temp_str = temp_str;
        this.temp_value = temp_value;
    }

    public CommonEvent(int code, String temp_str, int temp_value, boolean temp_boolean) {
        this.code = code;
        this.temp_str = temp_str;
        this.temp_value = temp_value;
        this.temp_boolean = temp_boolean;
    }

    public CommonEvent(int code, Object busObject) {
        this.code = code;
        this.busObject = busObject;
    }

    public BusSelectDicersifiedBean getBusSelectDicersifiedBean() {
        return busSelectDicersifiedBean;
    }

    public void setBusSelectDicersifiedBean(BusSelectDicersifiedBean busSelectDicersifiedBean) {
        this.busSelectDicersifiedBean = busSelectDicersifiedBean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTemp_str() {
        return temp_str;
    }

    public void setTemp_str(String temp_str) {
        this.temp_str = temp_str;
    }

    public int getTemp_value() {
        return temp_value;
    }

    public void setTemp_value(int temp_value) {
        this.temp_value = temp_value;
    }

    public boolean isTemp_boolean() {
        return temp_boolean;
    }

    public void setTemp_boolean(boolean temp_boolean) {
        this.temp_boolean = temp_boolean;
    }

    public Object getBusObject() {
        return busObject;
    }

    public void setBusObject(Object busObject) {
        this.busObject = busObject;
    }

}
