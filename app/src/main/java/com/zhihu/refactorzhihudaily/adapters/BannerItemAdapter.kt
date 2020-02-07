package com.zhihu.refactorzhihudaily.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.media.MediaBrowserCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.youth.banner.adapter.BannerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.view.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class BannerItemAdapter(mDatas: List<String>?,var context : Context,var imageId: MutableList<Int>)//设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
    : BannerAdapter<String, BannerItemAdapter.BannerViewHolder>(mDatas) {

    lateinit var detailOnClickListener : View.OnClickListener
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: String, position: Int, size: Int) {
        Glide.with(holder.imageView.context).load(data).into(holder.imageView)
        holder.imageView.setOnClickListener {
            var intent : Intent = Intent()
            intent.setClass(context,DetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("newsId",imageId.get(position))
            context.startActivity(intent)
        }

    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}