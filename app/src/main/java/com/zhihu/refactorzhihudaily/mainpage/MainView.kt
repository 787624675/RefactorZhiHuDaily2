package com.zhihu.refactorzhihudaily.mainpage

import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter

interface MainView {
    fun initViews()
    fun initListeners()

    fun showErrorMode(adapter: MultiItemAdapter)
}