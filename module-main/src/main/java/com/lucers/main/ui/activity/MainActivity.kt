package com.lucers.main.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.common.constants.AppRouteConstants
import com.lucers.main.R
import com.lucers.main.databinding.ActivityMainBinding
import com.lucers.mvvm.BaseMvvmActivity

@Route(path = AppRouteConstants.mainRoute)
class MainActivity : BaseMvvmActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun onBackPressed() {
    }
}
