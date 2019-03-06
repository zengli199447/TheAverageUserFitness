package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/10.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymTypeNetBean {

    private int status;
    private ResultBean result;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {
        private List<ShopclassBean> shopclass;

        public List<ShopclassBean> getShopclass() {
            return shopclass;
        }

        public void setShopclass(List<ShopclassBean> shopclass) {
            this.shopclass = shopclass;
        }

        public static class ShopclassBean {

            private String shopclassid;
            private String shopclassname;
            private String ordernum;
            boolean selectStatus;

            public ShopclassBean(String shopclassid, String shopclassname, String ordernum, boolean selectStatus) {
                this.shopclassid = shopclassid;
                this.shopclassname = shopclassname;
                this.ordernum = ordernum;
                this.selectStatus = selectStatus;
            }

            public boolean isSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(boolean selectStatus) {
                this.selectStatus = selectStatus;
            }

            public String getShopclassid() {
                return shopclassid;
            }

            public void setShopclassid(String shopclassid) {
                this.shopclassid = shopclassid;
            }

            public String getShopclassname() {
                return shopclassname;
            }

            public void setShopclassname(String shopclassname) {
                this.shopclassname = shopclassname;
            }

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }
        }
    }

}
