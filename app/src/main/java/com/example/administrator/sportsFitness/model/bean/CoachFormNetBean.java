package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CoachFormNetBean {

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
        private List<ShopBean> shop;

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class ShopBean {

            private String collectid;
            private String userid;
            private String secondname;
            private String photo;
            private String signature;

            public String getCollectid() {
                return collectid;
            }

            public void setCollectid(String collectid) {
                this.collectid = collectid;
            }

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
