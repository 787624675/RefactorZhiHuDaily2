package com.zhihu.refactorzhihudaily.view.recyclerview

import android.content.Context
import com.wangzai.rvadapter.base.DelegateType
import com.wangzai.rvadapter.base.ViewHolder
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.BannerItemAdapter
import com.zhihu.refactorzhihudaily.model.RemixItem
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
            banner.apply{
                setAdapter(BannerItemAdapter(item.list))
                setOrientation(Banner.HORIZONTAL)
                setIndicator(CircleIndicator(context))
                setUserInputEnabled(true)
                isAutoLoop(true)
                setDelayTime(3000)            }
        }

    }
}