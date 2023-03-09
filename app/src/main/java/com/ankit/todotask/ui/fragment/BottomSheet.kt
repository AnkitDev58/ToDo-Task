package com.ankit.todotask.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ankit.todotask.databinding.DialogEditBinding
import com.ankit.todotask.models.Product
import com.ankit.todotask.vm.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet(val product: Product) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogEditBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogEditBinding.inflate(layoutInflater)
        init()
        binding.btnDone.setOnClickListener {
            insert()
        }
        return binding.root
    }

    private fun insert() {
        val price =
            if (binding.edtPrice.text?.isNotEmpty() == true) binding.edtPrice.text.toString()
                .toInt() else product.price
        val discount =
            if (binding.edtDist.text?.isNotEmpty() == true) binding.edtDist.text.toString()
                .toDouble() else product.discountPercentage
        val rating =
            if (binding.edtRating.text?.isNotEmpty() == true) binding.edtRating.text.toString()
                .toDouble() else product.rating

        mainViewModel.editTask(product.also {
            it.price = price
            it.discountPercentage = discount
            it.rating = if (rating > 5.0) 5.0 else rating
        })
        dismiss()

    }

    private fun init() {
        binding.apply {
            edtDist.setText("${product.discountPercentage}")
            edtPrice.setText("${product.price}")
            edtRating.setText("${product.rating}")
        }
    }

}