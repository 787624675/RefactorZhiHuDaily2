package com.zhihu.refactorzhihudaily.view.recyclerviewdsl

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.BannerItemAdapter
import org.jetbrains.anko.layoutInflater

class BannerItem(val content: List<String>?,val height:Int):Item {

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_banner, parent, false)
            val banner = view.findViewById<Banner<String,BannerItemAdapter>>(R.id.banner)
            return ViewHolder(view, banner)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as BannerItem

            val params:ViewGroup.LayoutParams = holder.banner.getLayoutParams();
            params.height = (item.height*0.5).toInt()
            holder.banner.setLayoutParams(params)
            holder.banner.apply {
                setAdapter(BannerItemAdapter(item.content))
                setOrientation(Banner.HORIZONTAL)
                setIndicator(CircleIndicator(holder.banner.context))
                setUserInputEnabled(true)
                isAutoLoop(true)
                setDelayTime(3000)
            }
        }



        private class ViewHolder(itemView: View?, val banner: Banner<String,BannerItemAdapter>) : RecyclerView.ViewHolder(
            itemView!!
        )
    }


    override val controller: ItemController
        get() = Controller


}
fun MutableList<Item>.singleBanner(content: List<String>?,height: Int) = add(BannerItem(content,height))