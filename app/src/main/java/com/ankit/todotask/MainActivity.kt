package com.ankit.todotask

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ankit.todotask.base.BaseActivity
import com.ankit.todotask.databinding.ActivityMainBinding
import com.ankit.todotask.models.Resource
import com.ankit.todotask.vm.MainViewModel

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel by viewModels<MainViewModel>()
    private val navController by lazy { Navigation.findNavController(this, R.id.navHost) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun init() {

    }

    override fun listeners() {

    }

    override fun observer() {
        mainViewModel.getProducts().observe(this) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showDialog(it.isShow)
                }
                is Resource.Success -> {

                }
                null -> {

                }
            }
        }
    }
}