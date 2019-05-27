package com.wsg.go.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wsg.go.R;
import com.wsg.go.entity.ArticleData;
import com.wsg.go.entity.BannerData;
import com.wsg.go.entity.BaseData;
import com.wsg.go.netbuild.NetUtils;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.RequestOptionsUtls;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.utils.Tools;
import com.wsg.go.view.activity.common.PublicWebActivity;
import com.wsg.go.view.adapter.ArticleAdapter;
import com.wsg.go.view.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 10:39
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.bga_banner)
    BGABanner bgaBanner;

    ArticleAdapter articleAdapter;


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String title) {
        HomeFragment fragment = new HomeFragment();
        final Bundle args = new Bundle();
        fragment.setTitle(title);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initViews() {
        articleAdapter = new ArticleAdapter(getActivity());
        rvContent.setAdapter(articleAdapter);

        articleAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("url",articleAdapter.getData().get(position).getLink());
            readyGo(PublicWebActivity.class,bundle);
        });

        srlContent.setOnRefreshListener(this);
        srlContent.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initLazyData() {
        new Handler().postDelayed(this::getData, 3000);
    }


    public void getData() {
        String url = RequestUrl.getInstance().getBanner();
        NetUtils.getMain(this, BannerData.class, url, Constants.WAN_BANNER);

        pageNo = 0;
        getArticle();
    }


    public void getArticle(){
        String url = RequestUrl.getInstance().getArticle(pageNo);
        NetUtils.get(this, ArticleData.class, url, Constants.WAN_ARTICLE, false);
    }

    @Override
    public void onSuccess(Object data, String cmd) {
        switch (cmd) {
            case Constants.WAN_BANNER:
                setBannerData((BannerData)data);
                break;
            case Constants.WAN_ARTICLE:
                setArticleData((ArticleData) data);
                break;
        }
    }

    private void setBannerData(BannerData bannerData){
        if(Tools.isEmptyList(bannerData.getData())) return;
        List<String> imgList = new ArrayList<>();
        List<String> strList = new ArrayList<>();

        for(BannerData.DataEntity dataEntity : bannerData.getData()) {
            imgList.add(dataEntity.getImagePath());
            strList.add(dataEntity.getTitle());
        }

        bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                Glide.with(banner.getContext())
                        .load(model)
                        .apply(RequestOptionsUtls.getRequestOptionsBanner())
                        .into(itemView);
                itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("url",bannerData.getData().get(position).getUrl());
                    readyGo(PublicWebActivity.class,bundle);
                });
            }
        });
        bgaBanner.setData(imgList, strList);
    }

    private void setArticleData(ArticleData articleData){
        if(pageNo == 0){
            if(!Tools.isEmptyList(articleData.getData().getDatas())){
                articleAdapter.setNewData(articleData.getData().getDatas());
            }else{
                articleAdapter.setNewData(null);
                articleAdapter.setEmptyView(Tools.getEmptyView(getActivity()));
            }
        }else{
            if(!Tools.isEmptyList(articleData.getData().getDatas())){
                articleAdapter.addData(articleData.getData().getDatas());
            }else{
                ToastUtil.showToast(getActivity(),"暂无更多数据");
            }
        }
    }

    @Override
    public void onResult(String cmd) {
        switch (cmd) {
            case Constants.WAN_ARTICLE:
                stopLoad(srlContent);
                break;
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        ++pageNo;
        getArticle();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNo = 0;
        getArticle();
    }


}
