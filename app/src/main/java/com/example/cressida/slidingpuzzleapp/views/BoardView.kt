package com.example.cressida.slidingpuzzleapp.views

import android.app.Activity
import android.view.View
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import com.example.cressida.slidingpuzzleapp.logic.Block

class BoardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0) : View(context, attributeSet, defstyleAttr) {

    private val ROWS = 6
    private val COLUMNS = 6
    private val BLOCKSNUM = 3
    private val scale = resources.displayMetrics.density*2;

    private val height = 150 * scale
    private val width = 150 * scale

    private val ROWHEIGHT = (height / ROWS).toInt()
    private val ROWWIDTH = (width / COLUMNS).toInt()

    private var purplePaint: Paint? = null
    private var bluePaint: Paint? = null
    private var greenPaint: Paint? = null

    private val blocksDummy = ArrayList<Block>()
    private val blockRects = ArrayList<Rect>()
    private val rectColors = ArrayList<Paint?>()

    init {

        blocksDummy.add(Block(0, 0, 2, false))
        blocksDummy.add(Block(0, 1, 3, false))
        blocksDummy.add(Block(1, 2, 2, true))

        generateRectsFromBlocks()
        generateColorsForRects()
    }

    private var initialX:Float = ROWS.toFloat()
    private var initialY:Float = ROWS.toFloat()
    val TAG:String = this::class.java.simpleName

    override fun onTouchEvent(event: MotionEvent?): Boolean {


        var rectIndex = 0
        var touching: Boolean = false


        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
                rectIndex = getRectIndexFor(initialX!!.toFloat(), initialY!!.toFloat())
                touching = true
                Log.d(TAG, ("DOWN: x: $initialX, y: $initialY"))
                invalidate(blockRects[rectIndex])

            }

        // user pressed - move the block
        // get coordinates of the block
            MotionEvent.ACTION_UP -> {
                var finalX = event.x
                var finalY = event.y
                touching = false
                invalidate(blockRects[rectIndex])
            }
        // user released - new block location
        // add change to the coordinates of the block
/*            MotionEvent.ACTION_MOVE -> {
                blocksDummy[0].x = 3
                generateRectsFromBlocks()
                invalidate()
            }*/
        // user moved his finger - direction
        // calculate the change

/*            MotionEvent.ACTION_OUTSIDE -> {
                blocksDummy[0].x = 3
                generateRectsFromBlocks()
                invalidate()
            }*/
        // occurred outside bounds of current screen element
        // do nothing*/
            else -> super.onTouchEvent(event)
        // do nothing
        }

        return true // event handled
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawColor(Color.BLACK)
        DrawBlocks(canvas)
    }

    private fun DrawBlocks(canvas: Canvas) {
        for (i in 0 until BLOCKSNUM) {
            canvas!!.drawRect(blockRects[i], rectColors[i])
        }
    }

    fun getRectIndexFor(x: Float, y: Float): Int {
        var xint = x.toInt()
        var yint = y.toInt()
        Log.d(TAG, ("Coordinates in int: x: $xint, y: $yint"))
        for (i in 0 until 3) {
            var cont = blockRects[i].contains(x.toInt(), y.toInt())
            if (cont) {

                return i
            }
            var left = blockRects[i].left
            var top = blockRects[i].top
            var right = blockRects[i].right
            var bottom = blockRects[i].bottom
            Log.d(TAG, ("$i RECTDOWN: Left: $left, Top: $top, Right: $right, Bottom: $bottom, Contains: $cont"))
        }
        return -1 // x, y do not lie in our view
    }

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

            blockRects!!.add(rect)
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

        rectColors.add(purplePaint)
        rectColors.add(bluePaint)
        rectColors.add(greenPaint)

    }

/*    // preserve a squared ratio
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)

    }*/
}