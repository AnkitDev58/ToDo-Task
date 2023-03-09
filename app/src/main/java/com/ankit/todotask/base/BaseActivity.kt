package com.ankit.todotask.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ankit.todotask.databinding.DialogLoadingBinding
import com.ankit.todotask.utils.createAnyDialog

abstract class BaseActivity : AppCompatActivity() {

    private val bindingDialog by lazy { DialogLoadingBinding.inflate(layoutInflater) }
    private val dialogLoading by lazy { createAnyDialog(bindingDialog.root) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        listeners()
        observer()
    }

    abstract fun init()
    abstract fun listeners()
    abstract fun observer()

    fun showDialog(isShow: Boolean? = false) {
        if (isShow == true) {
            if (!dialogLoading.isShowing) dialogLoading.show()
        } else {
            dialogLoading.dismiss()
        }
    }
}