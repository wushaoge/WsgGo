package com.wsg.go.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wsg.go.R;
import com.wsg.go.entity.BaseData;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.netbuild.NetUtils;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.view.adapter.MeiziAdapter;
import com.wsg.go.view.fragment.base.BaseFragment;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 10:39
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeiziFragment extends BaseFragment implements OnRefreshListener,OnLoadMoreListener{


    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    MeiziAdapter meiziAdapter;

    public MeiziFragment() {
    }

    public static MeiziFragment newInstance(String title) {
        MeiziFragment fragment = new MeiziFragment();
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
        return R.layout.fragment_meizi;
    }

    @Override
    public void initViews() {

        meiziAdapter = new MeiziAdapter(getActivity(), null);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvContent.setLayoutManager(recyclerViewLayoutManager);
        rvContent.setAdapter(meiziAdapter);

        srlContent.setOnRefreshListener(this);
        srlContent.setOnLoadMoreListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void initLazyData() {
        new Handler().postDelayed(this::getData,3000);
    }


    public void getData() {
        String url = RequestUrl.getInstance().getMeizi();
//        OkGo.<MeiziData>get(url).tag(this).execute(getCallbackCustomDataMainInterface(MeiziData.class, Constants.GANK_BASEURL));
        NetUtils.getMain(this,MeiziData.class,url,Constants.GANK_BASEURL);
    }

    @Override
    public void onSuccess(Object data, String cmd) {
        switch (cmd) {
            case Constants.GANK_BASEURL:
                MeiziData meiziData = (MeiziData) data;
                if(pageNo == 1){
                    meiziAdapter.setNewData(meiziData.getResults());
                }else{
                    meiziAdapter.addData(meiziData.getResults());
                }
                break;
        }
    }

    @Override
    public void onResult(String cmd) {
        switch (cmd) {
            case Constants.GANK_BASEURL:
                stopLoad(srlContent);
                break;
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        ++pageNo;
        getData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNo = 1;
        getData();
    }


}
