package com.zhihu.refactorzhihudaily.model

import com.zhihu.refactorzhihudaily.model.todaynews.TodayNews
import com.zhihu.refactorzhihudaily.model.beforenews.BeforeNews
import com.zhihu.refactorzhihudaily.model.detailednews.DetailedNews
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("api/3/news/latest")
    fun getTodayNews():Deferred<TodayNews>

    @GET("api/3/news/before/{date}")
    fun getBeforeNews(@Path("date")date:String):Deferred<BeforeNews>

    @GET("api/3/news/{id}")
    fun getDetailedNews(@Path("id")id:Int):Deferred<DetailedNews>
}