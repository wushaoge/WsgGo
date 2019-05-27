package com.wsg.go.entity;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 16:46
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeiziData extends BaseData{


    /**
     * error : false
     * results : [{"_id":"5796b970421aa90d2fc94b4e","createdAt":"2016-07-26T09:14:24.76Z","desc":"今天两个妹子","publishedAt":"2016-07-26T10:30:11.357Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg","used":true,"who":"代码家"},{"_id":"58bf522a421aa90efc4fb689","createdAt":"2017-03-08T08:36:58.695Z","desc":"3-8","publishedAt":"2017-03-08T11:27:16.65Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-08-17126216_1253875034703554_7520300169779216384_n.jpg","used":true,"who":"daimajia"},{"_id":"599386fe421aa9672cdf0812","createdAt":"2017-08-16T07:42:54.135Z","desc":"8-16","publishedAt":"2017-08-17T11:36:42.967Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fil82i7zsmj20u011hwja.jpg","used":true,"who":"daimajia"},{"_id":"5b3ed2d5421aa91cfe803e35","createdAt":"2018-07-06T10:24:21.907Z","desc":"2018-07-06","publishedAt":"2018-07-06T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg","used":true,"who":"lijinshanmx"},{"_id":"56f8a5b0677659164d56442f","createdAt":"2016-03-28T11:32:00.491Z","desc":"3.28","publishedAt":"2016-03-28T11:43:51.83Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/610dc034gw1f2cf4ulmpzj20dw0kugn0.jpg","used":true,"who":"daimajia"},{"_id":"56eb5db867765933d9b0a8fc","createdAt":"2016-03-18T09:45:28.259Z","desc":"3.18","publishedAt":"2016-03-18T12:18:39.928Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1f20ruz456sj20go0p0wi3.jpg","used":true,"who":"张涵宇"},{"_id":"5b398cf8421aa95570db5491","createdAt":"2018-07-02T10:24:56.546Z","desc":"2018-07-02","publishedAt":"2018-07-02T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsvb1xduvaj30u013175p.jpg","used":true,"who":"lijinshanmx"},{"_id":"56e619a46776591744cf05c0","createdAt":"2016-03-14T09:53:40.126Z","desc":"3.14","publishedAt":"2016-03-14T11:55:19.66Z","source":"chrome","type":"福利","url":"http://ww1.sinaimg.cn/large/7a8aed7bjw1f1w5m7c9knj20go0p0ae4.jpg","used":true,"who":"张涵宇"},{"_id":"5b0b5839421aa97f00f67c5c","createdAt":"2018-05-28T09:15:37.475Z","desc":"2018-05-28","publishedAt":"2018-05-28T18:51:58.793Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frqscr5o00j30k80qzafc.jpg","used":true,"who":"lijinshanmx"},{"_id":"56cc6d29421aa95caa708205","createdAt":"2016-01-25T09:14:17.609Z","desc":"1.26","publishedAt":"2016-01-26T04:02:34.316Z","type":"福利","url":"http://ww2.sinaimg.cn/large/7a8aed7bjw1f0buzmnacoj20f00liwi2.jpg","used":true,"who":"张涵宇"}]
     */

    private boolean error;
    private List<ResultsEntity> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public static class ResultsEntity {
        /**
         * _id : 5796b970421aa90d2fc94b4e
         * createdAt : 2016-07-26T09:14:24.76Z
         * desc : 今天两个妹子
         * publishedAt : 2016-07-26T10:30:11.357Z
         * source : chrome
         * type : 福利
         * url : http://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg
         * used : true
         * who : 代码家
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
    }
}
