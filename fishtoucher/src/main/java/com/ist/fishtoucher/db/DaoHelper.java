package com.ist.fishtoucher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ist.fishtoucher.db.greenDao.beanManager.NovelDBManager;
import com.ist.fishtoucher.db.greenDao.beanManager.TestGreenDaoDBManager;
import com.ist.fishtoucher.entity.DaoMaster;
import com.ist.fishtoucher.entity.DaoSession;
import com.ist.fishtoucher.entity.TestGreenDaoEntity;
import com.ist.fishtoucher.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/7
 * Time: 21:33
 */
public class DaoHelper {
    String TAG = "GreenDaoManager";
    private static DaoHelper sInstance;

    private final String fDbName = "greenDao_db";
    private DaoSession mDaoSession;
    private TestGreenDaoDBManager mTestGreenDaoDBManager;
    private NovelDBManager mNovelDBManager;

    private DaoHelper() {}

    public static DaoHelper getInstance() {
        if (sInstance == null) {
            synchronized (DaoHelper.class) {
                if (sInstance == null) {
                    sInstance = new DaoHelper();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        if (mDaoSession == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, fDbName);
            SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(writableDatabase);
            mDaoSession = daoMaster.newSession();
        }
    }

    private DaoSession getDaoSession() {
        return mDaoSession;
    }

    /********************************************NovelDBManager**************************************************/
    public NovelDBManager getNovelDBManager() {
        if (mNovelDBManager == null) {
            mNovelDBManager = NovelDBManager.getInstance(mDaoSession);
        }
        return mNovelDBManager;
    }

    /********************************************test**************************************************/
    public TestGreenDaoDBManager getTestGreenDaoDBManager() {
        if (mTestGreenDaoDBManager == null) {
            mTestGreenDaoDBManager = new TestGreenDaoDBManager(mDaoSession.getTestGreenDaoEntityDao());
        }
        return mTestGreenDaoDBManager;
    }

    public void testGreenDaoDBManager() {
        TestGreenDaoDBManager testGreenDaoDBManager = getTestGreenDaoDBManager();
        ArrayList<TestGreenDaoEntity> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestGreenDaoEntity testGreenDaoEntity = new TestGreenDaoEntity("id" + i, "url" + i);
            list.add(testGreenDaoEntity);
        }
        List<TestGreenDaoEntity> loadList;
        //1、删
        testGreenDaoDBManager.deleteAll();
        //查
        loadList = testGreenDaoDBManager.loadAll();
        LogUtils.d(TAG, "loadAllAfterDelete: " + loadList);
        LogUtils.d(TAG, "========================================================================");
        //2、增
        testGreenDaoDBManager.insert(list);
        //查
        loadList = testGreenDaoDBManager.loadAll();
        LogUtils.d(TAG, "loadAllAfterInsert: " + loadList);
        LogUtils.d(TAG, "========================================================================");
        //3、改
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUrl("newUrl"+i);
        }
        testGreenDaoDBManager.update(list);
        loadList = testGreenDaoDBManager.loadAll();
        LogUtils.d(TAG, "loadAllAfterUpdate: " + loadList);
        LogUtils.d(TAG, "========================================================================");
    }
    /********************************************test**************************************************/
}
