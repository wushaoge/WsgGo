package com.wsg.go.view.activity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.wsg.go.R;
import com.wsg.go.entity.KaiyanVideoData;
import com.wsg.go.netbuild.NetUtils;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.view.activity.base.BaseMVCActivity;
import com.wsg.go.view.adapter.VideoAdapter;


import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-05-06 14:28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ListVideoActivity extends BaseMVCActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    int categoryId = 0;
    String title = "视频";

    VideoAdapter videoAdapter;

    LinearLayoutManager linearLayoutManager;

    @Override
    public void setContentBefore() {
        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }

    }

    @Override
    public void getBundleExtras(Bundle extras) {
        categoryId = getIntent().getIntExtra("id", 12);
        title = getIntent().getStringExtra("title");
    }

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public String getChildTitle() {
        return "";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initView() {
        setTitle(title);

        srlContent.setOnRefreshListener(this);
        srlContent.setOnLoadMoreListener(this);

        videoAdapter = new VideoAdapter(mContext);
        linearLayoutManager = new LinearLayoutManager(this);
        rvContent.setLayoutManager(linearLayoutManager);
        rvContent.setAdapter(videoAdapter);

        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    int position = GSYVideoManager.instance().getPlayPosition();
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(videoAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {

                        //如果滑出去了上面和下面就是否，和今日头条一样
                        //是否全屏
                        if(!GSYVideoManager.isFullState(ListVideoActivity.this)) {
                            GSYVideoManager.releaseAllVideos();
                            videoAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initMainNetData() {
        getData();
    }

    public void getData() {
        NetUtils.getMain(this, KaiyanVideoData.class, RequestUrl.getInstance().getCategoryVideoList(categoryId, pageNo), Constants.CATEGORY_VIDEO_LIST);
    }


    @Override
    public void onSuccess(Object data, String cmd) {
        switch (cmd) {
            case Constants.CATEGORY_VIDEO_LIST:
                KaiyanVideoData kaiyanVideoData = (KaiyanVideoData) data;
                if (pageNo == 1) {
                    videoAdapter.setNewData(kaiyanVideoData.getItemList());
                } else {
                    videoAdapter.addData(kaiyanVideoData.getItemList());
                }
                break;
        }
    }


    @Override
    public void onResult(String cmd) {
        switch (cmd) {
            case Constants.CATEGORY_VIDEO_LIST:
                stopLoad(srlContent);
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        ++pageNo;
        getData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getData();
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
            LogUtils.e("显示状态栏");
            setBarStatusColor(R.color.colorPrimary);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
            LogUtils.e("隐藏状态栏");
            hideStatusBar();
        }
        super.onConfigurationChanged(newConfig);
    }
}
