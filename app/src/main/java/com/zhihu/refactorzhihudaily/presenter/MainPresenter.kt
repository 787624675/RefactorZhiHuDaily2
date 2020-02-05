package com.zhihu.refactorzhihudaily.presenter

import com.zhihu.refactorzhihudaily.model.News

interface MainPresenter {
    fun<T> getData(realData:T,sampleData:T):T
    fun getTodayNews()
    fun getBeforeNews()
    fun initRecyclerView()
    fun firstlyInitTecyclerView()
    fun convertDateToChinese(date:String):String
    fun<T> isSampleList(list: T):Boolean
}