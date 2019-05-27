package com.wsg.go.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.amap.api.trace.TraceOverlay;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wsg.go.R;
import com.wsg.go.dbdao.CoordinatePointManagerDao;
import com.wsg.go.dbentity.CoordinatePoint;
import com.wsg.go.utils.AppApplicationMgr;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.view.service.MyMapService;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-14 11:39
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, TraceListener {

    @BindView(R.id.cd_start)
    CardView cdStart;
    @BindView(R.id.cd_search)
    CardView cdSearch;
    @BindView(R.id.cd_stop)
    CardView cdStop;
    @BindView(R.id.cd_add_line)
    CardView cdAddLine;
    @BindView(R.id.cd_rectify_line)
    CardView cdRectifyLine;
    @BindView(R.id.cd_clear_line)
    CardView cdClearLine;
    @BindView(R.id.cd_del_sql)
    CardView cdDelSql;

    MapView map;

    private AMap mAMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyLocationStyle myLocationStyle;
    private PolylineOptions mPolyoptions, tracePolytion;
    private Polyline mpolyline;


    //纠偏
    private LBSTraceClient mTraceClient;
    private List<TraceLocation> mTraceList;
    private ConcurrentMap<Integer, TraceOverlay> mOverlayList = new ConcurrentHashMap<Integer, TraceOverlay>();
    private int mCoordinateType = LBSTraceClient.TYPE_AMAP;
    private int mSequenceLineID = 1000;


    private double Lat_A = 31.303605;
    private double Lon_A = 121.342179;

    private double Lat_B = 31.403605;
    private double Lon_B = 121.442179;

    private double Lat_C = 31.503605;
    private double Lon_C = 121.542179;

    private double Lat_D = 31.603605;
    private double Lon_D = 121.642179;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        map = (MapView) findViewById(R.id.map);
        map.onCreate(savedInstanceState);// 此方法必须重写
        initData();
        //addPolylinesWithGradientColors();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mapServiceRuning();
    }

    public void initData() {
        mapServiceRuning();
        if (mAMap == null) {
            mAMap = map.getMap();
            setUpMap();
        }
    }

    /**
     * 多段颜色（渐变色）
     */
    private void addPolylinesWithGradientColors() {
        //四个点
        LatLng A = new LatLng(Lat_A, Lon_A);
        LatLng B = new LatLng(Lat_B, Lon_B);
        LatLng C = new LatLng(Lat_C, Lon_C);
        LatLng D = new LatLng(Lat_D, Lon_D);

        //用一个数组来存放颜色，渐变色，四个点需要设置四个颜色
        List<Integer> colorList = new ArrayList<Integer>();
        colorList.add(Color.RED);
        colorList.add(Color.YELLOW);
        colorList.add(Color.GREEN);
        colorList.add(Color.BLACK);//如果第四个颜色不添加，那么最后一段将显示上一段的颜色

        PolylineOptions options = new PolylineOptions();
        options.width(20);//设置宽度

        //加入四个点
        options.add(A, B, C, D);

        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        options.colorValues(colorList);

        //加上这个属性，表示使用渐变线
        options.useGradient(true);

        mAMap.addPolyline(options);
    }


    /**
     * 画线--多段颜色（渐变色）
     */
    private void drawLine() {
        List<LatLng> latList = new ArrayList<>();

        List<CoordinatePoint> list = CoordinatePointManagerDao.getInstance().queryAllData();
        for (CoordinatePoint point : list) {
            latList.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }


        //用一个数组来存放颜色，渐变色，四个点需要设置四个颜色
//        List<Integer> colorList = new ArrayList<Integer>();
//        colorList.add(Color.RED);
//        colorList.add(Color.YELLOW);
//        colorList.add(Color.GREEN);
//        colorList.add(Color.BLACK);//如果第四个颜色不添加，那么最后一段将显示上一段的颜色

        PolylineOptions options = new PolylineOptions();
        options.width(20);//设置宽度

        //加入全部的点
        options.addAll(latList);

        options.color(R.color.colorPrimary);

//        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
//        options.colorValues(colorList);
//
//        //加上这个属性，表示使用渐变线
//        options.useGradient(true);

        mAMap.addPolyline(options);
    }

    /**
     * 清除地图已完成或出错的轨迹
     */
    private void cleanFinishTrace() {
        mAMap.clear();
    }

    /**
     * 纠偏
     */
    private void tracLine() {
        mTraceList = new ArrayList<>();
        List<CoordinatePoint> list = CoordinatePointManagerDao.getInstance().queryAllData();
        for (CoordinatePoint point : list) {
            mTraceList.add(new TraceLocation(point.getLatitude(), point.getLongitude(), point.getSpeed(), point.getBearing(), point.getTime()));
        }

        if (mOverlayList.containsKey(mSequenceLineID)) {
            TraceOverlay overlay = mOverlayList.get(mSequenceLineID);
            overlay.zoopToSpan();
            int status = overlay.getTraceStatus();
            String tipString = "";
            if (status == TraceOverlay.TRACE_STATUS_PROCESSING) {
                tipString = "该线路轨迹纠偏进行中...";
            } else if (status == TraceOverlay.TRACE_STATUS_FINISH) {
                tipString = "该线路轨迹已完成";
            } else if (status == TraceOverlay.TRACE_STATUS_FAILURE) {
                tipString = "该线路轨迹失败";
            } else if (status == TraceOverlay.TRACE_STATUS_PREPARE) {
                tipString = "该线路轨迹纠偏已经开始";
            }
            Toast.makeText(this.getApplicationContext(), tipString,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        TraceOverlay mTraceOverlay = new TraceOverlay(mAMap);
        mOverlayList.put(mSequenceLineID, mTraceOverlay);
        List<LatLng> mapList = traceLocationToMap(mTraceList);
        mTraceOverlay.setProperCamera(mapList);
        mTraceClient = new LBSTraceClient(this.getApplicationContext());
        mTraceClient.queryProcessedTrace(mSequenceLineID, mTraceList,
                mCoordinateType, this);

    }

    /**
     * 轨迹纠偏点转换为地图LatLng
     *
     * @param traceLocationList
     * @return
     */
    public List<LatLng> traceLocationToMap(List<TraceLocation> traceLocationList) {
        List<LatLng> mapList = new ArrayList<LatLng>();
        for (TraceLocation location : traceLocationList) {
            LatLng latlng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            mapList.add(latlng);
        }
        return mapList;
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//        myLocationStyle.interval(1); //设置发起定位请求的时间间隔，单位：毫秒，默认值：1000毫秒，如果传小于1000的任何值将执行单次定位。
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        //初始化并且定位一次 直到点击开始才开始循环定位
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }


////        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }


    /**
     * 开始定位。
     */
    private void startlocation() {
        if (mLocationClient != null) {
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
            mAMap.setMyLocationStyle(myLocationStyle);

            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //间隔两秒定位一次
            mLocationOption.setInterval(2000);

            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    private void stoplocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    @OnClick({R.id.cd_start, R.id.cd_search, R.id.cd_stop, R.id.cd_add_line, R.id.cd_rectify_line, R.id.cd_clear_line, R.id.cd_del_sql})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cd_start:
                ToastUtil.showToast(this, "开始定位");
//                startlocation();
                //地图service
//                MyMapService.enqueueWork(getApplicationContext(), new Intent());
                EventBus.getDefault().post("我要开启","startTag");

                Intent mapIntent = new Intent(this, MyMapService.class);
                mapIntent.putExtra("tag","口腔癌是");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(mapIntent);
                }else{
                    startService(mapIntent);
                }
                break;
            case R.id.cd_stop:
                ToastUtil.showToast(this, "停止定位");
//                stoplocation();
                EventBus.getDefault().post("我要手动关闭啦","closeTag");
                stopService(new Intent(this, MyMapService.class));
                break;
            case R.id.cd_search:
                List<CoordinatePoint> list = CoordinatePointManagerDao.getInstance().queryAllData();
                String result = new Gson().toJson(list);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(result);
                String jsonFormat = gson.toJson(je);
                LogUtils.e(jsonFormat);

                ToastUtil.showToast(this, jsonFormat);
                break;
            case R.id.cd_del_sql:
                CoordinatePointManagerDao.getInstance().deleteAll();
                break;
            case R.id.cd_add_line:
                drawLine();
                break;
            case R.id.cd_rectify_line:
                tracLine();
                break;
            case R.id.cd_clear_line:
                cleanFinishTrace();
                break;
        }
    }

//    @Override
//    public MapView getMapView() {
//        return map;
//    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        LogUtils.e("~~~~~~activate~~~~~~:");
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();

        }
        mLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtils.e("onLocationChanged定位结果监听:");
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LogUtils.e("activate定位结果监听" + amapLocation.toString());
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

