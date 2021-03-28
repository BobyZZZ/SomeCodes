package com.bb.module_novelmanager.entity;

import android.text.TextUtils;


import com.bb.module_novelmanager.adapter.ExpandListAdapter;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/4
 * Time: 22:54
 */
public class RankPageDataFruitBean {
    @Pick("div#main > div#main > div")
    public List<TypeRank> typeRanks;

    public RankPageData filter() {
        return new RankPageData(this);
    }

    /**
     * 小说类型推荐排行榜：
     * 玄幻、修真、都市、历史、网游、科幻、全本小说、全部小说
     */
    public static class TypeRank implements ExpandListAdapter.GroupData {
        @Pick("div > h3")
        public String title;
        @Pick("div > ul")
        public List<TimeTypeRank> timeTypeRanks;

        @Override
        public String getType() {
            return title;
        }

        @Override
        public int getChildCount(int period) {
            return timeTypeRanks == null ? 0 : timeTypeRanks.get(period).items.size();
        }

        @Override
        public TimeTypeRank.Item getChild(int period, int position) {
            return timeTypeRanks == null ? null : timeTypeRanks.get(period).items.get(position);
        }

        @Override
        public String toString() {
            return "TypeRank{" +
                    "title='" + title + '\'' +
                    '}';
        }

        /**
         * 总榜、周榜、月榜、日榜
         */
        public static class TimeTypeRank {
            public String period;
            @Pick("ul > li")
            public List<Item> items;

            public static class Item implements ExpandListAdapter.ChildData {
                @Pick(value = "li > span", attr = Attrs.OWN_TEXT)
                private String period;//总、年、月、日
                @Pick(value = "li > a", attr = Attrs.OWN_TEXT)
                public String novelName;
                @Pick(value = "li > a", attr = Attrs.HREF)
                public String novelId;

                @Override
                public String getName() {
                    return novelName;
                }

                @Override
                public String toString() {
                    return "Item{" +
                            "period='" + period + '\'' +
                            ", novelName='" + novelName + '\'' +
                            ", novelId='" + novelId + '\'' +
                            '}';
                }
            }
        }
    }

    public class RankPageData extends RankPageDataFruitBean {
        public RankPageData(RankPageDataFruitBean fruitBean) {
            List<TypeRank> originTypeRanks = fruitBean.typeRanks;

            Iterator<TypeRank> typeRankIterator = originTypeRanks.iterator();
            while (typeRankIterator.hasNext()) {
                TypeRank originTypeRank = typeRankIterator.next();

                if (originTypeRank.timeTypeRanks == null || originTypeRank.timeTypeRanks.isEmpty()) {
                    //过滤无用数据，这里过滤顶部分类（小说推荐榜、小说收藏榜、小说新手榜）
                    typeRankIterator.remove();
                    continue;
                }

                ListIterator<TypeRank.TimeTypeRank.Item> itemListIterator;
                for (TypeRank.TimeTypeRank timeTypeRank : originTypeRank.timeTypeRanks) {
                    itemListIterator = timeTypeRank.items.listIterator();
                    while (itemListIterator.hasNext()) {
                        TypeRank.TimeTypeRank.Item next = itemListIterator.next();
                        if (!TextUtils.isEmpty(next.period)) {
                            timeTypeRank.period = next.period;
                            itemListIterator.remove();
                        } else if (TextUtils.isEmpty(next.novelId)) {
                            itemListIterator.remove();
                        }
                    }
                }
            }

            typeRanks = originTypeRanks;
        }

        @Override
        public String toString() {
            return "RankPageData{" +
                    "typeRanks=" + typeRanks +
                    '}';
        }
    }
}
