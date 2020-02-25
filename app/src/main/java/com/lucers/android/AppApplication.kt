package com.lucers.android

import com.lucers.common.DelegatesExt
import com.lucers.common.base.BaseApplication
import com.lucers.common.utils.LogUtil
import com.tencent.mmkv.MMKV

/**
 * AndroidApplication
 */
class AppApplication : BaseApplication() {

    companion object {
        var INSTANCE: AppApplication by DelegatesExt.notNullSingleValue()

        var mmkv: MMKV by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initMMKV()
    }

    private fun initMMKV() {
        val mmkvPath = MMKV.initialize(this)
        LogUtil.i("Lucers", "mmkvPath:$mmkvPath")

        mmkv = MMKV.defaultMMKV()
    }
}
