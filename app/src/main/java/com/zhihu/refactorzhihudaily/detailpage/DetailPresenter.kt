package com.zhihu.refactorzhihudaily.detailpage

import WebPageAdapter
import android.content.Context
import android.webkit.WebView
import androidx.viewpager.widget.ViewPager
import com.zhihu.refactorzhihudaily.model.ModMainDetail
import com.zhihu.refactorzhihudaily.network.News
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.network.RetrofitClient

class DetailPresenter {
    lateinit var  detailView : DetailView
    lateinit var context: Context

    constructor(detailView: DetailView,context: Context){
        this.detailView = detailView
        this.context = context
    }
    //获取正确的current position
    fun getIndex(remixList: MutableList<RemixItem>, currentNews: RemixItem):Int{
        var index : Int = 0
        for (i:Int in 0..remixList.indexOf(currentNews))
            if (remixList.get(i).id!=null&&remixList.get(i).id != 0){
                index++
            }

        return index
    }
//这个函数把webview放到viewpager里
    //分banner和list两种不同情况
    fun initView(type:Int,id:Int,page:ViewPager,context: Context){
        if (type == 1){    //type==1 表示是banner被点击了
            var currentNews : News = ModMainDetail.sampleNews         //被点击的新闻，默认是sample
            val pageList = ArrayList<WebView>()          //新建webView列表
            ModMainDetail.topNewsList.forEach {                //把topNews翻了个遍就是为了找与被点击新闻id相同的那个，然后先记着它
                detailView.addView(it.id,pageList,context)
                if (it.id == id){                         //找到了，哈哈
                    currentNews = it                   //先记着它
                }
            }
            val index : Int = ModMainDetail.topNewsList.indexOf(currentNews)          //找找被点击的新闻是列表的第几个
            val adapter = WebPageAdapter(pageList)
            detailView.showView(page,adapter,index)                                 //把viewpager的current置为index
        }else{                                                           //被点击的不是banner，而是recyclerview
            var currentNews : RemixItem? = null                          //和上面的逻辑一样，复制粘贴实现代码复用（滚粗）
            val pageList = ArrayList<WebView>()
            for (i:Int in 0 .. ModMainDetail.remixList.size-1){                       //不用forEach了，防止java.util.ConcurrentModificationException
                val it = ModMainDetail.remixList.get(i)
                if (it.id!=null&&it.id!=0){
                    detailView.addView(it.id!!,pageList,context)
                    if (it.id == id){
                        currentNews = it
                    }
                }
            }
            val index : Int = getIndex(ModMainDetail.remixList,currentNews!!)
            var adapter = WebPageAdapter(pageList)
            detailView.showView(page,adapter,index-1)

            //设置翻页监听，翻到最后一页时发送网络请求更新model
            page.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (position==pageList.size-1){
                        //滑到最后一页时加载下一天的新闻
                        RetrofitClient.getTheBeforeNewsList(adapter, pageList, context)
                    }
                }

            })

        }
    }

}