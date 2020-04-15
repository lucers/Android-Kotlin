package com.lucers.android.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

@Route(path = AppRouteConstants.mainRoute)
class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }
}
