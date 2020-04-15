package com.lucers.common.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.manager.ActivityManager
import com.lucers.common.ui.popupwindow.LoadingWindow

/**
 * BaseActivity
 */
abstract class BaseActivity(val contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private var screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    private var loadingWindow: LoadingWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()

        DataBindingUtil.setContentView<ViewDataBinding>(this, contentLayoutId)

        ActivityManager.addActivity(this)

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


    fun showLoadingWindow(tag: String) {
        hideSoftKeyboard()
        Handler().postDelayed(
            {
                if (loadingWindow == null) {
                    loadingWindow = LoadingWindow(this, tag)
                }
                loadingWindow?.showAsDropDown(window.decorView, 0, 0, Gravity.NO_GRAVITY)
            }, 50
        )
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    open fun hideLoadingWindow() {
        loadingWindow?.let {
            it.dismiss()
            onLoadingWindowDismiss(it.getTag())
        }
    }

    override fun onBackPressed() {
        loadingWindow?.let {
            if (it.isShowing) {
                it.dismiss()
                onLoadingWindowDismiss(it.getTag())
                return
            }
        }
        super.onBackPressed()
    }

    fun onLoadingWindowDismiss(tag: String) {
        LogUtils.i("onLoadingWindowDismiss : tag is -> $tag")
    }

    override fun onDestroy() {
        ActivityManager.removeActivity(this)
        super.onDestroy()
    }
}