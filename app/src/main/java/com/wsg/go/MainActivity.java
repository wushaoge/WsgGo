package com.wsg.go;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.jaeger.library.StatusBarUtil;
import com.wenming.library.LogReport;
import com.wsg.go.application.MyApplication;
import com.wsg.go.entity.HomeResourceData;
import com.wsg.go.utils.TablayoutUtils;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.view.activity.base.BaseMVCActivity;
import com.wsg.go.view.adapter.HomePagerAdapter;
import com.wsg.go.view.fragment.CategoryFragment;
import com.wsg.go.view.fragment.CollectionFragment;
import com.wsg.go.view.fragment.HomeFragment;
import com.wsg.go.view.fragment.MeiziFragment;
import com.wsg.go.view.fragment.base.BaseFragment;
import com.wsg.go.view.ui.listenter.HomeTabSelectedListener;
import com.wsg.go.view.ui.listenter.TestLifeCycle;
import com.wsg.go.view.ui.viewpager.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseMVCActivity {


    private final List<BaseFragment> mFragmentList = new ArrayList<>();

    @BindView(R.id.vp_content)
    MyViewPager vpContent;
    @BindView(R.id.tl_tablayout)
    TabLayout tlTablayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl_drawer)
    DrawerLayout dlDrawer;

    @Override
    public String getChildTitle() {
        return "首页";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setHeadGone();
        hideStatusBar();
//        StatusBarUtil.setTranslucentForDrawerLayout(this,dlDrawer);
        StatusBarUtil.setColorForDrawerLayout(this,dlDrawer, ContextCompat.getColor(mContext,R.color.colorPrimary));

        View headView = navView.getHeaderView(0);
        LottieAnimationView lottieAnimationView = headView.findViewById(R.id.lottie_view);
        lottieAnimationView.setAnimation("dressclo.json");
        lottieAnimationView.playAnimation();

        navView.setItemIconTintList(null);

        navView.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) {
                closeDrawer();
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_guide:
                    ToastUtil.showToast(mContext,"(￣o￣) . z Z");
                    break;
                case R.id.nav_operators:
                    ToastUtil.showToast(mContext,"O(∩_∩)O哈哈~");
                    break;
                case R.id.nav_networking:
                    ToastUtil.showToast(mContext,"✧(≖ ◡ ≖✿)嘿嘿");
                    break;
                default:
            }
            closeDrawer();
            return true;
        });
    }

    @Override
    public void initData() {
        //发送错误日志
        LogReport.getInstance().upload(mContext);

        getLifecycle().addObserver(new TestLifeCycle());

        setBackInVisible();

        List<HomeResourceData> bottomList = new ArrayList<>();
        bottomList.add(new HomeResourceData("首页", R.drawable.bottom_home));
        bottomList.add(new HomeResourceData("妹子", R.drawable.bottom_girl));
        bottomList.add(new HomeResourceData("视频", R.drawable.bottom_video));
        bottomList.add(new HomeResourceData("实验室", R.drawable.bottom_laboratory));

        mFragmentList.add(HomeFragment.newInstance("首页"));
        mFragmentList.add(MeiziFragment.newInstance("妹子"));
        mFragmentList.add(CategoryFragment.newInstance("视频"));
        mFragmentList.add(CollectionFragment.newInstance("实验室"));

        vpContent.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), mFragmentList));
        vpContent.setOffscreenPageLimit(mFragmentList.size());
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFragmentList.get(position).hideSoftKeyboard();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tlTablayout.setupWithViewPager(vpContent);
        tlTablayout.setTabsFromPagerAdapter(vpContent.getAdapter());
        //重新设置布局
        TablayoutUtils.setHomeTablayoutCustomView(mContext, tlTablayout, bottomList);
        //设置tablayout的选中监听
        tlTablayout.addOnTabSelectedListener(new HomeTabSelectedListener(mContext));
    }

    @Override
    public void initMainNetData() {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 返回按键点击间隔
     */
    private long exitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis();
                ToastUtil.showToast(mContext, "再按一次退出" + getString(R.string.app_name));
            } else {
                MyApplication.appExit();
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean closeDrawer() {
        if (dlDrawer.isDrawerOpen(GravityCompat.END)) {
            dlDrawer.closeDrawer(GravityCompat.END);
            return true;
        }
        return false;
    }

}
