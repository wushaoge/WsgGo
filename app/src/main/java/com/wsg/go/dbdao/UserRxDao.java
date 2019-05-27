package com.wsg.go.dbdao;

import com.wsg.go.dbentity.UserInfo;
import com.wsg.go.dbmanager.GreenDaoManager;

import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import rx.Observable;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-11 10:24
 * 描    述：Rx版操作
 * 修订历史：
 * ================================================
 */
public class UserRxDao {


    public static rx.Observable<UserInfo> insertData(UserInfo userInfo){
        return GreenDaoManager.getInstance().getDaoSession().getUserInfoDao().rx().insert(userInfo);
    }

    public static Observable<Void> delData(UserInfo userInfo){
        return GreenDaoManager.getInstance().getDaoSession().getUserInfoDao().rx().delete(userInfo);
    }

    public static Observable<List<UserInfo>> queryAllData(){
        RxQuery<UserInfo> rxQuery = GreenDaoManager.getInstance().getDaoSession().getUserInfoDao().queryBuilder().rx();
        return rxQuery.list();
    }

    public static Observable<List<UserInfo>> queryAllData2(){
        return GreenDaoManager.getInstance().getDaoSession().getUserInfoDao().rx().loadAll();
    }

}
