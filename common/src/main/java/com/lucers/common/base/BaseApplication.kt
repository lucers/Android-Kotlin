package com.lucers.common.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.lucers.common.BuildConfig
import com.lucers.common.DelegatesExt
import com.tencent.bugly.crashreport.CrashReport
import me.jessyan.autosize.AutoSizeConfig

/**
 * BaseApplication
 */
abstract class BaseApplication : Application() {

    companion object {
        var INSTANCE: Application by DelegatesExt.notNullSingleValue()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        // App day night model
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // ARouter log
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        // LogUtils log
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG

        // AutoSize log
        AutoSizeConfig.getInstance().setLog(BuildConfig.DEBUG)

        // ARouter init
        ARouter.init(this)

        // AutoSize textSize config
        AutoSizeConfig.getInstance().isExcludeFontScale = true

        initBugly()
    }

    private fun initBugly() {
        val packageName = applicationContext.packageName
        val progressName = ProcessUtils.getCurrentProcessName()
        val strategy = CrashReport.UserStrategy(applicationContext)
        strategy.isUploadProcess = progressName.isNullOrBlank() || progressName == packageName
        CrashReport.initCrashReport(applicationContext, BuildConfig.BUGLY_ID, BuildConfig.DEBUG, strategy)
    }

    abstract fun initApplication(context: Context)
}