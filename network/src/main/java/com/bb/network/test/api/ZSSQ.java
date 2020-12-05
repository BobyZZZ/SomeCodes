package com.bb.network.test.api;

import com.bb.network.test.bean.AllSoft;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/5
 * Time: 20:38
 */
public interface ZSSQ {
    //http://api.zhuishushenqi.com/cats/lv2/statistics
    String mBaseUrl = "http://api.zhuishushenqi.com";
    @GET("/cats/lv2/statistics")
    Observable<AllSoft> getAllSoft();
}
