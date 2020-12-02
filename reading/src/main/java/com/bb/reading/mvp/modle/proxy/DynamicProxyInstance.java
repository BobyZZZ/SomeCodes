package com.bb.reading.mvp.modle.proxy;

import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 20:43
 */
public class DynamicProxyInstance implements InvocationHandler {
    String TAG = "DynamicProxyInstance";

    private NovelDBManager mNovelDBManager;
    private Object mRealInstance;

    public DynamicProxyInstance setDBManager(NovelDBManager novelDBManager) {
        mNovelDBManager = novelDBManager;
        return this;
    }

    public <T> T create(Class<T> clazz, Object realInstance) {
        T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        mRealInstance = realInstance;
        return proxyInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object cache = checkCache(method, args);
        if (cache != null) {
            LogUtils.d(TAG,method.getName() + " from db!!!");
            return cache;
        }
        Object invoke = method.invoke(mRealInstance, args);
        LogUtils.d(TAG,method.getName() + " from server!!!");
        return invoke;
    }

    private Object checkCache(Method method, final Object[] args) {
        if (mNovelDBManager != null) {
            Method[] declaredMethods = mNovelDBManager.getClass().getDeclaredMethods();
            LogUtils.d(TAG, "checkCache: " + method.getName());
            for (final Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equals(method.getName())) {
                    LogUtils.d(TAG, "find same name method");
                    try {
                        Object invoke = declaredMethod.invoke(mNovelDBManager, args);
                        return invoke;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            LogUtils.e(TAG, "can not find the same name method");
        }
        return null;
    }


}
