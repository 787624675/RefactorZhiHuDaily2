package com.zhihu.refactorzhihudaily.detailpage

import com.zhihu.refactorzhihudaily.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class DetailModel{
    lateinit var css : String
    lateinit var html : String
    lateinit var js : String
    lateinit var okHttpClient: OkHttpClient
    fun buildOkhttp(){
        okHttpClient = OkHttpClient()
    }



}