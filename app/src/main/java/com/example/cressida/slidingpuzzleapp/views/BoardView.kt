package com.example.cressida.slidingpuzzleapp.views

import android.view.View
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.example.cressida.slidingpuzzleapp.logic.Block

@Suppress("DEPRECATION")
class BoardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0) : View(context, attributeSet, defstyleAttr) {

    private val rows = 6
    private val columns = 6
    private val blocksNum = 3
    private val scale = resources.displayMetrics.density*2

    private var height = 150 * scale
    private var width = 150 * scale

    private val rowHeight = (height / rows).toInt()
    private val rowWidth = (width / columns).toInt()

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

    private var initialX:Float = (0).toFloat()
    private var initialY:Float = (0).toFloat()
    private var prevX:Float = (0).toFloat()
    private var prevY:Float = (0).toFloat()
    var rectIndex = 0
    val TAG:String = this::class.java.simpleName

    /*
        TODO:
        1. Check if actual coordinates are bigger or smaller then the initial
        2. According to step one, give or take from rect coordinates you want to move
        3. Check if the newly coordinated rect intersects with another
        4. If true -> do nothing
        5. If false -> move the rect
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        var touching: Boolean = false

        when (event?.action) {
        // gesture begins with this
            MotionEvent.ACTION_DOWN -> {
                // touch coordinates
                initialX = event.x
                initialY = event.y
                prevX = initialX
                prevY = initialY
                rectIndex = getRectIndexFor(initialX, initialY)
                touching = true
                Log.d(TAG, ("DOWN: x: $initialX, y: $initialY"))
                // only this area is redrawn
                invalidate(blockRects[rectIndex])

            }

        // this gets called when the user starts moving his finger on the screen
            MotionEvent.ACTION_MOVE -> {
                var actualX = event.x
                var actualY = event.y
                if (blocksDummy[rectIndex].vertical) {
                    var diff = actualY - prevY
                    Log.d(TAG, ("DIFF: $diff"))
                    if (diff > 0 && blockRects[rectIndex].bottom + diff.toInt() < height) {
                        blockRects[rectIndex].top += diff.toInt()
                        blockRects[rectIndex].bottom += diff.toInt()
                    }
                    if (diff < 0 && blockRects[rectIndex].top + diff.toInt() > 0) {
                        blockRects[rectIndex].top += diff.toInt()
                        blockRects[rectIndex].bottom += diff.toInt()
                    }

                } else {
                    var diff = actualX - prevX
                    Log.d(TAG, ("DIFF: $diff"))
                    if (diff > 0 && blockRects[rectIndex].right + diff.toInt() < width) {
                        blockRects[rectIndex].left += diff.toInt()
                        blockRects[rectIndex].right += diff.toInt()
                    }
                    if (diff < 0 && blockRects[rectIndex].left + diff.toInt() > 0) {
                        blockRects[rectIndex].left += diff.toInt()
                        blockRects[rectIndex].right += diff.toInt()
                    }
                }
                //generateRectsFromBlocks()
                invalidate(blockRects[rectIndex])

                prevX = actualX
                prevY = actualY
            }

        // gesture ends with this
        // so this should finish a move
        // it should call the logic for Block coordinate change
            MotionEvent.ACTION_UP -> {
                //prevX = event.x
                //prevY = event.y
                touching = false
                invalidate(blockRects[rectIndex])
            }

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

        // Reset height and width to canvas props for movement logic
        height = canvas!!.height.toFloat()
        width = canvas!!.width.toFloat()
        canvas!!.drawColor(Color.BLACK)
        DrawBlocks(canvas)
    }

    private fun DrawBlocks(canvas: Canvas) {
        for (i in 0 until blocksNum) {
            canvas!!.drawRect(blockRects[i], rectColors[i])
        }
    }

/*    private fun checkCoordinates(x: Float, y: Float): Boolean {
        if (x > initialX)
            novel
        if (x < initialX)
            csökkent
        if (y > initialY)
            novel
        if (y < initialY)
            csokkent

        return true
    }

    private fun calculateCoordinates(actual: Float, initial: Float) : Float {

        return actual - initial
    }*/

    private fun getRectIndexFor(x: Float, y: Float): Int {
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

        var block: Block?
        var rect: Rect?
        var left: Int?
        var top: Int?
        var right: Int?
        var bottom: Int?


        for (i in 0 until blocksNum) {

            block = blocksDummy[i]

            if (!block.vertical) {

                left = block.x * rowWidth // X coordinate of the left side of the rectangle
                top = (block.y + 1) * rowHeight // Y coordinate of the top of the rectangle
                right = (block.x + block.size) * rowWidth // The X coordinate of the right side of the rectangle
                bottom = block.y * rowHeight // Y coordinate of the bottom of the rectangle

            } else {

                left = block.x * rowWidth
                top = (block.y + block.size) * rowHeight
                right = (block.x + 1) * rowWidth
                bottom = block.y * rowHeight

            }
            rect = Rect(left, bottom, right, top)

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

        rectColors.add(purplePaint)
        rectColors.add(bluePaint)
        rectColors.add(greenPaint)

    }

    // preserve a squared ratio
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)

    }
}