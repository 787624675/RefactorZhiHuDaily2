package com.zhihu.refactorzhihudaily.view.recyclerviewdsl

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.twt.retrox.recyclerviewdsl.Item
import cn.edu.twt.retrox.recyclerviewdsl.ItemController
import com.zhihu.refactorzhihudaily.R
import org.jetbrains.anko.layoutInflater

class DateItem(val content : String):Item {
    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = parent.context.layoutInflater
            val view = inflater.inflate(R.layout.item_date, parent, false)
            val textView = view.findViewById<TextView>(R.id.date)
            return ViewHolder(view, textView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

            holder as ViewHolder
            item as DateItem


            holder.textView.text = item.content
        }


        private class ViewHolder(itemView: View?, val textView: TextView) : RecyclerView.ViewHolder(
            itemView!!
        )
    }


    override val controller: ItemController
        get() = Controller


}
fun MutableList<Item>.singleDate(content: String) = add(DateItem(content))