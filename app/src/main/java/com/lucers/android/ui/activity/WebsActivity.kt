package com.lucers.android.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lucers.android.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.constants.AppRouteConstants

/**
 * WebsActivity
 */
@Route(path = AppRouteConstants.websRoute)
class WebsActivity : BaseActivity(R.layout.activity_webs) {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}