package com.lucers.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lucers.common.base.BaseActivity

/**
 * BaseMvvmActivity
 */
abstract class BaseMvvmActivity<DataBinding : ViewDataBinding>(contentLayoutId: Int) : BaseActivity(contentLayoutId) {

    lateinit var dataBinding: DataBinding

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, contentLayoutId)
        dataBinding.lifecycleOwner = this
    }
}
