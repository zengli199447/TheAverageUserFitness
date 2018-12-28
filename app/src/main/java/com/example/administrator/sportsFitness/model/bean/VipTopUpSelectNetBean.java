package com.example.administrator.sportsFitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class VipTopUpSelectNetBean {

    int price;
    int preferential;
    boolean selectStatus;

    public VipTopUpSelectNetBean(int price, int preferential, boolean selectStatus) {
        this.price = price;
        this.preferential = preferential;
        this.selectStatus = selectStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPreferential() {
        return preferential;
    }

    public void setPreferential(int preferential) {
        this.preferential = preferential;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }

}
