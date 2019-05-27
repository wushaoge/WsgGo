package com.wsg.go.view.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wsg.go.R;
import com.wsg.go.entity.ArticleData;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 17:03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleData.DataEntity.DatasEntity, BaseViewHolder> {

    private Context mContext;

    public ArticleAdapter(Context mContext) {
        super(R.layout.item_home_article);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleData.DataEntity.DatasEntity item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_create_time,item.getNiceDate() + "  " + item.getAuthor());
    }
}
