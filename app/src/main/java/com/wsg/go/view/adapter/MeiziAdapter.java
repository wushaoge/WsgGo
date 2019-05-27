package com.wsg.go.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.wsg.go.R;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.utils.RequestOptionsUtls;
import com.wsg.go.view.ui.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 17:03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeiziAdapter extends BaseQuickAdapter<MeiziData.ResultsEntity, BaseViewHolder> {

    private Context mContext;

    public MeiziAdapter(Context mContext, @Nullable List<MeiziData.ResultsEntity> data) {
        super(R.layout.item_meizi, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziData.ResultsEntity item) {
        Glide.with(mContext)
                .load(item.getUrl())
                .apply(RequestOptionsUtls.getRequestOptions())
                .into((ImageView) helper.getView(R.id.iv_img));



        helper.getView(R.id.iv_img).setOnClickListener(view -> {
            ArrayList<String> list =  new ArrayList<>();
            for (MeiziData.ResultsEntity entity : this.getData()) {
                list.add(entity.getUrl());
            }

            ImageView imageView = (ImageView) helper.getView(R.id.iv_img);

            MNImageBrowser.with(mContext)
                    //必须-当前位置
                    .setCurrentPosition(helper.getLayoutPosition())
                    //必须-图片加载用户自己去选择
                    .setImageEngine(new GlideImageEngine())
                    //必须（setImageList和setImageUrl二选一，会覆盖）-图片集合
                    .setImageList(list)
                    //设置隐藏指示器
                    .setIndicatorHide(false)
                    //自定义ProgressView，不设置默认默认没有
                    .setCustomProgressViewLayoutID(R.layout.common_img_progress)
                    //全屏模式：默认全屏模式
                    .setFullScreenMode(true)
                    //打开
                    .show(imageView);
        });
    }
}
