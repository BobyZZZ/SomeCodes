package com.bb.reading.db.greenDao.base;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:09
 */
public class BaseDB<T, K> {

    private final AbstractDao<T, K> mDao;

    public BaseDB(AbstractDao<T, K> dao) {
        mDao = dao;
    }

    /************************************************insert****************************************************/
    public long insert(T t) {
        return mDao.insert(t);
    }

    public void insert(List<T> tList) {
        mDao.insertInTx(tList);
    }

    public long insertOrReplace(T t) {
        return mDao.insertOrReplace(t);
    }

    public void insertOrReplace(List<T> t) {
        mDao.insertOrReplaceInTx(t);
    }

    /************************************************delete****************************************************/
    public void delete(T t) {
        mDao.delete(t);
    }

    public void deleteAll() {
        mDao.deleteAll();
    }

    /************************************************update****************************************************/
    public void update(T t) {
        mDao.update(t);
    }

    public void update(List<T> tList) {
        mDao.updateInTx(tList);
    }

    /************************************************query****************************************************/
    public List<T> query(WhereCondition whereCondition,WhereCondition... more) {
        return mDao.queryBuilder().where(whereCondition,more).build().list();
    }

    public T load(K key) {
        T data = mDao.load(key);
        return data;
    }

    public List<T> loadAll() {
        List<T> ts = mDao.loadAll();
        return ts;
    }

    public AbstractDao<T, K> getDao() {
        return mDao;
    }
}
