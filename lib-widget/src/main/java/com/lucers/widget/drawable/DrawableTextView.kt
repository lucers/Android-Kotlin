package com.lucers.widget.drawable

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import com.lucers.widget.R

/**
 * DrawableTextView
 *
 * @author Lucers
 */
class DrawableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    private var drawableSize = 0
    private var drawableLeftWidth = 0
    private var drawableLeftHeight = 0
    private var drawableTopWidth = 0
    private var drawableTopHeight = 0
    private var drawableRightWidth = 0
    private var drawableRightHeight = 0
    private var drawableBottomWidth = 0
    private var drawableBottomHeight = 0
    private var drawableLeft: Drawable? = null
    private var drawableTop: Drawable? = null
    private var drawableRight: Drawable? = null
    private var drawableBottom: Drawable? = null

    private var textRect: Rect = Rect()

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DrawableTextView)
            val indexCount = typedArray.indexCount
            for (i in 0 until indexCount) {
                when (val index = typedArray.getIndex(i)) {
                    R.styleable.DrawableTextView_drawableSize -> {
                        drawableSize = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableLeftWidth -> {
                        drawableLeftWidth = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableLeftHeight -> {
                        drawableLeftHeight = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableTopWidth -> {
                        drawableTopWidth = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableTopHeight -> {
                        drawableTopHeight = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableRightWidth -> {
                        drawableRightWidth = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableRightHeight -> {
                        drawableRightHeight = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableBottomWidth -> {
                        drawableBottomWidth = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableBottomHeight -> {
                        drawableBottomHeight = typedArray.getDimensionPixelSize(index, 0)
                    }
                    R.styleable.DrawableTextView_drawableLeft -> {
                        drawableLeft = typedArray.getDrawable(index)
                    }
                    R.styleable.DrawableTextView_drawableTop -> {
                        drawableTop = typedArray.getDrawable(index)
                    }
                    R.styleable.DrawableTextView_drawableRight -> {
                        drawableRight = typedArray.getDrawable(index)
                    }
                    R.styleable.DrawableTextView_drawableBottom -> {
                        drawableBottom = typedArray.getDrawable(index)
                    }
                }
            }

            drawableLeftWidth = if (drawableLeftWidth == 0) drawableSize else drawableLeftWidth
            drawableLeftHeight = if (drawableLeftHeight == 0) drawableSize else drawableLeftHeight
            drawableTopWidth = if (drawableTopWidth == 0) drawableSize else drawableTopWidth
            drawableTopHeight = if (drawableTopHeight == 0) drawableSize else drawableTopHeight
            drawableRightWidth = if (drawableRightWidth == 0) drawableSize else drawableRightWidth
            drawableRightHeight = if (drawableRightHeight == 0) drawableSize else drawableRightHeight
            drawableBottomWidth = if (drawableBottomWidth == 0) drawableSize else drawableBottomWidth
            drawableBottomHeight = if (drawableBottomHeight == 0) drawableSize else drawableBottomHeight

            typedArray.recycle()
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
        }
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        left?.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight)
        top?.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight)
        right?.setBounds(0, 0, drawableRightWidth, drawableRightHeight)
        bottom?.setBounds(0, 0, drawableBottomWidth, drawableBottomHeight)
        setCompoundDrawables(left, top, right, bottom)
    }

    fun setDrawableLeft(@DrawableRes drawableLeft: Int) {
        setDrawableLeft(AppCompatResources.getDrawable(context, drawableLeft))
    }

    fun setDrawableTop(@DrawableRes drawableTop: Int) {
        setDrawableTop(AppCompatResources.getDrawable(context, drawableTop))
    }

    fun setDrawableRight(@DrawableRes drawableRight: Int) {
        setDrawableRight(AppCompatResources.getDrawable(context, drawableRight))
    }

    fun setDrawableBottom(@DrawableRes drawableBottom: Int) {
        setDrawableBottom(AppCompatResources.getDrawable(context, drawableBottom))
    }

    fun setDrawableLeft(drawableLeft: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    }

    fun setDrawableTop(drawableTop: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    }

    fun setDrawableRight(drawableRight: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    }

    fun setDrawableBottom(drawableBottom: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
    }
}