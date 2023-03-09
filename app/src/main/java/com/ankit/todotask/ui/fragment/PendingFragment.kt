package com.ankit.todotask.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ankit.todotask.R
import com.ankit.todotask.adapter.ProductAdapter
import com.ankit.todotask.base.BaseFragment
import com.ankit.todotask.databinding.FragmentPendingBinding
import com.ankit.todotask.vm.MainViewModel

class PendingFragment : BaseFragment(R.layout.fragment_pending) {

    private lateinit var binding: FragmentPendingBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val adapter by lazy { ProductAdapter() }
    override fun bindView(view: View) {
        binding = FragmentPendingBinding.bind(view)
    }

    override fun init() {
        binding.recycler.adapter = adapter
    }

    override fun listeners() {
        adapter.onDone = {
            mainViewModel.taskDone(it)
        }
        adapter.onDelete = {
            mainViewModel.delete(it)
        }

        adapter.onEdit = {
            BottomSheet(it).show(childFragmentManager, "sss")
        }
    }

    override fun observer() {
        mainViewModel.getLocalProducts().observe(this.viewLifecycleOwner) {
            Log.i("pending: ", "$it")
            adapter.submitList(it)
        }
    }


}