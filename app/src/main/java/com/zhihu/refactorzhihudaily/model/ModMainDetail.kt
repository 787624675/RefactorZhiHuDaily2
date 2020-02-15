package com.zhihu.refactorzhihudaily.model


object ModMainDetail{
    //由于main activity 和 detail activity的数据是相同的而且需要同步更新，所以用这个单例来装载。。。
    val chineseMonthMap : Map<Int,String> = mapOf(1 to "一月",2 to "二月",3 to "三月",4 to "四月",5 to "五月",6 to "六月",7 to "七月",8 to "八月",9 to "九月",10 to "一月",11 to "十一月",12 to "十二月")
    val sampleImageUrl :String = "https://pic3.zhimg.com/v2-c6fc8f2f830aa0b2e448697c5f92b286.jpg"
    val sampleNews : News =
        News(
            "?????",
            "?????",
            "https://pic4.zhimg.com/v2-89b192a671f7a6fe9c49b8233d84028f.jpg",
            0,
            "20200203"
        )
    val sampleNewsList:MutableList<News> = mutableListOf(
        sampleNews,
        sampleNews,
        sampleNews
    )
    val sampleImages : MutableList<String> = mutableListOf(
        sampleImageUrl,
        sampleImageUrl,
        sampleImageUrl,
        sampleImageUrl,
        sampleImageUrl
    )
    var todayNewsList : List<News>? = sampleNewsList
    var beforeNewsList : MutableList<News>? = sampleNewsList
    var topImages : MutableList<String> = sampleImages
    var topNewsList : MutableList<News> = sampleNewsList
    var remixList : MutableList<RemixItem> = ArrayList()
    var idList = ArrayList<Int>()
    var screenHeight  = 800
    var screenWidth = 500
    var dayOrNight = "Day"
   // lateinit var mainContext:Context  //全部黄掉了，提示会导致内存泄漏，幻想破灭



    //判断数据是否是样例数据
    fun<T>  isSampleList(list: T): Boolean {
        if (list!!.equals(ModMainDetail.sampleImages)||list.equals(ModMainDetail.sampleNewsList)){
            return true
        }else{
            return false
        }
    }

}