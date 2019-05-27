package com.wsg.go.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wsg.go.R;


/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-13 19:17
 * 描    述：状态栏颜色
 * 修订历史：
 * ================================================
 */
public class StateBarTranslucentUtils {


    public static void setStateBarTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
           // activity_drawer.getWindow().setStatusBarColor(Color.TRANSPARENT);  //直接用这个方法会有兼容性问题
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }


    public static void setStateBarColor(Activity activity) {
        // 设置状态栏颜色
        ViewGroup contentLayout = (ViewGroup) activity.findViewById(android.R.id.content);
        setupStatusBarView(activity, contentLayout, ContextCompat.getColor(activity, R.color.colorPrimary));

        // 设置Activity layout的fitsSystemWindows
        View contentChild = contentLayout.getChildAt(0);
        contentChild.setFitsSystemWindows(true);
    }
    public static void setStateBarColor(Activity activity, int colorId) {
        // 设置状态栏颜色
        ViewGroup contentLayout = (ViewGroup) activity.findViewById(android.R.id.content);
        setupStatusBarView(activity, contentLayout, ContextCompat.getColor(activity,colorId));

        // 设置Activity layout的fitsSystemWindows
        View contentChild = contentLayout.getChildAt(0);
        contentChild.setFitsSystemWindows(true);
    }


    private static void setupStatusBarView(Activity activity, ViewGroup contentLayout, int color) {

        View mStatusBarView = null;

        View statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        contentLayout.addView(statusBarView, lp);

        mStatusBarView = statusBarView;


        mStatusBarView.setBackgroundColor(color);
    }

    /**
     * 获得状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        //LogUtils.e(context.getResources().getDimensionPixelSize(resourceId)+"");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