//            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
//                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                LatLng mylocation = new LatLng(amapLocation.getLatitude(),
//                        amapLocation.getLongitude());
//                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));
//                redrawline();
//                //
//                LogUtils.e("activate定位结果监听" + amapLocation.toString());
//            } else {
//                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
//                        + amapLocation.getErrorInfo();
//                LogUtils.e("AmapErr" + errText);
//            }
        }
    }


    @Subscriber(tag = "mapNotice")
    public void mapNotice(AMapLocation amapLocation) {
        LogUtils.e("service通知消息" + amapLocation.getLongitude() + "--" + amapLocation.getLatitude());
        mapServiceRuning();
    }

    private void mapServiceRuning() {
        String result = AppApplicationMgr.isServiceRunning(this, MyMapService.class.getName()) ? "正在运行" : "没有运行";
        LogUtils.e(MyMapService.class.getName() + result);
    }


    /**
     * 实时轨迹画线
     */
    private void redrawline() {
        if (mPolyoptions.getPoints().size() > 1) {
            if (mpolyline != null) {
                mpolyline.setPoints(mPolyoptions.getPoints());
            } else {
                mpolyline = mAMap.addPolyline(mPolyoptions);
            }
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }

    /**
     * 轨迹纠偏失败回调
     */
    @Override
    public void onRequestFailed(int lineID, String errorInfo) {
        LogUtils.e("onRequestFailed纠偏失败");
        Toast.makeText(this.getApplicationContext(), errorInfo,
                Toast.LENGTH_SHORT).show();
        if (mOverlayList.containsKey(lineID)) {
            TraceOverlay overlay = mOverlayList.get(lineID);
            overlay.setTraceStatus(TraceOverlay.TRACE_STATUS_FAILURE);
        }
    }

    /**
     * 轨迹纠偏过程回调
     */
    @Override
    public void onTraceProcessing(int lineID, int index, List<LatLng> segments) {
        LogUtils.e("onTraceProcessing轨迹纠偏过程回调");
        if (segments == null) {
            return;
        }
        if (mOverlayList.containsKey(lineID)) {
            TraceOverlay overlay = mOverlayList.get(lineID);
            overlay.setTraceStatus(TraceOverlay.TRACE_STATUS_PROCESSING);
            overlay.add(segments);
        }
    }

    /**
     * 轨迹纠偏结束回调
     */
    @Override
    public void onFinished(int lineID, List<LatLng> linepoints, int distance,
                           int watingtime) {
        LogUtils.e("onFinished轨迹纠偏结束回调");
        Toast.makeText(this.getApplicationContext(), "onFinished",
                Toast.LENGTH_SHORT).show();
        if (mOverlayList.containsKey(lineID)) {
            TraceOverlay overlay = mOverlayList.get(lineID);
            overlay.setTraceStatus(TraceOverlay.TRACE_STATUS_FINISH);
            overlay.setDistance(distance);
            overlay.setWaitTime(watingtime);
        }

    }
}
