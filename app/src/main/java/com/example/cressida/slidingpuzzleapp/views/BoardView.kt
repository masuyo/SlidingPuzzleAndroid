package com.example.cressida.slidingpuzzleapp.views

import android.view.View
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.logic.Block
import com.example.cressida.slidingpuzzleapp.logic.Board

@Suppress("DEPRECATION")
class BoardView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defstyleAttr: Int = 0) : View(context, attributeSet, defstyleAttr) {

    private val rows = 6
    private val columns = 6
    private val scale = resources.displayMetrics.density*2

    private var height = 150 * scale
    private var width = 150 * scale

    private val rowHeight = (height / rows).toInt()
    private val rowWidth = (width / columns).toInt()

    private var blocksDummy = ArrayList<Block>()
    private val blockRects = ArrayList<Rect>()

    private var horizontalTwoImg: Bitmap? = null
    private var horizontalThreeImg: Bitmap? = null
    private var verticalTwoImg: Bitmap? = null
    private var verticalThreeImg: Bitmap? = null
    private var finisherImg: Bitmap? = null

    private var prevX:Float = (0).toFloat()
    private var prevY:Float = (0).toFloat()
    private var rectIndex = 0

    init {



        blocksDummy.add(Block(0, 0, 2, false))
        blocksDummy.add(Block(0, 1, 3, false))
        blocksDummy.add(Block(1, 2, 2, true))
        blocksDummy.add(Block(4, 3, 2, false))

        horizontalTwoImg = BitmapFactory.decodeResource(resources, R.drawable.horizontal_2)
        horizontalThreeImg = BitmapFactory.decodeResource(resources, R.drawable.horizontal_3)
        verticalTwoImg = BitmapFactory.decodeResource(resources, R.drawable.vertical_2)
        verticalThreeImg = BitmapFactory.decodeResource(resources, R.drawable.vertical_3)
        finisherImg = BitmapFactory.decodeResource(resources, R.drawable.finisher)

        generateRectsFromBlocks()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                prevX = event.x
                prevY = event.y
                rectIndex = getRectIndexFor(prevX, prevY)
            }

            MotionEvent.ACTION_MOVE -> {

                var actualX = event.x
                var actualY = event.y

                if (rectIndex != -1) {
                    if (blocksDummy[rectIndex].vertical) {
                        var diff = actualY - prevY
                        var staysWithinTopBoundary = (diff < 0 && blockRects[rectIndex].top + diff.toInt() > 0)
                        var staysWithinBottomBoundary = (diff > 0 && blockRects[rectIndex].bottom + diff.toInt() < height)
                        if (staysWithinTopBoundary || staysWithinBottomBoundary) {
                            this.checkForVerticalChangeAndInvalidate(diff)
                        }
                    } else {
                        var diff = actualX - prevX
                        var staysWithinLeftBoundary = (diff > 0 && blockRects[rectIndex].right + diff.toInt() < width)
                        var staysWithingRightBoundary = (diff < 0 && blockRects[rectIndex].left + diff.toInt() > 0)
                        if (staysWithinLeftBoundary || staysWithingRightBoundary) {
                            this.checkForHorizontalChangeAndInvalidate(diff)
                        }
                    }
                }
                prevX = actualX
                prevY = actualY
            }

            MotionEvent.ACTION_UP -> {

            }
            else -> super.onTouchEvent(event)
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        height = canvas!!.height.toFloat()
        width = canvas!!.width.toFloat()

        drawBlocks(canvas)
    }

    private fun drawBlocks(canvas: Canvas) {
        var blockImg: Bitmap? = null
        for (i in 0 until blockRects.size) {
            if (blocksDummy[i].vertical) {
                if (blocksDummy[i].size == 2)
                    blockImg = verticalTwoImg
                if (blocksDummy[i].size == 3)
                    blockImg = verticalThreeImg
            } else {
                if (blocksDummy[i].size == 2)
                    blockImg = horizontalTwoImg
                if (blocksDummy[i].size == 3)
                    blockImg = horizontalThreeImg
            }
            canvas.drawBitmap(blockImg, Rect(0,0,blockImg!!.width, blockImg!!.height), blockRects[i], null)
        }
    }

    private fun checkForHorizontalChangeAndInvalidate(diff: Float) {
        var movingRect = blockRects[rectIndex]
        var rect = Rect(movingRect.left, movingRect.top, movingRect.right, movingRect.bottom)
        rect.left += diff.toInt()
        rect.right += diff.toInt()

        if (!rectIntersectsAnother(rect, rectIndex)) {
            blockRects[rectIndex].left += diff.toInt()
            blockRects[rectIndex].right += diff.toInt()
            invalidate(blockRects[rectIndex])
        }
    }

    private fun checkForVerticalChangeAndInvalidate(diff: Float) {
        var movingRect = blockRects[rectIndex]
        var rect = Rect(movingRect.left, movingRect.top, movingRect.right, movingRect.bottom)
        rect.top += diff.toInt()
        rect.bottom += diff.toInt()

        if (!rectIntersectsAnother(rect, rectIndex)) {
            blockRects[rectIndex].top += diff.toInt()
            blockRects[rectIndex].bottom += diff.toInt()
            invalidate(blockRects[rectIndex])
        }
    }

    private fun rectIntersectsAnother(rect: Rect, rectIndex: Int): Boolean {
        for (i in 0 until blockRects.size) {
            if (i != rectIndex) {
                if (rect.intersect(blockRects[i])) {
                    return true
                }
            }
        }
        return false
    }

    private fun getRectIndexFor(x: Float, y: Float): Int {
        for (i in 0 until blockRects.size) {
            var cont = blockRects[i].contains(x.toInt(), y.toInt())
            if (cont) {

                return i
            }
        }
        return -1
    }

    private fun generateRectsFromBlocks() {

        var block: Block?
        var rect: Rect?
        var left: Int?
        var top: Int?
        var right: Int?
        var bottom: Int?


        for (i in 0 until blocksDummy.size) {

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

    // preserve a squared ratio
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }
}