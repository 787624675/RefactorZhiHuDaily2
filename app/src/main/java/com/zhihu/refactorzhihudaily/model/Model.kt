package com.zhihu.refactorzhihudaily.model

import android.icu.util.ChineseCalendar
import com.zhihu.refactorzhihudaily.adapters.RecyclerViewAdapter
import com.zhihu.refactorzhihudaily.model.todaynews.Story
import com.zhihu.refactorzhihudaily.model.todaynews.TodayNews
import com.zhihu.refactorzhihudaily.model.todaynews.TopStory

object Model {
    //默认的图片
    val sampleImageUrl :String = "https://pic3.zhimg.com/v2-c6fc8f2f830aa0b2e448697c5f92b286.jpg"
    val sampleImages : MutableList<String> = mutableListOf<String>(sampleImageUrl,
        sampleImageUrl,sampleImageUrl,sampleImageUrl,sampleImageUrl)

    var newsList : List<News>? = null
    val sampleNews :News = News("?????","?????","https://pic4.zhimg.com/v2-89b192a671f7a6fe9c49b8233d84028f.jpg",0,"20200203")
    val sampleNewsList:MutableList<News> = mutableListOf(sampleNews, sampleNews, sampleNews)

    val chineseMonthMap : Map<Int,String> = mapOf(1 to "一月",2 to "二月",3 to "三月",4 to "四月",5 to "五月",6 to "六月",7 to "七月",8 to "八月",9 to "九月",10 to "一月",11 to "十一月",12 to "十二月")

    var beforeNewsList : MutableList<News>? = null
    var topImages : MutableList<String>? = null
    var newsRCVlist = ArrayList<News>()
    var rCVadapter = RecyclerViewAdapter()
}