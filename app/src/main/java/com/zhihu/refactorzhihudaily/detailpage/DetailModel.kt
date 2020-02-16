package com.zhihu.refactorzhihudaily.detailpage

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.ModMainDetail
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.network.BeforeNews
import com.zhihu.refactorzhihudaily.network.DetailedNews
import com.zhihu.refactorzhihudaily.network.HtmlUtil
import com.zhihu.refactorzhihudaily.network.RetrofitClient
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import java.lang.Exception

class DetailModel constructor(val context: Context){
//有2个model，令一个是ModelMain，
//由于webview的夜间模式没做成，所以这个model并未起作用。。。quq

    lateinit var  cssUrls : List<String>
    lateinit var jsUrls : List<String>
    lateinit var css : String
    lateinit var html : String
    lateinit var artical:String
    lateinit var js : String
    lateinit var htmlData : String
    lateinit var shareUrl:String
    val mHtmlUtil = HtmlUtil()
    //不能用
    fun  addNightView( pageList: ArrayList<WebView>, context: Context) {
        val webView  = WebView(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //为了让图片全部加载出来
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.getSettings().setDomStorageEnabled(true)
        webView.settings.blockNetworkImage = false
        webView.loadData(htmlData,mHtmlUtil.MIME_TYPE,  mHtmlUtil.ENCODING)
        if(html!=null){
            html = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "<head>\n" +
                    "\t<meta charset=\"utf-8\" />\n</head>\n" +
                    "<body>\n" + css +
                    artical.replace("<div class=\"img-place-holder\">", "") + "\n<body>";
            webView.loadDataWithBaseURL("x-data://base", html, "text/html", "utf-8", null);
        }else {
            webView.loadUrl(shareUrl);
        }
        pageList.add(webView)
    }
    //不能用
    fun getTheNightNews(id: Int,pageList: ArrayList<WebView>){
        GlobalScope.launch (Dispatchers.IO){
                var dataBean : DetailedNews
                dataBean = RetrofitClient.reqApi.getDetailedNews(id).await()
                html = dataBean.body
                artical = dataBean.body
                cssUrls = dataBean.css
                jsUrls = dataBean.js
                htmlData = mHtmlUtil.createNightHtmlData(html,cssUrls,jsUrls)
                shareUrl = dataBean.share_url;
            //有时候返回的没有body,我们得用ShareUrl来代替
            //根据是否为夜间模式来添加css样式

//            if(ModMainDetail.dayOrNight == "day"){
//                css = "<link type=\"text/css\" href=\"" +
//                        "file:///android_asset/zhihu_dark.css" +
//                        "\" " +
//                        "rel=\"stylesheet\" />\n";
//            }else {
                css = "<link type=\"text/css\" href=\"" +
                        "file:///android_asset/zhihu.css" +
                        "\" " +
                        "rel=\"stylesheet\" />\n";
//            }

            launch (Dispatchers.Main){

                addNightView(pageList,context)
            }
        }
    }



}