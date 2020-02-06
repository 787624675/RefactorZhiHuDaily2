package com.zhihu.refactorzhihudaily.view.recyclerview

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginTop
import com.wangzai.rvadapter.base.DelegateType
import com.wangzai.rvadapter.base.ViewHolder
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.BannerItemAdapter
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.presenter.MainImplementation.topNewsList
import com.zhihu.refactorzhihudaily.view.recyclerviewdsl.DateItem
import kotlinx.android.synthetic.main.item_banner.view.*
import org.jetbrains.anko.find

class BannerItem : DelegateType<RemixItem> {

    override val itemViewLayoutId :Int
        get() = R.layout.item_banner
    override fun isItemViewType(item: RemixItem, position: Int): Boolean {
        return position==0
    }

    override fun convert(context: Context, holder: ViewHolder, item: RemixItem, position: Int) {
        with(holder.itemView){
            val banner = findViewById<Banner<String,BannerItemAdapter>>(R.id.banner)
            val params: ViewGroup.LayoutParams = banner.getLayoutParams()
            val sum = find<TextView>(R.id.sum)
            val writer = find<TextView>(R.id.writer)
            val bannerHeight = (item.id*0.5).toInt()
            val images = mutableListOf<String>(item.list!!.get(0).imageUrl,item.list!!.get(1).imageUrl,item.list!!.get(2).imageUrl,item.list!!.get(3).imageUrl,item.list!!.get(4).imageUrl)
            gradient.layoutParams.height = (bannerHeight*0.5).toInt()
            sum.setText(item.list.get(0).title)
            frame_layout.layoutParams.height = (item.id*0.5).toInt()
            params.height = (item.id*0.5).toInt()
            banner.setLayoutParams(params)
            banner.apply{
                setAdapter(BannerItemAdapter(images))
                setOrientation(Banner.HORIZONTAL)
                setIndicator(CircleIndicator(context))
                setUserInputEnabled(true)
                isAutoLoop(true)
                setDelayTime(3000)
                addOnPageChangeListener(object : OnPageChangeListener{
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        sum.setText(item.list.get(position).title)
                        writer.setText(item.list.get(position).hint)
                    }

                })
            }
        }

    }
}