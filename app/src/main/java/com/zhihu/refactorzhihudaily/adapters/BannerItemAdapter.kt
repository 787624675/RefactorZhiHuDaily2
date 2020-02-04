package com.zhihu.refactorzhihudaily.adapters

import android.view.ViewGroup
import android.widget.ImageView
import com.youth.banner.adapter.BannerAdapter
import com.zhihu.refactorzhihudaily.model.todaynews.TodayNews
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.annotation.NonNull


class BannerItemAdapter(mDatas: List<String>)//设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
    : BannerAdapter<String, BannerItemAdapter.BannerViewHolder>(mDatas) {

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
                 //这个context可能会出问题
    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}