package com.lucers.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.BuildConfig
import com.lucers.common.DelegatesExt
import com.tencent.mmkv.MMKV
import me.jessyan.autosize.AutoSizeConfig

/**
 * BaseApplication
 */
open class BaseApplication : Application() {

    companion object {
        var INSTANCE: Application by DelegatesExt.notNullSingleValue()

        var mmkv: MMKV by DelegatesExt.notNullSingleValue()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        LogUtils.getConfig().isLogSwitch = false

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            LogUtils.getConfig().isLogSwitch = true
        }

        AutoSizeConfig.getInstance().setLog(BuildConfig.DEBUG)

        ARouter.init(this)

        AutoSizeConfig.getInstance().isExcludeFontScale = true

        MMKV.initialize(this)
        mmkv = MMKV.defaultMMKV()
    }
}