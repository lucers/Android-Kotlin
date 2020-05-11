package com.lucers.widget.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * TimeAxisItemDecoration
 */
class TimeAxisItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {

    val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    var lineColor = Color.BLUE
    var circleColor = Color.BLUE

    var lineWidth = SizeUtils.dp2px(2f)
    var circleSize = SizeUtils.dp2px(5f)

    var space = SizeUtils.dp2px(40f)

    init {
        linePaint.color = lineColor
        circlePaint.color = circleColor
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        parent.adapter?.let {
            val start = space / 2
            val end = start + lineWidth / 2

            for (index: Int in IntRange(0, parent.childCount - 1)) {
                val childView = parent.getChildAt(index)
                val top = childView.top + childView.paddingTop + circleSize * 2
                // drawCircle
                canvas.drawCircle(start.toFloat(), top.toFloat(), circleSize.toFloat(), circlePaint)
                // drawLine
                val position = parent.getChildAdapterPosition(childView)
                val bottom: Int
                bottom = if (position == it.itemCount - 1) {
                    top
                } else {
                    top + childView.height
                }
                canvas.drawRect((start - lineWidth / 2).toFloat(), top.toFloat(), end.toFloat(), bottom.toFloat(), linePaint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(space, 0, 0, 0)
    }
}