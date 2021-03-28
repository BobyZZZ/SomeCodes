package com.bb.module_novelmanager;

import android.util.Log;

import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_common.utils.log.LogUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 0:10
 */
public class NovelDBManagerTest {
    String TAG = "NovelDBManagerTest";

    private NovelDBManager mNovelDBManager;

    @Before
    public void setup() {
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
    }

    @Test
    public void getCategory() {
    }

    @Test
    public void saveCategory() {
    }

    @Test
    public void getAllLikedNovel() {
        List<NovelDetails> allLikedNovel = mNovelDBManager.getAllLikedNovel();
        for (NovelDetails details : allLikedNovel) {
            LogUtils.d(TAG, "getAllLikedNovel: " + details);
        }
    }

    @Test
    public void saveLikedNovel() {
        ArrayList<String> infos = new ArrayList<>();
            infos.add("作    者:烽火戏诸侯");
            infos.add("类    别:玄幻小说");
            infos.add("最后更新  :2021-01-18 23:59:43");
            infos.add("最    新:第八百五十二章 大概");
        NovelDetails novel = new NovelDetails("http://www.biquge.info/1_1245/", "http://www.biquge.info/files/article/image/1/1245/1245s.jpg", "剑来", infos, "大千世界，无奇不有。我陈平安，唯有一剑，可搬山，倒海，降妖，镇魔，敕神，摘星，断江，摧城，开天！\n" +
                "\n" +
                "本站提示：各位书友要是觉得《剑来》还不错的话请不要忘记向您QQ群和微博里的朋友推荐哦！");
        long l = mNovelDBManager.saveLikedNovel(novel);
        Log.d(TAG, "saveLikedNovel() called: " + l);
    }
}