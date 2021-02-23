package com.lucers.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlin.math.abs
import kotlin.math.max

/**
 * SymbolIndicatorView
 */
class SymbolIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var symbolSize = 0

    @ColorInt
    private var symbolColor = 0

    private var currentPosition = 0

    private val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.LINEAR_TEXT_FLAG).also {
            it.style = Paint.Style.FILL
            it.textAlign = Paint.Align.CENTER
        }
    }

    var symbolList: List<String> = mutableListOf()
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SymbolIndicatorView)
            symbolSize = typedArray.getDimensionPixelSize(
                R.styleable.SymbolIndicatorView_symbolSize,
                resources.getDimensionPixelSize(R.dimen.default_text_size)
            )
            symbolColor = typedArray.getColor(
                R.styleable.SymbolIndicatorView_symbolColor,
                ContextCompat.getColor(context, R.color.default_text_color)
            )

            paint.textSize = symbolSize.toFloat()
            paint.color = symbolColor
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureWidth(widthMeasureSpec), measuredHeight(heightMeasureSpec))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        return when (widthMode) {
            MeasureSpec.EXACTLY -> {
                max(widthSize, measureTextWidth())
            }
            else -> {
                measureTextWidth()
            }
        }
    }

    private fun measureTextWidth(): Int {
        var textMaxWidth = 0
        symbolList.forEach { str ->
            val textWidth = paint.measureText(str)
            if (textWidth > textMaxWidth) {
                textMaxWidth = textWidth.toInt()
            }
        }
        return textMaxWidth + paddingLeft + paddingRight
    }

    private fun measuredHeight(heightMeasureSpec: Int): Int {
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        return when (heightMode) {
            MeasureSpec.EXACTLY -> {
                max(heightSize, measureTextHeight())
            }
            else -> {
                measureTextHeight()
            }
        }
    }

    private fun measureTextHeight(): Int {
        var textMaxHeight = 0
        val rect = Rect()
        symbolList.forEach { str ->
            paint.getTextBounds(str, 0, str.length, rect)
            val textHeight = rect.height()
            if (textHeight > textMaxHeight) {
                textMaxHeight = textHeight
            }
        }
        return textMaxHeight * symbolList.size * 2 + paddingTop + paddingBottom
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (symbolList.isEmpty()) {
            return
        }
        val x: Float = (width * 0.5).toFloat()
        val singleHeight: Float = ((height - paddingTop - paddingBottom) / symbolList.size).toFloat()
        val distance = abs(paint.ascent() + paint.descent()) / 2
        var y: Float = singleHeight / 2 + distance + paddingTop
        symbolList.forEach { str ->
            canvas.drawText(str, x, y, paint)
            y += singleHeight
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (symbolList.isEmpty() || onTouchSymbolChangedListener == null) {
            return false
        }
        val position: Int = (event.y / (height - paddingTop - paddingBottom) * symbolList.size).toInt()
        if (position >= symbolList.size) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN or MotionEvent.ACTION_MOVE -> {
                if (currentPosition != position) {
                    onTouchSymbolChangedListener!!.onTouchSymbolChanged(symbolList[position], true)
                    currentPosition = position
                }
            }
            MotionEvent.ACTION_UP or MotionEvent.ACTION_CANCEL -> {
                onTouchSymbolChangedListener!!.onTouchSymbolChanged(symbolList[position], false)
                currentPosition = 0
            }
        }
        return true
    }

    var onTouchSymbolChangedListener: OnTouchSymbolChangedListener? = null

    interface OnTouchSymbolChangedListener {
        fun onTouchSymbolChanged(symbol: String, pressed: Boolean)
    }
}