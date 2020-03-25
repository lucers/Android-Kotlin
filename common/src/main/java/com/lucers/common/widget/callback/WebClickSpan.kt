package com.lucers.common.widget.callback

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * WebClickSpan
 */
class WebClickSpan(private val spanClickListener: SpanClickListener) : ClickableSpan() {

    override fun onClick(widget: View) {
        spanClickListener.onSpanClick()
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.color = Color.TRANSPARENT
    }

    interface SpanClickListener {
        /**
         * onSpanClick
         */
        fun onSpanClick()
    }
}