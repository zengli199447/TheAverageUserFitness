package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomePageNetBean {

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

        private NoticeBean notice;
        private NewversionBean newversion;
        private String RootPath;
        private List<BannerBean> banner;

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public NewversionBean getNewversion() {
            return newversion;
        }

        public void setNewversion(NewversionBean newversion) {
            this.newversion = newversion;
        }

        public String getRootPath() {
            return RootPath;
        }

        public void setRootPath(String RootPath) {
            this.RootPath = RootPath;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class NoticeBean {

            private String textinfoid;
            private String content;

            public String getTextinfoid() {
                return textinfoid;
            }

            public void setTextinfoid(String textinfoid) {
                this.textinfoid = textinfoid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class NewversionBean {

            private String txtinfoid;
            private String content;
            private String txtinfotype;
            private String versionvalue;
            private String versiontxt;
            private String downsite;

            public String getTxtinfoid() {
                return txtinfoid;
            }

            public void setTxtinfoid(String txtinfoid) {
                this.txtinfoid = txtinfoid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTxtinfotype() {
                return txtinfotype;
            }

            public void setTxtinfotype(String txtinfotype) {
                this.txtinfotype = txtinfotype;
            }

            public String getVersionvalue() {
                return versionvalue;
            }

            public void setVersionvalue(String versionvalue) {
                this.versionvalue = versionvalue;
            }

            public String getVersiontxt() {
                return versiontxt;
            }

            public void setVersiontxt(String versiontxt) {
                this.versiontxt = versiontxt;
            }

            public String getDownsite() {
                return downsite;
            }

            public void setDownsite(String downsite) {
                this.downsite = downsite;
            }
        }

        public static class BannerBean {

            private String textInfoid;
            private String title;
            private String jianjie;
            private String photo;
            private String content;
            private int infotype;
            private String createdate;
            private String numamount;
            private int intamount;
            private String intamount2;

            public String getTextInfoid() {
                return textInfoid;
            }

            public void setTextInfoid(String textInfoid) {
                this.textInfoid = textInfoid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getInfotype() {
                return infotype;
            }

            public void setInfotype(int infotype) {
                this.infotype = infotype;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getNumamount() {
                return numamount;
            }

            public void setNumamount(String numamount) {
                this.numamount = numamount;
            }

            public int getIntamount() {
                return intamount;
            }

            public void setIntamount(int intamount) {
                this.intamount = intamount;
            }

            public String getIntamount2() {
                return intamount2;
            }

            public void setIntamount2(String intamount2) {
                this.intamount2 = intamount2;
            }
        }
    }
}
