package com.wsg.go.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowManager;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.wsg.go.R;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.SwitchUtil;
import com.wsg.go.view.activity.base.BaseMVCActivity;
import com.wsg.go.view.ui.videoplayer.SwitchVideo;

import butterknife.BindView;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-05-17 15:51
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class VideoDetailActivity extends BaseMVCActivity {

    @BindView(R.id.video_palyer)
    SwitchVideo videoPalyer;

    public static final String URL = "url";
    private boolean isPlay = true;
    private boolean isPause;
    private OrientationUtils orientationUtils;

    private static final String OPTION_VIEW = "view";

    public static void startTActivity(Activity activity, View transitionView) {
        Intent intent = new Intent(activity, VideoDetailActivity.class);
        // 这里指定了共享的视图元素
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, OPTION_VIEW);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public String getChildTitle() {
        return "详情";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void initView() {
        setHeadGone();

        //增加title
        videoPalyer.getTitleTextView().setVisibility(View.GONE);
        videoPalyer.getBackButton().setVisibility(View.GONE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, videoPalyer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        SwitchUtil.optionPlayer(videoPalyer, getIntent().getStringExtra(URL), true, "这是title");

        SwitchUtil.clonePlayState(videoPalyer);

        videoPalyer.setIsTouchWiget(true);

        videoPalyer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        videoPalyer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPalyer.startWindowFullscreen(mContext, false, false);
                OrientationUtils orientationUtils = new OrientationUtils((Activity)mContext, videoPalyer);
                orientationUtils.setRotateWithSystem(false);
                orientationUtils.setEnable(true);
            }
        });

        videoPalyer.setSurfaceToPlay();

        // 这里指定了被共享的视图元素
        ViewCompat.setTransitionName(videoPalyer, OPTION_VIEW);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initMainNetData() {

    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        videoPalyer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        videoPalyer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        if(null != videoPalyer.getGSYVideoManager()){
            videoPalyer.getGSYVideoManager().setListener(videoPalyer.getGSYVideoManager().lastListener());
            videoPalyer.getGSYVideoManager().setLastListener(null);
        }
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();

        SwitchUtil.release();
        super.onDestroy();
    }



//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //如果旋转了就全屏
//        if (isPlay && !isPause) {
//            videoPalyer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
//        }
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
            LogUtils.e("显示状态栏");
            setBarStatusColor(R.color.colorPrimary);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
            LogUtils.e("隐藏状态栏");
            hideStatusBar();
        }
        super.onConfigurationChanged(newConfig);
    }
}
