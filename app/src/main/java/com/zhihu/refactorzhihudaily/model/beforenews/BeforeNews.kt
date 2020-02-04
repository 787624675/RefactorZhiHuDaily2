package com.zhihu.refactorzhihudaily.model.beforenews

import com.zhihu.refactorzhihudaily.model.News
import java.util.ArrayList

data class BeforeNews(
    val date: String,
    val stories: List<Story>
){
    fun getNews(): MutableList<News> {

        val newsList:MutableList<News> = ArrayList()
        stories.forEach {
            newsList.add(News(it.title,it.hint,it.images[0],it.id,date))
        }
        return newsList
    }
}