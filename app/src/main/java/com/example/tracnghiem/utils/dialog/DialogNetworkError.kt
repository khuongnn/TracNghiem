package com.example.tracnghiem.utils.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.tracnghiem.R
import com.example.tracnghiem.event.RefreshDataEvent
import org.greenrobot.eventbus.EventBus

class DialogNetworkError(context: Context) {
    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null

    init {
        alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder?.setCancelable(false)
        alertDialogBuilder?.setMessage("Unable Connect Network")
        alertDialogBuilder?.setPositiveButton("Reconnect") { _, _ ->
            dismiss()
            EventBus.getDefault().post(RefreshDataEvent())
        }
        alertDialogBuilder?.setNegativeButton("Cancel") { _, _ -> }
    }

    fun show() {
        alertDialog = alertDialogBuilder?.show()
    }

    private fun dismiss() {
        alertDialog?.dismiss()
    }

    fun isShowing(): Boolean {
        return alertDialog?.isShowing == true
    }
}