package com.zhihu.refactorzhihudaily.presenter

import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import cn.edu.twt.retrox.recyclerviewdsl.withItems
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.BeforeNews
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.model.RetrofitClient
import com.zhihu.refactorzhihudaily.view.MainActivity
import com.zhihu.refactorzhihudaily.view.MainActivity.Values.sampleImages
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
    var topImages : MutableList<String> = MainActivity.sampleImages
    var topNewsList : MutableList<News> = sampleNewsList
    var remixList : MutableList<RemixItem> = ArrayList()
    //获取今日新闻
    override fun getTodayNews(recyclerView:RecyclerView, mAdapter:MultiItemAdapter, screenHeight: Int){
        GlobalScope.launch (Dispatchers.Main){
            launch(Dispatchers.IO){
                val dataBean = RetrofitClient.reqApi.getTodayNews().await()
                topImages = dataBean.getTopImages()
                topNewsList = dataBean.getTopNews()
                todayNewsList = dataBean.getNews()
                val dataBeanBefore = RetrofitClient.reqApi.getBeforeNews(todayNewsList!!.get(0).date).await()
                beforeNewsList = dataBeanBefore.getNews()
                if (!isSampleList(topNewsList)){
                    remixList.add(0,RemixItem(topNewsList, "??","??","??",screenHeight,"??",1))
                }
                if (!isSampleList(todayNewsList)){
                    todayNewsList!!.forEach {
                        remixList.add(RemixItem(null,it.title,it.hint,it.imageUrl,it.id,it.date,3))
                    }

                }
                if (!isSampleList(beforeNewsList)){
                    remixList.add(RemixItem(null,"??","??","??",0, convertDateToChinese(beforeNewsList!!.get(0).date),2))
                    beforeNewsList!!.forEach {
                        remixList.add(RemixItem(null,it.title,it.hint,it.imageUrl,it.id,it.date,3))
                    }
                }
                launch (Dispatchers.Main){
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    //获取前些日子的新闻
    override fun getTheBeforeNews(recyclerView:RecyclerView, mAdapter:MultiItemAdapter, screenHeight: Int){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                    dataBean = RetrofitClient.reqApi.getBeforeNews(beforeNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                if (!isSampleList(beforeNewsList)){
                    remixList.add(RemixItem(null,"??","??","??",0, convertDateToChinese(
                        beforeNewsList!!.get(0).date),2))
                    beforeNewsList!!.forEach {
                        remixList.add(RemixItem(null,it.title,it.hint,it.imageUrl,it.id,it.date,3))
                    }
                }
                launch (Dispatchers.Main){
                        mAdapter.notifyDataSetChanged()
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
    override fun setListener(smartRefreshLayout:SmartRefreshLayout,recyclerView:RecyclerView,mAdapter: MultiItemAdapter,screenHeight: Int) {
        smartRefreshLayout.setOnRefreshListener {
                RefreshLayout -> run{
            getTodayNews(recyclerView,mAdapter,screenHeight)
            smartRefreshLayout.finishRefresh(200)

        }
        }
        smartRefreshLayout.setOnLoadMoreListener {
                RefreshLayout -> run {
            getTheBeforeNews(recyclerView,mAdapter,screenHeight)
            smartRefreshLayout.finishLoadMore(200)
        }
        }
    }


}

