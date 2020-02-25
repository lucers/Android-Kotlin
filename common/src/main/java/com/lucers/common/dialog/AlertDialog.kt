package com.lucers.common.dialog

import android.app.Dialog
import android.os.Bundle
import com.lucers.common.R
import com.lucers.common.base.BaseDialogFragment
import com.lucers.common.constants.BundleConstants
import kotlinx.android.synthetic.main.dialog_alert.*

/**
 * AlertDialog
 *
 * @author Lucers
 * @date 2019/8/26 0026
 */
class AlertDialog : BaseDialogFragment() {

    var onConfirmClickListener: OnConfirmClickListener? = null

    override fun getDialogLayoutId(): Int = R.layout.dialog_alert

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun initView() {
        btn_confirm.setOnClickListener {
            onConfirmClickListener?.onConfirmClick()
            dismiss()
        }
        btn_cancel.setOnClickListener {
            dismiss()
        }
    }

    override fun initData() {
        tv_alert_message.text = arguments?.getString(BundleConstants.alertMessage)
    }

    interface OnConfirmClickListener {

        fun onConfirmClick()
    }
}