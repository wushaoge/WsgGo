package com.wsg.go.utils;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-10 16:44
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class Constants {


    /**
     * 网路相关
     */
    public static class NetConstants{
        public static final String RESPONSE_DEFAULT_ERROR = "服务暂时不给力"; //服务暂时不给力啦
        public static final String RESPONSE_NOWIFI = "亲,您没网啦"; //断网啦
        public static final String RESPONSE_404 = "网络异常404啦"; //网络异常啦
        public static final String RESPONSE_503 = "连接超时503啦"; //连接超时啦
        public static final String RESPONSE_500 = "连接异常500啦"; //连接异常啦
    }

    /**
     * 干货集中营URL https://gank.io/api
     */
    public static final String GANK_BASEURL = "http://gank.io/api/random/data/";

    /**
     * 玩安卓banner
     */
    public static final String WAN_BANNER = "http://www.wanandroid.com/banner/json";

    /**
     * 玩安卓首页文章
     */
    public static final String WAN_ARTICLE = "http://www.wanandroid.com/article/list/";


    /**
     * 开眼视频所有分类数据
     */
    public static final String CATEGORY_LIST = "http://baobab.kaiyanapp.com/api/v4/categories/";

    /**
     * 获取某个分类底下数据
     * http://baobab.kaiyanapp.com/api/v3/categories/videoList?start=20&num=10&id=36&strategy=date
     */
    public static final String CATEGORY_VIDEO_LIST = "http://baobab.kaiyanapp.com/api/v3/categories/videoList?num=10&strategy=date";

    /**
     * 开眼list列表
     */
    public static final String KAIYAN_LIST = "http://baobab.kaiyanapp.com/api/v3/ranklist?num=10";


}
