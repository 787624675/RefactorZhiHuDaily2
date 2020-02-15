package com.zhihu.refactorzhihudaily.mainpage

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.zhihu.refactorzhihudaily.model.ModMainDetail
import org.jetbrains.anko.windowManager

class MainPresenter {
    lateinit var mainView: MainView
    lateinit var context: Context

    constructor(mainView: MainView,context: Context){
        this.mainView = mainView
        this.context = context
    }
    fun getScreenHeight(context: Context): MutableList<Int> {
        //获取屏幕高度
        val windowManager : WindowManager = context.windowManager
        val outMartics   = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMartics)
        val screenHeight  = outMartics.heightPixels
        val screenWidth =  outMartics.widthPixels
        return mutableListOf<Int>(screenHeight.toInt(),screenWidth.toInt())
    }
    fun getData(){
        ModMainDetail.screenHeight = getScreenHeight(context)[0]
        ModMainDetail.screenWidth = getScreenHeight(context)[1]

    }
}