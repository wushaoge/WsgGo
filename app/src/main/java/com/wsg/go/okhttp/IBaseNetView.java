package com.wsg.go.okhttp;


/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-28 19:39
 * 描    述：网络回调
 * 修订历史：
 * ================================================
 */
public interface IBaseNetView {

    /** 开始请求调用 */
    void onStart(String cmd);

    /** 数据返回成功页面刷新数据 */
    void onSuccess(Object data, String cmd);

    /** 数据返回失败页面刷新数据 */
    void onError(Object data, String cmd);

    /** 成功or失败后都会调用 */
    void onResult(String cmd);
}
