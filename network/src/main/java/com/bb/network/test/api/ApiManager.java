package com.bb.network.test.api;

import com.bb.network.INetworkRequestInfo;
import com.bb.network.NetworkApiServer;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/5
 * Time: 21:05
 */
public class ApiManager extends NetworkApiServer implements INetworkRequestInfo {
    private static ApiManager mInstance;

    public static ApiManager getInstance() {
        if (mInstance ==null) {
            synchronized (ApiManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiManager();
                }
            }
        }
        return mInstance;
    }

    private ApiManager() {
        init(this);
    }

    @Override
    public String getFormal() {
        return ZSSQ.mBaseUrl;
    }

    @Override
    public String getTest() {
        return ZSSQ.mBaseUrl;
    }

    @Override
    public String getAppVersionCode() {
        return "1";
    }

    @Override
    public String getAppVersionName() {
        return "test network lib";
    }

    @Override
    public boolean isDebug() {
        return false;
    }
}
