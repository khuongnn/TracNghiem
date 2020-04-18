package com.example.tracnghiem.utils.view

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.example.tracnghiem.R


fun Toast.showCustomToast(context: Context, message: String) {
    val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    val layout = inflater.inflate(R.layout.layout_custom_toast, null)
    val tvToastMessage = layout.findViewById<AppCompatTextView>(R.id.tvToastMessage)
    tvToastMessage.text = message
    val toast = Toast(context)
    toast.setGravity(Gravity.BOTTOM, 0, 200)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}



