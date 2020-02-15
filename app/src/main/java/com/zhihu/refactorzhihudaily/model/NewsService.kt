package com.zhihu.refactorzhihudaily.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.ArrayList

interface NewsService {
    @GET("api/3/news/latest")
    fun getTodayNews():Deferred<TodayNews>

    @GET("api/3/news/before/{date}")
    fun getBeforeNews(@Path("date")date:String):Deferred<BeforeNews>

    @GET("api/3/news/{id}")
    fun getDetailedNews(@Path("id")id:Int):Deferred<DetailedNews>


}
data class TodayNews(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
){
    fun getNews(): MutableList<News> {

        val newsList:MutableList<News> = ArrayList()
        stories.forEach {
            newsList.add(
                News(
                    it.title,
                    it.hint,
                    it.images[0],
                    it.id,
                    date
                )
            )
        }
        return newsList
    }
    fun getTopImages():MutableList<String>{
        val topImages : MutableList<kotlin.String> = ArrayList()
        top_stories.forEach {
            topImages.add(it.image)
        }
        return topImages
    }

    fun getTopNews(): MutableList<News> {

        val newsList:MutableList<News> = ArrayList()
        top_stories.forEach {
            newsList.add(
                News(
                    it.title,
                    it.hint,
                    it.image,
                    it.id,
                    it.image_hue
                )
            )
        }
        return newsList
    }


    data class TopStory(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image: String,
        val image_hue: String,
        val title: String,
        val type: Int,
        val url: String
    )
    data class Story(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image_hue: String,
        val images: List<String>,
        val title: String,
        val type: Int,
        val url: String
    )
}
data class BeforeNews(
    val date: String,
    val stories: List<Story>
){
    fun getNews(): MutableList<News> {

        val newsList:MutableList<News> = ArrayList()
        stories.forEach {
            newsList.add(
                News(
                    it.title,
                    it.hint,
                    it.images[0],
                    it.id,
                    date
                )
            )
        }
        return newsList
    }

    data class Story(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image_hue: String,
        val images: List<String>,
        val title: String,
        val type: Int,
        val url: String
    )
}
data class DetailedNews(
    val body: String,
    val css: List<String>,
    val ga_prefix: String,
    val id: Int,
    val image: String,
    val image_hue: String,
    val image_source: String,
    val images: List<String>,
    val js: List<String>,
    val share_url: String,
    val title: String,
    val type: Int,
    val url: String
)
data class News(val title:String,val hint:String,val imageUrl:String,val id:Int,val date:String) {
}


