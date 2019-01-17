package com.zhy.mvpgankio.category.bean;

import com.zhy.mvpgankio.common.base.BaseBean;

import java.util.List;

/**
 * 所有分类数据
 * Created by zhy on 2019/1/17.
 */

public class AllCategoryBean extends BaseBean {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5c1372779d21223f5a2baeac
         * createdAt : 2019-01-03T06:14:37.194Z
         * desc : 图像风格迁移demo，基于tensorflow lite，功能不太完备，但是基本思路很有趣，用ipc实现tensor模块，一定程度上提高了对内存的容错率，避免OOM。
         * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteamvvacj30f00qowqf","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteanw4d6j30f00qotkl","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteaon7g6j30f00qo46p","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteaqiqbnj30f00qo7rd"]
         * publishedAt : 2019-01-03T00:00:00.0Z
         * source : web
         * type : Android
         * url : https://github.com/MashirosBaumkuchen/Flora.git
         * used : true
         * who : 冬
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
