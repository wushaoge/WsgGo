package com.wsg.go.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wsg.go.BuildConfig;
import com.wsg.go.R;
import com.wsg.go.entity.BaseData;
import com.wsg.go.entity.CategoryData;
import com.wsg.go.entity.ListCategory;
import com.wsg.go.entity.MeiziData;
import com.wsg.go.netbuild.NetUtils;
import com.wsg.go.utils.Constants;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.RequestUrl;
import com.wsg.go.view.adapter.CategoryAdapter;
import com.wsg.go.view.adapter.MeiziAdapter;
import com.wsg.go.view.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class CategoryFragment extends BaseFragment{


    @BindView(R.id.srl_content)
    SmartRefreshLayout srlContent;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    CategoryAdapter categoryAdapter;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(String title) {
        CategoryFragment fragment = new CategoryFragment();
        final Bundle args = new Bundle();
        fragment.setTitle(title);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isShowLoading() {
        return false;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_meizi;
    }

    @Override
    public void initViews() {

        categoryAdapter = new CategoryAdapter(mContext);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvContent.setLayoutManager(recyclerViewLayoutManager);
        rvContent.setAdapter(categoryAdapter);

        srlContent.setEnableRefresh(false);
        srlContent.setEnableLoadMore(false);

    }

    @Override
    public void initData() {
    }

    @Override
    public void initLazyData() {
        getData();
    }


    public void getData() {
        String url = RequestUrl.getInstance().getCategoryList();
        NetUtils.getMain(this, CategoryData[].class,url,Constants.CATEGORY_LIST);

//        OkGo.<List<CategoryData>>get(url).tag(mContext).execute(new Callback<List<CategoryData>>() {
//            @Override
//            public void onStart(Request<List<CategoryData>, ? extends Request> request) {
//
//            }
//
//            @Override
//            public void onSuccess(Response<List<CategoryData>> response) {
//                categoryAdapter.setNewData(response.body());
//            }
//
//            @Override
//            public void onCacheSuccess(Response<List<CategoryData>> response) {
//
//            }
//
//            @Override
//            public void onError(Response<List<CategoryData>> response) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void uploadProgress(Progress progress) {
//
//            }
//
//            @Override
//            public void downloadProgress(Progress progress) {
//
//            }
//
//            @Override
//            public List<CategoryData> convertResponse(okhttp3.Response response) throws Throwable {
//                String responseData = response.body().string();
//                if (TextUtils.isEmpty(responseData)) return null;
//
//                if(BuildConfig.DEBUG){
//                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                    JsonParser jp = new JsonParser();
//                    JsonElement je = jp.parse(responseData);
//                    String jsonFormat = gson.toJson(je);
//                    LogUtils.e(jsonFormat);
//                }
//
//                final List<CategoryData> list = new Gson().fromJson(responseData, new TypeToken<List<CategoryData>>() {}.getType());
//                return list;
//            }
//        });
    }

    @Override
    public void onSuccess(Object data, String cmd) {
        switch (cmd) {
            case Constants.CATEGORY_LIST:
                CategoryData[] arr = (CategoryData[])data;
                categoryAdapter.setNewData(Arrays.asList(arr));
                break;
        }
    }


}
