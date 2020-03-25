package com.lucers.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import me.jessyan.autosize.AutoSizeConfig

/**
 * BaseApplication
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)

        AutoSizeConfig.getInstance().isExcludeFontScale = true

        MvRx.viewModelConfigFactory = MvRxViewModelConfigFactory(applicationContext)
    }
}