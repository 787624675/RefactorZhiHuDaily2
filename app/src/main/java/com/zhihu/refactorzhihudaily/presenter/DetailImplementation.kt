package com.zhihu.refactorzhihudaily.presenter

import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

object DetailImplementation : DetailPresenter{
    @JvmOverloads
    override fun addView(newsId : Int, pageList : ArrayList<WebView>, context: Context, position : Int){
        val webView  = WebView(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //为了让图片全部加载出来
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.getSettings().setDomStorageEnabled(true)
        webView.settings.blockNetworkImage = false
        webView.loadUrl("https://daily.zhihu.com/story/"+newsId)
        pageList.add(webView)
    }
}