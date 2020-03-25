package com.lucers.common.utils

import android.util.Log
import com.lucers.common.BuildConfig
import com.lucers.common.base.BaseApplication

/**
 * LogUtil
 *
 * @author Lucers
 * @date 2019/5/21
 */
object LogUtil {

    const val VERBOSE = 1
    const val DEBUG = 2
    const val INFO = 3
    const val WARN = 4
    const val ERROR = 5

    var level: Int = VERBOSE

    private val debugModel: Boolean = BuildConfig.DEBUG

    fun v(message: String) {
        v(AppUtil.getAppName(BaseApplication.INSTANCE), message)
    }

    fun v(tag: String, message: String) {
        if (level <= VERBOSE && debugModel) {
            Log.v(tag, message)
        }
    }

    fun d(message: String) {
        d(AppUtil.getAppName(BaseApplication.INSTANCE), message)
    }

    fun d(tag: String, message: String) {
        if (level <= DEBUG && debugModel) {
            Log.d(tag, message)
        }
    }

    fun i(message: String) {
        i(AppUtil.getAppName(BaseApplication.INSTANCE), message)
    }

    fun i(tag: String, message: String) {
        if (level <= INFO && debugModel) {
            Log.i(tag, message)
        }
    }

    fun w(message: String) {
        w(AppUtil.getAppName(BaseApplication.INSTANCE), message)
    }

    fun w(tag: String, message: String) {
        if (level <= WARN) {
            Log.w(tag, message)
        }
    }

    fun e(message: String) {
        e(AppUtil.getAppName(BaseApplication.INSTANCE), message)
    }

    fun e(tag: String, message: String) {
        if (level <= ERROR) {
            Log.e(tag, message)
        }
    }
}