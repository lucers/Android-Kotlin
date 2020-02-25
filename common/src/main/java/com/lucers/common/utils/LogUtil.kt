package com.lucers.common.utils

import android.util.Log
import com.lucers.common.BuildConfig

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

    /**
     * set level control log print
     */
    var level:Int = VERBOSE

    private val debugModel: Boolean = BuildConfig.DEBUG

    /**
     * verbose level log
     */
    fun v(tag: String, message: String) {
        if (level <= VERBOSE && debugModel) {
            Log.v(tag, message)
        }
    }

    /**
     * debug level log
     */
    fun d(tag: String, message: String) {
        if (level <= DEBUG && debugModel) {
            Log.d(tag, message)
        }
    }

    /**
     * info level log
     */
    fun i(tag: String, message: String) {
        if (level <= INFO && debugModel) {
            Log.i(tag, message)
        }
    }

    /**
     * warn level log
     */
    fun w(tag: String, message: String) {
        if (level <= WARN) {
            Log.w(tag, message)
        }
    }

    /**
     * error level log
     */
    fun e(tag: String, message: String) {
        if (level <= ERROR) {
            Log.e(tag, message)
        }
    }
}