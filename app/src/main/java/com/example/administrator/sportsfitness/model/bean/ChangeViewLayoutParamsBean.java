package com.example.administrator.sportsfitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/3/1.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ChangeViewLayoutParamsBean {

    String path;
    int width;
    int hight;
    boolean status;

    public ChangeViewLayoutParamsBean(String path, int width, int hight, boolean status) {
        this.path = path;
        this.width = width;
        this.hight = hight;
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
