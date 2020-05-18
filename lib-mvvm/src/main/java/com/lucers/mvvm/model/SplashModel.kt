package com.lucers.mvvm.model

import android.app.Application
import androidx.lifecycle.*
import com.lucers.mvvm.BaseAndroidViewModel

/**
 * SplashModel
 */
class SplashModel (application: Application) : BaseAndroidViewModel(application), LifecycleObserver {

    var showCount: Boolean = false
    var countTime: Int = 0

    fun getPoster() {
    }

    fun startCount() {
    }
}