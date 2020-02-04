package com.zhihu.refactorzhihudaily.presenter.interfaces

import com.zhihu.refactorzhihudaily.model.Model
import com.zhihu.refactorzhihudaily.model.Model.beforeNewsList
import com.zhihu.refactorzhihudaily.model.Model.newsList
import com.zhihu.refactorzhihudaily.model.Model.sampleNews
import com.zhihu.refactorzhihudaily.model.Model.sampleNewsList
import com.zhihu.refactorzhihudaily.model.Model.topImages
import com.zhihu.refactorzhihudaily.model.News
import com.zhihu.refactorzhihudaily.model.todaynews.TopStory

interface MainPresenter {
    fun getTopImages():MutableList<String>{
        if (topImages != null){
            return topImages!!
        }else{
            return Model.sampleImages
        }
    }
    fun getBeforeNewsItem():MutableList<News>{
        if(beforeNewsList==null){
            return sampleNewsList
        }else{
            return beforeNewsList!!
        }
    }

}