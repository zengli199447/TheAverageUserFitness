package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/9.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyTripNetBean {

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
        private List<NeeddoBean> needdo;

        public List<NeeddoBean> getNeeddo() {
            return needdo;
        }

        public void setNeeddo(List<NeeddoBean> needdo) {
            this.needdo = needdo;
        }

        public static class NeeddoBean {
            /**
             * orderheadid : 16
             * listimg : ./upload/2018/11/08/59d8e7af2cd11b0386a533ca76f6f537.jpg
             * shopname : 派狐健身
             * coursesname : 瑜伽课程
             * datatype_txt : 课程
             * time_txt : 01月11日-01月18日 18:30-20:30
             */

            private String orderheadid;
            private String listimg;
            private String shopname;
            private String coursesname;
            private String datatype_txt;
            private String time_txt;

            public String getOrderheadid() {
                return orderheadid;
            }

            public void setOrderheadid(String orderheadid) {
                this.orderheadid = orderheadid;
            }

            public String getListimg() {
                return listimg;
            }

            public void setListimg(String listimg) {
                this.listimg = listimg;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getCoursesname() {
                return coursesname;
            }

            public void setCoursesname(String coursesname) {
                this.coursesname = coursesname;
            }

            public String getDatatype_txt() {
                return datatype_txt;
            }

            public void setDatatype_txt(String datatype_txt) {
                this.datatype_txt = datatype_txt;
            }

            public String getTime_txt() {
                return time_txt;
            }

            public void setTime_txt(String time_txt) {
                this.time_txt = time_txt;
            }
        }
    }
}
