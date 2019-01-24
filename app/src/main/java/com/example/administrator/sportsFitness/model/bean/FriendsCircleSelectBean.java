package com.example.administrator.sportsFitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleSelectBean {

    String typeName;
    boolean status;

    public FriendsCircleSelectBean(String typeName, boolean status) {
        this.typeName = typeName;
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
