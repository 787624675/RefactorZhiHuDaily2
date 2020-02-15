package com.zhihu.refactorzhihudaily.network

import WebPageAdapter
import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.*
import com.zhihu.refactorzhihudaily.network.RetrofitClient.getTodayNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.coroutines.experimental.bg
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.suspendCoroutine

interface MyCallBack<T>{
    fun onSuccess(value:T)
    fun onError(t:Throwable)

}
enum class ErrorType {
    NETWORK_ERROR,//网络出错
    SERVICE_ERROR,//服务器访问异常
    RESPONSE_ERROR//请求返回值异常
}

/**
 * 网络请求出错的响应
 */
data class ErrorResponse(
    val errorType:ErrorType,//错误类型
    val errorTag:String,//错误tag,用于区别哪个请求出错
    val errorCode: String?,//错误代码
    val message: String?//错误信息
)


object RetrofitClient {



    val BASE_URL =  "https://news-at.zhihu.com/"

    val reqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return@lazy retrofit.create(NewsService::class.java)
    }

//detailActivity翻到最后一页时发送网络请求
    fun getTheBeforeNewsList(pageAdapter: WebPageAdapter, pageList:ArrayList<WebView>, context: Context){

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                dataBean = reqApi.getBeforeNews(
                    ModMainDetail.beforeNewsList!!.get(0).date).await()
                ModMainDetail.beforeNewsList = dataBean.getNews()
                if (!ModMainDetail.isSampleList(ModMainDetail.beforeNewsList)){
                    ModMainDetail.remixList.add(
                        RemixItem(
                            date = convertDateToChinese(
                                ModMainDetail.beforeNewsList!!.get(
                                    0
                                ).date
                            ), type = 2
                        )
                    )
                    ModMainDetail.beforeNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    if (pageAdapter.webViewList!=null){
                        ModMainDetail.beforeNewsList!!.forEach {
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
    fun addView(newsId: Int, pageList: ArrayList<WebView>, context: Context) {
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

    //获取今日新闻
    @JvmOverloads
    fun getTodayNews(mAdapter: MultiItemAdapter, screenHeight: Int){
        GlobalScope.launch (Dispatchers.Main){
            launch(Dispatchers.IO){
                ModMainDetail.remixList.clear()
                ModMainDetail.idList.clear()
                val dataBean = reqApi.getTodayNews().await()
                ModMainDetail.topImages = dataBean.getTopImages()
                ModMainDetail.topNewsList = dataBean.getTopNews()
                ModMainDetail.todayNewsList = dataBean.getNews()
                val dataBeanBefore = reqApi.getBeforeNews(
                    ModMainDetail.todayNewsList!!.get(0).date).await()
                ModMainDetail.beforeNewsList = dataBeanBefore.getNews()
                if (!ModMainDetail.isSampleList(ModMainDetail.topNewsList)){
                    ModMainDetail.remixList.add(0,
                        RemixItem(
                            list = ModMainDetail.topNewsList,
                            screenHeight = screenHeight,
                            type = 1
                        )
                    )

                }
                if (!ModMainDetail.isSampleList(ModMainDetail.todayNewsList)){
                    ModMainDetail.todayNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }

                }
                if (!ModMainDetail.isSampleList(ModMainDetail.beforeNewsList)){
                    ModMainDetail.remixList.add(
                        RemixItem(
                            date = convertDateToChinese(
                                ModMainDetail.beforeNewsList!!.get(
                                    0
                                ).date
                            ),
                            type = 2
                        )
                    )
                    ModMainDetail.beforeNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    //获取前些日子的新闻
    @JvmOverloads
    fun getTheBeforeNews(mAdapter: MultiItemAdapter, screenHeight: Int){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                dataBean = reqApi.getBeforeNews(
                    ModMainDetail.beforeNewsList!!.get(0).date).await()
                ModMainDetail.beforeNewsList = dataBean.getNews()
                if (!ModMainDetail.isSampleList(ModMainDetail.beforeNewsList)){
                    ModMainDetail.remixList.add(
                        RemixItem(
                            date = convertDateToChinese(
                                ModMainDetail.beforeNewsList!!.get(
                                    0
                                ).date
                            ), type = 2
                        )
                    )
                    ModMainDetail.beforeNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    if (mAdapter.mContext!=null){
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    //把日期转化为中文
    fun convertDateToChinese(date:String): String {
        val dates = date.toInt()
        val day = dates%100
        val month =  ((dates%10000)-day)/100
        return " $month 月 $day 日 "
    }
}

