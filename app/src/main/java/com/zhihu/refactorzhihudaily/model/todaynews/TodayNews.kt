package com.zhihu.refactorzhihudaily.model.todaynews

import com.zhihu.refactorzhihudaily.model.News
import java.util.ArrayList

data class TodayNews(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
){
    fun getNews(): MutableList<News> {
        
        val newsList:MutableList<News> = ArrayList()
        stories.forEach {
            newsList.add(News(it.title,it.hint,it.images[0],it.id,date))
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
}