package com.wsg.go.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.wsg.go.R;
import com.wsg.go.entity.CategoryData;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.RequestOptionsUtls;
import com.wsg.go.view.activity.ListVideoActivity;
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
public class CategoryAdapter extends BaseQuickAdapter<CategoryData, BaseViewHolder> {

    private Context mContext;

    public CategoryAdapter(Context mContext) {
        super(R.layout.item_category);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryData item) {
        Glide.with(mContext)
                .load(item.getHeaderImage())
                .apply(RequestOptionsUtls.getRequestOptions())
                .into((ImageView) helper.getView(R.id.iv_img));

        helper.setText(R.id.tv_category, "#" + item.getName() + "#");

        helper.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ListVideoActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("title", item.getName());
            mContext.startActivity(intent);
        });
    }
}
