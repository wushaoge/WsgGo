package com.wsg.go.presenter;

import com.wsg.go.entity.MeiziData;
import com.wsg.go.okhttp.IBaseNetView;
import com.wsg.go.okhttp.IBaseUIView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 09:31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IMeiziView extends IBaseNetView, IBaseUIView {

    void getMeiziData(MeiziData meiziData);

    void stopRefresh();

}
