package com.lucers.mvvm.model

import androidx.lifecycle.ViewModel

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