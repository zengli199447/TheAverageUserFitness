package com.example.administrator.sportsfitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/10.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymFormNetBean {

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

            private String shopid;
            private String photo;
            private String shopname;
            private String score;
            private String time_start;
            private String time_end;
            private String peopletotal;
            private String distance;
            private String price;
            private String selectTag;
            private String fulladdress;

            public String getFulladdress() {
                return fulladdress;
            }

            public void setFulladdress(String fulladdress) {
                this.fulladdress = fulladdress;
            }

            public String getSelectTag() {
                return selectTag;
            }

            public void setSelectTag(String selectTag) {
                this.selectTag = selectTag;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
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

            public String getPeopletotal() {
                return peopletotal;
            }

            public void setPeopletotal(String peopletotal) {
                this.peopletotal = peopletotal;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
