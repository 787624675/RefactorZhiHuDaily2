package com.zhihu.refactorzhihudaily.presenter

import android.opengl.Matrix
import android.text.BoringLayout
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.News

interface MainPresenter {
    fun getTodayNews(recyclerView:RecyclerView, mAdapter: MultiItemAdapter, screenHeight: Int)
    fun getTheBeforeNews(recyclerView:RecyclerView, mAdapter: MultiItemAdapter, screenHeight: Int)
    fun convertDateToChinese(date:String):String
    fun<T> isSampleList(list: T):Boolean
    fun setListener(smartRefreshLayout: SmartRefreshLayout, recyclerView:RecyclerView, mAdapter: MultiItemAdapter, screenHeight: Int)

}