package com.lucers.mvvm.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lucers.common.base.BaseActivity

/**
 * BaseMvvmActivity
 */
abstract class BaseMvvmActivity<DataBinding : ViewDataBinding>(contentLayoutId: Int) : BaseActivity(contentLayoutId) {

    lateinit var dataBinding: DataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        dataBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        dataBinding.lifecycleOwner = this
        super.onCreate(savedInstanceState)
    }
}
