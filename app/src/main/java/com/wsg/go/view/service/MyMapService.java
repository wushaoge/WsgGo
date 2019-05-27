package com.wsg.go.view.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationManagerCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wsg.go.R;
import com.wsg.go.dbdao.CoordinatePointManagerDao;
import com.wsg.go.dbentity.CoordinatePoint;
import com.wsg.go.utils.AppApplicationMgr;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.view.activity.MapActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MyMapService extends JobIntentService implements AMapLocationListener {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    String channelID = "2333";
    String channelName = "DEV";
    int requestCode = 12346;
    int notificationId = 1000;

    /**
     * 后台jobid,不要与其他的servcie后台服务重复
     */
    public static final int JOB_ID = 77777;

    public static void enqueueWork(Context context, Intent work) {
        LogUtils.e("MyMapService==enqueueWork");
        enqueueWork(context, MyMapService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        LogUtils.e("MyMapService==onHandleWork");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        LogUtils.e("MyMapService==onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("MyMapService==onStartCommand");
        LogUtils.e(intent.getStringExtra("tag"));
        buildDevNotification();
        startlocation();
        return START_STICKY;
    }

    /**
     *
     */
    private void buildDevNotification() {

//        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        Intent i = new Intent(this, MapActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification.Builder builder = new Notification.Builder(this);

        //setSmallIcon 设置小图标
        //setAutoCancel 点击后自动清除
        //setTicker 设置状态栏滚动提示
        //setWhen 设置通知时间，默认为系统发出通知的时间
        //setContentTitle 设置通知标题
        //setContentText 设置通知描述
        //setDefaults 通知效果
        //setOngoing 设置为正在进行的通知
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("正在定位中，请勿关闭") //通知首次出现在通知栏，带上升动画效果的，可设置文字，图标
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getString(R.string.app_name))
                .setContentText("WSGGO正在采集地理坐标,请勿关闭")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(false)
                .setOngoing(true)
                .setShowWhen(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), requestCode, i, PendingIntent.FLAG_CANCEL_CURRENT));

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Android 8.0需要创建频道id
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            //创建通知时指定channelID
            builder.setChannelId(channelID);
        }

//        if (notificationManager != null) {
//            notificationManager.notify(requestCode, notification);
//        }

        startForeground(notificationId,notification);
//        NotificationManagerCompat.from(getApplicationContext()).notify(notificationId, notification);
//        manager.notify(notificationId,notification);

    }

    /**
     * 开始定位。
     */
    private void startlocation() {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //间隔两秒定位一次
            mLocationOption.setInterval(10000);

            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("MyMapService==onDestroy");
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //移除标记为id的通知 (只是针对当前Context下的所有Notification)
        notificationManager.cancel(notificationId);
        EventBus.getDefault().unregister(this);

//        startService(new Intent(this,MyMapService.class));
    }

    @Subscriber(tag = "startTag")
    public void startTag(String content){
        LogUtils.e(content);
    }

    @Subscriber(tag = "closeTag")
    public void closeTag(String content){
        LogUtils.e(content);
    }



    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        String result = AppApplicationMgr.isRunApp()? "正在运行" : "已经关闭了";
        LogUtils.e("主程序" + result);

        if (amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                LogUtils.e("activate定位结果监听" + amapLocation.toString());

                EventBus.getDefault().post(amapLocation,"mapNotice");

                //存储地理位置
                //存储地理位置
                CoordinatePoint point = new CoordinatePoint();
                point.setLatitude(amapLocation.getLatitude());
                point.setLongitude(amapLocation.getLongitude());
                point.setSpeed(amapLocation.getSpeed());
                point.setBearing(amapLocation.getBearing());
                point.setTime(amapLocation.getTime());
                CoordinatePointManagerDao.getInstance().insertPointData(point);
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                LogUtils.e("AmapErr" + errText);
            }
        }
    }
}
