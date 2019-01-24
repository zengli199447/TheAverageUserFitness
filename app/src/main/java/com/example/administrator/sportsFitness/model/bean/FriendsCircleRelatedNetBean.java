package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/16.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleRelatedNetBean {

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
        private List<UserlistBean> userlist;

        public List<UserlistBean> getUserlist() {
            return userlist;
        }

        public void setUserlist(List<UserlistBean> userlist) {
            this.userlist = userlist;
        }

        public static class UserlistBean {

            private String friendid;
            private String userid;
            private String secondname;
            private String photo;
            private String signature;
            private String distance;
            private boolean selectStatus;

            public boolean isSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(boolean selectStatus) {
                this.selectStatus = selectStatus;
            }

            public String getFriendid() {
                return friendid;
            }

            public void setFriendid(String friendid) {
                this.friendid = friendid;
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

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
