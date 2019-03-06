package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/10.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CourseTypeNetBean {

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
        private List<CoursesclassBean> coursesclass;

        public List<CoursesclassBean> getCoursesclass() {
            return coursesclass;
        }

        public void setCoursesclass(List<CoursesclassBean> coursesclass) {
            this.coursesclass = coursesclass;
        }

        public static class CoursesclassBean {

            private String coursesclassid;
            private String coursesclassname;
            private String ordernum;
            boolean selectStatus;

            public CoursesclassBean(String coursesclassid, String coursesclassname, String ordernum, boolean selectStatus) {
                this.coursesclassid = coursesclassid;
                this.coursesclassname = coursesclassname;
                this.ordernum = ordernum;
                this.selectStatus = selectStatus;
            }

            public boolean isSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(boolean selectStatus) {
                this.selectStatus = selectStatus;
            }

            public String getCoursesclassid() {
                return coursesclassid;
            }

            public void setCoursesclassid(String coursesclassid) {
                this.coursesclassid = coursesclassid;
            }

            public String getCoursesclassname() {
                return coursesclassname;
            }

            public void setCoursesclassname(String coursesclassname) {
                this.coursesclassname = coursesclassname;
            }

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }
        }
    }
}
