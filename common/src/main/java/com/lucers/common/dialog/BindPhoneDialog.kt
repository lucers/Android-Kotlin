package com.lucers.common.dialog

import android.app.Dialog
import android.os.Bundle
import com.lucers.common.R
import com.lucers.common.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_bind_phone.*

/**
 * AlertDialog
 *
 * @author Lucers
 * @date 2019/8/26 0026
 */
class BindPhoneDialog : BaseDialogFragment() {

    var onBindPhoneClickListener: OnBindPhoneClickListener? = null
    var onRegisterClickListener: OnRegisterClickListener? = null

    override fun getDialogLayoutId() = R.layout.dialog_bind_phone

    override fun initView() {
        btn_bind_phone.setOnClickListener {
            onBindPhoneClickListener?.onBindPhoneClick()
            dismiss()
        }
        btn_to_register.setOnClickListener {
            onRegisterClickListener?.onRegisterClick()
            dismiss()
        }
    }

    override fun initData() {
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    interface OnBindPhoneClickListener {

        fun onBindPhoneClick()
    }

    interface OnRegisterClickListener {

        fun onRegisterClick()
    }
}