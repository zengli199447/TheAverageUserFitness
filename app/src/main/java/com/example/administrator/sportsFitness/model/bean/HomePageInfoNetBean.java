package com.example.administrator.sportsFitness.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/14.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomePageInfoNetBean implements Serializable {

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

        private String ifcollect;
        private String iffriend;
        private UserBean user;
        private List<CommentBean> comment;
        private List<NewsBean> news;

        public String getIfcollect() {
            return ifcollect;
        }

        public void setIfcollect(String ifcollect) {
            this.ifcollect = ifcollect;
        }

        public String getIffriend() {
            return iffriend;
        }

        public void setIffriend(String iffriend) {
            this.iffriend = iffriend;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class UserBean {

            private String age;
            private String brithday;
            private String cardno;
            private String city;
            private String createdate;
            private String face;
            private String height;
            private String idcard;
            private String img_center;
            private String newsshow;
            private String phone;
            private String photo;
            private String province;
            private String pwd;
            private String qq;
            private String realname;
            private String remark;
            private String role;
            private String score;
            private String secondname;
            private String sex;
            private String signature;
            private String state;
            private String state_check;
            private String tags;
            private String tel;
            private String tocken;
            private String total_jingyan;
            private String total_yuyue;
            private String userid;
            private String weight;
            private String wxopenid;
            private List<ZizhiBean> zizhi;

            public List<ZizhiBean> getZizhi() {
                return zizhi;
            }

            public void setZizhi(List<ZizhiBean> zizhi) {
                this.zizhi = zizhi;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getBrithday() {
                return brithday;
            }

            public void setBrithday(String brithday) {
                this.brithday = brithday;
            }

            public String getCardno() {
                return cardno;
            }

            public void setCardno(String cardno) {
                this.cardno = cardno;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getImg_center() {
                return img_center;
            }

            public void setImg_center(String img_center) {
                this.img_center = img_center;
            }

            public String getNewsshow() {
                return newsshow;
            }

            public void setNewsshow(String newsshow) {
                this.newsshow = newsshow;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getPwd() {
                return pwd;
            }

            public void setPwd(String pwd) {
                this.pwd = pwd;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getSecondname() {
                return secondname;
            }

            public void setSecondname(String secondname) {
                this.secondname = secondname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getState_check() {
                return state_check;
            }

            public void setState_check(String state_check) {
                this.state_check = state_check;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getTocken() {
                return tocken;
            }

            public void setTocken(String tocken) {
                this.tocken = tocken;
            }

            public String getTotal_jingyan() {
                return total_jingyan;
            }

            public void setTotal_jingyan(String total_jingyan) {
                this.total_jingyan = total_jingyan;
            }

            public String getTotal_yuyue() {
                return total_yuyue;
            }

            public void setTotal_yuyue(String total_yuyue) {
                this.total_yuyue = total_yuyue;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getWxopenid() {
                return wxopenid;
            }

            public void setWxopenid(String wxopenid) {
                this.wxopenid = wxopenid;
            }

            public static class ZizhiBean {

                private String imgpath;

                public String getImgpath() {
                    return imgpath;
                }

                public void setImgpath(String imgpath) {
                    this.imgpath = imgpath;
                }
            }

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

                private String imgpath;

                public String getImgpath() {
                    return imgpath;
                }

                public void setImgpath(String imgpath) {
                    this.imgpath = imgpath;
                }
            }

            public static class UserZhuanBean {

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

        public static class CommentBean {

            private String commentid;
            private String createdate;
            private String datatype;
            private String orderheadid;
            private String photo;
            private String refid;
            private String reftype;
            private String remark;
            private String remark_again;
            private String score;
            private String secondname;
            private String userid;
            private String userid_teacher;
            private List<CommentsNetBean.ResultBean.CommentBean.ImgBean> img;


            public String getCommentid() {
                return commentid;
            }

            public void setCommentid(String commentid) {
                this.commentid = commentid;
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

            public String getOrderheadid() {
                return orderheadid;
            }

            public void setOrderheadid(String orderheadid) {
                this.orderheadid = orderheadid;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getRefid() {
                return refid;
            }

            public void setRefid(String refid) {
                this.refid = refid;
            }

            public String getReftype() {
                return reftype;
            }

            public void setReftype(String reftype) {
                this.reftype = reftype;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getRemark_again() {
                return remark_again;
            }

            public void setRemark_again(String remark_again) {
                this.remark_again = remark_again;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

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

            public String getUserid_teacher() {
                return userid_teacher;
            }

            public void setUserid_teacher(String userid_teacher) {
                this.userid_teacher = userid_teacher;
            }

            public List<CommentsNetBean.ResultBean.CommentBean.ImgBean> getImg() {
                return img;
            }

            public void setImg(List<CommentsNetBean.ResultBean.CommentBean.ImgBean> img) {
                this.img = img;
            }

            public static class ImgBean {
                /**
                 * imgid : 62
                 * imgpath : 图片1
                 */

                private String imgid;
                private String imgpath;

                public String getImgid() {
                    return imgid;
                }

                public void setImgid(String imgid) {
                    this.imgid = imgid;
                }

                public String getImgpath() {
                    return imgpath;
                }

                public void setImgpath(String imgpath) {
                    this.imgpath = imgpath;
                }
            }
        }
    }

}
