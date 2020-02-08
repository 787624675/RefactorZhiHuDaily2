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
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.mainpage.MainPreImple.getTodayNews

import com.zhihu.refactorzhihudaily.model.ModMainDetail

import com.zhihu.refactorzhihudaily.model.ModMainDetail.getScreenHeight
import com.zhihu.refactorzhihudaily.model.ModMainDetail.remixList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.screenHeight
import org.jetbrains.anko.find
import java.util.*

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
        //设置刷新监听
        MainPreImple.setListener(smartRefreshLayout,mAdapter,screenHeight)
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

        //获取系统日期并设置
        val date = Date()
        day.setText(date.date.toString())
        month.setText(ModMainDetail.chineseMonthMap[date.month+1])

        //根据屏幕高度设置toolbar的height
        screenHeight  = getScreenHeight(this)
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)

        //设置recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        recyclerView.isFocusableInTouchMode = false
    }
//定义各种控件
    private lateinit var day : TextView
    private lateinit var month : TextView
    private lateinit var top : LinearLayout
    private lateinit var toolbar :androidx.appcompat.widget.Toolbar
    private lateinit var smartRefreshLayout : SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private val mAdapter =  MultiItemAdapter(this, remixList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()         //初始化视图
        initListeners()    //初始化监听器
        //发送网络请求
        getTodayNews(mAdapter,screenHeight)
    }
}
