package com.zhihu.refactorzhihudaily.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.wangzai.rvadapter.base.DelegateType
import com.wangzai.rvadapter.base.ViewHolder
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnPageChangeListener
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.BannerItemAdapter
import com.zhihu.refactorzhihudaily.model.RemixItem
import kotlinx.android.synthetic.main.item_banner.view.*
import org.jetbrains.anko.find

class BannerItem : DelegateType<RemixItem> {

    override val itemViewLayoutId :Int
        get() = R.layout.item_banner
    override fun isItemViewType(item: RemixItem, position: Int): Boolean {
        return position==0
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun convert(context: Context, holder: ViewHolder, item: RemixItem, position: Int) {
        with(holder.itemView){
            val normal = ContextCompat.getDrawable(context, R.drawable.gradient_for_viewpager) //用来设置图片下方的遮罩
            val normalGroup = normal as GradientDrawable
            val banner = findViewById<Banner<String,BannerItemAdapter>>(R.id.banner)
            val params: ViewGroup.LayoutParams = banner.getLayoutParams()
            val gradient = find<ImageView>(R.id.gradient)
            val sum = find<TextView>(R.id.sum)
            val writer = find<TextView>(R.id.writer)
            val bannerHeight = (item.screenHeight*0.5).toInt()
            var color : String
            var colors : IntArray
            val imageId  = mutableListOf<Int>(item.list!!.get(0).id,item.list!!.get(1).id,item.list!!.get(2).id,item.list!!.get(3).id,item.list!!.get(4).id)
            val images = mutableListOf<String>(item.list!!.get(0).imageUrl,item.list!!.get(1).imageUrl,item.list!!.get(2).imageUrl,item.list!!.get(3).imageUrl,item.list!!.get(4).imageUrl)
            gradient.layoutParams.height = (bannerHeight*0.5).toInt()
            color = "#"+item.list!!.get(0).date.substring(2)            //设置遮罩的初始颜色
            colors = intArrayOf(Color.parseColor(color),Color.parseColor(color),Color.TRANSPARENT)
            normalGroup.setColors(colors)
            gradient.background = normalGroup
            sum.setText(item.list!!.get(0).title)
            frame_layout.layoutParams.height = (item.screenHeight*0.5).toInt()
            params.height = (item.screenHeight*0.5).toInt()
            banner.setLayoutParams(params)
            banner.apply{
                setAdapter(BannerItemAdapter(images,context,imageId))
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
                        color = "#"+item.list!!.get(position).date.substring(2)             //设置遮罩颜色
                        colors = intArrayOf(Color.parseColor(color),Color.parseColor(color),Color.TRANSPARENT)
                        sum.setText(item.list!!.get(position).title)
                        writer.setText(item.list!!.get(position).hint)
                        normalGroup.setColors(colors)
                        gradient.background = normalGroup
                    }

                })
            }
        }

    }
}