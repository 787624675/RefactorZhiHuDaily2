package com.zhihu.refactorzhihudaily.model

import android.widget.TextView
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zhihu.refactorzhihudaily.model.Model.beforeNewsList
import com.zhihu.refactorzhihudaily.model.Model.newsList
import com.zhihu.refactorzhihudaily.model.Model.newsRCVlist
import com.zhihu.refactorzhihudaily.model.Model.rCVadapter
import com.zhihu.refactorzhihudaily.model.Model.topImages
import com.zhihu.refactorzhihudaily.model.beforenews.BeforeNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



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
    fun getTodayNews(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                val dataBean = reqApi.getTodayNews().await()
                newsList = dataBean.getNews()
                topImages = dataBean.getTopImages()
                newsRCVlist.clear()
                newsRCVlist.addAll(newsRCVlist)
                rCVadapter.imageList = topImages
                withContext(Dispatchers.Main){
                    rCVadapter.notifyDataSetChanged()
                }

            }

            //更新ui

        }
    }
    fun getBeforeNews(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                if (beforeNewsList!=null){
                    newsRCVlist.add(News("","","",0, beforeNewsList!!.get(0).date))
                    dataBean = reqApi.getBeforeNews(beforeNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                    newsRCVlist.addAll(beforeNewsList!!)

                    rCVadapter.notifyDataSetChanged()
                }
                if (beforeNewsList == null && newsList != null) {
                    newsRCVlist.add(News("","","",0, newsList!!.get(0).date))
                    dataBean = reqApi.getBeforeNews(newsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                    newsRCVlist.addAll(beforeNewsList!!)
                    rCVadapter.notifyDataSetChanged()
                }



            }

            //更新ui

        }
    }



}

