package com.zhihu.refactorzhihudaily.mainpage

import WebPageAdapter
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.*
import com.zhihu.refactorzhihudaily.model.ModMainDetail.beforeNewsList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.idList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.remixList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.todayNewsList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.topImages
import com.zhihu.refactorzhihudaily.model.ModMainDetail.topNewsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.windowManager

object MainPreImple: MainPresenter {



    //获取今日新闻
    @JvmOverloads
     override fun getTodayNews( mAdapter:MultiItemAdapter, screenHeight: Int){
        GlobalScope.launch (Dispatchers.Main){
            launch(Dispatchers.IO){
                remixList.clear()
                idList.clear()
                val dataBean = RetrofitClient.reqApi.getTodayNews().await()
                topImages = dataBean.getTopImages()
                topNewsList = dataBean.getTopNews()
                todayNewsList = dataBean.getNews()
                val dataBeanBefore = RetrofitClient.reqApi.getBeforeNews(todayNewsList!!.get(0).date).await()
                beforeNewsList = dataBeanBefore.getNews()
                if (!isSampleList(topNewsList)){
                    remixList.add(0,RemixItem(
                        list = topNewsList,
                        screenHeight = screenHeight,
                        type = 1))

                }
                if (!isSampleList(todayNewsList)){
                    todayNewsList!!.forEach {
                        remixList.add(RemixItem(
                            title = it.title,
                            hint = it.hint,
                            imageUrl = it.imageUrl,
                            id = it.id,
                            date = it.date,
                            type = 3))
                        idList.add(it.id)
                    }

                }
                if (!isSampleList(beforeNewsList)){
                    remixList.add(RemixItem(
                        date = convertDateToChinese(
                            beforeNewsList!!.get(
                                0
                            ).date
                        ),
                        type = 2))
                    beforeNewsList!!.forEach {
                        remixList.add(RemixItem(
                            title = it.title,
                            hint = it.hint,
                            imageUrl = it.imageUrl,
                            id = it.id,
                            date = it.date,
                            type = 3))
                        idList.add(it.id)
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
    override fun getTheBeforeNews( mAdapter:MultiItemAdapter, screenHeight: Int,pageAdapter: WebPageAdapter){
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                var dataBean : BeforeNews
                    dataBean = RetrofitClient.reqApi.getBeforeNews(beforeNewsList!!.get(0).date).await()
                    beforeNewsList = dataBean.getNews()
                if (!isSampleList(beforeNewsList)){
                    remixList.add(RemixItem(date = convertDateToChinese(
                        beforeNewsList!!.get(
                            0
                        ).date
                    ),type = 2))
                    beforeNewsList!!.forEach {
                        remixList.add(RemixItem(
                            title = it.title,
                            hint = it.hint,
                            imageUrl = it.imageUrl,
                            id = it.id,
                            date = it.date,
                            type = 3))
                        idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    if (mAdapter.mContext!=null){
                        mAdapter.notifyDataSetChanged()
                    }

                    if (pageAdapter.webViewList!=null){
                        pageAdapter.notifyDataSetChanged()
                    }
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
        if (list!!.equals(ModMainDetail.sampleImages)||list.equals(ModMainDetail.sampleNewsList)){
            return true
        }else{
            return false
        }
    }
//统一的设置监听器的函数
    override fun setListener(smartRefreshLayout:SmartRefreshLayout,mAdapter: MultiItemAdapter,screenHeight: Int) {
        smartRefreshLayout.setOnRefreshListener {
                RefreshLayout -> run{
            getTodayNews(

                mAdapter,
                screenHeight
            )
            smartRefreshLayout.finishRefresh(200)

        }
        }
        smartRefreshLayout.setOnLoadMoreListener {
                RefreshLayout -> run {
            getTheBeforeNews(
                mAdapter,
                screenHeight
            )
            smartRefreshLayout.finishLoadMore(200)
        }
        }
    }



}

