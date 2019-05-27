package com.wsg.go.dbdao;

import com.wsg.go.dbentity.UserInfo;
import com.wsg.go.dbgen.UserInfoDao;
import com.wsg.go.dbmanager.GreenDaoManager;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2019-01-10 16:05
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UserDao {

        private final GreenDaoManager daoManager;
        private static UserDao mUserDao;

        public UserDao() {
            daoManager = GreenDaoManager.getInstance();
        }

        public static UserDao getInstance() {
            if (mUserDao == null) {
                mUserDao = new UserDao();
            }
            return mUserDao;
        }

        /**
         * 插入数据 若未建表则先建表
         *
         * @param userInfo
         * @return
         */
        public boolean insertUserData(UserInfo userInfo) {
            boolean flag = false;
            flag = getUserInfoDao().insert(userInfo) == -1 ? false : true;
            return flag;
        }

        /**
         * 插入或替换数据
         *
         * @param userInfo
         * @return
         */
        public boolean insertOrReplaceData(UserInfo userInfo) {
            boolean flag = false;
            try {
                flag = getUserInfoDao().insertOrReplace(userInfo) == -1 ? false : true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }

        /**
         * 插入多条数据  子线程完成
         *
         * @param list
         * @return
         */
        public boolean insertOrReplaceMultiData(final List<UserInfo> list) {
            boolean flag = false;
            try {
                getUserInfoDao().getSession().runInTx(new Runnable() {
                    @Override
                    public void run() {
                        for (UserInfo userInfo : list) {
                            daoManager.getDaoSession().insertOrReplace(userInfo);
                        }
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }

        /**
         * 更新数据
         *
         * @param userInfo
         * @return
         */
        public boolean updateUserData(UserInfo userInfo) {
            boolean flag = false;
            try {
                getUserInfoDao().update(userInfo);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }

        /**
         * 根据id删除数据
         *
         * @param userInfo
         * @return
         */
        public boolean deleteUserData(UserInfo userInfo) {
            boolean flag = false;
            try {
                getUserInfoDao().delete(userInfo);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }

        /**
         * 删除所有数据
         *
         * @return
         */
        public boolean deleteAllData() {
            boolean flag = false;
            try {
                getUserInfoDao().deleteAll();
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }

        /**
         * 根据主键查询
         *
         * @param key
         * @return
         */
        public UserInfo queryUserDataById(long key) {
            return getUserInfoDao().load(key);
        }

        /**
         * 查询所有数据
         *
         * @return
         */
        public List<UserInfo> queryAllData() {
            return getUserInfoDao().loadAll();
        }

        /**
         * 根据名称查询 以年龄降序排列
         *
         * @param name
         * @return
         */
        public List<UserInfo> queryUserByName(String name) {
            Query<UserInfo> build = null;
            try {
                build = getUserInfoDao().queryBuilder()
                        .where(UserInfoDao.Properties.Name.eq(name))
                        .orderDesc(UserInfoDao.Properties.Age)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return build.list();
        }

        /**
         * 根据参数查询
         *
         * @param where
         * @param param
         * @return
         */
        public List<UserInfo> queryUserByParams(String where, String... param) {
            return getUserInfoDao().queryRaw(where, param);
        }

        public UserInfoDao getUserInfoDao() {
            return daoManager.getDaoSession().getUserInfoDao();
        }
}
