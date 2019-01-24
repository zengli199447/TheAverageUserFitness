package com.example.administrator.sportsFitness.model.bean;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/15.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TagNetBean {

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

        private TagUserBean tag_user;
        private List<TagSystemBean> tag_system;

        public TagUserBean getTag_user() {
            return tag_user;
        }

        public void setTag_user(TagUserBean tag_user) {
            this.tag_user = tag_user;
        }

        public List<TagSystemBean> getTag_system() {
            return tag_system;
        }

        public void setTag_system(List<TagSystemBean> tag_system) {
            this.tag_system = tag_system;
        }

        public static class TagUserBean {

            private String tags;

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }
        }

        public static class TagSystemBean {

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
