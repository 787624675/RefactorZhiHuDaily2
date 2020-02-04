package com.zhihu.refactorzhihudaily.model.beforenews

data class Story(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)