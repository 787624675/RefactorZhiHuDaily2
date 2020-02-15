package com.zhihu.refactorzhihudaily.loginpage

import com.zhihu.refactorzhihudaily.model.ModMainDetail

class Presenter  {
    var view : View
    constructor(view:View){
        this.view = view
    }
    fun initDayAndNight(){
        if (ModMainDetail.dayOrNight == "Day"){
            view.showDay()
        }else{
            view.showNight()
        }
    }
    fun handleDayAndNight(){
        if (ModMainDetail.dayOrNight == "Day"){
            ModMainDetail.dayOrNight = "Night"
            view.showNight()
        }else{
            ModMainDetail.dayOrNight = "Day"
            view.showDay()
        }
    }
}