package com.zhihu.refactorzhihudaily.detailpage

import WebPageAdapter
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.viewpager.widget.ViewPager
import com.zhihu.refactorzhihudaily.R

import org.jetbrains.anko.find

class DetailActivity : AppCompatActivity() ,DetailView{
    override fun addView(newsId: Int, pageList: ArrayList<WebView>, context: Context) {
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

    override fun showView(page: ViewPager,adapter: WebPageAdapter,index:Int) {
        page.adapter = adapter
        page.setCurrentItem(index)
    }


    lateinit var page : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        page = find<ViewPager>(R.id.page)
        var type = intent.getIntExtra("type",3)       //拿到被点击的item的种类，1表示banner被点击，3表示recyclerview被点击
        var id = intent.getIntExtra("newsId",0)        //拿到被点击新闻的id


        val detailPresenter = DetailPresenter(this,this)
        detailPresenter.initView(type,id,page,this)


    }



}
