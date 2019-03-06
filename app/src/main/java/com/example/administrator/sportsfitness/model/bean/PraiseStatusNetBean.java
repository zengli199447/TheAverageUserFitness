package com.example.administrator.sportsfitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PraiseStatusNetBean {

    private String message;
    private Object result;
    private int status;
    private int ifzan_cleck;
    private int ifcollect_cleck;

    public int getIfcollect_cleck() {
        return ifcollect_cleck;
    }

    public void setIfcollect_cleck(int ifcollect_cleck) {
        this.ifcollect_cleck = ifcollect_cleck;
    }

    public int getIfzan_cleck() {
        return ifzan_cleck;
    }

    public void setIfzan_cleck(int ifzan_cleck) {
        this.ifzan_cleck = ifzan_cleck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
