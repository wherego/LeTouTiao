package com.tfn.letoutiao.net;

import com.tfn.letoutiao.bean.GifBean;
import com.tfn.letoutiao.bean.JokeBean;
import com.tfn.letoutiao.bean.NewsDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tf on 2017/4/29.
 */

public interface QNewsService {

    public static final String DESC = "desc"; // 指定时间之前发布的
    public static final String ASC = "asc";   // 指定时间之后发布的


    /**
     * 根据 新闻类型 获取新闻数据
     *
     * @param type  新闻的类型
     * @return      查询结束 返回 数据的 被观察者
     */

    @GET("toutiao/index?key=9a5b8f45ff01e93639d7deac989f040f")
    Observable<NewsDataBean> getNewsData(
            @Query("type") String type);

    /**
     *
     * @param page
     * @param pagesize
     * @return
     */
    @POST("text.from?key=6798dff6847e6aa63facebdf107ea565")
    Observable<JokeBean> getCurrentJokeData(
            @Query("page") int page,
            @Query("pagesize") int pagesize
    );



    /**
     *获取笑话列表
     * @param time
     * @param page
     * @param pagesize
     * @param sort
     * @return
     */
    @GET("list.from?key=6798dff6847e6aa63facebdf107ea565")
    Observable<JokeBean> getAssignJokeData(
            @Query("time") long time,
            @Query("pager") int page,
            @Query("pagesize") int pagesize,
            @Query("sort") String sort
    );

    @GET("joke/randJoke.php?key=ae240f7fba620fc370b803566654949e&type=pic")
    Observable<GifBean> getGifData();

}
