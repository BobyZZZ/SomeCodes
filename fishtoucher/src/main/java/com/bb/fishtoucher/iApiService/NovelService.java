package com.bb.fishtoucher.iApiService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NovelService {
    public static String NORVEL_BASE_URL = "http://www.biquge.info/";

    public static String DIYI_XULIE_NOVEL_INDEX = "12_12696";//http://www.biquge.info/12_12696/5621986.html
    public static long DIYI_XULIE_FIRST_CHAPTER_INDEX = 5621986;//http://www.biquge.info/12_12696/5621986.html

    public static String SIYANG_HUMAN_NOVEL_INDEX = "69_69120";//http://www.biquge.info/69_69120/12898729.html
    public static long SIYANG_HUMAN_FIRST_CHAPTER_INDEX = 12898729;//http://www.biquge.info/69_69120/12898729.html

    public static String JIAN_LAI_NOVEL_INDEX = "1_1245";//http://www.biquge.info/69_69120/12898729.html
    /**
     * 放开那个魔女
     */
    public static String FKNGMN_NOVEL_INDEX = "24_24159";

    @GET("{novel_index}/{chapter_href}")
    Observable<ResponseBody> getChapter(@Path("novel_index")String novel_index, @Path("chapter_href")String chapter_href);

    @GET("{novel_index}/")
    Observable<ResponseBody> getCategory(@Path("novel_index")String novelIndex);
}
