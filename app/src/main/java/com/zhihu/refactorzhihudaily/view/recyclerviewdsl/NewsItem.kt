package com.zhihu.refactorzhihudaily.view.recyclerviewdsl

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.bumptech.glide.Glide
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.News
import org.jetbrains.anko.layoutInflater

class NewsItem( val content:News):Item {


    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_news, parent, false)
            val titleTextView = view.findViewById<TextView>(R.id.news_title)
            val hintTextView = view.findViewById<TextView>(R.id.news_hint)
            val imageView = view.findViewById<ImageView>(R.id.news_image)
            return ViewHolder(view, titleTextView,hintTextView,imageView )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as NewsItem
            holder.titleTextView.text = item.content.title
            holder.hintTextView.setText(item.content.hint)
            Glide.with(holder.imageView.context).load(item.content.imageUrl).into(holder.imageView)
        }


        private class ViewHolder(
            itemView: View?,
            val titleTextView: TextView,
            val hintTextView: TextView,
            val imageView: ImageView


        ) : RecyclerView.ViewHolder(
            itemView!!
        )
    }


    override val controller: ItemController
        get() = Controller


}
fun MutableList<Item>.singleNews(content: News) = add(NewsItem(content))
