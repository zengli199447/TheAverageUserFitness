package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/11.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderFormNetBean {

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
        private List<OrderBean> order;

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {

            private String orderheadid;
            private String listimg;
            private String coursesname;
            private String shopname;
            private String fulladdress;
            private String time_txt;
            private String producttotalmoney;
            private String state;
            private String state_pay;
            private int datatype;
            private String datatype_txt;
            private String ifcommented;
            private String total;
            private String total_yuyue;
            private String ordercode;

            public String getOrdercode() {
                return ordercode;
            }

            public void setOrdercode(String ordercode) {
                this.ordercode = ordercode;
            }

            public String getOrderheadid() {
                return orderheadid;
            }

            public void setOrderheadid(String orderheadid) {
                this.orderheadid = orderheadid;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public String getCoursesname() {
                return coursesname;
            }

            public void setCoursesname(String coursesname) {
                this.coursesname = coursesname;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getFulladdress() {
                return fulladdress;
            }

            public void setFulladdress(String fulladdress) {
                this.fulladdress = fulladdress;
            }

            public String getTime_txt() {
                return time_txt;
            }

            public void setTime_txt(String time_txt) {
                this.time_txt = time_txt;
            }

            public String getProducttotalmoney() {
                return producttotalmoney;
            }

            public void setProducttotalmoney(String producttotalmoney) {
                this.producttotalmoney = producttotalmoney;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getState_pay() {
                return state_pay;
            }

            public void setState_pay(String state_pay) {
                this.state_pay = state_pay;
            }

            public int getDatatype() {
                return datatype;
            }

            public void setDatatype(int datatype) {
                this.datatype = datatype;
            }

            public String getDatatype_txt() {
                return datatype_txt;
            }

            public void setDatatype_txt(String datatype_txt) {
                this.datatype_txt = datatype_txt;
            }

            public String getIfcommented() {
                return ifcommented;
            }

            public void setIfcommented(String ifcommented) {
                this.ifcommented = ifcommented;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getTotal_yuyue() {
                return total_yuyue;
            }

            public void setTotal_yuyue(String total_yuyue) {
                this.total_yuyue = total_yuyue;
            }
        }
    }
}
