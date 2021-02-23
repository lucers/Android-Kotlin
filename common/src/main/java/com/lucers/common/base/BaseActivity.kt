package com.lucers.common.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.ui.popupwindow.LoadingWindow

/**
 * BaseActivity
 */
abstract class BaseActivity(val contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    open var screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()

        ARouter.getInstance().inject(this)

        initView(savedInstanceState)

        initData(savedInstanceState)
    }

    open fun initWindow() {
        //orientation
        requestedOrientation = screenOrientation
        //statusBar
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private var loadingWindow: LoadingWindow? = null

    open fun startLoading() {
        runOnUiThread {
            hideSoftKeyboard()
            try {
                loadingWindow?.showAtLocation(window.decorView, Gravity.CENTER, 0, 0) ?: let {
                    loadingWindow = LoadingWindow(window.decorView.context).also {
                        it.showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
                    }
                }
            } catch (e: Exception) {
                LogUtils.e("startLoading failed: ${e.message}")
            }
        }
    }

    open fun stopLoading() {
        runOnUiThread { loadingWindow?.dismiss() }
    }

    open fun backActivity() {
        stopLoading()
        super.onBackPressed()
    }

    override fun onBackPressed() {
        loadingWindow?.let {
            if (it.isShowing) {
                stopLoading()
                return
            }
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        loadingWindow?.dismiss()
        loadingWindow = null
        super.onDestroy()
    }
}