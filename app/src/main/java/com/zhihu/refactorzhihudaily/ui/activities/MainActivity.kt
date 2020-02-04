package com.zhihu.refactorzhihudaily.ui.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.RecyclerViewAdapter
import com.zhihu.refactorzhihudaily.model.Model
import com.zhihu.refactorzhihudaily.model.Model.rCVadapter
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.RetrofitClient
import com.zhihu.refactorzhihudaily.presenter.interfaces.MainPresenter
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() ,MainPresenter{
    var recyclerView : RecyclerView? = null
    var screenHeight:Int = 0

    var newsRCVlist = ArrayList<News>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //找控件
        val day = findViewById<TextView>(R.id.day)
        val month = findViewById<TextView>(R.id.month)
        val toolbar :androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_top)
        val smartRefreshLayout = find<SmartRefreshLayout>(R.id.refresh_layout)
        recyclerView = findViewById(R.id.recycler_view)

        //发送网络请求
        RetrofitClient.getTodayNews()
        //获取屏幕高度
        val windowManager : WindowManager = this.windowManager
        var outMartics  = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMartics)
        screenHeight = outMartics.heightPixels
        //获取系统日期并设置
        val date = Date()
        day.setText(date.date.toString())
        month.setText(Model.chineseMonthMap[date.month])
        //根据屏幕高度设置toolbar的height
        val paramsForToolbar: ViewGroup.LayoutParams = toolbar.getLayoutParams();
        paramsForToolbar.height = (screenHeight*0.1).toInt()
        toolbar.setLayoutParams(paramsForToolbar)
        //配置recyclerview的适配器
        rCVadapter.screenHeight = screenHeight
        rCVadapter.OnItemClick =  {position: Int ->
            startActivity<DetailActivity>("newsId" to newsRCVlist.get(position).id )
        }
        rCVadapter.newsRVList=newsRCVlist
        recyclerView!!.adapter = rCVadapter
        //给refresh layout设置监听器
        smartRefreshLayout.setOnRefreshListener {
            RefreshLayout -> run{
            RetrofitClient.getTodayNews()
            smartRefreshLayout.finishRefresh(2000)
        }
        }
        smartRefreshLayout.setOnLoadMoreListener {
            RefreshLayout -> run {
            RetrofitClient.getBeforeNews()
            smartRefreshLayout.finishLoadMore(2000)
        }
        }






    }

}
