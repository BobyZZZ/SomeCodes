package com.bb.network;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 16:52
 */
public interface INetworkRequestInfo {
    String getAppVersionCode();
    String getAppVersionName();
    boolean isDebug();
}
