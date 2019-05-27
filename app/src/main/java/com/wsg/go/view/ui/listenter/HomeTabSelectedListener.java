package com.wsg.go.view.ui.listenter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsg.go.R;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-09-04 19:36
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class HomeTabSelectedListener implements TabLayout.OnTabSelectedListener {

    private Context mContext;

    public HomeTabSelectedListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tvTitle = (TextView) customView.findViewById(R.id.tv_title);
        ImageView ivImg = (ImageView) customView.findViewById(R.id.iv_img);
        tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

        //通过mutate（）复制加载出来的对象
//        Drawable drawable= ivImg.getDrawable().mutate();
//        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.colorPrimary)));
//        ivImg.setImageDrawable(wrappedDrawable );

        DrawableCompat.setTint(ivImg.getDrawable(), ContextCompat.getColor(mContext,R.color.colorPrimary));
        ivImg.setImageDrawable(ivImg.getDrawable());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        TextView tvTitle = (TextView) customView.findViewById(R.id.tv_title);
        ImageView ivImg = (ImageView) customView.findViewById(R.id.iv_img);
        tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_gray_deep));
        DrawableCompat.setTint(ivImg.getDrawable(), ContextCompat.getColor(mContext,R.color.font_black));
        ivImg.setImageDrawable(ivImg.getDrawable());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}