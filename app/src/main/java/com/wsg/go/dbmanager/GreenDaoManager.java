package com.wsg.go.dbmanager;

import com.wsg.go.application.MyApplication;
import com.wsg.go.dbgen.DaoMaster;
import com.wsg.go.dbgen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 15:50
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GreenDaoManager {

    private static final String DB_NAME = "wsggo_greendao";
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static final GreenDaoManager ourInstance = new GreenDaoManager();

    public static GreenDaoManager getInstance() {
        return ourInstance;
    }

    private GreenDaoManager() {
        if (ourInstance == null) {
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MyApplication.getInstance(), DB_NAME, null);
            Database db = helper.getWritableDb();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }
}
