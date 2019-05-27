package com.wsg.go.view.activity.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.wsg.go.R;
import com.wsg.go.entity.BaseData;
import com.wsg.go.okhttp.BaseDefaultCallback;
import com.wsg.go.okhttp.EBaseViewStatus;
import com.wsg.go.okhttp.IBaseNetView;
import com.wsg.go.okhttp.IBaseUIView;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018年07月25日13:29:37
 * 描    述：进一步优化
 * 修订历史：
 * ================================================
 */

public abstract class BaseMVCActivity extends MySwipeBackActivity  implements IBaseUIView,IBaseNetView {

    public Context mContext;

    EBaseViewStatus baseViewStatus = EBaseViewStatus.LOADING; //页面显示状态 SUCCESS, LOADING, ERROR  分别表示正常、加载中、失败

    //-----------公共控件START-------------//
    protected ImageView ivBack;
    protected TextView tvLeft;
    protected RelativeLayout rlBack;
    protected TextView tvTitle;
    protected ImageView ivRight;
    protected TextView tvRight;
    protected LinearLayout llRight;
    protected RelativeLayout rlRight;
    protected LinearLayout baseMain;
//  protected ImageView ivError;
    protected LottieAnimationView lottieErrorView;
    protected TextView tvError;
    protected TextView tvErrorHint;
    protected TextView tvReload;
    protected ViewStub vsLoading;
    protected ViewStub vsError;
    protected LinearLayout llError;
    protected LinearLayout llLoading;
    protected TextView tvBaseloadingmsg;
    protected LottieAnimationView lottieLoadingView;
    protected RelativeLayout baseHead;
    //-----------公共控件END-------------//
    Unbinder unbinder;

    //分页
    public int pageNo = 1;
    public int pageSize = 10;
    public int pageCount= 0;

    public abstract String getChildTitle(); //获取标题
    public abstract int getLayoutID(); //获取布局资源文件
    public abstract void initView(); //初始化view
    public abstract void initData(); //初始化数据
    public abstract void initMainNetData(); //主协议请求数据,适用于必须请求协议才能展示的页面,在initData之前请求
    public void setContentBefore(){}; //setContentView之前的方法
    public boolean isShowBarStatus(){return true;} //是否显示状态栏
    public boolean isShowLoading(){return false;} //是否显示加载中布局,如果return true则必须重写initMainNetData方法
    public void getBundleExtras(Bundle extras){} //接收bundle数据

    public Bundle baseSavedInstanceState; //地图有用到

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseSavedInstanceState = savedInstanceState;
        mContext = this;
        setContentBefore();
        setContentView(R.layout.activity_base_new);
        EventBus.getDefault().register(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        initViewsBase();
        setChildView(getLayoutID(),getChildTitle(),isShowLoading());
    }

    private void initViewsBase() {
        //沉浸式状态栏
        if(isShowBarStatus()) {
            StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(mContext, R.color.colorPrimary), 0);
        }

        //头部
        baseHead = (RelativeLayout) findViewById(R.id.base_head);
        rlBack = baseHead.findViewById(R.id.rl_back);
        ivBack = baseHead.findViewById(R.id.iv_back);
        tvLeft = baseHead.findViewById(R.id.tv_left);
        tvTitle = baseHead.findViewById(R.id.tv_title);
        rlRight = baseHead.findViewById(R.id.rl_right);
        ivRight = baseHead.findViewById(R.id.iv_right);
        tvRight = baseHead.findViewById(R.id.tv_right);

