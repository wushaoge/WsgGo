package com.wsg.go.okhttp;



/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-11 15:39
 * 描    述：界面回调
 * 修订历史：
 * ================================================
 */
public interface IBaseUIView {


    /** 获取当前布局状态,方便在callBack中统一处理 **/
    EBaseViewStatus getBaseViewStatus();

    /** 手动设置布局状态,一般情况不需要 **/
    void setBaseViewStatus(EBaseViewStatus baseViewStatus);

    /** 显示成功布局 */
    void showSuccessLayout();

    /** 显示加载中布局 */
    void showLoadingLayout();

    /** 显示失败布局 */
    void showErrorLayout(String errorMsg);



}
