package com.zhihu.refactorzhihudaily.mainpage

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.ItemManager
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.Moduel
import com.zhihu.refactorzhihudaily.mainpage.MainPreImple.getTodayNews
import com.zhihu.refactorzhihudaily.mainpage.MainPreImple.setListener
import org.jetbrains.anko.find
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(),MainView{
    override fun initListeners() {
        //初始各种监听器
        //点击top以后跳到最顶部
        top.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
        day.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }
        month.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

    }

    //初始化视图
    override fun initViews() {
        //找到各种控件
        day = findViewById(R.id.day)
        month = findViewById(R.id.month)
        toolbar  = findViewById(R.id.toolbar_top)
        smartRefreshLayout = find(R.id.refresh_layout)      //find是anko库的dsl，混用了orz
        recyclerView = findViewById(R.id.recycler_view)
        top = find(R.id.top)
    }
//定义各种控件
    lateinit var day : TextView
    lateinit var month : TextView
    lateinit var top : LinearLayout
    lateinit var toolbar :androidx.appcompat.widget.Toolbar
    lateinit var smartRefreshLayout : SmartRefreshLayout
    lateinit var recyclerView: RecyclerView
    var screenHeight = 800
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()         //初始化视图
        initListeners()    //初始化监听器
        //获取屏幕高度
        val windowManager : WindowManager = this.windowManager
        var outMartics   = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMartics)
        screenHeight  = outMartics.heightPixels

        //获取系统日期并设置
        val date = Date()
        day.setText(date.date.toString())
        month.setText(Moduel.chineseMonthMap[date.month+1])
        //根据屏幕高度设置toolbar的height
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)
        //recyclerview显示
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mAdapter =  MultiItemAdapter(this,
            MainPreImple.remixList)
        recyclerView.adapter = mAdapter
        recyclerView.isFocusableInTouchMode = false
        //设置各种监听器
        setListener(smartRefreshLayout,recyclerView,mAdapter,screenHeight)
        //发送网络请求
        getTodayNews(recyclerView,mAdapter,screenHeight)
    }
}
