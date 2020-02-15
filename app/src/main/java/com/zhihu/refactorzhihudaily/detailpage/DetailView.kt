package com.zhihu.refactorzhihudaily.detailpage

import WebPageAdapter
import android.content.Context
import android.webkit.WebView
import androidx.viewpager.widget.ViewPager

interface DetailView  {

    fun addView(newsId : Int, pageList : ArrayList<WebView>, context: Context)
    fun showView(page:ViewPager,adapter: WebPageAdapter,index:Int)
}