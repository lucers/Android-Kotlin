package com.lucers.android.wxapi

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.lucers.android.WXApplication
import com.lucers.common.base.BaseActivity
import com.lucers.common.base.BaseApplication
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * WXPayEntryActivity
 */
class WXPayEntryActivity : BaseActivity(0), IWXAPIEventHandler {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val result: Boolean =
            (BaseApplication.INSTANCE as WXApplication).wxApi.handleIntent(intent, this)
        if (result.not()) {
            finish()
        }
    }

    override fun onResp(baseResp: BaseResp) {
        LogUtils.d("onResp:${baseResp}")
        if (baseResp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            val payResp = baseResp as PayResp
            when (payResp.errCode) {
                BaseResp.ErrCode.ERR_OK -> {
                    val extData = payResp.extData
                    LogUtils.d("extData:${extData}")
                }
                BaseResp.ErrCode.ERR_USER_CANCEL -> {
                    LogUtils.e("ErrorCode:${payResp.errCode}")
                }
                else -> {
                    LogUtils.e("ErrorCode:${payResp.errCode}")
                }
            }
        }
        finish()
    }

    override fun onReq(baseReq: BaseReq) {
        LogUtils.d("onResp:${baseReq}")
    }
}