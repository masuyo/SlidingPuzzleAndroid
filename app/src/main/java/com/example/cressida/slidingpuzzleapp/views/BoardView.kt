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
    private val scale = resources.displayMetrics.density*2

    private var height = 150 * scale
    private var width = 150 * scale

    private val rowHeight = (height / rows).toInt()
    private val rowWidth = (width / columns).toInt()

    private var purplePaint: Paint? = null
    private var bluePaint: Paint? = null
    private var greenPaint: Paint? = null
    private val cPaint: Paint? = Paint()

    private val commonColor: Paint? = null
    private val finisherColor: Paint? = null
    //private var horizontalThreeImage: ImagePattern? = null

    private val blocksDummy = ArrayList<Block>()
    private val blockRects = ArrayList<Rect>()
    private val rectColors = ArrayList<Paint?>()

    init {

        blocksDummy.add(Block(0, 0, 2, false))
        blocksDummy.add(Block(0, 1, 3, false))
        blocksDummy.add(Block(1, 2, 2, true))
        blocksDummy.add(Block(4, 3, 2, false))

        generateRectsFromBlocks()
        generateColorsForRects()
    }

    private var initialX:Float = (0).toFloat()
    private var initialY:Float = (0).toFloat()
    private var prevX:Float = (0).toFloat()
    private var prevY:Float = (0).toFloat()
    private var rectIndex = 0
    private val TAG:String = this::class.java.simpleName

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        var touching: Boolean = false

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
                prevX = initialX
                prevY = initialY
                rectIndex = getRectIndexFor(initialX, initialY)
                touching = true
                Log.d(TAG, ("DOWN: x: $initialX, y: $initialY"))
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

            // it should call the logic for Block coordinate change
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
        //canvas!!.drawColor(Color.BLACK)
        drawBlocks(canvas)
    }

    private fun drawBlocks(canvas: Canvas) {
        for (i in 0 until blockRects.size) {
            for (j in 0 until 2) {
                generateFillThenStroke(j)
                canvas!!.drawRect(blockRects[i], cPaint)
            }
        }
    }

    private fun checkForHorizontalChangeAndInvalidate(diff: Float) {
        var movingRect = blockRects[rectIndex]
        var rect = Rect(movingRect.left, movingRect.top, movingRect.right, movingRect.bottom)
        rect.left += diff.toInt()
        rect.right += diff.toInt()

        if (!isRectIntersectsAnother(rect, rectIndex)) {
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

        if (!isRectIntersectsAnother(rect, rectIndex)) {
            blockRects[rectIndex].top += diff.toInt()
            blockRects[rectIndex].bottom += diff.toInt()
            invalidate(blockRects[rectIndex])
        }
    }

    private fun isRectIntersectsAnother(rect: Rect, rectIndex: Int): Boolean {
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
        rectColors.add(purplePaint)
        rectColors.add(bluePaint)
        rectColors.add(greenPaint)


        cPaint?.isAntiAlias = true
        cPaint?.style = Paint.Style.STROKE
        cPaint?.color = Color.BLACK
        cPaint?.strokeWidth = 5f

    }

    private fun generateFillThenStroke(i: Int) {
        cPaint?.isAntiAlias = true
        if (i == 0) {
            cPaint?.style = Paint.Style.FILL
            cPaint?.color = Color.BLACK
        } else {
            cPaint?.style = Paint.Style.STROKE
            cPaint?.color = Color.RED
            cPaint?.strokeWidth = 10f
        }
    }

    // preserve a squared ratio
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }
}