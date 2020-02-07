package com.zhihu.refactorzhihudaily.presenter

import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewpager.widget.ViewPager

interface DetailPresenter {
    fun addView(newsId : Int, pageList : ArrayList<WebView>,context: Context,position : Int = pageList.size)
}