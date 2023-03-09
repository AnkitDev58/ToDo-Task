package com.ankit.todotask.repo

import com.ankit.todotask.models.ProductModel
import com.ankit.todotask.room.DatabaseInstance
import retrofit2.Response

class ApiInterfaceImpl(private val apiInterface: ApiInterface) {

    suspend fun getProducts(): Response<ProductModel?>? {
        return try {
            apiInterface.getProducts()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}