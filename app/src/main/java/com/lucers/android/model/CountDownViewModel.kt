package com.lucers.android.model

import androidx.lifecycle.ViewModel
import io.reactivex.Observable

/**
 * CountDownViewModel
 */
class CountDownViewModel : ViewModel() {

    var countTime: Long = 0L

    var countOver: Boolean = false

    fun startCount() {
    }

    fun stopCount() {

    }

    fun finishCount() {

    }
}