package com.wsg.go.view.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.jaeger.library.StatusBarUtil;
import com.wsg.go.R;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.view.activity.base.BaseMVCActivity;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-05-20 16:08
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DrawerActivity extends BaseMVCActivity {


    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl_drawer)
    DrawerLayout dlDrawer;

    @Override
    public String getChildTitle() {
        return "抽屉";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_drawer;
    }

    @Override
    public void initView() {
        setHeadGone();
        hideStatusBar();
        StatusBarUtil.setTranslucentForDrawerLayout(this,dlDrawer);


        navView.setNavigationItemSelectedListener(item -> {
            if (item.isChecked()) {
                closeDrawer();
                return true;
            }
            switch (item.getItemId()) {
                case R.id.nav_guide:
                    ToastUtil.showToast(mContext,"让我付");
                    break;
                case R.id.nav_operators:
                    ToastUtil.showToast(mContext,"让我付");
                    break;
                case R.id.nav_networking:
                    ToastUtil.showToast(mContext,"让我付");
                    break;
                default:
            }
            closeDrawer();
            return true;
        });

    }


    public boolean closeDrawer() {
        if (dlDrawer.isDrawerOpen(GravityCompat.END)) {
            dlDrawer.closeDrawer(GravityCompat.END);
            return true;
        }
        return false;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initMainNetData() {

    }

}
