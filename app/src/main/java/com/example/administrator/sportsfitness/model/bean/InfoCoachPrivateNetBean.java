package com.example.administrator.sportsfitness.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoCoachPrivateNetBean implements Serializable {

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

            private String coursesid;
            private String listimg;
            private String shopid;
            private String userid;
            private String coursesname;
            private String price;
            private String total;
            private String date_start;
            private String date_end;
            private String time_start;
            private String time_end;
            private String meat;
            private String remark;
            private String mustread;
            private String createdate;
            private String state;
            private String city;
            private String area;
            private String address;
            private String longitude;
            private String latitude;
            private String province;
            private String datatype;
            private String coursesclassids;
            private String realname;
            private String secondname;
            private String photo;
            private String signature;
            private String userremark;
            private String fulladdress;
            private String total_yuyue;
            private String score;
            private String date_start_txt;
            private String time_start_txt;
            private String time_end_txt;
            private String ifcollect;
            private String ifjoin;
            private List<ImgBean> img;
            private List<StudentBean> student;

            public String getIfcollect() {
                return ifcollect;
            }

            public void setIfcollect(String ifcollect) {
                this.ifcollect = ifcollect;
            }

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

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getCoursesname() {
                return coursesname;
            }

            public void setCoursesname(String coursesname) {
                this.coursesname = coursesname;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
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

            public String getMeat() {
                return meat;
            }

            public void setMeat(String meat) {
                this.meat = meat;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getMustread() {
                return mustread;
            }

            public void setMustread(String mustread) {
                this.mustread = mustread;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
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

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getDatatype() {
                return datatype;
            }

            public void setDatatype(String datatype) {
                this.datatype = datatype;
            }

            public String getCoursesclassids() {
                return coursesclassids;
            }

            public void setCoursesclassids(String coursesclassids) {
                this.coursesclassids = coursesclassids;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
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

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getUserremark() {
                return userremark;
            }

            public void setUserremark(String userremark) {
                this.userremark = userremark;
            }

            public String getFulladdress() {
                return fulladdress;
            }

            public void setFulladdress(String fulladdress) {
                this.fulladdress = fulladdress;
            }

            public String getTotal_yuyue() {
                return total_yuyue;
            }

            public void setTotal_yuyue(String total_yuyue) {
                this.total_yuyue = total_yuyue;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getDate_start_txt() {
                return date_start_txt;
            }

            public void setDate_start_txt(String date_start_txt) {
                this.date_start_txt = date_start_txt;
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

            public String getIfjoin() {
                return ifjoin;
            }

            public void setIfjoin(String ifjoin) {
                this.ifjoin = ifjoin;
            }

            public List<ImgBean> getImg() {
                return img;
            }

            public void setImg(List<ImgBean> img) {
                this.img = img;
            }

            public List<StudentBean> getStudent() {
                return student;
            }

            public void setStudent(List<StudentBean> student) {
                this.student = student;
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

            public static class StudentBean implements Serializable{

                private String userid;
                private String secondname;
                private String photo;
                private String signature;

                public String getUserid() {
                    return userid;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
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

                public String getSignature() {
                    return signature;
                }

                public void setSignature(String signature) {
                    this.signature = signature;
                }
            }
        }
    }
}
