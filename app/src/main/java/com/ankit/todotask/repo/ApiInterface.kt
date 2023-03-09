package com.ankit.todotask.repo

import com.ankit.todotask.models.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    suspend fun getProducts(): Response<ProductModel?>

}