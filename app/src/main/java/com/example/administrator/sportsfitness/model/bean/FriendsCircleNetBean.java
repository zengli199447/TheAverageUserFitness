package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/11.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleNetBean {

    private String message;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {

            private String content;
            private String createdate;
            private String createdate_show;
            private String ifzan_cleck;
            private String limits;
            private String newsid;
            private String newsid_source;
            private String photo;
            private String secondname;
            private String state;
            private int total_ping;
            private int total_zan;
            private int total_zhuan;
            private String userid;
            private List<ImgpathjsonBean> imgpathjson;
            private List<UserZhuanBean> user_zhuan;

            public NewsBean(String content, String createdate, String createdate_show, String ifzan_cleck, String limits, String newsid, String newsid_source, String photo, String secondname, String state, int total_ping, int total_zan, int total_zhuan, String userid, List<ImgpathjsonBean> imgpathjson, List<UserZhuanBean> user_zhuan) {
                this.content = content;
                this.createdate = createdate;
                this.createdate_show = createdate_show;
                this.ifzan_cleck = ifzan_cleck;
                this.limits = limits;
                this.newsid = newsid;
                this.newsid_source = newsid_source;
                this.photo = photo;
                this.secondname = secondname;
                this.state = state;
                this.total_ping = total_ping;
                this.total_zan = total_zan;
                this.total_zhuan = total_zhuan;
                this.userid = userid;
                this.imgpathjson = imgpathjson;
                this.user_zhuan = user_zhuan;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getCreatedate_show() {
                return createdate_show;
            }

            public void setCreatedate_show(String createdate_show) {
                this.createdate_show = createdate_show;
            }

            public String getIfzan_cleck() {
                return ifzan_cleck;
            }

            public void setIfzan_cleck(String ifzan_cleck) {
                this.ifzan_cleck = ifzan_cleck;
            }

            public String getLimits() {
                return limits;
            }

            public void setLimits(String limits) {
                this.limits = limits;
            }

            public String getNewsid() {
                return newsid;
            }

            public void setNewsid(String newsid) {
                this.newsid = newsid;
            }

            public String getNewsid_source() {
                return newsid_source;
            }

            public void setNewsid_source(String newsid_source) {
                this.newsid_source = newsid_source;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getTotal_ping() {
                return total_ping;
            }

            public void setTotal_ping(int total_ping) {
                this.total_ping = total_ping;
            }

            public int getTotal_zan() {
                return total_zan;
            }

            public void setTotal_zan(int total_zan) {
                this.total_zan = total_zan;
            }

            public int getTotal_zhuan() {
                return total_zhuan;
            }

            public void setTotal_zhuan(int total_zhuan) {
                this.total_zhuan = total_zhuan;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public List<ImgpathjsonBean> getImgpathjson() {
                return imgpathjson;
            }

            public void setImgpathjson(List<ImgpathjsonBean> imgpathjson) {
                this.imgpathjson = imgpathjson;
            }

            public List<UserZhuanBean> getUser_zhuan() {
                return user_zhuan;
            }

            public void setUser_zhuan(List<UserZhuanBean> user_zhuan) {
                this.user_zhuan = user_zhuan;
            }

            public static class ImgpathjsonBean {

                public ImgpathjsonBean(String imgpath) {
                    this.imgpath = imgpath;
                }

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
    }
}
