package com.zhihu.refactorzhihudaily.model.detailednews

data class DetailedNews(
    val body: String,
    val css: List<String>,
    val ga_prefix: String,
    val id: Int,
    val image: String,
    val image_hue: String,
    val image_source: String,
    val images: List<String>,
    val js: List<String>,
    val share_url: String,
    val title: String,
    val type: Int,
    val url: String
)