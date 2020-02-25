package com.lucers.common.popupwindow

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.lucers.common.R

@SuppressLint("InflateParams")
class LoadingWindow(context: Context, private val tag: Int) : PopupWindow() {

    fun getTag(): Int = tag

    init {
        val contentView: View = LayoutInflater.from(context).inflate(R.layout.window_loading, null)
        setContentView(contentView)
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isClippingEnabled = false
    }
}