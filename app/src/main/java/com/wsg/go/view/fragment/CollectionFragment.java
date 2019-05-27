package com.wsg.go.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.wsg.go.R;
import com.wsg.go.view.activity.DrawerActivity;
import com.wsg.go.view.activity.GreedDaoTestActivity;
import com.wsg.go.view.activity.MapActivity;
import com.wsg.go.view.activity.MeiziMVCActivity;
import com.wsg.go.view.activity.MeiziMVPActivity;
import com.wsg.go.view.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-09 09:02
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CollectionFragment extends BaseFragment {


    @BindView(R.id.cd_meizi_mvc)
    CardView cdMeiziMvc;
    @BindView(R.id.cd_meizi_mvp)
    CardView cdMeiziMvp;
    @BindView(R.id.cd_greeddao)
    CardView cdGreeddao;
    @BindView(R.id.cd_map)
    CardView cdMap;


    public CollectionFragment() {
    }

    public static CollectionFragment newInstance(String title) {
        CollectionFragment fragment = new CollectionFragment();
        final Bundle args = new Bundle();
        fragment.setTitle(title);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_collection;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initLazyData() {

    }


    @OnClick({R.id.cd_meizi_mvc, R.id.cd_meizi_mvp, R.id.cd_greeddao, R.id.cd_map, R.id.cd_drawer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cd_meizi_mvc:
                readyGo(MeiziMVCActivity.class);
                break;
            case R.id.cd_meizi_mvp:
                readyGo(MeiziMVPActivity.class);
                break;
            case R.id.cd_greeddao:
                readyGo(GreedDaoTestActivity.class);
                break;
            case R.id.cd_map:
                readyGo(MapActivity.class);
                break;
            case R.id.cd_drawer:
                readyGo(DrawerActivity.class);
                break;
        }
    }



    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
