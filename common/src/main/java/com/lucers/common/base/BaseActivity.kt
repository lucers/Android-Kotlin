package com.lucers.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.ui.popupwindow.LoadingWindow
import com.lucers.common.utils.LogUtil

/**
 * BaseActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    private var screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    private var loadingWindow: LoadingWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()

        setContentView(getActivityLayout())

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

    abstract fun getActivityLayout(): Int

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    override fun invalidate() {
        LogUtil.e(this.javaClass.name, "invalidate")
    }

    open fun dealWithRequest(request: Async<Any>) {
        when (request) {
            is Loading -> {
                showLoadingWindow(request.hashCode())
            }
            is Fail -> {
                hideLoadingWindow()
                request.error.message?.let {
                    LogUtil.e(this.javaClass.name, it)
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
            else -> hideLoadingWindow()
        }
    }

    fun showLoadingWindow(tag: String) {
        if (loadingWindow == null) {
            loadingWindow = LoadingWindow(this, tag)
        }
        loadingWindow?.showAsDropDown(window?.decorView, 0, 0, Gravity.NO_GRAVITY)
    }

    open fun hideLoadingWindow() {
        loadingWindow?.dismiss()
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
        LogUtil.i("onLoadingWindowDismiss : tag is -> $tag")
    }
}