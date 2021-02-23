package com.lucers.common.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lucers.common.base.BaseDialogFragment

/**
 * BaseMvvmDialogFragment
 */
abstract class BaseMvvmDialogFragment<DataBinding : ViewDataBinding> : BaseDialogFragment() {

    lateinit var dataBinding: DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, getDialogLayoutId(), container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }
}