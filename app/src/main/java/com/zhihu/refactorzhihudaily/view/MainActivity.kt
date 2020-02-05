package com.zhihu.refactorzhihudaily.view

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemAdapter
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.BeforeNews
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.NewsService
import com.zhihu.refactorzhihudaily.model.RetrofitClient
import com.zhihu.refactorzhihudaily.presenter.MainPresenter
import com.zhihu.refactorzhihudaily.view.recyclerviewdsl.*
import kotlinx.coroutines.*
import org.jetbrains.anko.find
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , MainPresenter {

    companion object Values{
        //默认的数据
        val chineseMonthMap : Map<Int,String> = mapOf(1 to "一月",2 to "二月",3 to "三月",4 to "四月",5 to "五月",6 to "六月",7 to "七月",8 to "八月",9 to "九月",10 to "一月",11 to "十一月",12 to "十二月")
        val sampleImageUrl :String = "https://pic3.zhimg.com/v2-c6fc8f2f830aa0b2e448697c5f92b286.jpg"
        val sampleNews : News = News("?????","?????","https://pic4.zhimg.com/v2-89b192a671f7a6fe9c49b8233d84028f.jpg",0,"20200203")
        val sampleNewsList:MutableList<News> = mutableListOf(sampleNews, sampleNews, sampleNews)
        val sampleImages : MutableList<String> = mutableListOf<String>(sampleImageUrl, sampleImageUrl,sampleImageUrl,sampleImageUrl,sampleImageUrl)
    }

    var screenHeight:Int = 0
    var todayNewsList : List<News>? = sampleNewsList
    var beforeNewsList : MutableList<News>? = sampleNewsList
    var topImages : MutableList<String>? = sampleImages
    lateinit var itemManager: ItemManager
    var recyclerView : RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //找控件
        val day = findViewById<TextView>(R.id.day)
        val month = findViewById<TextView>(R.id.month)
        val toolbar :androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_top)
        val smartRefreshLayout = find<SmartRefreshLayout>(R.id.refresh_layout)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //发送网络请求
        getTodayNews()
        //获取屏幕高度
        val windowManager : WindowManager = this.windowManager
        var outMartics  = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMartics)
        screenHeight = outMartics.heightPixels
        //获取系统日期并设置
        val date = Date()
        day.setText(date.date.toString())
        month.setText(chineseMonthMap[date.month])
        //根据屏幕高度设置toolbar的height
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)
        //设置刷新
        smartRefreshLayout.setOnRefreshListener {
            RefreshLayout -> run{
            getTodayNews()
            smartRefreshLayout.finishRefresh(2000)
        }
        }
        smartRefreshLayout.setOnLoadMoreListener {
            RefreshLayout -> run {
            getBeforeNews()
            smartRefreshLayout.finishLoadMore(2000)
        }

        }
        //recyclerview显示

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        itemManager = ItemManager()
        recyclerView!!.adapter = ItemAdapter(itemManager)
        itemManager.autoRefresh {
            initRecyclerView()
        }
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }
    override fun <T> getData(realData: T, sampleData: T): T {
        if(realData==null){
            return sampleData!!
        }else{
            return realData!!
        }
    }
    override fun getTodayNews(){
        GlobalScope.launch(Dispatchers.IO){
            val dataBean = RetrofitClient.reqApi.getTodayNews().await()
            todayNewsList = dataBean.getNews()
            topImages = dataBean.getTopImages()
            launch (Dispatchers.Main){
                firstlyInitTecyclerView()
            }
        }
    }
    override fun getBeforeNews(){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                if (!isSampleList(beforeNewsList)){
                    dataBean = RetrofitClient.reqApi.getBeforeNews(beforeNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                    initRecyclerView()
                }
                if (isSampleList(beforeNewsList) &&( !isSampleList(todayNewsList))) {
                    dataBean = RetrofitClient.reqApi.getBeforeNews(todayNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                    initRecyclerView()
                }
            }
        }
    }
    override fun initRecyclerView() {
        recyclerView!!.withItems {
            //替换banner
            if (isSampleList(topImages)){
                itemManager.removeAll(mutableListOf(BannerItem(sampleImages,screenHeight)))
                itemManager.add(0,BannerItem(topImages,screenHeight))
            }
            //替换news

            if (!isSampleList(todayNewsList)) {
                itemManager.removeAll(mutableListOf(NewsItem(sampleNews)))
                todayNewsList!!.forEach {
                    itemManager.singleNews(it)
                }
            }
            if (!isSampleList(todayNewsList)&&!isSampleList(beforeNewsList)){
                itemManager.singleDate(convertDateToChinese(beforeNewsList!!.get(0).date))
                beforeNewsList!!.forEach {
                    itemManager.singleNews(it)
                }
            }
        }
    }
    override fun firstlyInitTecyclerView(){
        recyclerView!!.withItems {
            singleBanner(getData(topImages,sampleImages),screenHeight)
            todayNewsList?.forEach{singleNews(it)}
        }
    }
    override fun convertDateToChinese(date:String): String {
        val dates = date.toInt()
        val day = dates%100
        val month =  ((dates%10000)-dates)/100
        return " $month 月 $day 日 "
    }

    override fun<T>  isSampleList(list: T): Boolean {
        if (list!!.equals(sampleImages)||list.equals(sampleNewsList)){
            return true
        }else{
            return false
        }
    }
}
