package com.wsg.go.view.fragment.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
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
 * 创建日期：2018年12月13日09:31:38
 * 描    述：
 * 修订历史：
 * ================================================
 */

public abstract class BaseFragment extends Fragment  implements IBaseUIView, IBaseNetView {
    public static final String TITLE = "";

    EBaseViewStatus baseViewStatus = EBaseViewStatus.LOADING; //页面显示状态 SUCCESS, LOADING, ERROR  分别表示正常、加载中、失败

    public Context mContext;

    private boolean isFirstLoad = true;//是否是第一次加载
    private boolean isVisible;//是否对用户可见
    private boolean isInitView;//是否初始化控件

    protected View baseView; //公共布局view
    protected View convertView;//显示的converView

    //-----------公共控件START-------------//
    LinearLayout baseMain;
//    ImageView ivError;
    LottieAnimationView lottieErrorView;
    TextView tvError;
    TextView tvErrorHint;
    TextView tvReload;
    LinearLayout llError;
    LinearLayout llLoading;
    TextView tvBaseloadingmsg;
    LottieAnimationView lottieLoadingView;
    //-----------公共控件END-------------//
    Unbinder unbinder;


    public abstract int getLayoutID(); //获取布局资源文件
    public abstract void initViews();//初始化控件
    public abstract void initData();//初始化完后直接加载数据
    public abstract void initLazyData();//懒加载数据，如：网络请求获取数据

    public void reloadData(){ initLazyData(); } //重新加载数据,默认为加载initLazyData
    public boolean isShowLoading(){return false;} //是否显示加载中布局,如果return true则必须重写initData方法

    public int pageNo = 1;
    public int pageSize = 10;
    public int pageCount = 0;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isFirstLoad = true;
        initViewsBase(inflater,container); //初始化公共布局
        convertView = inflater.inflate(getLayoutID(),container,false);//用布局填充器填充布局
        unbinder = ButterKnife.bind(this, convertView);
        baseMain.addView(convertView); //将显示布局添加进来
        initViews();//初始化控件
        initShowView(); //初始化布局状态
        initData(); //初始化数据
        isInitView = true;//已经初始化
        lazyLoad();//懒加载数据
        return baseView;
    }

    private void initViewsBase(LayoutInflater inflater,ViewGroup container) {

        baseView = inflater.inflate(R.layout.fragment_base,container,false);//用布局填充器填充布局

        //错误显示内容
        llError = (LinearLayout) baseView.findViewById(R.id.ll_error);
        tvReload = (TextView)llError.findViewById(R.id.tv_reload);
        tvError = (TextView)llError.findViewById(R.id.tv_error);
        tvErrorHint = (TextView)llError.findViewById(R.id.tv_error_hint);
//        ivError = (ImageView)llError.findViewById(R.id.iv_error);
        lottieErrorView = (LottieAnimationView) llError.findViewById(R.id.lottie_error_view);
        lottieErrorView.setAnimation("halloween_smoothymon.json");
        lottieErrorView.setRepeatCount(ValueAnimator.INFINITE);
//        lottieErrorView.playAnimation();

        //主体布局
        baseMain = (LinearLayout) baseView.findViewById(R.id.base_main);

        //加载中布局
        llLoading = (LinearLayout) baseView.findViewById(R.id.ll_loading);
        tvBaseloadingmsg = (TextView) llLoading.findViewById(R.id.tv_baseloadingmsg);
        lottieLoadingView = (LottieAnimationView) llLoading.findViewById(R.id.lottie_loading_view);
        lottieLoadingView.setAnimation("whale.zip");
        lottieLoadingView.setRepeatCount(ValueAnimator.INFINITE);
//        lottieLoadingView.playAnimation();


        //默认布局
        baseMain.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);

        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLazyData();
            }
        });

    }

    private void initShowView(){
        if(isShowLoading()){
            showLoadingLayout();
        }else{
            showSuccessLayout();
        }
    }

    /**
     * 懒加载
     * 如果不是第一次加载、没有初始化控件、不对用户可见就不加载
     */
    protected  void lazyLoad(){
        if(!isFirstLoad || !isInitView || !isVisible){
            return;
        }
        initLazyData();//初始化数据
        isFirstLoad = false;//已经不是第一次加载
    }

    /**
     * 是否对用户可见
     * @param isVisibleToUser true:表示对用户可见，反之则不可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            //这里根据需求，如果不要每次对用户可见的时候就加载就不要调用lazyLoad()这个方法，根据个人需求
            lazyLoad();
        }else{
            isVisible = false;
        }
    }

    public void hideSoftKeyboard(){
        if (getContext() != null && getActivity() != null) {
            InputMethodManager service = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (service != null && service.isActive()) {
                service.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘
            }
        }
    }

    /**
     * 默认返回BaseData,显示加载框
     *
     * @param cmd
     * @return
     */
    public BaseDefaultCallback getCallback(String cmd) {
        return new BaseDefaultCallback<>(getActivity(), BaseData.class, cmd, this, this);
    }

    /**
     * 默认返回BaseData,不显示加载框
     *
     * @param cmd
     * @return
     */
    public BaseDefaultCallback getCallbackNoDialog(String cmd) {
        return new BaseDefaultCallback<>(getActivity(), BaseData.class, cmd, false, this, this);
    }

    /**
     * 返回自定义Data,显示加载框
     *
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomData(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(getActivity(), data, cmd, this, this);
    }

    /**
     * 返回自定义Data,不显示加载框
     *
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomDataNoDialog(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(getActivity(), data, cmd, false, this, this);
    }


    /**
     * 返回自定义Data,不显示加载框,并且加载出错显示错误布局，主协议
     *
     * @param cmd
     * @return
     */
    public <T> BaseDefaultCallback<T> getCallbackCustomDataMainInterface(Class<T> data, String cmd) {
        return new BaseDefaultCallback<>(getActivity(), data, cmd, false, this,this, true);
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
        baseMain.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        //显示布局再加载动画
        lottieLoadingView.playAnimation();
        baseViewStatus = EBaseViewStatus.LOADING;
    }

    /**
     * 显示正常的子布局,隐藏错误布局
     */
    @Override
    public void showSuccessLayout() {
        baseMain.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
        baseViewStatus = EBaseViewStatus.SUCCESS;
    }

    /**
     * 显示错误布局,隐藏子布局
     *
     * @param error
     */
    @Override
    public void showErrorLayout(String error) {
        baseMain.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
        //显示布局再加载动画
        lottieErrorView.playAnimation();
        if(!TextUtils.isEmpty(error)){
            tvError.setText(error);
            tvErrorHint.setText(error);
        }
        baseViewStatus = EBaseViewStatus.ERROR;
    }



    /**
     * 结束刷新/加载
     *
     * @param srl
     */
    public void stopLoad(SmartRefreshLayout srl) {
        if (srl != null) {
            if (srl.getState() == RefreshState.Refreshing) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }
    }

    /**
     * startActivity
     *
     * @param clazz clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        lottieLoadingView.resumeAnimation();
        lottieErrorView.resumeAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        lottieLoadingView.pauseAnimation();
        lottieErrorView.pauseAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
