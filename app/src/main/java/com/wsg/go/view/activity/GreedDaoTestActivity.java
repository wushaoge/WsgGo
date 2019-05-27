package com.wsg.go.view.activity;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wsg.go.R;
import com.wsg.go.dbdao.UserDao;
import com.wsg.go.dbdao.UserRxDao;
import com.wsg.go.dbentity.UserInfo;
import com.wsg.go.utils.LogUtils;
import com.wsg.go.utils.ToastUtil;
import com.wsg.go.utils.Tools;
import com.wsg.go.view.activity.base.BaseMVCActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 16:15
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GreedDaoTestActivity extends BaseMVCActivity {

    @BindView(R.id.cd_add)
    CardView cdAdd;
    @BindView(R.id.cd_del)
    CardView cdDel;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.cd_result)
    CardView cdResult;
    @BindView(R.id.cd_add_rx)
    CardView cdAddRx;
    @BindView(R.id.cd_del_rx)
    CardView cdDelRx;

    @Override
    public String getChildTitle() {
        return "数据库测试";
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_greeddao_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        queryData();
    }

    @Override
    public void initMainNetData() {

    }

    private void queryData() {
        List<UserInfo> lists = UserDao.getInstance().queryAllData();
        String result = new Gson().toJson(lists);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(result);
        String jsonFormat = gson.toJson(je);
        LogUtils.e(jsonFormat);

        tvResult.setText(jsonFormat);
    }


    private void addData() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("李四");
        userInfo.setAge(Tools.getRandom100());
        userInfo.setSex(1);
        UserDao.getInstance().insertUserData(userInfo);
        queryData();
    }


    private void delData() {
        List<UserInfo> lists = UserDao.getInstance().queryAllData();
        if (!Tools.isEmptyList(lists)) {
            UserDao.getInstance().deleteUserData(lists.get(lists.size() - 1));
        }
        queryData();
    }


    private void queryDataRx() {
        Observable<List<UserInfo>> observable = UserRxDao.queryAllData();
        observable.observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Subscriber<List<UserInfo>>() {
                      @Override
                      public void onCompleted() {
                          ToastUtil.showToast(mContext,"查询成功");
                          LogUtils.e("查询成功");
                      }

                      @Override
                      public void onError(Throwable e) {
                          ToastUtil.showToast(mContext,"查询出错");
                          LogUtils.e("查询出错");
                      }

                      @Override
                      public void onNext(List<UserInfo> userInfos) {
                              String result = new Gson().toJson(userInfos);

                              Gson gson = new GsonBuilder().setPrettyPrinting().create();
                              JsonParser jp = new JsonParser();
                              JsonElement je = jp.parse(result);
                              String jsonFormat = gson.toJson(je);
                              LogUtils.e(jsonFormat);

                              tvResult.setText(jsonFormat);
                      }
                  });

    }


    private void addDataRx() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("孙悟空");
        userInfo.setAge(Tools.getRandom100());
        userInfo.setSex(1);
        rx.Observable<UserInfo> observable = UserRxDao.insertData(userInfo);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showToast(mContext,"添加成功");
                        LogUtils.e("添加成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(mContext,"添加出错");
                        LogUtils.e("添加出错");
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        queryDataRx();
                    }
                });
    }


    private void delDataRx() {
        Observable<List<UserInfo>> observable = UserRxDao.queryAllData2();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(mContext,"queryAllData2查询出错");
                        LogUtils.e("queryAllData2查询出错");
                    }

                    @Override
                    public void onNext(List<UserInfo> userInfos) {
                        if (!Tools.isEmptyList(userInfos)) {
                            rx.Observable<Void> observable = UserRxDao.delData(userInfos.get(userInfos.size() - 1));
                            observable.observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<Void>() {
                                        @Override
                                        public void onCompleted() {
                                            ToastUtil.showToast(mContext,"删除成功");
                                            LogUtils.e("删除成功");
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ToastUtil.showToast(mContext,"删除出错");
                                            LogUtils.e("删除出错");
                                        }

                                        @Override
                                        public void onNext(Void aVoid) {
                                            queryDataRx();
                                        }
                                    });

                        }
                    }
                });
    }



    @OnClick({R.id.cd_add, R.id.cd_del, R.id.cd_add_rx, R.id.cd_del_rx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cd_add:
                addData();
                break;
            case R.id.cd_del:
                delData();
                break;
            case R.id.cd_add_rx:
                addDataRx();
                break;
            case R.id.cd_del_rx:
                delDataRx();
                break;
        }
    }

}
