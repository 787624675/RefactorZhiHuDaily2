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
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.presenter.MainImplementation
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.getTodayNews
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.setListener
import org.jetbrains.anko.find
import java.util.*

class MainActivity : AppCompatActivity(){
    lateinit var day : TextView
    lateinit var month : TextView
    lateinit var top : LinearLayout
    lateinit var toolbar :androidx.appcompat.widget.Toolbar
    lateinit var smartRefreshLayout : SmartRefreshLayout
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //找控件
        day = findViewById<TextView>(R.id.day)
        month = findViewById<TextView>(R.id.month)
        toolbar  = findViewById(R.id.toolbar_top)
        smartRefreshLayout = find<SmartRefreshLayout>(R.id.refresh_layout)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        top = find<LinearLayout>(R.id.top)
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
        month.setText(Moduel.chineseMonthMap[date.month+1])
        //根据屏幕高度设置toolbar的height
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)
        //recyclerview显示
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mAdapter =  MultiItemAdapter(this,MainImplementation.remixList)
        recyclerView.adapter = mAdapter
        recyclerView.isFocusableInTouchMode = false
        //设置各种监听器
        setListener(smartRefreshLayout,recyclerView,mAdapter,screenHeight)
        //发送网络请求
        getTodayNews(recyclerView,mAdapter,screenHeight)
    }
}
