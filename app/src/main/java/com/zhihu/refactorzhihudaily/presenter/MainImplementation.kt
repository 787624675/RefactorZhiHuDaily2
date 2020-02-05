package com.zhihu.refactorzhihudaily.presenter

import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.model.BeforeNews
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.RetrofitClient
import com.zhihu.refactorzhihudaily.view.MainActivity
import com.zhihu.refactorzhihudaily.view.MainActivity.Values.sampleNewsList
import com.zhihu.refactorzhihudaily.view.recyclerviewdsl.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object MainImplementation:MainPresenter {
    //recyclerView显示所需的一些变量
    var todayNewsList : List<News>? = MainActivity.sampleNewsList
    var beforeNewsList : MutableList<News>? = MainActivity.sampleNewsList
    var topImages : MutableList<String>? = MainActivity.sampleImages
    var totalNewsList : MutableList<News> = sampleNewsList
    val itemList = ArrayList<Item>()
    //获取今日新闻
    override fun getTodayNews(recyclerView:RecyclerView,itemManager:ItemManager,screenHeight: Int){
        GlobalScope.launch (Dispatchers.Main){
            launch(Dispatchers.IO){
                val dataBean = RetrofitClient.reqApi.getTodayNews().await()
                val dataBeanBefore = RetrofitClient.reqApi.getBeforeNews(todayNewsList!!.get(0).date).await()
                beforeNewsList = dataBeanBefore.getNews()
                todayNewsList = dataBean.getNews()
                topImages = dataBean.getTopImages()
                launch (Dispatchers.Main){
                    firstlyInitTecyclerView(recyclerView,itemManager,screenHeight)
                }
            }
        }
    }
    //获取前些日子的新闻
    override fun getTheBeforeNews(recyclerView:RecyclerView, itemManager:ItemManager, screenHeight: Int){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                    dataBean = RetrofitClient.reqApi.getBeforeNews(beforeNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                    launch (Dispatchers.Main){
                        loadMoreItems(recyclerView,itemManager,screenHeight)
                    }
            }
        }
    }
    //加载更多新闻
    override fun loadMoreItems(recyclerView:RecyclerView, itemManager:ItemManager, screenHeight: Int) {
        recyclerView.withItems{
                add(DateItem(convertDateToChinese(beforeNewsList!!.get(0).date)))
                beforeNewsList!!.forEach {
                    add(NewsItem(it))
                }

        }
    }
    //第一次加载新闻
    override fun firstlyInitTecyclerView(recyclerView: RecyclerView,itemManager: ItemManager,screenHeight:Int){
        recyclerView!!.withItems {
            add(BannerItem(topImages,screenHeight))
            if (!isSampleList(todayNewsList)){
                todayNewsList!!.forEach{
                    singleNews(it)
                }
                }
            if(!isSampleList(beforeNewsList)){
                beforeNewsList!!.forEach {
                    singleNews(it)
                }
            }
            }

        }

    //把日期转化为中文
    override fun convertDateToChinese(date:String): String {
        val dates = date.toInt()
        val day = dates%100
        val month =  ((dates%10000)-day)/100
        return " $month 月 $day 日 "
    }
//判断数据是否是样例数据
    override fun<T>  isSampleList(list: T): Boolean {
        if (list!!.equals(MainActivity.sampleImages)||list.equals(MainActivity.sampleNewsList)){
            return true
        }else{
            return false
        }
    }
//统一的设置监听器的函数
    override fun setListener(smartRefreshLayout:SmartRefreshLayout,recyclerView:RecyclerView,itemManager:ItemManager,screenHeight: Int) {
        smartRefreshLayout.setOnRefreshListener {
                RefreshLayout -> run{
            getTodayNews(recyclerView,itemManager,screenHeight)
            beforeNewsList = sampleNewsList     //这样做防止穿越时空
            smartRefreshLayout.finishRefresh(2000)

        }
        }
        smartRefreshLayout.setOnLoadMoreListener {
                RefreshLayout -> run {
            itemManager.autoRefresh {
                loadMoreItems(recyclerView,itemManager,screenHeight)
            }
            smartRefreshLayout.finishLoadMore(500)
        }
        }
    }


}

