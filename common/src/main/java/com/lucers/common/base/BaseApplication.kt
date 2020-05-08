package com.lucers.common.base

import android.app.Application
import android.content.Context
import android.os.Process
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.lucers.common.BuildConfig
import com.lucers.common.DelegatesExt
import com.tencent.bugly.crashreport.CrashReport
import me.jessyan.autosize.AutoSizeConfig
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG

        AutoSizeConfig.getInstance().setLog(BuildConfig.DEBUG)

        ARouter.init(this)

        AutoSizeConfig.getInstance().isExcludeFontScale = true

//        initBugly()
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