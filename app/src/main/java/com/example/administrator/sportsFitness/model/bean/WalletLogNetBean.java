package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/16.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WalletLogNetBean {

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

        private String moneytotal;
        private List<MoneyinConfigBean> moneyin_config;
        private List<MoneydetaillistBean> moneydetaillist;

        public String getMoneytotal() {
            return moneytotal;
        }

        public void setMoneytotal(String moneytotal) {
            this.moneytotal = moneytotal;
        }

        public List<MoneyinConfigBean> getMoneyin_config() {
            return moneyin_config;
        }

        public void setMoneyin_config(List<MoneyinConfigBean> moneyin_config) {
            this.moneyin_config = moneyin_config;
        }

        public List<MoneydetaillistBean> getMoneydetaillist() {
            return moneydetaillist;
        }

        public void setMoneydetaillist(List<MoneydetaillistBean> moneydetaillist) {
            this.moneydetaillist = moneydetaillist;
        }

        public static class MoneydetaillistBean {

            private String moneydetailid;
            private String remark;
            private String createdate;
            private String optmoney;

            public String getMoneydetailid() {
                return moneydetailid;
            }

            public void setMoneydetailid(String moneydetailid) {
                this.moneydetailid = moneydetailid;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getOptmoney() {
                return optmoney;
            }

            public void setOptmoney(String optmoney) {
                this.optmoney = optmoney;
            }
        }

        public static class MoneyinConfigBean {

            private String createdate;
            private String money_gift;
            private String money_in;
            private String moneyinconfigid;
            boolean selectStatus;

            public MoneyinConfigBean(String createdate, String money_gift, String money_in, String moneyinconfigid, boolean selectStatus) {
                this.createdate = createdate;
                this.money_gift = money_gift;
                this.money_in = money_in;
                this.moneyinconfigid = moneyinconfigid;
                this.selectStatus = selectStatus;
            }

            public boolean isSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(boolean selectStatus) {
                this.selectStatus = selectStatus;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getMoney_gift() {
                return money_gift;
            }

            public void setMoney_gift(String money_gift) {
                this.money_gift = money_gift;
            }

            public String getMoney_in() {
                return money_in;
            }

            public void setMoney_in(String money_in) {
                this.money_in = money_in;
            }

            public String getMoneyinconfigid() {
                return moneyinconfigid;
            }

            public void setMoneyinconfigid(String moneyinconfigid) {
                this.moneyinconfigid = moneyinconfigid;
            }
        }

    }

}
