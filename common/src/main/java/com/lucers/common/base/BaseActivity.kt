package com.lucers.common.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.manager.ActivityManager
import com.lucers.common.ui.popupwindow.LoadingWindow

/**
 * BaseActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    private var screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()

        setContentView(getActivityLayout())

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

    abstract fun getActivityLayout(): Int

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        ActivityManager.removeActivity(this)
        super.onDestroy()
    }
}