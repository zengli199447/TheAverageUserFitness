package com.example.administrator.sportsFitness.model.bean;

/**
 * 作者：真理 Created by Administrator on 2019/1/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ReplySendNetBean {

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

        private String replyid;
        private String userid;
        private String newsid;
        private String content;
        private String userid_to;
        private String createdate;
        private String username_from;
        private String username_to;

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserid_to() {
            return userid_to;
        }

        public void setUserid_to(String userid_to) {
            this.userid_to = userid_to;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getUsername_from() {
            return username_from;
        }

        public void setUsername_from(String username_from) {
            this.username_from = username_from;
        }

        public String getUsername_to() {
            return username_to;
        }

        public void setUsername_to(String username_to) {
            this.username_to = username_to;
        }
    }
}
