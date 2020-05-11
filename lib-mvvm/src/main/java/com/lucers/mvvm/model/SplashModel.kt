package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.lucers.mvvm.BaseAndroidViewModel

/**
 * SplashModel
 */
class SplashModel(application: Application) : BaseAndroidViewModel(application) {

    var showCount: Boolean = false
    var countTime: Int = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getPoster() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startCount() {
        showCount = true
    }
}