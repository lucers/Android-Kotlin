package com.lucers.android

import android.content.Context
import android.os.Process
import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.lucers.android.constants.AppConstants
import com.lucers.common.base.BaseApplication
import com.lucers.common.constants.ModuleApplication
import com.tencent.bugly.crashreport.CrashReport
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * AppApplication
 */
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initApplication(this)
    }

    override fun initApplication(context: Context) {
        initBugly()

        ModuleApplication.names.forEach {
            val clazz = Class.forName(it)
            try {
                val app = clazz.newInstance() as BaseApplication
                app.initApplication(this)
            } catch (e: Exception) {
                LogUtils.e(e.message)
            }
        }
    }

    private fun initBugly() {
        val packageName = applicationContext.packageName
        val progressName = getProcessName(Process.myPid())
        val strategy = CrashReport.UserStrategy(applicationContext)
        strategy.isUploadProcess = progressName.isNullOrBlank() || progressName == packageName
        CrashReport.initCrashReport(applicationContext, AppConstants.buglyId, BuildConfig.DEBUG, strategy)
    }

    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!processName.isBlank()) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}
