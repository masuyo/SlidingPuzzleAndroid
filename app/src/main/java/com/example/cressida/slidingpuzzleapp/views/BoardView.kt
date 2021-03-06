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
    private val scale = resources.displayMetrics.density * 2

    private var height = 150 * scale
    private var width = 150 * scale

    private val rowHeight = (height / rows).toInt()
    private val rowWidth = (width / columns).toInt()
    private val padding = 3

    private var blocksDummy = ArrayList<Block>()
    private val blockRects = ArrayList<Rect>()
    private var exitRect: Rect = Rect()

    private var horizontalTwoImg: Bitmap? = null
    private var horizontalThreeImg: Bitmap? = null
    private var verticalTwoImg: Bitmap? = null
    private var verticalThreeImg: Bitmap? = null
    private var finisherImg: Bitmap? = null

    private var initialX: Float = (0).toFloat()
    private var initialY: Float = (0).toFloat()
    private var prevX: Float = (0).toFloat()
    private var prevY: Float = (0).toFloat()
    private var rectIndex = 0
    private var board: Board? = null

    init {

        this.horizontalTwoImg = BitmapFactory.decodeResource(resources, R.drawable.horizontal_2)
        this.horizontalThreeImg = BitmapFactory.decodeResource(resources, R.drawable.horizontal_3)
        this.verticalTwoImg = BitmapFactory.decodeResource(resources, R.drawable.vertical_2)
        this.verticalThreeImg = BitmapFactory.decodeResource(resources, R.drawable.vertical_3)
        this.finisherImg = BitmapFactory.decodeResource(resources, R.drawable.finisher)

    }

    fun load(board: Board) {

        this.blocksDummy = board.table
        this.board = board
        this.generateRectsFromBlocks()
        this.generateExit()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
                prevX = initialX
                prevY = initialY
                rectIndex = getRectIndexFor(prevX, prevY)
            }

            MotionEvent.ACTION_MOVE -> {

                var actualX = event.x
                var actualY = event.y

                if (rectIndex != -1) {
                    this.checkForVerticalAndCallForBoundaryCheck(actualX, actualY)
                }
                prevX = actualX
                prevY = actualY
            }

            MotionEvent.ACTION_UP -> {

                if (rectIndex != -1) {
                    this.board!!.move()
                    if (rectIndex == 0) {
                        if (finisherIntersectsWithExit()) {
                            this.finish()
                        }
                    }
                }


            }
            else -> super.onTouchEvent(event)
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        this.height = canvas!!.height.toFloat()
        this.width = canvas!!.width.toFloat()

        this.drawBlocks(canvas)
    }

    private fun drawBlocks(canvas: Canvas) {
        var transPaint = Paint()
        transPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRect(exitRect, transPaint)
        var blockImg: Bitmap? = null
        canvas.drawBitmap(finisherImg, Rect(0, 0, finisherImg!!.width, finisherImg!!.height), blockRects[0], null)
        for (i in 1 until blockRects.size) {
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
            canvas.drawBitmap(blockImg, Rect(0, 0, blockImg!!.width, blockImg!!.height), blockRects[i], null)
        }

    }

    private fun finish() {
        this.board!!.gameOver()
    }


    private fun finisherIntersectsWithExit(): Boolean{
        if (blockRects[0].intersect(exitRect)) {
            return true
        }
        return false
    }

    private fun checkForVerticalAndCallForBoundaryCheck(actualX: Float, actualY: Float) {
        if (blocksDummy[rectIndex].vertical) {
            var diff = actualY - prevY
            this.checkForHorizontalBoundariesAndCallForChange(diff)
        } else {
            var diff = actualX - prevX
            this.checkForVerticalBoundariesAndCallForChange(diff)
        }
    }

    private fun checkForHorizontalBoundariesAndCallForChange(diff: Float) {
        var staysWithinTopBoundary = (diff < 0 && blockRects[rectIndex].top + diff.toInt() > 0)
        var staysWithinBottomBoundary = (diff > 0 && blockRects[rectIndex].bottom + diff.toInt() < height)
        if (staysWithinTopBoundary || staysWithinBottomBoundary) {
            this.checkForVerticalChangeAndInvalidate(diff)
        }
    }

    private fun checkForVerticalBoundariesAndCallForChange(diff: Float) {
        var staysWithinLeftBoundary = (diff > 0 && blockRects[rectIndex].right + diff.toInt() < width)
        var staysWithingRightBoundary = (diff < 0 && blockRects[rectIndex].left + diff.toInt() > 0)
        if (staysWithinLeftBoundary || staysWithingRightBoundary) {
            this.checkForHorizontalChangeAndInvalidate(diff)
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

    private fun generateExit() {
        var left = board!!.exit.x * rowWidth  + padding
        var bottom = (board!!.exit.y + board!!.exit.size) * rowHeight - padding
        var right = (board!!.exit.x + 1) * rowWidth - padding
        var top = board!!.exit.y * rowHeight + padding
        exitRect = Rect(left, top, right, bottom)
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

                left = block.x * rowWidth + padding// X coordinate of the left side of the rectangle
                bottom = (block.y + 1) * rowHeight - padding// Y coordinate of the top of the rectangle
                right = (block.x + block.size) * rowWidth - padding // The X coordinate of the right side of the rectangle
                top = block.y * rowHeight + padding// Y coordinate of the bottom of the rectangle

            } else {

                left = block.x * rowWidth  + padding
                bottom = (block.y + block.size) * rowHeight - padding
                right = (block.x + 1) * rowWidth - padding
                top = block.y * rowHeight + padding

            }
            rect = Rect(left, top, right, bottom)

            blockRects.add(rect)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width.toInt(), height.toInt())
    }
}