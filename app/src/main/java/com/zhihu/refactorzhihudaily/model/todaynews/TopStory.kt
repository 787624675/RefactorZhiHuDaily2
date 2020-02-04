package com.zhihu.refactorzhihudaily.model.todaynews

data class TopStory(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image: String,
    val image_hue: String,
    val title: String,
    val type: Int,
    val url: String
)