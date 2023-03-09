package com.ankit.todotask.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ankit.todotask.R
import com.ankit.todotask.adapter.ProductAdapter
import com.ankit.todotask.base.BaseFragment
import com.ankit.todotask.databinding.FragmentCompleteBinding
import com.ankit.todotask.vm.MainViewModel


class CompleteFragment : BaseFragment(R.layout.fragment_complete) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentCompleteBinding
    private val adapter by lazy { ProductAdapter() }

    override fun bindView(view: View) {
        binding = FragmentCompleteBinding.bind(view)
    }

    override fun init() {
        binding.recycler.adapter = adapter
    }

    override fun listeners() {
        adapter.onDelete = {
            mainViewModel.delete(it)
        }

        adapter.onEdit = {
            BottomSheet(it).show(childFragmentManager, "sss")
        }
    }

    override fun observer() {
        mainViewModel.getLocalProducts(true).observe(this.viewLifecycleOwner) {
            Log.i("compete: ", "$it")
            adapter.submitList(it)
        }
    }


}