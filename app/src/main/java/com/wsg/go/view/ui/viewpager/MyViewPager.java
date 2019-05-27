package com.wsg.go.view.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2017-11-06 15:08
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MyViewPager extends ViewPager
{
    private boolean scrollble = false;

    public MyViewPager(Context context)
    {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (scrollble)
        {
            return super.onTouchEvent(ev);
        }
        return scrollble;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (scrollble)
        {
            return super.onInterceptTouchEvent(ev);
        }
        return scrollble;
    }

    public boolean isScrollble()
    {
        return scrollble;
    }

    public void setScrollble(boolean scrollble)
    {
        this.scrollble = scrollble;
    }
}
