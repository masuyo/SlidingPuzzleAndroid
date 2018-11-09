package com.example.cressida.slidingpuzzleapp.views

import android.graphics.Canvas
import android.view.View
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet

class LevelView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0): View(context, attributeSet, defstyleAttr) {

    private val NUMBER_OF_ROWS = 6
    private val NUMBER_OF_COLUMNS = 6

    private var line: Path? = null
    private var paint: Paint? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val widthStep = width / NUMBER_OF_COLUMNS
        val heightStep = height / NUMBER_OF_ROWS
    }

}