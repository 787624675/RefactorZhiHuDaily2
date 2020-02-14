package com.zhihu.refactorzhihudaily.detailpage

import WebPageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.ModMainDetail.getTheBeforeNewsList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.sampleNews
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.RemixItem

import com.zhihu.refactorzhihudaily.model.ModMainDetail.remixList
import com.zhihu.refactorzhihudaily.model.ModMainDetail.topNewsList

import org.jetbrains.anko.find

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val page = find<ViewPager>(R.id.page)
        var type = intent.getIntExtra("type",3)       //拿到被点击的item的种类，1表示banner被点击，3表示recyclerview被点击
        var id = intent.getIntExtra("newsId",0)        //拿到被点击新闻的id

        if (type == 1){    //type==1 表示是banner被点击了
            var currentNews : News = sampleNews         //被点击的新闻，默认是sample
            val pageList = ArrayList<WebView>()          //新建webView列表
            topNewsList.forEach {                //把topNews翻了个遍就是为了找与被点击新闻id相同的那个，然后先记着它
                DetailPreImpl.addView(it.id,pageList,this@DetailActivity)
                if (it.id == id){                         //找到了，哈哈
                    currentNews = it                   //先记着它
                }
            }
            val index : Int = topNewsList.indexOf(currentNews)          //找找被点击的新闻是列表的第几个
            page.adapter = WebPageAdapter(pageList)
            page.setCurrentItem(index)                                  //把viewpager的current置为index
        }else{                                                           //被点击的不是banner，而是recyclerview
            var currentNews : RemixItem? = null                          //和上面的逻辑一样，复制粘贴实现代码复用（滚粗）
            val pageList = ArrayList<WebView>()
            for (i:Int in 0 .. remixList.size-1){                       //不用forEach了，防止java.util.ConcurrentModificationException
                val it = remixList.get(i)
                if (it.id!=null&&it.id!=0){
                    DetailPreImpl.addView(it.id!!,pageList,this@DetailActivity)
                    if (it.id == id){
                        currentNews = it
                    }
                }
            }
            val index : Int = getIndex(remixList,currentNews!!)
            var adapter = WebPageAdapter(pageList)
            page.adapter = adapter
            page.setCurrentItem(index-1)
            page.addOnPageChangeListener(object :OnPageChangeListener{
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
                        getTheBeforeNewsList(adapter,pageList,this@DetailActivity)
                    }
                }

            })

        }


    }


    //获取正确的current position
    fun getIndex(remixList: MutableList<RemixItem>,currentNews:RemixItem):Int{
        var index : Int = 0
        for (i:Int in 0..remixList.indexOf(currentNews))
            if (remixList.get(i).id!=null&&remixList.get(i).id != 0){
                index++
            }

        return index
    }
}
