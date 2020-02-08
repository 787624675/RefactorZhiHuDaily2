package com.zhihu.refactorzhihudaily.detailpage

import WebPageAdapter
import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zhihu.refactorzhihudaily.mainpage.MainPreImple
import com.zhihu.refactorzhihudaily.model.BeforeNews
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.model.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DetailImplementation : DetailPresenter {
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
    fun getTheBeforeNewsList( pageAdapter: WebPageAdapter,pageList:ArrayList<WebView>,context: Context){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                dataBean = RetrofitClient.reqApi.getBeforeNews(MainPreImple.beforeNewsList!!.get(0).date).await()
                MainPreImple.beforeNewsList = dataBean.getNews()
                if (!MainPreImple.isSampleList(MainPreImple.beforeNewsList)){
                    MainPreImple.remixList.add(
                        RemixItem(date =  MainPreImple.convertDateToChinese(
                            MainPreImple.beforeNewsList!!.get(0).date),type = 2)
                    )
                    MainPreImple.beforeNewsList!!.forEach {
                        MainPreImple.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3)
                        )
                        MainPreImple.idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    if (pageAdapter.webViewList!=null){
                        MainPreImple.beforeNewsList!!.forEach {
                            addView(
                                it.id,
                                pageList,
                                context
                            )
                        }
                        pageAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}