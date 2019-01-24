package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/11.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DynamicDetailsNetBean {

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

        private DetailBean detail;
        private List<ReplyBean> reply;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<ReplyBean> getReply() {
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class DetailBean {

            private String newsid;
            private String userid;
            private String content;
            private String state;
            private String createdate;
            private String limits;
            private String newsid_source;
            private String createdate_show;
            private String secondname;
            private String photo;
            private String ifzan_cleck;
            private int total_zhuan;
            private int total_zan;
            private int total_ping;
            private List<UserZhuanBean> user_zhuan;
            private List<ImgpathjsonBean> imgpathjson;

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getLimits() {
                return limits;
            }

            public void setLimits(String limits) {
                this.limits = limits;
            }

            public String getNewsid_source() {
                return newsid_source;
            }

            public void setNewsid_source(String newsid_source) {
                this.newsid_source = newsid_source;
            }

            public String getCreatedate_show() {
                return createdate_show;
            }

            public void setCreatedate_show(String createdate_show) {
                this.createdate_show = createdate_show;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getIfzan_cleck() {
                return ifzan_cleck;
            }

            public void setIfzan_cleck(String ifzan_cleck) {
                this.ifzan_cleck = ifzan_cleck;
            }

            public int getTotal_zhuan() {
                return total_zhuan;
            }

            public void setTotal_zhuan(int total_zhuan) {
                this.total_zhuan = total_zhuan;
            }

            public int getTotal_zan() {
                return total_zan;
            }

            public void setTotal_zan(int total_zan) {
                this.total_zan = total_zan;
            }

            public int getTotal_ping() {
                return total_ping;
            }

            public void setTotal_ping(int total_ping) {
                this.total_ping = total_ping;
            }

            public List<UserZhuanBean> getUser_zhuan() {
                return user_zhuan;
            }

            public void setUser_zhuan(List<UserZhuanBean> user_zhuan) {
                this.user_zhuan = user_zhuan;
            }

            public List<ImgpathjsonBean> getImgpathjson() {
                return imgpathjson;
            }

            public void setImgpathjson(List<ImgpathjsonBean> imgpathjson) {
                this.imgpathjson = imgpathjson;
            }

            public static class ImgpathjsonBean {
                /**
                 * imgpath : ./upload/appphotos/2019/01/11/46ef0ad470449636f5e424ba7e71a880C482nV.jpg
                 */

                private String imgpath;

                public String getImgpath() {
                    return imgpath;
                }

                public void setImgpath(String imgpath) {
                    this.imgpath = imgpath;
                }
            }

            public static class UserZhuanBean {

                public UserZhuanBean(String secondname, String userid) {
                    this.secondname = secondname;
                    this.userid = userid;
                }

                private String secondname;
                private String userid;

                public String getSecondname() {
                    return secondname;
                }

                public void setSecondname(String secondname) {
                    this.secondname = secondname;
                }

                public String getUserid() {
                    return userid;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
                }
            }

        }

        public static class ReplyBean {

            private String replyid;
            private String userid;
            private String newsid;
            private String content;
            private String userid_to;
            private String createdate;
            private String username_from;
            private String username_to;

            public ReplyBean(String replyid, String userid, String newsid, String content, String userid_to, String createdate, String username_from, String username_to) {
                this.replyid = replyid;
                this.userid = userid;
                this.newsid = newsid;
                this.content = content;
                this.userid_to = userid_to;
                this.createdate = createdate;
                this.username_from = username_from;
                this.username_to = username_to;
            }

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
}
