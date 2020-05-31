package com.lucers.main

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.base.BaseApplication
import com.lucers.common.constants.ModuleApplication

/**
 * AppApplication
 */
class MainApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initApplication(this)
    }

    override fun initApplication(context: Context) {
    }
}
