package com.lucers.widget.itemdecoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * LineItemDecoration
 *
 * @author Lucers
 * @date 2019/9/20
 */
open class SimpleLineItemDecoration(@ColorInt color: Int, lineWidth: Int, lineHeight: Int) :
    ItemDecoration() {

    protected val lineWidth: Int
    protected val lineHeight: Int
    private val linePaint: Paint = Paint()

    @JvmOverloads
    constructor(@ColorInt color: Int, lineSize: Float = 0.5f) : this(
        color,
        SizeUtils.dp2px(lineSize),
        SizeUtils.dp2px(lineSize)
    )

    constructor(lineSize: Float = 0.5f) : this(Color.TRANSPARENT, lineSize)

    init {
        linePaint.color = color
        this.lineWidth = lineWidth
        this.lineHeight = lineHeight
    }

    override fun getItemOffsets(outRect: Rect, child: View, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, child, recyclerView, state)
        val layoutManager = recyclerView.layoutManager ?: return
        when (layoutManager) {
            is GridLayoutManager -> {
                outRect.left = lineWidth / 2
                outRect.right = lineWidth / 2
                outRect.top = lineHeight / 2
                outRect.bottom = lineHeight / 2
            }
            is LinearLayoutManager -> {
                when {
                    recyclerView.getChildAdapterPosition(child) + 1 == layoutManager.getItemCount() -> {
                        return
                    }
                    layoutManager.orientation == RecyclerView.HORIZONTAL -> {
                        outRect.right = lineWidth
                    }
                    else -> {
                        outRect.bottom = lineHeight
                    }
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        val layoutManager = recyclerView.layoutManager ?: return
        // transparent color don't have to draw
        if (linePaint.color == Color.TRANSPARENT) {
            return
        }
        if (layoutManager is GridLayoutManager) {
            if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
                drawGridHorizontalDecoration(canvas, recyclerView, layoutManager.spanCount)
            } else {
                drawGridVerticalDecoration(canvas, recyclerView, layoutManager.spanCount)
            }
            return
        }
        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.orientation == RecyclerView.HORIZONTAL) {
                drawLinearVerticalDecoration(canvas, recyclerView)
            } else {
                drawLinearHorizontalDecoration(canvas, recyclerView)
            }
        }
    }

    private fun drawGridHorizontalDecoration(canvas: Canvas, recyclerView: RecyclerView, spanCount: Int) {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            if (notLastRow(i, spanCount, childCount)) {
                left = child.right
                top = child.top
                right = left + lineWidth
                bottom = child.bottom
                if ((i + 1) % spanCount != 0) {
                    bottom += lineHeight
                }
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
            }
            if ((i + 1) % spanCount == 0) {
                continue
            }
            left = child.left
            top = child.bottom
            right = left + child.width
            bottom = top + lineHeight
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
        }
    }

    private fun drawGridVerticalDecoration(canvas: Canvas, recyclerView: RecyclerView, spanCount: Int) {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            if (notLastRow(i, spanCount, childCount)) {
                left = child.left
                top = child.bottom
                right = left + child.width
                bottom = top + lineHeight
                canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
            }
            if ((i + 1) % spanCount == 0) {
                continue
            }
            left = child.right
            top = child.top
            bottom = child.bottom
            if (i + spanCount < childCount) {
                bottom += lineHeight
            }
            right = left + lineWidth
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
        }
    }

    private fun notLastRow(position: Int, spanCount: Int, childCount: Int): Boolean {
        val lines = if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
        return lines != position / spanCount + 1
    }

    private fun drawLinearHorizontalDecoration(canvas: Canvas, recyclerView: RecyclerView) {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        for (i in 0 until recyclerView.childCount - 1) {
            val child = recyclerView.getChildAt(i)
            left = child.left
            top = child.bottom
            right = child.right
            bottom = top + lineHeight
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
        }
    }

    private fun drawLinearVerticalDecoration(canvas: Canvas, recyclerView: RecyclerView) {
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        for (i in 0 until recyclerView.childCount - 1) {
            val child = recyclerView.getChildAt(i)
            left = child.right
            top = child.top
            right = left + lineWidth
            bottom = child.bottom
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), linePaint)
        }
    }
}