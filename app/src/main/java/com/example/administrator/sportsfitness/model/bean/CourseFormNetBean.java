package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/18.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CourseFormNetBean {

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
        private List<CoursesBean> courses;

        public List<CoursesBean> getCourses() {
            return courses;
        }

        public void setCourses(List<CoursesBean> courses) {
            this.courses = courses;
        }

        public static class CoursesBean {

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

            public CoursesBean(String coursesid, String listimg, String coursesname, String date_start, String date_end, String time_start, String time_end, String price, String shopname, String total, String yuyuetotal, String if_jinzhang, String distance, String state) {
                this.coursesid = coursesid;
                this.listimg = listimg;
                this.coursesname = coursesname;
                this.date_start = date_start;
                this.date_end = date_end;
                this.time_start = time_start;
                this.time_end = time_end;
                this.price = price;
                this.shopname = shopname;
                this.total = total;
                this.yuyuetotal = yuyuetotal;
                this.if_jinzhang = if_jinzhang;
                this.distance = distance;
                this.state = state;
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
    }
}
