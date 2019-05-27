package com.wsg.go.view.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wsg.go.R;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.presenter.IMeiziView;
import com.wsg.go.presenter.MeiziPresenter;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.view.activity.base.BaseMVPActivity;
import com.wsg.go.view.adapter.MeiziAdapter;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-09 09:18
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MeiziMVPActivity extends BaseMVPActivity implements OnRefreshListener, OnLoadMoreListener, IMeiziView {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    //Presenter
    MeiziPresenter meiziPresenter;

    MeiziAdapter meiziAdapter;

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public String getChildTitle() {
        return "妹子MVP";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_meizi;
    }

    @Override
    public void initPresenter() {
        meiziPresenter = new MeiziPresenter(mContext,this);
        addPersenterLifeCycle(meiziPresenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        meiziAdapter = new MeiziAdapter(mContext, null);
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
    public void initMainNetData() {
        LogUtils.e("回调回调回调回调回调");
        meiziPresenter.getMeiziData();
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        ++pageNo;
        initMainNetData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNo = 1;
        initMainNetData();
    }

    @Override
    public void getMeiziData(MeiziData meiziData) {
        if(pageNo == 1){
            meiziAdapter.setNewData(meiziData.getResults());
        }else{
            meiziAdapter.addData(meiziData.getResults());
        }
    }

    @Override
    public void stopRefresh() {
        stopLoad(srlContent);
    }
}
