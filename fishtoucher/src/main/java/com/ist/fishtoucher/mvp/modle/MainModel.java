package com.ist.fishtoucher.mvp.modle;


import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.utils.RetrofitManager;


import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MainModel implements MainContract.IMainModel {
    String TAG = "MainModel";
    private NovelService mNovelService;

    public MainModel() {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public Observable<ResponseBody> getCategory(String novelIndex) {
        Observable<ResponseBody> observable = mNovelService.getCategory(novelIndex);
        return observable;
    }

    @Override
    public Observable<ResponseBody> getChapter(String novelIndex,String chapterIndex) {
        NovelService novelService = RetrofitManager.getInstance().createRs(NovelService.class);
        Observable<ResponseBody> observable = novelService.getChapter(novelIndex,chapterIndex);
        return observable;
    }


}
