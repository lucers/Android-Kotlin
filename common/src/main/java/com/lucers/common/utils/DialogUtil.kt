package com.lucers.common.utils

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.lucers.common.base.BaseDialogFragment
import com.lucers.common.constants.BundleConstants
import com.lucers.common.dialog.AlertDialog
import com.lucers.common.dialog.BindPhoneDialog

object DialogUtil {

    fun <D : BaseDialogFragment?> hideDialog(activity: FragmentActivity, dialog: Class<D>) {
        val fragmentManager = activity.supportFragmentManager
        val dialogFragment =
            fragmentManager.findFragmentByTag(dialog.simpleName) as BaseDialogFragment?
        dialogFragment?.let {
            dialogFragment.dismissAllowingStateLoss()
        }
    }

    fun showAlertDialog(
        activity: FragmentActivity,
        alertMessage: String,
        confirmClickListener: AlertDialog.OnConfirmClickListener
    ) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragmentManager.findFragmentByTag(AlertDialog::class.java.simpleName)
        if (fragment != null) {
            fragmentTransaction.show(fragment)
            return
        }
        val dialogFragment = AlertDialog()
        dialogFragment.onConfirmClickListener = confirmClickListener
        val bundle = Bundle()
        bundle.putString(BundleConstants.alertMessage, alertMessage)
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentTransaction, dialogFragment.javaClass.simpleName)
    }

    fun showBindPhoneDialog(
        activity: FragmentActivity,
        onBindPhoneClickListener: BindPhoneDialog.OnBindPhoneClickListener,
        onRegisterClickListener: BindPhoneDialog.OnRegisterClickListener
    ) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragmentManager.findFragmentByTag(BindPhoneDialog::class.java.simpleName)
        if (fragment != null) {
            fragmentTransaction.show(fragment)
            return
        }
        val dialogFragment = BindPhoneDialog()
        dialogFragment.onBindPhoneClickListener = onBindPhoneClickListener
        dialogFragment.onRegisterClickListener = onRegisterClickListener
        dialogFragment.show(fragmentTransaction, dialogFragment.javaClass.simpleName)
    }
}