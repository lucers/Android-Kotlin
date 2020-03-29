package com.lucers.common.manager

import androidx.fragment.app.FragmentActivity
import java.util.*

/**
 * ActivityManager
 */
class ActivityManager {

    private val activityStack: Stack<FragmentActivity> = Stack<FragmentActivity>()

    fun addActivity(activity: FragmentActivity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: FragmentActivity) {
        activityStack.remove(activity)
    }

    fun currentActivity(): FragmentActivity {
        return activityStack.lastElement()
    }

    private fun finishActivity(activity: FragmentActivity?) {
        activity?.let {
            activityStack.remove(it)
            it.finish()
        }
    }
}