package com.lucers.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.blankj.utilcode.util.LogUtils
import com.lucers.common.base.BaseApplication
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.lang.IllegalStateException

/**
 * WXApplication
 */
class WXApplication : BaseApplication() {

    override fun initApplication(context: Context) {
        val wxAppId = BuildConfig.WX_APP_ID
        if (wxAppId.isBlank().not()) {
            registerToWeChat()
        } else {
            throw IllegalAccessException("WeChat AppId undefined!")
        }
    }

    private fun registerToWeChat() {
        val wxApi = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID)
        WXFunction.wxApi = wxApi

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val registerApp = wxApi?.registerApp(BuildConfig.WX_APP_ID) ?: false
                if (registerApp.not()) {
                    LogUtils.e("register to WeChat failed!")
                }
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }
}