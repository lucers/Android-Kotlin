package com.lucers.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver

/**
 * BaseAndroidViewModel
 */
open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
}