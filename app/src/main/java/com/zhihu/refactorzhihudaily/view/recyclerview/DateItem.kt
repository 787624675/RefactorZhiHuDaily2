package com.zhihu.refactorzhihudaily.view.recyclerview

import android.content.Context
import com.wangzai.rvadapter.base.DelegateType
import com.wangzai.rvadapter.base.ViewHolder
import com.zhihu.refactorzhihudaily.model.RemixItem
import kotlinx.android.synthetic.main.item_date.view.*
import org.jetbrains.anko.generated.appcompatV7.coroutines.R

class DateItem : DelegateType<RemixItem> {
    override val itemViewLayoutId: Int
        get() = com.zhihu.refactorzhihudaily.R.layout.item_date

    override fun convert(context: Context, holder: ViewHolder, item: RemixItem, position: Int) {
        with(holder.itemView){
            date.setText(item.date)
        }
    }

    override fun isItemViewType(item: RemixItem, position: Int): Boolean {
        return item.type == 2
    }

}