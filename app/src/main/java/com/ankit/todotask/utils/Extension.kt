package com.ankit.todotask.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View


fun Context.createAnyDialog(view: View): Dialog = Dialog(this).apply {
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(view)
}
