package com.zhihu.refactorzhihudaily.model

import android.view.View
import com.zhihu.refactorzhihudaily.view.recyclerview.DetailOnClickListener

class RemixItem @JvmOverloads constructor( list: MutableList<News>? = null, title:String? = null, hint:String? = null, imageUrl:String? = null, id:Int? = null, date:String? = null, screenHeight:Int = 0, type: Int? = 1){

    var screenHeight : Int = 0
    var list: MutableList<News>? = null
    var title:String? = null
    var hint:String? = null
    var imageUrl:String? = null
    var id:Int? = null
    var date:String? = null
    var type: Int? = null

     init {
        this.list = list
        this.title = title
        this.hint = hint
        this.imageUrl = imageUrl
        this.id = id
        this.date = date
        this.type = type
        this.screenHeight = screenHeight
    }
}