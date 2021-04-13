package com.lucers.android

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.base.BaseApplication
import com.lucers.common.constants.ModuleApplication

/**
 * AppApplication
 */
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initApplication(this)
    }

    override fun initApplication(context: Context) {
        ModuleApplication.names.forEach {
            try {
                val clazz = Class.forName(it)
                val app = clazz.newInstance() as BaseApplication
                app.initApplication(this)
            } catch (e: Exception) {
                LogUtils.e(e.message)
            }
        }
    }
}
