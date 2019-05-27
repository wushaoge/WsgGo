package com.wsg.go.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wsg.go.R;
import com.wsg.go.entity.BaseData;
import com.wsg.go.entity.KaiyanVideoData;
import com.wsg.go.netbuild.NetUtils;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.view.adapter.VideoAdapter;
import com.wsg.go.view.fragment.base.BaseFragment;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-09 09:02
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ListVideoFragment extends BaseFragment implements OnRefreshListener {


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    VideoAdapter videoAdapter;

    public static ListVideoFragment newInstance(String title) {
        ListVideoFragment fragment = new ListVideoFragment();
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
        return R.layout.common_list;
    }

    @Override
    public void initViews() {
        srlContent.setOnRefreshListener(this);
        srlContent.setEnableLoadMore(false);

        videoAdapter = new VideoAdapter(mContext);
        rvContent.setAdapter(videoAdapter);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initLazyData() {
       getData();
    }

    public void getData() {
        NetUtils.getMain(this, KaiyanVideoData.class, RequestUrl.getInstance().getKaiyanList(), Constants.KAIYAN_LIST);
    }



    @Override
    public void onSuccess(Object data, String cmd) {
        switch (cmd) {
            case Constants.KAIYAN_LIST:
                KaiyanVideoData kaiyanVideoData = (KaiyanVideoData) data;
                videoAdapter.setNewData(kaiyanVideoData.getItemList());
                break;
        }
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData();
    }

    @Override
    public void onResult(String cmd) {
        switch (cmd) {
            case Constants.KAIYAN_LIST:
                stopLoad(srlContent);
                break;
        }
    }
}
