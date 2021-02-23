package com.lucers.widget.ovonicProgressBar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.lucers.widget.R

/**
 * OvonicProgressBar
 *
 * @author Lucers
 * @date 2019/4/12
 */
class BothWayProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var totalProgress = 0
    private var lowProgress = 0
    private var highProgress = 0

    @ColorInt
    private var backgroundLineColor = Color.parseColor("#CCCCCC")

    @ColorInt
    private var progressLineColor = Color.parseColor("#5AB4FF")

    @ColorInt
    private var progressTextColor = Color.parseColor("#333333")

    private val linePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.style = Paint.Style.FILL_AND_STROKE
        }
    }

    private val thumbPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).also {
            it.style = Paint.Style.FILL_AND_STROKE
        }
    }

    private val textPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.style = Paint.Style.FILL_AND_STROKE
        }
    }

    private var thumbSize = 0f
    private var lineSize = 0f
    private var textMargin = 0f
    private var textHeight = 0f
    private var progressTextSize = 0f
    private var unitSize = 0f
    private var onProgressChangListener: OnProgressChangListener? = null

    init {
        initAttrs(attrs)
        initPaint()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null || context == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BothWayProgressBar)
        backgroundLineColor = typedArray.getColor(R.styleable.BothWayProgressBar_backgroundLineColor, backgroundLineColor)
        progressLineColor = typedArray.getColor(R.styleable.BothWayProgressBar_progressLineColor, progressLineColor)
        progressTextColor = typedArray.getColor(R.styleable.BothWayProgressBar_textColor, progressTextColor)
        thumbSize =
            typedArray.getDimension(R.styleable.BothWayProgressBar_thumbSize, resources.getDimensionPixelOffset(R.dimen.progress_bar_thumb_size).toFloat())
        lineSize = typedArray.getDimension(R.styleable.BothWayProgressBar_lineSize, resources.getDimensionPixelOffset(R.dimen.progress_bar_line_size).toFloat())
        progressTextSize =
            typedArray.getDimension(R.styleable.BothWayProgressBar_textSize, resources.getDimensionPixelOffset(R.dimen.default_text_size).toFloat())
        textMargin =
            typedArray.getDimension(R.styleable.BothWayProgressBar_textMargin, resources.getDimensionPixelOffset(R.dimen.progress_bar_text_margin).toFloat())
        lowProgress = typedArray.getInt(R.styleable.BothWayProgressBar_lowProgress, lowProgress)
        highProgress = typedArray.getInt(R.styleable.BothWayProgressBar_highProgress, highProgress)
        totalProgress = typedArray.getInt(R.styleable.BothWayProgressBar_totalProgress, totalProgress)
        typedArray.recycle()
    }

    private fun initPaint() {
        textPaint.textSize = progressTextSize
        textPaint.color = progressTextColor

        thumbPaint.color = Color.parseColor("#5AB4FF")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val barHeight = lineSize.coerceAtLeast(thumbSize)
        textHeight = textPaint.descent() - textPaint.ascent()
        val contentHeight = barHeight + textHeight + textMargin
        setMeasuredDimension(widthMeasureSpec, measureHeight(contentHeight.toInt(), heightMeasureSpec))
    }

    private fun measureHeight(height: Int, heightMeasureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        var result: Int
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = height + paddingTop + paddingBottom
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        result = Math.max(result, suggestedMinimumHeight)
        return result
    }

    private var lowTouch = false
    private var highTouch = false
    private var startX = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val downX = event.x
                val downY = event.y
                lowTouch = lowRegion!!.contains(downX.toInt(), downY.toInt())
                highTouch = highRegion!!.contains(downX.toInt(), downY.toInt())
                if (lowTouch || highTouch) {
                    startX = downX
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (lowTouch && highTouch) {
                    if (event.x - startX > 0) {
                        lowTouch = false
                    } else {
                        highTouch = false
                    }
                }
                if (lowTouch) {
                    computerCurrentLowProgress(event.x)
                    if (onProgressChangListener != null) {
                        onProgressChangListener!!.onProgressChange(lowProgress, highProgress)
                    }
                }
                if (highTouch) {
                    computerCurrentHighProgress(event.x)
                    if (onProgressChangListener != null) {
                        onProgressChangListener!!.onProgressChange(lowProgress, highProgress)
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun computerCurrentHighProgress(x: Float) {
        val bounds = highRegion!!.bounds
        val move = x - bounds.left
        val progress = (move / unitSize).toInt()
        highProgress += progress
        if (highProgress < lowProgress) {
            highProgress = lowProgress
        }
        if (highProgress > totalProgress) {
            highProgress = totalProgress
        }
        invalidate()
    }

    private fun computerCurrentLowProgress(x: Float) {
        val bounds = lowRegion!!.bounds
        val move = x - bounds.left
        val progress = (move / unitSize).toInt()
        lowProgress += progress
        if (lowProgress > highProgress) {
            lowProgress = highProgress
        }
        if (lowProgress < 0) {
            lowProgress = 0
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        computerUnitSize()
        drawBackgroundLine(canvas)
        drawProgressLine(canvas)
        drawLowThumb(canvas)
        drawHighThumb(canvas)
        drawLowProgressText(canvas)
        drawHighProgressText(canvas)
    }

    private fun drawBackgroundLine(canvas: Canvas) {
        linePaint.color = backgroundLineColor
        val left = paddingLeft + thumbSize / 2
        val top = (height shr 1) - lineSize / 2 + textHeight / 2 + textMargin / 2
        val right = width - paddingRight - thumbSize / 2
        val bottom = top + lineSize
        canvas.drawRoundRect(RectF(left, top, right, bottom), (height shr 1).toFloat(), (height shr 1).toFloat(), linePaint)
    }

    private fun drawProgressLine(canvas: Canvas) {
        linePaint.color = progressLineColor
        val left = paddingLeft + thumbSize / 2 + unitSize * lowProgress
        val top = (height shr 1) - lineSize / 2 + textHeight / 2 + textMargin / 2
        val right = width - paddingRight - thumbSize / 2 - unitSize * (totalProgress - highProgress)
        val bottom = top + lineSize
        canvas.drawRoundRect(RectF(left, top, right, bottom), (height shr 1).toFloat(), (height shr 1).toFloat(), linePaint)
    }

    private fun computerUnitSize() {
        if (totalProgress < 0 || highProgress < 0 || lowProgress < 0 || lowProgress > highProgress || highProgress > totalProgress) {
            throw RuntimeException("progress error-->lowProgress:$lowProgress,highProgress:$highProgress,totalProgress$totalProgress")
        }
        if (totalProgress <= 0) {
            return
        }
        unitSize = (width - paddingLeft - paddingRight - thumbSize) / totalProgress
    }

    private fun drawLowProgressText(canvas: Canvas) {
        val progressStr = lowProgress.toString()
        val textLeft = paddingLeft + thumbSize / 2
        canvas.drawText(progressStr, textLeft, textHeight, textPaint)
    }

    private fun drawHighProgressText(canvas: Canvas) {
        val progressStr = highProgress.toString()
        val textLeft = width - paddingRight - textPaint.measureText(progressStr) - thumbSize / 2
        canvas.drawText(progressStr, textLeft, textHeight, textPaint)
    }

    private var lowRegion: Region? = null

    private fun drawLowThumb(canvas: Canvas) {
        val lowPath = Path()
        val left = paddingLeft + unitSize * lowProgress
        val top = paddingTop + textHeight + textMargin
        lowPath.addCircle(left + thumbSize / 2, top + thumbSize / 2, thumbSize / 2, Path.Direction.CW)
        canvas.drawPath(lowPath, thumbPaint)
        lowRegion = updateRegion(lowPath)
    }

    private var highRegion: Region? = null

    private fun drawHighThumb(canvas: Canvas) {
        val highPath = Path()
        val left = width - paddingRight - unitSize * (totalProgress - highProgress)
        val top = paddingTop + textHeight + textMargin
        highPath.addCircle(left - thumbSize / 2, top + thumbSize / 2, thumbSize / 2, Path.Direction.CW)
        canvas.drawPath(highPath, thumbPaint)
        highRegion = updateRegion(highPath)
    }

    private fun updateRegion(path: Path?): Region {
        val region = Region()
        if (path != null) {
            val rectF = RectF()
            path.computeBounds(rectF, true)
            region.setPath(path, Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt()))
            region.bounds
        }
        return region
    }

    fun setOnProgressChangListener(onProgressChangListener: OnProgressChangListener?) {
        this.onProgressChangListener = onProgressChangListener
    }

    fun setProgress(startProgress: Int, endProgress: Int) {
        lowProgress = startProgress
        highProgress = endProgress
        invalidate()
    }
}