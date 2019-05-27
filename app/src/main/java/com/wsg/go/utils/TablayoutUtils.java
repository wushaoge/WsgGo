package com.wsg.go.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsg.go.R;
import com.wsg.go.entity.HomeResourceData;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-09-05 10:21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TablayoutUtils {


    /**
     * 首页设置tablayout布局
     * @param mContext
     * @param tlTablayout
     * @param bottomList
     */
    public static void setHomeTablayoutCustomView(Context mContext, TabLayout tlTablayout, List<HomeResourceData> bottomList){

        for (int i = 0; i < tlTablayout.getTabCount(); i++) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.tab_home_item, tlTablayout, false);
            TextView txtView = (TextView) itemView.findViewById(R.id.tv_title);
            txtView.setText(bottomList.get(i).getTitle());
            ImageView ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
            ivImg.setImageResource(bottomList.get(i).getDrwableResource());
            //默认选中第一项
            if (i == 0) {
                txtView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                DrawableCompat.setTint(ivImg.getDrawable(), ContextCompat.getColor(mContext,R.color.colorPrimary));
                ivImg.setImageDrawable(ivImg.getDrawable());
            } else {
                txtView.setTextColor(mContext.getResources().getColor(R.color.font_gray_deep));
                DrawableCompat.setTint(ivImg.getDrawable(), ContextCompat.getColor(mContext,R.color.font_black));
                ivImg.setImageDrawable(ivImg.getDrawable());
            }
            tlTablayout.getTabAt(i).setCustomView(itemView);
        }

    }





}
