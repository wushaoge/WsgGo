package com.wsg.go.view.activity;

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
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.view.activity.base.BaseMVCActivity;
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
public class MeiziMVCActivity extends BaseMVCActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;

    MeiziAdapter meiziAdapter;

    @Override
    public boolean isShowLoading() {
        return true;
    }

    @Override
    public String getChildTitle() {
        return "妹子MVC";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_meizi;
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
        getData();
    }


    public void getData() {
        String url = RequestUrl.getInstance().getMeizi();
        LogUtils.e(url);
        NetUtils.getMainNew(this, MeiziData.class, url, Constants.GANK_BASEURL);
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
