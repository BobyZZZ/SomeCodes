package com.bb.reading.mvp.modle.proxy;

import com.bb.reading.utils.LogUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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
public class DynamicProxyInstance<T> implements InvocationHandler {
    String TAG = "DynamicProxyInstance";

    private T mCacheImpl;
    private T mRealService;

    public T create(Class<T> clazz, T cacheImpl, T realService) {
        T proxyInstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        mCacheImpl = cacheImpl;
        mRealService = realService;
        return proxyInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        checkCache(method, args);
        Object invoke = method.invoke(mRealService, args);
        return invoke;
    }

    private void checkCache(Method method, final Object[] args) {
        if (mCacheImpl != null) {
            try {
                boolean shouldCache = false;
                Method realMethod = mRealService.getClass().getMethod(method.getName(), method.getParameterTypes());
                shouldCache = realMethod.isAnnotationPresent(ShouldCache.class);
                LogUtils.d(TAG, method.getName() + "( )" + "--- ShouldCache: " + shouldCache);
                if (shouldCache) {
                    method.invoke(mCacheImpl, args);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ShouldCache {
    }
}
