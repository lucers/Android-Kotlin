package com.lucers.common.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.manager.ActivityManager

/**
 * BaseActivity
 */
abstract class BaseActivity(val contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    var screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()

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

    private fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun onDestroy() {
        ActivityManager.removeActivity(this)
        super.onDestroy()
    }
}