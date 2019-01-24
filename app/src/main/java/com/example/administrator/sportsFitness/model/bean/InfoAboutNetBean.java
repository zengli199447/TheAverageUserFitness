package com.example.administrator.sportsFitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoAboutNetBean {

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

        private String friend;
        private String news;
        private String comment;
        private String collect;
        private String cardtype;
        private String moneytotal;

        public String getMoneytotal() {
            return moneytotal;
        }

        public void setMoneytotal(String moneytotal) {
            this.moneytotal = moneytotal;
        }

        public String getFriend() {
            return friend;
        }

        public void setFriend(String friend) {
            this.friend = friend;
        }

        public String getNews() {
            return news;
        }

        public void setNews(String news) {
            this.news = news;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }
    }
}
