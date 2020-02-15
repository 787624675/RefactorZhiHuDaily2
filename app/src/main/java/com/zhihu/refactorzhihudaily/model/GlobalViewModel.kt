package com.zhihu.refactorzhihudaily.model

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zhihu.refactorzhihudaily.model.ModMainDetail.dayOrNight

class GlobalViewModel(): ViewModel() {
    private var mCurrentMode : MutableLiveData<String>? = null
    fun getCurrentMode():MutableLiveData<String>{
        if (mCurrentMode == null){
            mCurrentMode = MutableLiveData()
        }
        return mCurrentMode!!
    }
    val modeObserver : Observer<String> = Observer {
        dayOrNight = it

    }
}