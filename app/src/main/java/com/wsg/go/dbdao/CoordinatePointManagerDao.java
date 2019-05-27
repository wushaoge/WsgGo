package com.wsg.go.dbdao;

import com.wsg.go.dbentity.CoordinatePoint;
import com.wsg.go.dbgen.CoordinatePointDao;
import com.wsg.go.dbmanager.GreenDaoManager;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-15 15:04
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CoordinatePointManagerDao {

    private final GreenDaoManager daoManager;
    private static CoordinatePointManagerDao mCoordinatePointManagerDao;

    public CoordinatePointManagerDao() {
        daoManager = GreenDaoManager.getInstance();
    }

    public static CoordinatePointManagerDao getInstance() {
        if (mCoordinatePointManagerDao == null) {
            mCoordinatePointManagerDao = new CoordinatePointManagerDao();
        }
        return mCoordinatePointManagerDao;
    }

    public CoordinatePointDao getCoordinatePointDao() {
        return daoManager.getDaoSession().getCoordinatePointDao();
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public List<CoordinatePoint> queryAllData() {
        return getCoordinatePointDao().loadAll();
    }

    /**
     * 插入数据 若未建表则先建表
     *
     * @param coordinatePoint
     * @return
     */
    public boolean insertPointData(CoordinatePoint coordinatePoint) {
        boolean flag = false;
        flag = getCoordinatePointDao().insert(coordinatePoint) == -1 ? false : true;
        return flag;
    }

    /**
     * 清除数据
     */
    public void deleteAll(){
        getCoordinatePointDao().deleteAll();
    }

}
