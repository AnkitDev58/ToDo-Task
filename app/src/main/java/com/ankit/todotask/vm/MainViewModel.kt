package com.ankit.todotask.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ankit.todotask.base.BaseUrl
import com.ankit.todotask.base.BaseViewModel
import com.ankit.todotask.models.Product
import com.ankit.todotask.models.ProductModel
import com.ankit.todotask.models.Resource
import com.ankit.todotask.repo.ApiInterface
import com.ankit.todotask.repo.ApiInterfaceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel(applications: Application) : BaseViewModel(applications) {

    private val apiImplements by lazy {
        ApiInterfaceImpl(BaseUrl.getRetrofitClient().create(ApiInterface::class.java))
    }

    private val productMutable = MutableLiveData<Resource<ProductModel?>?>()

    fun getProducts() = productMutable

    fun getLocalProducts(isCompleted: Boolean = false) = dao.getList(isCompleted)

    private fun check() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (dao.isEmpty()) setProduct()
            }
        }
    }

    private fun setProduct() {
        viewModelScope.launch {
            if (isConnected) {
                productMutable.value = Resource.Loading(true)
                productMutable.value = handleResponse(apiImplements.getProducts())
                productMutable.value = Resource.Loading(false)
            } else productMutable.value = Resource.Error("Please check internet connection")
        }
    }

    private suspend fun handleResponse(response: Response<ProductModel?>?): Resource<ProductModel?> {
        return withContext(Dispatchers.IO) {
            if (response?.isSuccessful == true) {
                response.body().let {
                    insertIntoData(it)
                    Resource.Success(it)
                }
            } else {
                Resource.Error(response?.message() ?: "something went wrong")
            }
        }
    }

    private suspend fun insertIntoData(it: ProductModel?) {
        withContext(Dispatchers.IO) {
            dao.insert(it?.products ?: return@withContext)
        }
    }

    fun taskDone(it: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.updateTask(it.apply {
                    isComplete = true
                })
            }
        }
    }

    fun delete(it: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.deleteTask(it)
            }
        }
    }

    fun editTask(item: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.updateTask(item)
            }
        }
    }

    override fun internetAvailable() {
        if (isConnected) check() else viewModelScope.launch {
            productMutable.value =
                Resource.Error("Please check internet connection")
        }
    }
}