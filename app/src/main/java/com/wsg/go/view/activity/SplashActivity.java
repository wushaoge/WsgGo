package com.wsg.go.view.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.wsg.go.MainActivity;
import com.wsg.go.R;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.view.activity.base.BaseMVCActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-18 09:58
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SplashActivity extends BaseMVCActivity implements EasyPermissions.PermissionCallbacks {

    private static final int PERMISSION_CODE = 6666;

    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION};

    @BindView(R.id.lottie_view)
    LottieAnimationView lottieView;
    @BindView(R.id.tv_countdown)
    TextView tvCountdown;
    @BindView(R.id.ll_skip)
    LinearLayout llSkip;

    @Override
    public String getChildTitle() {
        return null;
    }

    @Override
    public void setContentBefore() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    @Override
    public boolean isShowBarStatus() {
        return false;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setHeadGone();
        //申请权限
        checkPermissions();
    }

    @Override
    public void initMainNetData() {

    }

    private void startLottie() {
        //https://www.lottiefiles.com/download/3508
        lottieView.setAnimation("fabulous_onboarding_animation.json");
//        lottieView.setAnimation("bouncy_loader.zip");
//        lottieView.setAnimationFromUrl("https://www.lottiefiles.com/download/3508");
//        lottieView.setSpeed(-1);
        lottieView.addAnimatorUpdateListener(valueAnimator -> {
//            LogUtils.e(valueAnimator.getAnimatedFraction()+"");
            if(valueAnimator.getAnimatedFraction() == 1f){
                readyGoThenKill(MainActivity.class);
            }
        });
//        lottieView.addAnimatorListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                LogUtils.e("跳转啦啦啦啦");
//                readyGoThenKill(MainActivity.class);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
        lottieView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                LogUtils.e("总时长:"+lottieView.getDuration());
            }
        });
        lottieView.playAnimation();
//        lottieView.setProgress(0.5f);

    }

    @OnClick({R.id.tv_countdown, R.id.ll_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_countdown:
                break;
            case R.id.ll_skip:
//                lottieView.removeAllUpdateListeners();
                lottieView.removeAllUpdateListeners();
                readyGoThenKill(MainActivity.class);
                break;
        }
    }


    private void checkPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 已经申请过权限，做想做的事
            startLottie();
        } else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(this, "请授予相关权限才能正常运行", PERMISSION_CODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_CODE) {
            checkPermissions();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMISSION_CODE) {
            ToastUtil.showToast(mContext, "请授予相关权限才能正常运行APP");
            finish();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }



}
