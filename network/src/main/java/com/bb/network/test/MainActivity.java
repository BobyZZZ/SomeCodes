package com.bb.network.test;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bb.network.INetworkRequestInfo;
import com.bb.network.NetworkApiServer;
import com.bb.network.observer.BaseObserver;
import com.bb.network.test.api.ApiManager;
import com.bb.network.test.api.ZSSQ;
import com.bb.network.test.bean.AllSoft;
import com.bb.network.R;

public class MainActivity extends AppCompatActivity implements INetworkRequestInfo {
    String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        NetworkApiServer networkApiServer = ApiManager.getInstance();
        ZSSQ zssqApi = networkApiServer.getService(ZSSQ.class);
        zssqApi.getAllSoft()
                .compose(networkApiServer.<AllSoft>ioToMainWithHandlerError())
                .subscribe(new BaseObserver<AllSoft>() {
                    @Override
                    protected void onSuccess(AllSoft allSoft) {
                        Log.w(TAG, "onSuccess: " + allSoft);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        Log.e(TAG, "onFail: " + e.toString());
                    }
                });
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
        return true;
    }
}
