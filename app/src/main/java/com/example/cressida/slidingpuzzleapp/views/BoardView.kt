package com.example.cressida.slidingpuzzleapp.views

import android.view.View
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.cressida.slidingpuzzleapp.logic.Block

class BoardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0): View(context, attributeSet, defstyleAttr) {

    private val ROWS = 6
    private val COLUMNS = 6
    private val BLOCKSNUM = 3
    private val ROWHEIGHT = (height / ROWS).toInt()
    private val ROWWIDTH = (width / COLUMNS).toInt()

    private var purplePaint: Paint? = null
    private var bluePaint: Paint? = null
    private var greenPaint: Paint? = null

    private val blocksDummy = ArrayList<Block>()
    private val blockRects = ArrayList<Rect>()

    init {

        blocksDummy.add(Block(0, 0, 2, false))
        blocksDummy.add(Block(0, 1, 3, false))
        blocksDummy.add(Block(1, 2, 2, true))

        generateRectsFromBlocks()
        generateColorsForRects()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // TODO: draw a level using a matrix
        for (i in 0 until BLOCKSNUM) {
            canvas?.drawRect(blockRects[i], bluePaint)
        }
    }

   /* override fun onTouchEvent(event: MotionEvent?): Boolean {

        var touchX = event?.x
        var touchY = event?.y

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> true
            // user pressed - move the block
            // get coordinates of the block
            MotionEvent.ACTION_UP -> true
            // user released - new block location
            // add change to the coordinates of the block
            MotionEvent.ACTION_MOVE -> true
            // user moved his finger - direction
            // calculate the change
            MotionEvent.ACTION_OUTSIDE -> true
            // occurred outside bounds of current screen element
            // do nothing
            else -> super.onTouchEvent(event)
            // do nothing
        }

        invalidate()
        return true // event handled
    }*/

    private fun generateRectsFromBlocks() {

        var block: Block? = null
        var rect: Rect? = null
        var left: Int? = null
        var top: Int? = null
        var right: Int? = null
        var bottom: Int? = null


        for (i in 0 until BLOCKSNUM) {

            block = blocksDummy[i]

            if (!block.vertical) {

                left = block.x * ROWWIDTH // X coordinate of the left side of the rectangle
                top = (block.y + 1) * ROWHEIGHT // Y coordinate of the top of the rectangle
                right = (block.x + block.size) * ROWWIDTH // The X coordinate of the right side of the rectangle
                bottom = block.y * ROWHEIGHT // Y coordinate of the bottom of the rectangle

            } else {

                left = block.x * ROWWIDTH
                top = (block.y + block.size) * ROWHEIGHT
                right = (block.x + 1) * ROWWIDTH
                bottom = block.y * ROWHEIGHT

            }
            rect = Rect(left, top, right, bottom)
            blockRects.add(rect)
        }
    }

    private fun generateColorsForRects() {

        purplePaint = Paint()
        bluePaint = Paint()
        greenPaint = Paint()

        purplePaint?.color = Color.MAGENTA
        bluePaint?.color = Color.BLUE
        greenPaint?.color = Color.GREEN

        purplePaint?.isAntiAlias = true
        bluePaint?.isAntiAlias = true
        greenPaint?.isAntiAlias = true

    }

}