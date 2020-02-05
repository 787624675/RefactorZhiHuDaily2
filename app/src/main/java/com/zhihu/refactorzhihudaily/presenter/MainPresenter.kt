package com.zhihu.refactorzhihudaily.presenter

import android.opengl.Matrix
import android.text.BoringLayout
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.model.News

interface MainPresenter {
    fun getTodayNews(recyclerView:RecyclerView,itemManager:ItemManager,screenHeight: Int)
    fun getBeforeNews(recyclerView:RecyclerView,itemManager:ItemManager,screenHeight: Int)
    fun loadMoreItems(recyclerView: RecyclerView, itemManager: ItemManager, screenHeight: Int)
    fun firstlyInitTecyclerView(recyclerView: RecyclerView,screenHeight:Int)
    fun convertDateToChinese(date:String):String
    fun<T> isSampleList(list: T):Boolean
    fun setListener(smartRefreshLayout: SmartRefreshLayout, recyclerView:RecyclerView, itemManager:ItemManager, screenHeight: Int)

}