package com.wsg.go.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.wsg.go.dbgen.DaoMaster;
import com.wsg.go.dbgen.UserInfoDao;

import org.greenrobot.greendao.database.Database;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 15:55
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {


    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onUpgrade(db, oldVersion, newVersion);
        //----------------------------使用sql实现升级逻辑
//        if (oldVersion == newVersion) {
//            LogUtils.e("onUpgrade:数据库是最新版本,无需升级");
//            return;
//        }
//        LogUtils.e("onUpgrade: 数据库从版本" + oldVersion + "升级到版本" + newVersion);
//        switch (oldVersion) {
//            case 1:
//                String sql = "";
//                db.execSQL(sql);
//            case 2:
//            default:
//                break;
//        }

        if (oldVersion != newVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            },UserInfoDao.class);
        }

    }

}
