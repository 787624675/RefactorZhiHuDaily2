package com.zhihu.refactorzhihudaily.adapters

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import com.youth.banner.adapter.BannerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhihu.refactorzhihudaily.detailpage.DetailActivity


class BannerItemAdapter(mDatas: List<String>?,var context : Context,var imageId: MutableList<Int>)//设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
    : BannerAdapter<String, BannerItemAdapter.BannerViewHolder>(mDatas) {

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
            intent.setClass(context, DetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("newsId",imageId.get(position))
            intent.putExtra("type",1)
            context.startActivity(intent)
        }

    }

    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)
}