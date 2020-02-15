package com.zhihu.refactorzhihudaily.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.ScaleAnimation


//这是一个自定义view    //失败了
class Dot @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0,var x0:Float=600F,var y0 : Float=600F,var middle :Float = 0F) : View(context, attrs, defStyle) {



    //小圆点伸长
    fun extend(extension:Float){
        if (middle-0f<5){   //小于5才伸长，防止两次伸长
            middle-=extension*3
            requestLayout()
        }

    }
    fun shorten(extension: Float){
        if (middle<0){
            middle+=extension*3
            requestLayout()
        }

    }
    fun init(){
        middle=0f
        requestLayout()
    }
    fun moveLeft(extension: Float){

        x0 -= extension*3
        requestLayout()

    }
    fun moveRight(extension: Float){
        x0 += extension*3
        requestLayout()
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.parseColor("#E0E0E0")
        canvas!!.apply {
            drawCircle(x0, y0, 10F, paint)
            drawRect(x0,y0-10,x0+middle,y0+10,paint)
            drawCircle(x0+middle,y0,10f,paint)
        }
    }

}