package com.lucers.widget.drawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatEditText
import com.lucers.widget.R

/**
 * DrawableEditText
 *
 * @author Lucers
 */
class DrawableEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle) {

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

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DrawableEditText)

            drawableSize = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableSize,
                drawableSize
            )

            drawableLeft = typedArray.getDrawable(R.styleable.DrawableTextView_drawableLeft)
            drawableLeftWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableLeftWidth,
                drawableLeftWidth
            )
            drawableLeftHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableLeftHeight,
                drawableLeftHeight
            )

            drawableTop = typedArray.getDrawable(R.styleable.DrawableTextView_drawableTop)
            drawableTopWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableTopWidth,
                drawableTopWidth
            )
            drawableTopHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableTopHeight,
                drawableTopHeight
            )

            drawableRight = typedArray.getDrawable(R.styleable.DrawableTextView_drawableRight)
            drawableRightWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableRightWidth,
                drawableRightWidth
            )
            drawableRightHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableRightHeight,
                drawableRightHeight
            )

            drawableBottom = typedArray.getDrawable(R.styleable.DrawableTextView_drawableBottom)
            drawableBottomWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_drawableBottomWidth,
                drawableBottomWidth
            )
            drawableBottomHeight =
                typedArray.getDimensionPixelSize(
                    R.styleable.DrawableTextView_drawableBottomHeight,
                    drawableBottomHeight
                )

            drawableLeftWidth = if (drawableLeftWidth == 0) drawableSize else drawableLeftWidth
            drawableLeftHeight = if (drawableLeftHeight == 0) drawableSize else drawableLeftHeight
            drawableTopWidth = if (drawableTopWidth == 0) drawableSize else drawableTopWidth
            drawableTopHeight = if (drawableTopHeight == 0) drawableSize else drawableTopHeight
            drawableRightWidth = if (drawableRightWidth == 0) drawableSize else drawableRightWidth
            drawableRightHeight =
                if (drawableRightHeight == 0) drawableSize else drawableRightHeight
            drawableBottomWidth =
                if (drawableBottomWidth == 0) drawableSize else drawableBottomWidth
            drawableBottomHeight =
                if (drawableBottomHeight == 0) drawableSize else drawableBottomHeight

            typedArray.recycle()
            setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                drawableTop,
                drawableRight,
                drawableBottom
            )
        }
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        left?.setBounds(
            0,
            0,
            if (drawableLeftWidth == 0) left.intrinsicWidth else drawableLeftWidth,
            if (drawableLeftHeight == 0) left.intrinsicHeight else drawableLeftHeight
        )
        top?.setBounds(
            0,
            0,
            if (drawableTopWidth == 0) top.intrinsicWidth else drawableTopWidth,
            if (drawableTopHeight == 0) top.intrinsicHeight else drawableTopHeight
        )
        right?.setBounds(
            0,
            0,
            if (drawableRightWidth == 0) right.intrinsicWidth else drawableRightWidth,
            if (drawableRightHeight == 0) right.intrinsicHeight else drawableRightHeight
        )
        bottom?.setBounds(
            0,
            0,
            if (drawableBottomWidth == 0) bottom.intrinsicWidth else drawableBottomWidth,
            if (drawableBottomHeight == 0) bottom.intrinsicHeight else drawableBottomHeight
        )
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
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }

    fun setDrawableTop(drawableTop: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }

    fun setDrawableRight(drawableRight: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }

    fun setDrawableBottom(drawableBottom: Drawable?) {
        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )
    }
}