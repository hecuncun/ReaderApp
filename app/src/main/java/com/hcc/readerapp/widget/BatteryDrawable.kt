package com.hcc.readerapp.widget

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable

class BatteryDrawable:Drawable {
    constructor()
    constructor(power:Int){
        this.power = power
    }
    private val mPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }
    private val rectF by lazy {
        RectF()
    }
    private var power = MAX_POWER_VALUE

    companion object{
        const val MAX_POWER_VALUE = 100
    }

    fun setPower(power: Int){
        this.power = 0.coerceAtLeast(power).coerceAtLeast(100.coerceAtMost(power))
        invalidateSelf()
    }

    fun setColor(color :Int){
        mPaint.color = color
        invalidateSelf()
    }


    override fun draw(canvas: Canvas) {
        val rect = bounds
        val height = rect.height()
        //画外边框
        rectF.set(rect.left.toFloat(),rect.top.toFloat(),(rect.right-height/4).toFloat(),rect.bottom.toFloat())
        mPaint.style = Paint.Style.STROKE
        canvas.drawRoundRect(rectF,2f,2f,mPaint)
        //画右边的实心矩形
        rectF.set(rectF.right,rectF.top+height/4,rectF.right+height/4,rectF.top+height*3/4f)
        mPaint.style = Paint.Style.FILL
        canvas.drawRoundRect(rectF,2f,2f,mPaint)
        //画进度矩形
        val width = rect.width()-height/4
        val padding = Resources.getSystem().displayMetrics.density
        val per = (width - padding)*power/ MAX_POWER_VALUE
        rectF.set(rect.left + padding,rect.top + padding,rect.left + per,rect.bottom - padding)
        mPaint.style = Paint.Style.FILL_AND_STROKE
        canvas.drawRoundRect(rectF,2f,0f,mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter =colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int = PixelFormat.TRANSPARENT
}