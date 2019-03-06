package com.example.administrator.sportsfitness.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoGymNetBean implements Serializable{

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

    public static class ResultBean implements Serializable{

        private DetailBean detail;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public static class DetailBean implements Serializable{

            private String shopid;
            private String shopname;
            private String photo;
            private String score;
            private String price;
            private String province;
            private String city;
            private String area;
            private String address;
            private String openingtime_start;
            private String openingtime_end;
            private String remark;
            private String longitude;
            private String latitude;
            private String phone;
            private String createdate;
            private String serverstate;
            private String state;
            private String fulladdress;
            private String peopletotal;
            private String shopclassids;
            private String time_start_txt;
            private String time_end_txt;
            private String distance;
            private String ifcollect;
            private List<ImgBean> img;
            private List<CoursesBean> courses;
            private List<CommentBean> comment;

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getOpeningtime_start() {
                return openingtime_start;
            }

            public void setOpeningtime_start(String openingtime_start) {
                this.openingtime_start = openingtime_start;
            }

            public String getOpeningtime_end() {
                return openingtime_end;
            }

            public void setOpeningtime_end(String openingtime_end) {
                this.openingtime_end = openingtime_end;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getServerstate() {
                return serverstate;
            }

            public void setServerstate(String serverstate) {
                this.serverstate = serverstate;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getFulladdress() {
                return fulladdress;
            }

            public void setFulladdress(String fulladdress) {
                this.fulladdress = fulladdress;
            }

            public String getPeopletotal() {
                return peopletotal;
            }

            public void setPeopletotal(String peopletotal) {
                this.peopletotal = peopletotal;
            }

            public String getShopclassids() {
                return shopclassids;
            }

            public void setShopclassids(String shopclassids) {
                this.shopclassids = shopclassids;
            }

            public String getTime_start_txt() {
                return time_start_txt;
            }

            public void setTime_start_txt(String time_start_txt) {
                this.time_start_txt = time_start_txt;
            }

            public String getTime_end_txt() {
                return time_end_txt;
            }

            public void setTime_end_txt(String time_end_txt) {
                this.time_end_txt = time_end_txt;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getIfcollect() {
                return ifcollect;
            }

            public void setIfcollect(String ifcollect) {
                this.ifcollect = ifcollect;
            }

            public List<ImgBean> getImg() {
                return img;
            }

            public void setImg(List<ImgBean> img) {
                this.img = img;
            }

            public List<CoursesBean> getCourses() {
                return courses;
            }

            public void setCourses(List<CoursesBean> courses) {
                this.courses = courses;
            }

            public List<CommentBean> getComment() {
                return comment;
            }

            public void setComment(List<CommentBean> comment) {
                this.comment = comment;
            }

            public static class ImgBean implements Serializable{

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

            public static class CoursesBean implements Serializable{

                private String coursesid;
                private String listimg;
                private String coursesname;
                private String date_start;
                private String date_end;
                private String time_start;
                private String time_end;
                private String price;
                private String shopname;
                private String total;
                private String yuyuetotal;
                private String if_jinzhang;
                private String distance;
                private String state;

                public String getCoursesid() {
                    return coursesid;
                }

                public void setCoursesid(String coursesid) {
                    this.coursesid = coursesid;
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

                public String getDate_start() {
                    return date_start;
                }

                public void setDate_start(String date_start) {
                    this.date_start = date_start;
                }

                public String getDate_end() {
                    return date_end;
                }

                public void setDate_end(String date_end) {
                    this.date_end = date_end;
                }

                public String getTime_start() {
                    return time_start;
                }

                public void setTime_start(String time_start) {
                    this.time_start = time_start;
                }

                public String getTime_end() {
                    return time_end;
                }

                public void setTime_end(String time_end) {
                    this.time_end = time_end;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getShopname() {
                    return shopname;
                }

                public void setShopname(String shopname) {
                    this.shopname = shopname;
                }

                public String getTotal() {
                    return total;
                }

                public void setTotal(String total) {
                    this.total = total;
                }

                public String getYuyuetotal() {
                    return yuyuetotal;
                }

                public void setYuyuetotal(String yuyuetotal) {
                    this.yuyuetotal = yuyuetotal;
                }

                public String getIf_jinzhang() {
                    return if_jinzhang;
                }

                public void setIf_jinzhang(String if_jinzhang) {
                    this.if_jinzhang = if_jinzhang;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }
            }

            public static class CommentBean implements Serializable{

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

                public static class ImgBean implements Serializable{

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
}
