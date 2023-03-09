package com.ankit.todotask.base

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ankit.todotask.room.DatabaseInstance

abstract class BaseViewModel(private val applications: Application) :
    AndroidViewModel(applications) {

    var isConnected = false
    var connectivity: MutableLiveData<Boolean> = MutableLiveData(false)
    val dao by lazy { DatabaseInstance.getInstance(applications).dao() }


    private val connectivityManager by lazy {
        applications.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }
    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnected = true
            connectivity.postValue(true)
            internetAvailable()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isConnected = false
            connectivity.postValue(false)
            internetAvailable()

        }
    }

    abstract fun internetAvailable()

    init {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    /**
     * if you want to show no internet connection in activity please use this observer */
    fun isConnectedObserver() = connectivity
}