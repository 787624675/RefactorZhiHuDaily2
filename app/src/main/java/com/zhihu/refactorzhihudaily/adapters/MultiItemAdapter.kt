package com.zhihu.refactorzhihudaily.adapters

import android.content.Context
import com.wangzai.rvadapter.adapter.DelegateItemAdapter
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.view.recyclerview.BannerItem
import com.zhihu.refactorzhihudaily.view.recyclerview.DateItem
import com.zhihu.refactorzhihudaily.view.recyclerview.NewsItem

class MultiItemAdapter(mContext: Context?, mDatas: List<RemixItem>?)
    : DelegateItemAdapter<RemixItem>(mContext!!, mDatas!!) {
    init {
        addItemViewDelegate(BannerItem())
        addItemViewDelegate(DateItem())
        addItemViewDelegate(NewsItem())
    }
}