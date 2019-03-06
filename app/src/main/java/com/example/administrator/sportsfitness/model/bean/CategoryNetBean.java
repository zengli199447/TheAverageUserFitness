package com.example.administrator.sportsfitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CategoryNetBean {

    String type;
    String index;
    boolean selectStatus;

    public CategoryNetBean(String type, String index, boolean selectStatus) {
        this.type = type;
        this.index = index;
        this.selectStatus = selectStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }

}
