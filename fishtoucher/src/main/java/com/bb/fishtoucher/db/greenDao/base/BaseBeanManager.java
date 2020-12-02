package com.bb.fishtoucher.db.greenDao.base;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:09
 */
public class BaseBeanManager<T, K> {

    private final AbstractDao<T, K> mDao;

    public BaseBeanManager(AbstractDao<T, K> dao) {
        mDao = dao;
    }

    /************************************************insert****************************************************/
    public long insert(T t) {
        return mDao.insert(t);
    }

    public long insertOrReplace(T t) {
        return mDao.insertOrReplace(t);
    }

    public void insert(List<T> tList) {
        mDao.insertInTx(tList);
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
