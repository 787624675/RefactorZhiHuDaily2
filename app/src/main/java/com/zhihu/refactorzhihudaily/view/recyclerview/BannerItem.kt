package com.zhihu.refactorzhihudaily.view.recyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.wangzai.rvadapter.base.DelegateType
import com.wangzai.rvadapter.base.ViewHolder
import com.youth.banner.Banner
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.Indicator
import com.youth.banner.listener.OnPageChangeListener
import com.youth.banner.util.BannerUtils
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.adapters.BannerItemAdapter
import com.zhihu.refactorzhihudaily.model.ModMainDetail.screenHeight
import com.zhihu.refactorzhihudaily.model.ModMainDetail.screenWidth
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.view.Dot
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
            val all = find<FrameLayout>(R.id.frame_layout)

            var lastValue : Int = 0;
            var isLeft  = true;
            var lastMove : Int = 1

            var color : String
            var colors : IntArray
            val imageId  = mutableListOf<Int>(item.list!!.get(0).id,item.list!!.get(1).id,item.list!!.get(2).id,item.list!!.get(3).id,item.list!!.get(4).id)
            val images = mutableListOf<String>(item.list!!.get(0).imageUrl,item.list!!.get(1).imageUrl,item.list!!.get(2).imageUrl,item.list!!.get(3).imageUrl,item.list!!.get(4).imageUrl)

            val dot1 = Dot(context,x0 = (screenWidth*2).toFloat(),y0 = (screenHeight*0.5).toFloat()-50)
            val dot2 = Dot(context,x0 = (screenWidth*2).toFloat()-40,y0 = (screenHeight*0.5).toFloat()-50)
            val dot3 = Dot(context,x0 = (screenWidth*2).toFloat()-80,y0 = (screenHeight*0.5).toFloat()-50)
            val dot4 = Dot(context,x0 = (screenWidth*2).toFloat()-120,y0 = (screenHeight*0.5).toFloat()-50)
            val dot5 = Dot(context,x0 = (screenWidth*2).toFloat()-160,y0 = (screenHeight*0.5).toFloat()-50)

            val dots = mutableListOf<Dot>(dot5,dot4,dot3,dot2,dot1)

            dots[0].extend(15f)

            dots.forEach{
                all.addView(it)
            }



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

                        if (positionOffset != 0f) {
                            if (lastValue >= positionOffsetPixels) {
                                //右滑
                                isLeft = false;
                            } else if (lastValue < positionOffsetPixels) {
                                //左滑
                                isLeft = true;
                            }
                        }
                        lastValue = positionOffsetPixels;

                        if (isLeft){
                            if (position<4){
                                dots[position+1].extend(positionOffset)
                                dots[position].shorten(positionOffset)
                                dots[position].moveLeft(positionOffset)
                            }else{
                                dots[0].extend(positionOffset)
                                dots[4].shorten(positionOffset)
                                for (i in 0 .. 3){
                                    dots[i].moveRight(positionOffset)
                                }

                            }
                        }else{
                            if (position>0){
                                dots[position-1].extend(positionOffset)
                                dots[position].shorten(positionOffset)

                            }else{
                                dots[4].extend(positionOffset)
                                dots[0].shorten(positionOffset)


                            }
                        }


                    }

                    override fun onPageSelected(position: Int) {
                        color = "#"+item.list!!.get(position).date.substring(2)             //设置遮罩颜色
                        colors = intArrayOf(Color.parseColor(color),Color.parseColor(color),Color.TRANSPARENT)
                        sum.setText(item.list!!.get(position).title)
                        writer.setText(item.list!!.get(position).hint)
                        normalGroup.setColors(colors)
                        gradient.background = normalGroup
                        if (position<lastMove){
                            dots[lastMove].init()
                        }

                        lastMove = position

                    }

                })
            }
        }

    }
}