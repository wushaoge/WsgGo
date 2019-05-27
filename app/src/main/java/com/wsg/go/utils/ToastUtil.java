package com.wsg.go.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-10 16:49
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ToastUtil {

    private static Toast mToast;

    private ToastUtil() {

    }

    private static Toast getToast(Context c) { //没有做同步，因为限制只能在主线程调用。
        if (mToast == null) {
            if (c == null) {
                throw new RuntimeException("构造 Toast的context 不能为空");
            }
            mToast = Toast.makeText(c.getApplicationContext(), "构造toast", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        return mToast;
    }


    public static void showToast(Context context, String text) {
        if (TextUtils.isEmpty(text))
            return;
        getToast(context).setDuration(Toast.LENGTH_SHORT);
        getToast(context).setText(text);
        getToast(context).show();
    }


    public static void showToastLong(Context context, String text) {
        if (TextUtils.isEmpty(text))
            return;
        getToast(context).setDuration(Toast.LENGTH_LONG);
        getToast(context).setText(text);
        getToast(context).show();
    }

    public static void showToast(Context context, int textId) {
        showToast(context, context.getResources().getString(textId));
    }

    public static void showToastLong(Context context, int textId) {
        showToastLong(context, context.getResources().getString(textId));
    }

}
