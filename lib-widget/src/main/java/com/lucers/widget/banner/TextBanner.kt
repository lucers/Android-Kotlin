package com.lucers.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextSwitcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lucers.widget.R
import com.lucers.widget.drawable.DrawableTextView


/**
 * TextBanner
 */
class TextBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextSwitcher(context, attrs), LifecycleObserver {

    private var textSize = 0
    private var textColor = 0
    private var textGravity = 0

    private var position = 0

    private var texts: Array<CharSequence>? = null

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBanner)
        val indexCount = typedArray.indexCount
        textSize = context.resources.getDimensionPixelOffset(R.dimen.default_text_size)
        textColor = ContextCompat.getColor(context, R.color.default_text_color)
        for (i in 0 until indexCount) {
            when (val index = typedArray.getIndex(i)) {
                R.styleable.TextBanner_textSize -> {
                    textSize = typedArray.getDimensionPixelSize(index, textSize)
                }
                R.styleable.TextBanner_textColor -> {
                    textColor = typedArray.getColor(index, textColor)
                }
                R.styleable.TextBanner_textGravity -> {
                    textGravity = typedArray.getInt(index, textGravity)
                }
            }
        }
        typedArray.recycle()
        textGravity = when (textGravity) {
            1 -> Gravity.TOP
            2 -> Gravity.BOTTOM
            3 -> Gravity.START
            4 -> Gravity.END
            5 -> Gravity.CENTER_HORIZONTAL
            6 -> Gravity.CENTER_VERTICAL
            else -> Gravity.CENTER
        }
    }

    fun setData(texts: Array<CharSequence>?) {
        this.texts = texts
        // 1:setFactory
        setFactory {
            val textView = DrawableTextView(context)
            textView.layoutParams = LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            textView.gravity = Gravity.CENTER
            textView.setTextColor(textColor)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            textView
        }
        // 2:setText
        this.texts?.let {
            if (it.isNotEmpty()) {
                setText(it[position])
            }
        }
        // 3:setAnimation
        setInAnimation(context, R.anim.anim_text_banner_vertical_in)
        setOutAnimation(context, R.anim.anim_text_banner_vertical_out)
    }

    private val textRunnable = Runnable {
        texts?.let {
            if (it.isNotEmpty()) {
                position++
                setText(it[position % it.size])
                startRotation()
            }
        }
    }

    fun startRotation() {
        texts?.let {
            stopRotation()
            handler?.postDelayed(textRunnable, 3000)
        }
    }

    fun stopRotation() {
        handler?.removeCallbacks(textRunnable)
    }
}