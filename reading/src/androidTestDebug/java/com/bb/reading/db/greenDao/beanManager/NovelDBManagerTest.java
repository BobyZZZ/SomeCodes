package com.bb.reading.db.greenDao.beanManager;

import android.util.Log;

import com.bb.reading.db.DaoHelper;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.utils.log.LogUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
        for (int i = 0; i < 4; i++) {
            infos.add("info" + i);
        }
        NovelDetails novel = new NovelDetails("boby", "bobo", "bobo", infos, "introduction");
        long l = mNovelDBManager.saveLikedNovel(novel);
        Log.d(TAG, "saveLikedNovel() called: " + l);
    }
}