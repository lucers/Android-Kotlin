package com.lucers.common.manager

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.lucers.common.base.BaseDialogFragment
import com.lucers.common.constants.BundleConstants
import com.lucers.common.ui.dialog.AlertDialog

object DialogManager {

    fun <D : BaseDialogFragment?> hideDialog(activity: FragmentActivity, dialog: Class<D>) {
        val fragmentManager = activity.supportFragmentManager
        val dialogFragment =
            fragmentManager.findFragmentByTag(dialog.simpleName) as BaseDialogFragment?
        dialogFragment?.let {
            dialogFragment.dismissAllowingStateLoss()
        }
    }

    fun showAlertDialog(activity: FragmentActivity, alertMessage: String, confirmClickListener: AlertDialog.OnConfirmClickListener) {
        val fragmentManager = activity.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragmentManager.findFragmentByTag(AlertDialog::class.java.simpleName)
        fragment?.let {
            fragmentTransaction.show(it)
        } ?: let {
            val dialogFragment = AlertDialog()
            dialogFragment.onConfirmClickListener = confirmClickListener
            val bundle = Bundle()
            bundle.putString(BundleConstants.alertMessage, alertMessage)
            dialogFragment.arguments = bundle
            dialogFragment.show(fragmentTransaction, dialogFragment.javaClass.simpleName)
        }
    }
}