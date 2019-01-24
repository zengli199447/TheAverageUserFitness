package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CardFormNetBean {

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
        private List<CardBean> card;

        public List<CardBean> getCard() {
            return card;
        }

        public void setCard(List<CardBean> card) {
            this.card = card;
        }

        public static class CardBean {

            private String cardsettingid;
            private String price;
            private String title;
            private String createdate;
            private String datatype;
            private String enddate;

            public String getCardsettingid() {
                return cardsettingid;
            }

            public void setCardsettingid(String cardsettingid) {
                this.cardsettingid = cardsettingid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getDatatype() {
                return datatype;
            }

            public void setDatatype(String datatype) {
                this.datatype = datatype;
            }

            public String getEnddate() {
                return enddate;
            }

            public void setEnddate(String enddate) {
                this.enddate = enddate;
            }
        }
    }
}