        rlRight.setVisibility(View.INVISIBLE);
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);

        //错误显示内容
        vsError = (ViewStub) findViewById(R.id.vs_error);

        //主体布局
        baseMain = (LinearLayout) findViewById(R.id.base_main);

        //加载中布局
        vsLoading = (ViewStub) findViewById(R.id.vs_loading);


        //默认布局
        baseMain.setVisibility(View.VISIBLE);


        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backClick();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightClick();
            }
        });
    }

    /**
     * 状态栏着色，传自定义颜色
     */
    public void setBarStatusColor(int colorId) {
        StatusBarUtil.setColorForSwipeBack(this, ContextCompat.getColor(mContext, colorId), 0);
    }

    /**
     * 隐藏状态栏
     */
    public void hideStatusBar(){
        StatusBarUtil.hideFakeStatusBarView(this);
        ViewGroup contentView = ((ViewGroup) this.findViewById(android.R.id.content));
        contentView.setPadding(0, 0, 0, 0);
    }


    /**
     * 返回事件
     */
    public void backClick(){
        this.finish();
    }

    /**
     * 右侧布局事件
     */
    public void rightClick() {

    }

    /**
     * 设置右边为搜索图标
     */
    public void setRightImageForSearch(View.OnClickListener listener) {
        rlRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.ic_search_white);
        ivRight.setOnClickListener(listener);
    }

    public void setRightLayoutInVisible(){
        rlRight.setVisibility(View.INVISIBLE);
    }

    public void setRightLayoutGone(){
        rlRight.setVisibility(View.GONE);
    }

    /**
     * 设置子布局+标题
     *
     * @param layoutID
     * @param title
     */
    public void setChildView(int layoutID, String title, boolean isShowLoading) {
        setTitle(title);
        LayoutInflater flater = LayoutInflater.from(this);
        View view = flater.inflate(layoutID, baseMain,false);
        baseMain.addView(view);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
        initMainNetData();
        if(isShowLoading){
            showLoadingLayout();
        }else{
            showSuccessLayout();
        }
    }


    @Override
    public EBaseViewStatus getBaseViewStatus() {
        return baseViewStatus;
    }

    @Override
    public void setBaseViewStatus(EBaseViewStatus baseViewStatus) {
        this.baseViewStatus = baseViewStatus;
        switch (this.baseViewStatus){
            case LOADING:
                showLoadingLayout();
                break;
            case SUCCESS:
                showSuccessLayout();
                break;
            case ERROR:
                showErrorLayout("出错啦");
                break;
        }
    }


    /**
     * 显示加载中界面
     */
    @Override
    public void showLoadingLayout() {
        if(null == llLoading){
            llLoading = (LinearLayout) vsLoading.inflate();
            tvBaseloadingmsg = (TextView) findViewById(R.id.tv_baseloadingmsg);
            lottieLoadingView = (LottieAnimationView) findViewById(R.id.lottie_loading_view);
            lottieLoadingView.setAnimation("whale.zip");
            lottieLoadingView.setRepeatCount(ValueAnimator.INFINITE);
            lottieLoadingView.playAnimation();
        }

        baseMain.setVisibility(View.GONE);
        if(null != llError){
            llError.setVisibility(View.GONE);
        }
        if(null != llLoading){
            llLoading.setVisibility(View.VISIBLE);
        }
        baseViewStatus = EBaseViewStatus.LOADING;
    }

    /**
     * 显示子布局,隐藏错误布局
     */
    @Override
    public void showSuccessLayout() {
        baseMain.setVisibility(View.VISIBLE);
        if(null != llError){
            llError.setVisibility(View.GONE);
        }
        if(null != llLoading){
            llLoading.setVisibility(View.GONE);
        }
        baseViewStatus = EBaseViewStatus.SUCCESS;
    }

    /**
     * 显示错误布局,隐藏子布局
     *
     * @param error
     */
    @Override
    public void showErrorLayout(String error) {
        if(null == llError){
            llError = (LinearLayout) vsError.inflate();
            tvReload = (TextView)llError.findViewById(R.id.tv_reload);
//        ivError = (ImageView)llError.findViewById(R.id.iv_error);
            tvError = (TextView) llError.findViewById(R.id.tv_error);
            tvErrorHint = (TextView) llError.findViewById(R.id.tv_error_hint);
            lottieErrorView = (LottieAnimationView) findViewById(R.id.lottie_error_view);
            lottieErrorView.setAnimation("halloween_smoothymon.json");
            lottieErrorView.setRepeatCount(ValueAnimator.INFINITE);
            lottieErrorView.playAnimation();

            tvReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initMainNetData();
                }
            });
        }

        baseMain.setVisibility(View.GONE);

        if(null != llLoading){
            llLoading.setVisibility(View.GONE);
        }
        if(null != llError){
            llError.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(error)){
            tvError.setText(error);
            tvErrorHint.setText(error);
        }
        baseViewStatus = EBaseViewStatus.ERROR;
    }


    /**
     * 去掉标题栏
     */
    public void setHeadGone() {
        baseHead.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回按钮
     */
    public void setBackGone() {
        rlBack.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回按钮
     */
    public void setBackInVisible() {
        rlBack.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示返回按钮
     */
    public void setBackVisible() {
        rlBack.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }


    /**
     * 设置右侧文字
     * @param rightStr
     * @param listener
     */
    public void setRightTitle(String rightStr, View.OnClickListener listener) {
        rlRight.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightStr);
        tvRight.setOnClickListener(listener);
    }

    /**
     * 设置右侧文字
     * @param rightTitle
     */
    protected void setRightTitle(String rightTitle) {
        rlRight.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightTitle);
    }

    /**
     * 设置右侧图标
     *
     * @param resource
     */
    public void setRightImg(int resource) {
        rlRight.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resource);
    }

    /**
     * 设置左侧文字
     * @param text
     */
    public void setLeftText(String text) {
        ivBack.setVisibility(View.GONE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    /**
     * 设置出错提示
     * @param error
     */
    public void setErrorText(String error){
        tvError.setText(error);
        tvErrorHint.setText(error);
    }

    /**
     * 默认返回BaseData,显示加载框
     *
     * @param cmd
     * @return
     */
    public BaseDefaultCallback getCallback(String cmd) {
        return new BaseDefaultCallback<>(mContext, BaseData.class, cmd, this,this);
    }

    /**
     * 默认返回BaseData,不显示加载框
     * @param cmd
     * @return
     */
    public BaseDefaultCallback getCallbackNoDialog(String cmd) {
        return new BaseDefaultCallback<>(mContext, BaseData.class, cmd, false, this,this);
    }

    /**
     * 返回自定义Data,显示加载框
     *
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomData(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(mContext, data, cmd, this,this);
    }

    /**
     * 返回自定义Data,不显示加载框
     *
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomDataNoDialog(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(mContext, data, cmd, false, this,this);
    }

    /**
     * 返回自定义Data,不显示加载框,并且加载出错显示错误布局，适用于主协议请求
     * @param data
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomDataMainInterface(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(mContext, data, cmd, false, this, this, true);
    }


    @Override
    public void onStart(String cmd) {

    }


    @Override
    public void onSuccess(Object data, String cmd) {

    }

    @Override
    public void onError(Object data, String cmd) {

    }

    @Override
    public void onResult(String cmd) {

    }


    /**
     * startActivity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }


    /**
     * 结束刷新/加载
     *
     * @param srl
     */
    public void stopLoad(SmartRefreshLayout srl) {
        if (srl != null) {
//            if(pageNo < pageCount){
//                srl.setEnableLoadMore(true);
//            }else{
//                srl.setEnableLoadMore(false);
//            }
            if (srl.getState() == RefreshState.Refreshing) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != lottieLoadingView){
            lottieLoadingView.resumeAnimation();
        }
        if(null != lottieErrorView){
            lottieErrorView.resumeAnimation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != lottieLoadingView){
            lottieLoadingView.pauseAnimation();
        }
        if(null != lottieErrorView){
            lottieErrorView.pauseAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
