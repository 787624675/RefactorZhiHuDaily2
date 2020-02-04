package com.zhihu.refactorzhihudaily.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.Indicator
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.Model
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.ui.activities.DetailActivity
import com.zhihu.refactorzhihudaily.ui.activities.MainActivity
import com.zhihu.refactorzhihudaily.view.recyclerviewdsl.DateItem
import com.zhihu.refactorzhihudaily.view.recyclerviewdsl.NewsItem
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.startActivity


class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var newsRVList:MutableList<News>? = null
    var imageList :MutableList<String>? = null
    var screenHeight : Int = 0
    val TYPE_BANNER = 1
    val TYPE_NEWS = 2
    val TYPE_DATE = 3
    //定义一个点击监听的接口
    var OnItemClick:((position:Int)->Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = parent.context.layoutInflater
        var view :View
        when(viewType){
            TYPE_BANNER ->{
                view = inflater.inflate(R.layout.item_banner, parent, false)
                val banner = view.findViewById<Banner<String,BannerItemAdapter>>(R.id.banner)
                return BannerViewHolder(view,banner)
            }
            TYPE_NEWS ->{
                view = inflater.inflate(R.layout.item_news, parent, false)
                val titleTextView = view.findViewById<TextView>(R.id.news_title)
                val hintTextView = view.findViewById<TextView>(R.id.news_hint)
                val imageView = view.findViewById<ImageView>(R.id.news_image)
                return NewsViewHolder(view, titleTextView, hintTextView, imageView)
            }
            else->{
                view = inflater.inflate(R.layout.item_date, parent, false)
                val textView = view.findViewById<TextView>(R.id.date)
                return DateViewHolder(view, textView)
            }

        }

    }

    override fun getItemCount(): Int {
        return newsRVList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_BANNER ->{
                holder as BannerViewHolder
                val params:ViewGroup.LayoutParams = holder.banner.getLayoutParams();
                params.height = (screenHeight*0.5).toInt()
                holder.banner.setLayoutParams(params)
                holder.banner.apply {
                    setDatas(imageList!!)
                    setAdapter(BannerItemAdapter(Model.sampleImages))
                    setOrientation(Banner.HORIZONTAL)
                    setIndicator(CircleIndicator(holder.banner.context))
                    setUserInputEnabled(true)
                    isAutoLoop(true)
                    setDelayTime(3000)
                }
            }
            TYPE_NEWS->{

                holder as NewsViewHolder
                holder.titleTextView.text = newsRVList!!.get(position).title
                holder.hintTextView.setText(newsRVList!!.get(position).hint)
                Glide.with(holder.imageView.context).load(newsRVList!!.get(position).imageUrl).into(holder.imageView)
                holder.itemView.setOnClickListener {
                    OnItemClick?.invoke(position)
                    }
                }
            TYPE_DATE->{
                holder as DateViewHolder
                holder.textView.setText(convertDateToChinese(newsRVList!!.get(position).date.toInt()))
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position==0) return TYPE_BANNER
        else if (newsRVList!!.get(position).id == 0) return TYPE_DATE
        else return TYPE_NEWS
    }
    private class BannerViewHolder(itemView: View?, val banner: Banner<String, BannerItemAdapter>) : RecyclerView.ViewHolder(
        itemView!!
    )
    private class NewsViewHolder(
        itemView: View?,
        val titleTextView: TextView,
        val hintTextView: TextView,
        val imageView: ImageView
    ) : RecyclerView.ViewHolder(
        itemView!!
    )
    private class DateViewHolder(itemView: View?, val textView: TextView) : RecyclerView.ViewHolder(
        itemView!!
    )
    fun convertDateToChinese(date:Int):String{
        val day = date%100
        val month = (date%10000-day)/100
        return " $month 月 $day 日  "
    }

}