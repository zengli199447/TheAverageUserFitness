package com.example.administrator.sportsFitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TopUpNetBean {

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

        private String ordercode;
        private int orderheadid;

        public String getOrdercode() {
            return ordercode;
        }

        public void setOrdercode(String ordercode) {
            this.ordercode = ordercode;
        }

        public int getOrderheadid() {
            return orderheadid;
        }

        public void setOrderheadid(int orderheadid) {
            this.orderheadid = orderheadid;
        }
    }
}
