package com.wsg.go.utils;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 16:42
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RequestUrl {

    public RequestUrl() {

    }


    /**
     * 获取妹子福利url
     * @return
     */
    public String getMeizi(){
        String url = Constants.GANK_BASEURL+"福利/20";
        return url;
    }

    /**
     * 获取玩安卓banner
     * @return
     */
    public String getBanner(){
        String url = Constants.WAN_BANNER;
        return url;
    }


    /**
     * 获取玩安卓首页文章url
     * @return
     */
    public String getArticle(int page){
        String url = Constants.WAN_ARTICLE + page + "/json";
        return url;
    }

    /**
     * 开眼视频分类List
     * @return
     */
    public String getCategoryList(){
        String url = Constants.CATEGORY_LIST;
        return url;
    }

    /**
     * 开眼某个分类底下视频 带分页
     * @return
     */
    public String getCategoryVideoList(int categoryId, int pageNo){
        String url = Constants.CATEGORY_VIDEO_LIST + "&id=" + categoryId + "&start=" + pageNo*10;
        return url;
    }

    /**
     * 开眼视频List
     * @return
     */
    public String getKaiyanList(){
        String url = Constants.KAIYAN_LIST;
        return url;
    }



    public static RequestUrl getInstance() {
        return RequestUrl.RequestUrlHolder.holder;
    }

    private static class RequestUrlHolder {
        private static RequestUrl holder = new RequestUrl();
    }

}
