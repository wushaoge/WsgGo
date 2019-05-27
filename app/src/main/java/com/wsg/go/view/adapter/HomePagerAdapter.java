package com.wsg.go.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wsg.go.view.fragment.base.BaseFragment;

import java.util.List;

/**
 * ================================================
 * 作    者：zhengzk
 * 版    本：1.0
 * 创建日期：2018-07-27 16:07
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> mFragmentList;
    private Context mContext;

    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        mFragmentList = list;
    }

    public HomePagerAdapter(Context context, FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        mFragmentList = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }


}
