package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/16.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CommentsNetBean {

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
        private List<CommentBean> comment;

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class CommentBean {

            private String commentid;
            private String userid;
            private String score;
            private String remark;
            private String reftype;
            private String refid;
            private String createdate;
            private String datatype;
            private String remark_again;
            private String userid_teacher;
            private String orderheadid;
            private String secondname;
            private String photo;
            private List<ImgBean> img;

            public CommentBean(String commentid, String userid, String score, String remark, String reftype, String refid, String createdate, String datatype, String remark_again, String userid_teacher, String orderheadid, String secondname, String photo, List<ImgBean> img) {
                this.commentid = commentid;
                this.userid = userid;
                this.score = score;
                this.remark = remark;
                this.reftype = reftype;
                this.refid = refid;
                this.createdate = createdate;
                this.datatype = datatype;
                this.remark_again = remark_again;
                this.userid_teacher = userid_teacher;
                this.orderheadid = orderheadid;
                this.secondname = secondname;
                this.photo = photo;
                this.img = img;
            }

            public String getCommentid() {
                return commentid;
            }

            public void setCommentid(String commentid) {
                this.commentid = commentid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getReftype() {
                return reftype;
            }

            public void setReftype(String reftype) {
                this.reftype = reftype;
            }

            public String getRefid() {
                return refid;
            }

            public void setRefid(String refid) {
                this.refid = refid;
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

            public String getRemark_again() {
                return remark_again;
            }

            public void setRemark_again(String remark_again) {
                this.remark_again = remark_again;
            }

            public String getUserid_teacher() {
                return userid_teacher;
            }

            public void setUserid_teacher(String userid_teacher) {
                this.userid_teacher = userid_teacher;
            }

            public String getOrderheadid() {
                return orderheadid;
            }

            public void setOrderheadid(String orderheadid) {
                this.orderheadid = orderheadid;
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

            public List<ImgBean> getImg() {
                return img;
            }

            public void setImg(List<ImgBean> img) {
                this.img = img;
            }

            public static class ImgBean {

                public ImgBean(String imgid, String imgpath) {
                    this.imgid = imgid;
                    this.imgpath = imgpath;
                }

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
