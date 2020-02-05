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
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.getTodayNews
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.loadMoreItems
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.setListener
import org.jetbrains.anko.find
import java.util.*

class MainActivity : AppCompatActivity(){

    companion object Values{
        //默认的数据
        val chineseMonthMap : Map<Int,String> = mapOf(1 to "一月",2 to "二月",3 to "三月",4 to "四月",5 to "五月",6 to "六月",7 to "七月",8 to "八月",9 to "九月",10 to "一月",11 to "十一月",12 to "十二月")
        val sampleImageUrl :String = "https://pic3.zhimg.com/v2-c6fc8f2f830aa0b2e448697c5f92b286.jpg"
        val sampleNews : News = News("?????","?????","https://pic4.zhimg.com/v2-89b192a671f7a6fe9c49b8233d84028f.jpg",0,"20200203")
        val sampleNewsList:MutableList<News> = mutableListOf(sampleNews, sampleNews, sampleNews)
        val sampleImages : MutableList<String> = mutableListOf(sampleImageUrl, sampleImageUrl,sampleImageUrl,sampleImageUrl,sampleImageUrl)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //找控件
        val day = findViewById<TextView>(R.id.day)
        val month = findViewById<TextView>(R.id.month)
        val toolbar :androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_top)
        val smartRefreshLayout = find<SmartRefreshLayout>(R.id.refresh_layout)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
       //初始化工具
        val itemManager = ItemManager()
        //获取屏幕高度
        val windowManager : WindowManager = this.windowManager
        var outMartics   = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMartics)
        val screenHeight:Int = outMartics.heightPixels
        //获取系统日期并设置
        val date = Date()
        day.setText(date.date.toString())
        month.setText(chineseMonthMap[date.month+1])
        //根据屏幕高度设置toolbar的height
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)
        //recyclerview显示
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(itemManager)
        itemManager.autoRefresh { loadMoreItems(recyclerView,itemManager,screenHeight) }
        //设置各种监听器
        setListener(smartRefreshLayout,recyclerView,itemManager,screenHeight)
        //发送网络请求
        getTodayNews(recyclerView,itemManager,screenHeight)
    }
}
