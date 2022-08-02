package com.bb.module_novelmanager.network;

import com.bb.module_novelmanager.entity.HomePageBean;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_novelmanager.entity.PageData;
import com.bb.module_novelmanager.entity.RankPageDataFruitBean;
import com.bb.module_novelmanager.entity.SearchResult;

import io.reactivex.Observable;
import me.ghui.retrofit.converter.annotations.Html;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NovelService {
//    public static String NORVEL_BASE_URL = "http://www.paoshuzw.com/";
    public static String NORVEL_BASE_URL = "https://www.xbiquwx.la/";//v2
//    public static String NORVEL_BASE_URL = "http://www.biquge.info/";//v1
//    public static String NORVEL_BASE_URL = "http://m.biquge.info/";

    public static String DIYI_XULIE_NOVEL_INDEX = "12_12696";//http://www.biquge.info/12_12696/5621986.html
    public static long DIYI_XULIE_FIRST_CHAPTER_INDEX = 5621986;//http://www.biquge.info/12_12696/5621986.html

    public static String SIYANG_HUMAN_NOVEL_INDEX = "69_69120";//http://www.biquge.info/69_69120/12898729.html
    public static long SIYANG_HUMAN_FIRST_CHAPTER_INDEX = 12898729;//http://www.biquge.info/69_69120/12898729.html

    public static String JIAN_LAI_NOVEL_INDEX = "1_1245";//http://www.biquge.info/69_69120/12898729.html
    /**
     * 放开那个魔女
     */
    public static String FKNGMN_NOVEL_INDEX = "24_24159";

//    @GET("{novel_index}/{chapter_href}")
    @GET("/{chapter_href}")
    @Headers("needFilter:true")
    Observable<ResponseBody> getChapter(/*@Path("novel_index") String novel_index,*/ @Path("chapter_href") String chapter_href);

    @Html
    @GET
    Observable<NovelChapterContentFruitBean> getNovelChapterDetails(/*@Path("novel_index") String novel_index,*/ @Url String fullUrl);

    @GET
    Observable<ResponseBody> getCategory(@Url String fullUrl);

    /**
     * 根据分类获取小说
     * @param type
     * @param page
     * @return
     */
    @GET("/list/{type}_{page}.html")
    @Html
    Observable<PageData> getNovelBySort(@Path("type") int type, @Path("page") int page);

    /**
     * 获取主页数据
     * @return
     */
    @Html
    @GET("/")
    Observable<HomePageBean> getHomeData();

    /**
     * 小说详情页面
     * @return
     */
    @Html
    @GET
    Observable<NovelDetails> getNovelDetails(@Url String fullUrl);

    /**
     * 获取主页数据
     * @return
     */
    @GET("/")
    Observable<ResponseBody> getHomeDataTest();

    @GET("modules/article/search.php")
    @Html
    Observable<SearchResult> searchNovel(@Query("searchkey")String searchKey);

    @GET("/paihangbang/")
    Observable<RankPageDataFruitBean> getRankData();


    public class NovelType {
        /**
         * 玄幻
         */
        public static final int TYPE_XUANHUAN = 1;
        /**
         * 修真
         */
        public static final int TYPE_XIUZHEN = 2;
        /**
         * 都市
         */
        public static final int TYPE_DUSHI = 3;
        /**
         * 穿越
         */
        public static final int TYPE_CHUANYUE = 4;
        /**
         * 网游
         */
        public static final int TYPE_WANGYOU = 5;
        /**
         * 科幻
         */
        public static final int TYPE_KEHUAN = 6;
    }
}
