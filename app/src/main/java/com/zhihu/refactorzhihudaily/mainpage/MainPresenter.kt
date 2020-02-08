package com.zhihu.refactorzhihudaily.mainpage

import WebPageAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter

interface MainPresenter {
    fun getTodayNews(recyclerView:RecyclerView, mAdapter: MultiItemAdapter, screenHeight: Int)
    fun getTheBeforeNews(mAdapter: MultiItemAdapter = MultiItemAdapter(null,null), screenHeight: Int = 800, pageAdapter: WebPageAdapter = WebPageAdapter(null))
    fun convertDateToChinese(date:String):String
    fun<T> isSampleList(list: T):Boolean
    fun setListener(smartRefreshLayout: SmartRefreshLayout, recyclerView:RecyclerView, mAdapter: MultiItemAdapter, screenHeight: Int)

}