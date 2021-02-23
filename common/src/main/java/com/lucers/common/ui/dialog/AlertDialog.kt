package com.lucers.common.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.lucers.common.R
import com.lucers.common.constants.BundleConstants
import com.lucers.common.databinding.DialogAlertBinding
import com.lucers.common.mvvm.BaseMvvmDialogFragment

/**
 * AlertDialog
 *
 * @author Lucers
 * @date 2019/8/26 0026
 */
class AlertDialog : BaseMvvmDialogFragment<DialogAlertBinding>(), View.OnClickListener {

    var onConfirmClickListener: OnConfirmClickListener? = null
    var showCancel = false


    override fun getDialogLayoutId(): Int = R.layout.dialog_alert

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }
        return dialog
    }

    override fun initView() {
        super.initView()
        dataBinding.btnConfirm.setOnClickListener(this)
        dataBinding.btnCancel.setOnClickListener(this)
        dataBinding.btnCancel.visibility = if (showCancel) View.VISIBLE else View.GONE
    }

    override fun initData() {
        super.initData()
        dataBinding.tvAlertMessage.text = arguments?.getString(BundleConstants.alertMessage)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.width = ScreenUtils.getScreenWidth().div(5).times(4)
        windowParams?.dimAmount = 0.1f
        window?.attributes = windowParams
    }

    override fun onClick(v: View) {
        dismiss()
        if (v.id == R.id.btn_confirm) {
            onConfirmClickListener?.onConfirmClick()
        }
    }

    interface OnConfirmClickListener {

        fun onConfirmClick()
    }
}