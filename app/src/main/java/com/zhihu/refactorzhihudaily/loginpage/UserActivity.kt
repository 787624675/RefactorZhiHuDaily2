package com.zhihu.refactorzhihudaily.loginpage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.zhihu.refactorzhihudaily.R
import com.zhihu.refactorzhihudaily.model.ModMainDetail
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

class UserActivity : AppCompatActivity() , View{
    lateinit var linearLayout : LinearLayout
    lateinit var switch : Switch
    override fun showDay() {
        switch.text = "日间模式"
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }

    override fun showNight() {
        switch.text = "夜间模式"
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
           }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        switch = find<Switch>(R.id.switch1)
        linearLayout = find(R.id.all)
        val presenter : Presenter = Presenter(this)
        presenter.initDayAndNight()
        switch.setOnClickListener {
            presenter.handleDayAndNight()
        }
    }

}
