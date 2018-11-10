package com.example.cressida.slidingpuzzleapp.views

import android.graphics.Canvas
import android.view.View
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent

class LevelView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0): View(context, attributeSet, defstyleAttr) {

    private val ROWS = 6
    private val COLUMNS = 6

    private var _block: Path? = null
    private var _paint: Paint? = null

    init {
        _paint?.color = Color.BLUE
        _paint?.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val widthStep = width / COLUMNS
        val heightStep = height / ROWS

        // TODO: draw a level using a matrix
        canvas?.drawPath(_block, _paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        var touchX = event?.x
        var touchY = event?.y

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> true
            // if block can move down, move it down
            MotionEvent.ACTION_UP -> true
            // if block can move up, move it up
            MotionEvent.ACTION_MOVE -> true
            else -> true
            // do nothing
        }

        invalidate()
        return true // event handled
    }


}