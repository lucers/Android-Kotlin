package com.lucers.mvvm.base

import androidx.lifecycle.ViewModel

/**
 * BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}