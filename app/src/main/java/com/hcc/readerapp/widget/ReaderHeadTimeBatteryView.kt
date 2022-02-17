package com.hcc.readerapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.hcc.readerapp.util.DateUtils
import com.hcc.readerapp.util.dp
import com.hcc.readerapp.util.sp
import kotlin.math.abs

class ReaderHeadTimeBatteryView constructor(context: Context,attributeSet: AttributeSet?):View(context,attributeSet) {
    private val batteryDrawable:BatteryDrawable = BatteryDrawable().apply {
        setColor("#FFA1A1A1".toColorInt())
    }
    private val timePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = "#FFA1A1A1".toColorInt()
            textSize = 12f.sp
        }
    }
    private val timeRect = Rect()

    val padding = 4.dp

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 字体 高度
        setMeasuredDimension(getDefaultSize(0,widthMeasureSpec),(getPaintTextHeight()+padding*2).toInt())

    }

    private fun getPaintTextHeight():Float{
        val height:Float
        timePaint.fontMetrics.apply {
            height = abs(top - bottom)
        }
        return height
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            val time = DateUtils.formatDatetime(System.currentTimeMillis(),"HH:mm")
            drawText(time,0f,(measuredHeight - padding - timePaint.fontMetrics.bottom -1f.dp),timePaint)
            setupBatteryBounds(time)
            batteryDrawable.draw(canvas)
        }
    }

    private fun setupBatteryBounds(time:String){
        val height = getPaintTextHeight()
        val width = 20.dp
        val diff = (height-8.dp)/2
        //计算时间矩形
        timePaint.getTextBounds(time,0,time.length,timeRect)

        val batteryLeft = timeRect.width()+6.dp
        batteryDrawable.setBounds(batteryLeft,padding+diff.toInt(),(batteryLeft+width),(padding+height-diff).toInt())

    }

    fun updateTime(){
        postInvalidate()
    }

    fun setBattery(level:Int){
        batteryDrawable.setPower(level)
        postInvalidate()

    }


}