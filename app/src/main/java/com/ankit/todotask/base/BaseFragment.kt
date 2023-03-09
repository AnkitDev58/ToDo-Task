package com.ankit.todotask.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        init()
        listeners()
        observer()
    }

    abstract fun bindView(view: View)

    abstract fun init()
    abstract fun listeners()
    abstract fun observer()
}