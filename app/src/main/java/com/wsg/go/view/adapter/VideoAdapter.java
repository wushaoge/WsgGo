package com.wsg.go.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wsg.go.R;
import com.wsg.go.entity.KaiyanVideoData;
import com.wsg.go.utils.RequestOptionsUtls;
import com.wsg.go.utils.SwitchUtil;
import com.wsg.go.view.activity.VideoDetailActivity;
import com.wsg.go.view.ui.videoplayer.SwitchVideo;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 17:03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class VideoAdapter extends BaseQuickAdapter<KaiyanVideoData.ItemListEntity, BaseViewHolder> {

    private Context mContext;

    public static String TAG = "VideoAdapter";

    public VideoAdapter(Context mContext) {
        super(R.layout.item_video);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, KaiyanVideoData.ItemListEntity item) {
        SwitchVideo standardGSYVideoPlayer = helper.getView(R.id.video_palyer);
        standardGSYVideoPlayer.setUpLazy(item.getData().getPlayUrl(), true, null, null, item.getData().getTitle());

        //增加title
        standardGSYVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        standardGSYVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        standardGSYVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardGSYVideoPlayer.startWindowFullscreen(mContext, false, false);
                OrientationUtils orientationUtils = new OrientationUtils((Activity)mContext, standardGSYVideoPlayer);
                orientationUtils.setRotateWithSystem(false);
                orientationUtils.setEnable(true);
            }
        });

        //防止错位设置
        standardGSYVideoPlayer.setPlayTag(TAG);
        standardGSYVideoPlayer.setPlayPosition(helper.getLayoutPosition());
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        standardGSYVideoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        standardGSYVideoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        standardGSYVideoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        standardGSYVideoPlayer.setIsTouchWiget(false);

        //重力旋转
//        standardGSYVideoPlayer.setRotateWithSystem(true);
//        standardGSYVideoPlayer.setRotateViewAuto(true);


        //默认封面图片
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        standardGSYVideoPlayer.getThumbImageViewLayout().setVisibility(View.VISIBLE);
        //ImageLoader是Gilde封装的图片加载类，确认工具没问题的
        Glide.with(mContext)
                .load(item.getData().getCover().getFeed())
                .apply(RequestOptionsUtls.getRequestOptions())
                .into(imageView);
        standardGSYVideoPlayer.setThumbImageView(imageView);


        helper.setText(R.id.tv_title,item.getData().getTitle());

        if(null!=item.getData().getAuthor()&&null!=item.getData().getAuthor().getName()){
            helper.setText(R.id.tv_author_name,item.getData().getAuthor().getName());
        }

        helper.setText(R.id.tv_comment_count,item.getData().getConsumption().getCollectionCount()+"");


        ImageView ivHead = helper.getView(R.id.iv_head);
        Glide.with(mContext)
                .load(item.getData().getAuthor().getIcon())
                .apply(RequestOptionsUtls.getRequestOptionsHead())
                .into(ivHead);

        helper.getView(R.id.rl_bottom).setOnClickListener(v -> {
            if (standardGSYVideoPlayer.isInPlayingState()) {
                SwitchUtil.savePlayState(standardGSYVideoPlayer);
                standardGSYVideoPlayer.getGSYVideoManager().setLastListener(standardGSYVideoPlayer);
                //fixme 页面跳转是，元素共享，效果会有一个中间中间控件的存在
                //fixme 这时候中间控件 CURRENT_STATE_PLAYING，会触发 startProgressTimer
                //FIXME 但是没有cancel
                VideoDetailActivity.startTActivity((Activity) mContext, standardGSYVideoPlayer);
            }
        });

    }

}
