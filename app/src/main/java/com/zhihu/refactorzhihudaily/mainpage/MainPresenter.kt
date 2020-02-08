package com.zhihu.refactorzhihudaily.mainpage

import WebPageAdapter
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter

interface MainPresenter {
    //为Presenter建立了接口，主要是为了方便查找
    fun getTodayNews( mAdapter: MultiItemAdapter, screenHeight: Int)
    fun getTheBeforeNews(mAdapter: MultiItemAdapter = MultiItemAdapter(null,null), screenHeight: Int = 800, pageAdapter: WebPageAdapter = WebPageAdapter(null))
    fun convertDateToChinese(date:String):String
    fun<T> isSampleList(list: T):Boolean
    fun setListener(smartRefreshLayout: SmartRefreshLayout, mAdapter: MultiItemAdapter, screenHeight: Int)
}