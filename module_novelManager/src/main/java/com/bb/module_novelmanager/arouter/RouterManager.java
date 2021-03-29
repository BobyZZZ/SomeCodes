package com.bb.module_novelmanager.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bb.module_novelmanager.constant.NovelConstant;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 20:36
 */
public class RouterManager {
    private RouterManager() {
    }

    public static RouterManager getInstance() {
        return Holder.mInstance;
    }

    static class Holder {
        static RouterManager mInstance = new RouterManager();
    }

    /**
     * 跳转到小说详情页面
     * @param novelID 小说id
     */
    public void toNovelDetail(String novelID) {
        ARouter.getInstance().build(UrlConstant.ACTIVITY_URL_NOVEL_DETAIL)
                .withString(NovelConstant.KEY_NOVEL_ID, novelID)
                .navigation();
    }

    /**
     * 跳转到小说阅读页面
     * @param novelID 小说id
     */
    public void toNovelReading(String novelID, String chapterId) {
        ARouter.getInstance().build(UrlConstant.ACTIVITY_URL_NOVEL_READING)
                .withString(NovelConstant.KEY_NOVEL_ID, novelID)
                .withString(NovelConstant.KEY_CHAPTER_ID, chapterId)
                .navigation();
    }
}
