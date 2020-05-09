package com.lucers.common.ui.activity

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.lucers.common.R
import com.lucers.common.base.BaseActivity
import com.lucers.common.ui.adapter.ActivityAdapter
import kotlinx.android.synthetic.main.activity_activity_manage.*

/**
 * ActivityManageActivity
 */
class ActivityManageActivity : BaseActivity(R.layout.activity_activity_manage) {

    val activities = mutableListOf<Class<*>>()

    val activityAdapter = ActivityAdapter(activities)

    override fun initWindow() {
        super.initWindow()
        immersionBar {
            statusBarColor(R.color.colorAccent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        gv_activities.adapter = activityAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        for (activityInfo: ActivityInfo in packageInfo.activities) {
            LogUtils.d(activityInfo.name)
            if (activityInfo.name == this.javaClass.name) {
                continue
            }
            val activityClass = Class.forName(activityInfo.name)
            activities.add(activityClass)
        }
        activityAdapter.notifyDataSetChanged()
    }
}