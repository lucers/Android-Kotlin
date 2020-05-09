package com.lucers.widget.itemdecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * TimeAxisItemDecoration
 */
class TimeAxisItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.adapter?.let {
            var left: Float
            var top: Float
            var right: Float
            var bottom: Float
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, 0)
    }
}