package com.bb.reading.mvp.modle;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.HomePageBean;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.log.LongLogUtils;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:44
 */
public class HomeModelTest {
    String TAG = "HomeModelTest";

    @Test
    public void getHomeData() {
        NovelService novelService = RetrofitManager.getInstance().createRs(NovelService.class);
        novelService.getHomeData()
                .subscribe(new BaseObserver<HomePageBean>() {
                    @Override
                    protected void onSuccess(HomePageBean pageData) {
                        LongLogUtils.d(TAG, "onSuccess() called with: pageData = [" + pageData + "]");
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        Log.d(TAG, "onFail() called with: e = [" + e + "]");
                    }
                });
    }
    @Test
    public void getHomeDataHtml() {
        NovelService novelService = RetrofitManager.getInstance().createRs(NovelService.class);
        novelService.getHomeDataTest()
                .subscribe(new BaseObserver<ResponseBody>() {
                    String string;
                    @Override
                    protected void onSuccess(ResponseBody pageData) {
                        try {
                            string = pageData.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LongLogUtils.d(TAG, "onSuccess() called with: pageData = [" + string + "]");
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        Log.d(TAG, "onFail() called with: e = [" + e + "]");
                    }
                });
    }
}