package com.lucers.common.manager

import android.content.Context
import com.lucers.common.ui.popupwindow.LoadingWindow

/**
 * PopupWindowManager
 */
object PopupWindowManager {

    fun showLoadingWindow(context: Context) {
        LoadingWindow(context, LoadingWindow::class.java.name)
    }
}